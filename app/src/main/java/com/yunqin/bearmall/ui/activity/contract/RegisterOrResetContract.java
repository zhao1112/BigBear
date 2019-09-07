package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;
import android.graphics.Bitmap;

import com.yunqin.bearmall.bean.UserInfo;

import java.util.Map;

/**
 * @author Master
 */
public interface RegisterOrResetContract {


    interface Model {

    }

    interface UI {

        void attachVCod(Bitmap bitmap);

        void registerSuccess(UserInfo userInfo);

        void registerFail(String msg);

        void resetSuccess();

        void resetFail(String msg);

    }

    interface Presenter {

        void stat();

        void performRegister(Context context, Map<String, String> mHashMap);

        void perfromReset(Context context, Map<String, String> mHashMap);

        void perfromVerificationCode(Context context,String id);

        void sendMsgCode(Context context, Map<String, String> mHashMap);

    }

}
