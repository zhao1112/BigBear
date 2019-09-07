package com.yunqin.bearmall.ui.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.fragment.MyPutForwardVoucherFragment;
import com.yunqin.bearmall.ui.fragment.MyTransferVoucherFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class MyVoucherActivity extends BaseActivity {
    @BindView(R.id.content_left)
    TextView content_left;
    @BindView(R.id.view_left)
    View view_left;
    @BindView(R.id.content_right)
    TextView content_right;
    @BindView(R.id.view_right)
    View view_right;

    FragmentManager mFragmentManager;
    MyTransferVoucherFragment myTransferVoucherFragment;
    MyPutForwardVoucherFragment myPutForwardVoucherFragment;

    @Override
    public int layoutId() {
        return R.layout.activity_my_voucher;
    }

    @Override
    public void init() {
        mFragmentManager = getSupportFragmentManager();
        switchFragment(1);
    }


    @OnClick({R.id.toolbar_back, R.id.layout_left, R.id.layout_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.layout_left:
                switchFragment(1);
                setBackground(true);
                break;
            case R.id.layout_right:
                switchFragment(2);
                setBackground(false);
                break;
        }
    }


    private final void setBackground(boolean visibility) {
        content_left.setTextColor(visibility ? getResources().getColor(R.color.main_color) : Color.parseColor("#666666"));
        view_left.setVisibility(visibility ? View.VISIBLE : View.GONE);
        content_right.setTextColor(!visibility ? getResources().getColor(R.color.main_color) : Color.parseColor("#666666"));
        view_right.setVisibility(!visibility ? View.VISIBLE : View.GONE);
    }


    private void switchFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (index == 1) {
            if (myTransferVoucherFragment == null) {
                myTransferVoucherFragment = new MyTransferVoucherFragment();
                transaction.add(R.id.bt_content, myTransferVoucherFragment);
            }
            if (myPutForwardVoucherFragment != null) {
                transaction.hide(myPutForwardVoucherFragment);
            }
            transaction.hide(myTransferVoucherFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(myTransferVoucherFragment);
            transaction.commit();
        } else {
            if (myPutForwardVoucherFragment == null) {
                myPutForwardVoucherFragment = new MyPutForwardVoucherFragment();
                transaction.add(R.id.bt_content, myPutForwardVoucherFragment);
            }
            if (myTransferVoucherFragment != null) {
                transaction.hide(myTransferVoucherFragment);
            }
            transaction.hide(myPutForwardVoucherFragment);

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(myPutForwardVoucherFragment);
            transaction.commit();
        }


    }

}
