package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;
import com.yunqin.bearmall.bean.UserPromotion;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity.contract
 * @DATE 2020/1/10
 */
public interface VipContract {

    interface Presenter {
        void start(Context context);
    }

    interface UI extends BaseContract.BaseView {

        void resultData(UserPromotion promotion);

        void onFail(Throwable throwable);

        void onNotNetWork();

    }

}
