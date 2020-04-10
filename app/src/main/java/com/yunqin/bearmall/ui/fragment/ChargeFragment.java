package com.yunqin.bearmall.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ChargeAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.Charge;
import com.yunqin.bearmall.bean.ChargeResponse;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.activity.ChargeConfirmActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.OpenVipActivity;
import com.yunqin.bearmall.ui.activity.RenewVipActivity;
import com.yunqin.bearmall.ui.activity.VipCenterActivity;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.widget.RecyclerItemDecoration;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChargeFragment extends BaseFragment {

    @BindView(R.id.charge)
    Button buyButton;
    @BindView(R.id.layout_vip)
    View vipContainer;
    @BindView(R.id.layout_no_vip)
    View noVipContainer;
    @BindView(R.id.charge_warn)
    TextView warnView;
    @BindView(R.id.use_time)
    TextView userTimeView;
    @BindView(R.id.recycler_view_charge)
    RecyclerView chargeRecyclerView;
    @BindView(R.id.use_time_no)
    TextView userTimeViewNo;

    private ChargeAdapter adapter;
    private List<Charge> charges;
    private int ticketCount;
    private String mobile;
    private int carrierType;
    private OnGetChargeDataListener onGetChargeDataListener;

    public static ChargeFragment instance() {
        ChargeFragment fragment = new ChargeFragment();
        return fragment;
    }

    public void setOnGetChargeDataListener(OnGetChargeDataListener onGetChargeDataListener) {
        this.onGetChargeDataListener = onGetChargeDataListener;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_charge;
    }

    @Override
    public void init() {

        buyButton.setEnabled(false);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        chargeRecyclerView.setLayoutManager(layoutManager);
        chargeRecyclerView.addItemDecoration(new RecyclerItemDecoration(10, 3));
        chargeRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP && !buyButton.isEnabled()) {
                    buyButton.setEnabled(true);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        if (!TextUtils.isEmpty(mobile)) {
            String s = CommonUtils.validateMobile(mobile);
            if (s.equals("中国移动")) {
                carrierType = 0;
            } else if (s.equals("中国电信")) {
                carrierType = 1;
            } else if (s.equals("中国联通")) {
                carrierType = 2;
            }
        }

    }

    public void loadData() {
        Map<String, String> params = new HashMap<>();
        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).getVirtualRechargeInfo(params),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        Log.e("onSuccess", data );
                        ChargeResponse response = new Gson().fromJson(data, ChargeResponse.class);
                        if (response.isSuccess()) {
                            ChargeResponse.DataBean dataBean = response.getData();
                            if (onGetChargeDataListener != null) {
                                onGetChargeDataListener.onGetData(dataBean.getMobile(), dataBean.getCarrierType());
                            }
//                            carrierType = dataBean.getCarrierType();
                            ticketCount = dataBean.getUsableTicketCount();
                            boolean isMember = dataBean.getIsMs() == 1;
                            if (isMember) {
                                String warn = getString(R.string.charge_warn);
                                warnView.setText(warn);
                                if (ticketCount > 0) {
                                    userTimeView.setText(String.format("本月可用%d次", ticketCount));
                                } else {
                                    userTimeView.setText("本月已用");
                                }
                                vipContainer.setVisibility(View.VISIBLE);
                                noVipContainer.setVisibility(View.GONE);
                            } else {
                                noVipContainer.setVisibility(View.VISIBLE);
                                vipContainer.setVisibility(View.GONE);
                            }
                            charges = dataBean.getList();
                            adapter = new ChargeAdapter(getActivity(), charges);
                            chargeRecyclerView.setAdapter(adapter);
                            adapter.addData(charges);
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

    public void loadData2(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).getVirtualRechargeInfo(params),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        Log.e("onSuccess", data );
                        ChargeResponse response = new Gson().fromJson(data, ChargeResponse.class);
                        if (response.isSuccess()) {
                            ChargeResponse.DataBean dataBean = response.getData();
                            charges = dataBean.getList();
                            adapter = new ChargeAdapter(getActivity(), charges);
                            chargeRecyclerView.setAdapter(adapter);
                            adapter.clearData();
                            adapter.addData(charges);
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


    public void getPhoenNum(String mobile) {
        this.mobile = mobile;
    }


    @OnClick({R.id.charge, R.id.use_time_no})
    public void onViewClick(View view) {
        if (view.getId() == R.id.use_time_no) {
            jump2VipActivity();
        } else {
            if (adapter != null && adapter.getLastSeletIndex() != -1) {
                Charge charge = charges.get(adapter.getLastSeletIndex());
                if (mobile == null || !CommonUtils.isPhoneNumber(mobile)) {
                    return;
                }
                if (!TextUtils.isEmpty(mobile)) {

                    ChargeConfirmActivity.startChargeConfirmActivity(getActivity(), mobile, carrierType, ticketCount, charge);

                    //TODO[话费面额选择]
                    ConstantScUtil.phoneFeeAmountChoose(mobile + "", charge.getTitle(), charge.getPayPrice());
                    //TODO[点击立即充值]
                    ConstantScUtil.phoneFeeSubmit(mobile + "", charge.getTitle(), charge.getPayPrice(), null, null);
                }
            }
        }
    }

    public interface OnGetChargeDataListener {
        void onGetData(String mobile, int carrierType);
    }

    private void jump2VipActivity() {
        UserInfo user = BearMallAplication.getInstance().getUser();
        if (user == null) {
            LoginActivity.starActivity(getActivity());
        } else {
            boolean member = BearMallAplication.getInstance().getUser().getData().getMember().isMember();
            boolean opendMember = BearMallAplication.getInstance().getUser().getData().getMember().isOpendMember();
            Log.i("jump2VipActivity", "jump2VipActivity: " + member);
            Log.i("jump2VipActivity", "jump2VipActivity: " + opendMember);

            if (member || opendMember) {
                RenewVipActivity.startRenewVipActivity(getActivity(), null, null);
            } else {
                OpenVipActivity.startOpenVipActivity(getActivity(), null, null);
            }
        }

    }


}
