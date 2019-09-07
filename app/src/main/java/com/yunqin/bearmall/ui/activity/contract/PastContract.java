package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.bean.PastSnatchData;
import com.yunqin.bearmall.bean.ZeroPastData;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/25.
 */

public interface PastContract {

    interface IView extends BaseContract.BaseView{

        void onLoadedSnatchData(PastSnatchData data);

        void onLoadedZeroData(ZeroPastData data);

    }

    interface IModel extends BaseContract.BaseModel{

        Observable<String> getSnatchPastList(Map params);

        Observable<String> getZeroPastList(Map params);

    }

    interface IPresenter extends BaseContract.BasePresenter{

        void refreshSnatchPastData(int index);

        void refreshZeroPastData(int index);

    }

}
