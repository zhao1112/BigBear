package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BargainFreeShopAdapter;
import com.yunqin.bearmall.adapter.HelpFriendCutDownAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.bean.CutDownGetWhatBean;
import com.yunqin.bearmall.bean.HelpOtherBarginInfo;
import com.yunqin.bearmall.bean.MemberBargainProductListBean;
import com.yunqin.bearmall.inter.FreeGetBtnCallBack;
import com.yunqin.bearmall.ui.activity.contract.BargainFreeContact;
import com.yunqin.bearmall.ui.activity.contract.HelpFriendCutDownThePriceActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.BargainFreePresenter;
import com.yunqin.bearmall.ui.activity.presenter.HelpFriendCutDownThePriceActivityPresenter;
import com.yunqin.bearmall.ui.dialog.AffirmAddressDialog;
import com.yunqin.bearmall.ui.dialog.BargainAddressDialog;
import com.yunqin.bearmall.ui.dialog.BargainStrategyDialog;
import com.yunqin.bearmall.ui.dialog.TipCutDownInfoDialog;
import com.yunqin.bearmall.util.FileUtils;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.CustomRecyclerView;
import com.yunqin.bearmall.widget.ScrollListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author AYWang
 * @create 2018/8/28
 * @Describe
 */
public class HelpFriendCutDownThePriceActivity extends BaseActivity implements BargainFreeContact.UI,
        BargainFreeShopAdapter.OnBargainProductListener,
        BargainFreeShopAdapter.OnBargainSpecificationItemsListener,
        FreeGetBtnCallBack, BargainAddressDialog.OnBargainFreePartListener
        , HelpFriendCutDownThePriceActivityContract.UI {

    @BindView(R.id.recycler_view)
    CustomRecyclerView recycler_view;//循环滚动的那个列表

    @BindView(R.id.bargain_free_back_img_layout)
    RelativeLayout bargain_free_back_img_layout;

    @BindView(R.id.bargain_share_invite_friend)//帮好友砍一刀
            Button bargain_share_invite_friend;

    @BindView(R.id.custom_recommend_view)//推荐商品列表
            CustomRecommendView custom_recommend_view;

    @BindView(R.id.bargain_share_already_price)
    TextView bargain_share_already_price;

    @BindView(R.id.bargain_share_criticalRatio)
    TextView bargain_share_criticalRatio;

    @BindView(R.id.text_tip)
    TextView text_tip;

    @BindView(R.id.cutdown_layout)
    LinearLayout cutdown_layout;

    @BindView(R.id.bargain_free_list_view)//砍价商品列表
            ScrollListView bargain_free_list_view;

    @BindView(R.id.bargain_free_content_layout)
    FrameLayout bargain_free_content_layout;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.top_layout)
    FrameLayout top_layout;

    @BindView(R.id.second_layout)
    LinearLayout second_layout;

    @BindView(R.id.bargain_empty_layout)
    LinearLayout bargain_empty_layout;

    @BindView(R.id.bargain_free_share_original_price_tv)
    TextView bargain_free_share_original_price_tv;

    @BindView(R.id.bargain_free_rule)
    TextView bargain_free_rule;


    private List<BargainProductListBean.BargainProductList> bargainProductLists;
    private BargainFreeShopAdapter bargainFreeShopAdapter;


    private BargainFreeContact.Presenter bargainFreePresenter;


    private List<CutDownGetWhatBean.DataBean.BargainRecordListBean> dataList = new ArrayList<>();
    private HelpFriendCutDownAdapter helpFriendCutDownAdapter;

    private CompositeDisposable compositeDisposable;


    private TipCutDownInfoDialog tipCutDownInfoDialog;

    private HelpFriendCutDownThePriceActivityPresenter priceActivityPresenter;

    private String bargainRecord_id;//砍价商品ID
    private BargainStrategyDialog bargainStrategyDialog;


    public static void starActivity(Activity mContext, String bargainRecord_id) {
        Intent intent = new Intent(mContext, HelpFriendCutDownThePriceActivity.class);
        intent.putExtra("bargainRecord_id", bargainRecord_id);
        mContext.startActivity(intent);
    }


    @OnClick({R.id.bargain_free_back_img_layout, R.id.bargain_share_invite_friend, R.id.bargain_free_rule})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.bargain_free_back_img_layout:
                finish();
                break;
            case R.id.bargain_share_invite_friend:
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(this);
                    return;
                }
                if (!bargain_share_invite_friend.getText().toString().equals("我也要免费拿")) {
                    helpToBargain();
                } else {
                    scrollToPosition();
                }
                break;
            case R.id.bargain_free_rule:

                if (bargainStrategyDialog == null) {
                    bargainStrategyDialog = new BargainStrategyDialog(HelpFriendCutDownThePriceActivity.this);
                } else {
                    bargainStrategyDialog.show();
                }

                break;
        }
    }


    public void helpToBargain() {

        showLoading();

        Map<String, String> mHashMap = new HashMap();
        mHashMap.put("bargainRecord_id", bargainRecord_id);
        RetrofitApi.request(HelpFriendCutDownThePriceActivity.this, RetrofitApi.createApi(Api.class).helpOthersBargain(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {

                    hiddenLoadingView();

                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if (tipCutDownInfoDialog == null) {
                        tipCutDownInfoDialog = new TipCutDownInfoDialog(HelpFriendCutDownThePriceActivity.this, HelpFriendCutDownThePriceActivity.this);
                    }
                    tipCutDownInfoDialog.show(jsonObject1.getString("content"),
                            jsonObject1.getString("bargainAmount"),
                            jsonObject1.getString("iconUrl")
                    );


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
                try {

                    hiddenLoadingView();

                    JSONObject jsonObject = new JSONObject(e.getMessage());
                    int code = jsonObject.optInt("code");
                    if (code == 0) {
                        new ICustomDialog.Builder(HelpFriendCutDownThePriceActivity.this)
                                // 设置布局
                                .setLayoutResId(R.layout.kanjia_finish_layout)
                                // 点击空白是否消失
                                .setCanceledOnTouchOutside(false)
                                // 点击返回键是否消失
                                .setCancelable(false)
                                // 设置Dialog的绝对位置
                                .setDialogPosition(Gravity.CENTER)
                                // 设置自定义动画
                                .setAnimationResId(0)
                                // 设置监听ID
                                .setListenedItems(new int[]{R.id.join_member_ok})
                                // 设置回掉
                                .setOnDialogItemClickListener(new ICustomDialog.OnDialogItemClickListener() {
                                    @Override
                                    public void onDialogItemClick(ICustomDialog thisDialog, View clickView) {
                                        thisDialog.dismiss();
                                    }
                                })
                                .build().show();
                    }


                } catch (Exception ex) {

                }
            }
        });
    }


    /**
     * 滑动到指定位置
     */
    private void scrollToPosition() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, top_layout.getHeight() + second_layout.getHeight());
            }
        });
    }


    @Override
    public int layoutId() {
        return R.layout.activity_bargain_free_help;
    }

    @Override
    public void init() {

        if (getIntent() != null) {
            bargainRecord_id = getIntent().getStringExtra("bargainRecord_id");
        }


        initCustomViewData();

        compositeDisposable = new CompositeDisposable();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setFocusable(false);
        bargain_free_list_view.setFocusable(false);
        custom_recommend_view.setFocusable(false);

        bargainFreePresenter = new BargainFreePresenter(HelpFriendCutDownThePriceActivity.this, this);
        priceActivityPresenter = new HelpFriendCutDownThePriceActivityPresenter(this, this);

        Constans.params.clear();
        Constans.params.put("page_number", 1 + "");
        bargainFreePresenter.refresh(Constans.params);

        Map map = new HashMap();
        map.put("page_size", "50");
        priceActivityPresenter.getListData(map);

        showLoading();
        Map mapHelp = new HashMap();
        mapHelp.put("bargainRecord_id", bargainRecord_id);
        priceActivityPresenter.getContentInfo(mapHelp);
    }


    public void initCustomViewData() {

        custom_recommend_view.setTvBottom("推荐商品");
        custom_recommend_view.setDiviervisibility(View.VISIBLE);
        custom_recommend_view.setTopTextViewLeft();
        custom_recommend_view.setTopTextViewBgColor(HelpFriendCutDownThePriceActivity.this.getResources().getColor(R.color.white));
        custom_recommend_view.setTopTextViewHeight(HelpFriendCutDownThePriceActivity.this.getResources().getDimension(R.dimen.ds94));
        custom_recommend_view.hideTopLayout();

        custom_recommend_view.setManager(new GridLayoutManager(HelpFriendCutDownThePriceActivity.this, 2));
        custom_recommend_view.start(HelpFriendCutDownThePriceActivity.this);


        bargainProductLists = new ArrayList<>();
        bargainFreeShopAdapter = new BargainFreeShopAdapter(HelpFriendCutDownThePriceActivity.this, bargainProductLists);
        bargainFreeShopAdapter.setOnBargainProductListener(this);
        bargainFreeShopAdapter.setOnBargainSpecificationItemsListener(this);
        bargain_free_list_view.setAdapter(bargainFreeShopAdapter);

    }


    private long bargainProduct_id;
    private long sku_id;
    private BargainAddressDialog bargainAddressDialog;

    @Override
    public void onBargainProduct(long bargainProduct_id) {
        this.bargainProduct_id = bargainProduct_id;
    }

    @Override
    public void onBargainSpecificationItems(long sku_id) {
        this.sku_id = sku_id;
        bargainFreePresenter.getReceiverList(Constans.params);
    }

    @Override
    public void attachhData(BargainProductListBean bargainProductListBean) {
        bargainProductLists.clear();
        bargainProductLists = bargainProductListBean.getData().getBargainProductList();
        bargainFreeShopAdapter.setData(bargainProductLists);
        if (bargainProductLists.size() > 0) {
            bargain_free_list_view.setVisibility(View.VISIBLE);
            bargain_empty_layout.setVisibility(View.GONE);
        } else {
            bargain_free_list_view.setVisibility(View.GONE);
            bargain_empty_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void attachListData(BargainProductListBean bargainProductListBean) {

    }

    @Override
    public void saveJson(String data) {
        boolean writeFileReslut = FileUtils.writeFileFromString(HelpFriendCutDownThePriceActivity.this.getCacheDir().getAbsolutePath() + File.separator + "productBargainInfo.txt", data, false);//复写写文件
        if (writeFileReslut) {
            Log.e("saveJson", "文件保存成功");
        }
    }

    @Override
    public void attachMemberthData(MemberBargainProductListBean memberBargainProductListBean) {
    }

    private String addressData;

    @Override
    public void setReceiverList(String data) {
        hiddenLoadingView();
        addressData = data;
        AddressListBean bean = new Gson().fromJson(data, AddressListBean.class);
        if (bean.getData().size() == 0) {
            Constans.params.clear();
            Constans.params.put("bargainProduct_id", bargainProduct_id + "");
            Constans.params.put("sku_id", sku_id + "");
            bargainFreePresenter.partBargain(Constans.params);
        } else {
            showAddressDialog();
        }
    }

    private int page_numer_left = 1;

    @Override
    public void setPartBargain(String data) {
        hiddenLoadingView();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("code");
            if (code == 1) {

                Constans.params.clear();
                Constans.params.put("page_number", page_numer_left + "");
                bargainFreePresenter.refresh(Constans.params);

                String jsonData = jsonObject.getString("data");
                JSONObject jsonObjectData = new JSONObject(jsonData);
                long bargainRecord_id = jsonObjectData.getLong("bargainRecord_id");
                Intent intent = new Intent(HelpFriendCutDownThePriceActivity.this, BargainFreeShareActivity.class);
                intent.putExtra(BargainFreeShareActivity.BARGAINRECORD_ID, bargainRecord_id);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAdMobileList(String data) {

    }

    @Override
    public void getAdMobileListMid(String data) {

    }

    @Override
    public void onFail(Throwable e) {
        Log.e("Throwable", e.toString());
        hiddenLoadingView();
    }

    public void showAddressDialog() {
        if (bargainAddressDialog == null) {
            bargainAddressDialog = new BargainAddressDialog(HelpFriendCutDownThePriceActivity.this);
            bargainAddressDialog.setOnBargainFreePartListener(this);
            bargainAddressDialog.setData(addressData, 2);
            bargainAddressDialog.showDialog();
        } else {
            bargainAddressDialog.setData(addressData, 2);
            bargainAddressDialog.showDialog();
        }
    }

    @Override
    public void onBargainFreePart(AddressListBean.DataBean receiver) {
        if (receiver == null) {
            Constans.params.clear();
            Constans.params.put("bargainProduct_id", bargainProduct_id + "");
            Constans.params.put("sku_id", sku_id + "");
            bargainFreePresenter.partBargain(Constans.params);
        } else {
            AffirmAddressDialog affirmAddressDialog = new AffirmAddressDialog(HelpFriendCutDownThePriceActivity.this, receiver);
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
                        Constans.params.put("bargainProduct_id", bargainProduct_id + "");
                        Constans.params.put("sku_id", sku_id + "");
                        bargainFreePresenter.partBargain(Constans.params);
                    }
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 100 && data != null) {
            Constans.params.clear();
            Constans.params.put("bargainProduct_id", bargainProduct_id + "");
            Constans.params.put("sku_id", sku_id + "");
            bargainFreePresenter.partBargain(Constans.params);

        }
    }

    //Dialog上我也要免费拿按钮的点击
    @Override
    public void clickThisBtn() {
        bargain_share_invite_friend.setText("我也要免费拿");
        bargain_share_already_price.setVisibility(View.GONE);
        text_tip.setVisibility(View.VISIBLE);
        cutdown_layout.setVisibility(View.INVISIBLE);
        bargain_share_criticalRatio.setVisibility(View.GONE);
        custom_recommend_view.setVisibility(View.GONE);

        bargain_free_content_layout.setVisibility(View.VISIBLE);
        Constans.params.clear();
        Constans.params.put("page_number", 1 + "");
        bargainFreePresenter.refresh(Constans.params);

        scrollToPosition();
    }


    //循环滚动的列表数据
    // 喊好友砍价  中间滚动内容
    @Override
    public void attachListData(String data) {
        try {
            CutDownGetWhatBean cutDownGetWhatBean = new Gson().fromJson(data, CutDownGetWhatBean.class);
            List<CutDownGetWhatBean.DataBean.BargainRecordListBean> list = cutDownGetWhatBean.getData().getBargainRecordList();
            Collections.reverse(list);

            if (list != null && list.size() > 4) {
                for (int i = 0; i < 4; i++) {
                    this.dataList.add(list.get(0));
                    list.remove(0);
                }
                handleZeroPast(list);
            } else {
                this.dataList = list;
            }
            helpFriendCutDownAdapter = new HelpFriendCutDownAdapter<CutDownGetWhatBean.DataBean.BargainRecordListBean>(this, dataList);
            recycler_view.setAdapter(helpFriendCutDownAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleZeroPast(List<CutDownGetWhatBean.DataBean.BargainRecordListBean> list) {

        Disposable disposable = Observable.interval(800, 2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

//                        if (aLong > pasts.size()){
//                            compositeDisposable.clear();
//                        }

                        int position = (int) (aLong % list.size());

                        CutDownGetWhatBean.DataBean.BargainRecordListBean listBean = list.get(position);

                        HelpFriendCutDownThePriceActivity.this.dataList.add(0, listBean);

                        helpFriendCutDownAdapter.notifyItemInserted(0);

                        recycler_view.scrollToPosition(0);
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
    // 喊好友砍价  中间滚动内容 结束


    //帮好友砍价面板信息
    @Override
    public void attachHelpOtherBargainInfo(String data) {
        hiddenLoadingView();
        HelpOtherBarginInfo helpOtherBarginInfo = new Gson().fromJson(data, HelpOtherBarginInfo.class);
        upViewDataInfo(helpOtherBarginInfo);
    }


    //将请求来的信息赋值给View
    @BindView(R.id.bargain_share_user_head_img)
    ImageView bargain_share_user_head_img;

    @BindView(R.id.bargain_share_product_img)
    ImageView bargain_share_product_img;//商品图片

    @BindView(R.id.bargain_share_product_name)
    TextView bargain_share_product_name;//商品名称

    @BindView(R.id.bargain_share_product_count)
    TextView bargain_share_product_count;//已有多少人参加

    @BindView(R.id.bargain_share_original_price)
    TextView bargain_share_original_price;//大熊零售价

    @BindView(R.id.bargain_share_current_price)
    TextView bargain_share_current_price;

    @BindView(R.id.bargain_share_user_name)
    TextView bargain_share_user_name;

    @BindView(R.id.bargain_share_expire)
    CountdownView bargain_share_expire;

    private void upViewDataInfo(HelpOtherBarginInfo helpOtherBarginInfo) {
        HelpOtherBarginInfo.DataBean.BargainDetailsBean bargainDetailsBean = helpOtherBarginInfo.getData().getBargainDetails();

        if (bargainDetailsBean.getStatus() == 0) {
            showToast("当前砍价已过期");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
            return;
        }

        if (bargainDetailsBean.getStatus() == 2) {
            showToast("当前砍价已结束");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
            return;
        }

        Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                .load(bargainDetailsBean.getIconUrl()).into(bargain_share_user_head_img);

        bargain_share_user_name.setText(bargainDetailsBean.getNickName());

        Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment))
                .load(bargainDetailsBean.getProductImages().getThumbnail()).into(bargain_share_product_img);

        bargain_share_product_name.setText(bargainDetailsBean.getProductName());

        bargain_share_product_count.setText("已有" + bargainDetailsBean.getPersonalCount() + "人参加");

//        bargain_share_original_price.setText("¥"+bargainDetailsBean.getSourcePartPrice()+"＋BT"+bargainDetailsBean.getSourcePartBtAmount());
        bargain_share_original_price.setText("¥" + bargainDetailsBean.getPrice());

        bargain_free_share_original_price_tv.setText("¥" + bargainDetailsBean.getSourcePartPrice() + "＋BT" + bargainDetailsBean.getSourcePartBtAmount());

        bargain_share_already_price.setText("已砍¥" + bargainDetailsBean.getBargainedPrice() + "+BC" + bargainDetailsBean.getBargainedBtAmount());

        bargain_share_criticalRatio.setText("砍到" + bargainDetailsBean.getCriticalRatio() + ",可多砍" + bargainDetailsBean.getBargainMoreAmount() + "元");

        bargain_share_current_price.setText(("¥" + bargainDetailsBean.getCurrentPartPrice() + "＋BT" + bargainDetailsBean.getCurrentPartBtAmount()));

        bargain_share_expire.start(bargainDetailsBean.getRestTime());
        bargain_share_expire.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                showToast("砍价已结束");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });

        if (helpOtherBarginInfo.getData().getIsHaveBargained() == 1) {
            bargain_share_invite_friend.setText("我也要免费拿");
            bargain_share_already_price.setVisibility(View.GONE);
            text_tip.setVisibility(View.VISIBLE);
            cutdown_layout.setVisibility(View.INVISIBLE);
            bargain_share_criticalRatio.setVisibility(View.GONE);
            custom_recommend_view.setVisibility(View.GONE);

            bargain_free_content_layout.setVisibility(View.VISIBLE);
            Constans.params.clear();
            Constans.params.put("page_number", 1 + "");
            bargainFreePresenter.refresh(Constans.params);
            return;
        }
    }

    @Override
    public void onError() {
        hiddenLoadingView();
    }


    @Override
    public String getSearchData() {
        return "";
    }
}
