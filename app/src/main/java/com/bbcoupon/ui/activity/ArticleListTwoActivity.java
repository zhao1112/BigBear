package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.ArticleListAdapter;
import com.bbcoupon.ui.bean.ArticeleListInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.widget.RefreshSchoolView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/6/4
 */
public class ArticleListTwoActivity extends BaseActivity implements RequestContract.RequestView {


    @BindView(R.id.artw_title)
    TextView mArtwTitle;
    @BindView(R.id.artw_recycler)
    RecyclerView mArtwRecycler;
    @BindView(R.id.artw_refresh)
    TwinklingRefreshLayout mArtwRefresh;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;

    private String articleid, articletitle;
    private ArticleListAdapter listAdapter;
    private RequestPresenter presenter;
    private int page = 1;

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

        articleid = getIntent().getStringExtra("ARTICLEID");
        articletitle = getIntent().getStringExtra("ARTICLETITLE");

        if (TextUtils.isEmpty(articleid) && TextUtils.isEmpty(articletitle)) {
            return;
        }

        mArtwTitle.setText(articletitle);

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        mArtwRefresh.setHeaderView(new RefreshHeadView(ArticleListTwoActivity.this));
        mArtwRefresh.setBottomView(new RefreshBottomView(ArticleListTwoActivity.this));
        mArtwRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                listAdapter.deleteData();
                getData(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getData(page);
            }
        });

        listAdapter = new ArticleListAdapter(ArticleListTwoActivity.this);
        mArtwRecycler.setLayoutManager(new LinearLayoutManager(ArticleListTwoActivity.this));
        mArtwRecycler.setAdapter(listAdapter);

        listAdapter.setOnArticleList(new ArticleListAdapter.OnArticleList() {
            @Override
            public void onListId(int id, String title, String url) {
                Bundle bundle = new Bundle();
                bundle.putString("ARTICLEID", id + "");
                bundle.putString("ARTICLETITLE", title);
                bundle.putString("ARITCLEIMAGE", url);
                ArticleActivity.openArticleActivity(ArticleListTwoActivity.this, ArticleActivity.class, bundle);
            }
        });

        getData(page);
        mNulldata.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.artw_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof ArticeleListInfor) {
            ArticeleListInfor articeleListInfor = (ArticeleListInfor) data;
            if (articeleListInfor != null && articeleListInfor.getData() != null && articeleListInfor.getData().size() > 0) {
                mArtwRefresh.setBottomView(new RefreshBottomView(ArticleListTwoActivity.this));
                listAdapter.addData(articeleListInfor.getData());
                mNulldata.setVisibility(View.GONE);
            } else {
                mArtwRefresh.setBottomView(new RefreshSchoolView(ArticleListTwoActivity.this));
            }
        }
        hiddenLoadingView();
        mArtwRefresh.finishRefreshing();
        mArtwRefresh.finishLoadmore();
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        mArtwRefresh.finishRefreshing();
        mArtwRefresh.finishLoadmore();
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
        mArtwRefresh.finishRefreshing();
        mArtwRefresh.finishLoadmore();
    }

    public void getData(int page) {
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("pageSize", "10");
        map.put("classification", articleid);
        presenter.onArticleList(ArticleListTwoActivity.this, map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }
}
