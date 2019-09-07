package com.yunqin.bearmall.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.videodown.DownLoadDialog;
import com.videodown.http.DownLoad;
import com.videodown.http.bean.DownBean;
import com.videodown.http.i.OnDownloadListener;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.GuideArticleList;
import com.yunqin.bearmall.util.NetUtils;
import com.yunqin.bearmall.util.ShareVideoUtils;
import com.yunqin.bearmall.video.CustomVideo;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;

public class VideoDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.second_title)
    TextView subTitleView;
    @BindView(R.id.content)
    TextView contentView;
    @BindView(R.id.video)
    CustomVideo video;

    private GuideArticleList articleList;
    private boolean isDownloadSuccess;
    private String path;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};



    @Override
    public int layoutId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void init() {

        //没申请权限


        Intent intent = getIntent();
        if (intent != null){

            articleList = intent.getParcelableExtra("Artical");
            int videoLastSatus = intent.getIntExtra("Status",Jzvd.CURRENT_STATE_IDLE);

            String title = articleList.getTitle();
            String subTitle = articleList.getCaption();
            String content = articleList.getContent();

            titleView.setText(TextUtils.isEmpty(title)?"视频详情":title);
            subTitleView.setText(TextUtils.isEmpty(subTitle)?"":subTitle);
            contentView.setText(TextUtils.isEmpty(content)?"":content);

            String videoUrl = articleList.getGuideVideo();
            if (!TextUtils.isEmpty(videoUrl)){
                video.setUp(videoUrl,null,Jzvd.SCREEN_WINDOW_NORMAL);
            }
            Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_tuijian)).load(articleList.getImage()).into(video.thumbImageView);
            if (videoLastSatus == Jzvd.CURRENT_STATE_PLAYING || NetUtils.isWifi(this)){
                video.startVideo();
            }

//            video.startButton.performClick();
//            video.startVideo();
        }

    }

    private synchronized void download(){
        if (articleList==null){
            return;
        }
        if (isDownloadSuccess){
            showShare();
            return;
        }

        DownBean downBean = new DownBean();
        downBean.setUrl(articleList.getGuideVideo());
        downBean.setVersion("1");
        String name = articleList.getGuideVideo().substring(articleList.getGuideVideo().lastIndexOf("/"), articleList.getGuideVideo().length());
        downBean.setName(name);

        DownLoadDialog downLoadDialog = new DownLoadDialog(this).builder();
        downLoadDialog.setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setTitle("正在保存到视频相册")
                .show();

        DownLoad downLoad = new DownLoad(new OnDownloadListener<DownBean>() {
            @Override
            public void onStart(DownBean o) {

            }

            @Override
            public void onPublish(DownBean o, int percent) {
                downLoadDialog.setProgress(percent);
            }

            @Override
            public void onSuccess(DownBean o) {

                isDownloadSuccess = true;
                downLoadDialog.dismiss();
                path = o.getPath();
                showShare();

            }

            @Override
            public void onError(DownBean o) {

            }
        });
        downLoad.start(downBean);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    protected void onDestroy() {
        Jzvd.releaseAllVideos();
        super.onDestroy();
    }

    private void showShare() {

        ShareVideoUtils.share(this,articleList.getGuideVideo(),articleList.getTitle(),articleList.getCaption(),articleList.getImage(),articleList.getGuideVideo());

    }

    private boolean checkHasPermission(){

        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestpermission(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                    //跳设置没写
                }else {
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (checkHasPermission()){
            download();
        }

    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @OnClick({R.id.toolbar_back,R.id.right_icon})
    public void click(View view){

        switch (view.getId()){

            case R.id.toolbar_back:

                finish();

                break;

            case R.id.right_icon:

                if (checkHasPermission()){
                    download();
                }else {
                    requestpermission();
                }

                break;

        }

    }

}
