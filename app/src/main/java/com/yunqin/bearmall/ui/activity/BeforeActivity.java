package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.BeforeActivityBean;
import com.yunqin.bearmall.bean.CollectionGoods;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/8/9
 * @Describe 往期活动列表
 */
public class BeforeActivity extends BaseActivity {
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.activity_list)
    ListView activityList;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.empty)
    LinearLayout empty;

    private MyAdapter myAdapter;


    private Map map = new HashMap();
    private int page_numer = 1;
    private int isLoadMoreOrRefresh = 1;

    private List<BeforeActivityBean.DataBean.ActivityListBean> listBeanListView = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.activity_before;
    }

    @Override
    public void init() {
        toolbarTitle.setText("往期活动");
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myAdapter = new MyAdapter();
        activityList.setEmptyView(empty);
        activityList.setAdapter(myAdapter);


        initRefresh();
    }


    private void initRefresh() {
        refreshLayout.setHeaderView(new RefreshHeadView(this));
        refreshLayout.setBottomView(new RefreshBottomView(this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page_numer = 1;
                isLoadMoreOrRefresh = 1;
                getDatas();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                ++page_numer;
                isLoadMoreOrRefresh = 2;
                getDatas();
            }
        });

        refreshLayout.startRefresh();


        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VanguardListPageActivity.startH5Activity(BeforeActivity.this, VanguardListPageActivity.loadUrlActivity+listBeanListView.get(position).getArticle_id(), "活动详情");
            }
        });

    }

    private void getDatas() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getPastActivityList(getParams()), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                onFinishRe();
                BeforeActivityBean beforeActivityBean = new Gson().fromJson(data, BeforeActivityBean.class);
                if (beforeActivityBean.getData().getHas_more() == 0) {
                    refreshLayout.setEnableLoadmore(false);
                } else {
                    refreshLayout.setEnableLoadmore(true);
                }
                if (isLoadMoreOrRefresh == 1) {
                    listBeanListView.clear();
                }
                listBeanListView.addAll(beforeActivityBean.getData().getActivityList());
                myAdapter.notifyDataSetChanged();
//                if(listBeanListView.size()>0){
//                    empty.setVisibility(View.GONE);
//                    activityList.setVisibility(View.VISIBLE);
//                    myAdapter.notifyDataSetChanged();
//                }else {
//                    empty.setVisibility(View.VISIBLE);
//                    activityList.setVisibility(View.GONE);
//                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                onFinishRe();
            }
        });
    }

    private Map getParams() {
        map.clear();
        map.put("page_number", page_numer + "");
        return map;
    }

    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

    class  MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listBeanListView.size();
        }

        @Override
        public Object getItem(int position) {
            return listBeanListView.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyVireHoler viewHolder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(BeforeActivity.this).inflate(R.layout.item_before_activity,null);
                viewHolder = new MyVireHoler(convertView,position);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (MyVireHoler) convertView.getTag();
            }
            if(listBeanListView.size()>0){
                Glide.with(BeforeActivity.this).
                        setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_banner))
                        .load(listBeanListView.get(position).getImg()).into(viewHolder.img);
            }
            return convertView;
        }


        class MyVireHoler {
            @BindView(R.id.img)
            ImageView img;
            public MyVireHoler(View itemView,int position) {
                ButterKnife.bind(this, itemView);
            }
        }

    }
}
