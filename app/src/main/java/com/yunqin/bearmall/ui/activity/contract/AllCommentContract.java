package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.bean.BearFAQ;
import com.yunqin.bearmall.bean.WaitCommentBean;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/26
 * @Describe
 */
public interface  AllCommentContract {
    interface M{
    }

    interface UI{
        void attachData(WaitCommentBean waitCommentBean);
    }

    interface Present extends BaseContract.BasePresenter{
    }
}
