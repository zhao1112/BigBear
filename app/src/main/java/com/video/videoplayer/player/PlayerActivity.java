package com.video.videoplayer.player;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.video.videocontroller.StandardVideoController;
import com.videodown.http.DownLoad;
import com.videodown.http.bean.DownBean;
import com.videodown.http.i.OnDownloadListener;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class PlayerActivity extends BaseActivity {

    @BindView(R.id.bearmall_video)
    VideoView mBearmallVideo;
    private boolean isDownloadSuccess = false;
    private String video_url;
    private String path;

    @Override
    public int layoutId() {
        return R.layout.activity_player;
    }

    @Override
    public void init() {

        if (getIntent() == null) return;

        video_url = getIntent().getStringExtra("VODEO_URL");
        mBearmallVideo.setUrl(video_url);
        mBearmallVideo.setVideoController(new StandardVideoController(PlayerActivity.this));
        mBearmallVideo.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoViewManager.instance().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoViewManager.instance().resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoViewManager.instance().release();
    }

    @Override
    public void onBackPressed() {
        if (!VideoViewManager.instance().onBackPressed()) {
            super.onBackPressed();
        }
    }


    @OnClick({R.id.bus_back, R.id.video_dwon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bus_back:
                finish();
                break;
            case R.id.video_dwon:
                download(video_url, 1);
                break;
        }
    }

    private synchronized void download(String video_url, int type) {
        if (TextUtils.isEmpty(video_url)) {
            return;
        }
        if (isDownloadSuccess) {
            return;
        }

        DownBean downBean = new DownBean();
        downBean.setUrl(video_url);
        downBean.setVersion("1");
        String name = video_url.substring(video_url.lastIndexOf("/"), video_url.length());
        downBean.setName(name);


        DownLoad downLoad = new DownLoad(new OnDownloadListener<DownBean>() {
            @Override
            public void onStart(DownBean o) {

            }

            @Override
            public void onPublish(DownBean o, int percent) {
            }

            @Override
            public void onSuccess(DownBean o) {
                isDownloadSuccess = true;
                path = o.getPath();

                if (type == 1) {
                    File file = new File(path);
                    ContentResolver localContentResolver = PlayerActivity.this.getContentResolver();
                    ContentValues localContentValues = getVideoContentValues(PlayerActivity.this, file, System.currentTimeMillis());
                    Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri));
                    showToast("视频已保存到相册");
                }
            }

            @Override
            public void onError(DownBean o) {

            }
        });
        downLoad.start(downBean);

    }


    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }
}
