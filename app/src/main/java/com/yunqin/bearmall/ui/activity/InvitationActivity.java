package com.yunqin.bearmall.ui.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.InvitationBean;
import com.yunqin.bearmall.ui.dialog.InvitationPosterDialog;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/25
 * @Describe
 * 邀请好友
 */
public class InvitationActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.rule)
    TextView rule;//规则

    @BindView(R.id.m2_text_people_number)
    TextView m2_text_people_number;

    @BindView(R.id.m2_text_bt_number)
    TextView m2_text_bt_number;

    @BindView(R.id.m1_text_people_number)
    TextView m1_text_people_number;

    @BindView(R.id.m1_text_bt_number)
    TextView m1_text_bt_number;

    @BindView(R.id.bt_get)
    TextView bt_get;//累计获得糖果数

    @BindView(R.id.copy)
    TextView copy;//复制链接

    @BindView(R.id.link)
    TextView link;//链接

    @BindView(R.id.creat_poster_text)
    TextView creat_poster_text;//立即生成

    @BindView(R.id.get_bt_number)
    TextView get_bt_number;

    @BindView(R.id.m2_bt_number)
    TextView m2_bt_number;

    @BindView(R.id.creat_poster)
    HighlightButton creat_poster;


    private String changeTextOne;
    private String changeTextTwo;

    @Override
    public int layoutId() {
        return R.layout.activity_invatation;
    }
    @Override
    public void init() {
        toolbarTitle.setText("邀请好友获利");
        getInvitationData();
    }

    @OnClick({R.id.toolbar_back,R.id.copy,R.id.creat_poster,R.id.creat_poster_text})
    void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_back:
                this.finish();
                break;
            case R.id.copy:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, link.getText().toString()));
                showToast("链接复制成功");
                break;
            case R.id.creat_poster:
            case R.id.creat_poster_text:
                new InvitationPosterDialog(InvitationActivity.this);
                break;
        }
    }

    public void getInvitationData(){
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getInviteDetails(), new RetrofitApi.IResponseListener() {
            @SuppressLint({"StringFormatMatches", "NewApi"})
            @Override
            public void onSuccess(String data) {
                InvitationBean invitationBean = new Gson().fromJson(data,InvitationBean.class);

                link.setText("邀请链接:"+invitationBean.getInviteUrl());

                bt_get.setText(Html.fromHtml(getString(R.string.get_total_bt ,invitationBean.getData().getInviteTotalReward())));

                m1_text_bt_number.setText(invitationBean.getData().getM1RewardValue()+"");

                get_bt_number.setText((invitationBean.getData().getM1RewardValue()*invitationBean.getData().getM1Count())+"");

                m1_text_people_number.setText(invitationBean.getData().getM1Count()+"");

                m2_text_bt_number.setText("M1收益*"+((int)(invitationBean.getData().getM2RewardRatio()*100))+"%");

                m2_text_people_number.setText(invitationBean.getData().getM2Count()+"");

                m2_bt_number.setText((((int) (Integer.valueOf(get_bt_number.getText().toString())*0.2))*invitationBean.getData().getM2Count())+"");

                changeTextOne = ((int)(invitationBean.getData().getM2RewardRatio()*100))+"%";

                changeTextTwo = invitationBean.getData().getM1RewardValue()+"";

                initRule();
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void initRule() {
        rule.setText(String.format(getResources().getString(R.string.rule),changeTextOne,changeTextTwo,changeTextOne));
    }
}
