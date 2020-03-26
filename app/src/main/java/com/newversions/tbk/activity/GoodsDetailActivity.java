package com.newversions.tbk.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.activity.HomeActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.VipExplainActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.PermissionsChecker;
import com.yunqin.bearmall.widget.CompletedView;
import com.yunqin.bearmall.widget.OpenGoodsDetail;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * 商业详情
 */
public class GoodsDetailActivity extends BaseActivity implements Serializable, GoodsDetailContract.View {

    @BindView(R.id.ban_goods_image)
    Banner banGoodsImage;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.tv_goods_yuanjia_1)
    TextView tvGoodsYuanjia;
    @BindView(R.id.tv_goods_xiaoliang)
    TextView tvGoodsXiaoliang;
    @BindView(R.id.tv_goods_qixian)
    TextView tvGoodsQixian;
    @BindView(R.id.wv_goods_detail)
    WebView wvGoodsDetail;
    @BindView(R.id.tv_quanhoujia_bottom)
    TextView tvQuanhoujiaBottom;
    @BindView(R.id.tv_yongjin_num)
    TextView tv_yongjin_num;
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
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @BindView(R.id.rl_seller_name)
    LinearLayout rlSellerName;
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
    @BindView(R.id.shenji)
    LinearLayout shenji;
    @BindView(R.id.s_y)
    TextView s_y;
    @BindView(R.id.zui)
    TextView zui;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.g_2)
    TextView mG2;
    @BindView(R.id.shen_ji)
    TextView shen_ji;


    private TextView quanhoujia;
    private TextView youhui, shenq;
    private RelativeLayout qh;
    private int imageSize = 0;
    private List<TBKHomeGoodsEntity.RecommendBean> mList = new ArrayList<>();
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
    private GoodDetailEntity.GoodDetailBean goodDetail;
    private List<String> images;
    private PopupWindow mMPopupWindow;
    private String mShouc;
    private CompletedView copmleted;
    private int indusum = 20;
    private TextView mView2;
    private TextView mXiang;
    private TextView mWan;
    private int mFinalI = 0;

    public static void startGoodsDetailActivity(Context context, String goodsId, String shouc) {
        startGoodsDetailActivity(context, goodsId, Constants.GOODS_TYPE_DEFAULT, shouc);
    }

    public static void startGoodsDetailActivity(Context context, String goodsId, int type, String shouc) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(Constants.INTENT_KEY_ID, goodsId);
        intent.putExtra(Constants.INTENT_KEY_TYPE, type);
        intent.putExtra("Shouc", shouc);
        context.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void init() {
        setTranslucentStatus();
        initView();
        mPresenter = new GoodsDetailPresenter(this, this);
        homeAdapter = new MyAdapter();
        goodsId = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
        mShouc = getIntent().getStringExtra("Shouc");
        keyword = getIntent().getStringExtra("DETAILSKEYWORD");
        positin = getIntent().getIntExtra("POSITION", 0);
        search = getIntent().getBooleanExtra("SEARCH", false);
        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(this));
        mTwinklingRefreshLayout.setEnableLoadmore(false);
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.init(goodsId);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        rlv.setLayoutManager(linearLayoutManager);
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
        UserInfo userInfo = BearMallAplication.getInstance().getUser();
        if (userInfo != null && userInfo.getIdentity() != null) {
            //判断是否是合伙人
            if (userInfo.getIdentity().isPartner()) {
                shen_ji.setVisibility(View.GONE);
            } else {
                shen_ji.setVisibility(View.VISIBLE);
            }
        }

        tvGoodsTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!TextUtils.isEmpty(tvGoodsTitle.getText().toString())) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, tvGoodsTitle.getText().toString()));
                    showToast("复制成功");
                }
                return true;
            }
        });
        if (mShouc.equals("2")) {
            linCollect.setVisibility(View.VISIBLE);
        } else {
            linCollect.setVisibility(View.GONE);
        }
    }

    private void initView() {
        quanhoujia = findViewById(R.id.tv_quanhoujia_1);
        youhui = findViewById(R.id.youhui);
        shenq = findViewById(R.id.shenq);
        qh = findViewById(R.id.qh);
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

    @Override
    public void attachData(GoodDetailEntity goodDetailEntity) {
        mPresenter.getTBKHomeGoodsListData(goodsId);
        goodDetail = goodDetailEntity.getGoodDetail();
        images = goodDetail.getImages();
        tvGoodsTitle.setText(goodDetail.getName());
        banGoodsImage.setImages(goodDetail.getImages());
        banGoodsImage.start();
        tvGoodsQixian.setText(goodDetail.getCouponStartDate() + "-" + goodDetail.getCouponEndDate());
        try {
            String[] split = CommonUtils.doubleToString(goodDetail.getDiscountPrice()).split("\\.");
            String str2 = split[0] + ".<small>" + split[1] + "</small>";
            quanhoujia.setText(Html.fromHtml(str2));
            tvGoodsYuanjia.setText("¥" + CommonUtils.doubleToString(goodDetail.getPrice()));
            tvGoodsYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        wvGoodsDetail.setVisibility(View.VISIBLE);
        tvGoodsXiaoliang.setText("销量" + goodDetail.getSellNum());
        zui.setText(goodDetail.getMaxCommision() + "元");
        collection = goodDetail.isCollected();
        changeCollection(goodDetail.isCollected());
        if (mShouc.equals("3")) {
            s_y.setText("收益" + CommonUtils.doubleToString(goodDetail.getCommision()) + "元");
            tv_yongjin_num.setText("收益" + CommonUtils.doubleToString(goodDetail.getCommision()) + "元");
        } else if (mShouc.equals("4")) {
            double doubleExtra = getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0);
            String comm = CommonUtils.doubleToString(doubleExtra);
            s_y.setText("收益" + comm + "元");
            tv_yongjin_num.setText("收益" + comm + "元");
        } else {
            s_y.setText("收益" + getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0) + "元");
            tv_yongjin_num.setText("收益" + getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0) + "元");
        }

        if (StringUtils.isEmpty(goodDetail.getSellerName())) {
            rlSellerName.setVisibility(View.GONE);
        } else {
            Glide.with(GoodsDetailActivity.this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(goodDetail.getSellerLogo()).into(imLogo);
            tvSellerName.setText(goodDetail.getSellerName());

            tvArg1.setText(goodDetail.getDsr() + "");
            tvArg2.setText(goodDetail.getDse() + "");
            tvArg3.setText(goodDetail.getDss() + "");
            FunWu(tvArg1, tv_1, goodDetail.getDsr());
            FunWu(tvArg2, tv_2, goodDetail.getDse());
            FunWu(tvArg3, tv_3, goodDetail.getDss());

        }
        if ("0".equals(goodDetail.getCouponAmount() + "")) {
            qh.setVisibility(View.GONE);
            shenq.setVisibility(View.GONE);
            mG2.setText("抢购价");
        } else {
            qh.setVisibility(View.VISIBLE);
            shenq.setVisibility(View.VISIBLE);
            shenq.setText("立省" + goodDetail.getCouponAmount() + "元");
            youhui.setText(goodDetail.getCouponAmount() + "");
            mG2.setText("券后价");
        }


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

    private void FunWu(TextView sum, TextView fen, Double aDouble) {
        BigDecimal data1 = new BigDecimal(aDouble);
        BigDecimal data2 = new BigDecimal("4.8");
        BigDecimal data3 = new BigDecimal("4.5");

        if (data1.compareTo(data2) > 0 || data1.compareTo(data2) == 0) {
            sum.setTextColor(getResources().getColor(R.color.gao));
            fen.setText("高");
            fen.setTextColor(getResources().getColor(R.color.gao1));
            fen.setBackground(getResources().getDrawable(R.drawable.bg_gao));
        } else if (data1.compareTo(data2) < 0 && data1.compareTo(data3) > 0 || data1.compareTo(data3) == 0) {
            sum.setTextColor(getResources().getColor(R.color.ping));
            fen.setText("平");
            fen.setTextColor(getResources().getColor(R.color.ping1));
            fen.setBackground(getResources().getDrawable(R.drawable.bg_ping));
        } else if (data1.compareTo(data3) < 0) {
            sum.setTextColor(getResources().getColor(R.color.di));
            fen.setText("低");
            fen.setTextColor(getResources().getColor(R.color.di1));
            fen.setBackground(getResources().getDrawable(R.drawable.bg_di));
        }
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
    @OnClick({R.id.iv_btn_back, R.id.lin_collect, R.id.lin_collect2, R.id.lin_share, R.id.lin_quanhoujia,
            R.id.lin_buy_buy, R.id.ll_more_comm, R.id.iv_btn_download, R.id.shen_ji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_btn_back:
                finish();
                break;
            case R.id.lin_buy_buy:
            case R.id.lin_quanhoujia:
                // TODO: 2019/7/16 0016 购买
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(this);
                } else {
                    //Toast.makeText(this, "正在跳转淘宝", Toast.LENGTH_SHORT).show();
                    toTaobao();
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
                    Log.e("onViewClicked", goodsId + "");
                    Log.e("onViewClicked", collection + "");
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
            case R.id.iv_btn_download:
                if (images != null && images.size() > 0) {
                    //下载图片
                    mFinalI = 0;
                    indusum = 20;
                    permissionsChecker = new PermissionsChecker(GoodsDetailActivity.this);
                    showDialog2(images.size());
                }
                break;
            case R.id.shen_ji:
                UserInfo user = BearMallAplication.getInstance().getUser();
                if (user != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("audit", user.getIdentity().getIsAudit());
                    VipExplainActivity.opneVipExplainActivity(GoodsDetailActivity.this, VipExplainActivity.class, bundle);
                } else {
                    LoginActivity.starActivity(GoodsDetailActivity.this);
                }
                break;
        }
    }


    public class MyWebViewClient extends WebViewClient {

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

    public class MyAdapter extends RecyclerView.Adapter<GoodsViewHolder> {

        @NonNull
        @Override
        public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_xiangqin, parent, false);
            return new GoodsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GoodsViewHolder goodsViewHolder, int position) {
            TBKHomeGoodsEntity.RecommendBean recommendBean = mList.get(position);
            Log.i("TAG", "onBindViewHolder: " + recommendBean);
            goodsViewHolder.itemHomeProTitle.setText(recommendBean.getName());
            goodsViewHolder.itemHomeProQuan.setText(recommendBean.getCouponAmount() + "元券");
            try {
                goodsViewHolder.itemHomeProQuanhoujia.setText("¥" + CommonUtils.doubleToString(recommendBean.getDiscountPrice()));
                goodsViewHolder.itemHomeProYuanjia.setText("¥" + CommonUtils.doubleToString(recommendBean.getPrice()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            goodsViewHolder.itemHomeProYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            goodsViewHolder.tvCommision.setText("返 ¥ " + recommendBean.getCommision());
            Glide.with(GoodsDetailActivity.this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(recommendBean.getOutIcon()).into(goodsViewHolder.itemHomeProImage);
            goodsViewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(GoodsDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, mList.get(position).getCommision());
                intent.putExtra("Shouc", "1");
                GoodsDetailActivity.this.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        int id = R.layout.item_priduct_sum;
        @BindView(R.id.item_home_pro_image)
        ImageView itemHomeProImage;
        @BindView(R.id.item_home_pro_title)
        TextView itemHomeProTitle;
        @BindView(R.id.item_home_pro_quan)
        TextView itemHomeProQuan;
        @BindView(R.id.item_home_pro_quanhoujia)
        TextView itemHomeProQuanhoujia;
        @BindView(R.id.item_home_pro_yuanjia)
        TextView itemHomeProYuanjia;
        @BindView(R.id.tv_commision)
        TextView tvCommision;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //图片下载
    private static final long ALPHA_DURATION = 2000;
    private PermissionsChecker permissionsChecker;
    static final String[] PERMISSIONS = new String[]{WRITE_EXTERNAL_STORAGE};

    private void beginAlpha(List<String> ImageUrl) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(ALPHA_DURATION);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //没有考虑用户永久拒绝的情况
                if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
                    showMissingPermissionDialog();
                } else {
                    returnBitMap(ImageUrl);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animatorSet.start();
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoodsDetailActivity.this);
        builder.setTitle("帮助");
        builder.setMessage(R.string.string_help_text);

        // 拒绝, 退出应用
        builder.setNegativeButton("关闭", (dialog, which) -> {
            Toast.makeText(GoodsDetailActivity.this, "缺少必要权限，无法保存", Toast.LENGTH_SHORT).show();
        });

        builder.setPositiveButton("设置", (dialog, which) -> startAppSettings());

        builder.show();
    }

    private void showDialog2(int sume) {
        OpenGoodsDetail.lightoff(GoodsDetailActivity.this);
        View view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.dialog_goods, null);
        mMPopupWindow = new PopupWindow();
        mMPopupWindow.setContentView(view);
        mMPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mMPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置背景图片， 必须设置，不然动画没作用
        mMPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mMPopupWindow.setFocusable(false);
        // 设置点击popupwindow外屏幕其它地方消失
        mMPopupWindow.setOutsideTouchable(true);
        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mMPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        TextView view1 = view.findViewById(R.id.sume_one);
        mView2 = view.findViewById(R.id.sume_two);
        copmleted = view.findViewById(R.id.copmleted);
        mXiang = view.findViewById(R.id.xiang);
        mWan = view.findViewById(R.id.wan);
        view1.setText("共" + sume + "张  已下载 ");
        copmleted.setTargetPercent(0);
        mView2.setText("0");
        mMPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                OpenGoodsDetail.lighton(GoodsDetailActivity.this);
            }
        });
        view.findViewById(R.id.wan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMPopupWindow.dismiss();
            }
        });
        view.findViewById(R.id.xiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbum();
                mMPopupWindow.dismiss();
            }
        });
        //下载图片
        beginAlpha(images);
    }


    public void openAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }


    /**
     * 将URL转化成bitmap形式
     *
     * @param url
     * @return bitmap type
     */
    public final void returnBitMap(List<String> url) {
        copmleted.setTargetPercent(100);
        for (int i = 0; i < url.size(); i++) {
            Glide.with(GoodsDetailActivity.this).asBitmap().load(url.get(i)).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    //保存图片必须在子线程中操作，是耗时操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            File appDir = new File(Environment.getExternalStorageDirectory(), "InvitationPoster");
                            if (!appDir.exists()) {
                                appDir.mkdir();
                            }
                            String fileName = System.currentTimeMillis() + ".jpg";
                            File file = new File(appDir, fileName);
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.flush();
                                fos.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // 其次把文件插入到系统图库
                            try {
                                MediaStore.Images.Media.insertImage(GoodsDetailActivity.this.getContentResolver(), file.getAbsolutePath()
                                        , fileName, null);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                mFinalI++;
                                handler.sendMessage(new Message());
                                return;
                            }
                            mFinalI++;
                            handler.sendMessage(new Message());
                            // 最后通知图库更新
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(file);
                            intent.setData(uri);
                            GoodsDetailActivity.this.sendBroadcast(intent);
                            if (file.isFile() && file.exists()) {
                                file.delete();
                            }
                        }
                    }).start();
                }
            });
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mView2.setText(mFinalI + "");
            if (mFinalI == images.size()) {
                copmleted.setVisibility(View.GONE);
                mXiang.setVisibility(View.VISIBLE);
                mWan.setText("完成");
            }
        }
    };


    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + GoodsDetailActivity.this.getPackageName()));
        GoodsDetailActivity.this.startActivity(intent);
    }
}
