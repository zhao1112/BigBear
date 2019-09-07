package com.yunqin.bearmall.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindViews;
import butterknife.OnClick;

public class PayWaysFragment extends BaseFragment {

    public static PayWaysFragment instance(int position){

        PayWaysFragment fragment = new PayWaysFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION",position);
        fragment.setArguments(bundle);
        return fragment;

    }

    private int position;

    @BindViews({R.id.rest_radio,R.id.wechat_radio,R.id.alipay_radio})
    List<ImageView> radioImage;

    private WeakReference<OnSelectPayListener> onSelectPayWeakReference;

    public PayWaysFragment setOnSelectPayListener(OnSelectPayListener listener){

        onSelectPayWeakReference = new WeakReference<>(listener);

        return this;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_pay_way;
    }

    @Override
    public void init() {

        Bundle bundle = getArguments();
        if (bundle != null){
            position = bundle.getInt("POSITION");
        }
        setSelect(position);

    }

    private void setSelect(int position){

        for (int i=0;i<radioImage.size();i++){
            if (position == i){
                radioImage.get(i).setSelected(true);
            }else {
                radioImage.get(i).setSelected(false);
            }
        }
        this.position = position;
    }

    @OnClick({R.id.rest_layout,R.id.wechat_layout,R.id.alipay_layout,R.id.pay_way_btn})
    public void onViewClick(View view){

        switch (view.getId()){

            case R.id.rest_layout:
                setSelect(0);
                break;

            case R.id.wechat_layout:
                setSelect(1);
                break;

            case R.id.alipay_layout:
                setSelect(2);
                break;

            case R.id.pay_way_btn:

                if (onSelectPayWeakReference.get()!=null){
                    onSelectPayWeakReference.get().onSelect(position);
                }

                break;

        }

    }

    public interface OnSelectPayListener{

        void onSelect(int position);

    }

}
