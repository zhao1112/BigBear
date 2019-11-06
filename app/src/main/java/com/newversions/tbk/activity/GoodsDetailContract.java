package com.newversions.tbk.activity;

import com.newversions.tbk.entity.GoodDetailEntity;
import com.newversions.tbk.entity.LikeGuessEntity;
import com.newversions.tbk.entity.TBKHomeGoodsEntity;
import com.yunqin.bearmall.bean.ContenGoods;

public interface GoodsDetailContract {
    public interface View {
        void showLoad();

        void hideLoad();

        void onNotNetWork();

        void onRefreshFinish();

        void onLoadMoreFinish();

        void attachData(GoodDetailEntity goodDetailEntity);

        void attachAddData(TBKHomeGoodsEntity tbkHomeGoodsEntity);

        void haseMore(boolean haseMore);

        void contenGoods(ContenGoods conten);
    }

    public interface Presenter {
        void init(String goodsId);

        void getMoreLikeGoods(String goodsId);

        void getTBKHomeGoodsListData(String goodsId);

        void getContenGoods(String goodsID);
//        void getCommissionUrl(String goodsId);
    }
}
