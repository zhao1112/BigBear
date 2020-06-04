package com.bbcoupon.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bbcoupon.util.WindowUtils;
import com.bbcoupon.widget.ArticleHistoryFlowLayout;
import com.newversions.tbk.activity.ProductSumActivity2;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/6/3
 */
public class ArticleSearchActivity extends BaseActivity implements TextWatcher, TextView.OnEditorActionListener, View.OnClickListener {

    @BindView(R.id.history_flow)
    FlowLayout mHistoryFlow;
    @BindView(R.id.hot_flow)
    ArticleHistoryFlowLayout mHotFlow;
    @BindView(R.id.input_content)
    DelEditText mInputContent;

    private static final String KEY = "HISTORYk";
    private static final String SPLIT = ",";
    private List<String> historyList;

    @Override
    public int layoutId() {
        return R.layout.activity_articlesearch;
    }

    @Override
    public void init() {

        mInputContent.addTextChangedListener(this);
        mInputContent.setOnEditorActionListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHistoryFlow.cleanTag();
        historyList = getSearchData();
        setList(historyList);
        setHotList(historyList);
    }

    @OnClick({R.id.ar_back, R.id.search, R.id.clear_all})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ar_back:
                finish();
                break;
            case R.id.search:
                if (mInputContent.getText().toString().length() > 0) {
                    bundle.putString("ARTICLECONTENT",mInputContent.getText().toString());
                    ArticleListActivity.openArticleListActivity(ArticleSearchActivity.this,ArticleListActivity.class,bundle);
                }else {
                    showToast("请输入搜索内容");
                }
                break;
            case R.id.clear_all:
                PopupWindow popupWindow = WindowUtils.ShowVirtual(ArticleSearchActivity.this, R.layout.item_artsearch_popup,
                        R.style.bottom_animation, 2);
                popupWindow.getContentView().findViewById(R.id.arts_confirmation).setOnClickListener(this);
                popupWindow.getContentView().findViewById(R.id.arts_cancel).setOnClickListener(this);
                break;
        }
    }

    private void setList(List<String> mSearchList) {
        mHistoryFlow.setListData(mSearchList);
        mHistoryFlow.setOnTagClickListener(text -> {
            mInputContent.setText(text);
            mInputContent.setSelection(text.length());
            Bundle bundle = new Bundle();
            bundle.putString("ARTICLECONTENT",mInputContent.getText().toString());
            ArticleListActivity.openArticleListActivity(ArticleSearchActivity.this,ArticleListActivity.class,bundle);
        });
    }

    private void setHotList(List<String> mSearchList) {
        mHotFlow.setListData(mSearchList);
        mHotFlow.setOnTagClickListener(text -> {
            mInputContent.setText(text);
            mInputContent.setSelection(text.length());
            Bundle bundle = new Bundle();
            bundle.putString("ARTICLECONTENT",mInputContent.getText().toString());
            ArticleListActivity.openArticleListActivity(ArticleSearchActivity.this,ArticleListActivity.class,bundle);
        });
    }


    private List<String> getSearchData() {
        List<String> list = new ArrayList<>();
        String data = (String) SharedPreferencesHelper.get(this, KEY, "");
        if (data.length() > 0) {
            String[] currdata = data.split(SPLIT);
            for (String content : currdata) {
                list.add(content);
            }
        }
        return list;
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP:
                    // TODO 发送请求
                    if (mInputContent.getText().toString().length() > 0) {
                        assemblyData(mInputContent.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("ARTICLECONTENT",mInputContent.getText().toString());
                        ArticleListActivity.openArticleListActivity(ArticleSearchActivity.this,ArticleListActivity.class,bundle);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arts_confirmation:
                SharedPreferencesHelper.clearWhichOne(this, KEY);
                historyList = getSearchData();
                mHistoryFlow.cleanTag();
                setList(historyList);
                WindowUtils.dismissBrightness(ArticleSearchActivity.this);
                break;
            case R.id.arts_cancel:
                WindowUtils.dismissBrightness(ArticleSearchActivity.this);
                break;
        }
    }
}
