package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.GuideBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/27.
 */

public interface GuideContract {

    interface IUI extends BaseContract.BaseView{

        void onGetGuideListData(GuideBean guideBean);

        void onLoadError();

        void onNotNetWork();


        void onHasMore(boolean hasMore);



    }

    interface IModel extends BaseContract.BaseModel{

        Observable<String> getGuideList(Map params);

    }


    interface IPresent extends BaseContract.BasePresenter{

        void refrshData(int pageIndex,String category_id);

    }

}
