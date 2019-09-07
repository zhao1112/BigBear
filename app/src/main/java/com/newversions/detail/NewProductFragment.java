package com.newversions.detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.NewOrderBean;
import com.newversions.NewOrderChildBean;
import com.newversions.detail.dialog.ISpecificationDialog;
import com.newversions.view.CommentImgView;
import com.newversions.view.ICustomDialog;
import com.newversions.view.MyWebView;
import com.newversions.view.NewShopView;
import com.newversions.view.NewVersionChoicenessView;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.activity.ConfirmActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.TrolleyActivity;
import com.yunqin.bearmall.ui.activity.VipCenterActivity;
import com.yunqin.bearmall.ui.dialog.CustomerServiceDialog;
import com.yunqin.bearmall.ui.dialog.PriceInstructionDialog;
import com.yunqin.bearmall.ui.dialog.ServiceInstructionDialog;
import com.yunqin.bearmall.util.IShareUtil;
import com.yunqin.bearmall.util.ScreenUtils;
import com.yunqin.bearmall.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;

/**
 * Create By Master
 * On 2019/1/10 15:02
 */
public class NewProductFragment extends BaseFragment implements HtmlFoundation {


    @BindView(R.id.web_view)
    MyWebView webView;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    private float mScreenWidth;

    @BindView(R.id.new_version_top1)
    LinearLayout mBarTop1;
    @BindView(R.id.new_version_top2)
    LinearLayout mBarTop2;


    @BindView(R.id.scroll_view_type_video_pause_1)
    View scroll_view_type_video_pause_1;

    @BindView(R.id.scroll_view_type_2)
    View scroll_view_type_2;
    @BindView(R.id.scroll_view_type_3)
    View scroll_view_type_3;
    @BindView(R.id.head_layout)
    View head_layout;


    @BindView(R.id.overlay_video_pic_layout)
    LinearLayout overlay_video_pic_layout;


    @BindView(R.id.product_detail_price)
    TextView product_detail_price;


    private String product_id = "";
    private String sku_id = "";

    private FragmentToActivityInter fragmentToActivityInter;
    private static final String PRODUCT_DETAIL = "/view/findProductDetailPage?product_id=";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentToActivityInter) {
            fragmentToActivityInter = (FragmentToActivityInter) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int layoutId() {
        return R.layout.new_version_product_fragment;
    }


    private boolean isLogin = false;

    @Override
    public void onResume() {
        super.onResume();
        if (isLogin) {
            init();
            isLogin = false;
        }


    }

    @Override
    public void init() {
        product_id = getArguments().getString("productId");


        sku_id = getArguments().getString("sku_id");
        mScreenWidth = (float) ScreenUtils.getScreenWidth(getActivity());//获取屏幕宽度，用来计算appbar透明度
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

//                Log.e("TAG-CP", "商品推荐距离顶部距离:" + (newVersionChoicenessView.getTop() - scrollY));

                float mAppBarAlpha = ((float) scrollY) / mScreenWidth;
                if (mAppBarAlpha >= 1) {
                    mAppBarAlpha = 1;
                }
                mBarTop2.setAlpha(mAppBarAlpha);
                mBarTop1.setAlpha(1 - mAppBarAlpha);


                if (newVersionChoicenessView.getTop() - scrollY < 500) {
                    changeTabBackground(4);
                    return;
                }


                if (scroll_view_type_3.getTop() - scrollY < 500) {
                    changeTabBackground(3);
                    return;
                }

                if (scroll_view_type_2.getTop() - scrollY < 500 && scroll_view_type_2.getTop() - scrollY > 150) {
                    changeTabBackground(2);
                } else if (scroll_view_type_2.getTop() - scrollY > 500) {
                    changeTabBackground(1);
                }


                if (scroll_view_type_video_pause_1.getTop() - scrollY < 100) {
                    Jzvd.goOnPlayOnPause();
                }


            }
        });
        initWebView();


        Map<String, String> map = new HashMap<>();
        map.put("product_id", product_id);
        map.put("sku_id", sku_id);

        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).getProductData(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                NewProductBean newProductBean = new Gson().fromJson(data, NewProductBean.class);
                initProduct(newProductBean);
            }

            @Override
            public void onNotNetWork() {
            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }

    @BindView(R.id.n_v_ttt_name)
    TextView n_v_ttt_name;
    @BindView(R.id.n_v_xiaoshou_)
    TextView n_v_xiaoshou_;
    @BindView(R.id.jaige_layout)
    LinearLayout jaige_layout;
    @BindView(R.id.dandu_jiage_layout)
    LinearLayout dandu_jiage_layout;
    @BindView(R.id.n_v_dangqianjia)
    TextView n_v_dangqianjia;
    @BindView(R.id.n_v_bear_dangqianjia)
    TextView n_v_bear_dangqianjia;
    @BindView(R.id.n_v_yuanjia)
    TextView n_v_yuanjia;
    @BindView(R.id.n_v_bear_yuanjia)
    TextView n_v_bear_yuanjia;
    @BindView(R.id.no_no_dangqianjia)
    TextView no_no_dangqianjia;
    @BindView(R.id.tangguo_shuoming)
    TextView tangguo_shuoming;

    @BindView(R.id.comment_container)
    LinearLayout comment_container;
    @BindView(R.id.comment_empty_layout)
    LinearLayout comment_empty_layout;
    @BindView(R.id.new_version_comment_count)
    TextView new_version_comment_count;

    @BindView(R.id.product_img_top)
    ViewPager productImgViewPager;
    @BindView(R.id.product_detail_bt_price)
    TextView product_detail_bt_price;

    @BindView(R.id.shi_pin_button)
    Button shi_pin_button;
    @BindView(R.id.tu_pian_button)
    Button tu_pian_button;


    private NewProductBean newProductBean;

    private void initProduct(NewProductBean newProductBean) {

        this.newProductBean = newProductBean;


        // 商品展示
        NewVersionProductImgViewPagerAdapter productImgViewPagerAdapter;

        if (newProductBean.getData().getProduct().isIsHasVideo()) {

            overlay_video_pic_layout.setVisibility(View.VISIBLE);

            productImgViewPagerAdapter = new NewVersionProductImgViewPagerAdapter(getActivity(),
                    newProductBean.getData().getProduct().isIsHasVideo(),
                    newProductBean.getData().getProduct().getProductVideo(),
                    newProductBean.getData().getProduct().getProductVideoImage().getSource(),
                    newProductBean.getData().getProduct().getProductImages());
        } else {

            overlay_video_pic_layout.setVisibility(View.GONE);

            productImgViewPagerAdapter = new NewVersionProductImgViewPagerAdapter(getActivity(), newProductBean.getData().getProduct().getProductImages());
        }
        productImgViewPager.setAdapter(productImgViewPagerAdapter);

        productImgViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // TODO 如果有视频
                if (newProductBean.getData().getProduct().isIsHasVideo()) {
                    if (position == 1) {
                        Jzvd.releaseAllVideos();
                    }
                    if (position == 0) {
                        shi_pin_button.setBackgroundColor(Color.parseColor("#23A064"));
                        tu_pian_button.setBackgroundColor(Color.parseColor("#2b000000"));
                    } else {
                        shi_pin_button.setBackgroundColor(Color.parseColor("#2b000000"));
                        tu_pian_button.setBackgroundColor(Color.parseColor("#23A064"));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // 价格名称展示
        instantmessageLists = newProductBean.getData().getInstantmessageList();

        NewProductBean.DataBean.ProductBean productBean = newProductBean.getData().getProduct();
        n_v_ttt_name.setText(productBean.getProductName());
        n_v_xiaoshou_.setText(String.format("已销%d笔", productBean.getSales()));

        product_detail_price.setText(String.format("¥%s", productBean.getPrice()));


        if (productBean.isIsSurportMsp()) {
            // 是否支持会员价

            product_detail_bt_price.setText(String.format("金熊价 ¥%s", productBean.getMembershipPrice()));


            if (productBean.getIsDiscount() == 1) {// 是折扣商品
                if (productBean.getModel() == 0) {
                    n_v_dangqianjia.setText(String.format("¥%s", productBean.getPrice()));
                    n_v_yuanjia.setVisibility(View.VISIBLE);
                    n_v_yuanjia.setText(String.format("¥%s", productBean.getSourcePrice()));
                    n_v_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                    n_v_bear_dangqianjia.setText(String.format("金熊价 ¥%s", productBean.getMembershipPrice()));
                    n_v_bear_yuanjia.setVisibility(View.GONE);

                } else if (productBean.getModel() == 1) {

                    n_v_dangqianjia.setText(String.format("¥%s", productBean.getPrice()));
                    n_v_yuanjia.setVisibility(View.GONE);

                    n_v_bear_dangqianjia.setText(String.format("金熊价 ¥%s", productBean.getMembershipPrice()));
                    n_v_bear_yuanjia.setVisibility(View.VISIBLE);
                    n_v_bear_yuanjia.setText(String.format("¥%s", productBean.getSourceMembershipPrice()));
                    n_v_bear_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                } else if (productBean.getModel() == 2) {

                    n_v_dangqianjia.setText(String.format("¥%s", productBean.getPrice()));
                    n_v_yuanjia.setVisibility(View.VISIBLE);
                    n_v_yuanjia.setText(String.format("¥%s", productBean.getSourcePrice()));
                    n_v_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                    n_v_bear_dangqianjia.setText(String.format("金熊价 ¥%s", productBean.getMembershipPrice()));
                    n_v_bear_yuanjia.setVisibility(View.VISIBLE);
                    n_v_bear_yuanjia.setText(String.format("¥%s", productBean.getSourceMembershipPrice()));
                    n_v_bear_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                }
            } else {

                n_v_dangqianjia.setText(String.format("¥%s", productBean.getPrice()));
                n_v_yuanjia.setVisibility(View.GONE);

                n_v_bear_dangqianjia.setText(String.format("金熊价 ¥%s", productBean.getMembershipPrice()));
                n_v_bear_yuanjia.setVisibility(View.GONE);
            }
        } else {
            if (productBean.getIsDiscount() == 1) {
                // 是折扣商品

                if (productBean.getModel() == 0) {
                    n_v_dangqianjia.setText(String.format("¥%s", productBean.getPrice()));
                    n_v_yuanjia.setVisibility(View.GONE);


                    product_detail_bt_price.setText(String.format("金熊价 ¥%s", productBean.getSourcePrice()));
                    product_detail_bt_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    product_detail_bt_buy.setClickable(false);

                    n_v_bear_dangqianjia.setText(String.format("金熊价 ¥%s", productBean.getSourcePrice()));
                    n_v_bear_dangqianjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    n_v_bear_yuanjia.setVisibility(View.GONE);

                }
            } else {

                product_detail_bt_price.setText(String.format("金熊价 ¥%s", productBean.getPrice()));
                product_detail_bt_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                product_detail_bt_buy.setClickable(false);


                jaige_layout.setVisibility(View.GONE);
                dandu_jiage_layout.setVisibility(View.VISIBLE);
                no_no_dangqianjia.setText(String.format("¥%s", productBean.getPrice()));
            }
        }

        // 糖果说明
        tangguo_shuoming.setText("返糖果数：最高可返实付价的" + newProductBean.getData().getBtInstruction().getV0() + "%");

        // 评论
        try {

            new_version_comment_count.setText(String.format("评论 (%d)", newProductBean.getData().getReviewTotalCount()));
            for (int i = 0; i < (newProductBean.getData().getReviewList().size() > 3 ? 3 : newProductBean.getData().getReviewList().size()); i++) {
                CommentImgView commentImgView = new CommentImgView(getActivity());
                commentImgView.setData(newProductBean.getData().getReviewList().get(i));
                comment_container.addView(commentImgView);

                comment_container.setVisibility(View.VISIBLE);
                comment_empty_layout.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            comment_container.setVisibility(View.GONE);
            comment_empty_layout.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }

        // TODO 是否收藏
        isFavorite = newProductBean.getData().getIsFavorite();
        changeCollectBackground(false);

        // TODO 商店信息
        if (newProductBean.getData().getStore() != null && newProductBean.getData().getStore().getHotProductList() != null
                && newProductBean.getData().getStore().getHotProductList().size() > 0) {
            NewShopView newShopView = new NewShopView(getActivity());
            newShopView.setData(newProductBean.getData().getStore());
            shop_container.addView(newShopView);
        }
        // TODO 推荐
        newVersionChoicenessView.setManager(new GridLayoutManager(getActivity(), 2));
        newVersionChoicenessView.init();
    }


    @BindView(R.id.product_detail_bt_buy)
    LinearLayout product_detail_bt_buy;


    @BindView(R.id.shop_container)
    LinearLayout shop_container;
    @BindView(R.id.choiceness_view)
    NewVersionChoicenessView newVersionChoicenessView;


    private void changeCollectBackground(boolean change) {
        if (change) {
            isFavorite = isFavorite == 0 ? 1 : 0;
        }
        if (isFavorite == 0) {
            collectTv.setText("收藏");
            productCollect.setImageResource(R.drawable.icon_like_normal);
        } else if (isFavorite == 1) {
            collectTv.setText("已收藏");
            productCollect.setImageResource(R.drawable.icon_like_selected);
        }
    }

    private int isFavorite;
    @BindView(R.id.product_collect)
    ImageView productCollect;
    @BindView(R.id.product_detail_collect_tv)
    TextView collectTv;

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("gbk");
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(new HtmlCallAndroid(this), "android");


        //解决WebView链接为https，内容图片为http，图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //todo 废物方法
//                ViewGroup.LayoutParams params = webView.getLayoutParams();
//                params.width = getResources().getDisplayMetrics().widthPixels;
//                params.height = nestedScrollView.getHeight();
//                webView.setLayoutParams(params);


//                LinearLayout.LayoutParams  params = (LinearLayout.LayoutParams) webView.getLayoutParams();
//                params.width = getScreenWidth();
//                params.height = getScreenHeight()-SizeUtil.dip2px(ScreenShotByH5Activity.this,150);
//                webView.setLayoutParams(params);


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(BuildConfig.BASE_URL + PRODUCT_DETAIL + product_id);
//                view.loadUrl("https://www.baidu.com");
                return true;
            }
        });


        String url = BuildConfig.BASE_URL + PRODUCT_DETAIL + product_id;
//        url = "https://www.baidu.com";
        webView.loadUrl(url);


//        ViewTreeObserver viewTreeObserver = webView.getViewTreeObserver();
//
//        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                int height = webView.getMeasuredHeight();
//                Toast.makeText(getActivity(), "height:" + height, Toast.LENGTH_SHORT).show();
//                if (height != 0) {
//                    webView.getViewTreeObserver().removeOnPreDrawListener(this);
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
//                    webView.setLayoutParams(layoutParams);
//                }
//                return false;
//            }
//        });


    }


    private ServiceInstructionDialog serviceInstructionDialog;
    private PriceInstructionDialog priceInstructionDialog;


    @OnClick({R.id.switch_ping_lun, R.id.new_version_top1_back, R.id.new_version_top2_back,
            R.id.baozhang_shuoming, R.id.jiage_shuoming,
            R.id.new_version_top1_gouwuche, R.id.new_version_top1_share,
            R.id.new_version_top2_gouwuche, R.id.new_version_top2_share,
            R.id.new_version_top_shangpin, R.id.new_version_top_pinglun,
            R.id.new_version_top_xiangqing, R.id.product_detail_customer,
            R.id.product_layout_collect, R.id.product_detail_direct_buy,
            R.id.product_detail_add_cart,
            R.id.product_detail_bt_buy, R.id.shi_pin_button, R.id.tu_pian_button,
            R.id.new_version_top_tuijian
    })
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.shi_pin_button:
                productImgViewPager.setCurrentItem(0);
                break;
            case R.id.tu_pian_button:
                productImgViewPager.setCurrentItem(1);
                break;
            case R.id.switch_ping_lun:
                fragmentToActivityInter.chooseFragment(false);
                break;
            case R.id.new_version_top1_back:
                getActivity().finish();
                break;
            case R.id.new_version_top2_back:
                getActivity().finish();
                break;
            case R.id.baozhang_shuoming:
                if (serviceInstructionDialog == null) {
                    serviceInstructionDialog = new ServiceInstructionDialog(getActivity());
                }
                serviceInstructionDialog.show();
                break;
            case R.id.jiage_shuoming:
                if (priceInstructionDialog == null) {
                    priceInstructionDialog = new PriceInstructionDialog(getActivity());
                }
                priceInstructionDialog.show();
                break;
            case R.id.new_version_top2_gouwuche:
                startGpuwuche();
                break;
            case R.id.new_version_top1_gouwuche:
                startGpuwuche();
                break;
            case R.id.new_version_top1_share:
                shareGoods();
                break;
            case R.id.new_version_top2_share:
                shareGoods();
                break;
            case R.id.new_version_top_shangpin:
                changeTabBackground(1);
                nestedScrollView.scrollTo(0, 0);
                break;
            case R.id.new_version_top_pinglun:
                changeTabBackground(2);
                nestedScrollView.scrollTo(0, scroll_view_type_2.getTop() - head_layout.getHeight());
                break;
            case R.id.new_version_top_xiangqing:
                changeTabBackground(3);
                nestedScrollView.scrollTo(0, scroll_view_type_3.getTop() - head_layout.getHeight());
                break;

            case R.id.new_version_top_tuijian:
                changeTabBackground(4);
                nestedScrollView.scrollTo(0, newVersionChoicenessView.getTop() - head_layout.getHeight());
                break;
            case R.id.product_detail_customer:
                // TODO 客服
                showKeFuDialog();
                break;
            case R.id.product_layout_collect:
                clickShouCang();
                break;
            case R.id.product_detail_add_cart:
                // 加入购物车
                addShopCar();
                break;
            case R.id.product_detail_direct_buy:
                // 直接购买

                if (BearMallAplication.getInstance().getUser() != null) {

                    if (BearMallAplication.getInstance().getUser().getData().getMember().isMember()) {
                        goBuy();
                    } else {
                        if (newProductBean.getData().getProduct().getIsLimitMs()) {
                            // 是否是会员专属
                            joinMember(R.id.tip, "该商品是会员专属商品，您还不是会员，无法以金熊价购买商品。马上开通会员享受更多权益！", "我再想想");
                        } else {
                            goBuy();
                        }
                    }
                } else {
                    goToLogin();
                }


                break;
            case R.id.product_detail_bt_buy:
                // 会员价购买
                if (BearMallAplication.getInstance().getUser() != null) {
                    if (BearMallAplication.getInstance().getUser().getData().getMember().isMember()) {
                        gohyBuy();
                    } else {
                        joinMember(R.id.tip, "您还不是会员，无法以金熊价购买商品。马上开通会员享受更多权益！", "直接购买");
                    }
                } else {
                    goToLogin();
                }

                break;
        }
    }


    @BindView(R.id.new_version_top_shangpin_tv)
    TextView new_version_top_shangpin_tv;
    @BindView(R.id.new_version_top_shangpin_line)
    View new_version_top_shangpin_line;

    @BindView(R.id.new_version_top_pinglun_tv)
    TextView new_version_top_pinglun_tv;
    @BindView(R.id.new_version_top_pinglun_line)
    View new_version_top_pinglun_line;

    @BindView(R.id.new_version_top_xiangqing_tv)
    TextView new_version_top_xiangqing_tv;
    @BindView(R.id.new_version_top_xiangqing_line)
    View new_version_top_xiangqing_line;

    @BindView(R.id.new_version_top_tuijian_tv)
    TextView new_version_top_tuijian_tv;
    @BindView(R.id.new_version_top_tuijian_line)
    View new_version_top_tuijian_line;

    private void changeTabBackground(int position) {
        hideTabBackground();
        if (position == 1) {
            new_version_top_shangpin_line.setVisibility(View.VISIBLE);
            new_version_top_shangpin_tv.setTextColor(Color.parseColor("#23A064"));
        } else if (position == 2) {
            new_version_top_pinglun_line.setVisibility(View.VISIBLE);
            new_version_top_pinglun_tv.setTextColor(Color.parseColor("#23A064"));
        } else if (position == 3) {
            new_version_top_xiangqing_line.setVisibility(View.VISIBLE);
            new_version_top_xiangqing_tv.setTextColor(Color.parseColor("#23A064"));
        } else if (position == 4) {
            new_version_top_tuijian_line.setVisibility(View.VISIBLE);
            new_version_top_tuijian_tv.setTextColor(Color.parseColor("#23A064"));
        }
    }

    private void hideTabBackground() {
        new_version_top_shangpin_line.setVisibility(View.INVISIBLE);
        new_version_top_pinglun_line.setVisibility(View.INVISIBLE);
        new_version_top_xiangqing_line.setVisibility(View.INVISIBLE);
        new_version_top_tuijian_line.setVisibility(View.INVISIBLE);

        new_version_top_shangpin_tv.setTextColor(Color.parseColor("#666666"));
        new_version_top_pinglun_tv.setTextColor(Color.parseColor("#666666"));
        new_version_top_xiangqing_tv.setTextColor(Color.parseColor("#666666"));
        new_version_top_tuijian_tv.setTextColor(Color.parseColor("#666666"));
    }


    private void startGpuwuche() {
        Intent intent = new Intent(getActivity(), TrolleyActivity.class);
        intent.putExtra("isBack", true);
        startActivity(intent);
    }

    private ShareBean shareBean;

    private void shareGoods() {
        if (shareBean == null) {
            showLoading();
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("source_id", product_id);
            mHashMap.put("type", "1");

            RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getShareParams(mHashMap), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        hiddenLoadingView();
                        shareBean = new Gson().fromJson(data, ShareBean.class);
                        IShareUtil.Share(getActivity(), shareBean.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNotNetWork() {
                    hiddenLoadingView();
                }

                @Override
                public void onFail(Throwable e) {
                    hiddenLoadingView();
                }
            });
        } else {
            IShareUtil.Share(getActivity(), shareBean.getData());
        }
    }

    private CustomerServiceDialog customerServiceDialog;
    private List<NewProductBean.DataBean.InstantmessageListBean> instantmessageLists;//im弹框数据

    private void showKeFuDialog() {
        String phone = "";
        String qq = "";
        if (customerServiceDialog == null) {
            for (int i = 0; i < instantmessageLists.size(); i++) {
                if (instantmessageLists.get(i).getType() == 1) {
                    phone = instantmessageLists.get(i).getAccount();
                } else if (instantmessageLists.get(i).getType() == 0) {
                    qq = instantmessageLists.get(i).getAccount();
                }

            }
            customerServiceDialog = new CustomerServiceDialog(getActivity(), phone, qq);
        }
        customerServiceDialog.show();
    }

    private void clickShouCang() {
        if (BearMallAplication.getInstance().getUser() != null) {
            showLoading();
            Map<String, String> map = new HashMap();
            map.put("product_id", String.valueOf(product_id));
            map.put("isFavorite", isFavorite == 0 ? "1" : "0");

            RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).setFavorite(map), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) {
                    Log.e("setFavorite", data);
                    try {
                        hiddenLoadingView();
                        JSONObject jsonObject = new JSONObject(data);
                        int code = jsonObject.getInt("code");
                        if (code == 1) {
                            changeCollectBackground(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNotNetWork() {
                    hiddenLoadingView();
                }

                @Override
                public void onFail(Throwable e) {
                    hiddenLoadingView();
                }
            });
        } else {
            goToLogin();
        }
    }


    private void addShopCar() {
        if (BearMallAplication.getInstance().getUser() != null) {
            ISpecificationDialog iSpecificationDialog = new ISpecificationDialog(getActivity(), false, new ISpecificationDialog.OnDialogClickListener() {
                @Override
                public void onDialogClick(int sku_id, int quantity) {

                    Map<String, String> mHashMap = new HashMap<>();

                    mHashMap.put("product_id", product_id);
                    mHashMap.put("sku_id", sku_id + "");
                    mHashMap.put("quantity", quantity + "");

                    showLoading();
                    RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).joinCart(mHashMap), new RetrofitApi.IResponseListener() {
                        @Override
                        public void onSuccess(String data) throws JSONException {

                            ToastUtils.showToast(getActivity(), "加入购物车成功");

                            hiddenLoadingView();

                        }

                        @Override
                        public void onNotNetWork() {
                            hiddenLoadingView();
                        }

                        @Override
                        public void onFail(Throwable e) {
                            hiddenLoadingView();
                        }
                    });


                }
            });
            iSpecificationDialog.show();
            iSpecificationDialog.setData(newProductBean.getData().getProduct().getProductImages().get(0).getSource(),
                    newProductBean.getData().getProduct().getSpecificationItems(), newProductBean.getData().getSkuList());

        } else {
            goToLogin();
        }
    }


    private void goBuy() {
        ISpecificationDialog iSpecificationDialog = new ISpecificationDialog(getActivity(), false, new ISpecificationDialog.OnDialogClickListener() {
            @Override
            public void onDialogClick(int sku_id, int quantity) {
                // TODO 直接购买
                String name = newProductBean.getData().getProduct().getProductName();
                String imgUrl = newProductBean.getData().getProduct().getProductImages().get(0).getSource();
                int productId = newProductBean.getData().getProduct().getProduct_id();
                String price = "";
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 0; i < newProductBean.getData().getSkuList().size(); i++) {
                    if (newProductBean.getData().getSkuList().get(i).getSku_id() == sku_id) {
                        price = newProductBean.getData().getSkuList().get(i).getPrice();
                        for (int j = 0; j < newProductBean.getData().getSkuList().get(i).getSpecificationValues().size(); j++) {
                            stringBuffer.append(newProductBean.getData().getSkuList().get(i).getSpecificationValues().get(j).getValue() + " ");

                        }
                        break;
                    }
                }
                String guiGe = stringBuffer.toString();
                NewOrderChildBean newOrderChildBean = new NewOrderChildBean(name, imgUrl, price, guiGe, String.valueOf(quantity), productId, sku_id, 0);

                String storeName = newProductBean.getData().getStore().getStore_name();
                String storeImg = newProductBean.getData().getStore().getLogo();

                List<NewOrderChildBean> list = new ArrayList();
                list.add(newOrderChildBean);
                NewOrderBean newOrderBean = new NewOrderBean(storeName, storeImg, list);
                List<NewOrderBean> newOrderBeans = new ArrayList<>();
                newOrderBeans.add(newOrderBean);

                Intent intent = new Intent(getActivity(), ConfirmActivity.class);
                intent.putExtra(ConfirmActivity.INTENT_DATA, (ArrayList) newOrderBeans);
                intent.putExtra("type_order", "0");
                startActivity(intent);

            }
        });
        iSpecificationDialog.show();
        iSpecificationDialog.setData(newProductBean.getData().getProduct().getProductImages().get(0).getSource(),
                newProductBean.getData().getProduct().getSpecificationItems(), newProductBean.getData().getSkuList());


    }


    private void gohyBuy() {


        ISpecificationDialog iSpecificationDialog = new ISpecificationDialog(getActivity(), true, new ISpecificationDialog.OnDialogClickListener() {
            @Override
            public void onDialogClick(int sku_id, int quantity) {
                String name = newProductBean.getData().getProduct().getProductName();
                String imgUrl = newProductBean.getData().getProduct().getProductImages().get(0).getSource();
                int productId = newProductBean.getData().getProduct().getProduct_id();
                String price = "";
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 0; i < newProductBean.getData().getSkuList().size(); i++) {
                    if (newProductBean.getData().getSkuList().get(i).getSku_id() == sku_id) {
                        price = newProductBean.getData().getSkuList().get(i).getMembershipPrice();
                        for (int j = 0; j < newProductBean.getData().getSkuList().get(i).getSpecificationValues().size(); j++) {
                            stringBuffer.append(newProductBean.getData().getSkuList().get(i).getSpecificationValues().get(j).getValue() + " ");

                        }
                        break;
                    }
                }
                String guiGe = stringBuffer.toString();
                NewOrderChildBean newOrderChildBean = new NewOrderChildBean(name, imgUrl, price, guiGe, String.valueOf(quantity), productId, sku_id, 1);

                String storeName = newProductBean.getData().getStore().getStore_name();
                String storeImg = newProductBean.getData().getStore().getLogo();

                List<NewOrderChildBean> list = new ArrayList();
                list.add(newOrderChildBean);
                NewOrderBean newOrderBean = new NewOrderBean(storeName, storeImg, list);
                List<NewOrderBean> newOrderBeans = new ArrayList<>();
                newOrderBeans.add(newOrderBean);

                Intent intent = new Intent(getActivity(), ConfirmActivity.class);
                intent.putExtra(ConfirmActivity.INTENT_DATA, (ArrayList) newOrderBeans);
                intent.putExtra("type_order", "0");
                startActivity(intent);

            }
        });
        iSpecificationDialog.show();
        iSpecificationDialog.setData(newProductBean.getData().getProduct().getProductImages().get(0).getSource(),
                newProductBean.getData().getProduct().getSpecificationItems(), newProductBean.getData().getSkuList());


    }


    private void joinMember(int id, String title, String button) {
        new ICustomDialog.Builder(getActivity())
                // 设置布局
                .setLayoutResId(R.layout.join_member_layout)
                // 点击空白是否消失
                .setCanceledOnTouchOutside(false)
                // 点击返回键是否消失
                .setCancelable(false)
                // 设置Dialog的绝对位置
                .setDialogPosition(Gravity.CENTER)
                // 设置自定义动画
                .setAnimationResId(0)
                // 设置监听ID
                .setListenedItems(new int[]{R.id.join_member_no, R.id.join_member_ok})
                .setCustomTextIds(new int[]{id, R.id.join_member_no})
                .setCustomTextContents(new String[]{title, button})

                // 设置回掉
                .setOnDialogItemClickListener(new ICustomDialog.OnDialogItemClickListener() {
                    @Override
                    public void onDialogItemClick(ICustomDialog thisDialog, View clickView) {
                        if (clickView.getId() == R.id.join_member_ok) {
                            startActivity(new Intent(getActivity(), VipCenterActivity.class));
                        } else {
                            if (newProductBean.getData().getProduct().getIsLimitMs()) {
                                // 是否是会员专属
                                if (button.equals("我再想想")) {
                                } else {
                                    joinMember(R.id.tip, "该商品是会员专属商品，您还不是会员，无法以金熊价购买商品。马上开通会员享受更多权益！", "我再想想");
                                }
                            } else {
                                goBuy();
                            }
                        }
                        thisDialog.dismiss();
                    }
                })
                .build().show();
    }


    public void goToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        isLogin = true;
    }

    @Override
    public void onResultHeight(int height) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ViewGroup.LayoutParams params = webView.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels;
                params.height = dp2px(height) + 230;


                webView.setLayoutParams(params);
            }
        });
    }

    public static int px2dp(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
