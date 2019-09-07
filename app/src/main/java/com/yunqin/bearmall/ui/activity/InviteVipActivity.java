package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.InviteBean;
import com.yunqin.bearmall.bean.InviteResponse;
import com.yunqin.bearmall.bean.PayBean;
import com.yunqin.bearmall.bean.PayResponse;
import com.yunqin.bearmall.bean.RightDesc;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.bean.VipItem;
import com.yunqin.bearmall.bean.VipResponse;
import com.yunqin.bearmall.ui.fragment.PayDialogFragment;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class InviteVipActivity extends BaseActivity {


    public static void startInviteVipActivity(Context context,String invitationtype,String inviteVode){
        Intent intent = new Intent(context,InviteVipActivity.class);
        intent.putExtra("invitationType",invitationtype);
        intent.putExtra("invitationCode",inviteVode);
        context.startActivity(intent);
    }


//    @BindViews({R.id.left,R.id.right})
//    List<ViewGroup> containers;
//    @BindViews({R.id.left_title,R.id.right_title})
//    List<TextView> titles;
//    @BindViews({R.id.left_price,R.id.right_price})
//    List<TextView> prices;

    @BindView(R.id.left)
    ViewGroup leftContainer;
    @BindView(R.id.left_title)
    TextView leftTitle;
    @BindView(R.id.left_price)
    TextView leftPrice;

    @BindView(R.id.radio_image)
    ImageView radioImage;

    @BindView(R.id.equity_text)
    TextView equityText;

    @BindView(R.id.real_price)
    TextView realPriceView;

    private InviteBean inviteBean;

    private String price;
    private String goodsName;

    private PayDialogFragment fragment;

    private String invitationCode;
    private String invitationType;

    @Override
    public int layoutId() {
        return R.layout.activity_invite_vip;
    }

    @Override
    public void init() {

        immerseStatusBar();
//        inviteCode = getIntent().getStringExtra("CODE");
        invitationType = getIntent().getStringExtra("invitationType");
        invitationCode = getIntent().getStringExtra("invitationCode");
        loadData();

    }

    private void seupData(){

        leftContainer.setVisibility(View.VISIBLE);
        leftTitle.setText(inviteBean.getName());
        leftPrice.setText("¥"+inviteBean.getShowPrice());
        leftContainer.setSelected(true);
        leftTitle.setSelected(true);
        leftPrice.setSelected(true);

        StringBuffer stringBuffer = new StringBuffer();
        List<RightDesc> rightDescs = inviteBean.getRightsDec();
        for (int i=0;i<rightDescs.size();i++){
            RightDesc rightDesc = rightDescs.get(i);
            int isValid = Integer.parseInt(rightDesc.getIs_valid());
            if (isValid == 0){
                stringBuffer.append(String.format("%d、%s",i,rightDesc.getDesc()));
                if (i!=rightDescs.size()-1){
                    stringBuffer.append("\n");
                }
            }
        }
        equityText.setText(stringBuffer.toString());
        stringBuffer.setLength(0);
        realPriceView.setText("¥"+inviteBean.getRealPrice());
        price = inviteBean.getRealPrice();
        goodsName = inviteBean.getName();

    }




    private void loadData(){

        showLoading();
        Map<String,String> params = new HashMap<>();
        params.put("inviteType",invitationType);
        params.put("inviteCode",invitationCode);

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getSpecOpenMemberPageInfo(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                hiddenLoadingView();

                InviteResponse response = new Gson().fromJson(data,InviteResponse.class);

                if (response.isSuccess()){

                    InviteResponse.DataBean dataBean = response.getData();

                    inviteBean = dataBean.getOpenMemberInfo();

                    seupData();
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

    private void preparePay(){

        showLoading();

        Map<String,String> params = new HashMap<>();
        params.put("memberGrade_id",inviteBean.getMemberGrade_id()+"");

        params.put("type","1");
        params.put("amount",price);
        params.put("invitationCode",invitationCode);
        params.put("invitationType",invitationType);


        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).openMemberOrder(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();

//                PayResponse response = new Gson().fromJson(data,PayResponse.class);
//                if (response.isSuccess()){
//                    PayBean payBean = response.getData();
//                    fragment = PayDialogFragment.instance(payBean,goodsName);
//                    fragment.show(getSupportFragmentManager(),"");
//                }

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

                StarActivityUtil.starActivity(InviteVipActivity.this, PayActivity.class, bundle2);

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

    @OnClick({R.id.toolbar_back,R.id.agree_layout,R.id.confirm_btn, R.id.server_desc})
    public void onViewClick(View view){

        switch (view.getId()){

            case R.id.toolbar_back:

                finish();

                break;

            case R.id.agree_layout:

                radioImage.setSelected(!radioImage.isSelected());

                break;

            case R.id.confirm_btn:

                if (radioImage.isSelected()){
                    preparePay();
                }else {
                    showToast("请同意<<大熊会员服务协议>>");
                }

                break;

            case R.id.server_desc:
//                showToast("跳转");
                String guidelUrl = BuildConfig.BASE_URL+"/view/memberRightsPage";
                VanguardListPageActivity.startH5Activity(this,guidelUrl,"大熊会员服务协议");
                break;


        }

    }


}
