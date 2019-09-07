package com.yunqin.bearmall.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.MoneyRewardActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.HomeMessageBean;
import com.yunqin.bearmall.eventbus.GetMessageEvent;
import com.yunqin.bearmall.ui.activity.BCMessageActivity;
import com.yunqin.bearmall.ui.activity.DealMessageActivity;
import com.yunqin.bearmall.ui.activity.EventMessageActivity;
import com.yunqin.bearmall.ui.activity.SystemMessageActivity;
import com.yunqin.bearmall.util.SharedPreferencesHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 */
public class InformationFragment extends BaseFragment {


    @BindView(R.id.toolbar_back)
    ImageView backView;

    @BindView(R.id.toolbar_title)
    TextView titleView;

    @BindView(R.id.info1)
    TextView info1;

    @BindView(R.id.time1)
    TextView time1;

    @BindView(R.id.info2)
    TextView info2;

    @BindView(R.id.time2)
    TextView time2;

    @BindView(R.id.info3)
    TextView info3;

    @BindView(R.id.time3)
    TextView time3;

    @BindView(R.id.info4)
    TextView info4;

    @BindView(R.id.time4)
    TextView time4;

    @BindView(R.id.info5)
    TextView info5;

    @BindView(R.id.time5)
    TextView time5;


    @BindView(R.id.info_layout_1)
    LinearLayout info_layout_1;

    @BindView(R.id.info_layout_2)
    LinearLayout info_layout_2;

    @BindView(R.id.info_layout_3)
    LinearLayout info_layout_3;

    @BindView(R.id.info_layout_4)
    LinearLayout info_layout_4;

    @BindView(R.id.info_layout_5)
    LinearLayout info_layout_5;

    @BindView(R.id.number_unread_1)
    TextView number_unread_1;

    @BindView(R.id.number_unread_2)
    TextView number_unread_2;

    @BindView(R.id.number_unread_3)
    TextView number_unread_3;

    @BindView(R.id.number_unread_4)
    TextView number_unread_4;

    @BindView(R.id.number_unread_5)
    TextView number_unread_5;

    @Override
    public int layoutId() {
        return R.layout.fragment_infromation;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);

        backView.setVisibility(View.VISIBLE);
        titleView.setText("消息");

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gETReceive(GetMessageEvent getMessageEvent) {
        getLaseMessageData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLaseMessageData();
    }

    private void getLaseMessageData() {
        Map<String, String> map = new HashMap();

        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) != 0l) {
            map.put("lastTime0", (long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) != 0l) {
            map.put("lastTime1", (long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) != 0l) {
            map.put("lastTime2", (long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) != 0l) {
            map.put("lastTime3", (long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) + "");
        }

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getMainPushMessageInfo(map), new RetrofitApi.IResponseListener() {

            @Override
            public void onSuccess(String data) {
                try {
                    HomeMessageBean homeMessageBean = new Gson().fromJson(data, HomeMessageBean.class);
                    if (homeMessageBean.getData().getMessage_0() != null) {
                        info_layout_1.setVisibility(View.VISIBLE);
                        time1.setText(homeMessageBean.getData().getMessage_0().getCreatedDate());
                        info1.setText(homeMessageBean.getData().getMessage_0().getTitle());

                        setUnReadNumber(number_unread_1, homeMessageBean.getData().getMessage_0().getUnreadMessageCount());

                    } else {
                        info_layout_1.setVisibility(View.GONE);
                    }

                    if (homeMessageBean.getData().getMessage_1() != null) {
                        info_layout_2.setVisibility(View.VISIBLE);
                        time2.setText(homeMessageBean.getData().getMessage_1().getCreatedDate());
                        info2.setText(homeMessageBean.getData().getMessage_1().getTitle());

                        setUnReadNumber(number_unread_2, homeMessageBean.getData().getMessage_1().getUnreadMessageCount());

                    } else {
                        info_layout_2.setVisibility(View.GONE);
                    }


                    if (homeMessageBean.getData().getMessage_2() != null) {
                        info_layout_3.setVisibility(View.VISIBLE);
                        time3.setText(homeMessageBean.getData().getMessage_2().getCreatedDate());
                        info3.setText(homeMessageBean.getData().getMessage_2().getTitle());

                        setUnReadNumber(number_unread_3, homeMessageBean.getData().getMessage_2().getUnreadMessageCount());

                    } else {
                        info_layout_3.setVisibility(View.GONE);
                    }


                    if (homeMessageBean.getData().getMessage_3() != null) {
                        info_layout_4.setVisibility(View.VISIBLE);
                        time4.setText(homeMessageBean.getData().getMessage_3().getCreatedDate());
                        info4.setText(homeMessageBean.getData().getMessage_3().getTitle());

                        setUnReadNumber(number_unread_4, homeMessageBean.getData().getMessage_3().getUnreadMessageCount());

                    } else {
                        info_layout_4.setVisibility(View.GONE);
                    }

                    if (homeMessageBean.getData().getMessage_4() != null) {
                        info_layout_5.setVisibility(View.VISIBLE);
                        time5.setText(homeMessageBean.getData().getMessage_4().getCreatedDate());
                        info5.setText(homeMessageBean.getData().getMessage_4().getTitle());

                        setUnReadNumber(number_unread_5, homeMessageBean.getData().getMessage_4().getUnreadMessageCount());

                    } else {
                        info_layout_5.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }

    @OnClick({R.id.bc_layout, R.id.deal_layout, R.id.system_layout, R.id.event_layout, R.id.event_layout_shangjin})
    void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.event_layout_shangjin:

                MoneyRewardActivity.startActivity(getActivity());

                break;


            case R.id.bc_layout:

                SharedPreferencesHelper.put(getActivity(), "lastTime0", System.currentTimeMillis());

                Intent intent = new Intent(getContext(), BCMessageActivity.class);

                startActivity(intent);

                break;

            case R.id.deal_layout:

                SharedPreferencesHelper.put(getActivity(), "lastTime1", System.currentTimeMillis());

                intent = new Intent(getContext(), DealMessageActivity.class);

                startActivity(intent);

                break;

            case R.id.system_layout:

                SharedPreferencesHelper.put(getActivity(), "lastTime2", System.currentTimeMillis());

                intent = new Intent(getContext(), SystemMessageActivity.class);

                startActivity(intent);

                break;

            case R.id.event_layout:

                SharedPreferencesHelper.put(getActivity(), "lastTime3", System.currentTimeMillis());

                intent = new Intent(getContext(), EventMessageActivity.class);

                startActivity(intent);

                break;

        }

    }


    public void setUnReadNumber(TextView view, int unReadNumebr) {
        if (unReadNumebr > 0) {
            view.setVisibility(View.VISIBLE);
            view.setText(unReadNumebr + "");
        } else {
            view.setVisibility(View.GONE);
        }
    }

}
