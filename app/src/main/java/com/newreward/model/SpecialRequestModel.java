package com.newreward.model;


import com.newreward.contract.SpecialRequestContract;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;

import io.reactivex.Observable;


/**
 * @author AYWang
 * @create 2019/1/22
 * @Describe
 */
public class SpecialRequestModel implements SpecialRequestContract.Model {
    @Override
    public Observable<String> getSpecInvitationPageInfo() {
        return RetrofitApi.createApi(Api.class).getSpecInvitationPageInfo();
    }
}
