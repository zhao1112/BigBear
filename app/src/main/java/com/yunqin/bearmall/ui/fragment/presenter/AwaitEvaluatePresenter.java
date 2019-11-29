package com.yunqin.bearmall.ui.fragment.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newversions.detail.order.VirtualOrdersActivity;
import com.yunqin.bearmall.adapter.AllFragmentAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.OrderBean;
import com.yunqin.bearmall.eventbus.OrderTypeUpdate;
import com.yunqin.bearmall.ui.activity.AfterSaleActivity;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.CommentActivity;
import com.yunqin.bearmall.ui.activity.HomeActivity;
import com.yunqin.bearmall.ui.activity.LogisticsActivity;
import com.yunqin.bearmall.ui.activity.MineOrderActivity;
import com.yunqin.bearmall.ui.activity.OrderDetailsActivity;
import com.yunqin.bearmall.ui.activity.PayActivity;
import com.yunqin.bearmall.ui.fragment.contract.TabFragmentContract;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class AwaitEvaluatePresenter implements TabFragmentContract.Presenter {

    private Context mContext;
    private TabFragmentContract.UI mUI;
    private AllFragmentAdapter allFragmentAdapter;

    public AwaitEvaluatePresenter(Context mContext, TabFragmentContract.UI mUI) {
        this.mContext = mContext;
        this.mUI = mUI;
    }

    @Override
    public void start(Context context, String orderStatus) {
        mUI.showLoad();

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "");
        mHashMap.put("page_number", "");
        mHashMap.put("orderStatus", orderStatus);
        mHashMap.put("queryContent", "");

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getOrdersList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {

                OrderBean orderBean = new Gson().fromJson(data, OrderBean.class);
                if (orderBean.getCode() == 1) {
                    allFragmentAdapter = new AllFragmentAdapter(context, orderBean.getData().getOrdersList());
                } else {
                    allFragmentAdapter = new AllFragmentAdapter(context, null);
                }
                mUI.attachAdapter(allFragmentAdapter);
                mUI.onHasMore(orderBean.getData().getHas_more() == 1);
                mUI.hideLoad();
            }

            @Override
            public void onNotNetWork() {
                mUI.hideLoad();
                mUI.onNotNetWork();
                mUI.onHasMore(false);
            }

            @Override
            public void onFail(Throwable e) {
                allFragmentAdapter = new AllFragmentAdapter(context, null);
                mUI.attachAdapter(allFragmentAdapter);
                mUI.hideLoad();
                mUI.onHasMore(false);
            }
        });
    }

    @Override
    public void quXiao(int index) {
        mUI.showLoad();
        Map<String, String> mHashMap = new HashMap<>();
        List<OrderBean.DataBean.OrdersListBean> list = allFragmentAdapter.getData();
        mHashMap.put("orderStatus", String.valueOf(list.get(index).getStatus()));
        mHashMap.put("orders_id", String.valueOf(list.get(index).getOrders_id()));
        mHashMap.put("orderProductType", String.valueOf(allFragmentAdapter.getData().get(index).getOrderProductType()));

        request(RetrofitApi.createApi(Api.class).cancleOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        list.get(index).setStatus(7);
                        allFragmentAdapter.setData(list);
                    } else {
                        Toast.makeText(mContext, String.format("取消失败,请稍后再试!", index), Toast.LENGTH_SHORT).show();
                    }
                    mUI.hideLoad();
                } catch (Exception e) {
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(mContext, String.format("取消失败,请稍后再试!", index), Toast.LENGTH_SHORT).show();
                mUI.hideLoad();
            }
        });
//
    }

    @Override
    public void quFuKuan(int index) {

        Bundle bundle = new Bundle();
        bundle.putString("btAmount", allFragmentAdapter.getData().get(index).getBtAmount());
        bundle.putString("amount", allFragmentAdapter.getData().get(index).getAmount());
        bundle.putString("outTradeNo", allFragmentAdapter.getData().get(index).getOutTradeNo());
        bundle.putString("ordersId", allFragmentAdapter.getData().get(index).getOrders_id() + "");
        bundle.putInt("isNeedPay", allFragmentAdapter.getData().get(index).getIsNeedPay());
        bundle.putInt("orderProductType", allFragmentAdapter.getData().get(index).getOrderProductType());
        bundle.putBoolean("isBanlancePaid", allFragmentAdapter.getData().get(index).isBanlancePaid());

        StarActivityUtil.starActivity((Activity) mContext, PayActivity.class, bundle);


    }

    @Override
    public void zaiCiGouMai(int index) {

        if (allFragmentAdapter.getData().get(index).getOrderProductType() == 1) {
            Intent intent = new Intent(mContext, ChargeActivity.class);
            mContext.startActivity(intent);
            return;
        }

        mUI.showLoad();

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orders_id", allFragmentAdapter.getData().get(index).getOrders_id() + "");

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).joinCartFromOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                mUI.hideLoad();
                JSONObject jsonObject = new JSONObject(data);
                int code = jsonObject.optInt("code");
                if (code == 1) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.putExtra("switchFragment", "TrolleyFragment");
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(mContext, "网络错误,请稍后重试!", Toast.LENGTH_SHORT).show();
                mUI.hideLoad();
            }
        });
    }

    @Override
    public void chaKanWuLiu(int index) {

        List<OrderBean.DataBean.OrdersListBean> list = allFragmentAdapter.getData();
        LogisticsActivity.start(mContext, String.valueOf(list.get(index).getOrders_id()));
    }

    @Override
    public void queRenShouHuo(int index) {

        mUI.showLoad();
        Map<String, String> mHashMap = new HashMap<>();
        List<OrderBean.DataBean.OrdersListBean> list = allFragmentAdapter.getData();
        mHashMap.put("orderStatus", String.valueOf(list.get(index).getStatus()));
        mHashMap.put("orders_id", String.valueOf(list.get(index).getOrders_id()));
        mHashMap.put("orderProductType", String.valueOf(allFragmentAdapter.getData().get(index).getOrderProductType()));

        request(RetrofitApi.createApi(Api.class).confirmOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        list.get(index).setStatus(4);
                        allFragmentAdapter.setData(list);
                    } else {
                        Toast.makeText(mContext, String.format("确认收货失败,请稍后再试!", data), Toast.LENGTH_SHORT).show();
                    }
                    mUI.hideLoad();
                } catch (Exception e) {
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(mContext, String.format("确认收货失败,请稍后再试!", e.getMessage()), Toast.LENGTH_SHORT).show();
                mUI.hideLoad();
            }
        });


    }

    @Override
    public void shaiDanPingJia(int index) {

        List<OrderBean.DataBean.OrdersListBean> list = allFragmentAdapter.getData();

        Bundle bundle = new Bundle();
        bundle.putInt("orders_id", list.get(index).getOrders_id());
        int orders_count = list.get(index).getItem().size();
        bundle.putInt("orders_count", orders_count);
        for (int i = 0; i < orders_count; i++) {
            bundle.putString(String.format("icon%d", i), list.get(index).getItem().get(i).getThumbnail());
            bundle.putInt(String.format("item%d", i), list.get(index).getItem().get(i).getProduct_id());
            StringBuffer specificationItems = new StringBuffer();
            for (String content : list.get(index).getItem().get(i).getSpecificationItems()) {
                specificationItems.append(content).append(",");
            }
            bundle.putString(String.format("SpecificationItems%d", i), specificationItems.toString());
        }
        StarActivityUtil.starActivity((Activity) mContext, CommentActivity.class, bundle);
        EventBus.getDefault().post(new OrderTypeUpdate(MineOrderActivity.FragmentType.AWAIT_PINGJIA));

    }

    @Override
    public void shanChuDingDan(int index) {

        mUI.showLoad();
        Map<String, String> mHashMap = new HashMap<>();
        List<OrderBean.DataBean.OrdersListBean> list = allFragmentAdapter.getData();
        mHashMap.put("orderStatus", String.valueOf(list.get(index).getStatus()));
        mHashMap.put("orders_id", String.valueOf(list.get(index).getOrders_id()));
        mHashMap.put("orderProductType", String.valueOf(allFragmentAdapter.getData().get(index).getOrderProductType()));

        request(RetrofitApi.createApi(Api.class).deleteOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        list.remove(index);
                        allFragmentAdapter.setData(list);
                    } else {
                        Toast.makeText(mContext, String.format("删除订单失败,请稍后再试!", data), Toast.LENGTH_SHORT).show();
                    }
                    mUI.hideLoad();
                } catch (Exception e) {
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(mContext, String.format("删除订单失败,请稍后再试!", e.getMessage()), Toast.LENGTH_SHORT).show();
                mUI.hideLoad();
            }
        });


    }

    @Override
    public void shenQingShouHou(int index) {
        List<OrderBean.DataBean.OrdersListBean> list = allFragmentAdapter.getData();
        Bundle bundle = new Bundle();
        bundle.putString("Orders_id", "" + list.get(index).getOrders_id());
        bundle.putString("orderProductType", "" + list.get(index).getOrderProductType());

        Intent intent = new Intent(mContext, AfterSaleActivity.class);
        intent.putExtras(bundle);
        intent.putExtra("source_id",MineOrderActivity.FragmentType.AWAIT_PINGJIA);
        mContext.startActivity(intent);
    }

    @Override
    public void chaKanXiangQing(int index, int childIndex) {
        List<OrderBean.DataBean.OrdersListBean> list = allFragmentAdapter.getData();

        if (list.get(index).getOrderProductType() == 0) {
            OrderDetailsActivity.start(mContext, list.get(index).getOrders_id(), MineOrderActivity.FragmentType.AWAIT_PINGJIA, list.get(index).getOrderProductType());
        } else {
            VirtualOrdersActivity.start(mContext, list.get(index).getOrders_id(),MineOrderActivity.FragmentType.AWAIT_PINGJIA);
        }


    }


    @Override
    public void shuaXin() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "");
        mHashMap.put("page_number", "");
        mHashMap.put("orderStatus", "4");
        mHashMap.put("queryContent", "");


        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getOrdersList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {

                page_number = 2;

                OrderBean orderBean = new Gson().fromJson(data, OrderBean.class);

                if (allFragmentAdapter == null) {
                    allFragmentAdapter = new AllFragmentAdapter(mContext, orderBean.getData().getOrdersList());
                    mUI.attachAdapter(allFragmentAdapter);
                } else {
                    allFragmentAdapter.setData(orderBean.getData().getOrdersList());
                    mUI.updateView(allFragmentAdapter);
                }

                mUI.shuaXinFinish();
                mUI.onHasMore(orderBean.getData().getHas_more() == 1);
            }

            @Override
            public void onNotNetWork() {
                mUI.onHasMore(false);
            }

            @Override
            public void onFail(Throwable e) {
                if (allFragmentAdapter == null) {
                    allFragmentAdapter = new AllFragmentAdapter(mContext, null);
                    mUI.attachAdapter(allFragmentAdapter);
                } else {

                }
                mUI.shuaXinFinish();
                mUI.onHasMore(false);
            }
        });
    }


    private int page_number = 2;

    @Override
    public void jiaZaiGengDuo() {

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "");
        mHashMap.put("page_number", "" + page_number++);
        mHashMap.put("orderStatus", "4");
        mHashMap.put("queryContent", "");


        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getOrdersList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {

                OrderBean orderBean = new Gson().fromJson(data, OrderBean.class);
                if (allFragmentAdapter == null) {
                    allFragmentAdapter = new AllFragmentAdapter(mContext, orderBean.getData().getOrdersList());
                } else {
                    allFragmentAdapter.addData(orderBean.getData().getOrdersList());
                }
                mUI.jiaZaiGengDuoFinish(orderBean.getData().getHas_more() == 1);
                mUI.onHasMore(orderBean.getData().getHas_more() == 1);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                mUI.jiaZaiGengDuoFinish(false);
                mUI.onHasMore(false);
            }
        });


    }

    @Override
    public AllFragmentAdapter getAdapter() {
        return allFragmentAdapter;
    }


    private void request(Observable<String> observable, RetrofitApi.IResponseListener iResponseListener) {
        RetrofitApi.request(mContext, observable, iResponseListener);
    }


}
