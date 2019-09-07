package com.yunqin.bearmall.ui.fragment.contract;

import android.content.Context;
import android.support.annotation.IntDef;

import com.yunqin.bearmall.adapter.MenuGoodsAdapter;
import com.yunqin.bearmall.adapter.MenuShopGoodsAdapter;
import com.yunqin.bearmall.adapter.MenuShopsAdapter;
import com.yunqin.bearmall.bean.ShopGoods;

import java.util.Map;

import static com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract.Rank.DOWN;
import static com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract.Rank.UP;

public interface ShopGoodsContract {

    interface Model {
        void getShopProductList(Context context, Map map);
    }

    interface Presenter {
        void star();
        void onShuaXin();
        void onJiaZai();
    }

    interface UI {
        void attachAdapter(MenuShopGoodsAdapter adapter,ShopGoods shopGoods);
        Map getParams();
        void onHasMore(boolean hasMore);
        void shuaXinFinish();
        void jiaZaiFinish();

    }

    interface onPresenterModelListener {
        void onSuccess(String data);

        void onFail();
    }


}
