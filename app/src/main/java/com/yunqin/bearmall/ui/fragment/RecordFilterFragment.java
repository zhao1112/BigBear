package com.yunqin.bearmall.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by chenchen on 2018/7/24.
 */

public class RecordFilterFragment extends BaseFragment {

    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    private String lastSelectID;
    private OnFilterConditionChangedListener listener;

    public void setListener(OnFilterConditionChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_record_filter;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.bg_click})
    void backGroundClick(View view){
        if (getActivity()!=null){
            getActivity().onBackPressed();
        }
    }


    @OnClick({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    public void chooseFilter(View view){
        for (Button button : buttons){
            if ( TextUtils.equals(String.valueOf(button.getTag()),String.valueOf(view.getTag()))){
                button.setSelected(true);
                String tag = String.valueOf(view.getTag());
                if (!TextUtils.equals(lastSelectID,tag)){
                    lastSelectID = tag;
                    if (this.listener != null){
                        this.listener.onChanged(tag);
                    }
                    if (getActivity()!=null){
                        getActivity().onBackPressed();
                    }
                }
            }else {
                button.setSelected(false);
            }
        }
    }


    public interface OnFilterConditionChangedListener{
        void onChanged(String condition);
    }


}
