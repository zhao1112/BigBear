package com.yunqin.bearmall.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.tbk.activity.ProductSumActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.SearchAdapter;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SearchMatch;
import com.yunqin.bearmall.ui.activity.contract.SearchActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.SearchPresenter;
import com.yunqin.bearmall.ui.fragment.MenuGoodsFragment;
import com.yunqin.bearmall.ui.fragment.MenuShopFragment;
import com.yunqin.bearmall.ui.fragment.SearchEmptyFragment;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 */
public class SearchActivity extends BaseActivity implements TextWatcher, SearchAdapter.OnItemCallBack, SearchActivityContract.UI,
        TextView.OnEditorActionListener {

    private static final String KEY = "k";
    private static final String SPLIT = ",";

    @BindView(R.id.goods_check)
    RadioButton radioButtonone;
    @BindView(R.id.shop_check)
    RadioButton radioButtontwo;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.flow_layout)
    FlowLayout mFlowLayout;
    @BindView(R.id.search_list_view)
    ListView mListView;
    @BindView(R.id.input_content_text)
    DelEditText mEditText;
    @BindView(R.id.fragment_view_group)
    LinearLayout mFrameLayout;
    @BindView(R.id.linear_layout_content)
    LinearLayout mLinearLayout;
    @BindView(R.id.child_1)
    LinearLayout childLinearLayout;
    @BindView(R.id.back)
    RelativeLayout backRelativeLayout;
    @BindView(R.id.cancel)
    TextView cancelTextView;


    List<String> mSearchList;
    SearchAdapter searchAdapter;

    SearchActivityContract.Presenter presenter = new SearchPresenter(this);

    private String lastMenuGoodsData = null;
    private String lastMenuShopData = null;
    private Fragment iL, iR;

    @BindView(R.id.not_net_view)
    LinearLayout not_net_view;

    @Override
    public int layoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        memberType = getIntent().getStringExtra("memberType");
        if (memberType == null) {
            memberType = "0";
        }

        mEditText.addTextChangedListener(this);
        mEditText.setOnEditorActionListener(this);
        mSearchList = getSearchData();
        searchAdapter = new SearchAdapter(this, mSearchList);
        searchAdapter.setOnItemCallBack(this);
        mListView.setAdapter(searchAdapter);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (radioButtonone.isChecked()) {
                // TODO 添加Fragment
                startFragment(lastMenuGoodsData);
                radioButtonone.setTextColor(Color.parseColor("#FFFFFF"));
                radioButtontwo.setTextColor(Color.parseColor("#23A064"));
            } else {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("searchValue", valueType);
                Fragment fragment = Fragment.instantiate(this, MenuShopFragment.class.getName(), bundle);
                fragmentTransaction.replace(R.id.frame_layout_content, fragment);
                fragmentTransaction.commit();

                mFrameLayout.setVisibility(View.VISIBLE);
                childLinearLayout.setVisibility(View.VISIBLE);

                backRelativeLayout.setVisibility(View.VISIBLE);
                cancelTextView.setVisibility(View.GONE);

                // 隐藏筛选按钮
//                filterRelativeLayout.setVisibility(View.GONE);

                radioButtonone.setTextColor(Color.parseColor("#23A064"));
                radioButtontwo.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        presenter.start(this);
    }

    @OnClick({R.id.cancel, R.id.clear_all, R.id.back, R.id.filter, R.id.reset_load_data})
    public void onChecked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.clear_all:
                // TODO 删除所有adapter数据
                SharedPreferencesHelper.clearWhichOne(this, KEY);
                mSearchList = getSearchData();
                searchAdapter.setData(mSearchList, true);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.filter:
                break;
            case R.id.reset_load_data:
                // TODO 重试...
                if (mEditText.getText().toString().length() > 0) {
                    ProductSumActivity.startProductSumActivity(this, mEditText.getText().toString(), 8, mEditText.getText().toString());
                } else {
                    presenter.start(this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mFrameLayout.getVisibility() == View.VISIBLE) {
            mFrameLayout.setVisibility(View.GONE);
        }
        if (mListView.getVisibility() == View.GONE) {
            mListView.setVisibility(View.VISIBLE);
        }
        if (backRelativeLayout.getVisibility() == View.VISIBLE) {
            backRelativeLayout.setVisibility(View.GONE);
        }
        if (cancelTextView.getVisibility() == View.GONE) {
            cancelTextView.setVisibility(View.VISIBLE);
        }

        mLinearLayout.setVisibility(s.length() == 0 ? View.VISIBLE : View.GONE);
        presenter.onTextChangedSearch(this, s + "", new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                List<String> list = new ArrayList<>();
                SearchMatch searchMatch = new Gson().fromJson(data, SearchMatch.class);
                if (searchMatch.getData() != null) {
                    for (SearchMatch.DataBean dataBean : searchMatch.getData()) {
                        list.add(dataBean.getSearchValue());
                    }
                }
                mSearchList = list;
                searchAdapter.setData(s.length() == 0 ? mSearchList = getSearchData() : mSearchList, s.length() == 0 ? true : false);
                onNotNet(false);
            }

            @Override
            public void onNotNetWork() {
                onNotNet(true);
            }

            @Override
            public void onFail(Throwable e) {
                mSearchList = new ArrayList<>();
                searchAdapter.setData(s.length() == 0 ? mSearchList = getSearchData() : mSearchList, s.length() == 0 ? true : false);
                onNotNet(true);
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

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
    public void itemText(String index) {
        // TODO 点击Item
        mEditText.setText(index);
        mEditText.setSelection(index.length());
        assemblyData(index);
        ProductSumActivity.startProductSumActivity(this, index, 8, index);
    }

    @Override
    public void itemImg(String index) {
        // TODO 点击图片删除
        removeData(index);
        mSearchList = getSearchData();
        searchAdapter.setData(mSearchList, true);
    }

    @Override
    public void attachHotSearch(List<String> list) {
        mFlowLayout.setListData(list);
        mFlowLayout.setOnTagClickListener(text -> {
            mEditText.setText(text);
            mEditText.setSelection(text.length());
            mListView.setVisibility(View.GONE);
            assemblyData(text);
            ProductSumActivity.startProductSumActivity(this, text, 8, text);
        });
    }

    private String valueType = "";

    @Override
    public void attachAddText(String text) {
        valueType = text;
    }

    @Override
    public void startEmptyFragment() {
        //TODO 空布局
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SearchEmptyFragment searchEmptyFragment = new SearchEmptyFragment();
        fragmentTransaction.replace(R.id.frame_layout_content, searchEmptyFragment);
        fragmentTransaction.commit();

        mFrameLayout.setVisibility(View.VISIBLE);
        childLinearLayout.setVisibility(View.GONE);

        backRelativeLayout.setVisibility(View.VISIBLE);
        cancelTextView.setVisibility(View.GONE);

    }

    @Override
    public void startFragment(String mMenuGoods) {
        // TODO 内容布局
        lastMenuGoodsData = mMenuGoods;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("data", mMenuGoods);
        bundle.putString("valueType", valueType);
        Fragment menuGoodsFragment = Fragment.instantiate(this, MenuGoodsFragment.class.getName(), bundle);
        fragmentTransaction.replace(R.id.frame_layout_content, menuGoodsFragment);
        fragmentTransaction.commit();

        mFrameLayout.setVisibility(View.VISIBLE);
        childLinearLayout.setVisibility(View.VISIBLE);

        backRelativeLayout.setVisibility(View.VISIBLE);
        cancelTextView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadings() {
        showLoading();
    }

    @Override
    public void hideLoadings() {
        hiddenLoadingView();
    }

    private String memberType = "0";

    @Override
    public String getMemberType() {
        // 0 普通会员
        // 1 付费会员
        return "";
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP:
                    // TODO 发送请求
                    if (mEditText.getText().toString().length() > 0) {
                        ProductSumActivity.startProductSumActivity(this, mEditText.getText().toString(), 8, mEditText.getText().toString());
                        assemblyData(mEditText.getText().toString());
                    }
                    hiddenKeyboard();
                    return true;
                default:
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                hiddenKeyboard();
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onNotNet(boolean show) {
        hiddenLoadingView();
        if (show) {
            not_net_view.setVisibility(View.VISIBLE);
        } else {
            not_net_view.setVisibility(View.GONE);
        }
    }
}
