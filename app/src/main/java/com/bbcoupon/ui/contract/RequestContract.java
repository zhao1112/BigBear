package com.bbcoupon.ui.contract;

import android.content.Context;

import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.contract
 * @DATE 2020/4/22
 */
public class RequestContract {

    public interface RequestView {
        void onSuccess(Object data);

        void onNotNetWork();

        void onFail(Throwable e);
    }

    public interface RequestPresenter<RequestView> {
        void setRelation(RequestView relation);

        void setUntying(RequestView untying);

        void onCandySharing(Context context, Map<String, String> map);
    }

    public interface RequestModel {
        void onCandySharing(Context context, RequestContract.RequestView requestView, Map<String, String> map);
    }
}
