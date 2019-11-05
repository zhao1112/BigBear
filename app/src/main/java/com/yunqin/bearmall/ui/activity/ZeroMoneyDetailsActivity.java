package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.os.Handler;
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
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.bean.ZeroActivityBean;
import com.yunqin.bearmall.eventbus.ChangeFragmentEvent;
import com.yunqin.bearmall.eventbus.CountDownFinishEvent;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.inter.ProductInstructionCallBack;
import com.yunqin.bearmall.inter.ScrollViewForActivityListener;
import com.yunqin.bearmall.inter.sureAddressCallBack;
import com.yunqin.bearmall.ui.activity.contract.ZeroDetailsContract;
import com.yunqin.bearmall.ui.activity.presenter.ZeroDetailsPresenter;
import com.yunqin.bearmall.ui.dialog.ActivityTextTipDialog;
import com.yunqin.bearmall.ui.dialog.BargainAddressDialog;
import com.yunqin.bearmall.ui.dialog.PriceInstructionDialog;
import com.yunqin.bearmall.ui.dialog.ServiceInstructionDialog;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.ScreenUtils;
import com.yunqin.bearmall.util.ShareUtil;
import com.yunqin.bearmall.widget.DeficitScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yunqin.bearmall.ui.fragment.TrolleyFragment.ORDER_TYPE;

/**
 * @author AYWang
 * @create 2018/8/6
 * @Describe
 */
public class ZeroMoneyDetailsActivity extends BaseActivity implements JoinZeroCallBack, ScrollViewForActivityListener,
        ZeroDetailsContract.UI, ProductInstructionCallBack, BargainAddressDialog.OnBargainFreePartListener,
        sureAddressCallBack, BargainAddressDialog.IdNumber {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.transparent_status_back_img)
    ImageView transparentStatusBackImg;
    @BindView(R.id.transparent_status_head_back)
    RelativeLayout transparentStatusHeadBack;
    @BindView(R.id.transparent_status_add_cart_img)
    ImageView transparentStatusAddCartImg;
    @BindView(R.id.transparent_status_head_add_cart)
    RelativeLayout transparentStatusHeadAddCart;
    @BindView(R.id.transparent_status_share_img)
    ImageView transparentStatusShareImg;
    @BindView(R.id.transparent_status_share)
    RelativeLayout transparentStatusShare;
    @BindView(R.id.transparent_status_head_layout)
    RelativeLayout transparentStatusHeadLayout;
    @BindView(R.id.show_status_back_img)
    ImageView showStatusBackImg;
    @BindView(R.id.show_status_go_cart_img)
    ImageView showStatusGoCartImg;
    @BindView(R.id.show_status_share_img)
    ImageView showStatusShareImg;
    @BindView(R.id.xtablelayout)
    XTabLayout xtablelayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.product_collect)
    ImageView productCollect;
    @BindView(R.id.product_layout_collect)
    LinearLayout productLayoutCollect;
    @BindView(R.id.buy_money)
    TextView buyMoney;
    @BindView(R.id.zero_detail_add_cart)
    LinearLayout zeroDetailAddCart;
    @BindView(R.id.need_people_number)
    TextView needPeopleNumber;
    @BindView(R.id.join_money)
    TextView joinMoney;
    @BindView(R.id.product_detail_join_act)
    LinearLayout productDetailJoinAct;

    private ProductViewPagerAdapter adapter;

    private float mScreenWidth;

    private float mAppBarAlptha = 0f;

    private ZeroDetailsContract.Presenter presenter;

    private String groupPurchasing_id;


    private ZeroActivityBean zeroActivityBean;
    private ZeroActivityBean.DataBean.GroupPurchasingItemBean groupPurchasingItemBean;
    private int isFavorite;


    private String product_id;

    private ServiceInstructionDialog serviceInstructionDialog;
    private PriceInstructionDialog priceInstructionDialog;
    private ActivityTextTipDialog activityTextTipDialog;

    private String receiver_id = "";

    private static final int REFUND_CODE = 66;

    private BargainAddressDialog bargainAddressDialog;


    private ShareBean shareBean = null;

    @Override
    public int layoutId() {
        return R.layout.activity_zero_details;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CountDownFinishEvent countDownFinishEvent) {

        int whichRefresh = countDownFinishEvent.getWhichRefresh();

        if (whichRefresh == 0) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        viewpager.setOffscreenPageLimit(2);
        immerseStatusBar();//设置viewPager中fragment沉浸式状态栏
        changStatusIconCollor(true);//状态栏icon为深色
        mScreenWidth = (float) ScreenUtils.getScreenWidth(ZeroMoneyDetailsActivity.this);//获取屏幕宽度，用来计算appbar透明度
        viewpagerPageChange();//处理viewpager页面选中事件


        groupPurchasing_id = getIntent().getExtras().getString("groupPurchasing_id", "0");


        Map map = new HashMap();
        map.put("groupPurchasing_id", groupPurchasing_id);

        showLoading();
        presenter = new ZeroDetailsPresenter(this, this);
        presenter.getZeroDetailstData(map);

    }

    @OnClick({R.id.product_layout_collect, R.id.zero_detail_add_cart, R.id.product_detail_join_act
            , R.id.show_status_back_img, R.id.show_status_go_cart_img, R.id.transparent_status_share_img, R.id.show_status_share_img})
    void onClick(View view) {
        if (view.getId() == R.id.show_status_back_img) {
            finish();
            return;
        }
        if (BearMallAplication.getInstance().getUser() == null) {
            LoginActivity.starActivity(this);
            return;
        }
        switch (view.getId()) {
            case R.id.transparent_status_share_img:
            case R.id.show_status_share_img:
                if (shareBean == null) {
                    showLoading();
                    Constans.params.clear();
                    Constans.params.put("source_id", groupPurchasing_id + "");
                    Constans.params.put("type", 3 + "");
                    presenter.getShareData(Constans.params);
                } else {
                    ShareUtil.Share(this, shareBean.getData(), false);
                }

                break;
            case R.id.show_status_go_cart_img:

                Intent intent1 = new Intent(ZeroMoneyDetailsActivity.this, TrolleyActivity.class);
                intent1.putExtra("isBack", true);
                startActivity(intent1);

                break;
            case R.id.product_layout_collect:
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
                presenter.setFavorite(map);
                break;

            case R.id.zero_detail_add_cart:
                if (groupPurchasingItemBean == null) {
                    showToast("请稍后再试");
                    return;
                }


                try {
                    if (BearMallAplication.getInstance().getUser().getData().getMember().isMember()) {
                        goBuy();
                    } else {
                        joinMember("您还不是会员，无法以金熊价购买商品。马上开通会员享受更多权益！");
                    }
                } catch (Exception e) {

                }

                break;

            case R.id.product_detail_join_act:
                if (groupPurchasingItemBean == null) {
                    showToast("请稍后再试");
                    return;
                }

                if (!BearMallAplication.getInstance().getUser().getData().getMember().isMember()) {
                    joinMember("您还不是会员，无法兑换。马上开通会员享受更多权益！");
                    return;
                }


                Constans.params.clear();
                showLoading();
                if (groupPurchasingItemBean.getIsPart() == 0) {
                    bargainAddressDialog = new BargainAddressDialog(ZeroMoneyDetailsActivity.this);
                    bargainAddressDialog.setOnBargainFreePartListener(this);
                    bargainAddressDialog.setIdNumber(zeroActivityBean.getData().getGroupPurchasingItem().isCrossBorder());
                    bargainAddressDialog.setOnIdNumber(this);
                    bargainAddressDialog.showDialog();
                    presenter.getReceiverList(Constans.params);
                } else {
                    DialogUtils.tipSearchDialog(this, 2, "", this);
                }
                break;
        }
    }

    private void joinMember(String tip) {
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
                .setCustomTextIds(new int[]{R.id.join_member_no, R.id.tip})
                .setCustomTextContents(new String[]{"我再想想", tip})


                // 设置回掉
                .setOnDialogItemClickListener((thisDialog, clickView) -> {
                    if (clickView.getId() == R.id.join_member_ok) {
                        // startActivity(new Intent(this, VipCenterActivity.class));
                        jump2VipActivity();
                    }
                    thisDialog.dismiss();
                })
                .build().show();
    }

    private void goBuy() {
        try {
            String name = groupPurchasingItemBean.getProductName();
            String imgUrl = groupPurchasingItemBean.getProductImages().get(0).getSource();
            String price = String.valueOf(groupPurchasingItemBean.getMembershipPrice());
            int SkuId = groupPurchasingItemBean.getSku_id();
            int productId = groupPurchasingItemBean.getProduct_id();

            StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i < groupPurchasingItemBean.getSpecifications().size(); i++) {
                stringBuffer.append(groupPurchasingItemBean.getSpecifications().get(i) + " ");
            }

            String guiGe = stringBuffer.toString();


            NewOrderChildBean newOrderChildBean = new NewOrderChildBean(name, imgUrl, price, guiGe, "1", productId, SkuId, 1);

            String storeName = groupPurchasingItemBean.getStoreName();
            String storeImg = groupPurchasingItemBean.getLogo();

            List<NewOrderChildBean> list = new ArrayList();
            list.add(newOrderChildBean);
            NewOrderBean newOrderBean = new NewOrderBean(storeName, storeImg, list);
            List<NewOrderBean> newOrderBeans = new ArrayList<>();
            newOrderBeans.add(newOrderBean);

            Intent intent = new Intent(this, ConfirmActivity.class);
            intent.putExtra(ConfirmActivity.INTENT_DATA, (ArrayList) newOrderBeans);
            intent.putExtra("type_order", "0");
            intent.putExtra(ORDER_TYPE, "pintuan");
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onScrollChanged(DeficitScrollView scrollView, int x, int y, int oldx, int oldy) {

        mAppBarAlptha = ((float) y) / mScreenWidth;
        if (mAppBarAlptha >= 1) {
            mAppBarAlptha = 1;
        } else {
            appbar.setAlpha(mAppBarAlptha);
            transparentStatusHeadLayout.setAlpha(1 - mAppBarAlptha);
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
                    appbar.setAlpha(1);
                    transparentStatusHeadLayout.setAlpha(0);
                } else if (position == 0) {
                    appbar.setAlpha(mAppBarAlptha);
                    transparentStatusHeadLayout.setAlpha(1 - mAppBarAlptha);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void attachTitle(List<Fragment> listFragment, List<String> listTitle) {
        for (int i = 0; i < listTitle.size(); i++) {
            xtablelayout.addTab(xtablelayout.newTab().setText(listTitle.get(i)));
        }
        adapter = new ProductViewPagerAdapter(ZeroMoneyDetailsActivity.this, getSupportFragmentManager(), listFragment, listTitle);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        //初始化显示第一个页面
        xtablelayout.setupWithViewPager(viewpager);
        xtablelayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void getZeroDetailstData(String data) {
        hiddenLoadingView();

        zeroActivityBean = new Gson().fromJson(data, ZeroActivityBean.class);
        groupPurchasingItemBean = zeroActivityBean.getData().getGroupPurchasingItem();
        isFavorite = zeroActivityBean.getData().getGroupPurchasingItem().getIsFavorite();
        product_id = zeroActivityBean.getData().getGroupPurchasingItem().getProduct_id() + "";
        //是否已经参加过团
        if (groupPurchasingItemBean.getIsPart() == 1) {
            DialogUtils.tipSearchDialog(this, 2, "", this);
//            productDetailJoinAct.setBackgroundColor(Color.parseColor("#22563f"));
        }

        //设置是否收藏icon
        if (isFavorite == 0) {
            productCollect.setImageResource(R.drawable.icon_like_normal);
        } else if (isFavorite == 1) {
            productCollect.setImageResource(R.drawable.icon_like_selected);
        }

        buyMoney.setText("¥" + zeroActivityBean.getData().getGroupPurchasingItem().getMembershipPrice());
        joinMoney.setText("¥0+BC" + zeroActivityBean.getData().getGroupPurchasingItem().getCost());


        needPeopleNumber.setText("兑换价");
    }

    @Override
    public void setFavorite(String data) {
        hiddenLoadingView();
        if (isFavorite == 1) {
            productCollect.setImageResource(R.drawable.icon_like_selected);
            Toast.makeText(ZeroMoneyDetailsActivity.this, "收藏成功", Toast.LENGTH_LONG).show();
        } else {
            productCollect.setImageResource(R.drawable.icon_like_normal);
            Toast.makeText(ZeroMoneyDetailsActivity.this, "取消成功", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void joinZeroActivity(String data) {
        //参团回掉
        hiddenLoadingView();
        EventBus.getDefault().post(new ChangeFragmentEvent());
        Toast.makeText(ZeroMoneyDetailsActivity.this, data, Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    @Override
    public void showInstructionDialog(int flag) {
        if (flag == 0) {
            if (serviceInstructionDialog == null) {
                serviceInstructionDialog = new ServiceInstructionDialog(ZeroMoneyDetailsActivity.this);
            } else {
                serviceInstructionDialog.show();
            }
        } else if (flag == 1) {
            if (priceInstructionDialog == null) {
                priceInstructionDialog = new PriceInstructionDialog(ZeroMoneyDetailsActivity.this);
            } else {
                priceInstructionDialog.show();
            }
        } else if (flag == 2) {
            viewpager.setCurrentItem(xtablelayout.getTabCount() - 1);//切换到评论fragment
        } else if (flag == 3) {
            if (activityTextTipDialog == null) {
                activityTextTipDialog = new ActivityTextTipDialog(ZeroMoneyDetailsActivity.this);
            } else {
                activityTextTipDialog.show();
            }
        }

    }


    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
        receiver_id = "";
    }

    //参团按钮点后的dialog处理
    @Override
    public void sureBtn(int flag) {
        if (flag == 1) {
            //确认参团
            showLoading();
            Map map = new HashMap();
            map.put("groupPurchasing_id", groupPurchasing_id);
            map.put("tag", groupPurchasingItemBean.getTag() + "");
            map.put("receiver_id", receiver_id);
            map.put("idCard", idNumber + "");
            presenter.join(map);
        } else {
            EventBus.getDefault().post(new ChangeFragmentEvent());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 500);
        }
    }

    @Override
    public void canle() {
        hiddenLoadingView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 100 && data != null) {
//            if (bargainAddressDialog == null) {
            bargainAddressDialog = new BargainAddressDialog(ZeroMoneyDetailsActivity.this);
            bargainAddressDialog.setIdNumber(zeroActivityBean.getData().getGroupPurchasingItem().isCrossBorder());
            bargainAddressDialog.setOnBargainFreePartListener(this);
            bargainAddressDialog.setOnIdNumber(this);
            bargainAddressDialog.show();
            presenter.getReceiverList(Constans.params);
        }
    }

    //地址id回掉
    @Override
    public void onBargainFreePart(AddressListBean.DataBean receiver) {
        if (receiver == null) {
            return;
        }
        //todo  王志伟
        this.receiver_id = receiver.getReceiver_id() + "";
        Constans.params.clear();
        if (receiver.getReceiver_id() > 0) {
            DialogUtils.tipAddressDialog(this, receiver, this);
        } else {
            this.receiver_id = "";
        }
    }

    //地址相关信息
    @Override
    public void setReceiverList(String data) {
        hiddenLoadingView();
        if (bargainAddressDialog != null) {
            bargainAddressDialog.setData(data, 1);
        }
    }

    @Override
    public void attachSahreBean(ShareBean shareBean) {
        hiddenLoadingView();
        this.shareBean = shareBean;
        ShareUtil.Share(this, shareBean.getData(), false);
    }

    @Override
    public void sureAddressBtn() {
        DialogUtils.tipSearchDialog(this, 1, joinMoney.getText().toString(), this);
    }

    @Override
    public void canleSureAddress() {
        this.receiver_id = "";
    }


    private String idNumber;

    @Override
    public void onIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }


    private void jump2VipActivity() {
        UserInfo user = BearMallAplication.getInstance().getUser();
        if (user == null) {
            LoginActivity.starActivity(ZeroMoneyDetailsActivity.this);
        } else {
            boolean member = BearMallAplication.getInstance().getUser().getData().getMember().isMember();
            boolean opendMember = BearMallAplication.getInstance().getUser().getData().getMember().isOpendMember();
            Log.i("jump2VipActivity", "jump2VipActivity: " + member);
            Log.i("jump2VipActivity", "jump2VipActivity: " + opendMember);

            if (member || opendMember) {
                RenewVipActivity.startRenewVipActivity(ZeroMoneyDetailsActivity.this, null, null);
            } else {
                OpenVipActivity.startOpenVipActivity(ZeroMoneyDetailsActivity.this, null, null);
            }
        }

    }
}
