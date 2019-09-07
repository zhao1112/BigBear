package com.yunqin.bearmall.ui.activity.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.fragment.contract.GuideContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/27.
 */

public class GuideModel implements GuideContract.IModel {
    @Override
    public Observable<String> getGuideList(Map params) {
        return RetrofitApi.createApi(Api.class).getArticleList(params);
    }
}
