package com.newreward.contract;


import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import io.reactivex.Observable;

/**
 * @author AYWang
 * @create 2019/1/22
 * @Describe
 */
public interface SpecialRequestContract {
    interface Model{
        Observable<String> getSpecInvitationPageInfo();
    }
    interface UI extends BaseContract.BaseView{
        void setSpecPageInfo(String data);
        void setShareData(ShareBean.DataBean shareBean);
        void onNetFail();
        void getShareDataFaile();
    }

    interface Presenter{
        void getShareData();
    }
}
