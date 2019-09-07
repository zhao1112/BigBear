package com.yunqin.bearmall.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.util.NetUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe 目前无提现功能
 */
public class MyPutForwardVoucherFragment extends BaseFragment {


    @BindView(R.id.content)
    LinearLayout content;


    @BindView(R.id.not_net)
    View not_net;


    @OnClick({R.id.reset_load_data})
    public void selec(View view) {
        click();
    }


    @Override
    public int layoutId() {
        return R.layout.fragment_put_forward_voucher;
    }

    @Override
    public void init() {
        click();
    }


    private void click() {
        if (!NetUtils.isConnected(getActivity())) {
            Toast.makeText(getActivity(), "当前无网络连接!", Toast.LENGTH_SHORT).show();
            content.setVisibility(View.GONE);
            not_net.setVisibility(View.VISIBLE);
        } else {
            content.setVisibility(View.VISIBLE);
            not_net.setVisibility(View.GONE);
        }
    }


}
