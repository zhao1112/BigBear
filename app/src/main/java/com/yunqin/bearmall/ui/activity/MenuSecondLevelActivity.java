package com.yunqin.bearmall.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.FiltrateAdapter;
import com.yunqin.bearmall.adapter.SearchAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.FiltrateBean;
import com.yunqin.bearmall.bean.MenuGoods;
import com.yunqin.bearmall.bean.SearchMatch;
import com.yunqin.bearmall.inter.OnFragmentInteractionListener;
import com.yunqin.bearmall.ui.fragment.MenuGoodsFragment;
import com.yunqin.bearmall.ui.fragment.MenuShopFragment;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/12
 * @Describe
 */
public class MenuSecondLevelActivity extends BaseActivity implements SearchAdapter.OnItemCallBack, OnFragmentInteractionListener {


    @BindView(R.id.goods_check)
    RadioButton radioButtonone;

    @BindView(R.id.shop_check)
    RadioButton radioButtontwo;

    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.filter)
    RelativeLayout filter;


    @BindView(R.id.fragment_content)
    FrameLayout mFrameLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.menu_second_edit_text_view)
    EditText mEditText;


    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;


    private int Category_id;


    @BindView(R.id.content)
    LinearLayout content;


    @OnClick({R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


    private MenuGoodsFragment mMenuGoodsFragment;
    private MenuShopFragment mMenuShopFragment;
    private Fragment lastFragment;


    @Override
    public int layoutId() {
        return R.layout.activity_menu_second;
    }

    @Override
    public void init() {

        mTwinklingRefreshLayout.setEnableRefresh(false);
        mTwinklingRefreshLayout.setEnableLoadmore(false);

        searchAdapter = new SearchAdapter(this, null);
        searchAdapter.setOnItemCallBack(this);
        mListView.setAdapter(searchAdapter);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (radioButtonone.isChecked()) {
                // TODO 添加Fragment
                setFragment(MenuGoodsFragment.class.getName());

                if (mMenuGoodsFragment.getData() != null
                        && mMenuGoodsFragment.getData().getData() != null
                        && mMenuGoodsFragment.getData().getData().getAttributeList() != null
                        && mMenuGoodsFragment.getData().getData().getAttributeList().size() > 0) {
                    filter.setVisibility(View.VISIBLE);
                } else {
                    filter.setVisibility(View.INVISIBLE);
                }

                radioButtonone.setTextColor(Color.parseColor("#FFFFFF"));
                radioButtontwo.setTextColor(Color.parseColor("#23A064"));
            } else {
                setFragment(MenuShopFragment.class.getName());

                filter.setVisibility(View.INVISIBLE);
                radioButtonone.setTextColor(Color.parseColor("#23A064"));
                radioButtontwo.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Category_id = bundle.getInt("Category_id");
            String name = bundle.getString("name");
            mEditText.setText(name);
            setFragment(bundle.getString("classname"));
        }
        initEditTextCallBack();
    }

    private void initEditTextCallBack() {
        mEditText.clearFocus();
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!isUpdate) {
                    return;
                }

                if (s.toString().trim().isEmpty()) {
                    hiddenKeyboard();
                    finish();
                } else {
                    startSearch(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                if (event.getAction() == KeyEvent.ACTION_UP) {
                    hiddenKeyboard();
                    if (mEditText.getText().toString().length() > 0) {
                        searchGoods(mEditText.getText().toString());
                    }
                } else {
                    return true;
                }
            }

            return false;
        });
        mEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // 此处为得到焦点时的处理内容
                mListView.setVisibility(View.VISIBLE);
                content.setVisibility(View.GONE);
                startSearch(mEditText.getText().toString());
            } else {
                mListView.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setFragment(String className) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        if (className.equals(MenuGoodsFragment.class.getName())) {
            if (mMenuGoodsFragment == null) {
                Bundle bundle = new Bundle();
                bundle.putInt("Category_id", Category_id);
                mMenuGoodsFragment = (MenuGoodsFragment) Fragment.instantiate(this, className, bundle);
                transaction.add(R.id.fragment_content, mMenuGoodsFragment);
            }

            lastFragment = mMenuGoodsFragment;

            transaction.show(mMenuGoodsFragment);
            transaction.commit();
        } else if (className.equals(MenuShopFragment.class.getName())) {
            if (mMenuShopFragment == null) {
                Bundle bundle = new Bundle();
                bundle.putInt("Category_id", Category_id);
                bundle.putString("searchValue", searchData);
                mMenuShopFragment = (MenuShopFragment) Fragment.instantiate(this, className, bundle);
                transaction.add(R.id.fragment_content, mMenuShopFragment);
            }

            lastFragment = mMenuShopFragment;

            transaction.show(mMenuShopFragment);
            transaction.commit();
        } else {
            // TODO ...
        }


    }


    private void hideFragment(FragmentTransaction transaction) {
        if (mMenuGoodsFragment != null) {
            transaction.hide(mMenuGoodsFragment);
        }

        if (mMenuShopFragment != null) {
            transaction.hide(mMenuShopFragment);
        }

    }


    @OnClick({R.id.filter, R.id.drawer_cancel, R.id.drawer_confirm})
    public void onSelect(View view) {
        switch (view.getId()) {
            case R.id.filter:

                if (lastFragment instanceof MenuGoodsFragment) {
                    if (mMenuGoodsFragment.getData() != null) {
                        if (initDrawerRightView(mMenuGoodsFragment.getData())) {
                            mDrawerLayout.openDrawer(GravityCompat.END);
                        } else {
                            Toast.makeText(MenuSecondLevelActivity.this, "暂无筛选条件", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MenuSecondLevelActivity.this, "暂无筛选条件", Toast.LENGTH_SHORT).show();
                    }
                } else if (lastFragment instanceof MenuShopFragment) {
                    if (mMenuShopFragment.getData() != null) {
                        mDrawerLayout.openDrawer(GravityCompat.END);
                    } else {
                        Toast.makeText(MenuSecondLevelActivity.this, "暂无筛选条件", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.drawer_confirm:

                String data = mEditText.getText().toString();

                if (lastFragment instanceof MenuGoodsFragment) {
                    mMenuGoodsFragment.updateSearch(data, mFiltrateAdapter.getSelectData());
                } else if (lastFragment instanceof MenuShopFragment) {
                    mMenuShopFragment.updateSearch(data);
                }

                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;
            case R.id.drawer_cancel:
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;
            default:
                break;
        }
    }


    @BindView(R.id.list_view)
    ListView mListView;

    private SearchAdapter searchAdapter;


    /**
     * 模糊搜索 -- 流程
     *
     * @param value
     */
    public void startSearch(String value) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("searchValue", value);
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getSearchMatchList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                mListView.setVisibility(View.VISIBLE);
                content.setVisibility(View.GONE);
                List<String> list = new ArrayList<>();
                SearchMatch searchMatch = new Gson().fromJson(data, SearchMatch.class);
                if (searchMatch.getData() != null) {
                    for (SearchMatch.DataBean dataBean : searchMatch.getData()) {
                        list.add(dataBean.getSearchValue());
                    }
                }
                searchAdapter.setData(list, false);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }


    private boolean isUpdate = true;
    private String searchData = "";


    @Override
    public void itemText(String data) {
        mEditText.clearFocus();
        searchGoods(data);
    }


    /**
     * 搜索商品
     *
     * @param data
     */
    private void searchGoods(String data) {
        hiddenKeyboard();
        isUpdate = false;
        searchData = data;
        mEditText.setText(data);
        mEditText.setSelection(data.length());
        isUpdate = true;

        mListView.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

        // TODO 刷新接口
        if (lastFragment instanceof MenuGoodsFragment) {
            mMenuGoodsFragment.updateSearch(data, "");
        } else if (lastFragment instanceof MenuShopFragment) {
            mMenuShopFragment.updateSearch(data);
        } else {
            // TODO 不会出现此情况
        }
    }


    @Override
    public void itemImg(String data) {

    }


    /**
     * 添加筛选布局
     */

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private FiltrateAdapter mFiltrateAdapter;


    private boolean initDrawerRightView(MenuGoods menuGoods) {
        List<FiltrateBean> mListBean = getData(menuGoods);
        if (mListBean.size() > 0) {
            if (mFiltrateAdapter == null) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mFiltrateAdapter = new FiltrateAdapter(this, mListBean);
                mRecyclerView.setAdapter(mFiltrateAdapter);
            } else {
                mFiltrateAdapter.setData(mListBean);
            }
            return true;
        }
        return false;
    }


    private List<FiltrateBean> getData(MenuGoods menuGoods) {
        List<FiltrateBean> list = new ArrayList<>();
        try {
            for (int i = 0; i < menuGoods.getData().getAttributeList().size(); i++) {
                List<FiltrateBean.Bean> childList = new ArrayList<>();
                MenuGoods.DataBean.AttributeListBean mAttributeListBean = menuGoods.getData().getAttributeList().get(i);
                FiltrateBean mFiltrateBean = new FiltrateBean();
                mFiltrateBean.setAttrIndex(mAttributeListBean.getAttrIndex());
                mFiltrateBean.setAttrName(mAttributeListBean.getAttrName());
                String options = mAttributeListBean.getOptions();
                if (options == null || "".equals(options)) {
                    continue;
                }
                String[] args = options.split(",");
                for (int j = 0; j < args.length; j++) {
                    FiltrateBean.Bean bean = new FiltrateBean.Bean();
                    bean.setOptionsName(args[j]);
                    bean.setAttrIndex(mAttributeListBean.getAttrIndex());
                    childList.add(bean);
                }
                mFiltrateBean.setOptions(childList);
                list.add(mFiltrateBean);
            }
        } catch (Exception e) {
            list.clear();
        }
        return list;
    }


    @Override
    public void updateFiltrateView() {
        if (lastFragment instanceof MenuGoodsFragment) {
            if (mMenuGoodsFragment.getData() != null
                    && mMenuGoodsFragment.getData().getData() != null
                    && mMenuGoodsFragment.getData().getData().getAttributeList() != null
                    && mMenuGoodsFragment.getData().getData().getAttributeList().size() > 0) {
                filter.setVisibility(View.VISIBLE);
            } else {
                filter.setVisibility(View.INVISIBLE);
            }
        }
    }
}
