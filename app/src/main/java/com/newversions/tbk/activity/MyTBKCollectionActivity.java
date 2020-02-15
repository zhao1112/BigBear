package com.newversions.tbk.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newversions.tbk.entity.TBKCollectionEntity;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTBKCollectionActivity extends BaseActivity {
    @BindView(R.id.rcl)
    RecyclerView rlv;
    @BindView(R.id.not_net_group)
    LinearLayout notNetGroup;
    private List<TBKHomeEntity.CommodityBean> mList = new ArrayList();
    private HomeAdapter homeAdapter;
    private int page = 1;
    private boolean hasMore = true;

    @Override
    public int layoutId() {
        return R.layout.activity_tbk_collection;
    }

    @Override
    public void init() {
        rlv.setLayoutManager(new GridLayoutManager(this, 1));
        homeAdapter = new HomeAdapter(R.layout.item_priduct_sum2, mList);
        homeAdapter.openLoadAnimation();

        homeAdapter.setUpFetchEnable(true);
        // rlv.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL));
        homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getData();
            }
        }, rlv);
        rlv.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(CollectAndReferActivity.this, GoodsDetailActivity.class);
//                intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getId());
//                intent.putExtra(Constants.INTENT_KEY_URL, mList.get(position).getIconUrl());
//                startActivity(intent);
                GoodsDetailActivity.startGoodsDetailActivity(MyTBKCollectionActivity.this, mList.get(position).getItemId());
            }
        });
        homeAdapter.setEmptyView(R.layout.empty_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        mList.clear();
        getData();
    }

    @OnClick({R.id.reset_load_data, R.id.toolbar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reset_load_data:
                getData();
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    class HomeAdapter extends BaseQuickAdapter<TBKHomeEntity.CommodityBean, BaseViewHolder> {
        public HomeAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TBKHomeEntity.CommodityBean item) {
            if (StringUtils.isEmpty(item.getSellerName())) {
                helper.setGone(R.id.tv_goods_shop, false);
            }
            helper.setText(R.id.tv_goods_shop, StringUtils.addImageLabel(mContext, item.getTmall() == 1 ? R.mipmap.icon_tmall :
                    R.mipmap.icon_taobao1, item.getSellerName()));
            helper.setText(R.id.item_home_pro_title, item.getName());
            helper.setText(R.id.item_home_pro_quan, "券¥" + (int) item.getCouponAmount() + "");
            helper.setText(R.id.item_home_pro_yuanjia, "¥" + item.getPrice());
            helper.setText(R.id.item_home_pro_quanhoujia, "" + item.getDiscountPrice());
            helper.setText(R.id.tv_commision, "预估返：" + item.getCommision() + "元");
            ((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
                    Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(item.getOutIcon()).into((ImageView) helper.getView(R.id.item_home_pro_image));
            helper.setText(R.id.item_home_xiaoliang, "" + item.getSellNum() + "人已购");
//			helper.getView(R.id.iv_collct).setVisibility(View.VISIBLE);

        }
    }

    private void getData() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getTBKCollection(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                notNetGroup.setVisibility(View.GONE);
                TBKCollectionEntity tbkCollectionEntity = new Gson().fromJson(data, TBKCollectionEntity.class);
                mList.addAll(tbkCollectionEntity.getCommodity());
                if (page == 1) {
                    homeAdapter.setNewData(mList);
                } else {
                    homeAdapter.addData(tbkCollectionEntity.getCommodity());
                }
                if (page < tbkCollectionEntity.getPages()) {
                    hasMore = true;
                    homeAdapter.loadMoreComplete();
                } else {
                    hasMore = false;
                    homeAdapter.loadMoreEnd();
                }

            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                notNetGroup.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                homeAdapter.loadMoreComplete();
            }
        });
    }
}
