package com.yunqin.bearmall.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BargainFreeShopAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.ui.dialog.BargainAddressDialog;
import com.yunqin.bearmall.ui.fragment.contract.BargainFreeShopContract;
import com.yunqin.bearmall.ui.fragment.presenter.BargainFreeShopPresenter;

import java.util.List;

import butterknife.BindView;

public class BargainFreeShopFragment extends BaseFragment implements BargainFreeShopContract.UI,
        BargainFreeShopAdapter.OnBargainProductListener,BargainFreeShopAdapter.OnBargainSpecificationItemsListener,
        BargainAddressDialog.OnBargainFreePartListener{

    @BindView(R.id.bargain_free_list)
    RecyclerView free_list;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.no_net)
    View noNetView;

    private BargainFreeShopAdapter bargainFreeShopAdapter;
    private List<BargainProductListBean.BargainProductList> bargainProductLists;
    private BargainFreeShopPresenter bargainFreeShopPresenter;

    @Override
    public int layoutId() {
        return R.layout.fragment_bargain_free_shop;
    }

    @Override
    public void init() {
        bargainFreeShopPresenter = new BargainFreeShopPresenter(this,getActivity());

    }

    @Override
    public void onBargainProduct(long bargainProduct_id) {

    }

    @Override
    public void onBargainSpecificationItems(long sku_id) {

    }

    @Override
    public void onBargainFreePart(AddressListBean.DataBean receiver) {

    }

    @Override
    public void attachhData(BargainProductListBean bargainProductListBean) {

    }

    @Override
    public void saveJson(String data) {

    }

    @Override
    public void setReceiverList(String data) {

    }

    @Override
    public void setPartBargain(String data) {

    }

    @Override
    public void getAdMobileList(String data) {

    }

    @Override
    public void getAdMobileListMid(String data) {

    }

    @Override
    public void onFail(Throwable e) {

    }
}
