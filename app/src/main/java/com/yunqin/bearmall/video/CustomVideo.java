package com.yunqin.bearmall.video;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.util.RudenessScreenHelper;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserActionStd;
import cn.jzvd.JzvdStd;

public class CustomVideo extends JzvdStd {

    private LinearLayout playLayout;
    private View wifiAlert;
    private ImageView pauseStartView;
    private ImageView muteView;
    private boolean isMute = true;

    public CustomVideo(Context context) {
        super(context);
    }

    public CustomVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        playLayout = findViewById(R.id.play);
        wifiAlert = findViewById(R.id.wifi_alert);

        pauseStartView = findViewById(R.id.pause_start);
        muteView = findViewById(R.id.mute);

        playLayout.setOnClickListener(this);
        pauseStartView.setOnClickListener(this);
        muteView.setOnClickListener(this);

    }


    @Override
    public void onPrepared() {
        super.onPrepared();
        if (this.currentScreen == SCREEN_WINDOW_LIST){
            isMute = true;
        }else {
            isMute = false;
        }
        changeVolum();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.play:
                wifiAlert.setVisibility(INVISIBLE);
                onEvent(JZUserActionStd.ON_CLICK_START_WIFIDIALOG);
                startVideo();
                WIFI_TIP_DIALOG_SHOWED = true;
                break;

            case R.id.mute:

                isMute = !isMute;
                changeVolum();

                break;

            case R.id.pause_start:

                break;

        }
    }

    @Override
    public void onClickUiToggle() {
        super.onClickUiToggle();
        muteView.setVisibility(bottomContainer.getVisibility());
    }

    @Override
    public void dissmissControlView() {
        super.dissmissControlView();
        post(new Runnable() {
            @Override
            public void run() {
                muteView.setVisibility(bottomContainer.getVisibility());
            }
        });

    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int bottomPro, int retryLayout) {
        super.setAllControlsVisiblity(topCon, bottomCon, startBtn, loadingPro, thumbImg, bottomPro, retryLayout);
        muteView.setVisibility(bottomContainer.getVisibility());

    }

    private void changeVolum() {

        if (isMute){
            JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
            muteView.setImageResource(R.drawable.icon_voice_off);
        }else {
            JZMediaManager.instance().jzMediaInterface.setVolume(1f, 1f);
            muteView.setImageResource(R.drawable.icon_voice_on);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_custom_video;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public void startVideo() {
        super.startVideo();
    }



    //非全屏
    @Override
    public void playOnThisJzvd() {
        super.playOnThisJzvd();
//        JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
//        changeVolum();
    }

    //全屏
    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
        JZMediaManager.instance().jzMediaInterface.setVolume(1f, 1f);
        muteView.setImageResource(R.drawable.icon_voice_on);
        //需要处理一下声音开关
    }



    @Override
    public void showWifiDialog() {

        if (this.currentScreen == SCREEN_WINDOW_LIST){
            onEvent(JZUserActionStd.ON_CLICK_START_WIFIDIALOG);
            startVideo();
        }else if (this.currentScreen == SCREEN_WINDOW_NORMAL){
            startButton.setVisibility(INVISIBLE);
            wifiAlert.setVisibility(VISIBLE);
        }


    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
//        startVideo();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        RudenessScreenHelper.resetDensity(getContext(),750);
    }


}
