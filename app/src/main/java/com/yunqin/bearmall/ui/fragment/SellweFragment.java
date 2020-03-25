package com.yunqin.bearmall.ui.fragment;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.newversions.tbk.activity.ProductSumActivity2;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.HotAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.HotBean;
import com.yunqin.bearmall.ui.activity.SellwellActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2020/3/21
 */
public class SellweFragment extends BaseFragment {


    @BindView(R.id.xtablelayout_3)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.image_top)
    ImageView image_top;
    private String mType;


    @Override
    public int layoutId() {
        return R.layout.frag_sellw;
    }

    @Override
    public void init() {

        mType = getArguments().getString("TYPE");


        List<String> stringList = new ArrayList<>();
        stringList.add("综合");
        stringList.add("女装");
        stringList.add("母婴");
        stringList.add("美妆");
        stringList.add("居家日用");
        stringList.add("鞋品");
        stringList.add("美食");
        stringList.add("文娱车品");
        stringList.add("数码家电");
        stringList.add("男装");
        stringList.add("内衣");
        stringList.add("箱包");
        stringList.add("配饰");
        stringList.add("户外运动");
        stringList.add("家装家纺");

        for (int i = 0; i < stringList.size(); i++) {
            View v = getLayoutInflater().inflate(R.layout.item_tab, null);
            TabViewHolder2 holder = new TabViewHolder2(v);
            v.setTag(holder);
            holder.tv.setText(stringList.get(i));
            holder.tv.setTextColor(getResources().getColor(R.color.Twxz));
            if (i == 0) {
                holder.tv.setTextColor(getResources().getColor(R.color.white));
                holder.im.setImageResource(R.mipmap.hover);
            }

            tablayout.addTab(tablayout.newTab().setCustomView(v));
        }


        HotAdapter hotAdapter = new HotAdapter(getActivity(), getChildFragmentManager(), stringList, mType);
        viewPager.setAdapter(hotAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabViewHolder2 holder = (TabViewHolder2) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.white));
                holder.im.setImageResource(R.mipmap.hover);
                holder.im.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabViewHolder2 holder = (TabViewHolder2) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.Twxz));
                holder.im.setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getListData();

    }

    public class TabViewHolder2 {

        TextView tv;
        ImageView im;

        TabViewHolder2(View v) {
            tv = v.findViewById(R.id.textView_3);
            im = v.findViewById(R.id.imageView_2);
        }
    }

    private void getListData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(1));
        map.put("pageSize", String.valueOf(10));
        map.put("cid", "0");
        map.put("type", mType);
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getHotSelling(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        HotBean searchData = new Gson().fromJson(data, HotBean.class);
                        if (searchData != null && searchData.getCommodityList() != null && searchData.getCommodityList().size() > 0) {
                            try {
                                Glide.with(getActivity())
                                        .load(searchData.getPlatformList().getImage())
                                        .apply(new RequestOptions().placeholder(R.drawable.default_product))
                                        .into(image_top);
                                tablayout.setBackgroundColor(Color.parseColor(searchData.getPlatformList().getContent()));
                                viewPager.setBackgroundColor(Color.parseColor(searchData.getPlatformList().getContent()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
