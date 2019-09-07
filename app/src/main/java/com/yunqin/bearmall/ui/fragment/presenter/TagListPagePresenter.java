package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.MenuGoods;
import com.yunqin.bearmall.ui.fragment.contract.TagListPageContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/30
 * @Describe
 */
public class TagListPagePresenter implements TagListPageContract.Presenter {

    private TagListPageContract.UI view;
    private Context mContext;
    private Map map = new HashMap();
    private int page_number = 1;

    public TagListPagePresenter(Context mContext, TagListPageContract.UI view) {
        this.view = view;
        this.mContext = mContext;
    }

    boolean isMember = false;

    public void setMember(boolean isMember) {
        this.isMember = isMember;
    }


    @Override
    public void start(boolean isLoadMore, String category_id) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMenuProductList(getMap(isLoadMore, category_id)), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    MenuGoods menuGoods = new Gson().fromJson(data, MenuGoods.class);
                    view.onHasMore(menuGoods.getData().getHas_more() == 1);
                    view.attachData(menuGoods, isLoadMore);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    view.onError();
                }

            }

            @Override
            public void onNotNetWork() {
                view.onHasMore(false);
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onHasMore(false);
                view.onError();
            }
        });
    }

    private Map getMap(boolean isLoadMore, String category_id) {
        map.clear();
        if (!isLoadMore) {
            page_number = 1;
        } else {
            page_number++;
        }
        map.put("page_number", page_number + "");
        map.put("category_id", category_id);
        map.put("memberType", isMember ? "1" : "0");


        Log.e("CCCCCC==== ",isMember ? "1" : "0");



        return map;
    }

}
