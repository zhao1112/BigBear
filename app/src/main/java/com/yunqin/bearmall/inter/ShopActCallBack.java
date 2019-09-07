package com.yunqin.bearmall.inter;

import com.yunqin.bearmall.bean.ShopActivityBean;

/**
 * @author AYWang
 * @create 2018/8/10
 * @Describe
 */
public interface ShopActCallBack {
        //1 0元拼团  2 发起砍价
        void clickBtn(ShopActivityBean.DataBean.ListBean listBean,int flag);
}
