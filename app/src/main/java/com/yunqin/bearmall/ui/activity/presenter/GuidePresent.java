package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.GuideBean;
import com.yunqin.bearmall.ui.activity.model.GuideModel;
import com.yunqin.bearmall.ui.fragment.contract.GuideContract;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchen on 2018/7/27.
 */

public class GuidePresent implements GuideContract.IPresent {

    private GuideContract.IUI view;

    private GuideContract.IModel model;

    private Context context;

    public GuidePresent(GuideContract.IUI view) {
        this.view = view;
        model = new GuideModel();
    }

    @Override
    public void start(Context context) {

        this.context = context;

    }

    @Override
    public void refrshData(int pageIndex, String category_id) {

        Map<String, String> params = new HashMap<>();
        params.put("page_size", "10");
        params.put("page_number", pageIndex + "");
//        params.put("category_id", category_id);

        RetrofitApi.request(context, model.getGuideList(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                GuideBean guideBean = new Gson().fromJson(data, GuideBean.class);
                view.onGetGuideListData(guideBean);
                view.onHasMore(guideBean.getData().getHas_more() == 1);

            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
                view.onHasMore(false);
            }

            @Override
            public void onFail(Throwable e) {

                view.onLoadError();
            }
        });

    }


}
