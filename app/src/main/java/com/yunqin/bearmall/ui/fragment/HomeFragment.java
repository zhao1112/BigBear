package com.yunqin.bearmall.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.newversions.IAdvClick;
import com.newversions.util.SharedPreferencesManager;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.TabTitleAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.bean.MessageItemCount;
import com.yunqin.bearmall.bean.PopBean;
import com.yunqin.bearmall.eventbus.GetMessageEvent;
import com.yunqin.bearmall.eventbus.PopWindowEvent;
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.ui.activity.MenuActicity;
import com.yunqin.bearmall.ui.activity.SearchActivity;
import com.yunqin.bearmall.ui.fragment.contract.HomeContract;
import com.yunqin.bearmall.ui.fragment.presenter.HomePresenter;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.DotView;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import permison.PermissonUtil;
import permison.listener.PermissionListener;

/**
 * @author Master
 */
public class HomeFragment extends BaseFragment implements HomeContract.UI {


    HomeContract.Presenter mPresenter;

    @BindView(R.id.xtablelayout)
    XTabLayout xtablelayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.menu)
    RelativeLayout menu;


    private HighlightButton mHighlightButton;


    private TabTitleAdapter adapter;


    @BindView(R.id.not_net)
    View view;


    @BindView(R.id.dot_view)
    DotView dot_view;


    @Override
    public int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);


        showLoading();
        mPresenter = new HomePresenter(this);
        mPresenter.start(getActivity());
    }

    @OnClick({R.id.menu, R.id.home_search, R.id.reset_load_data, R.id.xiao_xi})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                StarActivityUtil.starActivity(getActivity(), MenuActicity.class);
                break;
            case R.id.home_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("memberType", "0");
                startActivity(intent);
                //TODO[搜索]
                break;
            case R.id.reset_load_data:
                EventBus.getDefault().post(new GetMessageEvent());
                mPresenter.start(getActivity());
                break;
            case R.id.xiao_xi:
                InformationFragmentActivity.start(getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void attachChannel(Channel channel) {
        hiddenLoadingView();

        if (channel == null) {
            return;
        }

        view.setVisibility(View.GONE);
        xtablelayout.setVisibility(View.VISIBLE);

        xtablelayout.addTab(xtablelayout.newTab().setText("大熊精选"));
        for (int i = 0; i < channel.getData().size(); i++) {
            xtablelayout.addTab(xtablelayout.newTab().setText(channel.getData().get(i).getName()));
        }
        adapter = new TabTitleAdapter(getActivity(), getFragmentManager(), channel.getData(), false);
        viewpager.removeAllViews();
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(3);
        //初始化显示第一个页面
        xtablelayout.setupWithViewPager(viewpager);
        xtablelayout.setTabsFromPagerAdapter(adapter);

        xtablelayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    mPresenter.initAdData(getActivity());
                }
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        view.setVisibility(View.VISIBLE);
        xtablelayout.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        requestData();
        if (isVisibility) {
            mPresenter.initAdData(getActivity());
        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        isVisibility = !hidden;
        super.onHiddenChanged(hidden);
        if (!hidden) {

            requestData();

            setDarkStatusIcon(true);
            int vvv = xtablelayout.getSelectedTabPosition();
            if (vvv == 0) {
                mPresenter.initAdData(getActivity());
            }
        }
    }

    private boolean isVisibility = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(PopWindowEvent event) {
        if (isVisibility) {
            int vvv = xtablelayout.getSelectedTabPosition();
            if (vvv == 0) {
                mPresenter.initAdData(getActivity());
            }
        }
    }


    @Override
    public void resultAdData(String key, String value, String data) {
        PopBean popBean = new Gson().fromJson(data, PopBean.class);
        if (popBean.getData() != null && popBean.getData().getPopupAd() != null
                && popBean.getData().getPopupAd().getImg() != null) {
            SharedPreferencesManager.setParam(getActivity(), key, value);

            new ICustomDialog.Builder(getActivity())
                    // 设置布局
                    .setLayoutResId(R.layout.dialog_first_ad)
                    // 点击空白是否消失
                    .setCanceledOnTouchOutside(false)
                    // 点击返回键是否消失
                    .setCancelable(true)
                    // 设置Dialog的绝对位置
                    .setDialogPosition(Gravity.CENTER)
                    // 设置自定义动画
                    .setAnimationResId(R.style.CenterAnimation)
                    .setWindow(true)
                    // 设置监听ID
                    .setListenedItems(new int[]{R.id.ad_img, R.id.ad_close})
                    .setImageViewResource(R.id.ad_img, popBean.getData().getPopupAd().getImg())
                    // 设置回掉
                    .setOnDialogItemClickListener((thisDialog, clickView) -> {
                        thisDialog.dismiss();
                        if (clickView.getId() == R.id.ad_img) {
                            IAdvClick.click(getActivity(), popBean.getData().getPopupAd().getType(),
                                    popBean.getData().getPopupAd().getSkipType(), popBean.getData().getPopupAd().getSource_id(),
                                    popBean.getData().getPopupAd().getAdUrl());
                        }
                    }).build().show();
        }
    }


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

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getUnreadMessageCount(timeMap),
                new RetrofitApi.IResponseListener() {
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