package com.yunqin.bearmall.ui.fragment.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.ui.fragment.contract.HomeContract;

import io.reactivex.Observable;

/**
 * @author Master
 */
public class HomeFragmentModel implements HomeContract.Model {

    @Override
    public Observable<String> getSubjectTitle() {

        Observable<String> stringObservable = RetrofitApi.createApi(Api.class).getSubjectTitle2();

        return stringObservable;
    }
}
