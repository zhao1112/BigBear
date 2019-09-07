package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/13.
 */

public interface MessageContract {

    public interface IView{

        void onLoadData(String data);

        void onLoadError();

    }

    public interface IModel{

        Observable<String> getMessage(Map params);

    }


    public interface IPresent extends BaseContract.BasePresenter{

        void refreshData(int type,int index);

    }

}
