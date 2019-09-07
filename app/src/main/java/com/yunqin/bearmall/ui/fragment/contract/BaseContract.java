package com.yunqin.bearmall.ui.fragment.contract;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;

public interface BaseContract {

    interface BaseModel {

    }

    interface BasePresenter extends LifecycleObserver {
        void start(Context context);
    }

    interface BaseView {

    }

}
