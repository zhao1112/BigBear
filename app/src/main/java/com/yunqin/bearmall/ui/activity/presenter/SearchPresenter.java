package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.MenuGoods;
import com.yunqin.bearmall.ui.activity.contract.SearchActivityContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPresenter implements SearchActivityContract.Presenter {


    SearchActivityContract.UI view;


    public SearchPresenter(SearchActivityContract.UI view) {
        this.view = view;
    }

    @Override
    public void start(Context context) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getHotSearchList(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                /**
                 * {
                 "data": "苹果,三星,索尼,华为,魅族,佳能,小米,美的,格力",
                 "code": 1,
                 "msg": "请求成功"
                 }
                 */
                List<String> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String content = jsonObject.getString("data");
                    if (content != null && !"".equals(content) && !"null".equals(content)) {
                        String[] datas = content.split(",");
                        for (int i = 0; i < datas.length; i++) {
                            list.add(datas[i]);
                        }
                        view.onNotNet(false);
                        view.attachHotSearch(list);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {
                view.onNotNet(true);
            }

            @Override
            public void onFail(Throwable e) {
                view.onNotNet(true);
            }
        });
    }

    @Override
    public void searchData(Context context, String searchValue) {

        view.showLoadings();
        view.attachAddText(searchValue);

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "1");
        mHashMap.put("category_id", "");
        mHashMap.put("brand_id", "");
        mHashMap.put("orderType", "");
        mHashMap.put("searchValue", searchValue);
        mHashMap.put("attr_", "");
        mHashMap.put("memberType", view.getMemberType());

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMenuProductList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                view.hideLoadings();
                try {
                    MenuGoods menuGoods = new Gson().fromJson(data, MenuGoods.class);
                    if (menuGoods != null && menuGoods.getCode() == 1 && menuGoods.getData() != null && menuGoods.getData().getProductList().size() > 0) {
                        view.startFragment(data);
                    } else {
                        view.startEmptyFragment();
                    }
                    view.onNotNet(false);
                } catch (JsonSyntaxException e) {
                    view.startEmptyFragment();
                }

            }

            @Override
            public void onNotNetWork() {
                view.onNotNet(true);
            }

            @Override
            public void onFail(Throwable e) {
                view.onNotNet(true);
                view.hideLoadings();
                view.startEmptyFragment();
            }
        });
    }

    @Override
    public void onTextChangedSearch(Context context, String value, RetrofitApi.IResponseListener listener) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("searchValue", value);
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getSearchMatchList(mHashMap), listener);
    }
}
