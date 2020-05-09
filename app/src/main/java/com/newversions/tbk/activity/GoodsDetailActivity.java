package com.newversions.tbk.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.base.ImageSelectInfor;
import com.bbcoupon.ui.activity.ChoiceShareActivity;
import com.bbcoupon.ui.activity.WebViewActivity;
import com.bbcoupon.util.ConstantUtil;
import com.bbcoupon.util.CopyTextUtil;
import com.bbcoupon.util.WindowUtils;
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
import com.newversions.tbk.view.ObservableScrollView;
import com.timqi.sectorprogressview.ColorfulRingProgressView;
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
import com.yunqin.bearmall.util.AuntTao;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DownLoadImage;
import com.yunqin.bearmall.widget.OpenGoodsDetail;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permison.PermissonUtil;
import permison.listener.PermissionListener;


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
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.isdouble)
    RelativeLayout isdouble;
    @BindView(R.id.double_content)
    TextView double_content;


    private TextView quanhoujia;
    private TextView youhui, shenq;
    private RelativeLayout qh;
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
    private String commissionType;
    private TextView downLoadImageSum;
    private TextView openImage;
    private TextView complete;
    private String[] ImageList = null;
    private DownLoadImage mInstance;
    private RelativeLayout mProgressView;
    private ColorfulRingProgressView mProgress;
    private TextView mProgressText;
    private String Profit;

    public static void startGoodsDetailActivity(Context context, String goodsId, String commission) {
        startGoodsDetailActivity(context, goodsId, Constants.GOODS_TYPE_DEFAULT, commission);
    }

    public static void startGoodsDetailActivity(Context context, String goodsId, int type, String commission) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(Constants.INTENT_KEY_ID, goodsId);
        intent.putExtra(Constants.INTENT_KEY_TYPE, type);
        intent.putExtra(Constants.INTENT_KEY_COMMISSION, commission);
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
        commissionType = getIntent().getStringExtra(Constants.INTENT_KEY_COMMISSION);
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
                    CopyTextUtil.CopyText(GoodsDetailActivity.this, tvGoodsTitle.getText().toString());
                    showToast("复制成功");
                }
                return true;
            }
        });
        if ("2".equals(commissionType)) {
            linCollect.setVisibility(View.VISIBLE);
        } else {
            linCollect.setVisibility(View.GONE);
        }

        //下载图片类
        mInstance = DownLoadImage.getInstance();
        mInstance.setContext(GoodsDetailActivity.this);

        mInstance.setOnDownLoadImage(new DownLoadImage.onDownLoadImage() {
            @Override
            public void progressMax(int value) {

            }

            @Override
            public void progressValue(int value, int contentLen) {
                int va = (value * 100) / contentLen;
                mProgress.setPercent(va);
                mProgressText.setText(mProgress.getPercent() + "%");
            }

            @Override
            public void progerssVisibility() {
                mProgressView.setVisibility(View.GONE);
                openImage.setVisibility(View.VISIBLE);
                complete.setText("完成");
            }

            @Override
            public void downLiadImage(int imageLength) {
                if (ImageList != null && ImageList.length > 0) {
                    showDialogView(ImageList.length);
                }
            }

            @Override
            public void downLoadValue(int value) {
                downLoadImageSum.setText(value + "");
            }
        });

        scrollView.setScrollViewListener(new ObservableScrollView.IScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {


            }

            @Override
            public void onScrollListener(int scrollY) {
                Log.e("onScrollListener", " " + scrollY);
//                if (scrollY >= 900) {
//                    boolean first_goodes = (boolean) SharedPreferencesHelper.get(GoodsDetailActivity.this, ConstantUtil.first_goodes,
//                            false);
//                    if (!first_goodes) {
//                        scrollView.scrollTo(0, 890);
//                        scrollView.smoothScrollTo(0, 890);
//                        View view = WindowUtils.ShowBrightness(GoodsDetailActivity.this, R.layout.item_course_goodes, 0);
//                        view.findViewById(R.id.goodes_ok).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                SharedPreferencesHelper.put(GoodsDetailActivity.this, ConstantUtil.first_goodes, true);
//                                WindowUtils.dismissBrightness(GoodsDetailActivity.this);
//                            }
//                        });
//                    }
//                }
            }
        });
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
        if (goodDetailEntity.getGoodDetail().getActive() != null) {
            if (goodDetailEntity.getGoodDetail().getActive().getIsActive() == 1) {
                try {
                    double_content.setText("限时佣金翻倍，翻倍后可得" + goodDetailEntity.getGoodDetail().getActive().getActiveCommission() + "元");
                    isdouble.setVisibility(View.VISIBLE);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    isdouble.setVisibility(View.GONE);
                }
            } else {
                isdouble.setVisibility(View.GONE);
            }
        } else {
            isdouble.setVisibility(View.GONE);
        }

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

        if (commissionType.equals(Constants.COMMISSION_TYPE_THREE)) {
            s_y.setText("收益" + CommonUtils.doubleToString(goodDetail.getCommision()) + "元");
            tv_yongjin_num.setText("收益" + CommonUtils.doubleToString(goodDetail.getCommision()) + "元");
            Profit = CommonUtils.doubleToString(goodDetail.getCommision()) + "";
        } else if (commissionType.equals(Constants.COMMISSION_TYPE_FOUR)) {
            double doubleExtra = getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0);
            String comm = CommonUtils.doubleToString(doubleExtra);
            s_y.setText("收益" + comm + "元");
            tv_yongjin_num.setText("收益" + comm + "元");
            Profit = comm + "";
        } else {
            s_y.setText("收益" + getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0) + "元");
            tv_yongjin_num.setText("收益" + getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0) + "元");
            Profit = getIntent().getDoubleExtra(Constants.INTENT_KEY_COMM, 0) + "";
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
                    AuntTao auntTao = new AuntTao();
                    auntTao.setContext(GoodsDetailActivity.this);
                    auntTao.AuntTabo();
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
            R.id.lin_buy_buy, R.id.ll_more_comm, R.id.iv_btn_download, R.id.shen_ji,R.id.isdouble})
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
                    if (goodDetail == null) {
                        return;
                    }
                    Intent intent = new Intent(this, ChoiceShareActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_DATA, goodDetail);
                    intent.putExtra("Profit", Profit);
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
                PermissonUtil.checkPermission(GoodsDetailActivity.this, new PermissionListener() {
                    @Override
                    public void havePermission() {
                        if (images != null && images.size() > 0) {
                            ImageList = new String[images.size()];
                            for (int i = 0; i < images.size(); i++) {
                                ImageList[i] = images.get(i);
                            }
                            if (ImageList != null && ImageList.length > 0) {
                                mInstance.DownImageLength(ImageList);
                            }
                        } else {
                            showToast("图片未加载完成");
                        }
                    }

                    @Override
                    public void requestPermissionFail() {
                        showToast("缺少必要权限");
                    }
                }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
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
            case R.id.isdouble:
                Bundle bundle = new Bundle();
                bundle.putString("Web_Url", "https://testapi.bbcoupon.cn/view/doubleRule/list");
                bundle.putString("Web_Tiele", "活动时间与规则");
                bundle.putString("Web_Type", ConstantUtil.DOUBLING_RULE);
                WebViewActivity.openWebViewActivity(GoodsDetailActivity.this,bundle);
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
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);
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

    private void showDialogView(int sume) {
        OpenGoodsDetail.lightoff(GoodsDetailActivity.this);
        View view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.dialog_goods, null);
        mMPopupWindow = new PopupWindow();
        mMPopupWindow.setContentView(view);
        mMPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mMPopupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置背景图片， 必须设置，不然动画没作用
        mMPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mMPopupWindow.setFocusable(false);
        // 设置点击popupwindow外屏幕其它地方消失
        mMPopupWindow.setOutsideTouchable(true);
        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mMPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView view1 = view.findViewById(R.id.sume_one);
        mProgressText = view.findViewById(R.id.ProgressText);
        downLoadImageSum = view.findViewById(R.id.sume_two);
        openImage = view.findViewById(R.id.xiang);
        complete = view.findViewById(R.id.wan);
        mProgressView = view.findViewById(R.id.ProgressView);
        mProgress = view.findViewById(R.id.Progress);

        mProgress.setPercent(0);
        view1.setText("共" + sume + "张  已下载 ");
        downLoadImageSum.setText("0");
        mProgress.setPercent(0);
        mProgressText.setText(0 + "%");

        mMPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                OpenGoodsDetail.lighton(GoodsDetailActivity.this);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMPopupWindow.dismiss();
            }
        });

        openImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbum();
                mMPopupWindow.dismiss();
            }
        });

        //开始下载图片
        mInstance.DownLoadImag(ImageList);
    }

    public void openAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

}
