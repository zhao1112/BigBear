package com.bbcoupon.ui.presenter;

import android.content.Context;

import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.model.RequestModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.presenter
 * @DATE 2020/4/22
 */
public class RequestPresenter implements RequestContract.RequestPresenter<RequestContract.RequestView> {

    private Reference<RequestContract.RequestView> reference;
    private RequestContract.RequestView requestView;
    private RequestContract.RequestModel requestModel;

    @Override
    public void setRelation(RequestContract.RequestView relation) {
        this.requestView = relation;
        reference = new WeakReference<>(relation);
        requestModel = new RequestModel();
    }

    @Override
    public void setUntying(RequestContract.RequestView untying) {
        if (reference != null) {
            reference.clear();
        }
    }

    @Override
    public void onCandySharing(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onCandySharing(context, requestView, map);
        }
    }

}
