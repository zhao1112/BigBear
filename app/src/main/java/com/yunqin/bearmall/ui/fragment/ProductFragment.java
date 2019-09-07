package com.yunqin.bearmall.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BrandHotProductRecyclerViewAdapter;
import com.yunqin.bearmall.adapter.ProductCommentAdapter;
import com.yunqin.bearmall.adapter.ProductImgViewPagerAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.eventbus.ProductMessageEvent;
import com.yunqin.bearmall.inter.ProductInstructionCallBack;
import com.yunqin.bearmall.inter.ScrollViewForActivityListener;
import com.yunqin.bearmall.inter.ScrollViewListener;
import com.yunqin.bearmall.ui.activity.ProductDetailActivity;
import com.yunqin.bearmall.ui.activity.ShopActivity;
import com.yunqin.bearmall.util.RecyclerViewItemDecration;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.DeficitScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class ProductFragment extends BaseFragment implements ScrollViewListener {


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

    @BindViews({R.id.product_name, R.id.product_price, R.id.product_bt_price, R.id.product_sales})//商品名称，法币价，发
            List<TextView> mProductTextView;

    @BindView(R.id.product_bt_instruction)
    TextView productBtInstructionTextView;//糖果说明

    @BindView(R.id.comment_count)
    TextView commentCount;//评论个数

    @BindView(R.id.comment_listview)
    com.yunqin.bearmall.widget.ScrollListView mScrollListView;//评论列表

    @BindView(R.id.brand_icon)
    CircleImageView brand_icon;//店铺icon

    @BindViews({R.id.brand_name, R.id.brand_product_number, R.id.brand_sales_number})//店铺信息
            List<TextView> brandTextView;

    @BindView(R.id.brand_hot_product)//店铺热销
            RecyclerView brandHotProductRecyclerView;

    @BindView(R.id.custom_recommend_view)//推荐
            CustomRecommendView customRecommendView;

    @BindView(R.id.comment_empty_layout)//没有评论
            LinearLayout comment_empty_layout;

    @BindView(R.id.tip_no_comment)
    TextView tip_no_comment;

    @BindView(R.id.product_bt_info_text)
    TextView product_bt_info_text;

    @BindView(R.id.zero_rule_instruction)
    LinearLayout zero_rule_instruction;

    @OnClick({R.id.product_service_instruction, R.id.product_price_instruction, R.id.check_all_comment, R.id.brand_shopping})
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

            case R.id.brand_shopping:
                Intent intent = new Intent(getActivity(), ShopActivity.class);
                intent.putExtra("store_id", String.valueOf(store.getStore_id()));
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private ScrollViewForActivityListener mScrollViewForActivityListener;
    private ProductInstructionCallBack productInstructionCallBack;

    //商品对象
    private String priceInstruction;//价格说明内容
    private ProductDetail.Product product;
    private ProductDetail.BtInstruction btInstruction;//糖果说明内容
    private ProductDetail.Store store;
    private List<ProductDetail.ReviewList> reviewList;
    //    private List<ProductDetail.GuaranteeInstruction> guaranteeInstruction;//保障说明内容
    private int isFavorite;
    private List<ProductDetail.SkuList> skuList;
    private long sku_id;
    private ProductCommentAdapter productCommentAdapter;
    private BrandHotProductRecyclerViewAdapter brandHotProductRecyclerViewAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        if (activity instanceof ProductDetailActivity) {
            mScrollViewForActivityListener = (ScrollViewForActivityListener) activity;
            productInstructionCallBack = (ProductInstructionCallBack) activity;
        } else {
            throw new IllegalArgumentException("activity must implements ScrollViewForActivityListener");
        }
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_product;
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


        //todo 在商品详情页面  显示 糖果说明
        zero_rule_instruction.setVisibility(View.GONE);
        product_bt_info_text.setVisibility(View.VISIBLE);
        productBtInstructionTextView.setVisibility(View.VISIBLE);
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

    //接收ProductActivityPresenter请求接口成功后返回的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ProductMessageEvent messageEvent) {
        Log.e("ProductMessageEvent", messageEvent.getMessage());
        String serverJsonData = messageEvent.getMessage();

        parseJsonData(serverJsonData);

    }

    private void parseJsonData(String serverJsonData) {
        if (serverJsonData != null && !serverJsonData.isEmpty()) {
            ProductDetail productDetail = new Gson().fromJson(serverJsonData, ProductDetail.class);
            priceInstruction = productDetail.getData().getPriceInstruction();
            product = productDetail.getData().getProduct();
            btInstruction = productDetail.getData().getBtInstruction();
            store = productDetail.getData().getStore();
            reviewList = productDetail.getData().getReviewList();
//            guaranteeInstruction = productDetail.getData().getGuaranteeInstruction();
            isFavorite = productDetail.getData().getIsFavorite();
            skuList = productDetail.getData().getSkuList();
            sku_id = productDetail.getData().getSku_id();

            if (product != null) {
                setProductData();//设置商品数据
            }
            if (btInstruction != null) {
                setBtInstructionData();//设置糖果数据
            }
            if (reviewList != null) {
                setReviewListData(productDetail.getData().getReviewTotalCount());//设置评论数据
            }
            if (store != null) {
                setStoreData();//设置店铺数据
            }
        }
    }

    private void setStoreData() {
        Glide.with(getActivity()).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(store.getLogo()).into(brand_icon);
        brandTextView.get(0).setText(store.getStore_name());
        brandTextView.get(1).setText(store.getProductNumber() + "");
        brandTextView.get(2).setText(store.getProductSales() + "");

        brandHotProductRecyclerView.setHasFixedSize(true);
        brandHotProductRecyclerView.setNestedScrollingEnabled(false);

        brandHotProductRecyclerView.addItemDecoration(new RecyclerViewItemDecration(16));//设置item间距

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        brandHotProductRecyclerView.setLayoutManager(linearLayoutManager);

        if (brandHotProductRecyclerViewAdapter == null) {
            brandHotProductRecyclerViewAdapter = new BrandHotProductRecyclerViewAdapter(getActivity(), store.getHotProductList());
            brandHotProductRecyclerView.setAdapter(brandHotProductRecyclerViewAdapter);

            brandHotProductRecyclerViewAdapter.setOnItemClickListener(new BrandHotProductRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void OnItemClickObject(int position, ProductDetail.HotProductList hotProductList) {

                    Intent intent = new Intent(getActivity(), NewProductDetailActivity.class);
                    intent.putExtra("productId", "" + hotProductList.getProduct_id());
                    intent.putExtra("sku_id", "");
                    startActivity(intent);
                }
            });
        }
    }

    //设置评论数据
    private void setReviewListData(int total) {
        if (reviewList.size() == 0) {
            comment_empty_layout.setVisibility(View.VISIBLE);
            mScrollListView.setVisibility(View.GONE);
            tip_no_comment.setText("暂无评论");
        } else {
            mScrollListView.setVisibility(View.VISIBLE);
            comment_empty_layout.setVisibility(View.GONE);

            commentCount.setText("评论（" + total + "）");
            if (productCommentAdapter == null) {
                productCommentAdapter = new ProductCommentAdapter(getActivity(), reviewList);
                mScrollListView.setAdapter(productCommentAdapter);
            } else {
                productCommentAdapter.notifyDataSetChanged();
            }
        }
    }

    //设置商品数据
    private void setProductData() {
        ProductImgViewPagerAdapter productImgViewPagerAdapter = new ProductImgViewPagerAdapter(getActivity(), product.getProductImages());
        productImgViewPager.setAdapter(productImgViewPagerAdapter);
        indicatorTextView.setText("1/" + product.getProductImages().size());
        productImgViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorTextView.setText(position + 1 + "/" + product.getProductImages().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mProductTextView.get(0).setText(product.getProductName());
        mProductTextView.get(1).setText("¥" + product.getPrice());
        mProductTextView.get(2).setText("¥" + product.getPartPrice() + "+BC" + product.getPartBtAmount());
        mProductTextView.get(3).setText("已销" + product.getSales() + "" + "笔");

    }

    //糖果说明
    private void setBtInstructionData() {
        productBtInstructionTextView.setText("返糖果数：最高可返实付价的" + btInstruction.getV0() + "%");
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
