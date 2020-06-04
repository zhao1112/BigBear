package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.ArticleListAdapter;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.widget.RefreshHeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/6/4
 */
public class ArticleListTwoActivity extends BaseActivity {


    @BindView(R.id.artw_title)
    TextView mArtwTitle;
    @BindView(R.id.artw_recycler)
    RecyclerView mArtwRecycler;
    @BindView(R.id.artw_refresh)
    TwinklingRefreshLayout mArtwRefresh;

    private String articlecontent;
    private ArticleListAdapter listAdapter;

    public static void openArticleListTwoActivity(Activity activity, Class cla, Bundle bundle) {
        Intent intent = new Intent(activity, cla);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_article_two_list;
    }

    @Override
    public void init() {


        mArtwRefresh.setHeaderView(new RefreshHeadView(ArticleListTwoActivity.this));
        mArtwRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });

        listAdapter = new ArticleListAdapter(ArticleListTwoActivity.this);
        mArtwRecycler.setLayoutManager(new LinearLayoutManager(ArticleListTwoActivity.this));
        mArtwRecycler.setAdapter(listAdapter);
    }


    @OnClick(R.id.artw_back)
    public void onViewClicked() {
        finish();
    }
}
