package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.RightDesc;
import com.yunqin.bearmall.bean.VipItem;
import com.yunqin.bearmall.bean.VipResponse;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.SlantTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class OpenVipActivity extends BaseActivity {

    public static void startOpenVipActivity(Context context, String type, String code) {
        Intent intent = new Intent(context, OpenVipActivity.class);
        intent.putExtra("invitationType", type);
        intent.putExtra("invitationCode", code);
        context.startActivity(intent);
    }

    @BindViews({R.id.left, R.id.center, R.id.right})
    List<ViewGroup> containers;
    @BindViews({R.id.left_title, R.id.center_title, R.id.right_title})
    List<TextView> titles;
    @BindViews({R.id.left_price, R.id.center_price, R.id.right_price})
    List<TextView> prices;
    @BindViews({R.id.left_real_price, R.id.center_real_price, R.id.right_real_price})
    List<TextView> mPrices;
    @BindViews({R.id.left_slant, R.id.center_slant, R.id.right_slant})
    List<SlantTextView> slantTextViews;
    @BindView(R.id.radio_image)
    ImageView radioImage;
    @BindView(R.id.equity_content)
    LinearLayout equityContentLayout;
    @BindView(R.id.equity_text)
    TextView equityText;
    @BindView(R.id.real_price)
    TextView realPriceView;

    private List<VipItem> items;
    private int selectPosition;
    private String price;
    private String goodsName;

    private String invitationCode;
    private String invitationType;

    @Override
    public int layoutId() {
        return R.layout.activity_open_vip;
    }

    @Override
    public void init() {
        //TODO[点击会员充值]
        ConstantScUtil.VIPRechargeClick();

        immerseStatusBar();
        invitationType = getIntent().getStringExtra("invitationType");
        invitationCode = getIntent().getStringExtra("invitationCode");
        if (invitationType == null) {
            invitationType = "";
        }
        if (invitationCode == null) {
            invitationCode = "";
        }
        loadData();
    }

    private void seupData(List<VipItem> items) {
        int count = items.size() > 3 ? 3 : items.size();
        for (int i = 0; i < count; i++) {
            containers.get(i).setVisibility(View.VISIBLE);
            VipItem vipItem = items.get(i);
            titles.get(i).setText(vipItem.getName());
            prices.get(i).setText("¥" + vipItem.getShowPrice());
            mPrices.get(i).setText("¥" + vipItem.getFirstChargePrice());
            String discount = vipItem.getShowDiscount();
            if (!TextUtils.isEmpty(discount)) {
                slantTextViews.get(i).setVisibility(View.VISIBLE);
                slantTextViews.get(i).setText(discount + "折");
            } else {
                slantTextViews.get(i).setVisibility(View.INVISIBLE);
            }
        }
        setSelect(0);
    }

    private void setSelect(int position) {
        selectPosition = position;
        changeText(position);
        for (int j = 0; j < containers.size(); j++) {
            ViewGroup viewGroup = containers.get(j);
            viewGroup.setSelected(j == position);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                viewGroup.getChildAt(i).setSelected(j == position);
            }
            if (j == position) {
                changeText(position);
            }
        }

    }

    private void changeText(int position) {
        StringBuffer stringBuffer = new StringBuffer();
        VipItem item = items.get(position);
        List<RightDesc> rightDescs = item.getRightDesc();
        for (int i = 0; i < rightDescs.size(); i++) {
            RightDesc rightDesc = rightDescs.get(i);
            int isValid = Integer.parseInt(rightDesc.getIs_valid());
            if (isValid == 0) {
                stringBuffer.append(rightDesc.getDesc());
                if (i != rightDescs.size() - 1) {
                    stringBuffer.append("\n");
                }
            }
        }
        equityText.setText(stringBuffer.toString());
        stringBuffer.setLength(0);
        realPriceView.setText("¥" + item.getFirstChargePrice());
        price = item.getFirstChargePrice();
        goodsName = item.getName();
    }

    private void loadData() {
        showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("invitationType", invitationType);
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getOpenMemberPageInfo(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                VipResponse response = new Gson().fromJson(data, VipResponse.class);
                if (response.isSuccess()) {
                    VipResponse.DataBean dataBean = response.getData();
                    items = dataBean.getList();
                    if (items != null && items.size() > 0) {
                        seupData(items);
                    }
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
        VipItem item = items.get(selectPosition);
        //TODO[会员类型选择]
        ConstantScUtil.VIPRechargeTypeClick(item.getName(), item.getShowPrice() + "", item.getFirstChargePrice() + "");

        Map<String, String> params = new HashMap<>();
        params.put("memberGrade_id", item.getMemberGrade_id() + "");
        params.put("type", "1");
        params.put("amount", price);
        params.put("invitationCode", invitationCode);
        params.put("invitationType", invitationType);

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).openMemberOrder(params), new RetrofitApi.IResponseListener() {
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
                bundle2.putInt("orderProductType", 2);
                bundle2.putBoolean("member_charge", true);
                bundle2.putString("type_name", item.getName());
                StarActivityUtil.starActivity(OpenVipActivity.this, PayActivity.class, bundle2);
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

    @OnClick({R.id.toolbar_back, R.id.agree_layout, R.id.confirm_btn, R.id.left, R.id.center, R.id.right, R.id.server_desc})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.agree_layout:
                radioImage.setSelected(!radioImage.isSelected());
                break;
            case R.id.confirm_btn:
                if (radioImage.isSelected()) {
                    preparePay();
                } else {
                    showToast("请同意<<大熊会员服务协议>>");
                }
                break;
            case R.id.left:
                setSelect(0);
                break;
            case R.id.center:
                setSelect(1);
                break;
            case R.id.right:
                setSelect(2);
                break;
            case R.id.server_desc:
                String guidelUrl = BuildConfig.BASE_URL + "/view/memberRightsPage";
                VanguardListPageActivity.startH5Activity(this, guidelUrl, "大熊会员服务协议");
                break;
        }
    }
}
