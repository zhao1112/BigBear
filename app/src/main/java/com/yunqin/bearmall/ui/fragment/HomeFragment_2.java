package com.yunqin.bearmall.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.newversions.IAdvClick;
import com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2;
import com.newversions.util.SharedPreferencesManager;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.HomeTabTitleAdapter;
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
import com.yunqin.bearmall.util.CommonUtil;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.HomeScrollView;
import com.yunqin.bearmall.widget.OpenGoodsDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2020/3/20
 */
public class HomeFragment_2 extends BaseFragment implements HomeContract.UI {


    @BindView(R.id.ll_title)
    RelativeLayout title;
    @BindView(R.id.xtablelayout_2)
    XTabLayout xtablelayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.home_image)
    ImageView home_image;

    private HomeContract.Presenter mPresenter;
    private HomeTabTitleAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_home_2;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        mPresenter = new HomePresenter(this);
        mPresenter.start(getActivity());
    }


    @Override
    public void attachChannel(Channel channel) {

        if (channel == null) {
            return;
        }

        xtablelayout.setVisibility(View.VISIBLE);

        xtablelayout.addTab(xtablelayout.newTab().setText("大熊精选"));
        for (int i = 0; i < channel.getData().size(); i++) {
            xtablelayout.addTab(xtablelayout.newTab().setText(channel.getData().get(i).getName()));
        }
        adapter = new HomeTabTitleAdapter(getActivity(), getFragmentManager(), channel.getData(), home_image);
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

    @OnClick({R.id.home_search, R.id.xiaoxi})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("memberType", "0");
                startActivity(intent);
                //TODO[搜索]
                break;
            case R.id.xiaoxi:
                InformationFragmentActivity.start(getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
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
                            //TODO[点击广告]
                            ConstantScUtil.showAd();
                        }
                        showNotice();
                    }).build().show();
        }
    }

    private void showNotice() {
        //检查通知权限
        if (!CommonUtils.isNotificationEnabled(getActivity())) {
            OpenGoodsDetail.lightoff(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_notice, null);
            PopupWindow mPopupWindow = new PopupWindow();
            mPopupWindow.setContentView(view);
            mPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            // 设置背景图片， 必须设置，不然动画没作用
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setAnimationStyle(R.style.CenterAnimation);
            mPopupWindow.setFocusable(true);
            // 设置点击popupwindow外屏幕其它地方消失
            mPopupWindow.setOutsideTouchable(true);
            // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
            mPopupWindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            view.findViewById(R.id.no_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                    OpenGoodsDetail.lighton(getActivity());
                }
            });
            view.findViewById(R.id.no_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                    CommonUtil.toSelfSetting(getActivity());
                    OpenGoodsDetail.lighton(getActivity());
                }
            });
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    OpenGoodsDetail.lighton(getActivity());
                }
            });
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
