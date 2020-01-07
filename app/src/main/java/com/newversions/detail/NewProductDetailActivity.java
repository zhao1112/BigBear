package com.newversions.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.RudenessScreenHelper;

/**
 * Create By Master
 * On 2019/1/10 14:23
 */
public class NewProductDetailActivity extends BaseActivity implements FragmentToActivityInter {

    private NewCommentFragment newCommentFragment;
    private NewProductFragment newProductFragment;
    private FragmentManager fragmentManager;
    private Fragment lastFragment;
    private String productId = "";
    private String sku_id = "";

    @Override
    public int layoutId() {
        return R.layout.new_product_layout;
    }

    @Override
    public void init() {
        productId = getIntent().getStringExtra("productId");
        sku_id = getIntent().getStringExtra("sku_id");
        if (sku_id == null) {
            sku_id = "";
        }
        immerseStatusBar();
        changStatusIconCollor(true);
        RudenessScreenHelper.resetDensity(this, 750);

        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        bundle.putString("sku_id", sku_id);
        newCommentFragment = (NewCommentFragment) Fragment.instantiate(this, NewCommentFragment.class.getName(), bundle);
        newProductFragment = (NewProductFragment) Fragment.instantiate(this, NewProductFragment.class.getName(), bundle);

        lastFragment = newProductFragment;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.new_version_product_content, newProductFragment);
        fragmentTransaction.add(R.id.new_version_product_content, newCommentFragment);
        fragmentTransaction.hide(newCommentFragment);
        fragmentTransaction.show(newProductFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void chooseFragment(boolean type) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hiderFragment(fragmentTransaction);
        if (type) {
            fragmentTransaction.show(newProductFragment);
            lastFragment = newProductFragment;
        } else {
            fragmentTransaction.show(newCommentFragment);
            lastFragment = newCommentFragment;
        }
        fragmentTransaction.commit();
    }

    private void hiderFragment(FragmentTransaction transaction) {
        if (newProductFragment != null) {
            transaction.hide(newProductFragment);
        }
        if (newCommentFragment != null) {
            transaction.hide(newCommentFragment);
        }
    }


    @Override
    public void onBackPressed() {
        if (lastFragment instanceof NewCommentFragment) {
            chooseFragment(true);
            return;
        }
        super.onBackPressed();
    }

}
