package com.newversions.tbk.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbcoupon.adapter.SearchListHorizontalAdapter;
import com.bbcoupon.adapter.SearchListVerticalAdapter;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductSumAdapter2;
import com.yunqin.bearmall.adapter.ProductSumAdapter3;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SearchData;
import com.yunqin.bearmall.widget.RefreshHeadView;
import com.yunqin.bearmall.widget.SwitchButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 根据类别查看商品
 */
public class ProductSumActivity2 extends BaseActivity implements RequestContract.RequestView {


    @BindView(R.id.input_content_text)
    LinearLayout input_content_text;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.recycle_horizontal)
    RecyclerView recycle_horizontal;
    @BindView(R.id.recycle_vertical)
    RecyclerView recycle_vertical;
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
    @BindView(R.id.refresh_horizontal)
    TwinklingRefreshLayout refresh_horizontal;
    @BindView(R.id.refresh_vertical)
    TwinklingRefreshLayout refresh_vertical;
    @BindView(R.id.list_recycle_vertical)
    RecyclerView list_recycle_vertical;
    @BindView(R.id.list_refresh_vertical)
    TwinklingRefreshLayout list_refresh_vertical;
    @BindView(R.id.list_recycle_horizontal)
    RecyclerView list_recycle_horizontal;
    @BindView(R.id.list_refresh_horizontal)
    TwinklingRefreshLayout list_refresh_horizontal;


    private List<String> tabs = new ArrayList<>();
    private int orderType = 2;
    private int sortType = 0;
    private int page = 1;
    private String pageSize = "10";
    private String groupId, Search_Type;
    private String Keyword;
    private int type;//1:最上面的分类列表，2：导航栏的分类列表，3：快速导航的分类列表，8：搜索，9：限时抢购
    private ProductSumAdapter3 mSumAdapter;
    private ProductSumAdapter2 productSumAdapter2;
    private boolean isListShow = false;
    private String youquan = "0";
    private RequestPresenter presenter;
    private SearchListVerticalAdapter listVerticalAdapter;
    private SearchListHorizontalAdapter listHorizontalAdapter;
    private boolean isList = false;


    public static void startProductSumActivity2(Activity activity, String groupId, int type, String title, String search_type) {
        Intent intent = new Intent(activity, ProductSumActivity2.class);
        intent.putExtra(Constants.INTENT_KEY_ID, groupId);
        intent.putExtra(Constants.INTENT_KEY_TITLE, title);
        intent.putExtra(Constants.INTENT_KEY_TYPE, type);
        intent.putExtra(ConstantUtil.Search_Type, search_type);
        activity.startActivityForResult(intent, 1);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_product;
    }

    @Override
    public void init() {

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        title_tx.setText(getIntent().getStringExtra(Constants.INTENT_KEY_TITLE));
        groupId = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
        type = getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, 1);
        Keyword = getIntent().getStringExtra(Constants.INTENT_KEY_TITLE);
        Search_Type = getIntent().getStringExtra(ConstantUtil.Search_Type);

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
                if (!isList) {
                    mSumAdapter.cleanList();
                    productSumAdapter2.cleanList();
                } else {
                    listVerticalAdapter.cleanList();
                    listHorizontalAdapter.cleanList();
                }
                if (refresh_horizontal.getVisibility() == View.VISIBLE) {
                    refresh_horizontal.startRefresh();
                }
                if (refresh_vertical.getVisibility() == View.VISIBLE) {
                    refresh_vertical.startRefresh();
                }
                if (list_refresh_vertical.getVisibility() == View.VISIBLE) {
                    list_refresh_vertical.startRefresh();
                }
                if (list_refresh_horizontal.getVisibility() == View.VISIBLE) {
                    list_refresh_horizontal.startRefresh();
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
                if (!isList) {
                    mSumAdapter.cleanList();
                    productSumAdapter2.cleanList();
                } else {
                    listVerticalAdapter.cleanList();
                    listHorizontalAdapter.cleanList();
                }
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
                if (refresh_horizontal.getVisibility() == View.VISIBLE) {
                    refresh_horizontal.startRefresh();
                }
                if (refresh_vertical.getVisibility() == View.VISIBLE) {
                    refresh_vertical.startRefresh();
                }
                if (list_refresh_vertical.getVisibility() == View.VISIBLE) {
                    list_refresh_vertical.startRefresh();
                }
                if (list_refresh_horizontal.getVisibility() == View.VISIBLE) {
                    list_refresh_horizontal.startRefresh();
                }
            }
        });

        //搜索Horizontal
        refresh_horizontal.setHeaderView(new RefreshHeadView(ProductSumActivity2.this));
        refresh_horizontal.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                mSumAdapter.cleanList();
                productSumAdapter2.cleanList();
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });
        recycle_horizontal.setLayoutManager(new GridLayoutManager(ProductSumActivity2.this, 2));
        mSumAdapter = new ProductSumAdapter3(ProductSumActivity2.this);
        recycle_horizontal.setAdapter(mSumAdapter);
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
        //搜索Vertical
        refresh_vertical.setHeaderView(new RefreshHeadView(ProductSumActivity2.this));
        refresh_vertical.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                mSumAdapter.cleanList();
                productSumAdapter2.cleanList();
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });
        recycle_vertical.setLayoutManager(new LinearLayoutManager(ProductSumActivity2.this));
        productSumAdapter2 = new ProductSumAdapter2(ProductSumActivity2.this);
        recycle_vertical.setAdapter(productSumAdapter2);
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
        //推荐Vertical
        list_refresh_vertical.setHeaderView(new RefreshHeadView(ProductSumActivity2.this));
        list_refresh_vertical.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                listVerticalAdapter.cleanList();
                listHorizontalAdapter.cleanList();
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });
        list_recycle_vertical.setLayoutManager(new LinearLayoutManager(ProductSumActivity2.this));
        listVerticalAdapter = new SearchListVerticalAdapter(ProductSumActivity2.this);
        list_recycle_vertical.setAdapter(listVerticalAdapter);
        listVerticalAdapter.setOnClickProductSumItem(new SearchListVerticalAdapter.onClickVerticalItem() {
            @Override
            public void onListItem(int position, SearchData.ListBean bean) {
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
        //推荐Horizontal
        list_refresh_horizontal.setHeaderView(new RefreshHeadView(ProductSumActivity2.this));
        list_refresh_horizontal.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                listVerticalAdapter.cleanList();
                listHorizontalAdapter.cleanList();
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });
        listHorizontalAdapter = new SearchListHorizontalAdapter(ProductSumActivity2.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductSumActivity2.this, 10);
        list_recycle_horizontal.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return listHorizontalAdapter.getItemColumnCount(position);
            }
        });
        list_recycle_horizontal.setAdapter(listHorizontalAdapter);
        listHorizontalAdapter.setOnClickHorizontalItem(new SearchListHorizontalAdapter.onClickHorizontalItem() {
            @Override
            public void onListItem(int position, SearchData.ListBean bean) {
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
        //有券没券
        switcButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    if (!isList) {
                        mSumAdapter.cleanList();
                        productSumAdapter2.cleanList();
                    } else {
                        listVerticalAdapter.cleanList();
                        listHorizontalAdapter.cleanList();
                    }
                    youquan = "1";
                } else {
                    if (!isList) {
                        mSumAdapter.cleanList();
                        productSumAdapter2.cleanList();
                    } else {
                        listVerticalAdapter.cleanList();
                        listHorizontalAdapter.cleanList();
                    }
                    youquan = "0";
                }
                if (refresh_horizontal.getVisibility() == View.VISIBLE) {
                    refresh_horizontal.startRefresh();
                }
                if (refresh_vertical.getVisibility() == View.VISIBLE) {
                    refresh_vertical.startRefresh();
                }
                if (list_refresh_vertical.getVisibility() == View.VISIBLE) {
                    list_refresh_vertical.startRefresh();
                }
                if (list_refresh_horizontal.getVisibility() == View.VISIBLE) {
                    list_refresh_horizontal.startRefresh();
                }
            }
        });

        mNulldata.setVisibility(View.VISIBLE);

        showLoading();

        getData();

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
                    if (!isList) {
                        refresh_horizontal.setVisibility(View.GONE);
                        refresh_vertical.setVisibility(View.VISIBLE);
                        recycle_horizontal.setVisibility(View.GONE);
                        recycle_vertical.setVisibility(View.VISIBLE);
                    } else {
                        list_refresh_vertical.setVisibility(View.VISIBLE);
                        list_recycle_vertical.setVisibility(View.VISIBLE);
                        list_refresh_horizontal.setVisibility(View.GONE);
                        list_recycle_horizontal.setVisibility(View.GONE);
                    }
                    isListShow = !isListShow;
                    list_show.setImageDrawable(getResources().getDrawable(R.drawable.seatch_change1));
                } else {
                    if (!isList) {
                        refresh_horizontal.setVisibility(View.VISIBLE);
                        refresh_vertical.setVisibility(View.GONE);
                        recycle_horizontal.setVisibility(View.VISIBLE);
                        recycle_vertical.setVisibility(View.GONE);
                    } else {
                        list_refresh_vertical.setVisibility(View.GONE);
                        list_recycle_vertical.setVisibility(View.GONE);
                        list_refresh_horizontal.setVisibility(View.VISIBLE);
                        list_recycle_horizontal.setVisibility(View.VISIBLE);
                    }
                    isListShow = !isListShow;
                    list_show.setImageDrawable(getResources().getDrawable(R.drawable.change2));
                }
                break;
        }
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
        presenter.setUntying(this);
    }

    private void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("content", groupId);//分组ID
        map.put("orderType", String.valueOf(orderType));//升降序
        map.put("sortType", String.valueOf(sortType));//排序规则
        map.put("youquan ", youquan);//是否有券 1有券 其他值无券
        map.put("page", String.valueOf(page));
        map.put("pageSize", pageSize);
        map.put("type", Search_Type);
        if (BearMallAplication.getInstance().getUser() != null
                && BearMallAplication.getInstance().getUser().getData() != null
                && BearMallAplication.getInstance().getUser().getData().getToken() != null
                && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        presenter.onKeywordSearch(ProductSumActivity2.this, map);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof SearchData) {
            SearchData searchData = (SearchData) data;
            if (searchData != null && searchData.getData() != null && searchData.getData().size() > 0) {
                mSumAdapter.addList(searchData.getData());
                productSumAdapter2.addList(searchData.getData());

                isList = false;

                refresh_horizontal.finishRefreshing();
                refresh_vertical.finishRefreshing();
                refresh_horizontal.finishLoadmore();
                refresh_vertical.finishLoadmore();

                mNulldata.setVisibility(View.GONE);
            }
            if (searchData != null && searchData.getList() != null && searchData.getList().size() > 0) {
                listVerticalAdapter.addList(searchData.getList());
                listHorizontalAdapter.addList(searchData.getList());

                isList = true;

                list_refresh_vertical.finishRefreshing();
                list_refresh_vertical.finishLoadmore();
                list_refresh_horizontal.finishRefreshing();
                list_refresh_horizontal.finishLoadmore();
                refresh_vertical.setVisibility(View.GONE);
                if (list_refresh_horizontal.getVisibility() == View.GONE) {
                    if (list_refresh_vertical.getVisibility() == View.GONE){
                        list_refresh_vertical.setVisibility(View.VISIBLE);
                    }
                }
                mNulldata.setVisibility(View.GONE);
            }
        }
        hiddenLoadingView();
    }

    @Override
    public void onNotNetWork() {
        if (!isList) {
            refresh_horizontal.finishRefreshing();
            refresh_vertical.finishRefreshing();
            refresh_horizontal.finishLoadmore();
            refresh_vertical.finishLoadmore();
        } else {
            list_refresh_vertical.finishRefreshing();
            list_refresh_vertical.finishLoadmore();
            list_refresh_horizontal.finishRefreshing();
            list_refresh_horizontal.finishLoadmore();
        }
        hiddenLoadingView();
    }

    @Override
    public void onFail(Throwable e) {
        if (!isList) {
            refresh_horizontal.finishRefreshing();
            refresh_vertical.finishRefreshing();
            refresh_horizontal.finishLoadmore();
            refresh_vertical.finishLoadmore();
        } else {
            list_refresh_vertical.finishRefreshing();
            list_refresh_vertical.finishLoadmore();
            list_refresh_horizontal.finishRefreshing();
            list_refresh_horizontal.finishLoadmore();
        }
        hiddenLoadingView();
    }

}
