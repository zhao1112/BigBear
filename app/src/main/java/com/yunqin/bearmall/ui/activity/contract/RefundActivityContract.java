package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;

import com.yunqin.bearmall.adapter.RefundAdapter;

/**
 * @author Master
 * @create 2018/7/28 11:22
 */
public interface RefundActivityContract {


    interface Model {

    }

    interface UI {
        void attachAdapter(RefundAdapter adapter);
        void showLoad();
        void hideLoad();
        void jiaZaiGengDuoFinish(boolean isMore);
        void shuaXinFinish();
        void onNotNetWork();
    }

    interface Presenter {
        void init(Context context);
        void shuaXin(Context context);
        void jiaZaiGengDuo(Context context);
    }


}
