package com.newversions.tbk.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductSumAdapter2;
import com.yunqin.bearmall.adapter.ProductSumAdapter3;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SearchData;
import com.yunqin.bearmall.widget.RefreshHeadView;
import com.yunqin.bearmall.widget.SwitchButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 根据类别查看商品
 */
public class ProductSumActivity2 extends BaseActivity {


    @BindView(R.id.input_content_text)
    LinearLayout input_content_text;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.rcl)
    RecyclerView rcl;
    @BindView(R.id.rc)
    RecyclerView rc2;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    @BindView(R.id.switcButton)
    SwitchButton switcButton;
    @BindView(R.id.list_show)
    ImageView list_show;
    @BindView(R.id.title_tx)
    TextView title_tx;
    @BindView(R.id.whole_search)
    LinearLayout whole_search;


    Unbinder unbinder;
    private List<String> tabs = new ArrayList<>();

    private int orderType = 2;
    private int sortType = 0;
    private int page = 1;
    private int pageSize = 10;
    private boolean hasNext = true;
    @BindView(R.id.n_v_refreshLayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @BindView(R.id.n_v_refreshLayout2)
    TwinklingRefreshLayout mTwinklingRefreshLayout2;
    private boolean isLoadMore = false;
    private boolean isFlash = false;
    private String groupId;
    private String Keyword;
    private int type;//1:最上面的分类列表，2：导航栏的分类列表，3：快速导航的分类列表，8：搜索，9：限时抢购
    private ProductSumAdapter3 mSumAdapter;
    private ProductSumAdapter2 productSumAdapter2;
    private boolean isListShow = false;
    private boolean isQuan = false;
    private String youquan = "0";

    public static void startProductSumActivity2(Activity activity, String groupId, int type, String title) {
        Intent intent = new Intent(activity, ProductSumActivity2.class);
        intent.putExtra(Constants.INTENT_KEY_ID, groupId);
        intent.putExtra(Constants.INTENT_KEY_TITLE, title);
        intent.putExtra(Constants.INTENT_KEY_TYPE, type);
        activity.startActivityForResult(intent, 1);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_product;
    }

    @Override
    public void init() {
        title_tx.setText(getIntent().getStringExtra(Constants.INTENT_KEY_TITLE));


        groupId = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
        type = getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, 1);
        Keyword = getIntent().getStringExtra(Constants.INTENT_KEY_TITLE);


        showLoading();

        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(ProductSumActivity2.this));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isFlash = true;
                page = 1;
                mSumAdapter.cleanList();
                productSumAdapter2.cleanList();
                getListData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasNext) {
                    isLoadMore = true;
                    page++;
                    getListData();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });
        mTwinklingRefreshLayout2.setHeaderView(new RefreshHeadView(ProductSumActivity2.this));

        mTwinklingRefreshLayout2.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isFlash = true;
                page = 1;
                mSumAdapter.cleanList();
                productSumAdapter2.cleanList();
                getListData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasNext) {
                    isLoadMore = true;
                    page++;
                    getListData();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                    mTwinklingRefreshLayout2.finishLoadmore();
                }
            }
        });

        tablayout.setTabMode(TabLayout.MODE_FIXED);

        tabs.add("综合");
        tabs.add("销量");
        tabs.add("价格");


        for (int i = 0; i < tabs.size(); i++) {
            View v = getLayoutInflater().inflate(R.layout.item_tab_view, null);
            TabViewHolder holder = new TabViewHolder(v);
            v.setTag(holder);
            holder.tv.setText(tabs.get(i));
            holder.tv.setTextColor(getResources().getColor(R.color.home_select_color));
            if (i == 0) {
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                holder.im.setImageResource(R.mipmap.se_down);
                holder.im.setTag(true);
            } else {
                holder.im.setImageResource(R.mipmap.search_lable);
            }
            tablayout.addTab(tablayout.newTab().setCustomView(v));
        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                sortType = tab.getPosition();
                page = 1;
                orderType = 2;//1升2降
                mSumAdapter.cleanList();
                productSumAdapter2.cleanList();
                if (mTwinklingRefreshLayout.getVisibility() == View.VISIBLE) {
                    mTwinklingRefreshLayout.startRefresh();
                }
                if (mTwinklingRefreshLayout2.getVisibility() == View.VISIBLE) {
                    mTwinklingRefreshLayout2.startRefresh();
                }
                TabViewHolder holder = (TabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                if (tab.getPosition() == 0) {
                    holder.im.setImageResource(R.mipmap.se_down);
                } else {
                    holder.im.setImageResource(R.mipmap.search_down);
                }
                holder.im.setTag(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabViewHolder holder = (TabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.home_select_color));
                if (tab.getPosition() == 0) {
                    holder.im.setImageResource(R.mipmap.se_down);
                } else {
                    holder.im.setImageResource(R.mipmap.search_lable);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mSumAdapter.cleanList();
                productSumAdapter2.cleanList();
                page = 1;
                TabViewHolder holder = (TabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                if (tab.getPosition() == 0) {
                    orderType = 1;
                    holder.im.setImageResource(R.mipmap.se_down);
                    holder.im.setTag(false);
                } else {
                    if ((boolean) holder.im.getTag()) {
                        orderType = 1;
                        holder.im.setImageResource(R.mipmap.search_up);
                        holder.im.setTag(false);
                    } else {
                        orderType = 2;
                        holder.im.setImageResource(R.mipmap.search_down);
                        holder.im.setTag(true);
                    }
                }
                if (mTwinklingRefreshLayout.getVisibility() == View.VISIBLE) {
                    mTwinklingRefreshLayout.startRefresh();
                }
                if (mTwinklingRefreshLayout2.getVisibility() == View.VISIBLE) {
                    mTwinklingRefreshLayout2.startRefresh();
                }
            }
        });

        rcl.setLayoutManager(new GridLayoutManager(ProductSumActivity2.this, 2));
        mSumAdapter = new ProductSumAdapter3(ProductSumActivity2.this);
        rcl.setAdapter(mSumAdapter);

        rc2.setLayoutManager(new LinearLayoutManager(ProductSumActivity2.this));
        productSumAdapter2 = new ProductSumAdapter2(ProductSumActivity2.this);
        rc2.setAdapter(productSumAdapter2);

        mNulldata.setVisibility(View.VISIBLE);

        mSumAdapter.setOnClickProductSumItem(new ProductSumAdapter3.onClickProductSumItem() {
            @Override
            public void onItem(int position, SearchData.DataBean bean) {
                Intent intent = new Intent(ProductSumActivity2.this, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, bean.getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, bean.getCommision());
                intent.putExtra("DETAILSKEYWORD", Keyword);
                intent.putExtra("POSITION", position);
                intent.putExtra("SEARCH", true);
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);
                startActivity(intent);
            }
        });

        productSumAdapter2.setOnClickProductSumItem(new ProductSumAdapter2.onClickProductSumItem2() {
            @Override
            public void onItem(int position, SearchData.DataBean bean) {
                Intent intent = new Intent(ProductSumActivity2.this, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, bean.getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, bean.getCommision());
                intent.putExtra("DETAILSKEYWORD", Keyword);
                intent.putExtra("POSITION", position);
                intent.putExtra("SEARCH", true);
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);
                startActivity(intent);
            }
        });

        switcButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    mSumAdapter.cleanList();
                    productSumAdapter2.cleanList();
                    isQuan = true;
                    youquan = "1";
                    getListData();
                } else {
                    mSumAdapter.cleanList();
                    productSumAdapter2.cleanList();
                    isQuan = false;
                    youquan = "0";
                    getListData();
                }
                mTwinklingRefreshLayout.startRefresh();
                mTwinklingRefreshLayout2.startRefresh();
            }
        });
        getListData();

        Intent searchConten = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("result", getIntent().getStringExtra(Constants.INTENT_KEY_TITLE));
        setResult(3, searchConten.putExtras(bundle));
    }


    @OnClick({R.id.toolbar_back, R.id.list_show, R.id.input_content_text, R.id.whole_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
            case R.id.input_content_text:
            case R.id.whole_search:
                finish();
                break;
            case R.id.list_show:
                if (isListShow) {
                    mTwinklingRefreshLayout.setVisibility(View.GONE);
                    mTwinklingRefreshLayout2.setVisibility(View.VISIBLE);
                    rcl.setVisibility(View.GONE);
                    rc2.setVisibility(View.VISIBLE);
                    isListShow = !isListShow;
                    list_show.setImageDrawable(getResources().getDrawable(R.drawable.seatch_change1));
                } else {
                    mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
                    mTwinklingRefreshLayout2.setVisibility(View.GONE);
                    rcl.setVisibility(View.VISIBLE);
                    rc2.setVisibility(View.GONE);
                    isListShow = !isListShow;
                    list_show.setImageDrawable(getResources().getDrawable(R.drawable.change2));
                }
                break;
        }
    }


    private void getListData() {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("content", groupId);//分组ID
        map.put("orderType", String.valueOf(orderType));//升降序
        map.put("sortType", String.valueOf(sortType));//排序规则
        map.put("youquan ", youquan);//是否有券 1有券 其他值无券
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null
                && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        RetrofitApi.request(ProductSumActivity2.this, RetrofitApi.createApi(Api.class).KeywordSearch(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        SearchData searchData = new Gson().fromJson(data, SearchData.class);
                        if (searchData != null && searchData.getData() != null && searchData.getData().size() > 0) {
                            mSumAdapter.addList(searchData.getData());
                            productSumAdapter2.addList(searchData.getData());
                            mNulldata.setVisibility(View.GONE);
                        }

                        if (isFlash) {
                            mTwinklingRefreshLayout.finishRefreshing();
                            mTwinklingRefreshLayout2.finishRefreshing();
                            isFlash = false;
                        }
                        if (isLoadMore) {
                            mTwinklingRefreshLayout.finishLoadmore();
                            mTwinklingRefreshLayout2.finishLoadmore();
                            isLoadMore = false;
                        }

                        hiddenLoadingView();
                        rc2.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNotNetWork() {
                        hiddenLoadingView();
                        if (isFlash) {
                            mTwinklingRefreshLayout.finishRefreshing();
                            mTwinklingRefreshLayout2.finishRefreshing();
                            isFlash = false;
                        }
                        if (isLoadMore) {
                            mTwinklingRefreshLayout.finishLoadmore();
                            mTwinklingRefreshLayout2.finishLoadmore();
                            isLoadMore = false;
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        hiddenLoadingView();
                        if (isFlash) {
                            mTwinklingRefreshLayout.finishRefreshing();
                            mTwinklingRefreshLayout2.finishRefreshing();
                            isFlash = false;
                        }
                        if (isLoadMore) {
                            mTwinklingRefreshLayout.finishLoadmore();
                            mTwinklingRefreshLayout2.finishLoadmore();
                            isLoadMore = false;
                        }
                    }
                });


    }


    private class TabViewHolder {

        TextView tv;
        ImageView im;

        TabViewHolder(View v) {
            tv = v.findViewById(R.id.textView);
            im = v.findViewById(R.id.imageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
