package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.newversions.MemberMallActivity;
import com.newreward.bean.PrivilegeBean;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.MemberBeanResponse;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.json.JSONException;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 * @create 2018/8/23 14:34
 */
public class PayOverMemberActivity extends BaseActivity {

    @BindView(R.id.pay_status)
    TextView pay_status;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recommend_view)
    CustomRecommendView mCustomRecommendView;








    @BindView(R.id.t1)
    TextView t1;

    @BindView(R.id.t2)
    TextView t2;

    @BindView(R.id.t3)
    TextView t3;

    @BindView(R.id.t4)
    TextView t4;

    @BindView(R.id.t5)
    TextView t5;

    @BindView(R.id.t6)
    TextView t6;

    @BindView(R.id.type_1)
    HighlightButton type_1;
    @BindView(R.id.type_2)
    HighlightButton type_2;
    @BindView(R.id.type_0)
    HighlightButton type_0;


    public static void start(Context context) {
        Intent intent = new Intent(context, PayOverMemberActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_pay_over_member;
    }

    @Override
    public void init() {
        toolbar_title.setText("开通成功");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mCustomRecommendView.setManager(gridLayoutManager);
        mCustomRecommendView.hideTopLayout();
        mCustomRecommendView.start(this);

        updateUser();
        getData();

    }

    private void updateUser() {
        if (BearMallAplication.getInstance().getUser() != null) {

            RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberInfo(new HashMap<>()), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Log.e("用户信息", data);
                        UserInfo userInfo = BearMallAplication.getInstance().getUser();
                        UserInfo.DataBean dataBean = userInfo.getData();
                        MemberBeanResponse response = new Gson().fromJson(data, MemberBeanResponse.class);
                        UserInfo.DataBean.MemberBean memberBean = response.getData();
                        dataBean.setMember(memberBean);
                        userInfo.setData(dataBean);
                        BearMallAplication.getInstance().setUser(userInfo);
                    } catch (JsonSyntaxException e) {
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
    }

    @OnClick({R.id.toolbar_back, R.id.go_to_order,R.id.type_0,R.id.type_1,R.id.type_2})
    public void onSelect(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.go_to_order:
                finish();
                break;



            case R.id.type_0:
            case R.id.type_1:
            case R.id.type_2:

                try {
                    int tag = (int) view.getTag();
                    if (tag == 0){
                        startActivity(new Intent(this,MemberMallActivity.class));
                    }else if (tag == 1){
                        startActivity(new Intent(this,ChargeActivity.class));
                    }else {
                        startActivity(new Intent(this, ZeroMoneyActivity.class));
                    }
                }catch (Exception e){

                }

                break;




            default:
                break;
        }
    }














    //获取页面数据
    public void getData(){
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberUnusedPrivilege(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                PrivilegeBean privilegeBean = new Gson().fromJson(data,PrivilegeBean.class);
                changePageInfo(privilegeBean);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }


    private void changePageInfo(PrivilegeBean privilegeBean) {

        try{
            PrivilegeBean.DataBean.GroupRecordBean groupRecordBean = privilegeBean.getData().get(0).getGroupRecord();
            PrivilegeBean.DataBean.PhoneTicketRecordBean phoneTicketRecord = privilegeBean.getData().get(1).getPhoneTicketRecord();
            PrivilegeBean.DataBean.CashTicketRecordBean cashTicketRecordBean = privilegeBean.getData().get(2).getCashTicketRecord();

            if(phoneTicketRecord.getRestCount() == 0){
                t3.setVisibility(View.GONE);
                type_1.setClickable(false);
                type_1.setBackgroundResource(R.drawable.privilege_shape_1);
            }else {
                t3.setVisibility(View.GONE);
                type_1.setClickable(true);
                type_1.setBackgroundResource(R.drawable.privilege_shape);
                type_1.setTag(phoneTicketRecord.getType());
            }

            if(groupRecordBean.getRestCount() == 0){
                t6.setVisibility(View.GONE);
                type_2.setClickable(false);
                type_2.setBackgroundResource(R.drawable.privilege_shape_1);
            }else {
                type_2.setClickable(true);
                t6.setVisibility(View.GONE);
                type_2.setBackgroundResource(R.drawable.privilege_shape);
                type_2.setTag(groupRecordBean.getType());
            }

            if(cashTicketRecordBean.getRestCount() == 0){
                type_0.setClickable(false);
                type_0.setBackgroundResource(R.drawable.privilege_shape_1);
                t5.setVisibility(View.GONE);
            }else {
                type_0.setClickable(true);
                type_0.setBackgroundResource(R.drawable.privilege_shape);
                t5.setVisibility(View.GONE);
                type_0.setTag(cashTicketRecordBean.getType());
            }

            String t1Text = String.format("1、%s本月还可参与 <font color=#E75B56>%d</font> "+"次",groupRecordBean.getName(),groupRecordBean.getRestCount());
            t1.setText(Html.fromHtml(t1Text));
            t6.setText("剩余"+groupRecordBean.getExpireDays()+"天过期");
            String t2Text = String.format("2、%s剩余 <font color=#E75B56>%d</font> "+"张",phoneTicketRecord.getName(),phoneTicketRecord.getRestCount());
            t2.setText(Html.fromHtml(t2Text));
            t3.setText("剩余"+phoneTicketRecord.getExpireDays()+"天过期");
            String t4Text = String.format("2、%s剩余 <font color=#E75B56>%d</font> "+"张",cashTicketRecordBean.getName(),cashTicketRecordBean.getRestCount());
            t4.setText( Html.fromHtml(t4Text));
            t5.setText("剩余"+cashTicketRecordBean.getExpireDays()+"天过期");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

































}
