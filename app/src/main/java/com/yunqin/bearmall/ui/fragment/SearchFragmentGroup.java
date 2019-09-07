package com.yunqin.bearmall.ui.fragment;

import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;

import butterknife.BindView;

/**
 * @author Master
 * @create 2018/8/13 16:24
 */
public class SearchFragmentGroup extends BaseFragment {


    @BindView(R.id.goods_check)
    RadioButton radioButtonone;

    @BindView(R.id.shop_check)
    RadioButton radioButtontwo;

    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;


    @Override
    public int layoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void init() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (radioButtonone.isChecked()) {
                radioButtonone.setTextColor(Color.parseColor("#FFFFFF"));
                radioButtontwo.setTextColor(Color.parseColor("#23A064"));
            } else {
                radioButtonone.setTextColor(Color.parseColor("#23A064"));
                radioButtontwo.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
    }
}
