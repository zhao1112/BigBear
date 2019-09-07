package com.newreward.SpecialRequestUI;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newreward.apdater.RecordListAdapter;
import com.newreward.bean.RecordBean;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author AYWang
 * @create 2019/1/22
 * @Describe
 */
public class RequestRecord extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.list)
    ListView listView;

    private RecordListAdapter recordListAdapter;
    private List<RecordBean> recordBeanList = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.activity_record_list;
    }

    @Override
    public void init() {
        toolbar_title.setText("邀约记录");
        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recordBeanList.add(new RecordBean());
        recordListAdapter = new RecordListAdapter(this,recordBeanList);
        listView.setAdapter(recordListAdapter);
        getRecordData();
    }

    private void getRecordData() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getSpecInvitationRecord(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
                RecordBean[] array = new Gson().fromJson(  jsonObject1.getJSONArray("list").toString(),RecordBean[].class);
                List<RecordBean> list = Arrays.asList(array);
                recordBeanList.addAll(list);
                recordListAdapter.notifyDataSetChanged();
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
