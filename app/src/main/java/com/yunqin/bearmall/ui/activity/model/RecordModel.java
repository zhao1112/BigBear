package com.yunqin.bearmall.ui.activity.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.SwweetRecordContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/21.
 */

public class RecordModel implements SwweetRecordContract.Model {

    @Override
    public Observable<String> getRecordList(Map params) {
        return RetrofitApi.createApi(Api.class).getIncomeRecordList(params);
    }

    @Override
    public Observable<String> getRecordListWithID(Map params) {
        return RetrofitApi.createApi(Api.class).getIncomeRecordListWithID(params);
    }

    @Override
    public Observable<String> getOutcomRecord(Map params) {
        return RetrofitApi.createApi(Api.class).getOutcomeRecordList(params);
    }

}
