package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.newversions.NewOrderBean;
import com.newversions.NewOrderChildBean;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductViewPagerAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.BargainDetail;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.inter.BargainInstructionCallBack;
import com.yunqin.bearmall.inter.ScrollViewForActivityListener;
import com.yunqin.bearmall.ui.activity.contract.BargainFreeDetailContract;
import com.yunqin.bearmall.ui.activity.presenter.BargainFreeDetailPresenter;
import com.yunqin.bearmall.ui.dialog.AffirmAddressDialog;
import com.yunqin.bearmall.ui.dialog.BargainAddressDialog;
import com.yunqin.bearmall.ui.dialog.KeepBargainDialog;
import com.yunqin.bearmall.ui.dialog.PriceInstructionDialog;
import com.yunqin.bearmall.ui.dialog.ServiceInstructionDialog;
import com.yunqin.bearmall.ui.dialog.SpecificationItemsProductBargainDialog;
import com.yunqin.bearmall.util.FileUtils;
import com.yunqin.bearmall.util.ScreenUtils;
import com.yunqin.bearmall.util.ShareUtil;
import com.yunqin.bearmall.util.TimeUtils;
import com.yunqin.bearmall.widget.DeficitScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

import static com.yunqin.bearmall.ui.fragment.TrolleyFragment.ORDER_TYPE;

public class BargainFreeDetailActivity extends BaseActivity implements BargainFreeDetailContract.UI, ScrollViewForActivityListener,
        BargainInstructionCallBack, SpecificationItemsProductBargainDialog.OnBargainSpecificationItemsListener, BargainAddressDialog.OnBargainFreePartListener {
    public static final String BARGAIN_PRODUCT_ID = "bargain_product_id";
    public static final String BARGAIN_IS_ONGOING = "bargain_is_ongoing";

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

//    //收藏
//    @BindView(R.id.bargain_free_detail_collect_layout)
//    LinearLayout bargainFreeDetailCollectLayout;

    //收藏图标
    @BindView(R.id.bargain_free_detail_collect_img)
    ImageView bargainFreeDetailCollectImg;

    //收藏
    @BindView(R.id.bargain_free_detail_collect_tv)
    TextView bargainFreeDetailCollectTv;

    @BindViews({R.id.bargain_free_detail_original_price, R.id.bargain_free_detail_current_price})
    List<TextView> mBargainFreeTextView;

    //当前价购买
    @BindView(R.id.bargain_detail_current_layout)
    LinearLayout currentLayout;

    //当前价购买剩余时间
    @BindView(R.id.bargain_detail_current_time_tv)
    TextView currentTime;

    //当前价购买价格
    @BindView(R.id.bargain_free_detail_current_price)
    TextView currentPrice;

    //免费砍价拿
    @BindView(R.id.bargain_detail_start)
    TextView startBargain;

    @OnClick({R.id.transparent_status_head_back, R.id.show_status_back_img,
            R.id.transparent_status_share, R.id.show_status_share_img,
            R.id.bargain_free_detail_collect_layout, R.id.bargain_free_detail_original_layout,
            R.id.bargain_free_detail_current_layout
    })
    public void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.show_status_back_img:
                finish();
                break;

            case R.id.show_status_share_img:
                if (shareBean == null) {
                    showLoading();
                    Constans.params.clear();
                    Constans.params.put("source_id", bargainProduct.getBargainProduct_id() + "");
                    Constans.params.put("type", 4 + "");
                    mPresenter.getShareData(BargainFreeDetailActivity.this, Constans.params);
                } else {
                    ShareUtil.Share(this, shareBean.getData(), false);
                }
                break;

            case R.id.bargain_free_detail_original_layout://原价购买


                if (bargainDetail.getData().isMember()) {

                    if (bargainProduct.getIsOngoing() == 1) {//用户参加砍价活动后，原价直接购买之前选中的sku_id;
                        addDataToOrderDeatil(false);
                    } else {
                        showSpecificationItemsDialog(SpecificationItemsProductBargainDialog.ORIGINAL_BUY);
                    }
                } else {
                    if (bargainProduct.isLimitMs()) {
                        joinMember();
                    } else {
                        if (bargainProduct.getIsOngoing() == 1) {//用户参加砍价活动后，原价直接购买之前选中的sku_id;
                            addDataToOrderDeatil(false);
                        } else {
                            showSpecificationItemsDialog(SpecificationItemsProductBargainDialog.ORIGINAL_BUY);
                        }
                    }
                }


                break;

            case R.id.bargain_free_detail_current_layout:

                if (bargainProduct.getRestCount() == 0) {
                    Toast.makeText(this, "今日已售罄，明天再来", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (bargainProduct.getIsOngoing() == 1) {//用户参加砍价活动后，当前价购买之前选中的sku_id;------------//当前价购买
//                    addDataToOrderDeatil(false);
                    if (keepBargainDialog == null) {
                        keepBargainDialog = new KeepBargainDialog(BargainFreeDetailActivity.this, null);
                        keepBargainDialog.showDialog();
                    } else {
                        keepBargainDialog.showDialog();
                    }
                } else {
                    if (BearMallAplication.getInstance().getUser() != null) {
                        showSpecificationItemsDialog(SpecificationItemsProductBargainDialog.BARGAIN_BUY);//发起砍价
                    } else {
                        intent = new Intent(BargainFreeDetailActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                break;

            case R.id.bargain_free_detail_collect_layout://收藏
                if (BearMallAplication.getInstance().getUser() != null) {
                    Map map = new HashMap();
                    map.clear();
                    map.put("product_id", String.valueOf(bargainProduct.getProduct_id()));
                    if (isFavorite == 0) {
                        isFavorite = 1;
                        map.put("isFavorite", String.valueOf(isFavorite));

                    } else if (isFavorite == 1) {
                        isFavorite = 0;
                        map.put("isFavorite", String.valueOf(isFavorite));
                    }
                    mPresenter.setBargainFavorite(BargainFreeDetailActivity.this, map);

                } else {
                    goToLogin();
                }

                break;
        }
    }

    private float mAppBarAlptha = 0f;
    private float mScreenWidth;
    private BargainFreeDetailPresenter mPresenter;
    private ProductViewPagerAdapter adapter;

    private ServiceInstructionDialog serviceInstructionDialog;
    private PriceInstructionDialog priceInstructionDialog;

    private BargainDetail bargainDetail;
    private ProductDetail.Store mStore;
    private int isFavorite;
    private BargainDetail.BargainProduct bargainProduct;

    private long bargain_product_id;
    private long sku_id;
    //    private boolean isOngoing;//是否可以砍价
    private BargainAddressDialog bargainAddressDialog;
    private SpecificationItemsProductBargainDialog specificationItemsProductBargainDialog;
    private long expireDate;
    private AddressListBean.DataBean address;
    private KeepBargainDialog keepBargainDialog;
    private ShareBean shareBean = null;

    @Override
    public int layoutId() {
        return R.layout.activity_bargain_free_detail;
    }

    @Override
    public void init() {
        immerseStatusBar();//设置viewPager中fragment沉浸式状态栏
        changStatusIconCollor(true);//状态栏icon为深色

        //因为和详情页用的相同的头部布局，所以需要隐藏加入购物车按钮
        mHeadLayoutImg.get(1).setVisibility(View.GONE);
        mAppBarLayoutImg.get(1).setVisibility(View.GONE);

        bargain_product_id = getIntent().getLongExtra(BARGAIN_PRODUCT_ID, 0l);
        Log.e("bargain_product_id", bargain_product_id + "");
//        isOngoing = getIntent().getBooleanExtra(BARGAIN_IS_ONGOING, false);
        viewpager.setOffscreenPageLimit(2);

        mScreenWidth = (float) ScreenUtils.getScreenWidth(BargainFreeDetailActivity.this);//获取屏幕宽度，用来计算appbar透明度
        viewpagerPageChange();//处理viewpager页面选中事件

        showLoading();
        mPresenter = new BargainFreeDetailPresenter(BargainFreeDetailActivity.this);
        Constans.params.clear();
        Constans.params.put("bargainProduct_id", bargain_product_id + "");
        if (bargain_product_id > 0) {
            mPresenter.getBargainProductDetail(BargainFreeDetailActivity.this, Constans.params);
        }
    }

    @Override
    public void attachTitle(List<Fragment> fragmentList, List<String> titleList) {
        for (int i = 0; i < titleList.size(); i++) {
            xtablelayout.addTab(xtablelayout.newTab().setText(titleList.get(i)));
        }
        adapter = new ProductViewPagerAdapter(BargainFreeDetailActivity.this, getSupportFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        //初始化显示第一个页面
        xtablelayout.setupWithViewPager(viewpager);
        xtablelayout.setTabsFromPagerAdapter(adapter);
    }


    @Override
    public void getBargainProductDetail(String data) {
        hiddenLoadingView();

        boolean writeFileReslut = FileUtils.writeFileFromString(BargainFreeDetailActivity.this.getCacheDir().getAbsolutePath() + File.separator + "productBargainDetailInfo.txt", data, false);//复写写文件
        if (!writeFileReslut) {
            return;
        }
        bargainDetail = new Gson().fromJson(data, BargainDetail.class);
        mStore = bargainDetail.getData().getStore();
//        reviewList = bargainDetail.getData().getReviewList();
        isFavorite = bargainDetail.getData().getIsFavorite();
//        reviewCount = bargainDetail.getData().getReviewCount();
        bargainProduct = bargainDetail.getData().getBargainProduct();

        //设置是否收藏icon
        if (isFavorite == 0) {
            bargainFreeDetailCollectImg.setImageResource(R.drawable.icon_like_normal);
            bargainFreeDetailCollectTv.setText("收藏");
        } else if (isFavorite == 1) {
            bargainFreeDetailCollectImg.setImageResource(R.drawable.icon_like_selected);
            bargainFreeDetailCollectTv.setText("已收藏");
        }

        //设置商品原来的价格
//        mBargainFreeTextView.get(0).setText("¥"+bargainProduct.getPartPrice()+"+BC"+bargainProduct.getPartBtAmount());
        mBargainFreeTextView.get(0).setText("¥" + bargainProduct.getPrice());
        //设置当前价格/显示砍价免费拿按钮
        if (bargainProduct.getIsOngoing() == 1) {//用户参加砍价活动
            sku_id = bargainProduct.getSku_id();
            currentLayout.setVisibility(View.VISIBLE);
            startBargain.setVisibility(View.GONE);
            expireDate = bargainProduct.getExpireDate();
            currentTime.setText(TimeUtils.generateTime(expireDate));
            mBargainFreeTextView.get(1).setText("当前价格¥" + bargainProduct.getCurrentPartPrice());
            countDow();
        } else {
            currentLayout.setVisibility(View.GONE);
            startBargain.setVisibility(View.VISIBLE);
        }

    }

    public void countDow() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        expireDate = expireDate - 1000;
                        currentTime.setText(TimeUtils.generateTime(expireDate));
                    }
                });
            }
        }, 1000, 1000);
    }


    @Override
    public void setBargainFavorite(String data) {
        hiddenLoadingView();
        if (isFavorite == 1) {
            bargainFreeDetailCollectImg.setImageResource(R.drawable.icon_like_selected);
            bargainFreeDetailCollectTv.setText("已收藏");
            Toast.makeText(BargainFreeDetailActivity.this, "收藏成功", Toast.LENGTH_LONG).show();
        } else {
            bargainFreeDetailCollectImg.setImageResource(R.drawable.icon_like_normal);
            bargainFreeDetailCollectTv.setText("收藏");
            Toast.makeText(BargainFreeDetailActivity.this, "取消成功", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPartBargain(String data) {
        hiddenLoadingView();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                String jsonData = jsonObject.getString("data");
                JSONObject jsonObjectData = new JSONObject(jsonData);
                long bargainRecord_id = jsonObjectData.getLong("bargainRecord_id");
                Intent intent = new Intent(BargainFreeDetailActivity.this, BargainFreeShareActivity.class);
                intent.putExtra(BargainFreeShareActivity.BARGAINRECORD_ID, bargainRecord_id);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String addressData;

    @Override
    public void setReceiverList(String data) {

        addressData = data;
        AddressListBean bean = new Gson().fromJson(data, AddressListBean.class);
        if (bean.getData().size() == 0) {//没有地址则直接发起砍价
            Constans.params.clear();
            Constans.params.put("bargainProduct_id", bargainProduct.getBargainProduct_id() + "");
            Constans.params.put("sku_id", sku_id + "");
            mPresenter.partBargain(BargainFreeDetailActivity.this, Constans.params);
        } else {
            showAddressDialog();
        }

//        hiddenLoadingView();
//        if(bargainAddressDialog != null){
//            bargainAddressDialog.setData(data,2);
//        }
    }

    @Override
    public void addBargainOrders(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                Intent intent = new Intent(BargainFreeDetailActivity.this, MineOrderActivity.class);
                startActivity(intent);
                finish();
//                String jsonData = jsonObject.getString("data");
//                JSONObject jsonObjectData = new JSONObject(jsonData);
//                String btAmount = jsonObjectData.getString("btAmount");
//                String amount = jsonObjectData.getString("amount");
//                String outTradeNo = jsonObjectData.getString("outTradeNo");
//                long ordersId = jsonObjectData.getInt("ordersId");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void share(ShareBean shareBean) {
        hiddenLoadingView();
        this.shareBean = shareBean;
        ShareUtil.Share(this, shareBean.getData(), false);
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();

    }

    public void goToLogin() {
        Intent intent = new Intent(BargainFreeDetailActivity.this, LoginActivity.class);
        startActivity(intent);
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
                serviceInstructionDialog = new ServiceInstructionDialog(BargainFreeDetailActivity.this);
            } else {
                serviceInstructionDialog.show();
            }
        } else if (flag == 1) {
            if (priceInstructionDialog == null) {
                priceInstructionDialog = new PriceInstructionDialog(BargainFreeDetailActivity.this);
            } else {
                priceInstructionDialog.show();
            }
        } else if (flag == 2) {
            viewpager.setCurrentItem(xtablelayout.getTabCount() - 1);//切换到评论fragment
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 100 && data != null) {
            mPresenter.getReceiverList(BargainFreeDetailActivity.this);
        }
    }

//    public void showAddressDialog(){
//        if(bargainAddressDialog == null){
//            bargainAddressDialog = new BargainAddressDialog(BargainFreeDetailActivity.this);
//            bargainAddressDialog.setOnBargainFreePartListener(this);
//            bargainAddressDialog.showDialog();
//            mPresenter.getReceiverList(BargainFreeDetailActivity.this);
//        }else{
//            bargainAddressDialog.showDialog();
//            mPresenter.getReceiverList(BargainFreeDetailActivity.this);
//        }
//    }

    public void showAddressDialog() {
        if (bargainAddressDialog == null) {
            bargainAddressDialog = new BargainAddressDialog(BargainFreeDetailActivity.this);
            bargainAddressDialog.setOnBargainFreePartListener(this);
            bargainAddressDialog.setData(addressData, 2);
            bargainAddressDialog.showDialog();
        } else {
            bargainAddressDialog.setData(addressData, 2);
            bargainAddressDialog.showDialog();
        }
    }

    public void showSpecificationItemsDialog(int type) {//弹出选择框
        if (specificationItemsProductBargainDialog == null) {
            specificationItemsProductBargainDialog = new SpecificationItemsProductBargainDialog(BargainFreeDetailActivity.this, bargainProduct, mStore, bargainProduct.getProduct_id(), 0, bargainProduct.getSkuList());
            specificationItemsProductBargainDialog.setOnBargainSpecificationItemsListener(this);
            specificationItemsProductBargainDialog.showDialog(type);
        } else {
            specificationItemsProductBargainDialog.showDialog(type);
        }
    }

    //弹出地址框
    @Override
    public void onBargainSpecificationItems(long sku_id) {
        this.sku_id = sku_id;
        Log.e("onBargainSpecification", sku_id + "");
        mPresenter.getReceiverList(BargainFreeDetailActivity.this);
    }


    private void joinMember() {
        new ICustomDialog.Builder(this)
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
                // 设置回掉
                .setOnDialogItemClickListener((thisDialog, clickView) -> {
                    if (clickView.getId() == R.id.join_member_ok) {
                        startActivity(new Intent(this, VipCenterActivity.class));
                    }
                    thisDialog.dismiss();
                })
                .build().show();
    }

    private void goBuy() {
        String name = bargainProduct.getProductName();
        String imgUrl = bargainProduct.getProductImages().get(0).getSource();
        String price = String.valueOf(bargainProduct.getPrice());
        int SkuId = (int) bargainProduct.getSku_id();
        int productId = (int) bargainProduct.getProduct_id();

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < bargainProduct.getSkuList().size(); i++) {
            if (bargainProduct.getSkuList().get(i).getSku_id() == bargainProduct.getSku_id()) {
                for (int j = 0; j < bargainProduct.getSkuList().get(i).getSpecificationValues().size(); j++) {
                    stringBuffer.append(bargainProduct.getSkuList().get(i).getSpecificationValues().get(j).getValue() + " ");
                }
                break;
            }
        }
        String guiGe = stringBuffer.toString();


        NewOrderChildBean newOrderChildBean = new NewOrderChildBean(name, imgUrl, price, guiGe, "1", productId, SkuId, 0);

        String storeName = mStore.getStore_name();
        String storeImg = mStore.getLogo();

        List<NewOrderChildBean> list = new ArrayList();
        list.add(newOrderChildBean);
        NewOrderBean newOrderBean = new NewOrderBean(storeName, storeImg, list);
        List<NewOrderBean> newOrderBeans = new ArrayList<>();
        newOrderBeans.add(newOrderBean);

        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra(ConfirmActivity.INTENT_DATA, (ArrayList) newOrderBeans);
        intent.putExtra("type_order", "0");
        intent.putExtra(ORDER_TYPE, "kanjia");
        startActivity(intent);
    }

    public void addDataToOrderDeatil(boolean isOriginal) {//flase则直接以法币购买，true以法币+BT方式购买

        goBuy();


    }

    //发起砍价
    @Override
    public void onBargainFreePart(AddressListBean.DataBean receiver) {

        if (receiver == null) {
            Constans.params.clear();
            if (receiver.getReceiver_id() > 0) {
                Constans.params.put("receiver_id", receiver.getReceiver_id() + "");
            }
            Constans.params.put("bargainProduct_id", bargainProduct.getBargainProduct_id() + "");
            Constans.params.put("sku_id", sku_id + "");
            mPresenter.partBargain(BargainFreeDetailActivity.this, Constans.params);
        } else {
            AffirmAddressDialog affirmAddressDialog = new AffirmAddressDialog(BargainFreeDetailActivity.this, receiver);
            affirmAddressDialog.setOnShowAddressDetailListenter(new AffirmAddressDialog.OnShowAddressDetailListenter() {
                @Override
                public void onShowAddressDetail(int flag) {
                    if (flag == 0) {
                        showAddressDialog();
                    } else if (flag == 1) {
                        Constans.params.clear();
                        if (receiver.getReceiver_id() > 0) {
                            Constans.params.put("receiver_id", receiver.getReceiver_id() + "");
                        }
                        Constans.params.put("bargainProduct_id", bargainProduct.getBargainProduct_id() + "");
                        Constans.params.put("sku_id", sku_id + "");
                        mPresenter.partBargain(BargainFreeDetailActivity.this, Constans.params);
                    }
                }
            });
        }
    }
}
