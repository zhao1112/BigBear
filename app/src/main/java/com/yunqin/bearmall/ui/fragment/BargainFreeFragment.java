package com.yunqin.bearmall.ui.fragment;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductCommentAdapter;
import com.yunqin.bearmall.adapter.ProductImgViewPagerAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BargainDetail;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.eventbus.BargainFreeEvent;
import com.yunqin.bearmall.inter.BargainInstructionCallBack;
import com.yunqin.bearmall.inter.ScrollViewForActivityListener;
import com.yunqin.bearmall.inter.ScrollViewListener;
import com.yunqin.bearmall.ui.activity.BargainFreeDetailActivity;
import com.yunqin.bearmall.ui.dialog.BargainStrategyDialog;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.DeficitScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class BargainFreeFragment extends BaseFragment implements ScrollViewListener {


    @BindView(R.id.scrollView)
    DeficitScrollView deficitScrollView;

    @BindView(R.id.product_fragment_head)
    RelativeLayout headLayout;

    @BindView(R.id.fillStatusBarView)
    View fillStatusBarView;

    @BindView(R.id.product_img_top)
    ViewPager productImgViewPager;

    @BindView(R.id.product_img_selector_tv)
    TextView indicatorTextView;

    @BindView(R.id.product_name)//商品名称
    TextView mProductNameTextView;

    @BindView(R.id.comment_count)
    TextView commentCount;//评论个数

    @BindViews({R.id.bargain_free_residue_count,R.id.bargain_free_residue_time})
    List<TextView> bargainResidue;//多少人已免费拿，距离活动结束时间

    @BindView(R.id.comment_empty_layout)
    LinearLayout commentEmptyLayout;

    @BindView(R.id.comment_listview)
    com.yunqin.bearmall.widget.ScrollListView mScrollListView;//评论列表

    @BindView(R.id.custom_recommend_view)//推荐
    CustomRecommendView customRecommendView;



    @OnClick({R.id.bargain_free_service_instruction, R.id.bargain_free_rule, R.id.bargain_free_price_instruction, R.id.check_all_comment})
    public void OnClick(View view) {
        Log.e("OnClick", "OnClick");
        switch (view.getId()) {
            case R.id.bargain_free_rule://砍价免费拿详细规则，跳转页面
                if(bargainStrategyDialog == null ){
                    bargainStrategyDialog = new BargainStrategyDialog(getActivity());
                }else{
                    bargainStrategyDialog.show();
                }
                break;

            case R.id.bargain_free_service_instruction://服务说明
                if (productInstructionCallBack != null) {
                    productInstructionCallBack.showInstructionDialog(0);
                }
                break;

            case R.id.bargain_free_price_instruction://价格说明
                if (productInstructionCallBack != null) {
                    productInstructionCallBack.showInstructionDialog(1);
                }
                break;

            case R.id.check_all_comment:
                if (productInstructionCallBack != null) {
                    productInstructionCallBack.showInstructionDialog(2);
                }
                break;

            default:
                break;
        }
    }

    private ScrollViewForActivityListener mScrollViewForActivityListener;
    private BargainInstructionCallBack productInstructionCallBack;

    //商品对象
    private ProductCommentAdapter productCommentAdapter;
    private BargainDetail bargainDetail;
    private ProductDetail.Store store;
    private List<ProductDetail.ReviewList> reviewList;
    private int reviewCount;
    private BargainDetail.BargainProduct bargainProduct;
    private BargainStrategyDialog bargainStrategyDialog;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        if (activity instanceof BargainFreeDetailActivity) {
            mScrollViewForActivityListener = (ScrollViewForActivityListener) activity;
            productInstructionCallBack = (BargainInstructionCallBack) activity;
        } else {
            throw new IllegalArgumentException("activity must implements ScrollViewForActivityListener");
        }
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_bargain_free;
    }

    @Override
    public void init() {
        deficitScrollView.setScrollViewListener(this);
        headLayout.setFocusable(true);
        headLayout.setFocusableInTouchMode(true);
        headLayout.requestFocus();

        commentCount.setText("砍价评论（0）");

        customRecommendView.setTvBottom("推荐商品");
        customRecommendView.setDiviervisibility(View.VISIBLE);
        customRecommendView.setTopTextViewLeft();
        customRecommendView.setTopTextViewBgColor(getActivity().getResources().getColor(R.color.white));
        customRecommendView.setTopTextViewHeight(getActivity().getResources().getDimension(R.dimen.ds94));
        customRecommendView.hideTopLayout();

        customRecommendView.setManager(new GridLayoutManager(getActivity(), 2));
        customRecommendView.start(getActivity());
    }

    @Override
    public void onScrollChanged(DeficitScrollView scrollView, int x, int y, int oldx, int oldy) {
        headLayout.scrollTo(x, -y / 2);
        if (mScrollViewForActivityListener != null) {
            mScrollViewForActivityListener.onScrollChanged(scrollView, x, y, oldx, oldy);//把frament中scrollview的滚动监听的数据返回给activity，来处理头部区域的透明度
        }

    }

    @Override
    public void onResume() {
        //设置viewPager中fragment沉浸式状态栏，和activity中的代码配合使用，并且activity和fragment的layout的跟布局不能使用fitsSystemWindows属性
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) fillStatusBarView.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = getStatusBar();
        fillStatusBarView.setLayoutParams(lp);
        super.onResume();
    }

    //接收BargainFreeDetailActivityPresenter请求接口成功后返回的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(BargainFreeEvent messageEvent) {
        Log.e("ProductMessageEvent", messageEvent.getMessage());
        String serverJsonData = messageEvent.getMessage();
        parseJsonData(serverJsonData);

    }

    private void parseJsonData(String serverJsonData) {
        if (serverJsonData != null && !serverJsonData.isEmpty()) {
            bargainDetail = new Gson().fromJson(serverJsonData, BargainDetail.class);
            store = bargainDetail.getData().getStore();
            reviewList = bargainDetail.getData().getReviewList();
            reviewCount = bargainDetail.getData().getReviewTotalCount();
            bargainProduct = bargainDetail.getData().getBargainProduct();

            setProductData();
            setReviewListData();
            setBargainResidue();

        }
    }



    private void setBargainResidue() {
        //todo，自定义view处理倒计时
        bargainResidue.get(0).setText(bargainProduct.getFinishedNumber()+"人已随意拿");
    }

    //设置评论数据
    private void setReviewListData() {
        if (reviewList.size() > 0) {
            commentEmptyLayout.setVisibility(View.GONE);
            mScrollListView.setVisibility(View.VISIBLE);

            commentCount.setText("砍价评论（" + reviewCount + "）");
            if (productCommentAdapter == null) {
                productCommentAdapter = new ProductCommentAdapter(getActivity(), reviewList);
                mScrollListView.setAdapter(productCommentAdapter);
            } else {
                productCommentAdapter.notifyDataSetChanged();
            }
        }else{
            commentEmptyLayout.setVisibility(View.VISIBLE);
            mScrollListView.setVisibility(View.GONE);
        }
    }

    //设置商品数据
    private void setProductData() {
        ProductImgViewPagerAdapter productImgViewPagerAdapter = new ProductImgViewPagerAdapter(getActivity(), bargainProduct.getProductImages());
        productImgViewPager.setAdapter(productImgViewPagerAdapter);
        indicatorTextView.setText("1/" + bargainProduct.getProductImages().size());
        productImgViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorTextView.setText(position + 1 + "/" + bargainProduct.getProductImages().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mProductNameTextView.setText(bargainProduct.getProductName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mScrollViewForActivityListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
