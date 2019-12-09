package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.SweetRecord;
import com.yunqin.bearmall.ui.activity.contract.SwweetRecordContract;
import com.yunqin.bearmall.ui.activity.model.RecordModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/21.
 */

public class SweetRecordPresenter implements SwweetRecordContract.Present {

    private SwweetRecordContract.Model model;

    private SwweetRecordContract.UI view;

    public SweetRecordPresenter( SwweetRecordContract.UI view) {
        this.view = view;
        model = new RecordModel();
    }



    @Override
    public void start(Context context) {


    }

    /**
     *
     * @param type  0 全部收入 1 收入withType 2 消费记录
     * @param id     收入type
     * @param queryIdentify  消费查询id
     * @param context
     * @param index
     */
    @Override
    public void refresh( int type,String id,int queryIdentify,Context context,int index) {

        Map<String,String> params = new HashMap<>();
        params.put("page_number",index+"");
        params.put("page_size","10");

        if (type == 1){
            params.put("type",id);
        }else if (type == 2 && queryIdentify != 0){
            params.put("queryIdentify",queryIdentify+"");
        }

        Observable<String> observable ;

        switch (type){
            case 0:
            default:
                observable = model.getRecordList(params);
                break;
            case 1:
                observable = model.getRecordListWithID(params);
                break;
            case 2:
                observable = model.getOutcomRecord(params);
                break;
        }

        RetrofitApi.request(context,observable , new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    SweetRecord record = new Gson().fromJson(data, SweetRecord.class);
                    view.finishLoadedData(record);
                }catch (Exception e){
                    e.printStackTrace();
                    view.failedLoadedData();
                }
            }

            @Override
            public void onNotNetWork() {
                view.failedLoadedData();
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.failedLoadedData();
            }
        });
    }
}
