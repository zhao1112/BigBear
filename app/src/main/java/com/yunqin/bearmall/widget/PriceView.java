package com.yunqin.bearmall.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;

import butterknife.BindView;


public class PriceView extends FrameLayout {


    TextView n_v_dangqianjia;
    TextView n_v_yuanjia;
    TextView n_v_bear_dangqianjia;
    TextView n_v_bear_yuanjia;
    LinearLayout jaige_layout;
    LinearLayout dandu_jiage_layout;
    TextView no_no_dangqianjia;

    public PriceView(@NonNull Context context) {
        this(context,null);
    }

    public PriceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PriceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addPriceLayout();
    }

    private void addPriceLayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_preice_view,null);
        n_v_dangqianjia= view.findViewById(R.id.n_v_dangqianjia);
        n_v_yuanjia = view.findViewById(R.id.n_v_yuanjia);
        n_v_bear_dangqianjia = view.findViewById(R.id.n_v_bear_dangqianjia);
        n_v_bear_yuanjia = view.findViewById(R.id.n_v_bear_yuanjia);
        jaige_layout = view.findViewById(R.id.jaige_layout);
        dandu_jiage_layout = view.findViewById(R.id.dandu_jiage_layout);
        no_no_dangqianjia = view.findViewById(R.id.no_no_dangqianjia);
        addView(view);
    }


    public void setPrice(String membershipPrice,String price,boolean isSurportMsp,int isDiscount,int model,String sourceMembershipPrice,String sourcePrice){

        jaige_layout.setVisibility(GONE);
        dandu_jiage_layout.setVisibility(GONE);

        if (isSurportMsp) {
            // 是否支持会员价
            jaige_layout.setVisibility(View.VISIBLE);
            if (isDiscount == 1) {
                // 是折扣商品
                if (model == 0) {
                    n_v_dangqianjia.setText(String.format("¥%s", price));
                    n_v_yuanjia.setVisibility(View.VISIBLE);
                    n_v_yuanjia.setText(String.format("¥%s", sourcePrice));
                    n_v_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                    n_v_bear_dangqianjia.setText(String.format("直购价 ¥%s", membershipPrice));
                    n_v_bear_yuanjia.setVisibility(View.GONE);

                } else if (model == 1) {

                    n_v_dangqianjia.setText(String.format("¥%s", price));
                    n_v_yuanjia.setVisibility(View.GONE);

                    n_v_bear_dangqianjia.setText(String.format("直购价 ¥%s", membershipPrice));
                    n_v_bear_yuanjia.setVisibility(View.VISIBLE);
                    n_v_bear_yuanjia.setText(String.format("¥%s", sourceMembershipPrice));
                    n_v_bear_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                } else if (model == 2) {

                    n_v_dangqianjia.setText(String.format("¥%s",price));
                    n_v_yuanjia.setVisibility(View.VISIBLE);
                    n_v_yuanjia.setText(String.format("¥%s", sourcePrice));
                    n_v_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                    n_v_bear_dangqianjia.setText(String.format("直购价 ¥%s", membershipPrice));
                    n_v_bear_yuanjia.setVisibility(View.VISIBLE);
                    n_v_bear_yuanjia.setText(String.format("¥%s", sourceMembershipPrice));
                    n_v_bear_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                }
            } else {
                n_v_dangqianjia.setText(String.format("¥%s", price));
                n_v_yuanjia.setVisibility(View.GONE);

                n_v_bear_dangqianjia.setText(String.format("直购价 ¥%s", membershipPrice));
                n_v_bear_yuanjia.setVisibility(View.GONE);
            }
        } else {
            if (isDiscount == 1) {
                // 是折扣商品
                jaige_layout.setVisibility(View.VISIBLE);
                if (model == 0) {
                    n_v_dangqianjia.setText(String.format("¥%s", price));
                    n_v_yuanjia.setVisibility(View.GONE);

                    n_v_bear_dangqianjia.setText(String.format("¥%s", sourcePrice));
                    n_v_bear_dangqianjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    n_v_bear_yuanjia.setVisibility(View.GONE);

                }
            } else {
                dandu_jiage_layout.setVisibility(View.VISIBLE);
                no_no_dangqianjia.setText(String.format("¥%s", price));
            }
        }
    }



}
