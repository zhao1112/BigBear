package com.yunqin.bearmall.ui.fragment.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.fragment.contract.SweetSnatchContract;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/14.
 */

public class SweetSnatchModel implements SweetSnatchContract.IModel {

    @Override
    public Observable<String> getTagData() {
        return RetrofitApi.createApi(Api.class).getTreasureTagInfo();
    }

}
