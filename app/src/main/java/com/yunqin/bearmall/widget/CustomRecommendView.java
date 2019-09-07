package com.yunqin.bearmall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.RecommendAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.SearchBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 由于多处用到推荐商品布局
 * 封装成一自定义View 来统一处理
 */
public class CustomRecommendView extends LinearLayout {

    ImageView custom_img_bg;
    TextView tv_recommend;
    TextView custom_tv;
    RecyclerView custom_view_recycler;
    RelativeLayout top_title;
    View divier;


    public CustomRecommendView(Context context) {
        this(context, null);
    }

    public CustomRecommendView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecommendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_recommend_view, this, true);
        custom_img_bg = findViewById(R.id.custom_img_bg);
        custom_tv = findViewById(R.id.custom_tv);
        tv_recommend = findViewById(R.id.tv_recommend);
        custom_view_recycler = findViewById(R.id.custom_view_recycler);
        divier = findViewById(R.id.divier);
        //实现优化  固定大小
        custom_view_recycler.setHasFixedSize(true);
        top_title = findViewById(R.id.top_title);
    }

    public void setImageSrc(int icon) {
        custom_img_bg.setBackgroundResource(icon);
    }

    public void setTvContent(String content) {
        custom_tv.setText(content);
    }

    public void setManager(RecyclerView.LayoutManager manager) {
        custom_view_recycler.setLayoutManager(manager);
    }

    public void setTvBottom(String content) {
        tv_recommend.setText(content);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        custom_view_recycler.setAdapter(adapter);
    }

    public void start(Context context) {

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "60");

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getRecommendProductList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                SearchBean searchBean = new Gson().fromJson(data, SearchBean.class);
                setAdapter(new RecommendAdapter(context, searchBean));
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }


    public void hideTopLayout() {
        top_title.setVisibility(GONE);
    }

    public void showTopLayout() {
        top_title.setVisibility(VISIBLE);
    }


    public void setTopTextViewBgColor(int color) {
        tv_recommend.setBackgroundColor(color);
    }

    public void setTopTextViewHeight(float height) {
//        tv_recommend.setHeight((int)height);
//        tv_recommend.invalidate();

        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) tv_recommend.getLayoutParams();
        //获取当前控件的布局对象
        params.height=(int)height;//设置当前控件布局的高度
        tv_recommend.setLayoutParams(params);//将设置好的布局参数应用到控件中
    }

    public void setTopTextViewLeft() {
        tv_recommend.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
    }

    public void setDiviervisibility(int diviervisibility) {
        divier.setVisibility(diviervisibility);
    }


}
