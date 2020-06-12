package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.ArticleListAdapter;
import com.bbcoupon.ui.bean.ArticeleListInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.bbcoupon.widget.RefreshSchoolView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/6/4
 */
public class ArticleListActivity extends BaseActivity implements TextView.OnEditorActionListener, RequestContract.RequestView {

    @BindView(R.id.list_input)
    DelEditText mListInput;
    @BindView(R.id.list_recycler)
    RecyclerView mListRecycler;
    @BindView(R.id.list_refresh)
    TwinklingRefreshLayout mListRefresh;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;

    private static final String KEY = "HISTORYk";
    private static final String SPLIT = ",";
    private String articlecontent;
    private ArticleListAdapter listAdapter;
    private int page = 1;
    private RequestPresenter presenter;
    private boolean isFirst = true;

    public static void openArticleListActivity(Activity activity, Class cla, Bundle bundle) {
        Intent intent = new Intent(activity, cla);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_article_list;
    }

    @Override
    public void init() {
        articlecontent = getIntent().getStringExtra("ARTICLECONTENT");
        if (articlecontent == null) {
            finish();
            return;
        }

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        mListInput.setText(articlecontent);
        mListInput.setOnEditorActionListener(this);

        mListRefresh.setHeaderView(new RefreshHeadView(ArticleListActivity.this));
        mListRefresh.setBottomView(new RefreshBottomView(ArticleListActivity.this));
        mListRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
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

        listAdapter = new ArticleListAdapter(ArticleListActivity.this);
        mListRecycler.setLayoutManager(new LinearLayoutManager(ArticleListActivity.this));
        mListRecycler.setAdapter(listAdapter);

        listAdapter.setOnArticleList(new ArticleListAdapter.OnArticleList() {
            @Override
            public void onListId(int id, String title, String url) {
                Bundle bundle = new Bundle();
                bundle.putString("ARTICLEID", id + "");
                ArticleActivity.openArticleActivity(ArticleListActivity.this, ArticleActivity.class, bundle);
            }
        });

        getData(page);
        mNulldata.setVisibility(View.VISIBLE);
    }

    private void getData(int page) {
        if (!TextUtils.isEmpty(articlecontent)) {
            Map<String, String> map = new HashMap<>();
            map.put("words", articlecontent);
            map.put("page", page + "");
            map.put("pageSize", "10");
            presenter.onArticleListByWords(ArticleListActivity.this, map);
        } else {
            showToast("搜索内容不能为空");
        }
    }

    @OnClick({R.id.list_back, R.id.list_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.list_back:
                finish();
                break;
            case R.id.list_search:
                if (mListInput.getText().toString().length() > 0) {
                    assemblyData(mListInput.getText().toString());
                    articlecontent = mListInput.getText().toString();
                    listAdapter.deleteData();
                    if (ConstantUtil.isSchoolClick()) {
                        mListRefresh.startRefresh();
                    }
                    hiddenKeyboard();
                } else {
                    showToast("请输入搜索内容");
                }
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP:
                    // TODO 发送请求
                    if (mListInput.getText().toString().length() > 0) {
                        assemblyData(mListInput.getText().toString());
                        articlecontent = mListInput.getText().toString();
                        listAdapter.deleteData();
                        if (ConstantUtil.isSchoolClick()) {
                            mListRefresh.startRefresh();
                        }
                        hiddenKeyboard();
                    } else {
                        showToast("请输入搜索内容");
                    }
                    return true;
                default:
                    return true;
            }
        }
        return false;
    }


    /**
     * 添加方法
     *
     * @param data
     */
    private void assemblyData(String data) {
        String content = (String) SharedPreferencesHelper.get(this, KEY, "");
        if ("".equals(content)) {
            SharedPreferencesHelper.put(this, KEY, data);
        } else if (content.contains(data)) {
            SharedPreferencesHelper.put(this, KEY, data + SPLIT + removeData(data));
        } else {
            SharedPreferencesHelper.put(this, KEY, data + SPLIT + content);
        }
    }

    private String removeData(String str) {
        String currData = "";
        String content = (String) SharedPreferencesHelper.get(this, KEY, "");
        if (content.contains(str + SPLIT)) {
            currData = content.replace(str + SPLIT, "");
        } else {
            currData = content.replace(str, "");
        }
        SharedPreferencesHelper.put(this, KEY, currData);
        return currData;
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof ArticeleListInfor) {
            ArticeleListInfor articeleListInfor = (ArticeleListInfor) data;
            if (articeleListInfor != null && articeleListInfor.getData() != null && articeleListInfor.getData().size() > 0) {
                mListRefresh.setBottomView(new RefreshBottomView(ArticleListActivity.this));
                listAdapter.addData(articeleListInfor.getData());
                mNulldata.setVisibility(View.GONE);
                isFirst = false;
            } else {
                if (isFirst) {
                    showToast("没有搜索到相关的文章呦！");
                }
                mListRefresh.setBottomView(new RefreshSchoolView(ArticleListActivity.this));
            }
        }
        hiddenLoadingView();
        mListRefresh.finishRefreshing();
        mListRefresh.finishLoadmore();
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        mListRefresh.finishRefreshing();
        mListRefresh.finishLoadmore();
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
        mListRefresh.finishRefreshing();
        mListRefresh.finishLoadmore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }
}
