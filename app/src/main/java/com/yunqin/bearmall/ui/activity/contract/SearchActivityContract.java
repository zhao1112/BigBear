package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;

import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.MenuGoods;

import java.util.List;

public interface SearchActivityContract {

    interface Model {

    }

    interface Presenter {
        void start(Context context);

        void searchData(Context context, String searchValue);

        void onTextChangedSearch(Context context, String value, RetrofitApi.IResponseListener listener);
    }

    interface UI {
        void attachHotSearch(List<String> list);

        void attachAddText(String text);

        void startEmptyFragment();

        void startFragment(String mMenuGoods);

        void showLoadings();

        void hideLoadings();

        String getMemberType();

        void onNotNet(boolean show);


    }

}
