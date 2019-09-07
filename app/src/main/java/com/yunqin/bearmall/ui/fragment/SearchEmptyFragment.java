package com.yunqin.bearmall.ui.fragment;

import android.support.v7.widget.GridLayoutManager;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.widget.CustomRecommendView;

import butterknife.BindView;

/**
 * @author Master
 */
public class SearchEmptyFragment extends BaseFragment {

    @BindView(R.id.custom_recommend_view)
    CustomRecommendView mCustomRecommendView;

    @Override
    public int layoutId() {
        return R.layout.fragment_empty_search;
    }

    @Override
    public void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mCustomRecommendView.setImageSrc(R.drawable.search_null);
        mCustomRecommendView.setTvContent("抱歉，没有找到你想要的东东");
        mCustomRecommendView.setManager(gridLayoutManager);
        mCustomRecommendView.start(getActivity());
    }
}