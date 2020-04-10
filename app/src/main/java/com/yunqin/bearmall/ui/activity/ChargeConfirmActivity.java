package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.Charge;
import com.yunqin.bearmall.bean.Coupon;
import com.yunqin.bearmall.bean.VirtualCucalateResponse;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChargeConfirmActivity extends BaseActivity {


    public static void startChargeConfirmActivity(Context context, String mobile, int carrierType, int usableTicketCount, Charge charge) {

        Intent intent = new Intent(context, ChargeConfirmActivity.class);
        intent.putExtra("mobile", mobile);
        intent.putExtra("carrierType", carrierType);
        intent.putExtra("usableTicketCount", usableTicketCount);
        intent.putExtra("charge", charge);
        context.startActivity(intent);

    }

    @BindView(R.id.phone_number)
    TextView phoneNumView;
    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.title)
    TextView goodsTitleView;
    @BindView(R.id.price)
    TextView goodsPriceView;
    @BindView(R.id.coupon_name)
    TextView couponNameView;
    @BindView(R.id.goods_price)
    TextView goodsSalePriceView;
    @BindView(R.id.save_price)
    TextView savePriceView;
    @BindView(R.id.real_price)
    TextView realPriceView;
    @BindView(R.id.opeator_image)
    ImageView oprateImage;

    private Charge charge;
    private Coupon coupon;
    private String finalAmount;
    private String mobile;
    private static final int COUPON_REQUEST_CODE = 1;

    @Override
    public int layoutId() {
        return R.layout.activity_charge_confirm;
    }

    @Override
    public void init() {


        titleView.setText("提交订单");
        mobile = getIntent().getStringExtra("mobile");
        int carrierType = getIntent().getIntExtra("carrierType", 0);
        int usableTicketCount = getIntent().getIntExtra("usableTicketCount", 0);
        charge = getIntent().getParcelableExtra("charge");

        phoneNumView.setText(String.format("流量充值号码：%s", mobile));
        int imageID;
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
        switch (carrierType) {
            case 0:
            default:
                imageID = R.drawable.icon_china_mobile;
                break;

            case 1:
                imageID = R.drawable.icon_telecom;
                break;

            case 2:
                imageID = R.drawable.icon_china_union;
                break;
        }
        oprateImage.setImageResource(imageID);

        goodsTitleView.setText(charge.getTitle());
        goodsPriceView.setText("¥" + charge.getPayPrice());
        goodsSalePriceView.setText("¥" + charge.getPayPrice());
        realPriceView.setText("¥" + charge.getPayPrice());
        finalAmount = charge.getPayPrice();

        initCoupon();
        calculateOrderAmount();

    }


    private void initCoupon() {

        Map<String, String> mHashMap = new HashMap<>();

        mHashMap.put("queryType", "0");
        mHashMap.put("ticketType", "1");

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberTicketDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                int mCouponCount = jsonObject1.optInt("totalCount");

                if (mCouponCount > 0) {
                    couponNameView.setText(String.format("%d张可用", mCouponCount));
                } else {
                    couponNameView.setText("暂无可用");
                }
            }

            @Override
            public void onNotNetWork() {
                couponNameView.setText("暂无可用");
            }

            @Override
            public void onFail(Throwable e) {
                couponNameView.setText("暂无可用");
            }
        });


    }


    private void calculateOrderAmount() {

        showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("usersTicketLog_id", coupon == null ? "" : coupon.getUsersTicket_id() + "");
        params.put("virtualRechargeProudct_id", charge.getVirtualRechargeProudct_id() + "");

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).calculateVirtualOrderAmount(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                hiddenLoadingView();
                VirtualCucalateResponse response = new Gson().fromJson(data, VirtualCucalateResponse.class);
                if (response.isSuccess()) {

                    VirtualCucalateResponse.DataBean dataBean = response.getData();
                    savePriceView.setText("¥" + dataBean.getDiscountAmount());
                    goodsSalePriceView.setText("¥" + dataBean.getPrice());
                    realPriceView.setText("¥" + dataBean.getAmount());
                    finalAmount = dataBean.getAmount();
                }

            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {

                hiddenLoadingView();
            }
        });


    }

    private void preparePay() {

        showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("amount", finalAmount);
        params.put("virtualRechargeProudct_id", charge.getVirtualRechargeProudct_id() + "");
        params.put("mobile", mobile);
        Log.e("preparePay", mobile );
        params.put("code", charge.getCode());
        params.put("usersTicketLog_id", coupon == null ? "" : coupon.getUsersTicket_id() + "");

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).virtualRechargeOrder(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                hiddenLoadingView();

                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                Bundle bundle2 = new Bundle();
                bundle2.putString("btAmount", jsonObject1.optString("btAmount"));
                bundle2.putString("amount", jsonObject1.optString("amount"));
                bundle2.putString("outTradeNo", jsonObject1.optString("outTradeNo"));
                bundle2.putString("ordersId", jsonObject1.optString("ordersId"));
                bundle2.putInt("isNeedPay", jsonObject1.optInt("isNeedPay"));
                bundle2.putInt("orderProductType", 1);

                StarActivityUtil.starActivity(ChargeConfirmActivity.this, PayActivity.class, bundle2);
                ChargeConfirmActivity.this.finish();
            }

            @Override
            public void onNotNetWork() {

                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {

                hiddenLoadingView();
            }
        });


    }


    private long lastTime = 0;

    @OnClick({R.id.toolbar_back, R.id.confirm_btn, R.id.layout_phone_coupon})
    public void onViewClick(View view) {

        switch (view.getId()) {

            case R.id.toolbar_back:

                finish();

                break;

            case R.id.confirm_btn:

                if (System.currentTimeMillis() - lastTime > 1000) {
                    lastTime = System.currentTimeMillis();
                } else {
                    return;
                }

                preparePay();

                break;

            case R.id.layout_phone_coupon:

                CouponActivity.startCouponActivityWithResult(this, COUPON_REQUEST_CODE, 1);

                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == COUPON_REQUEST_CODE) {
                Coupon coupon = data.getParcelableExtra("COUPON");
                if (this.coupon == null || this.coupon.getUsersTicket_id() != coupon.getUsersTicket_id()) {
                    this.coupon = coupon;
                    calculateOrderAmount();
                }
            }
        }
    }
}
