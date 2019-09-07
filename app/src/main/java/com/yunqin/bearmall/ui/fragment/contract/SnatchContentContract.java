package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.SnatchContent;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/7.
 */

public interface SnatchContentContract {

    interface IView extends BaseContract.BaseView{
        void onLoadedData(SnatchContent data);
    }

    interface IModel extends BaseContract.BaseModel{
        Observable<String> getTreasureBasicInfo(Map params);
    }

    interface IPresent extends BaseContract.BasePresenter{
        void refreshData(int pageIndex,int tag,int isToday);
    }

}
