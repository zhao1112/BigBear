package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;

import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/20
 * @Describe
 */
public interface SettingContract {
    interface  Presenter extends BaseContract.BasePresenter{
        void changeNickName(Context context, Map map);
        void bindWx(Context context, Map map);
        void out(Context context);
    }

    interface  UI extends BaseContract.BaseView{
        void bindWx();
        void outSussess();
        void outFail();
        void changeNickNameSussess();
        void  attachData(String data);
        void onError();
        void onFail();//昵称修改
    }

}
