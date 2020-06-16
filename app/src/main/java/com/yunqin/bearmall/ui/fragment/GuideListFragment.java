package com.yunqin.bearmall.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.GuideWithVideoAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.GuideArticleList;
import com.yunqin.bearmall.bean.GuideWithVideoBean;
import com.yunqin.bearmall.util.NetUtils;
import com.yunqin.bearmall.video.CustomVideo;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;
import io.reactivex.Observable;

public class GuideListFragment extends BaseFragment {

    public static GuideListFragment getInstance(int position) {
        GuideListFragment guideListFragment = new GuideListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        guideListFragment.setArguments(bundle);
        return guideListFragment;
    }


    private int position;
    private int pageIndex = 1;
    private boolean isloadMore;
    private List<GuideArticleList> datas;
    private GuideWithVideoAdapter adapter;

    @BindView(R.id.guide_list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;


    @Override
    public int layoutId() {
        return R.layout.fragment_guide_list;
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }


    @Override
    public void init() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("POSITION");
        }
        datas = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_guide_item_line));
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        refreshLayout.setHeaderView(new RefreshHeadView(getContext()));
        refreshLayout.setBottomView(new RefreshBottomView(getContext()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                isloadMore = false;
                pageIndex = 1;
                loadData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isloadMore = true;
                loadData();
            }
        });
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {

                CustomVideo video = view.findViewById(R.id.guide_video);

                if (video != null) {
                    Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {

                    case RecyclerView.SCROLL_STATE_IDLE:
                        autoPlayVideo(recyclerView);
                        break;

                }
            }
        });

        refreshLayout.startRefresh();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            autoPlayVideo(recyclerView);
        } else {
            Jzvd.releaseAllVideos();
        }
    }

    public void outterAutoPlay() {
        autoPlayVideo(recyclerView);
    }

    private void autoPlayVideo(RecyclerView view) {
        if (getContext() == null) {
            return;
        }
        if (!NetUtils.isWifi(getContext())) {
            return;
        }
        if (view == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        int visibleCount = lastVisibleItem - firstVisibleItem;
        for (int i = 0; i <= visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.guide_video) != null) {
                CustomVideo videoPlayerStandard1 = view.getChildAt(i).findViewById(R.id.guide_video);
                Rect rect = new Rect();
                //获取当前view 的 位置
                videoPlayerStandard1.getLocalVisibleRect(rect);
                int videoheight3 = videoPlayerStandard1.getHeight();
                //当播放器完全显示出来
                if (rect.top == 0 && rect.bottom == videoheight3) {
                    //播放状态为正常或错误状态去播放视频
                    if (videoPlayerStandard1.currentState == CustomVideo.CURRENT_STATE_NORMAL || videoPlayerStandard1.currentState == CustomVideo.CURRENT_STATE_ERROR) {
                        //主动去调用控件的点击事件播放视频
                        videoPlayerStandard1.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        Jzvd.releaseAllVideos();
    }


    private void loadData() {

        if (getUserVisibleHint())
            showLoading();

        Map<String, String> params = new HashMap<>();
        params.put("page_size", "10");
        params.put("page_number", pageIndex + "");
        if (position != 0) {
            if (position == 1) {
                params.put("type", "1");
            } else {
                params.put("type", "0");
            }
        }

        Observable<String> observable = RetrofitApi.createApi(Api.class).getArticleList(params);
        RetrofitApi.request(getContext(), observable, new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                hiddenLoadingView();
                if (isloadMore) {
                    refreshLayout.finishLoadmore();
                } else {
                    refreshLayout.finishRefreshing();
                }

                GuideWithVideoBean response = new Gson().fromJson(data, GuideWithVideoBean.class);
                if (response.isSuccess()) {
                    GuideWithVideoBean.DataBean dataBean = response.getData();
                    if (dataBean.getHas_more() == 1) {
                        pageIndex++;
                        refreshLayout.setEnableLoadmore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                    }
                    List<GuideArticleList> lists = dataBean.getGuideArticleList();
                    if (adapter == null) {
                        datas.addAll(lists);
                        adapter = new GuideWithVideoAdapter(getActivity(), datas);
                        recyclerView.setAdapter(adapter);
                    } else {
                        if (isloadMore) {
                            datas.addAll(lists);
                        } else {
                            datas.clear();
                            datas.addAll(lists);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (getUserVisibleHint()) {
                                autoPlayVideo(recyclerView);
                            }

                        }
                    }, 300);
                }

            }

            @Override
            public void onNotNetWork() {

                hiddenLoadingView();
                if (isloadMore) {
                    refreshLayout.finishLoadmore();
                } else {
                    refreshLayout.finishRefreshing();
                }
                if (adapter == null) {
                    adapter = new GuideWithVideoAdapter(getActivity(), datas);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFail(Throwable e) {

                hiddenLoadingView();
                if (isloadMore) {
                    refreshLayout.finishLoadmore();
                } else {
                    refreshLayout.finishRefreshing();
                }
                if (adapter == null) {
                    adapter = new GuideWithVideoAdapter(getContext(), datas);
                    recyclerView.setAdapter(adapter);
                }

            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
