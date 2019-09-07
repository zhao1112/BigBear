package com.yunqin.bearmall.ui.fragment.tab;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.AllFragmentAdapter;
import com.yunqin.bearmall.base.LazyFragment;
import com.yunqin.bearmall.eventbus.OrderTypeUpdate;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.ui.activity.MineOrderActivity;
import com.yunqin.bearmall.ui.fragment.contract.TabFragmentContract;
import com.yunqin.bearmall.ui.fragment.presenter.AwaitEvaluatePresenter;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 待评价
 */

public class AwaitEvaluateFragment extends LazyFragment implements TabFragmentContract.UI, AllFragmentAdapter.OnChildClickListener {


    @BindView(R.id.custom_empty_recommend_view)
    CustomRecommendView mCustomRecommendView;

    @BindView(R.id.fragment_all_recycle_view)
    RecyclerView recyclerView;

    private boolean isFirst = true;
    TabFragmentContract.Presenter presenter;


    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;


    // 标志位，标志已经初始化完成。
    private boolean isPrepared;


    @Override
    public int layoutId() {
        return R.layout.fragment_all_order;
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        presenter = new AwaitEvaluatePresenter(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        if (isPrepared && isVisible && !isFirst) {
            Log.e("CHENGPAN", "刷新*待评价*" + "AwaitEvaluateFragment");
            presenter.shuaXin();
        }

        if (isFirst) {
            isFirst = false;
            presenter.start(getActivity(), "4");
        }
        //填充各控件的数据
    }


    @Override
    public void attachAdapter(AllFragmentAdapter adapter) {
        if (adapter.getItemCount() == 0) {
            mCustomRecommendView.setVisibility(View.VISIBLE);
            twinklingRefreshLayout.setVisibility(View.GONE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            mCustomRecommendView.setImageSrc(R.drawable.order_no);
            mCustomRecommendView.setTvContent("你还没有相关的订单");
            mCustomRecommendView.setManager(gridLayoutManager);
            mCustomRecommendView.start(getActivity());
        } else {
            adapter.setOnChildClickListener(this);
            mCustomRecommendView.setVisibility(View.GONE);
            twinklingRefreshLayout.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);


            twinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
            twinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
            twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {


                @Override
                public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                    presenter.shuaXin();
                }

                @Override
                public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                    if (hasMore) {
                        presenter.jiaZaiGengDuo();
                    } else {
                        twinklingRefreshLayout.finishLoadmore();
                    }

                }
            });


        }
    }


    @Override
    public void updateView(AllFragmentAdapter adapter) {
        if (adapter.getItemCount() == 0) {
            mCustomRecommendView.setVisibility(View.VISIBLE);
            twinklingRefreshLayout.setVisibility(View.GONE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            mCustomRecommendView.setImageSrc(R.drawable.order_no);
            mCustomRecommendView.setTvContent("你还没有相关的订单");
            mCustomRecommendView.setManager(gridLayoutManager);
            mCustomRecommendView.start(getActivity());

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
    public void shuaXinFinish() {
        twinklingRefreshLayout.finishRefreshing();
    }

    @Override
    public void jiaZaiGengDuoFinish(boolean isMore) {
        twinklingRefreshLayout.finishLoadmore();
    }

    @Override
    public void quXiao(int index) {

        StringBuffer stringBuffer = new StringBuffer("确认取消订单, ");
        if (presenter.getAdapter() == null) {

        } else {
            try {
                stringBuffer.append("订单号：" + presenter.getAdapter().getData().get(index).getSn());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DialogUtils.tipSearchDialog(getActivity(), 666, stringBuffer.toString(), new JoinZeroCallBack() {
            @Override
            public void sureBtn(int flag) {
                presenter.quXiao(index);
            }

            @Override
            public void canle() {

            }
        });


    }

    @Override
    public void quFuKuan(int index) {
        presenter.quFuKuan(index);
    }

    @Override
    public void zaiCiGouMai(int index) {
        presenter.zaiCiGouMai(index);
    }

    @Override
    public void chaKanWuLiu(int index) {
        presenter.chaKanWuLiu(index);
    }

    @Override
    public void queRenShouHuo(int index) {
        StringBuffer stringBuffer = new StringBuffer("是否确认收货, ");
        if (presenter.getAdapter() == null) {

        } else {
            stringBuffer.append("订单号：" + presenter.getAdapter().getData().get(index).getSn());
        }

        DialogUtils.tipSearchDialog(getActivity(), 666, stringBuffer.toString(), new JoinZeroCallBack() {
            @Override
            public void sureBtn(int flag) {
                presenter.queRenShouHuo(index);
            }

            @Override
            public void canle() {

            }
        });

    }

    @Override
    public void shaiDanPingJia(int index) {
        presenter.shaiDanPingJia(index);
    }

    @Override
    public void shanChuDingDan(int index) {

        StringBuffer stringBuffer = new StringBuffer("确认删除订单, ");
        if (presenter.getAdapter() == null) {

        } else {
            stringBuffer.append("订单号：" + presenter.getAdapter().getData().get(index).getSn());
        }

        DialogUtils.tipSearchDialog(getActivity(), 666, stringBuffer.toString(), new JoinZeroCallBack() {
            @Override
            public void sureBtn(int flag) {
                presenter.shanChuDingDan(index);
            }

            @Override
            public void canle() {

            }
        });


    }

    @Override
    public void shenQingShouHou(int index) {
        presenter.shenQingShouHou(index);
    }

    @Override
    public void chaKanXiangQing(int index, int childIndex) {
        presenter.chaKanXiangQing(index, childIndex);
    }

    @Override
    public void updateView() {
        presenter.shuaXin();
    }

    @BindView(R.id.not_net)
    View view;


    @OnClick({R.id.reset_load_data})
    public void select(View view) {
        presenter.start(getActivity(), "");
    }


    @Override
    public void onNotNetWork() {
        mCustomRecommendView.setVisibility(View.GONE);
        twinklingRefreshLayout.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }


    private boolean hasMore = true;


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
    public void onResume() {
        super.onResume();
        Log.e("CHENGPAN", this.getClass().getSimpleName() + " onResume");
        if (isUpdate) {
            isUpdate = false;
            presenter.shuaXin();
        }
    }

    private boolean isUpdate = false;

    @Subscribe
    public void onUpdate(OrderTypeUpdate update) {
        if (update.getId() == MineOrderActivity.FragmentType.AWAIT_PINGJIA) {
            isUpdate = true;
        }
    }


}