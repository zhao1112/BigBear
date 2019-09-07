package com.yunqin.bearmall.ui.activity.contract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/19
 * @Describe
 */
public interface LoginActivityContract {
    interface  model{
    }
    interface UI{
        void isBindPhone(String data);
        void onerror();
    }
    interface presenter{
        void start(Map map);
    }
}
