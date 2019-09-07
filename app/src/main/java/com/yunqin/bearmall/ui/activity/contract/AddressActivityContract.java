package com.yunqin.bearmall.ui.activity.contract;

import android.support.annotation.IntDef;

import com.yunqin.bearmall.adapter.AddressAdapter;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import static com.yunqin.bearmall.ui.activity.contract.AddressActivityContract.PerformClick.DEFAUL;
import static com.yunqin.bearmall.ui.activity.contract.AddressActivityContract.PerformClick.DEL;
import static com.yunqin.bearmall.ui.activity.contract.AddressActivityContract.PerformClick.EDIT;

public interface AddressActivityContract {

    interface Model {

    }

    interface Presenter extends BaseContract.BasePresenter {

        void onDestroy();

        void performClick(AddressListBean.DataBean bean, @PerformClick int type);

    }

    interface UI extends BaseContract.BaseView {
        void attachAdapter(AddressAdapter addressAdapter);
        void onNotNetWork();
        void showLoad();
        void hideLoad();
    }

    @IntDef({EDIT, DEL, DEFAUL})
    @interface PerformClick {
        int EDIT = 0;
        int DEL = 1;
        int DEFAUL = 2;
    }


}
