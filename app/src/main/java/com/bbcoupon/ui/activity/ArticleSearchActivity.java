package com.bbcoupon.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

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
public class ArticleSearchActivity extends BaseActivity implements TextWatcher, TextView.OnEditorActionListener {

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
        switch (view.getId()) {
            case R.id.ar_back:
                finish();
                break;
            case R.id.search:
                break;
            case R.id.clear_all:
                break;
        }
    }

    private void setList(List<String> mSearchList) {
        mHistoryFlow.setListData(mSearchList);
        mHistoryFlow.setOnTagClickListener(text -> {
            mInputContent.setText(text);
            mInputContent.setSelection(text.length());
        });
    }

    private void setHotList(List<String> mSearchList) {
        mHotFlow.setListData(mSearchList);
        mHotFlow.setOnTagClickListener(text -> {
            mInputContent.setText(text);
            mInputContent.setSelection(text.length());
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
                    }
                    hiddenKeyboard();
                    return true;
                default:
                    return true;
            }
        }
        return false;
    }
}
