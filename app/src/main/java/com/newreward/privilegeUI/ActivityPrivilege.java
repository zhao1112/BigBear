package com.newreward.privilegeUI;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.MemberMallActivity;
import com.newversions.view.NewVersionChoicenessView;
import com.newreward.bean.PrivilegeBean;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyActivity;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.json.JSONException;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2019/1/24
 * @Describe
 */
public class ActivityPrivilege extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.name)
    TextView name;

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


    @BindView(R.id.recommend_view)
    NewVersionChoicenessView choicenessView;

    @Override
    public int layoutId() {
        return R.layout.activity_privilege;
    }

    @Override
    public void init() {
        try {
            //userinfo 有可能为null
            name.setText(BearMallAplication.getInstance().getUser().getData().getMember().getNickName());
        }catch (Exception e){
            e.printStackTrace();
        }
        toolbar_title.setText("未使用特权");
        getData();
        choicenessView.setManager(new GridLayoutManager(this, 2));
        choicenessView.init();

    }

    @OnClick({R.id.toolbar_back,R.id.type_0,R.id.type_1,R.id.type_2,})
    void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_back:
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

//            case R.id.type_0:
//                startActivity(new Intent(this,MemberMallActivity.class));
//                break;
//
//            case R.id.type_1:
//                //话费
//                startActivity(new Intent(this,ChargeActivity.class));
//
//                break;
//
//            case R.id.type_2:
//                //0元兑
//                startActivity(new Intent(this, ZeroMoneyActivity.class));
//                break;
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

//            t1.setText( Html.fromHtml(getString(R.string.privitege_1, groupRecordBean.getRestCount()+"")));
//            t2.setText( Html.fromHtml(getString(R.string.privitege_2, phoneTicketRecord.getRestCount()+"")));
//            "本次售后服务由<font color=#23A064>%s</font>为您提供
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