package com.newversions.tbk.activity;

import com.newversions.tbk.entity.GoodDetailEntity;
import com.newversions.tbk.entity.LikeGuessEntity;
import com.newversions.tbk.entity.TBKHomeGoodsEntity;

public interface GoodsDetailContract {
    public interface View{
        void showLoad();
        void hideLoad();
        void onNotNetWork();
        void onRefreshFinish();
        void onLoadMoreFinish();
        void attachData(GoodDetailEntity goodDetailEntity);
        void attachAddData(TBKHomeGoodsEntity tbkHomeGoodsEntity);
        void haseMore(boolean haseMore);
//        void to
    }

    public interface Presenter{
        void init(String goodsId);
        void getMoreLikeGoods(String goodsId);
//        void getCommissionUrl(String goodsId);
    }
}
