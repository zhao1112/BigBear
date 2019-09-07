package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.newversions.NewOrderBean;
import com.newversions.NewOrderChildBean;
import com.yunqin.bearmall.adapter.MoreTypeViewAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.ConfirmActivityContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Master
 */
public class ConfirmActivityPresenter implements ConfirmActivityContract.Presenter {

    Context mContext;
    ConfirmActivityContract.UI mView;
    MoreTypeViewAdapter mMoreTypeViewAdapter;

    public ConfirmActivityPresenter(Context context, ConfirmActivityContract.UI view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void start(String orderType, String areaName, String bargainRecord_id, String usersTicketLog_id, List<NewOrderBean> list) {
        List<Object> list1 = new ArrayList<>();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orderType", orderType);
        mHashMap.put("areaName", areaName);
        mHashMap.put("bargainRecord_id", bargainRecord_id);
        mHashMap.put("usersTicketLog_id", usersTicketLog_id);

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < list.size(); i++) {
            NewOrderBean newOrderBean = list.get(i);
            list1.add(newOrderBean);
            for (int j = 0; j < newOrderBean.getChildBeans().size(); j++) {
                try {
                    NewOrderChildBean newOrderChildBean = newOrderBean.getChildBeans().get(j);
                    list1.add(newOrderChildBean);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("product_id", newOrderChildBean.getProductId());
                    jsonObject.put("sku_id", newOrderChildBean.getSkuId());
                    jsonObject.put("quantity", newOrderChildBean.getCount());
                    jsonObject.put("isMsp", newOrderChildBean.getiMap());
                    jsonArray.put(jsonObject);
                } catch (Exception e) {
                }
            }
        }
        mHashMap.put("orderItemList", jsonArray.toString());

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).calculateOrderAmount(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");


                    mView.yunFei(jsonObject1.optString("freight"));
                    mView.tangGuo(jsonObject1.optString("btAmount"));
                    mView.money(jsonObject1.optString("price"));
                    mView.totalMoney(jsonObject1.optString("amount"));
                    mView.youhuiMoney(jsonObject1.optString("discount"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
        mMoreTypeViewAdapter = new MoreTypeViewAdapter(mContext, list1);
        mView.attachAdapter(mMoreTypeViewAdapter);
    }

    @Override
    public void refresh(String orderType, String areaName, String bargainRecord_id, String usersTicketLog_id, List<NewOrderBean> list) {
        List<Object> list1 = new ArrayList<>();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orderType", orderType);
        mHashMap.put("areaName", areaName);
        mHashMap.put("bargainRecord_id", bargainRecord_id);
        mHashMap.put("usersTicketLog_id", usersTicketLog_id);

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < list.size(); i++) {
            NewOrderBean newOrderBean = list.get(i);
            list1.add(newOrderBean);
            for (int j = 0; j < newOrderBean.getChildBeans().size(); j++) {
                try {
                    NewOrderChildBean newOrderChildBean = newOrderBean.getChildBeans().get(j);
                    list1.add(newOrderChildBean);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("product_id", newOrderChildBean.getProductId());
                    jsonObject.put("sku_id", newOrderChildBean.getSkuId());
                    jsonObject.put("quantity", newOrderChildBean.getCount());
                    jsonObject.put("isMsp", newOrderChildBean.getiMap());
                    jsonArray.put(jsonObject);
                } catch (Exception e) {
                }
            }
        }
        mHashMap.put("orderItemList", jsonArray.toString());

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).calculateOrderAmount(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                    mView.yunFei(jsonObject1.optString("freight"));
                    mView.tangGuo(jsonObject1.optString("btAmount"));
                    mView.money(jsonObject1.optString("price"));
                    mView.totalMoney(jsonObject1.optString("amount"));
                    mView.youhuiMoney(jsonObject1.optString("discount"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }


}
