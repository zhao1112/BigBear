package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.ProductSumActivity;
import com.yunqin.bearmall.adapter.MenuOneAdapter;
import com.yunqin.bearmall.adapter.MenuTwoAdapter;
import com.yunqin.bearmall.bean.Menu;
import com.yunqin.bearmall.ui.activity.MenuSecondLevelActivity;
import com.yunqin.bearmall.ui.activity.contract.MenuActivtyContract;
import com.yunqin.bearmall.ui.activity.model.MenuActivityModel;
import com.yunqin.bearmall.ui.fragment.MenuGoodsFragment;

/**
 * @author AYWang
 * @create 2018/7/12
 * @Describe
 */
public class MenuActivityPersenter implements MenuActivtyContract.Persenter, MenuActivtyContract.menuDataCallBack {

    Context mContext;
    MenuActivtyContract.UI mView;
    MenuActivityModel menuActivityModel;
    MenuOneAdapter menuOneAdapter;
    MenuTwoAdapter menuTwoAdapter;

    Menu menu;

    public MenuActivityPersenter(Context mContext, MenuActivtyContract.UI mView) {
        this.mContext = mContext;
        this.mView = mView;
        menuActivityModel = new MenuActivityModel(mContext, this);
    }

    @Override
    public void star() {
        menuActivityModel.getMenuData();
    }

    @Override
    public void menuData(Menu menu) {
        this.menu = menu;
        menuOneAdapter = new MenuOneAdapter(mContext, menu.getData());
        mView.attachOneAdapter(menuOneAdapter, menu);
    }

    @Override
    public void onNotNetWork() {
        mView.onNotNetWork();
    }

    @Override
    public void fail(Throwable throwable) {
        mView.hiddLoad();
    }

    /**
     * 左侧导航栏点击事件
     *
     * @param index
     */
    public void itemClick(int index) {
        menuTwoAdapter = new MenuTwoAdapter(mContext, menu.getData().get(index).getSubCategory());
        mView.attachTwoAdapter(menuTwoAdapter);
    }

    /**
     * 右侧导航点击事件
     *
     * @param index
     */
    public void onRightItemClcik(int index, Object object) {

//        Bundle bundle = new Bundle();
//        bundle.putString("classname", MenuGoodsFragment.class.getName());
//        int Category_id = ((Menu.DataBean.SubCategoryBeanX.SubCategoryBean) object).getCategory_id();
//        bundle.putInt("Category_id", Category_id);
//        bundle.putString("name", ((Menu.DataBean.SubCategoryBeanX.SubCategoryBean) object).getName());
//
//        Intent intent = new Intent(mContext, MenuSecondLevelActivity.class);
//
//        intent.putExtras(bundle);
        Menu.DataBean.SubCategoryBeanX.SubCategoryBean bean = (Menu.DataBean.SubCategoryBeanX.SubCategoryBean) object;
        Intent intent = new Intent(mContext, ProductSumActivity.class);
        intent.putExtra(Constants.INTENT_KEY_ID,bean.getCategory_id()+"");
        intent.putExtra(Constants.INTENT_KEY_TITLE,bean.getName());
        intent.putExtra(Constants.INTENT_KEY_TYPE,1);
        mContext.startActivity(intent);


    }

}
