package com.yunqin.bearmall.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MenuOneAdapter;
import com.yunqin.bearmall.adapter.MenuTwoAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.Menu;
import com.yunqin.bearmall.ui.activity.contract.MenuActivtyContract;
import com.yunqin.bearmall.ui.activity.presenter.MenuActivityPersenter;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import butterknife.BindView;
import butterknife.OnClick;
import cn.example.lamor.Faceplate;
import cn.example.lamor.container.MultiStateConfigurator;
import cn.example.lamor.container.MultiStateContainer;
import cn.example.lamor.container.StateController;
import cn.example.lamor.utils.MultiStateLayout;

/**
 * 分类导航页面
 */
public class MenuActicity extends BaseActivity implements MenuActivtyContract.UI, MultiStateContainer, MultiStateConfigurator {

    @BindView(R.id.menu_one)
    RecyclerView menu_one;

    @BindView(R.id.menu_two)
    RecyclerView menu_two;

    @BindView(R.id.close)
    RelativeLayout close;

    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.twink)
    TwinklingRefreshLayout twink;


    private MenuActivityPersenter menuActivityPersenter;
    private GridLayoutManager gridLayoutManager;
    private MenuTwoAdapter menuTwoAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_menu;
    }

    @Override
    public void init() {


        twink.setEnableRefresh(false);
        twink.setEnableLoadmore(false);


        Faceplate.getDefault().performInjectLayoutLayers(this);
        menu_one.setLayoutManager(new LinearLayoutManager(this));
        gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return menuTwoAdapter.getAdapterType(position);
            }
        });
        menu_two.setLayoutManager(gridLayoutManager);
        menuActivityPersenter = new MenuActivityPersenter(MenuActicity.this, this);
        showLoading();
        menuActivityPersenter.star();

    }

    @OnClick({R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                this.finish();
                break;
        }
    }


    @Override
    public void attachOneAdapter(MenuOneAdapter menuOneAdapter, Menu menu) {
        hiddenLoadingView();
        mStateController.showContent(false);
        menu_one.setAdapter(menuOneAdapter);
        menuOneAdapter.setPersenter(menuActivityPersenter);
    }

    @Override
    public void attachTwoAdapter(MenuTwoAdapter menuTwoAdapter) {
        hiddenLoadingView();
        mStateController.showContent(false);
        this.menuTwoAdapter = menuTwoAdapter;
        menu_two.setAdapter(menuTwoAdapter);
        menuTwoAdapter.setPersenter(menuActivityPersenter);
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        mStateController.showError(false);
    }

    @Override
    public void showLoad() {
        showLoading();
    }

    @Override
    public void hiddLoad() {
        hiddenLoadingView();
    }

    private StateController mStateController;

    @Override
    public void attachState(MultiStateLayout multiStateLayout) {
        View view = LayoutInflater.from(this).inflate(R.layout.network_error_layout, multiStateLayout, false);
        HighlightButton mHighlightButton = view.findViewById(R.id.reset_load_data);
        mHighlightButton.setOnClickListener(v -> {
            showLoading();
            menuActivityPersenter.star();
        });
        multiStateLayout.attachLayout(MultiStateLayout.STATE_ERROR, view);
    }

    @Override
    public Object getTarget() {
        return content;
    }

    @Override
    public void onRefTypeReturned(StateController stateController) {
        this.mStateController = stateController;
    }
}
