package com.yunqin.bearmall.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.PayBean;

public class PayDialogFragment extends DialogFragment implements View.OnClickListener{


    public static PayDialogFragment instance(PayBean payBean,String goodsName){

        PayDialogFragment fragment = new PayDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("PAY",payBean);
        bundle.putString("NAME",goodsName);
        fragment.setArguments(bundle);
        return fragment;
    }


    FragmentManager fragmentManager ;
    private PayBean payBean;
    private String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pay_dialog,null);
        initView(view);
        payBean = getArguments().getParcelable("PAY");
        name = getArguments().getString("NAME");
        fragmentManager = getChildFragmentManager();
        addFragment();

        return view;
    }

    private void addFragment() {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container,FragmentPayInfo.instance(payBean,name).setFragmentManager(fragmentManager));
        transaction.commit();
    }

    private void initView(View view){

        view.findViewById(R.id.cancle).setOnClickListener(this);
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
        {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (fragmentManager.getBackStackEntryCount() > 0){
                        fragmentManager.popBackStack();
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
    }

    private float getWindowInfo(Context c, int type) {
        WindowManager wm = (WindowManager) c
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        switch (type) {
            case 0:
                return outMetrics.widthPixels;
            case 1:
                return outMetrics.heightPixels;
            case 2:
                return outMetrics.densityDpi;
            case 3:
                return outMetrics.density;
        }
        return -1f;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null){
            int width = (int) getWindowInfo(getActivity(), 0);
            getDialog().getWindow().setLayout( width, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){


            case R.id.cancle:

                dismiss();

                break;

        }
    }


}
