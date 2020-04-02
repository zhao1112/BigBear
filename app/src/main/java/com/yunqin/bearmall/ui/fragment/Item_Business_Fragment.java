package com.yunqin.bearmall.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ItemBusinessAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BusinessTabBean;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2020/3/30
 */
public class Item_Business_Fragment extends BaseFragment {


    @BindView(R.id.item_bu_table)
    TabLayout mItemBuTable;
    @BindView(R.id.bu_paper)
    ViewPager mBuPaper;
    Unbinder unbinder;

    private List<BusinessTabBean.DataBean> data = null;
    private String mBusinesstype;


    public static Item_Business_Fragment getInstance(String businessType) {
        Bundle bundle = new Bundle();
        bundle.putString("BUSINESSTYPE", businessType);
        Item_Business_Fragment fragment = new Item_Business_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_item_business;
    }

    @Override
    public void init() {

        mBusinesstype = getArguments().getString("BUSINESSTYPE");

        if ("0".equals(mBusinesstype)) {
            mItemBuTable.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        if ("1".equals(mBusinesstype)) {
            mItemBuTable.setTabMode(TabLayout.MODE_FIXED);
        }

        mItemBuTable.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                BusinessTabView businessTabView = (BusinessTabView) tab.getCustomView().getTag();
                businessTabView.tabView.setTextColor(getResources().getColor(R.color.business));
                businessTabView.tabView.setBackground(getResources().getDrawable(R.drawable.item_bg_business));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                BusinessTabView businessTabView = (BusinessTabView) tab.getCustomView().getTag();
                businessTabView.tabView.setTextColor(getResources().getColor(R.color.businessno));
                businessTabView.tabView.setBackground(getResources().getDrawable(R.drawable.item_bg_business_transparent));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getTabTitle(mBusinesstype);

    }

    private void getTabTitle(String businesstype) {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("type", businesstype);
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getBusinessCategory(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                BusinessTabBean businessTabBean = new Gson().fromJson(data, BusinessTabBean.class);
                if (businessTabBean != null && businessTabBean.getData() != null && businessTabBean.getData().size() > 0) {
                    setTabTitle(businessTabBean.getData());
                } else {

                }
                hiddenLoadingView();
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });
    }

    private void setTabTitle(List<BusinessTabBean.DataBean> data) {

        for (int i = 0; i < data.size(); i++) {
            View v = getLayoutInflater().inflate(R.layout.item_tab_business, null);
            BusinessTabView businessTabView = new BusinessTabView(v);
            v.setTag(businessTabView);
            businessTabView.tabView.setText(data.get(i).getCategoryName());
            if (i == 0) {
                businessTabView.tabView.setTextColor(getResources().getColor(R.color.white));
                businessTabView.tabView.setBackground(getResources().getDrawable(R.drawable.item_bg_business));
            }
            mItemBuTable.addTab(mItemBuTable.newTab().setCustomView(v));
        }


        ItemBusinessAdapter hotAdapter = new ItemBusinessAdapter(getActivity(), getChildFragmentManager(), data,mBusinesstype);
        mBuPaper.setAdapter(hotAdapter);
        mBuPaper.setOffscreenPageLimit(3);
        mBuPaper.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mItemBuTable));
        mItemBuTable.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBuPaper));

        int currentItem = mBuPaper.getCurrentItem();
        mItemBuTable.getTabAt(currentItem).select();
    }


    public class BusinessTabView {

        public TextView tabView;

        BusinessTabView(View view) {
            tabView = (TextView) view.findViewById(R.id.tab_view);
        }
    }

}
