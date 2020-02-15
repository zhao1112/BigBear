package com.newversions.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.IAdvClick;
import com.newversions.InviteFriendActivity;
import com.newversions.MemberMallActivity;
import com.newversions.util.SharedPreferencesManager;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.LoanBean;
import com.yunqin.bearmall.ui.activity.BargainFreeActivity;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.DailyTasksActivity;
import com.yunqin.bearmall.ui.activity.LoanActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.ZanWeiKaiFangActivity;
import com.yunqin.bearmall.ui.activity.ZeorExchangeActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.util.ToastUtils;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;


import butterknife.BindView;

/**
 * Create By Master
 * On 2019/1/3 16:13
 */
public class NewVersionHomeFragment extends BaseFragment implements NewVersionHomeContract.View, NewVersionHomeAdapter.OnItemClickListener {

    @BindView(R.id.n_v_recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.n_v_refreshLayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @BindView(R.id.n_v_not_net)
    View mNoNetView;

    private boolean hasNext = false;
    private NewVersionHomeAdapter mNewVersionHomeAdapter;
    private NewVersionHomeContract.Presenter mPresenter;

    @Override
    public int layoutId() {
        return R.layout.new_version_home_layout;
    }

    @Override
    public void init() {

        mPresenter = new NewVersionHomePresenter(getActivity(), this);
        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
//        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        mTwinklingRefreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasNext) {
                    mPresenter.onLoadMore();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });

        mNewVersionHomeAdapter = new NewVersionHomeAdapter(getActivity());
        mNewVersionHomeAdapter.setOnItemClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mNewVersionHomeAdapter.getItemColumnCount(position);
            }
        });
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mNewVersionHomeAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();


                if (isSlidingToLast && lastVisibleItem == (totalItemCount - 1)) {
                    mPresenter.onLoadMore();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });
        mPresenter.init();
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
//        mNoNetView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshFinish() {
        mTwinklingRefreshLayout.finishRefreshing();
    }

    @Override
    public void onLoadMoreFinish() {
        mTwinklingRefreshLayout.finishLoadmore();
    }

    @Override
    public void onHasMore(boolean hasMore) {
        this.hasNext = hasMore;
        if (hasMore) {
            mTwinklingRefreshLayout.setEnableLoadmore(false);
        } else {
            mTwinklingRefreshLayout.setEnableLoadmore(true);
        }
    }

    @Override
    public void attachData(HomeBean homeBean) {
        mNoNetView.setVisibility(View.GONE);
        mNewVersionHomeAdapter.setData(homeBean);
    }

    @Override
    public void attachAddData(HomeBean homeBean) {
        mNewVersionHomeAdapter.addData(homeBean);
    }

    @Override
    public void attachBannerData(NewHomeAd homeAd) {
        mNewVersionHomeAdapter.setBannerData(homeAd);
    }

    @Override
    public void loanData(String json) {
        // TODO: 2019/4/18 我要借钱
        LoanBean loanBean = new Gson().fromJson(json,LoanBean.class);
        LoanActivity.startLoanActivity(getActivity(),loanBean.getData().getLinkSite(),"我要借钱");
    }

    @Override
    public void loanError() {
        ToastUtils.showToast(getActivity(),"加载失败");
    }


    @Override
    public void onItemClick(View view) {

    }

    @Override
    public void onHomeChildClick(View view) {
        switch (view.getId()) {
            case R.id.n_v_home_1:
                // TODO 充值中心
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(getActivity());
                    return;
                }
                startActivity(new Intent(getActivity(), ChargeActivity.class));
                break;
            case R.id.n_v_home_2:
                // TODO 糖果0元兑
                startActivity(new Intent(getActivity(), ZeorExchangeActivity.class));
                break;
            case R.id.n_v_home_3:
                // TODO 会员商城
                StarActivityUtil.starActivity(getActivity(), MemberMallActivity.class);
                break;
            case R.id.n_v_home_4:
                // TODO 邀请赚赏金
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(getActivity());
                    return;
                }

                InviteFriendActivity.startActivity(getActivity());
//                StarActivityUtil.starActivity(getActivity(), VipCenterActivity.class);
                break;
            case R.id.n_v_home_5:
                // TODO 砍价随意拿
                startActivity(new Intent(getActivity(), BargainFreeActivity.class));
                break;
            case R.id.n_v_home_6:
                // TODO 每日任务
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(getActivity());
                    return;
                }

                DailyTasksActivity.starActivity(getActivity());
                break;
            case R.id.n_v_home_7:
                // TODO 我要借钱
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(getActivity());
                    return;
                }
                mPresenter.getLoanData();
                break;
//                Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
            case R.id.n_v_home_8:
                // TODO 游戏中心
                startActivity(new Intent(getActivity(), ZanWeiKaiFangActivity.class));
//                Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.n_v_home_image_1:
                NewHomeAd.DataBean.AdMobileCrossList1Bean abc = (NewHomeAd.DataBean.AdMobileCrossList1Bean) ((IImageView) view).getITag();
                advClick(abc.getType(), abc.getSkipType(), (long) abc.getSource_id());
                break;
            case R.id.n_v_home_image_2:
                NewHomeAd.DataBean.AdMobileMidListBean abc1 = (NewHomeAd.DataBean.AdMobileMidListBean) ((IImageView) view).getITag();
                advClick(abc1.getType(), abc1.getSkipType(), (long) abc1.getSource_id());
                break;
            case R.id.n_v_home_image_3:
                NewHomeAd.DataBean.AdMobileMidListBean abc2 = (NewHomeAd.DataBean.AdMobileMidListBean) ((IImageView) view).getITag();
                advClick(abc2.getType(), abc2.getSkipType(), (long) abc2.getSource_id());
                break;
            case R.id.n_v_home_image_4:
                NewHomeAd.DataBean.AdMobileMidListBean abc3 = (NewHomeAd.DataBean.AdMobileMidListBean) ((IImageView) view).getITag();
                advClick(abc3.getType(), abc3.getSkipType(), (long) abc3.getSource_id());
                break;
            case R.id.n_v_home_image_5:
                NewHomeAd.DataBean.AdMobileMidListBean abc4 = (NewHomeAd.DataBean.AdMobileMidListBean) ((IImageView) view).getITag();
                advClick(abc4.getType(), abc4.getSkipType(), (long) abc4.getSource_id());
                break;
            case R.id.n_v_tjw_img:
                NewHomeAd.DataBean.AdMobileCrossList2Bean abc5 = (NewHomeAd.DataBean.AdMobileCrossList2Bean) ((IImageView) view).getITag();
                advClick(abc5.getType(), abc5.getSkipType(), (long) abc5.getSource_id());
                break;
            default:
                break;
        }
    }


    private void advClick(int type, int skipType, long sourceId) {
        IAdvClick.click(getActivity(), type, skipType, sourceId,null);
    }


}
