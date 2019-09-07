package com.yunqin.bearmall.ui.activity;
/**
 * 设置Activity中fragmnet沉浸式状态栏
 * 参考链接 https://blog.csdn.net/qq1377399077/article/details/78487677
 * <p>
 * 1.普通的Activity我们采用github上的库放在BaseActivity来实现.
 * 2.Activity的fragment我们对其每个fragment进行处理：
 * - Activity全屏显示
 * - 设置Activity的主题为：Theme.AppCompat.Light（如果没设置为light设置透明状态栏时会显示黑色）
 * - 设置状态栏透明
 * 3.这时会出现两种情况，一种是顶部为图片（或布局背景为图片）的fragment实现了图二的效果，达到了我们的要求，另一种是标题栏和状态栏混合在一起，文字混乱，我们开始处理这种情况：
 * - 在当前的fragment中的标题栏上部添加一块view，使之和状态栏的高度一致便解决了混合在一起显示的乱像.
 */

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductViewPagerAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.CartItemCount;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.eventbus.CollectionGoodsEvent;
import com.yunqin.bearmall.eventbus.TrolleyCountEvent;
import com.yunqin.bearmall.inter.ProductInstructionCallBack;
import com.yunqin.bearmall.inter.ScrollViewForActivityListener;
import com.yunqin.bearmall.ui.activity.contract.ProductActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.ProductActivityPresenter;
import com.yunqin.bearmall.ui.dialog.CustomerServiceDialog;
import com.yunqin.bearmall.ui.dialog.PriceInstructionDialog;
import com.yunqin.bearmall.ui.dialog.ServiceInstructionDialog;
import com.yunqin.bearmall.ui.dialog.SpecificationItemsDialog;
import com.yunqin.bearmall.util.FileUtils;
import com.yunqin.bearmall.util.IShareUtil;
import com.yunqin.bearmall.util.ScreenUtils;
import com.yunqin.bearmall.util.ToastUtils;
import com.yunqin.bearmall.widget.DeficitScrollView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class ProductDetailActivity extends BaseActivity implements ProductActivityContract.UI, ScrollViewForActivityListener, ProductInstructionCallBack {
    public static final String PRODUCT_ID = "product_id";
    public static final String SKU_ID = "sku_id";
    public static final int ADD_CART = 0;
    public static final int BUY_PRICE = 1;
    public static final int BUY_BT_PRICE = 2;

    private int collection_index = -1;//todo 王志伟添加的代码

    @BindView(R.id.xtablelayout)
    XTabLayout xtablelayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.transparent_status_head_layout)
    RelativeLayout mHeadLayout;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindViews({R.id.transparent_status_head_back, R.id.transparent_status_head_add_cart, R.id.transparent_status_share})
    List<RelativeLayout> mHeadLayoutImg;

    @BindViews({R.id.show_status_back_img, R.id.show_status_go_cart_img, R.id.show_status_share_img})
    List<ImageView> mAppBarLayoutImg;

    @BindView(R.id.product_collect)
    ImageView productCollect;

    @BindViews({R.id.product_detail_price, R.id.product_detail_bt_price})
    List<TextView> mProductTextView;

    @BindView(R.id.product_detail_collect_tv)
    TextView collectTv;


    @OnClick({R.id.transparent_status_head_back, R.id.transparent_status_head_add_cart, R.id.transparent_status_share,
            R.id.show_status_back_img, R.id.show_status_go_cart_img, R.id.show_status_share_img,
            R.id.product_detail_customer, R.id.product_layout_collect,
            R.id.product_detail_add_cart, R.id.product_detail_direct_buy, R.id.product_detail_bt_buy,})
    public void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.show_status_back_img:
                finish();
                break;

            case R.id.show_status_go_cart_img://购物车


                intent = new Intent(ProductDetailActivity.this, TrolleyActivity.class);
                intent.putExtra("isBack", true);
                startActivity(intent);

//                intent = new Intent(ProductDetailActivity.this,HomeActivity.class);
//                intent.putExtra("switchFragment","TrolleyFragment");
//                startActivity(intent);
                break;

            case R.id.show_status_share_img:
                // TODO 商品分享 18888888888
                if (shareBean == null) {
                    showLoading();
                    Constans.params.clear();
                    Constans.params.put("source_id", product.getProduct_id() + "");
                    Constans.params.put("type", 1 + "");
                    mPresenter.getShareData(ProductDetailActivity.this, Constans.params);
                } else {
                    IShareUtil.Share(this, shareBean.getData());
                }
                break;

            case R.id.product_detail_customer://客服
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
                    customerServiceDialog = new CustomerServiceDialog(ProductDetailActivity.this, phone, qq);
                } else {
                    customerServiceDialog.show();
                }
                break;

            case R.id.product_layout_collect://收藏
                if (BearMallAplication.getInstance().getUser() != null) {
                    Map map = new HashMap();
                    map.clear();
                    map.put("product_id", String.valueOf(product_id));
                    if (isFavorite == 0) {
                        isFavorite = 1;
                        map.put("isFavorite", String.valueOf(isFavorite));

                    } else if (isFavorite == 1) {
                        isFavorite = 0;
                        map.put("isFavorite", String.valueOf(isFavorite));
                    }
                    hiddenLoadingView();
                    mPresenter.setFavorite(ProductDetailActivity.this, map);
                } else {
                    goToLogin();
                }

                break;

            case R.id.product_detail_add_cart://加入购物车
                if (BearMallAplication.getInstance().getUser() != null) {
                    if (productDetail != null) {
                        if (dialogCart == null) {
                            dialogCart = new SpecificationItemsDialog(ProductDetailActivity.this, mPresenter, productDetail.getData().getProduct(), store, product_id, sku_id, skuList);
                            dialogCart.showDialog(ADD_CART);
                        } else {
                            dialogCart.showDialog(ADD_CART);
                        }
                    }
                } else {
                    goToLogin();
                }

                break;

            case R.id.product_detail_direct_buy://法币购买
                if (BearMallAplication.getInstance().getUser() != null) {
                    if (productDetail != null) {
                        if (dialogCart == null) {
                            dialogCart = new SpecificationItemsDialog(ProductDetailActivity.this, mPresenter, productDetail.getData().getProduct(), store, product_id, sku_id, skuList);
                            dialogCart.showDialog(BUY_PRICE);
                        } else {
                            dialogCart.showDialog(BUY_PRICE);
                        }
                    }
                } else {
                    goToLogin();
                }
                break;

            case R.id.product_detail_bt_buy://大熊价购买
                if (BearMallAplication.getInstance().getUser() != null) {
                    if (productDetail != null) {
                        if (dialogCart == null) {
                            dialogCart = new SpecificationItemsDialog(ProductDetailActivity.this, mPresenter, productDetail.getData().getProduct(), store, product_id, sku_id, skuList);
                            dialogCart.showDialog(BUY_BT_PRICE);
                        } else {
                            dialogCart.showDialog(BUY_BT_PRICE);
                        }
                    }
                } else {
                    goToLogin();
                }

                break;
        }
    }

    private float mAppBarAlptha = 0f;
    private float mScreenWidth;
    private int isFavorite;
    private ProductActivityPresenter mPresenter;
    private ProductViewPagerAdapter adapter;
    private ProductDetail productDetail;
    private ProductDetail.Product product;
    private List<ProductDetail.SkuList> skuList;
    private long product_id, sku_id;
    private ProductDetail.Store store;
    private List<ProductDetail.SpecificationItems> specificationItems;
    private SpecificationItemsDialog dialogCart;
    private ServiceInstructionDialog serviceInstructionDialog;
    private PriceInstructionDialog priceInstructionDialog;
    private List<ProductDetail.InstantmessageList> instantmessageLists;//im弹框数据
    private CustomerServiceDialog customerServiceDialog;
    private ShareBean shareBean = null;

    @Override
    public int layoutId() {
        return R.layout.activity_product_detail;
    }

    public void goToLogin() {
        Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void init() {
        viewpager.setOffscreenPageLimit(2);

        immerseStatusBar();//设置viewPager中fragment沉浸式状态栏
        changStatusIconCollor(true);//状态栏icon为深色
        mScreenWidth = (float) ScreenUtils.getScreenWidth(ProductDetailActivity.this);//获取屏幕宽度，用来计算appbar透明度
        viewpagerPageChange();//处理viewpager页面选中事件

        Intent intent = getIntent();
        product_id = intent.getLongExtra(PRODUCT_ID, 0l);
        sku_id = intent.getLongExtra(SKU_ID, 0l);

        collection_index = intent.getIntExtra("collection_index", -1);//todo 王志伟
        Map map = new HashMap();
        map.put("product_id", String.valueOf(product_id));
        if (sku_id > 0) {
            map.put("sku_id", String.valueOf(sku_id));
        }

        showLoading();
        mPresenter = new ProductActivityPresenter(this);
        mPresenter.getProductData(ProductDetailActivity.this, map);
    }

    @Override
    public void attachTitle(List<Fragment> fragmentList, List<String> titleList) {
        for (int i = 0; i < titleList.size(); i++) {
            xtablelayout.addTab(xtablelayout.newTab().setText(titleList.get(i)));
        }
        adapter = new ProductViewPagerAdapter(ProductDetailActivity.this, getSupportFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        //初始化显示第一个页面
        xtablelayout.setupWithViewPager(viewpager);
        xtablelayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void getProductData(String data) {
        hiddenLoadingView();

        //此处的作用是，为规格弹框保留最初始的数据，在SpecificationItemsDialog中有详细介绍
        boolean writeFileReslut = FileUtils.writeFileFromString(ProductDetailActivity.this.getCacheDir().getAbsolutePath() + File.separator + "productInfo.txt", data, false);//复写写文件

        productDetail = new Gson().fromJson(data, ProductDetail.class);
        isFavorite = productDetail.getData().getIsFavorite();
        skuList = productDetail.getData().getSkuList();
        specificationItems = productDetail.getData().getProduct().getSpecificationItems();
        instantmessageLists = productDetail.getData().getInstantmessageList();
        store = productDetail.getData().getStore();

        //设置是否收藏icon
        if (isFavorite == 0) {
            collectTv.setText("收藏");
            productCollect.setImageResource(R.drawable.icon_like_normal);
        } else if (isFavorite == 1) {
            collectTv.setText("已收藏");
            productCollect.setImageResource(R.drawable.icon_like_selected);
        }

        //设置价格
        product = productDetail.getData().getProduct();
        mProductTextView.get(0).setText("¥" + product.getPrice());
        mProductTextView.get(1).setText("¥" + product.getPartPrice() + "+BC" + product.getPartBtAmount());
    }

    @Override
    public void setFavorite(String data) {
        hiddenLoadingView();
        if (isFavorite == 1) {
            productCollect.setImageResource(R.drawable.icon_like_selected);
            collectTv.setText("已收藏");
            Toast.makeText(ProductDetailActivity.this, "收藏成功", Toast.LENGTH_LONG).show();
        } else {
            productCollect.setImageResource(R.drawable.icon_like_normal);
            collectTv.setText("收藏");
            Toast.makeText(ProductDetailActivity.this, "取消成功", Toast.LENGTH_LONG).show();
        }

    }

    //todo 王志伟
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFavorite == 0) {
            if (collection_index != -1) {
                EventBus.getDefault().post(new CollectionGoodsEvent(collection_index));
            }
        }
    }

    @Override
    public void joinCart(String data) {
        ToastUtils.showToast(ProductDetailActivity.this, "加入购物车成功");

        Constans.params.clear();
        mPresenter.getCartItemCount(ProductDetailActivity.this, Constans.params);
    }

    @Override
    public void getCartItemCount(String data) {
        CartItemCount cartItemCount = new Gson().fromJson(data, CartItemCount.class);

        EventBus.getDefault().post(new TrolleyCountEvent(cartItemCount.getData().getItemCount()));//向activity发送请求成功的数
    }

    @Override
    public void share(ShareBean shareBean) {
        hiddenLoadingView();
        this.shareBean = shareBean;
        IShareUtil.Share(this, shareBean.getData());
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
    }

    @Override
    public void onScrollChanged(DeficitScrollView scrollView, int x, int y, int oldx, int oldy) {

        mAppBarAlptha = ((float) y) / mScreenWidth;
        if (mAppBarAlptha >= 1) {
            mAppBarAlptha = 1;
        } else {
            mAppBarLayout.setAlpha(mAppBarAlptha);
            mHeadLayout.setAlpha(1 - mAppBarAlptha);
        }

    }

    private void viewpagerPageChange() {
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1 || position == 2) {
                    mAppBarLayout.setAlpha(1);
                    mHeadLayout.setAlpha(0);
                } else if (position == 0) {
                    mAppBarLayout.setAlpha(mAppBarAlptha);
                    mHeadLayout.setAlpha(1 - mAppBarAlptha);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void showInstructionDialog(int flag) {
        if (flag == 0) {
            if (serviceInstructionDialog == null) {
                serviceInstructionDialog = new ServiceInstructionDialog(ProductDetailActivity.this);
            } else {
                serviceInstructionDialog.show();
            }
        } else if (flag == 1) {
            if (priceInstructionDialog == null) {
                priceInstructionDialog = new PriceInstructionDialog(ProductDetailActivity.this);
            } else {
                priceInstructionDialog.show();
            }
        } else if (flag == 2) {
            viewpager.setCurrentItem(xtablelayout.getTabCount() - 1);//切换到评论fragment
        }
    }
}
