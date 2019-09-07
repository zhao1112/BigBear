package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.MessageContract;
import com.yunqin.bearmall.ui.activity.model.MessageModel;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import mlxy.utils.T;

/**
 * Created by chenchen on 2018/8/13.
 */

public class MessagePresenter implements MessageContract.IPresent{

    private MessageContract.IView view;
    private MessageModel model;
    private Context context;

    public MessagePresenter(MessageContract.IView view) {
        this.view = view;
        this.model = new MessageModel();
    }

    @Override
    public void refreshData(int type, int index) {

        Map<String,String> params = new HashMap<>();
        params.put("category",type+"");
        params.put("page_number",""+index);
        params.put("page_size","10");

        RetrofitApi.request(context, model.getMessage(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                view.onLoadData(data);

            }

            @Override
            public void onNotNetWork() {

                view.onLoadError();

            }

            @Override
            public void onFail(Throwable e) {

                view.onLoadError();
            }
        });

    }

    @Override
    public void start(Context context) {

        this.context = context;
    }
}
