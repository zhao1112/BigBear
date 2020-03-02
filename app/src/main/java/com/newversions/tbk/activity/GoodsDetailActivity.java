package com.newversions.tbk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.newversions.tbk.entity.GoodDetailEntity;
import com.newversions.tbk.entity.TBKHomeGoodsEntity;
import com.newversions.tbk.entity.ToTaoBaoEntity;
import com.newversions.tbk.utils.GlideImageLoader;
import com.newversions.tbk.utils.StringUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ContenGoods;
import com.yunqin.bearmall.ui.activity.HomeActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 商业详情
 */
public class GoodsDetailActivity extends BaseActivity implements Serializable, GoodsDetailContract.View {

    @BindView(R.id.ban_goods_image)
    Banner banGoodsImage;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.tv_goods_yuanjia)
    TextView tvGoodsYuanjia;
    @BindView(R.id.tv_quanhoujia)
    TextView tvQuanhoujia;
    @BindView(R.id.tv_goods_xiaoliang)
    TextView tvGoodsXiaoliang;
    @BindView(R.id.tv_stamps_value)
    TextView tvStampsValue;
    @BindView(R.id.tv_goods_qixian)
    TextView tvGoodsQixian;
    @BindView(R.id.tv_get_stamps)
    TextView tvGetStamps;
    @BindView(R.id.wv_goods_detail)
    WebView wvGoodsDetail;
    @BindView(R.id.tv_quanhoujia_bottom)
    TextView tvQuanhoujiaBottom;
    @BindView(R.id.tv_yongjin_num)
    TextView tv_yongjin_num;
    @BindView(R.id.lin_get_stamps)
    LinearLayout linGetStamps;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.lin_collect)
    LinearLayout linCollect;
    @BindView(R.id.lin_share)
    LinearLayout linShare;
    @BindView(R.id.lin_quanhoujia)
    LinearLayout linQuanhoujia;
    @BindView(R.id.lin_buy)
    LinearLayout linBuy;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @BindView(R.id.rl_seller_name)
    RelativeLayout rlSellerName;
    @BindView(R.id.lin_collect2)
    LinearLayout linCollect2;
    @BindView(R.id.iv_btn_back)
    ImageView ivBtnBack;
    @BindView(R.id.im_logo)
    ImageView imLogo;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_arg_1)
    TextView tvArg1;
    @BindView(R.id.tv_arg_2)
    TextView tvArg2;
    @BindView(R.id.tv_arg_3)
    TextView tvArg3;

    private List<TBKHomeGoodsEntity.RecommendBean> mList = new ArrayList<>();
    private int page;
    private String itemId;
    private int type;//1表示常规商品,2表示自营商品,3表示猜你喜欢商品
    public static final int TYPE_GOODS = 1;
    public static final int TYPE_TBK = 2;
    private String goodsId;
    private String keyword;
    private boolean hasNext;
    private GoodsDetailContract.Presenter mPresenter;
    private MyAdapter homeAdapter;
    private boolean collection;
    private boolean search;
    private double commission;
    private int positin;
    private String TAG = "WebView";

    public static void startGoodsDetailActivity(Context context, String goodsId) {
        startGoodsDetailActivity(context, goodsId, Constants.GOODS_TYPE_DEFAULT);
    }

    public static void startGoodsDetailActivity(Context context, String goodsId, int type) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(Constants.INTENT_KEY_ID, goodsId);
        intent.putExtra(Constants.INTENT_KEY_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void init() {
        setTranslucentStatus();
        mPresenter = new GoodsDetailPresenter(this, this);
        homeAdapter = new MyAdapter();
        goodsId = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
        keyword = getIntent().getStringExtra("DETAILSKEYWORD");
        positin = getIntent().getIntExtra("POSITION", 0);
        search = getIntent().getBooleanExtra("SEARCH", false);
        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(this));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.init(goodsId);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                Log.d("TAG1122", "onLoadMore: " + hasNext);
                if (hasNext) {
                    mPresenter.getMoreLikeGoods(goodsId);
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });
        Log.d("goods", "init: " + goodsId);
        rlv.setLayoutManager(new GridLayoutManager(this, 2));
        rlv.setAdapter(homeAdapter);
        mPresenter.init(goodsId);
        mPresenter.getContenGoods(goodsId);
        banGoodsImage.setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        banGoodsImage.isAutoPlay(true);
        //设置轮播时间
        banGoodsImage.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banGoodsImage.setIndicatorGravity(BannerConfig.CENTER);
        wvGoodsDetail.getSettings().setJavaScriptEnabled(true);
        // 设置缓存模式：不使用缓存
        wvGoodsDetail.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wvGoodsDetail.getSettings().setDomStorageEnabled(true);
        getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_DEFAULT);
        if (type == Constants.GOODS_TYPE_TBK || type == Constants.GOODS_TYPE_TBK_SEARCH) {
            linCollect.setVisibility(View.GONE);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<GoodsViewHolder> {

        @NonNull
        @Override
        public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_priduct_sum, parent, false);
            return new GoodsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GoodsViewHolder goodsViewHolder, int position) {
            TBKHomeGoodsEntity.RecommendBean recommendBean = mList.get(position);
            Log.i("TAG", "onBindViewHolder: " + recommendBean);
            goodsViewHolder.itemHomeProTitle.setText(recommendBean.getName());
            goodsViewHolder.itemHomeProQuan.setText(recommendBean.getCouponAmount() + "元券");
            goodsViewHolder.itemHomeXiaoliang.setText("月销" + recommendBean.getSellNum());
            goodsViewHolder.itemHomeProQuanhoujia.setText("¥" + recommendBean.getDiscountPrice());
            goodsViewHolder.itemHomeProYuanjia.setText("¥" + recommendBean.getPrice());
            goodsViewHolder.itemHomeProYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            goodsViewHolder.tvCommision.setText("预估佣金：" + recommendBean.getCommision());
            Glide.with(GoodsDetailActivity.this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(recommendBean.getOutIcon()).into(goodsViewHolder.itemHomeProImage);
            goodsViewHolder.itemSellerName.setText(StringUtils.addImageLabel(GoodsDetailActivity.this, recommendBean.getTmall() == 1 ?
                    R.mipmap.icon_tmall : R.mipmap.icon_taobao1, recommendBean.getSellerName()));
            goodsViewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(GoodsDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, mList.get(position).getCommision());
                GoodsDetailActivity.this.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        int id = R.layout.item_priduct_sum;
        @BindView(R.id.item_home_pro_image)
        ImageView itemHomeProImage;
        @BindView(R.id.item_home_pro_title)
        TextView itemHomeProTitle;
        @BindView(R.id.item_home_pro_quan)
        TextView itemHomeProQuan;
        @BindView(R.id.item_home_xiaoliang)
        TextView itemHomeXiaoliang;
        @BindView(R.id.item_home_pro_quanhoujia)
        TextView itemHomeProQuanhoujia;
        @BindView(R.id.item_home_pro_yuanjia)
        TextView itemHomeProYuanjia;
        @BindView(R.id.tv_commision)
        TextView tvCommision;
        @BindView(R.id.item_seller_name)
        TextView itemSellerName;
        @BindView(R.id.rl)
        RelativeLayout rl;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void showLoad() {
        showLoading();
    }

    @Override
    public void hideLoad() {
        Log.i("hideLoad", "hideLoad: ");
        hiddenLoadingView();
    }

    @Override
    public void onNotNetWork() {
        Toast.makeText(GoodsDetailActivity.this, "服务器繁忙,请重新加载", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefreshFinish() {
        mTwinklingRefreshLayout.finishRefreshing();
    }

    @Override
    public void onLoadMoreFinish() {
        mTwinklingRefreshLayout.onFinishLoadMore();
        mTwinklingRefreshLayout.finishLoadmore();
    }

    GoodDetailEntity.GoodDetailBean goodDetail;

    @Override
    public void attachData(GoodDetailEntity goodDetailEntity) {
        mPresenter.getTBKHomeGoodsListData(goodsId);
        goodDetail = goodDetailEntity.getGoodDetail();
        tvGoodsTitle.setText(goodDetail.getName());
        banGoodsImage.setImages(goodDetail.getImages());
        banGoodsImage.start();
        tvGoodsQixian.setText("使用期限：" + goodDetail.getCouponStartDate() + "-" + goodDetail.getCouponEndDate());
        tvStampsValue.setText(goodDetail.getCouponAmount() + "元优惠券");
        if (getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_DEFAULT) == Constants.GOODS_TYPE_TBK) {
            tv_yongjin_num.setText("预估返：" + getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0) + "元");
            commission = getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0);
        } else {
            tv_yongjin_num.setText("预估返：" + goodDetail.getCommision() + "元");
            commission = goodDetail.getCommision();
        }
        tvGoodsYuanjia.setText("¥" + goodDetail.getPrice());
        tvGoodsYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tvQuanhoujia.setText("¥" + goodDetail.getDiscountPrice());
        if (StringUtils.isEmpty(goodDetail.getSellerName())) {
            rlSellerName.setVisibility(View.GONE);
        } else {
            Glide.with(GoodsDetailActivity.this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(goodDetail.getSellerLogo()).into(imLogo);
            tvSellerName.setText(goodDetail.getSellerName());
            tvArg1.setText("商品描述: " + goodDetail.getDsr());
            tvArg2.setText("买家服务: " + goodDetail.getDse());
            tvArg3.setText("物流服务: " + goodDetail.getDss());
        }
        wvGoodsDetail.setVisibility(View.VISIBLE);
        tvGoodsXiaoliang.setText(goodDetail.getSellNum() + "人已购");
        collection = goodDetail.isCollected();
        changeCollection(goodDetail.isCollected());
        //TODO[商品详情页]
        ConstantScUtil.searchDetail(goodsId + "", goodDetail.getName(), goodDetail.getCategoryId() + "",
                goodDetail.getSubCategoryId() + "", goodDetail.getSellerName(), goodDetail.getSellNum() + "",
                goodDetail.getCouponAmount() + "", commission + "", goodDetail.getPrice() + "",
                goodDetail.getDiscountPrice() + "");
        //TODO[搜索结果]
        ConstantScUtil.searchResult(search, keyword, positin, goodDetail.getId() + "", goodDetail.getName(),
                goodDetail.getCategoryId() + "", goodDetail.getSubCategoryId() + "", goodDetail.getSellerName(),
                goodDetail.getSellNum() + "", goodDetail.getCouponAmount() + "", commission + "",
                goodDetail.getPrice() + "", goodDetail.getDiscountPrice() + "");
    }

    public void changeCollection(boolean collection) {
        ivCollect.setImageResource(collection ? R.mipmap.icon_collect : R.mipmap.icon_like_details);
    }

    @Override
    public void attachAddData(TBKHomeGoodsEntity likeGuessEntity) {
        Log.d("TAG", "attachAddData: " + likeGuessEntity.getRecommend().size());
        mList.addAll(likeGuessEntity.getRecommend());
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void haseMore(boolean haseMore) {
        this.hasNext = haseMore;

    }

    //商品详情
    @Override
    public void contenGoods(ContenGoods conten) {
        if (conten != null && conten.getData().getPcDescContent() != null) {
            try {
                wvGoodsDetail.getSettings().setUseWideViewPort(true);
                wvGoodsDetail.getSettings().setLoadWithOverviewMode(true);
                wvGoodsDetail.getSettings().setBuiltInZoomControls(true);
                wvGoodsDetail.getSettings().setDisplayZoomControls(false);
                wvGoodsDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
                wvGoodsDetail.setWebChromeClient(new WebChromeClient());
                wvGoodsDetail.getSettings().setDefaultTextEncodingName("UTF-8");
                wvGoodsDetail.getSettings().setBlockNetworkImage(false);
                WebSettings settings = wvGoodsDetail.getSettings();
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                // 是否支持js  说了用js，这句是必须加上的
                settings.setJavaScriptEnabled(true);
                //  重写 WebViewClient
                wvGoodsDetail.setWebViewClient(new MyWebViewClient());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    wvGoodsDetail.getSettings().setMixedContentMode(wvGoodsDetail.getSettings()
                            .MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
                }
                String replace = conten.getData().getPcDescContent().replace("//", "https://");
                String substring = replace.substring(0, replace.indexOf("<p"));
                String replace1 = replace.replace(substring, "");
                wvGoodsDetail.loadDataWithBaseURL(null, replace1, "text/html", " charset=UTF-8", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void toTaobao() {
        Map<String, String> map = new HashMap<>();
        map.put("itemId", goodsId);
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getHighCommission(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                ToTaoBaoEntity toTaoBaoEntity = new Gson().fromJson(data, ToTaoBaoEntity.class);
                Log.i("onSuccess", data);
                if (toTaoBaoEntity.getCode() == 2) {
                    // TODO: 2019/8/15 0015 shouquan
                    Log.i("onSuccess", "onSuccess: " + "-------------------");
                    Intent intent = new Intent(GoodsDetailActivity.this, WebActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_URL, toTaoBaoEntity.getData());
                    intent.putExtra(Constants.INTENT_KEY_TITLE, "淘宝授权");
                    startActivity(intent);
                    finish();
                    return;
                }
                ArouseTaoBao arouseTaoBao = new ArouseTaoBao(GoodsDetailActivity.this);
                if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
                    arouseTaoBao.openTaoBao(toTaoBaoEntity.getData());
                } else {
                    showToast("请先下载淘宝");
                    hiddenLoadingView();
                }
            }

            @Override
            public void onNotNetWork() {
                Log.i("onNotNetWork", "onNotNetWork: ");
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    // todo 点击监听
    @OnClick({R.id.iv_btn_back, R.id.lin_collect, R.id.lin_collect2, R.id.lin_share, R.id.lin_get_stamps, R.id.lin_quanhoujia,
            R.id.lin_buy, R.id.ll_more_comm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_btn_back:
                finish();
                break;
            case R.id.lin_buy:
            case R.id.lin_get_stamps:
                // TODO: 2019/7/16 0016 购买
            case R.id.lin_quanhoujia:
                // TODO: 2019/7/16 0016 购买
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(this);
                } else {
                    //Toast.makeText(this, "正在跳转淘宝", Toast.LENGTH_SHORT).show();
                    STaobao();
//                    toTaobao();
                }
                break;
            case R.id.lin_collect:
                // TODO: 2019/7/16 0016  收藏
                if (BearMallAplication.getInstance().getUser() == null) {
                    showToast("请先登录");
                    LoginActivity.starActivity(this);
                } else {
                    collection = !collection;
                    Map<String, String> map = new HashMap<>();
                    map.put("goodsId", goodsId);
                    map.put("collection", collection + "");
                    RetrofitApi.request(this, RetrofitApi.createApi(Api.class).changeCollection(map), new RetrofitApi.IResponseListener() {
                        @Override
                        public void onSuccess(String data) throws JSONException {
                            changeCollection(collection);
                        }

                        @Override
                        public void onNotNetWork() {

                        }

                        @Override
                        public void onFail(Throwable e) {

                        }
                    });
                }
                break;
            case R.id.lin_collect2:
                // TODO: 2019/7/16 0016 首页 
                Intent intenta =
                        new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intenta);
                break;
            case R.id.lin_share:
                // TODO: 2019/7/16 0016  分享
                if (BearMallAplication.getInstance().getUser() != null) {
                    Intent intent = new Intent(this, ShareComissionActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_DATA, goodDetail);
                    startActivity(intent);
                    //TODO[分享]
                    if (goodDetail != null) {
                        ConstantScUtil.searchShare(goodDetail.getId() + "", goodDetail.getName(), goodDetail.getSellerName(),
                                goodDetail.getCouponAmount() + "", commission + "", goodDetail.getPrice() + "",
                                goodDetail.getDiscountPrice() + "");
                    }
                } else {
                    showToast("请先登录");
                    LoginActivity.starActivity(this);
                }
                break;
        }
    }

    private void STaobao() {
        LoginService loginService = MemberSDK.getService(LoginService.class);
        if (loginService != null) {
            loginService.auth(GoodsDetailActivity.this, new LoginCallback() {
                @Override
                public void onSuccess(Session session) {
                    //初始化成功
                    Log.e(TAG, "onSuccess: " + session.topAccessToken);
                    //第二次授权
                    BaiCuanUrl();
                }

                @Override
                public void onFailure(int i, String s) {
                    //提示 初始化 失败
                }
            });
        } else {
            //提示 初始化 失败
        }
    }

    private String BCUrl = "https://oauth.m.taobao.com/authorize?response_type=token&client_id=27683376&view=wap&redirect_ur=http://127.0.0.1:12345/error";

    private void BaiCuanUrl() {

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
                if (url.contains("map.")) {
                    Log.d(TAG, "shouldOverrideUrlLoading() returned: " + true + "---" + url);
                    return true;
                } else {
                    Log.d(TAG, "shouldOverrideUrlLoading() returned: " + false + "---" + url);
                    return false;
                }
            }
        };

        AlibcTrade.openByUrl(GoodsDetailActivity.this, "", BCUrl, null, webViewClient,
                null, null, null, null, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                        Log.d(TAG, "shouldOverrideUrlLoading() returned: " + true + "---");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.d(TAG, "shouldOverrideUrlLoading() returned: " + s + "---code" + i);
                    }
                });

    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (wvGoodsDetail == null) return;
            //  html加载完成之后，调用js的方法
            imgReset();
        }

        private void imgReset() {
            wvGoodsDetail.loadUrl("javascript:(function(){"
                    + "var objs = document.getElementsByTagName('img'); "
                    + "for(var i=0;i<objs.length;i++)  " + "{"
                    + "var img = objs[i];   "
                    + "    img.style.width = '100%';   "
                    + "    img.style.height = 'auto';   "
                    + "}" + "})()");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
