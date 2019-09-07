package com.newversions.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ZhuanQianBean;


public class ICustomDialog3 extends Dialog implements View.OnClickListener {

    private static Context iContext;

    private static ZhuanQianBean iZhuanQianBean;


    public ICustomDialog3() {
        super(iContext, R.style.CenterAnimation);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zhuan_qian_zhong_xin_dialig);

        setCanceledOnTouchOutside(false);
        setCancelable(false);


        findViewById(R.id.ad_close).setOnClickListener(this);

//
        LinearLayout linearLayout = findViewById(R.id.item_container);


        for (int i = 0; i < iZhuanQianBean.getData().getDrawActivityInfo().size(); i++) {

            ZhuanQianBean.DataBean.DrawActivityInfoBean abc = iZhuanQianBean.getData().getDrawActivityInfo().get(i);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;

            ZhuanQianView zhuanQianView = new ZhuanQianView(iContext).setData(
                    abc.getImg(), abc.getTimes(), abc.getCount(), abc.getName(),
                    abc.getRaffleConfig_id(), abc.getIsReward(), abc.getIsFinish(), abc.getBtAmount()
            );

            linearLayout.addView(zhuanQianView, params);
        }


    }

    @Override
    public void onClick(View v) {
        dismiss();
    }


    public static class Builder {

        public Builder(Context context) {
            iContext = context;
            iZhuanQianBean = null;
        }


        public Builder setCustomTextIds(ZhuanQianBean zhuanQianBean) {
            iZhuanQianBean = zhuanQianBean;
            return this;
        }


        public ICustomDialog3 build() {
            return new ICustomDialog3();
        }
    }


}