package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.MySnatch;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/7.
 */

public interface MySnatchContract {


    interface IView extends BaseContract.BaseView{

        void onLoadedData(MySnatch data);

        void onLoadError();

    }


    interface IModel extends BaseContract.BaseModel{

        Observable<String> getMemberTreasureInfo(Map params);

    }


    interface IPresent extends BaseContract.BasePresenter{

        void refreshData(int pageIndex);

    }


}
