package com.yunqin.bearmall.ui.fragment.contract;

import android.content.Context;

import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.UserBTInfo;
import com.yunqin.bearmall.bean.UserInfo;

import java.util.Map;

public interface MineContract {

    interface Presenter {
        void start(Context context, Map map);

        void getTaskInfoData(Context context);

        void getOrderNumberInfo(Context context);

        void updateUserInfo(Context context);

        void onDestroy();

        void initAdData(Context context);

    }

    interface UI extends BaseContract.BaseView {

        void resultAdData(String key, String value, String data);

        void attachUserBTInfo(UserBTInfo userBTInfo);

        void initBannerTop(BannerBean bannerBean);

        void initTaskInfo(String taskInfo);

        void initOrderNumberInfo(String orderNumberInfo);

        void onUpdateUserInfo(UserInfo userInfo);
    }

}
