package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.TreasureInfo;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/9.
 */

public interface SnatchDetailContract {

    public interface IView extends BaseContract.BaseView{

        void onLoadedData(TreasureInfo data);

    }


    public interface IModel extends BaseContract.BaseModel{

        Observable<String> getTreasureInfo(Map params);

    }


    public interface IPresent extends BaseContract.BasePresenter{

        void loadData(String tag,String tid,int isToday);

    }


}
