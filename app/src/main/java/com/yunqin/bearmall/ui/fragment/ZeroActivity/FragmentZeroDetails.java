package com.yunqin.bearmall.ui.fragment.ZeroActivity;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductCommentAdapter;
import com.yunqin.bearmall.adapter.ZeroDetailsImgViewPagerAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.bean.ZeroActivityBean;
import com.yunqin.bearmall.eventbus.CountDownFinishEvent;
import com.yunqin.bearmall.eventbus.ZeroActivityMessageEvent;
import com.yunqin.bearmall.inter.ProductInstructionCallBack;
import com.yunqin.bearmall.inter.ScrollViewForActivityListener;
import com.yunqin.bearmall.inter.ScrollViewListener;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.DeficitScrollView;
import com.yunqin.bearmall.widget.ScrollListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * @author AYWang
 * @create 2018/8/7
 * @Describe
 */
public class FragmentZeroDetails extends BaseFragment implements ScrollViewListener {

    @BindView(R.id.fillStatusBarView)
    View fillStatusBarView;
    @BindView(R.id.product_img_top)
    ViewPager productImgTop;
    @BindView(R.id.product_img_selector_tv)
    TextView productImgSelectorTv;
    @BindView(R.id.join_total_people_number)
    TextView joinTotalPeopleNumber;
    @BindView(R.id.countdown_view)
    CountdownView countdownView;
    @BindView(R.id.goods_surplus_number)
    TextView goodsSurplusNumber;
    @BindView(R.id.goods_name)
    TextView goodsName;
    @BindView(R.id.jion_progress)
    ProgressBar jionProgress;
    @BindView(R.id.jion_need_numner)
    TextView jionNeedNumner;
    @BindView(R.id.product_bt_instruction)
    TextView productBtInstruction;
    @BindView(R.id.product_service_instruction)
    LinearLayout productServiceInstruction;
    @BindView(R.id.zero_rule_instruction)
    LinearLayout zeroRuleInstruction;
    @BindView(R.id.product_price_instruction)
    LinearLayout productPriceInstruction;
    @BindView(R.id.comment_count)
    TextView commentCount;
    @BindView(R.id.check_all_comment)
    LinearLayout checkAllComment;
    @BindView(R.id.comment_listview)
    ScrollListView commentListview;
    @BindView(R.id.custom_recommend_view)
    CustomRecommendView customRecommendView;
    @BindView(R.id.scrollView)
    DeficitScrollView deficitScrollView;
    @BindView(R.id.get_bc_number)
    TextView get_bc_number;
    @BindView(R.id.comment_empty_layout)
    LinearLayout comment_empty_layout;
    @BindView(R.id.product_fragment_head)
    RelativeLayout headLayout;
    @BindView(R.id.tip_no_comment)
    TextView tip_no_comment;

    private ScrollViewForActivityListener mScrollViewForActivityListener;
    private ProductInstructionCallBack productInstructionCallBack;
    private ZeroActivityBean.DataBean.GroupPurchasingItemBean itemBean;
    private List<ProductDetail.ReviewList> reviewList;
    private ProductCommentAdapter productCommentAdapter;

    @OnClick({R.id.product_service_instruction, R.id.product_price_instruction, R.id.check_all_comment, R.id.zero_rule_instruction})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.product_service_instruction://服务说明（全场包邮，7天包换，假一赔十）
                if (productInstructionCallBack != null) {
                    productInstructionCallBack.showInstructionDialog(0);
                }
                break;

            case R.id.product_price_instruction://价格说明
                if (productInstructionCallBack != null) {
                    productInstructionCallBack.showInstructionDialog(1);
                }
                break;

            case R.id.check_all_comment:
                if (productInstructionCallBack != null) {
                    productInstructionCallBack.showInstructionDialog(2);
                }
                break;

            case R.id.zero_rule_instruction:
                if (productInstructionCallBack != null) {
                    productInstructionCallBack.showInstructionDialog(3);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        if (activity instanceof ZeroMoneyDetailsActivity) {
            mScrollViewForActivityListener = (ScrollViewForActivityListener) activity;
            productInstructionCallBack = (ProductInstructionCallBack) activity;
        } else {
            throw new IllegalArgumentException("activity must implements ScrollViewForActivityListener");
        }
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_zero_details;
    }

    @Override
    public void init() {
        deficitScrollView.setScrollViewListener(this);
        headLayout.setFocusable(true);
        headLayout.setFocusableInTouchMode(true);
        headLayout.requestFocus();

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
            mScrollViewForActivityListener.onScrollChanged(scrollView, x, y, oldx, oldy);//把frament中scrollview的滚动监听的数据返回给activity
            // ，来处理头部区域的透明度
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


    //接收ProductActivityPresenter请求接口成功后返回的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ZeroActivityMessageEvent messageEvent) {
        String serverJsonData = messageEvent.getMessage();
        parseJsonData(serverJsonData);
    }


    private void parseJsonData(String serverJsonData) {
        if (serverJsonData != null && !serverJsonData.isEmpty()) {
            ZeroActivityBean zeroActivityBean = new Gson().fromJson(serverJsonData, ZeroActivityBean.class);

            itemBean = zeroActivityBean.getData().getGroupPurchasingItem();
            reviewList = zeroActivityBean.getData().getReviewList();
            if (itemBean != null) {
                setProductData();//设置商品数据
                try {
                    countdownView.start(itemBean.getRestTime());
                    countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                        @Override
                        public void onEnd(CountdownView cv) {
                            EventBus.getDefault().post(new CountDownFinishEvent(0));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (reviewList != null && reviewList.size() > 0) {
                comment_empty_layout.setVisibility(View.GONE);
                commentListview.setVisibility(View.VISIBLE);
                setReviewListData(zeroActivityBean.getData().getReviewTotalCount());//设置评论数据
            } else {
                tip_no_comment.setText("暂无拼团晒单");
                comment_empty_layout.setVisibility(View.VISIBLE);
                commentListview.setVisibility(View.GONE);
            }
        }
    }


    //设置商品数据
    private void setProductData() {
        ZeroDetailsImgViewPagerAdapter zeroDetailsImgViewPagerAdapter = new ZeroDetailsImgViewPagerAdapter(getActivity(),
                itemBean.getProductImages());
        productImgTop.setAdapter(zeroDetailsImgViewPagerAdapter);
        productImgSelectorTv.setText("1/" + itemBean.getProductImages().size());
        productImgTop.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                productImgSelectorTv.setText(position + 1 + "/" + itemBean.getProductImages().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        joinTotalPeopleNumber.setText(String.format("累计兑换%s份", itemBean.getTotalCount()));
        goodsName.setText(itemBean.getProductName());
        jionProgress.setMax(itemBean.getGroupNumber());
        jionProgress.setProgress(itemBean.getPersonalCount());

        jionNeedNumner.setText("返糖果数：最高可返实付价的" + itemBean.getBtInstruction().getV0() + "%");

        //TODO[0元兑浏览商品详情页]
        ConstantScUtil.commodityDetail(itemBean.getProduct_id() + "", itemBean.getProductName(), "", itemBean.getTotalCount() + "",
                itemBean.getProductName(), "0");
    }

    //设置评论数据
    private void setReviewListData(int total) {
        commentCount.setText("评论（" + total + "）");

        if (productCommentAdapter == null) {
            productCommentAdapter = new ProductCommentAdapter(getActivity(), reviewList);
            commentListview.setAdapter(productCommentAdapter);
        } else {
            productCommentAdapter.notifyDataSetChanged();
        }


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
