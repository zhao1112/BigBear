package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.HomeAd;
import com.yunqin.bearmall.bean.HomeBean;
import com.yunqin.bearmall.ui.fragment.contract.BearRecommendFragmentContract;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Master
 */
public class BearRecommendPresenter implements BearRecommendFragmentContract.Presenter {

    private Context iContext;
    private BearRecommendFragmentContract.UI iUI;
    private MoreTypeRecycleViewAdapter mMoreTypeRecycleViewAdapter;
    private HomeAd homeAd;
    private HomeBean homeBean;

    public BearRecommendPresenter(Context context, BearRecommendFragmentContract.UI view) {
        this.iContext = context;
        this.iUI = view;
    }

    @Override
    public void start() {


        /**
         *  更新商品数据
         */

        iUI.showLoad();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "1");

        RetrofitApi.request(iContext, RetrofitApi.createApi(Api.class).getHomeListData(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                homeBean = new Gson().fromJson(data, HomeBean.class);
                iUI.onHasMore(homeBean.getData().getHas_more() == 1);
                mMoreTypeRecycleViewAdapter = new MoreTypeRecycleViewAdapter(iContext, homeBean);
                initTopTypeData();
                init5();
            }

            @Override
            public void onNotNetWork() {
                iUI.hideLoad();
                iUI.onNotNetWork();
                iUI.onHasMore(false);
            }

            @Override
            public void onFail(Throwable e) {
                iUI.hideLoad();
                iUI.onHasMore(false);
                Toast.makeText(iContext, "失败商品数据:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initTopTypeData() {
        RetrofitApi.request(iContext, RetrofitApi.createApi(Api.class).getHomeAdData(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                homeAd = new Gson().fromJson(data, HomeAd.class);
                init1();
                init2();
                init3();
                init4();
                iUI.hideLoad();
            }

            @Override
            public void onNotNetWork() {
                iUI.hideLoad();
                iUI.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                iUI.hideLoad();
                Toast.makeText(iContext, "失败广告数据:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void init1() {
        List<HomeAd.DataBean.AdBean> lists = homeAd.getData().getAdMobileTopList();
        iUI.initBannerTop(mMoreTypeRecycleViewAdapter, lists);
    }

    @Override
    public void init2() {
        iUI.initLinearMenu(mMoreTypeRecycleViewAdapter);
    }

    @Override
    public void init3() {
        List<HomeAd.DataBean.AdBean> lists = homeAd.getData().getAdMobileMidList();
        iUI.initFeaturedFirstHolder3(mMoreTypeRecycleViewAdapter, lists);
    }

    @Override
    public void init4() {
        List<HomeAd.DataBean.AdBean> lists = homeAd.getData().getAdMobileCrossList();
        iUI.initFeaturedFirstHolder1(mMoreTypeRecycleViewAdapter, lists);
    }

    @Override
    public void init5() {
        iUI.attachAdapter(mMoreTypeRecycleViewAdapter);
    }


    private int page_number = 2;


    @Override
    public void shuaXin() {

        page_number = 2;
        /**
         *  更新商品数据
         */

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "1");

        RetrofitApi.request(iContext, RetrofitApi.createApi(Api.class).getHomeListData(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                homeBean = new Gson().fromJson(data, HomeBean.class);
                if (mMoreTypeRecycleViewAdapter == null) {
                    mMoreTypeRecycleViewAdapter = new MoreTypeRecycleViewAdapter(iContext, homeBean);
                    updateAdv();
                    iUI.attachAdapter(mMoreTypeRecycleViewAdapter);
                } else {
                    updateAdv();
                }
                iUI.onHasMore(homeBean.getData().getHas_more() == 1);
            }

            @Override
            public void onNotNetWork() {
                iUI.shuaXinFinish();
                iUI.onHasMore(false);
                iUI.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(iContext, "失败商品数据:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                iUI.shuaXinFinish();
                iUI.onHasMore(false);
            }
        });
    }

    private void updateAdv() {
        RetrofitApi.request(iContext, RetrofitApi.createApi(Api.class).getHomeAdData(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                homeAd = new Gson().fromJson(data, HomeAd.class);
                init1();
                init2();
                init3();
                init4();
                iUI.attachAdapter(mMoreTypeRecycleViewAdapter, homeBean);
                iUI.shuaXinFinish();
            }

            @Override
            public void onNotNetWork() {
                iUI.shuaXinFinish();
                iUI.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                iUI.shuaXinFinish();
                Toast.makeText(iContext, "失败广告数据:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void jiaZaiGengDuo() {

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "" + page_number++);

        RetrofitApi.request(iContext, RetrofitApi.createApi(Api.class).getHomeListData(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                homeBean = new Gson().fromJson(data, HomeBean.class);
                mMoreTypeRecycleViewAdapter.addData(homeBean);
                iUI.jiaZaiGengDuoFinish();
                iUI.onHasMore(homeBean.getData().getHas_more() == 1);

            }

            @Override
            public void onNotNetWork() {
                iUI.jiaZaiGengDuoFinish();
                iUI.onHasMore(false);
                iUI.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(iContext, "失败商品数据:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                iUI.jiaZaiGengDuoFinish();
                iUI.onHasMore(false);
            }
        });


    }


}
