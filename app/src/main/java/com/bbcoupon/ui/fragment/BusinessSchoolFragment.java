package com.bbcoupon.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bbcoupon.ui.activity.ArticleActivity;
import com.bbcoupon.ui.activity.ArticleListTwoActivity;
import com.bbcoupon.ui.activity.ArticleSearchActivity;
import com.bbcoupon.ui.adapter.SchoolAdapter;
import com.bbcoupon.ui.bean.SchoolInfor;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.fragment
 * @DATE 2020/6/1
 */
public class BusinessSchoolFragment extends BaseFragment {
    @BindView(R.id.sc_recycler)
    RecyclerView mScRecycler;
    @BindView(R.id.sc_refresh)
    TwinklingRefreshLayout mScRefresh;

    private SchoolAdapter schoolAdapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_b_school;
    }

    @Override
    public void init() {

        mScRefresh.setHeaderView(new RefreshHeadView(getActivity()));
        mScRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });

        mScRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        schoolAdapter = new SchoolAdapter(getActivity());
        mScRecycler.setAdapter(schoolAdapter);

        //文章詳情
        schoolAdapter.setOnArticle(new SchoolAdapter.OnArticle() {
            @Override
            public void setArticle() {
                startActivity(new Intent(getActivity(), ArticleActivity.class));
            }

            @Override
            public void setArticelIcon() {
                Log.e("iconContentHolder", "onClick: 3");
                Bundle bundle = new Bundle();
                ArticleListTwoActivity.openArticleListTwoActivity(getActivity(),ArticleListTwoActivity.class,bundle);
            }
        });

        SchoolInfor schoolInfor = new SchoolInfor();
        schoolInfor.setSearch(1.20);
        List<String> bannerlist = new ArrayList<>();
        bannerlist.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2972391495,2782219748&fm=26&gp=0.jpg");
        bannerlist.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3261518574,2028926837&fm=26&gp=0.jpg");
        bannerlist.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2694467485,2818553963&fm=26&gp=0.jpg");
        bannerlist.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3543644120,2628946092&fm=11&gp=0.jpg");
        bannerlist.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2000436488,3631662985&fm=26&gp=0.jpg");
        schoolInfor.setBanner(bannerlist);
        List<SchoolInfor.Icon> iconList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            SchoolInfor.Icon icon = new SchoolInfor.Icon();
            if (i % 2 == 0) {
                iconList.add(icon);
                icon.setItem("http://img5.imgtn.bdimg.com/it/u=4089178969,726909818&fm=26&gp=0.jpg");
            } else {
                iconList.add(icon);
                icon.setItem("http://img1.imgtn.bdimg.com/it/u=2326324663,2897927302&fm=26&gp=0.jpg");
            }
        }
        schoolInfor.setIconList(iconList);
        SchoolInfor.Data data = new SchoolInfor.Data();
        List<SchoolInfor.Data> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(data);
        }
        schoolInfor.setList(list);

        schoolAdapter.addDataList(schoolInfor);

    }


    @OnClick(R.id.art_search)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), ArticleSearchActivity.class));
    }
}
