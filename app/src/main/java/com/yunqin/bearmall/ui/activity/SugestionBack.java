package com.yunqin.bearmall.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.contract.FeedBackContract;
import com.yunqin.bearmall.ui.activity.presenter.FeedBackPresenter;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/8/21
 * @Describe
 */
public class SugestionBack extends BaseActivity implements FeedBackContract.UI{
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.edittext)
    EditText edittext;
    @BindView(R.id.input_number)
    TextView input_number;
    @BindView(R.id.send_btn)
    HighlightButton sendBtn;

    private int inputNumber;

    private FeedBackContract.Presenter presenter;

    @Override
    public int layoutId() {
        return R.layout.activity_sugeation_back;
    }

    @Override
    public void init() {
        toolbarTitle.setText("意见反馈");

        presenter = new FeedBackPresenter(this);

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return(event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });

        edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(120)});
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("Edittext",count+"<--------------------count");
                Log.e("Edittext",start+"<--------------------start");
                Log.e("Edittext",before+"<--------------------before");
                // 此方法有问题，当删除文字的时候会出现错误
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("Edittext",s.length()+"<--------------------Editable");
                inputNumber = s.length();
                if(inputNumber <= 120){
                    input_number.setText(inputNumber+"/120");
                }
            }
        });

    }

    @OnClick({R.id.toolbar_back,R.id.send_btn})
    void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.send_btn:
                if(edittext.getText().toString().trim().length() <= 0){
                    showToast("请输入反馈内容");
                    return;
                }
                showLoading();
                Map map = new HashMap();
                map.put("content",edittext.getText().toString().trim());
                presenter.sendFeed(this,map);
                break;
        }
    }


    @Override
    public void attachData(String data) {
        hiddenLoadingView();
        showToast("反馈成功");
        myHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
        showToast("反馈成功");
        myHandler.sendEmptyMessageDelayed(0,1000);
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
        }
    };
}
