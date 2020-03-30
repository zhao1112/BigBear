package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.entity.GoodsEntity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.LimiteAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import mlxy.utils.T;

public class LimitedActivity extends BaseActivity {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.rcl)
    RecyclerView rcl;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    Unbinder unbinder;
    private List<String> tabs = new ArrayList<>();

    private int orderType = 2;
    private int sortType = 1;
    private int page = 1;
    private int pageSize = 10;
    private boolean hasNext = true;
    @BindView(R.id.n_v_refreshLayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    private boolean isLoadMore = false;
    private boolean isFlash = false;
    private String groupId;
    private String Keyword;
    private int selectTabIndex;
    private int type = 9;//1:最上面的分类列表，2：导航栏的分类列表，3：快速导航的分类列表，8：搜索，9：限时抢购
    private LimiteAdapter mSumAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_limited;
    }

    public static void openLimitedActivity(Activity context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }

    @Override
    public void init() {
        setTranslucentStatus();
        showLoading();
        groupId = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
        Keyword = getIntent().getStringExtra("KEYWORD");

        Log.e("KEYWORD", "init: " + groupId);
        Log.e("KEYWORD", "init: " + type);
        Log.e("KEYWORD", "init: " + Keyword);


        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(LimitedActivity.this));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isFlash = true;
                page = 1;
                mSumAdapter.cleanList();
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isLoadMore = true;
                page++;
                getData();
            }
        });

        tablayout.setTabMode(TabLayout.MODE_FIXED);

        tabs.add("0:00");
        tabs.add("10:00");
        tabs.add("12:00");
        tabs.add("15:00");
        tabs.add("19:00");


        for (int i = 0; i < tabs.size(); i++) {
            View v = getLayoutInflater().inflate(R.layout.item_tab_view2, null);
            TabViewHolder holder = new TabViewHolder(v);
            v.setTag(holder);
            holder.tv.setText(tabs.get(i));
            holder.tv2.setText(getCanBuyStr(i));
            holder.tv.setTextColor(getResources().getColor(R.color.wxz));
            holder.tv2.setTextColor(getResources().getColor(R.color.wxz));
            holder.tv2.setBackground(getResources().getDrawable(R.drawable.app_x_tet2));
            tablayout.addTab(tablayout.newTab().setCustomView(v));
        }


        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                sortType = tab.getPosition() + 1;//最新。。。。。。
                page = 1;
                orderType = 2;//1升2降
                mSumAdapter.cleanList();
                getData();
                TabViewHolder holder = (TabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.white));
                holder.tv2.setTextColor(getResources().getColor(R.color.xz));
                holder.tv2.setBackground(getResources().getDrawable(R.drawable.app_x_tet));
                selectTabIndex = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabViewHolder holder = (TabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.wxz));
                holder.tv2.setTextColor(getResources().getColor(R.color.wxz));
                holder.tv2.setBackground(getResources().getDrawable(R.drawable.app_x_tet2));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rcl.setLayoutManager(new LinearLayoutManager(LimitedActivity.this));
        mSumAdapter = new LimiteAdapter(LimitedActivity.this);
        rcl.setAdapter(mSumAdapter);
        mNulldata.setVisibility(View.VISIBLE);
        mSumAdapter.setOnClickProductSumItem(new LimiteAdapter.onClickProductSumItem() {
            @Override
            public void onItem(int position, GoodsEntity.CommodityBean bean) {
                if (type == 9) {
                    if (!canBuyCheck()) {
                        return;
                    }
                }
                Intent intent = new Intent(LimitedActivity.this, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, bean.getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, bean.getCommision());
                intent.putExtra("DETAILSKEYWORD", Keyword);
                intent.putExtra("POSITION", position);
                intent.putExtra("SEARCH", false);
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);

                startActivity(intent);
            }
        });

        selectTabIndex = getCanButIndex();
        tablayout.getTabAt(selectTabIndex).select();

    }

    @OnClick({R.id.back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private String getCanBuyStr(int target) {
        int canBuyIndex = getCanButIndex();
        if (canBuyIndex == target) {
            return "抢购中";
        }
        if (canBuyIndex > target) {
            return "已开抢";
        }
        return "即将开抢";
    }

    private int getCanButIndex() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //0101420
        if (hour < 10) {
            return 0;
        } else if (hour < 12) {
            return 1;
        } else if (hour < 15) {
            return 2;
        } else if (hour < 19) {
            return 3;
        } else {
            return 4;
        }
    }

    private boolean canBuyCheck() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            return selectTabIndex <= 0;
        } else if (hour < 12) {
            return selectTabIndex <= 1;
        } else if (hour < 15) {
            return selectTabIndex <= 2;
        } else if (hour < 19) {
            return selectTabIndex <= 3;
        }
        return true;
    }

    private void getData() {
        //TODO[搜索按钮]
        ConstantScUtil.sebsorsSearch(type);
        // TODO: 2019/7/17 0017 获取数据
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(type));//入口类型
        map.put("orderType", String.valueOf(orderType));//升降序
        map.put("sortType", String.valueOf(sortType));//排序规则
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        Log.i("ConstantScUtil", "type ->" + String.valueOf(type));
        Log.i("ConstantScUtil", "orderType ->" + String.valueOf(orderType));
        Log.i("ConstantScUtil", "sortType ->" + String.valueOf(sortType));
        RetrofitApi.request(LimitedActivity.this, RetrofitApi.createApi(Api.class).getGoodsList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                GoodsEntity goodsEntity = new Gson().fromJson(data, GoodsEntity.class);
                if (goodsEntity != null && goodsEntity.getCommodity() != null && goodsEntity.getCommodity().size() > 0) {
                    mSumAdapter.addList(goodsEntity.getCommodity(), canBuyCheck());
                    mNulldata.setVisibility(View.GONE);
                }

                if (isFlash) {
                    mTwinklingRefreshLayout.finishRefreshing();
                    isFlash = false;
                }
                if (isLoadMore) {
                    mTwinklingRefreshLayout.finishLoadmore();
                    isLoadMore = false;
                }

                hiddenLoadingView();
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                if (isFlash) {
                    mTwinklingRefreshLayout.finishRefreshing();
                    isFlash = false;
                }
                if (isLoadMore) {
                    mTwinklingRefreshLayout.finishLoadmore();
                    isLoadMore = false;
                }
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                if (isFlash) {
                    mTwinklingRefreshLayout.finishRefreshing();
                    isFlash = false;
                }
                if (isLoadMore) {
                    mTwinklingRefreshLayout.finishLoadmore();
                    isLoadMore = false;
                }
            }
        });


    }

    private class TabViewHolder {

        TextView tv;
        TextView tv2;

        TabViewHolder(View v) {
            tv = v.findViewById(R.id.textView);
            tv2 = v.findViewById(R.id.textView_2);
        }
    }

}
