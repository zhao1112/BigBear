package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;

import com.yunqin.bearmall.adapter.MoreTypeViewAdapter;
import com.yunqin.bearmall.bean.CartItem;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/8/14
 * @Describe
 */
public interface DailyContract {
    interface UI {
        void getTaskListInfo(String data);
        void onFail();
    }

    interface Presenter {
        void initTaskListInfo();
    }
}
