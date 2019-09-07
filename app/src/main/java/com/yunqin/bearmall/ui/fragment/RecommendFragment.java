package com.yunqin.bearmall.ui.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.RecommendPagerAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.ui.fragment.contract.HomeContract;
import com.yunqin.bearmall.ui.fragment.presenter.HomePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 */
public class RecommendFragment extends BaseFragment implements HomeContract.UI {

    @BindView(R.id.xtablelayout)
    XTabLayout xtablelayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.toolbar_back)
    ImageView backView;

    @BindView(R.id.toolbar_title)
    TextView titleView;

    @BindView(R.id.not_net_group)
    LinearLayout not_net_group;


    private HomeContract.Presenter mPresenter;
    private RecommendPagerAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void init() {
        backView.setVisibility(View.INVISIBLE);
        titleView.setText("推荐");
        mPresenter = new HomePresenter(this);
        mPresenter.start(getActivity());
        showLoading();
    }


    @Override
    public void attachChannel(Channel channel) {

        hiddenLoadingView();

        if (channel == null) {
            return;
        }

        not_net_group.setVisibility(View.GONE);

        for (int i = 0; i < channel.getData().size(); i++) {
            xtablelayout.addTab(xtablelayout.newTab().setText(channel.getData().get(i).getName()));
        }
        adapter = new RecommendPagerAdapter(getActivity(), getChildFragmentManager(), channel.getData());
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(3);
        //初始化显示第一个页面
        xtablelayout.setupWithViewPager(viewpager);
//        xtablelayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        not_net_group.setVisibility(View.VISIBLE);
    }

    @Override
    public void resultAdData(String key, String value, String data) {

    }


    @OnClick({R.id.reset_load_data})
    public void onSelect(View view) {
        hiddenLoadingView();
        mPresenter.start(getActivity());
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.e("C-LOG", "onHiddenChanged = true");
        } else {
            Log.e("C-LOG", "onHiddenChanged = false");
        }
    }
}
