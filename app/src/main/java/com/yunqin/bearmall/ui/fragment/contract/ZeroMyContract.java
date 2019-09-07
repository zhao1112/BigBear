package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.ZeroGoodsBean;

/**
 * @author AYWang
 * @create 2018/8/3
 * @Describe
 */
public interface ZeroMyContract {
    interface  UI{
        void attachData(String data, boolean isLoadMore);
        void onError();
        void onNoNet();
    }
    interface presenter{
        void start(boolean isLoadMore);
    }
}
