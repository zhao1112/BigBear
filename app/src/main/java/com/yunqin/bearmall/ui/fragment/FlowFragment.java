package com.yunqin.bearmall.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class FlowFragment extends BaseFragment {

    @BindView(R.id.recycler_view_flow)
    RecyclerView flowRecyclerView;




    @Override
    public int layoutId() {
        return R.layout.fragment_flow;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.charge})
    public void onViewClick(View view){

    }

}
