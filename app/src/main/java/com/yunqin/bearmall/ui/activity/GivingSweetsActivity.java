package com.yunqin.bearmall.ui.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.fragment.FriendsGiftsFragment;
import com.yunqin.bearmall.ui.fragment.GiveFriendsFragment;
import com.yunqin.bearmall.util.NetUtils;
import com.yunqin.bearmall.util.StarActivityUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class GivingSweetsActivity extends BaseActivity {

    @BindView(R.id.content_left)
    TextView content_left;
    @BindView(R.id.view_left)
    View view_left;
    @BindView(R.id.content_right)
    TextView content_right;
    @BindView(R.id.view_right)
    View view_right;

    FragmentManager mFragmentManager;
    GiveFriendsFragment mGiveFriendsFragment;
    FriendsGiftsFragment mFriendsGiftsFragment;

    @Override
    public int layoutId() {
        return R.layout.activity_giving_sweets;
    }

    @Override
    public void init() {
        mFragmentManager = getSupportFragmentManager();
        switchFragment(1);
    }


    @OnClick({R.id.toolbar_right, R.id.toolbar_back, R.id.layout_left, R.id.layout_right})
    public void sendBt(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right:
                // TODO 跳转
                if (!NetUtils.isConnected(this)) {
                    Toast.makeText(this, "当前无网络连接!", Toast.LENGTH_SHORT).show();
                    return;
                }
                StarActivityUtil.starActivity(this, FindBTGiveWhoActivity.class);
                break;
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
            if (mGiveFriendsFragment == null) {
                mGiveFriendsFragment = new GiveFriendsFragment();
                transaction.add(R.id.bt_content, mGiveFriendsFragment);
            }
            if (mFriendsGiftsFragment != null) {
                transaction.hide(mFriendsGiftsFragment);
            }
            transaction.hide(mGiveFriendsFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(mGiveFriendsFragment);
            transaction.commit();
        } else {
            if (mFriendsGiftsFragment == null) {
                mFriendsGiftsFragment = new FriendsGiftsFragment();
                transaction.add(R.id.bt_content, mFriendsGiftsFragment);
            }
            if (mGiveFriendsFragment != null) {
                transaction.hide(mGiveFriendsFragment);
            }
            transaction.hide(mFriendsGiftsFragment);

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(mFriendsGiftsFragment);
            transaction.commit();
        }


    }


}
