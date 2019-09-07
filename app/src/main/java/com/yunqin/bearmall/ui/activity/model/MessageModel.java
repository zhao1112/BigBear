package com.yunqin.bearmall.ui.activity.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.MessageContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/13.
 */

public class MessageModel implements MessageContract.IModel {
    @Override
    public Observable<String> getMessage(Map params) {
        return RetrofitApi.createApi(Api.class).getPushMessageList(params);
    }


}
