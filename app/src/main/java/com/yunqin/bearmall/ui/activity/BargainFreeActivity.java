package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.IAdvClick;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BargainFreeMemberAdapter;
import com.yunqin.bearmall.adapter.BargainFreeShopAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.bean.MemberBargainProductListBean;
import com.yunqin.bearmall.ui.activity.contract.BargainFreeContact;
import com.yunqin.bearmall.ui.activity.presenter.BargainFreePresenter;
import com.yunqin.bearmall.ui.dialog.AffirmAddressDialog;
import com.yunqin.bearmall.ui.dialog.BargainAddressDialog;
import com.yunqin.bearmall.ui.dialog.BargainStrategyDialog;
import com.yunqin.bearmall.util.FileUtils;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;
import com.yunqin.bearmall.widget.TopBanner;

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

public class BargainFreeActivity extends BaseActivity implements BargainFreeContact.UI, BargainFreeShopAdapter.OnBargainSpecificationItemsListener,
        BargainAddressDialog.OnBargainFreePartListener, BargainFreeShopAdapter.OnBargainProductListener {

    @BindViews({R.id.bargain_free_shop_img, R.id.bargain_free_my_img})
    List<ImageView> iconImg;

    @BindViews({R.id.bargain_free_shop_tv, R.id.bargain_free_my_tv})
    List<TextView> cateTv;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @BindViews({R.id.bargain_free_left_layout, R.id.bargain_free_right_layou})
    List<LinearLayout> layout;

    @BindView(R.id.bargain_free_list_view)
    ListView listView;

    @BindView(R.id.bargain_free_member_list_view)
    RecyclerView recyclerView;
    //构建适配器

    @BindView(R.id.bargain_free_rule)
    TextView ruleTextView;

    @BindViews({R.id.bargain_empty_layout, R.id.bargain_my_empty_layout})
    List<LinearLayout> empty_layout;

    @BindView(R.id.bargain_free_list_view_head_content_layout)
    LinearLayout content_layout;

    @BindView(R.id.bargain_free_left_view_pager)
    TopBanner viewPagerInner;

    @BindView(R.id.bargain_free_left_adver)
    ImageView left_adverInner;

    private int page_numer_left = 1;
    private int isLoadMoreOrRefreshLeft = 1;
    private int page_numer_right = 1;
    private int isLoadMoreOrRefreshRight = 1;
    private boolean isLeft = true;
    private BargainFreeContact.Presenter bargainFreePresenter;
    private BargainFreeShopAdapter bargainFreeShopAdapter;
    private BargainFreeMemberAdapter bargainFreeMemberAdapter;

    private List<BargainProductListBean.BargainProductList> bargainProductLists;
    private List<MemberBargainProductListBean.BargainRecordList> bargainMemberProductLists;

    private BargainStrategyDialog bargainStrategyDialog;

    private View view;
    private TopBanner viewPager;
    private ImageView left_adver;
    private LinearLayout bargain_free_work_at_for_bc;
    private EditText kan_jia_search_edit_text;


    @OnClick({R.id.bargain_free_back_img_layout, R.id.bargain_free_shop_layout,
            R.id.bargain_free_my_layout, R.id.bargain_free_rule, R.id.bargain_free_work_at_for_bc})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bargain_free_back_img_layout:
                finish();
                break;

            case R.id.bargain_free_work_at_for_bc:
                if(BearMallAplication.getInstance().getUser()!=null){
                    intent = new Intent(BargainFreeActivity.this, DailyTasksActivity.class);
                    startActivity(intent);
                }else {
                    LoginActivity.starActivity(this);
                }
                break;

            case R.id.bargain_free_shop_layout:
                isOnClickLeft(true);
//                switchFragment(1);
                break;

            case R.id.bargain_free_my_layout:
                //todo  王志伟  未登录去登陆
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(this);
                } else {
                    isOnClickLeft(false);
//                    switchFragment(1);
                }
                break;

            case R.id.bargain_free_rule:
                if (bargainStrategyDialog == null) {
                    bargainStrategyDialog = new BargainStrategyDialog(BargainFreeActivity.this);
                } else {
                    bargainStrategyDialog.show();
                }
                //免单攻略
                break;

            default:
                break;
        }
    }


    @Override
    public int layoutId() {
        return R.layout.activity_bargain_free;
    }

    @Override
    public void init() {

//        mFragmentManager = getSupportFragmentManager();
//        switchFragment(1);
        view = LayoutInflater.from(BargainFreeActivity.this).inflate(R.layout.bargain_free_list_view_head_view, null);
        viewPager = view.findViewById(R.id.bargain_free_left_view_pager);
        left_adver = view.findViewById(R.id.bargain_free_left_adver);
        bargain_free_work_at_for_bc = view.findViewById(R.id.bargain_free_work_at_for_bc);

        // 2
        kan_jia_search_edit_text = view.findViewById(R.id.kan_jia_search_edit_text);

        kan_jia_search_edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    KAN_JIA_SEARCH_DATA = kan_jia_search_edit_text.getText().toString();
                    page_numer_left = 1;
                    isLoadMoreOrRefreshLeft = 1;
                    Constans.params.clear();
                    Constans.params.put("page_number", "" + page_numer_left);
                    bargainFreePresenter.refreshListData(Constans.params);
                }
                return false;
            }
        });


        bargain_free_work_at_for_bc.setOnClickListener(v -> {
            if(BearMallAplication.getInstance().getUser()!=null){
                Intent intent = new Intent(BargainFreeActivity.this, DailyTasksActivity.class);
                startActivity(intent);
            }else {
                LoginActivity.starActivity(this);
            }
        });

        listView.addHeaderView(view);

        showLoading();
        bargainProductLists = new ArrayList<>();
        bargainMemberProductLists = new ArrayList<>();

        refreshLayout.setHeaderView(new RefreshHeadView(BargainFreeActivity.this));
        refreshLayout.setBottomView(new RefreshBottomView(BargainFreeActivity.this));

        bargainFreeShopAdapter = new BargainFreeShopAdapter(BargainFreeActivity.this, bargainProductLists);
        bargainFreeShopAdapter.setOnBargainProductListener(this);
        bargainFreeShopAdapter.setOnBargainSpecificationItemsListener(this);
        listView.setAdapter(bargainFreeShopAdapter);

        // TODO 777
//        listView.setEmptyView(empty_layout.get(0));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BargainFreeActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        bargainFreeMemberAdapter = new BargainFreeMemberAdapter(BargainFreeActivity.this, bargainMemberProductLists);
        bargainFreeMemberAdapter.setOnBargainFreeAgainlistener(new BargainFreeMemberAdapter.OnBargainFreeAgainlistener() {
            @Override
            public void onBargainFreeAgain() {
                isOnClickLeft(true);
            }
        });
        recyclerView.setAdapter(bargainFreeMemberAdapter);

//        listView.get(1).setAdapter(bargainFreeMemberAdapter);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                Log.e("onRefresh", "onRefresh");
                if (isLeft) {
                    page_numer_left = 1;
                    isLoadMoreOrRefreshLeft = 1;
                    KAN_JIA_SEARCH_DATA = "";
                    Constans.params.clear();
                    Constans.params.put("page_number", "" + page_numer_left);
                    bargainFreePresenter.refresh(Constans.params);
                } else {
                    page_numer_right = 1;
                    isLoadMoreOrRefreshRight = 1;
                    Constans.params.clear();
                    Constans.params.put("page_number", "" + page_numer_right);
                    bargainFreePresenter.getMemberBargainProductList(Constans.params);
                }

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                Log.e("onLoadMore", "onLoadMore");
                if (isLeft) {
                    isLoadMoreOrRefreshLeft = 2;
                    Constans.params.clear();
                    Constans.params.put("page_number", ++page_numer_left + "");
                    bargainFreePresenter.refresh(Constans.params);
                } else {
                    isLoadMoreOrRefreshRight = 2;
                    Constans.params.clear();
                    Constans.params.put("page_number", ++page_numer_right + "");
                    bargainFreePresenter.getMemberBargainProductList(Constans.params);
                }

            }
        });

        bargainFreePresenter = new BargainFreePresenter(BargainFreeActivity.this, this);

        Constans.params.clear();
        Constans.params.put("page_number", page_numer_left + "");
        bargainFreePresenter.refresh(Constans.params);

    }


    private Map<String, String> map;

    //
    public void isOnClickLeft(boolean isLeft) {
        this.isLeft = isLeft;
        if (isLeft) {
            iconImg.get(0).setImageResource(R.drawable.ic_bargain_free_shop_selected);
            iconImg.get(1).setImageResource(R.drawable.ic_bargain_free_my_normal);
            cateTv.get(0).setTextColor(getResources().getColor(R.color.main_color));
            cateTv.get(1).setTextColor(getResources().getColor(R.color.product_customer_collect));

            layout.get(0).setVisibility(View.VISIBLE);
            layout.get(1).setVisibility(View.GONE);

            if (bargainFreeShopAdapter.getListSize() == 0) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }


        } else {

            iconImg.get(0).setImageResource(R.drawable.ic_bargain_free_shop_normal);
            iconImg.get(1).setImageResource(R.drawable.ic_bargain_free_my_selected);
            cateTv.get(0).setTextColor(getResources().getColor(R.color.product_customer_collect));
            cateTv.get(1).setTextColor(getResources().getColor(R.color.main_color));

            layout.get(1).setVisibility(View.VISIBLE);
            layout.get(0).setVisibility(View.GONE);

            linearLayout.setVisibility(View.GONE);


            Constans.params.clear();
            Constans.params.put("page_number", page_numer_right + "");
            bargainFreePresenter.getMemberBargainProductList(Constans.params);
        }
    }

    @Override
    public void attachhData(BargainProductListBean bargainProductListBean) {
        hiddenLoadingView();
        if (bargainProductListBean.getData().getHas_more() == 0) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
        }

        if (isLoadMoreOrRefreshLeft == 1) {
            bargainProductLists.clear();
            bargainProductLists = bargainProductListBean.getData().getBargainProductList();
            bargainFreeShopAdapter.setData(bargainProductLists);
        } else {
            bargainFreeShopAdapter.addData(bargainProductListBean.getData().getBargainProductList());
        }
        onFinishRe();

        if (bargainFreeShopAdapter.getListSize() == 0) {
            listView.setVisibility(View.GONE);
            empty_layout.get(0).setVisibility(View.VISIBLE);

            content_layout.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            empty_layout.get(0).setVisibility(View.GONE);

            content_layout.setVisibility(View.GONE);
        }

        map = new HashMap();
        map.put("positionType", 10 + "");
        bargainFreePresenter.getAdMobileList(map);

        map = new HashMap();
        map.put("positionType", 11 + "");
        bargainFreePresenter.getAdMobileListMid(map);


        // TODO 日了一笔
        linearLayout.setVisibility(View.GONE);
        kan_jia_search_edit_text.setText("");
        KAN_JIA_SEARCH_DATA = "";


    }

    @Override
    public void saveJson(String data) {
        boolean writeFileReslut = FileUtils.writeFileFromString(BargainFreeActivity.this.getCacheDir().getAbsolutePath() + File.separator + "productBargainInfo.txt", data, false);//复写写文件
        if (writeFileReslut) {
            Log.e("saveJson", "文件保存成功");
        }
    }

    @Override
    public void attachMemberthData(MemberBargainProductListBean memberBargainProductListBean) {
        hiddenLoadingView();
        if (memberBargainProductListBean.getData().getHas_more() == 0) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
        }

        if (isLoadMoreOrRefreshRight == 1) {
            bargainMemberProductLists.clear();
            bargainMemberProductLists = memberBargainProductListBean.getData().getBargainRecordList();
            bargainFreeMemberAdapter.setData(bargainMemberProductLists);
        } else {
            bargainFreeMemberAdapter.addData(memberBargainProductListBean.getData().getBargainRecordList());
        }
        if (isRereshTimer) {
            countDow();
        }
        onFinishRe();

        if (bargainFreeMemberAdapter.getListSize() == 0) {
            recyclerView.setVisibility(View.GONE);
            empty_layout.get(1).setVisibility(View.VISIBLE);


        } else {
            recyclerView.setVisibility(View.VISIBLE);
            empty_layout.get(1).setVisibility(View.GONE);


        }
    }

    private boolean isRereshTimer = true;

    public void countDow() {
        isRereshTimer = false;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < bargainMemberProductLists.size(); i++) {
                            MemberBargainProductListBean.BargainRecordList bargainRecordList = bargainMemberProductLists.get(i);
                            if (bargainRecordList.getStatus() == 1) {
                                long time = bargainRecordList.getExpireDate();
                                bargainRecordList.setExpireDate(time - 1000);
                            }
                        }
                        bargainFreeMemberAdapter.setData(bargainMemberProductLists);
                    }
                });
            }
        }, 1000, 1000);
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


    @Override
    public void setPartBargain(String data) {//发起砍价
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
                Intent intent = new Intent(BargainFreeActivity.this, BargainFreeShareActivity.class);
                intent.putExtra(BargainFreeShareActivity.BARGAINRECORD_ID, bargainRecord_id);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getAdMobileList(String data) {
        BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
        List<BannerBean.DataBean.AdMobileListBean> adMobileList = bannerBean.getData().getAdMobileList();
        List<String> mlistUrl = new ArrayList<>();
        mlistUrl.clear();
        if (adMobileList != null && adMobileList.size() > 0) {
//            BannerViewPagerAdapter bannerViewPagerAdapter = new BannerViewPagerAdapter(BargainFreeActivity.this,adMobileList);
            for (int i = 0; i < adMobileList.size(); i++) {
                mlistUrl.add(adMobileList.get(i).getImg());
            }

            if (bargainFreeShopAdapter.getListSize() == 0) {
                viewPagerInner.setImagesUrl(mlistUrl);

                viewPagerInner.setOnItemClickListener(new TopBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                    0：普通商品 1：说明广告 2：导购文章 4：会员往期活动 5：0元拼团 6：砍价免费拿 7 糖果夺宝
                            adClick(adMobileList.get(position));
                    }
                });
            } else {
                viewPager.setImagesUrl(mlistUrl);
                viewPager.setOnItemClickListener(new TopBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                    0：普通商品 1：说明广告 2：导购文章 4：会员往期活动 5：0元拼团 6：砍价免费拿 7 糖果夺宝

                        adClick(adMobileList.get(position));
                    }
                });
            }
        }
    }

    public void adClick(BannerBean.DataBean.AdMobileListBean adMobileListBean) {
        IAdvClick.click(this, adMobileListBean.getType(), adMobileListBean.getSkipType(), adMobileListBean.getSource_id(),null);
    }

    @Override
    public void getAdMobileListMid(String data) {
        BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
        List<BannerBean.DataBean.AdMobileListBean> adMobileList = bannerBean.getData().getAdMobileList();
        if (adMobileList != null && adMobileList.size() > 0) {
            if (bargainFreeShopAdapter.getListSize() == 0) {
                Glide.with(BargainFreeActivity.this).load(adMobileList.get(0).getImg()).into(left_adverInner);

                left_adverInner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adClick(adMobileList.get(0));
                    }
                });
            } else {
                Glide.with(BargainFreeActivity.this).load(adMobileList.get(0).getImg()).into(left_adver);

                left_adver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adClick(adMobileList.get(0));
                    }
                });
            }
        }


    }


    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

    @Override
    public void onFail(Throwable e) {
        Log.e("Throwable", e.toString());
        hiddenLoadingView();
        onFinishRe();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 100 && data != null) {
//            Constans.params.clear();
//            Constans.params.put("bargainProduct_id",bargainProduct_id+"");
//            Constans.params.put("sku_id",sku_id+"");
//            bargainFreePresenter.partBargain(Constans.params);

            bargainFreePresenter.getReceiverList(Constans.params);

        } else if (requestCode == 1 && resultCode == 1000 && data != null) {
            isOnClickLeft(false);
        }
    }

    public void showAddressDialog() {
        if (bargainAddressDialog == null) {
            bargainAddressDialog = new BargainAddressDialog(BargainFreeActivity.this);
            bargainAddressDialog.setOnBargainFreePartListener(this);
            bargainAddressDialog.setData(addressData, 2);
            bargainAddressDialog.showDialog();
        } else {
            bargainAddressDialog.setData(addressData, 2);
            bargainAddressDialog.showDialog();
        }
    }

    private long sku_id;
    private BargainAddressDialog bargainAddressDialog;
    private long bargainProduct_id;

    @Override
    public void onBargainSpecificationItems(long sku_id) {
        this.sku_id = sku_id;
        bargainFreePresenter.getReceiverList(Constans.params);
    }

    @Override
    public void onBargainFreePart(AddressListBean.DataBean receiver) {
        if (receiver == null) {
            Constans.params.clear();
            Constans.params.put("bargainProduct_id", bargainProduct_id + "");
            Constans.params.put("sku_id", sku_id + "");
            bargainFreePresenter.partBargain(Constans.params);
        } else {
            AffirmAddressDialog affirmAddressDialog = new AffirmAddressDialog(BargainFreeActivity.this, receiver);
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


    /**
     * 5555555555
     *
     * @param bargainProduct_id
     */

    @Override
    public void onBargainProduct(long bargainProduct_id) {
        this.bargainProduct_id = bargainProduct_id;
    }


    private String KAN_JIA_SEARCH_DATA = "";

    @Override
    public String getSearchData() {
        return KAN_JIA_SEARCH_DATA;
    }


    @BindView(R.id.bargain_empty_layout_add_p)
    LinearLayout linearLayout;

    @Override
    public void attachListData(BargainProductListBean bargainProductListBean) {
        hiddenLoadingView();
        if (bargainProductListBean.getData().getHas_more() == 0) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
        }
        onFinishRe();
        if (isLoadMoreOrRefreshLeft == 1) {
            bargainProductLists.clear();
            bargainProductLists = bargainProductListBean.getData().getBargainProductList();
            bargainFreeShopAdapter.setData(bargainProductLists);
        } else {
            bargainFreeShopAdapter.addData(bargainProductListBean.getData().getBargainProductList());
        }

        if (bargainFreeShopAdapter.getListSize() == 0) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }
}
