package com.yunqin.bearmall.ui.fragment;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * Created by chenchen on 2018/7/25.
 */

public class QusetionFragment extends BaseFragment implements View.OnClickListener{

    public static QusetionFragment fragmentWithQusetion(int tag,String title,String option0,String option1,String option2,String option3){
        QusetionFragment fragment = new QusetionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag",tag);
        bundle.putString("title",title);
        bundle.putString("option0",option0);
        bundle.putString("option1",option1);
        bundle.putString("option2",option2);
        bundle.putString("option3",option3);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.anwer_container)
    LinearLayout anwerContainer;

    @BindView(R.id.question)
    TextView titleView;


    private List<String> data = new ArrayList<>();
    private OnAnwerItemClickedListener onAnwerItemClickedListener;
    private int tag;
    private boolean isIntercept;
    private String title;

    public OnAnwerItemClickedListener getOnAnwerItemClicked() {
        return onAnwerItemClickedListener;
    }

    public void setOnAnwerItemClickedListener(OnAnwerItemClickedListener onAnwerItemClickedListener) {
        this.onAnwerItemClickedListener = onAnwerItemClickedListener;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_question;
    }

    @Override
    public void init() {

        Bundle bundle = getArguments();
        if (bundle != null){
            String option0 = bundle.getString("option0");
            String option1 = bundle.getString("option1");
            String option2 = bundle.getString("option2");
            String option3 = bundle.getString("option3");
            title = bundle.getString("title");
            tag  = bundle.getInt("tag");
            orginzeData(option0,option1,option2,option3);
        }

        titleView.setText(title);
        for (int i=0;i<data.size();i++){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_answer_text,null,false);
            TextView textView = view.findViewById(R.id.answer_text);
            textView.setTag(i);
            textView.setOnClickListener(this);
            textView.setText(data.get(i));
            anwerContainer.addView(view);
        }



    }

    private void orginzeData(String option0, String option1, String option2, String option3) {

        if (!TextUtils.isEmpty(option0)){
            data.add(option0);
        }
        if (!TextUtils.isEmpty(option1)){
            data.add(option1);
        }
        if (!TextUtils.isEmpty(option2)){
            data.add(option2);
        }
        if (!TextUtils.isEmpty(option3)){
            data.add(option3);
        }

    }

    @Override
    public void onClick(View v) {

        if (isIntercept){

        }else {
            v.setSelected(true);
            isIntercept = true;
            String answer = "A";
            switch ((int)v.getTag()){
                case 0:
                    answer = "A";
                    break;

                case 1:
                    answer = "B";
                    break;

                case 2:
                    answer = "C";
                    break;

                case 3:
                    answer = "D";
                    break;
            }
            if (onAnwerItemClickedListener != null){
                onAnwerItemClickedListener.onAnswerClicked(this.tag,answer);
            }
        }

    }


    public interface OnAnwerItemClickedListener{

        void onAnswerClicked(int tag,String answer);

    }

}
