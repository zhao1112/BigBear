package com.newversions;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.TabTitleAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.eventbus.GetMessageEvent;
import com.yunqin.bearmall.ui.activity.MenuActicity;
import com.yunqin.bearmall.ui.activity.SearchActivity;
import com.yunqin.bearmall.ui.fragment.contract.HomeContract;
import com.yunqin.bearmall.ui.fragment.presenter.HomePresenter;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By Master
 * On 2019/1/9 9:48
 */
public class MemberHomeFragment extends BaseFragment implements HomeContract.UI {


    HomeContract.Presenter mPresenter;

    @BindView(R.id.xtablelayout)
    XTabLayout xtablelayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.menu)
    RelativeLayout menu;


    private HighlightButton mHighlightButton;


    private TabTitleAdapter adapter;


    @BindView(R.id.not_net)
    View view;


    @BindView(R.id.xiao_xi)
    ImageView xiao_xi;


    @Override
    public int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void init() {

        xiao_xi.setVisibility(View.GONE);


        showLoading();
        mPresenter = new HomePresenter(this);
        mPresenter.start(getActivity());
    }

    @OnClick({R.id.menu, R.id.home_search, R.id.reset_load_data})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                StarActivityUtil.starActivity(getActivity(), MenuActicity.class);
                break;
            case R.id.home_search:

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("memberType", "1");
                startActivity(intent);

                break;
            case R.id.reset_load_data:
                EventBus.getDefault().post(new GetMessageEvent());
                mPresenter.start(getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void attachChannel(Channel channel) {
        hiddenLoadingView();

        if (channel == null) {
            return;
        }

        view.setVisibility(View.GONE);
        xtablelayout.setVisibility(View.VISIBLE);

        xtablelayout.addTab(xtablelayout.newTab().setText("大熊精选"));
        for (int i = 0; i < channel.getData().size(); i++) {
            xtablelayout.addTab(xtablelayout.newTab().setText(channel.getData().get(i).getName()));
        }
        adapter = new TabTitleAdapter(getActivity(), getFragmentManager(), channel.getData(), true);
        viewpager.removeAllViews();
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(3);
        //初始化显示第一个页面
        xtablelayout.setupWithViewPager(viewpager);
        xtablelayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        view.setVisibility(View.VISIBLE);
        xtablelayout.setVisibility(View.GONE);
    }

    @Override
    public void resultAdData(String key, String value, String data) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setDarkStatusIcon(true);
        }
    }
}
