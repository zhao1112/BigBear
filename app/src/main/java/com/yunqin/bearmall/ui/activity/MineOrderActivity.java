package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.newversions.tbk.fragment.MyOrderFragment;
import com.newversions.tbk.fragment.PddOrderFragment;
import com.newversions.tbk.fragment.TBKOrderFragment;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.StarActivityUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yunqin.bearmall.ui.activity.MineOrderActivity.FragmentType.ALL_ORDER;
import static com.yunqin.bearmall.ui.activity.MineOrderActivity.FragmentType.AWAIT_FAHUO;
import static com.yunqin.bearmall.ui.activity.MineOrderActivity.FragmentType.AWAIT_PINGJIA;
import static com.yunqin.bearmall.ui.activity.MineOrderActivity.FragmentType.AWAIT_SHOUHUO;
import static com.yunqin.bearmall.ui.activity.MineOrderActivity.FragmentType.AWAIT_ZHIFU;

/**
 * 我的订单
 *
 * @author Master
 */
public class MineOrderActivity extends BaseActivity {

    @BindView(R.id.rb_my_order)
    RadioButton rbMyOrder;
    @BindView(R.id.rb_tb_order)
    RadioButton rbTbOrder;
    @BindView(R.id.rb_tb_pdd)
    RadioButton rb_tb_pdd;
    @BindView(R.id.rg_order)
    RadioGroup rgOrder;

    private Fragment nowFragment;
    private MyOrderFragment myOrderFragment;
    private TBKOrderFragment tbkOrderFragment;
    private PddOrderFragment mPddOrderFragment;


    @IntDef({ALL_ORDER, AWAIT_ZHIFU, AWAIT_FAHUO, AWAIT_SHOUHUO, AWAIT_PINGJIA})
    public @interface FragmentType {
        int ALL_ORDER = -1;// 全部订单
        int AWAIT_ZHIFU = 1;// 待付款
        int AWAIT_FAHUO = 2;// 待发货
        int AWAIT_SHOUHUO = 3;// 待收货
        int AWAIT_PINGJIA = 4;// 待评价
    }


    public static void start(Activity activity, @FragmentType int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", type);
        StarActivityUtil.starActivity(activity, MineOrderActivity.class, bundle);
    }

    public static void openMineOrderActivity(Activity activity, boolean type) {
        Intent intent = new Intent(activity, MineOrderActivity.class);
        intent.putExtra("TYPE", type);
        activity.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    public void init() {
        boolean type = getIntent().getBooleanExtra("TYPE", false);

        tbkOrderFragment = new TBKOrderFragment();
        myOrderFragment = new MyOrderFragment();
        mPddOrderFragment = new PddOrderFragment();

        //RadioGroup订单页面切换按钮
        rgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_my_order:
                        switchFragment(tbkOrderFragment);
                        break;
                    case R.id.rb_tb_order:
                        switchFragment(myOrderFragment);
                        break;
                    case R.id.rb_tb_pdd:
                        switchFragment(mPddOrderFragment);
                        break;
                }
            }
        });

        if (type) {
            rbTbOrder.setChecked(true);
        } else {
            rbMyOrder.setChecked(true);
        }

    }

    private void switchFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(nowFragment).show(fragment).commit();
            nowFragment = fragment;
        } else {
            if (nowFragment == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(nowFragment).add(R.id.content, fragment).commit();
            }
            nowFragment = fragment;
        }
    }

    //返回按钮
    @OnClick({R.id.toolbar_back})
    public void doClicks(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

}
