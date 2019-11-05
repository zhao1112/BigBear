package com.yunqin.bearmall.ui.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.eventbus.ChangeFragmentEvent;
import com.yunqin.bearmall.ui.dialog.ActivityTextTipDialog;
import com.yunqin.bearmall.ui.fragment.ZeroGoodsFragment;
import com.yunqin.bearmall.ui.fragment.ZeroMyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/8/2
 * @Describe 0元拼团页面
 */
public class ZeroMoneyActivity extends BaseActivity {
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.right_layout)
    LinearLayout rightLayout;

    @BindView(R.id.toolbar_right)
    TextView toolbar_right;

    private ActivityTextTipDialog activityTextTipDialog;

    private boolean isChang = false;

    FragmentManager mFragmentManager;
    ZeroGoodsFragment zeroGoodsFragment;
    ZeroMyFragment zeroMyFragment;

    @Override
    public int layoutId() {
        return R.layout.activity_zeromoney;
    }

    @Override
    public void init() {
        toolbarTitle.setText("糖果0元兑");

        mFragmentManager = getSupportFragmentManager();
        switchFragment(1);
        toolbar_right.setText("规则说明");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ChangeFragmentEvent changeFragmentEvent) {
        isChang = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isChang) {
            isChang = false;
            switchFragment(2);
            setBackground(false);
        }
    }

    @OnClick({R.id.toolbar_back, R.id.left_layout, R.id.right_layout, R.id.toolbar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                this.finish();
                break;
            case R.id.left_layout:
                switchFragment(1);
                setBackground(true);
                break;
            case R.id.right_layout:
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(this);
                    return;
                }
                switchFragment(2);
                setBackground(false);
                break;
            case R.id.toolbar_right:
                if (toolbar_right.getText().toString().equals("往期详情")) {
                    PastActivity.startPastActivity(this, 1);
                } else {
                    if (activityTextTipDialog == null) {
                        activityTextTipDialog = new ActivityTextTipDialog(ZeroMoneyActivity.this);
                    } else {
                        activityTextTipDialog.show();
                    }
                }
                break;
        }
    }

    private final void setBackground(boolean ischeck) {
        leftImg.setBackgroundResource(ischeck ? R.drawable.icon_0yuan_goods : R.drawable.icon_0yuan_goods_p);
        leftText.setTextColor(ischeck ? getResources().getColor(R.color.main_color) : Color.parseColor("#666666"));
        rightImg.setBackgroundResource(ischeck ? R.drawable.icon_0yuan_my : R.drawable.icon_0yuan_my_n);
        rightText.setTextColor(ischeck ? Color.parseColor("#666666") : getResources().getColor(R.color.main_color));
    }


    private void switchFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (index == 1) {
            toolbar_right.setText("规则说明");
            toolbar_right.setVisibility(View.VISIBLE);
            if (zeroGoodsFragment == null) {
                zeroGoodsFragment = new ZeroGoodsFragment();
                transaction.add(R.id.content, zeroGoodsFragment);
            }
            if (zeroMyFragment != null) {
                transaction.hide(zeroMyFragment);
            }
            transaction.hide(zeroGoodsFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(zeroGoodsFragment);
            transaction.commit();
        } else {
            toolbar_right.setText("往期详情");
            toolbar_right.setVisibility(View.INVISIBLE);
            if (zeroMyFragment == null) {
                zeroMyFragment = new ZeroMyFragment();
                transaction.add(R.id.content, zeroMyFragment);
            }
            if (zeroGoodsFragment != null) {
                transaction.hide(zeroGoodsFragment);
            }
            transaction.hide(zeroMyFragment);

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(zeroMyFragment);
            transaction.commit();
        }
    }

    public void switch2Fragment() {
        switchFragment(1);
        setBackground(true);
    }

}
