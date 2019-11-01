package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.newversions.tbk.fragment.MyOrderFragment;
import com.newversions.tbk.fragment.TBKOrderFragment;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MineOrderTabAdapter;
import com.yunqin.bearmall.adapter.TaoBaoOrderTabAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.StarActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.toolbar_title)
    TextView mToolBatTitle;

    @BindView(R.id.toolbar_search_img)
    ImageView mImageView;


    private static Activity mActivity = null;
    @BindView(R.id.rb_my_order)
    RadioButton rbMyOrder;
    @BindView(R.id.rb_tb_order)
    RadioButton rbTbOrder;
    @BindView(R.id.rg_order)
    RadioGroup rgOrder;
    private Fragment nowFragment;
    private MyOrderFragment myOrderFragment;
    private TBKOrderFragment tbkOrderFragment;
    private int orderType = TYPE_MY_ORDER;

    public static final int TYPE_MY_ORDER = 1;
    public static final int TYPE_TAOBAO_ORDER = 2;

    public static void finishThis() {
        if (mActivity != null) {
            mActivity.finish();
            mActivity = null;
        }
    }


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

    private int index = 0;


    @Override
    public int layoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    public void init() {
        tbkOrderFragment = new TBKOrderFragment();
        myOrderFragment = new MyOrderFragment();

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            index = bundle.getInt("index", 0);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        myOrderFragment.setArguments(bundle);

        rgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_my_order:
                        orderType = TYPE_MY_ORDER;
                        switchFragment(myOrderFragment);
                        break;
                    case R.id.rb_tb_order:
                        orderType = TYPE_TAOBAO_ORDER;
                        switchFragment(tbkOrderFragment);
                        break;
                }
            }
        });
        switchFragment(tbkOrderFragment);
    }

    private void switchFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(nowFragment).show(fragment).commit();
            nowFragment = fragment;
        } else {
            if (nowFragment == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(nowFragment).replace(R.id.content, fragment).commit();
            }
            nowFragment = fragment;
        }
    }

    @OnClick({R.id.toolbar_back})
    public void doClicks(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }


}
