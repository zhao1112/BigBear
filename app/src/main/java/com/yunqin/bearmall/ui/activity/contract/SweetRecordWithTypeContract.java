package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.bean.SweetRecordAllType;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/23.
 */

public interface SweetRecordWithTypeContract {

    interface IModel{

        Observable<String> getMemberIncomeAllType();

    }

    interface IUI extends BaseContract.BaseView {

        void onGetData(SweetRecordAllType data);

        void onNotNetWork();

        void onLoadError();

    }

    interface IPresent extends BaseContract.BasePresenter{

    }

}
