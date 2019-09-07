package com.yunqin.bearmall.ui.activity;

import android.widget.ListView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import butterknife.BindView;

/**
 * @author AYWang
 * @create 2018/7/31
 * @Describe
 */
public class MessageAboutActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.message_list)
    ListView message_list;

    @Override
    public int layoutId() {
        return 0;
    }

    @Override
    public void init() {

    }
}
