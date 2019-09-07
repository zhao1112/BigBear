package com.yunqin.bearmall.ui.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.FindBTGiveWhoActivity;
import com.yunqin.bearmall.ui.fragment.CollectionGoodsFragment;
import com.yunqin.bearmall.ui.fragment.CollectionShopFragment;
import com.yunqin.bearmall.ui.fragment.FriendsGiftsFragment;
import com.yunqin.bearmall.ui.fragment.GiveFriendsFragment;
import com.yunqin.bearmall.util.StarActivityUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCollectionActivity extends BaseActivity {

    @BindView(R.id.content_left)
    TextView content_left;
    @BindView(R.id.view_left)
    View view_left;
    @BindView(R.id.content_right)
    TextView content_right;
    @BindView(R.id.view_right)
    View view_right;

    FragmentManager mFragmentManager;
    CollectionGoodsFragment collectionGoodsFragment;
    CollectionShopFragment collectionShopFragment;

    @Override
    public int layoutId() {
        return R.layout.activity_my_collection;
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
            if (collectionGoodsFragment == null) {
                collectionGoodsFragment = new CollectionGoodsFragment();
                transaction.add(R.id.bt_content, collectionGoodsFragment);
            }
            if(collectionShopFragment != null){
                transaction.hide(collectionShopFragment);
            }
            transaction.hide(collectionGoodsFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(collectionGoodsFragment);
            transaction.commit();
        } else {
            if (collectionShopFragment == null) {
                collectionShopFragment = new CollectionShopFragment();
                transaction.add(R.id.bt_content, collectionShopFragment);
            }
            if(collectionGoodsFragment != null){
                transaction.hide(collectionGoodsFragment);
            }
            transaction.hide(collectionShopFragment);

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(collectionShopFragment);
            transaction.commit();
        }
    }
}
