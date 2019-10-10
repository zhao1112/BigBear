package com.newversions.tbk.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.entity.GoodsEntity;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductSumFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.rcl)
    RecyclerView rcl;
    private List<String> tabs = new ArrayList<>();

    private int orderType = 2;
    private int sortType = 1;
    private int page = 1;
    private int pageSize = 10;
    private boolean hasNext = true;
    private HomeAdapter homeAdapter;
    private List<GoodsEntity.CommodityBean> mList = new ArrayList<>();
    @BindView(R.id.n_v_refreshLayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    private boolean isLoadMore = false;
    private boolean isFlash = false;
    private String groupId;
    private String Keyword;
    private int selectTabIndex;
    private int type;//1:最上面的分类列表，2：导航栏的分类列表，3：快速导航的分类列表，8：搜索，9：限时抢购

    public static ProductSumFragment getInstance(String goodsId, int type,String Keyword) {
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
        groupId = getArguments().getString(Constants.INTENT_KEY_ID);
        type = getArguments().getInt(Constants.INTENT_KEY_TYPE, 1);
        Keyword = getArguments().getString("KEYWORD");
        if (type == 8) {//搜索
            tablayout.setVisibility(View.GONE);
        } else {//首页
            tablayout.setVisibility(View.VISIBLE);
        }
        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
//        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
//        mTwinklingRefreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                mPresenter.onRefresh();
                isFlash = true;
                page = 1;
                mList.clear();
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
                mList.clear();
                homeAdapter.notifyItemRangeRemoved(0, mList.size());
                getData();
                TabViewHolder holder = (TabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                if (type == 9) {
                    selectTabIndex = tab.getPosition();
                    return;
                }
//                if (tab.getPosition() != tabs.size() - 1) {
//                    return;
//                }
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
                mList.clear();
                homeAdapter.notifyItemRangeRemoved(0, mList.size());
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
        homeAdapter = new HomeAdapter(R.layout.item_priduct_sum, mList);
        homeAdapter.openLoadAnimation();
        homeAdapter.setUpFetchEnable(true);
        // rlv.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL));
        homeAdapter.bindToRecyclerView(rcl);
        rcl.setAdapter(homeAdapter);
        homeAdapter.setEmptyView(R.layout.no_video_layout);

        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (type == 9) {
                    if (!canBuyCheck()) {
                        showToast("抢购时间还未到。请等待");
                        return;
                    }
                }
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getItemId() + "");
//                intent.putExtra(Constants.INTENT_KEY_URL, mList.get(position).getOutIcon());
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, mList.get(position).getCommision());
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
        // TODO: 2019/7/17 0017 获取数据
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("id", groupId);//分组ID
        map.put("type", String.valueOf(type));//入口类型
        map.put("orderType", String.valueOf(orderType));//升降序
        map.put("sortType", String.valueOf(sortType));//排序规则
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getGoodsList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                GoodsEntity goodsEntity = new Gson().fromJson(data, GoodsEntity.class);
                mList.addAll(goodsEntity.getCommodity() == null ? new ArrayList<GoodsEntity.CommodityBean>() : goodsEntity.getCommodity());
                homeAdapter.notifyDataSetChanged();
                hiddenLoadingView();
                rcl.setVisibility(View.VISIBLE);
                if (isFlash) {
                    mTwinklingRefreshLayout.finishRefreshing();
                    isFlash = false;
                }
                if (isLoadMore) {
                    mTwinklingRefreshLayout.finishLoadmore();
                    isLoadMore = false;
                }
                //TODO[搜索按钮]
                //sebsorsSearch();
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
                rcl.setVisibility(View.VISIBLE);
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
                rcl.setVisibility(View.VISIBLE);
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

    class HomeAdapter extends BaseQuickAdapter<GoodsEntity.CommodityBean, BaseViewHolder> {
        public HomeAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsEntity.CommodityBean item) {
            helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(getActivity(), item.getTmall() == 1 ? R.mipmap.icon_tmall :
                    R.mipmap.icon_taobao1, item.getName()));
            helper.setText(R.id.item_home_pro_quan, "券¥" + item.getCouponAmount());
            helper.setText(R.id.item_home_pro_yuanjia, "¥" + item.getPrice());
            helper.setText(R.id.item_home_pro_quanhoujia, "" + item.getDiscountPrice());
            helper.setText(R.id.tv_commision, "预估返：" + item.getCommision() + "元");
            ((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
                    Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
            Glide.with(getContext()).load(item.getOutIcon()).into((ImageView) helper.getView(R.id.item_home_pro_image));
            helper.setText(R.id.item_home_xiaoliang, item.getSellNum() + "人已购");
            helper.setText(R.id.item_seller_name, item.getSellerName());
            helper.setImageResource(R.id.im_tmall, item.getTmall() == 1 ? R.mipmap.icon_tmall : R.mipmap.icon_taobao1);
        }
    }

    //神策搜索按钮统计
    public void sebsorsSearch() {
        if (type == 8) {
            ConstantScUtil.sensorsTrack("searchButtonClick", null);
        }
    }
}
