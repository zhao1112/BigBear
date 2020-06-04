package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.ArticleListAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.RefreshHeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/6/4
 */
public class ArticleListActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.list_input)
    DelEditText mListInput;
    @BindView(R.id.list_recycler)
    RecyclerView mListRecycler;
    @BindView(R.id.list_refresh)
    TwinklingRefreshLayout mListRefresh;

    private static final String KEY = "HISTORYk";
    private static final String SPLIT = ",";
    private String articlecontent;
    private ArticleListAdapter listAdapter;

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

        mListInput.setText(articlecontent);
        mListInput.setOnEditorActionListener(this);

        mListRefresh.setHeaderView(new RefreshHeadView(ArticleListActivity.this));
        mListRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });

        listAdapter = new ArticleListAdapter(ArticleListActivity.this);
        mListRecycler.setLayoutManager(new LinearLayoutManager(ArticleListActivity.this));
        mListRecycler.setAdapter(listAdapter);
    }

    @OnClick({R.id.list_back, R.id.list_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.list_back:
                finish();
                break;
            case R.id.list_search:

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
}
