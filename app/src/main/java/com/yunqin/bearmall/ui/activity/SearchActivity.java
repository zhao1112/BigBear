package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.newversions.tbk.activity.ProductSumActivity2;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.newversions.tbk.fragment.NewVersionTBKHomeAdapter;
import com.newversions.tbk.utils.BannerClicker;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.SearchAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SearchBannerBean;
import com.yunqin.bearmall.bean.SearchMatch;
import com.yunqin.bearmall.ui.activity.contract.SearchActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.SearchPresenter;
import com.yunqin.bearmall.ui.fragment.MenuGoodsFragment;
import com.yunqin.bearmall.ui.fragment.SearchEmptyFragment;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.FlowLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 */
public class SearchActivity extends BaseActivity implements TextWatcher, SearchAdapter.OnItemCallBack, SearchActivityContract.UI,
        TextView.OnEditorActionListener {

    private static final String KEY = "k";
    private static final String SPLIT = ",";


    @BindView(R.id.flow_layout)
    FlowLayout mFlowLayout;
    @BindView(R.id.search_list_view)
    FlowLayout mListView;
    @BindView(R.id.cancel)
    TextView cancelTextView;
    @BindView(R.id.not_net_view)
    LinearLayout not_net_view;
    @BindView(R.id.input_content_text)
    DelEditText mEditText;
    @BindView(R.id.banner)
    Banner banner;

    private List<String> mSearchList;
    private SearchAdapter searchAdapter;
    private SearchActivityContract.Presenter presenter = new SearchPresenter(this);
    private String lastMenuGoodsData = null;
    private List<SearchBannerBean.DataBean.PlatformBannerBean> list;


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
        presenter.start(this);
        getBanner();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("OnBannerClick", "OnBannerClick: " + list.size());
                if (list != null && list.size() > 0) {
                    BannerClicker.bannerClick(SearchActivity.this, list.get(position).getTargetType(),
                            list.get(position).getTarget(), list.get(position).getTitle());
                }
            }
        });
    }

    private void setList(List<String> mSearchList) {
        mListView.setListData(mSearchList);
        mListView.setOnTagClickListener(text -> {
            mEditText.setText(text);
            mEditText.setSelection(text.length());
            ProductSumActivity2.startProductSumActivity2(this, text, 8, text);
        });
    }

    private void setListdele(List<String> mSearchList) {
        mListView.setListData(mSearchList);
        mListView.cleanTag();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEditText.setText("");
        mListView.cleanTag();
        mSearchList = getSearchData();
        setList(mSearchList);
    }

    @OnClick({R.id.cancel, R.id.reset_load_data, R.id.clear_all, R.id.search})
    public void onChecked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.reset_load_data:
                // TODO 重试...
                if (mEditText.getText().toString().length() > 0) {
                    ProductSumActivity2.startProductSumActivity2(this, mEditText.getText().toString(), 8, mEditText.getText().toString());
                } else {
                    presenter.start(this);
                }
                break;
            case R.id.clear_all:
                // TODO 删除所有adapter数据
                SharedPreferencesHelper.clearWhichOne(this, KEY);
                mSearchList = getSearchData();
                setListdele(mSearchList);
                break;
            case R.id.search:
                if (mEditText.getText().toString().length() > 0) {
                    ProductSumActivity2.startProductSumActivity2(this, mEditText.getText().toString(), 8, mEditText.getText().toString());
                    assemblyData(mEditText.getText().toString());
                }
                hiddenKeyboard();
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
        if (mListView.getVisibility() == View.GONE) {
            mListView.setVisibility(View.VISIBLE);
        }
        if (cancelTextView.getVisibility() == View.GONE) {
            cancelTextView.setVisibility(View.VISIBLE);
        }

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
                onNotNet(false);
            }

            @Override
            public void onNotNetWork() {
                onNotNet(true);
            }

            @Override
            public void onFail(Throwable e) {
                mSearchList = new ArrayList<>();
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
        ProductSumActivity2.startProductSumActivity2(this, index, 8, index);
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
            assemblyData(text);
            ProductSumActivity2.startProductSumActivity2(this, text, 8, text);
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
        return "";
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP:
                    // TODO 发送请求
                    if (mEditText.getText().toString().length() > 0) {
                        ProductSumActivity2.startProductSumActivity2(this, mEditText.getText().toString(), 8,
                                mEditText.getText().toString());
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

    private void getBanner() {
        Map<String, String> mHashMap = new HashMap<>();
        RetrofitApi.request(SearchActivity.this, RetrofitApi.createApi(Api.class).getSearchLunboTu(mHashMap),
                new RetrofitApi.IResponseListener() {

                    @Override
                    public void onSuccess(String data) throws JSONException {
                        try {
                            Log.e("TCP_DATA", data);
                            SearchBannerBean searchBannerBean = new Gson().fromJson(data, SearchBannerBean.class);
                            if (searchBannerBean != null && searchBannerBean.getData() != null && searchBannerBean.getData().getPlatformBanner() != null
                                    && searchBannerBean.getData().getPlatformBanner().size() > 0) {
                                list = searchBannerBean.getData().getPlatformBanner();
                                addBannerList(searchBannerBean.getData().getPlatformBanner());
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onNotNetWork() {

                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }

    private void addBannerList(List<SearchBannerBean.DataBean.PlatformBannerBean> bannerOne) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < bannerOne.size(); i++) {
            stringList.add(bannerOne.get(i).getImage());
        }
        banner.setImages(stringList);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load(path)
                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                    .apply(options)
                    .into(imageView);
        }
    }

    RoundedCorners roundedCorners = new RoundedCorners(10);
    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
    // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
}
