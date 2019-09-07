package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ReviewListBean;
import com.yunqin.bearmall.ui.fragment.contract.ProductCommentFragmentContact;

import java.util.Map;

public class ProductCommentFragmentPresenter implements ProductCommentFragmentContact.presenter{

    ProductCommentFragmentContact.UI view;
    Context mContext;


    public ProductCommentFragmentPresenter(Context mContext, ProductCommentFragmentContact.UI view){
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void refresh(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getProductReviewList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                ReviewListBean reviewListBean = new Gson().fromJson(data,ReviewListBean.class);
                view.attachhData(reviewListBean);
                view.onHasMore(reviewListBean.getData().getHas_more() == 1);

            }

            @Override
            public void onNotNetWork() {
                view.onHasMore(false);
            }

            @Override
            public void onFail(Throwable e) {
                view.onHasMore(false);
                view.onError();
            }
        });

    }
}
