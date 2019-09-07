package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.ZeroGoodsBean;

/**
 * @author AYWang
 * @create 2018/8/3
 * @Describe
 */
public interface ZeroGoodsContract {
    interface  UI{
        void attachData(ZeroGoodsBean zeroGoodsBean, boolean isLoadMore);
        void attachBannerData(BannerBean bannerBean);
        void attachCenterAdData(BannerBean bannerBean);
        void onError();
        void noNet();
    }
    interface presenter{
        void start(boolean isLoadMore);
        void getBannerData();
        void getCenterAdData();
    }
}
