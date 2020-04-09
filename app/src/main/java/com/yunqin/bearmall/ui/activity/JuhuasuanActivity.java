package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.HotBean;
import com.yunqin.bearmall.ui.fragment.JuhuasuanFragment;
import com.yunqin.bearmall.ui.fragment.SellweFragment;

import org.json.JSONException;

import java.util.HashMap;

import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity
 * @DATE 2020/4/8
 */
public class JuhuasuanActivity extends BaseActivity {


    private String mType;
    private TextView mTitleText;

    public static void openJuhuasuanActivity(Activity context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_sellwell;
    }

    @Override
    public void init() {
        mType = getIntent().getStringExtra("TYPE");
        mTitleText = findViewById(R.id.title);

        JuhuasuanFragment sellweFragment = new JuhuasuanFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", mType);
        sellweFragment.setArguments(bundle);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, sellweFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.show(sellweFragment);
        fragmentTransaction.commit();

        getListData();
    }

    @OnClick({R.id.toolbar_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    private void getListData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(1));
        map.put("pageSize", String.valueOf(10));
        map.put("cid", "0");
        map.put("type", mType);
        RetrofitApi.request(JuhuasuanActivity.this, RetrofitApi.createApi(Api.class).getHotSelling(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        HotBean searchData = new Gson().fromJson(data, HotBean.class);
                        if (searchData != null && searchData.getCommodityList() != null && searchData.getCommodityList().size() > 0) {
                            mTitleText.setText(searchData.getPlatformList().getTitle());
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
