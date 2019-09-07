package com.yunqin.bearmall.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.BTGive;
import com.yunqin.bearmall.eventbus.ProductMessageEvent;
import com.yunqin.bearmall.eventbus.VoucherEvent;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/20
 * @Describe
 * 就三个接口调用一下  不写MVP了 工期紧啊  Fucking Tiger
 * 第二天又看了一遍  看不懂了  操
 * ...
 * mb的第三天同事来扩展东西了
 */
public class FindBTGiveWhoActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.sure_btn)
    Button sure_btn;

    @BindView(R.id.search_btn)
    Button search_btn;

    @BindView(R.id.rule)
    TextView rule;

    @BindView(R.id.bt_money)
    TextView bt_money;

    @BindView(R.id.search_text)
    EditText search_text;

    @BindView(R.id.bt_number_editext)
    EditText bt_number_editext;

    @BindView(R.id.member)
    LinearLayout member;

    @BindView(R.id.member_head)
    CircleImageView member_head;

    @BindView(R.id.menber_name)
    TextView menber_name;

    @BindView(R.id.menber_number)
    TextView menber_number;

    @BindView(R.id.do_task)
    TextView do_task;

    private long member_id;

    private String  inputString;

    private int transferNumbers=0;//剩余赠送次数

    private String usersTicket_id="";//转增券id
    private String amount="";//转增券金额


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(VoucherEvent voucherEvent) {
        usersTicket_id = voucherEvent.getVoucherID();
        amount = voucherEvent.getVoucherAmount();
        giveBtToOne(2);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_find_bt_give_who;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        if(getIntent().getExtras() != null){
            usersTicket_id = (String) getIntent().getExtras().get("usersTicket_id");
            amount = (String) getIntent().getExtras().get("amount");
        }

        toolbar_title.setText("糖果赠送");
        bt_number_editext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!bt_number_editext.getText().toString().equals("") && member_id != 0){
                    sure_btn.setClickable(true);
                    sure_btn.setBackgroundResource(R.drawable.bt_give_btn_focused);
                    sure_btn.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    sure_btn.setClickable(false);
                    sure_btn.setBackgroundResource(R.drawable.bt_give_btn_unfocused);
                    sure_btn.setTextColor(Color.parseColor("#B3B3B3"));
                }
            }
        });
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!search_text.getText().toString().equals(inputString)){
                    member.setVisibility(View.INVISIBLE);
                    member_id = 0;
                    sure_btn.setClickable(false);
                    sure_btn.setBackgroundResource(R.drawable.bt_give_btn_unfocused);
                    sure_btn.setTextColor(Color.parseColor("#B3B3B3"));
                }

                if(!search_text.getText().toString().equals("")){
                    search_btn.setClickable(true);
                    search_btn.setBackgroundResource(R.drawable.bt_give_btn_focused);
                    search_btn.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    search_btn.setClickable(false);
                    search_btn.setBackgroundResource(R.drawable.bt_give_btn_unfocused);
                    search_btn.setTextColor(Color.parseColor("#B3B3B3"));
                }
            }
        });
    }

    @OnClick({R.id.search_btn,R.id.sure_btn,R.id.toolbar_back,R.id.do_task})
    void onClick(View view){
        switch (view.getId()){
            case R.id.search_btn:
                inputString = search_text.getText().toString();
                getGiveOne(search_text.getText().toString());
                break;
            case R.id.sure_btn:
                if(transferNumbers == 0){
                    Intent intent = new Intent(this,VoucherDialogActivity.class);
                    startActivity(intent);
                    showToast("您当前赠送次数已用完，快去使用赠送券儿吧！");
                }else {
                    if(usersTicket_id.equals("")){
                        giveBtToOne(1);
                    }else {
                        giveBtToOne(2);
                    }
                }
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.do_task:
                StarActivityUtil.starActivity(this, DailyTasksActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 2.24.	获取糖果赠送面板基础数据
     * */
    @SuppressLint("StringFormatMatches")
    private void getData() {
        Constans.params.clear();
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getPresentPanelBasicINfo(Constans.params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    bt_money.setText("BC"+jsonObject.getJSONObject("data").optString("balanceNow"));
                    rule.setText(Html.fromHtml(getResources().getString(R.string.bt_give_rule,jsonObject.getJSONObject("data").optInt("residualCount"),jsonObject.getJSONObject("data").optString("perBtAmount"))));
                    transferNumbers =jsonObject.getJSONObject("data").optInt("residualCount");
                } catch (JSONException e) {
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

    private void getGiveOne(String queryParm) {
        Map map = new HashMap();
        map.put("queryParm",queryParm);
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).findMemberToSend(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    BTGive btGive = new Gson().fromJson(data,BTGive.class);
                    if(btGive.getData().size()<=0){
                        showToast("您搜索的用户不存在");
                        member.setVisibility(View.INVISIBLE);
                        member_id=0;
                    }else {
                        member.setVisibility(View.VISIBLE);
                        Glide.with(FindBTGiveWhoActivity.this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(btGive.getData().get(0).getIconUrl()).into(member_head);
                        menber_name.setText(btGive.getData().get(0).getNickName());
                        menber_number.setText("大熊号："+btGive.getData().get(0).getBigBearNumber());
                        member_id=btGive.getData().get(0).getMember_id();
                        if(!bt_number_editext.getText().toString().equals("")){
                            sure_btn.setClickable(true);
                            sure_btn.setBackgroundResource(R.drawable.bt_give_btn_focused);
                            sure_btn.setTextColor(Color.parseColor("#ffffff"));
                        }
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
                member.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void giveBtToOne(int type) {
        Map map = new HashMap();
        map.put("type",type+"");
        map.put("presentAmount",bt_number_editext.getText().toString());
        map.put("member_id",member_id+"");

        if(type == 2){
            map.put("usersTicket_id",usersTicket_id+"");
        }

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).sendPoint(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                showToast("赠送成功");
                finish();
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
