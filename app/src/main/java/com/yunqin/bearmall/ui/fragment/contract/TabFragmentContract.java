package com.yunqin.bearmall.ui.fragment.contract;

import android.content.Context;

import com.yunqin.bearmall.adapter.AllFragmentAdapter;
import com.yunqin.bearmall.bean.OrderBean;

import java.util.List;

public interface TabFragmentContract {


    interface Model {

    }

    interface Presenter {


        void start(Context context, String orderStatus);


        void quXiao(int index);

        void quFuKuan(int index);

        void zaiCiGouMai(int index);

        void chaKanWuLiu(int index);

        void queRenShouHuo(int index);

        void shaiDanPingJia(int index);

        void shanChuDingDan(int index);

        void shenQingShouHou(int index);

        void chaKanXiangQing(int index, int childIndex);


        void shuaXin();

        void jiaZaiGengDuo();

        AllFragmentAdapter getAdapter();


    }

    interface UI {
        void attachAdapter(AllFragmentAdapter adapter);

        void updateView(AllFragmentAdapter adapter);

        void showLoad();

        void hideLoad();

        void shuaXinFinish();

        void jiaZaiGengDuoFinish(boolean isMore);

        void onNotNetWork();

        void onHasMore(boolean hasMore);

        void updateView();

    }


}
