package com.yunqin.bearmall.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.PayBean;

import butterknife.BindView;
import butterknife.OnClick;

public class FragmentPayInfo extends BaseFragment implements PayWaysFragment.OnSelectPayListener{


    private final String money = "¥";
    public static FragmentPayInfo instance(PayBean payBean,String goodsName){
        FragmentPayInfo fragment = new FragmentPayInfo();
        Bundle bundle = new Bundle();
        bundle.putParcelable("PAY",payBean);
        bundle.putString("NAME",goodsName);
        fragment.setArguments(bundle);
        return fragment;

    }

    private FragmentManager fragmentManager;
    private PayBean payBean;

    public FragmentPayInfo setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    private int position;

    @BindView(R.id.pay_name)
    TextView payNameView;
    @BindView(R.id.godds_name)
    TextView goodsNameView;
    @BindView(R.id.price)
    TextView priceView;

    @Override
    public int layoutId() {
        return R.layout.fragment_pay_info;
    }

    @Override
    public void init() {
        payBean = getArguments().getParcelable("PAY");
        String name = getArguments().getString("NAME");
        goodsNameView.setText(name);
        priceView.setText(money+payBean.getAmount());
    }


    @OnClick({R.id.pay_container,R.id.confirm})
    public void onViewClick(View view){

        switch (view.getId()){

            case R.id.pay_container:

                selectPay();

                break;

            case R.id.confirm:

                break;

        }

    }

    private void pay(){



    }

    private void selectPay() {

        PayWaysFragment fragment = PayWaysFragment.instance(position);
        fragment.setOnSelectPayListener(this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);

        fragmentTransaction.hide(this);
        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    @Override
    public void onSelect(int position) {

        this.position = position;
        switch (position){
            case 0:
                payNameView.setText("余额支付");
                break;

            case 1:
                payNameView.setText("微信支付");
                break;

            case 2:
                payNameView.setText("支付宝");
                break;

        }
        if (fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStack();
        }

    }


}
