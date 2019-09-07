package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.adapter.MenuOneAdapter;
import com.yunqin.bearmall.adapter.MenuTwoAdapter;
import com.yunqin.bearmall.bean.Menu;
import com.yunqin.bearmall.bean.ShopBean;

import java.util.List;
import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/12
 * @Describe
 */
public interface ShopActivtyContract {

    interface  Model{
        void getShopData(Map map);
        void collectionShop(Map map);
    }
    interface UI{

        Map getParams();

        void isCollectionShop();

        void initBinner(List<ShopBean.DataBean.AdListBean> adListBeans);

        void initXtableLayout(List<ShopBean.DataBean.TagListBean> lables);

        void initTopLayout(ShopBean shopBean);
    }
    interface Persenter{
        void star();
        void collectionShop(Map map);
    }

    interface shopDataCallBack{
        void shopData(ShopBean shopBean);
    }

    interface collectionShopCallBack{
        void success();
    }


}
