package com.newversions.tbk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.entity.GoodsEntity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductSumAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

public class ProductSumFragment extends BaseFragment {
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
    private int type;//1:最上面的分类列表，2：导航栏的分类列表，3：快速导航的分类列表，8：搜索，9：限时抢购
    private ProductSumAdapter mSumAdapter;

    public static ProductSumFragment getInstance(String goodsId, int type, String Keyword) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_KEY_ID, goodsId);
        bundle.putInt(Constants.INTENT_KEY_TYPE, type);
        bundle.putString("KEYWORD", Keyword);
        ProductSumFragment fragment = new ProductSumFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int layoutId() {
        return R.layout.goods_list;
    }

    @Override
    public void init() {
        showLoading();
        groupId = getArguments().getString(Constants.INTENT_KEY_ID);
        type = getArguments().getInt(Constants.INTENT_KEY_TYPE, 1);
        Keyword = getArguments().getString("KEYWORD");
        if (type == 8) {//搜索
            tablayout.setVisibility(View.GONE);
        } else {//首页
            tablayout.setVisibility(View.VISIBLE);
        }
        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
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
                if (hasNext) {
                    isLoadMore = true;
                    page++;
                    getData();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        if (type == 9) {
            tabs.add("0:00\n" + getCanBuyStr(0));
            tabs.add("10:00\n" + getCanBuyStr(1));
            tabs.add("14:00\n" + getCanBuyStr(2));
            tabs.add("20:00\n" + getCanBuyStr(3));
        } else if (type == 3 || type == 5) {
            tabs.add("综合");
            tabs.add("销量");
            tabs.add("券额");
            tabs.add("券后价");
        } else {
            tabs.add("销量");
            tabs.add("最新");
            tabs.add("券额");
            tabs.add("券后价");
        }

        for (int i = 0; i < tabs.size(); i++) {
            View v = getLayoutInflater().inflate(R.layout.item_tab_view, null);
            TabViewHolder holder = new TabViewHolder(v);
            v.setTag(holder);
            holder.tv.setText(tabs.get(i));
            holder.tv.setTextColor(getResources().getColor(R.color.home_select_color));
            if (type == 9) {
                if (i == 0)
                    holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                if (i == 0) {
                    holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                    holder.im.setImageResource(R.mipmap.icon_sort01);
                    holder.im.setTag(true);
                } else {
                    holder.im.setImageResource(R.mipmap.icon_arrangement);
                }
            }
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
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                if (type == 9) {
                    selectTabIndex = tab.getPosition();
                    return;
                }
                holder.im.setImageResource(R.mipmap.icon_sort01);
                holder.im.setTag(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabViewHolder holder = (TabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.home_select_color));
                if (type == 9) return;
                holder.im.setImageResource(R.mipmap.icon_arrangement);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (type == 9) return;
                page = 1;
                mSumAdapter.cleanList();
                TabViewHolder holder = (TabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                if ((boolean) holder.im.getTag()) {
                    orderType = 1;
                    holder.im.setImageResource(R.mipmap.icon_sort00);
                    holder.im.setTag(false);
                } else {
                    orderType = 2;
                    holder.im.setImageResource(R.mipmap.icon_sort01);
                    holder.im.setTag(true);
                }
                getData();
            }
        });

        rcl.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mSumAdapter = new ProductSumAdapter(getActivity());
        rcl.setAdapter(mSumAdapter);
        mNulldata.setVisibility(View.VISIBLE);
        mSumAdapter.setOnClickProductSumItem(new ProductSumAdapter.onClickProductSumItem() {
            @Override
            public void onItem(int position, GoodsEntity.CommodityBean bean) {
                if (type == 9) {
                    if (!canBuyCheck()) {
                        showToast("抢购时间还未到。请等待");
                        return;
                    }
                }
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, bean.getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, bean.getCommision());
                intent.putExtra("DETAILSKEYWORD", Keyword);
                intent.putExtra("POSITION", position);
                if (type == 8) {
                    intent.putExtra("SEARCH", true);
                } else {
                    intent.putExtra("SEARCH", false);
                }
                startActivity(intent);
            }
        });

        if (type == 9) {
            selectTabIndex = getCanButIndex();
            tablayout.getTabAt(selectTabIndex).select();
        } else {
            getData();
        }
    }

    private String getCanBuyStr(int target) {
        int canBuyIndex = getCanButIndex();
        if (canBuyIndex == target) {
            return "疯抢中";
        }
        if (canBuyIndex > target) {
            return "已开始";
        }
        return "即将开始";
    }

    private int getCanButIndex() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //0101420
        if (hour < 10) {
            return 0;
        } else if (hour < 14) {
            return 1;
        } else if (hour < 20) {
            return 2;
        } else {
            return 3;
        }
    }

    private boolean canBuyCheck() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            return selectTabIndex <= 0;
        } else if (hour < 14) {
            return selectTabIndex <= 1;
        } else if (hour < 20) {
            return selectTabIndex <= 2;
        }
        return true;
    }

    private void getData() {
        //TODO[搜索按钮]
        ConstantScUtil.sebsorsSearch(type);
        // TODO: 2019/7/17 0017 获取数据
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("id", groupId);//分组ID
        map.put("type", String.valueOf(type));//入口类型
        map.put("orderType", String.valueOf(orderType));//升降序
        map.put("sortType", String.valueOf(sortType));//排序规则
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        Log.i("ConstantScUtil", "type ->" + String.valueOf(type));
        Log.i("ConstantScUtil", "orderType ->" + String.valueOf(orderType));
        Log.i("ConstantScUtil", "sortType ->" + String.valueOf(sortType));
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getGoodsList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                GoodsEntity goodsEntity = new Gson().fromJson(data, GoodsEntity.class);
                if (goodsEntity != null && goodsEntity.getCommodity() != null && goodsEntity.getCommodity().size() > 0) {
                    mSumAdapter.addList(goodsEntity.getCommodity());
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
                rcl.setVisibility(View.VISIBLE);
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
        ImageView im;

        TabViewHolder(View v) {
            tv = v.findViewById(R.id.textView);
            im = v.findViewById(R.id.imageView);
        }
    }

}
