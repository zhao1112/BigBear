package com.yunqin.bearmall.ui.activity.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.SweetRecordWithTypeContract;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/23.
 */

public class RecordWithTypeModel implements SweetRecordWithTypeContract.IModel {
    @Override
    public Observable<String> getMemberIncomeAllType() {
        return RetrofitApi.createApi(Api.class).getMemberIncomeAllType();
    }
}
