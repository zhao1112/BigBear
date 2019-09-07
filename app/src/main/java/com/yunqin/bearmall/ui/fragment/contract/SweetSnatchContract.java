package com.yunqin.bearmall.ui.fragment.contract;

import android.content.Context;

import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.TreasureData;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/14.
 */

public interface SweetSnatchContract {

    interface IView extends BaseContract.BaseView{

        void onLoadedData(TreasureData.DataBean tag);
        void initBannerTop(BannerBean bannerBean);
        void onLoadError();
    }

    interface IModel extends BaseContract.BaseModel{

        Observable<String> getTagData();

    }


    interface IPresenter extends BaseContract.BasePresenter{

        void getBannerData(Context context);

    }

}
