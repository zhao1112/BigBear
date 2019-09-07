package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.bean.BearFAQ;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/25.
 */

public interface BearClassContract {

    interface IModel extends BaseContract.BaseModel{

        Observable<String> getQusetion();

        Observable<String> getNextTi(Map params);

    }

    interface IUI extends BaseContract.BaseView{

        void onGetInitData(BearFAQ data);

        void onGetNextTiData(BearFAQ data);

    }

    interface IPresent extends BaseContract.BasePresenter{

        void onRequestNextTI(int tag,String answer);


    }

}
