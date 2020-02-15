package com.yunqin.bearmall.ui.fragment.tab;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.HomeAd;
import com.yunqin.bearmall.bean.HomeBean;
import com.yunqin.bearmall.ui.activity.BargainFreeActivity;
import com.yunqin.bearmall.ui.activity.BargainFreeDetailActivity;
import com.yunqin.bearmall.ui.activity.DailyTasksActivity;
import com.yunqin.bearmall.ui.activity.SweetSnatchActivity;
import com.yunqin.bearmall.ui.activity.VanguardListPageActivity;
import com.yunqin.bearmall.ui.activity.ZeorExchangeActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;
import com.yunqin.bearmall.ui.fragment.contract.BearRecommendFragmentContract;
import com.yunqin.bearmall.ui.fragment.presenter.BearRecommendPresenter;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 */
public class BearRecommendFragment extends BaseFragment implements BearRecommendFragmentContract.UI,
        View.OnClickListener, MoreTypeRecycleViewAdapter.OnItemClickListener {


    @BindView(R.id.main_recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;

    private BearRecommendFragmentContract.Presenter mPresenter;


    @BindView(R.id.not_net)
    View view;


    @Override
    public int layoutId() {
        return R.layout.fragment_bear_recommend;
    }

    @Override
    public void init() {
        mRecyclerView.setNestedScrollingEnabled(false);

        twinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        twinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mPresenter.shuaXin();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    mPresenter.jiaZaiGengDuo();
                } else {
                    twinklingRefreshLayout.finishLoadmore();
                }

            }
        });
        mPresenter = new BearRecommendPresenter(getActivity(), this);
        mPresenter.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_spell_group:
                //todo 熊波为了测试添加的代码
//                intent = new Intent(getActivity(), BargainFreeShareActivity.class);
//                getActivity().startActivity(intent);
                Intent intent1 = new Intent(getActivity(), ZeorExchangeActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.menu_free_obtain:
                Intent intent = new Intent(getActivity(), BargainFreeActivity.class);
                getActivity().startActivity(intent);
//                intent = new Intent(getActivity(), BargainFreeDetailActivity.class);
//                getActivity().startActivity(intent);
                break;
            case R.id.menu_sweets_snatch:
                StarActivityUtil.starActivity(getActivity(), SweetSnatchActivity.class);
                break;
            case R.id.menu_daily_task:
                DailyTasksActivity.starActivity(getActivity());
                break;
            case R.id.main_image_left:
                goToAdv(0, false);
                break;
            case R.id.main_image_center:
                goToAdv(1, false);
                break;
            case R.id.main_image_right:
                goToAdv(2, false);
                break;
            default:
                break;
        }
    }

    private void goToAdv(int position, boolean isOne) {
        int type;
        int source_id;
        if (isOne) {
            type = initFeaturedFirstHolder1.get(position).getType();
            source_id = initFeaturedFirstHolder1.get(position).getSource_id();
        } else {
            type = initFeaturedFirstHolder3.get(position).getType();
            source_id = initFeaturedFirstHolder3.get(position).getSource_id();
        }

        Intent iIntent = null;
        if (type == 0) {


            iIntent = new Intent(getActivity(), NewProductDetailActivity.class);
            iIntent.putExtra("productId", "" + source_id);
            iIntent.putExtra("sku_id", "");
            startActivity(iIntent);
        } else if (type == 1) {
            // TODO 说明广告  (无用)
        } else if (type == 2) {
            // TODO 导购文章
            String guidelUrl = BuildConfig.BASE_URL + "/view/findGuideArticleDetailPage?guideArticle_id=" + source_id;
            VanguardListPageActivity.startH5Activity(getActivity(), guidelUrl, "导购详情");
        } else if (type == 4) {
            // TODO 会员往期活动
            VanguardListPageActivity.startH5Activity(getActivity(), VanguardListPageActivity.loadUrlActivity + source_id, "活动详情");
        } else if (type == 5) {
            // TODO 0元拼团
            iIntent = new Intent(getActivity(), ZeroMoneyDetailsActivity.class);
            iIntent.putExtra("groupPurchasing_id", source_id + "");
            startActivity(iIntent);
        } else if (type == 6) {
            // TODO 砍价免费拿
            Intent intent = new Intent(getActivity(), BargainFreeDetailActivity.class);
            intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, (long) source_id);
            intent.putExtra(BargainFreeDetailActivity.BARGAIN_IS_ONGOING, false);
            getActivity().startActivity(intent);
        } else if (type == 7) {
            // TODO 糖果夺宝


        } else if (type == 8) {

            Intent intent1 = new Intent(getActivity(), ZeorExchangeActivity.class);
            getActivity().startActivity(intent1);

        } else if (type == 9) {

            Intent intent = new Intent(getActivity(), BargainFreeActivity.class);
            getActivity().startActivity(intent);

        }
    }


    @Override
    public void attachAdapter(final MoreTypeRecycleViewAdapter adapter) {
        view.setVisibility(View.GONE);
        twinklingRefreshLayout.setVisibility(View.VISIBLE);

        adapter.setmOnItemClickLitener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getAdapterType(position);
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(adapter);

    }


    @Override
    public void attachAdapter(MoreTypeRecycleViewAdapter adapter, HomeBean homeBean) {
        adapter.setData(homeBean);
    }

    @Override
    public void initBannerTop(MoreTypeRecycleViewAdapter adapter, List<HomeAd.DataBean.AdBean> lists) {
        adapter.setHeadView0Data(lists);
    }

    @Override
    public void initLinearMenu(MoreTypeRecycleViewAdapter adapter) {
        adapter.setHeaderView1(this);
    }


    private List<HomeAd.DataBean.AdBean> initFeaturedFirstHolder3;

    @Override
    public void initFeaturedFirstHolder3(MoreTypeRecycleViewAdapter adapter, List<HomeAd.DataBean.AdBean> lists) {
        this.initFeaturedFirstHolder3 = lists;
        adapter.setHeaderView2(lists, this);
    }


    private List<HomeAd.DataBean.AdBean> initFeaturedFirstHolder1;

    @Override
    public void initFeaturedFirstHolder1(MoreTypeRecycleViewAdapter adapter, List<HomeAd.DataBean.AdBean> lists) {
        this.initFeaturedFirstHolder1 = lists;
        adapter.setHeaderView3(lists, position -> goToAdv(position, true));
    }

    @Override
    public void shuaXinFinish() {
        twinklingRefreshLayout.finishRefreshing();
    }


    private boolean hasMore = true;

    @Override
    public void jiaZaiGengDuoFinish() {
        twinklingRefreshLayout.finishLoadmore();
    }


    @Override
    public void onHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        if (hasMore) {
            twinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        } else {
            twinklingRefreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        }
    }

    @Override
    public void showLoad() {
        showLoading();
    }

    @Override
    public void hideLoad() {
        hiddenLoadingView();
    }

    @Override
    public void onNotNetWork() {
        view.setVisibility(View.VISIBLE);
        twinklingRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View v, int postion) {
    }


    @OnClick({R.id.reset_load_data})
    public void select(View view) {
        mPresenter.start();
    }


}
