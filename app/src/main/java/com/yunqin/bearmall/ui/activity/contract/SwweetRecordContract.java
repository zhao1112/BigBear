package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;

import com.yunqin.bearmall.bean.SweetRecord;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/21.
 */

public interface SwweetRecordContract {

    interface Model{

        Observable<String> getRecordList(Map params);

        Observable<String> getRecordListWithID(Map params);

        Observable<String> getOutcomRecord(Map params);

    }

    interface UI extends BaseContract.BaseView{

        void finishLoadedData(SweetRecord record);

        void failedLoadedData();

        void onNotNetWork();


    }

    interface Present extends BaseContract.BasePresenter {

        void refresh(int type,String id,int queryIdentify,Context context, int pageNum);

    }


}
