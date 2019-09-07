package com.newversions.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.RecommendAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.SearchBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Master
 * On 2019/1/15 13:05
 */
public class NewVersionChoicenessView extends LinearLayout {

    private Context context;
    private RecyclerView choiceness_recycler_view;

    public NewVersionChoicenessView(Context context) {
        this(context, null);
    }

    public NewVersionChoicenessView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewVersionChoicenessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.new_version_choiceness_layout, this, true);
        choiceness_recycler_view = findViewById(R.id.choiceness_recycler_view);
        choiceness_recycler_view.setNestedScrollingEnabled(false);
    }

    public void setManager(RecyclerView.LayoutManager manager) {
        choiceness_recycler_view.setLayoutManager(manager);
    }

    public void init() {


        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "60");

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getRecommendProductList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                SearchBean searchBean = new Gson().fromJson(data, SearchBean.class);
                choiceness_recycler_view.setAdapter(new RecommendAdapter(context, searchBean));
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