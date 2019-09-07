package com.yunqin.bearmall.ui.activity.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.adapter.AddressAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.ui.activity.contract.AddressActivityContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressPresenter implements AddressActivityContract.Presenter {

    AddressActivityContract.UI view;

    private AddressAdapter addressAdapter;

    public AddressPresenter(AddressActivityContract.UI view) {
        this.view = view;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        assert view != null;
        view = null;
    }

    @Override
    public void performClick(AddressListBean.DataBean bean, int type) {
        if (type == AddressActivityContract.PerformClick.EDIT) {
            // TODO 编辑
        } else if (type == AddressActivityContract.PerformClick.DEL) {
            // TODO 删除
            List<AddressListBean.DataBean> list = addressAdapter.getData();
            list.remove(bean);
            addressAdapter.setData(list);
        } else if (type == AddressActivityContract.PerformClick.DEFAUL) {
            // TODO 设置默认
            List<AddressListBean.DataBean> list = addressAdapter.getData();
            for (int i = 0; i < list.size(); i++) {
                if (bean == list.get(i)) {
                    list.get(i).setIsDefault(true);
                    continue;
                }
                list.get(i).setIsDefault(false);
            }
            addressAdapter.setData(list);
        }
    }

    @Override
    public void start(Context context) {
        view.showLoad();
        Map<String, String> mHashMap = new HashMap<>();
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getReceiverList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                view.hideLoad();
                AddressListBean bean = new Gson().fromJson(data, AddressListBean.class);
                addressAdapter = new AddressAdapter(context, bean.getData());
                view.attachAdapter(addressAdapter);
            }

            @Override
            public void onNotNetWork() {
                view.hideLoad();
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.hideLoad();
            }
        });
    }
}
