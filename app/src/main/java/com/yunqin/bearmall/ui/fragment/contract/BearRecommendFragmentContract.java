package com.yunqin.bearmall.ui.fragment.contract;


import com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter;
import com.yunqin.bearmall.bean.HomeAd;
import com.yunqin.bearmall.bean.HomeBean;

import java.util.List;

public interface BearRecommendFragmentContract {

    interface Model {
        List<String> getNormalGoodsTitls();

        List<String> getBannerImageUrls();

        List<String> getFeaturedFirst3();

        List<String> getFeaturedFirst1();
    }

    interface UI {

        /**
         * 返回Adapter
         *
         * @param adapter
         */
        void attachAdapter(MoreTypeRecycleViewAdapter adapter);

        void attachAdapter(MoreTypeRecycleViewAdapter adapter, HomeBean homeBean);

        /**
         * 初始化Banner广告
         */
        void initBannerTop(MoreTypeRecycleViewAdapter adapter, List<HomeAd.DataBean.AdBean> lists);

        /**
         * 初始化Banner下面四个Menu选项
         */
        void initLinearMenu(MoreTypeRecycleViewAdapter adapter);

        /**
         * 3个横向推荐位
         */
        void initFeaturedFirstHolder3(MoreTypeRecycleViewAdapter adapter, List<HomeAd.DataBean.AdBean> lists);

        /**
         * 一个横向推荐位
         */
        void initFeaturedFirstHolder1(MoreTypeRecycleViewAdapter adapter, List<HomeAd.DataBean.AdBean> lists);


        void shuaXinFinish();

        void jiaZaiGengDuoFinish();

        void onHasMore(boolean hasMore);


        void showLoad();
        void hideLoad();

        void onNotNetWork();



    }

    interface Presenter {
        void start();

        void init1();

        void init2();

        void init3();

        void init4();

        void init5();


        void shuaXin();

        void jiaZaiGengDuo();


    }


}
