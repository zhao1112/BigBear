package com.yunqin.bearmall.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.GuidePagerAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.MessageItemCount;
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DotView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jzvd.Jzvd;

public class GuideWithVideoFragment extends BaseFragment {


    @BindView(R.id.top_xiao_xi)
    ImageView top_xiao_xi;

    @BindView(R.id.guide_pager)
    ViewPager pager;

    @BindView(R.id.guide_tab)
    XTabLayout tabLayout;

    GuidePagerAdapter adapter;
    List<GuideListFragment> fragments;

    @Override
    public int layoutId() {
        return R.layout.fragment_guide_with_video;
    }

    @Override
    public void init() {

        tabLayout.setVisibility(View.VISIBLE);

        fragments = new ArrayList<>();
        fragments.add(GuideListFragment.getInstance(0));
        fragments.add(GuideListFragment.getInstance(1));
        fragments.add(GuideListFragment.getInstance(2));
        adapter = new GuidePagerAdapter(getChildFragmentManager(), fragments);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabsFromPagerAdapter(adapter);


        top_xiao_xi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InformationFragmentActivity.start(getActivity());
            }
        });


    }


    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        autoPlay();
        requestData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Jzvd.releaseAllVideos();
        } else {

            requestData();

            autoPlay();
        }
    }

    private void autoPlay() {

        if (pager.getChildCount() > 0) {
            GuideListFragment fragment = fragments.get(pager.getCurrentItem());
            if (fragment != null)
                fragment.outterAutoPlay();
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }














    @BindView(R.id.dot_view)
    DotView dot_view;

    public void requestData() {
        Map timeMap = new HashMap();
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) != 0l) {
            timeMap.put("lastTime0", (long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) != 0l) {
            timeMap.put("lastTime1", (long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) != 0l) {
            timeMap.put("lastTime2", (long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) != 0l) {
            timeMap.put("lastTime3", (long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) + "");
        }

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getUnreadMessageCount(timeMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                MessageItemCount messageItemCount = new Gson().fromJson(data, MessageItemCount.class);
                int count = messageItemCount.getData().getUnreadMessageCount();
                dot_view.setShowNum(count);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }















}
