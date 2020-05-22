package com.yunqin.bearmall.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bbcoupon.ui.activity.PersonalActivity;
import com.bbcoupon.ui.bean.WXInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.bbcoupon.util.JurisdictionUtil;
import com.bbcoupon.util.WindowUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.umeng.commonsdk.debug.D;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.MemberBeanResponse;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.UpLoadHeadImage;
import com.yunqin.bearmall.widget.DelEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permison.PermissonUtil;
import permison.listener.PermissionListener;

public class BinDingWXActivity extends BaseActivity implements RequestContract.RequestView, View.OnClickListener {

    @BindView(R.id.wx_id)
    DelEditText mWxId;
    @BindView(R.id.wx_image)
    ImageView mWxImage;

    private static final int CODE_CAMERA_REQUEST = 2;
    private static final int CODE_GALLERY_REQUEST = 3;
    private Uri iconUri;
    private Uri cropImageUri = null;
    private Bitmap mBitmap = null;
    private int type = 0;
    private RequestPresenter presenter;
    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
    private Uri imageUri;
    private static final int CROP_PICTURE = 4;

    @Override
    public int layoutId() {
        return R.layout.activity_bin_ding_wx;
    }

    @Override
    public void init() {
        presenter = new RequestPresenter();
        presenter.setRelation(this);
        showLoading();
        presenter.onUsersWXInfo(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }

    @OnClick({R.id.toolbar_back, R.id.top_clear, R.id.tijiao, R.id.wx_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.top_clear:
                mWxId.setText("");
                mWxImage.setImageDrawable(getResources().getDrawable(R.mipmap.erweima));
                cropImageUri = null;
                break;
            case R.id.tijiao:
                setWX();
                break;
            case R.id.wx_image:
                PermissonUtil.checkPermission(BinDingWXActivity.this, new PermissionListener() {
                    @Override
                    public void havePermission() {
                        PopupWindow popupWindow = WindowUtils.ShowVirtual(BinDingWXActivity.this, R.layout.item_heard_pop,
                                R.style.bottom_animation, 2);
                        popupWindow.getContentView().findViewById(R.id.sett_cancel).setOnClickListener(BinDingWXActivity.this);
                        popupWindow.getContentView().findViewById(R.id.sett_album).setOnClickListener(BinDingWXActivity.this);
                        popupWindow.getContentView().findViewById(R.id.sett_camera).setOnClickListener(BinDingWXActivity.this);
                    }

                    @Override
                    public void requestPermissionFail() {

                    }
                }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});

                break;
        }
    }

    private void setWX() {
        if (cropImageUri != null && !TextUtils.isEmpty(mWxId.getText().toString())) {
            if (isChinese(mWxId.getText().toString())) {
                showToast("请输入正确格式的微信号");
                return;
            }
            if (!CommonUtils.checkWX(mWxId.getText().toString())) {
                showToast("请输入正确格式的微信号");
                return;
            }
            UpLoadHeadImage.okHttpwxImage(BinDingWXActivity.this, cropImageUri, System.currentTimeMillis() + "-WXImage",
                    mWxId.getText().toString(), new UpLoadHeadImage.OnUpLoadHeadImageCallBack2() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("onSuccess", data);
                            upInfo();
                            try {
                                JSONObject object = new JSONObject(data);
                                if (object.optInt("code") == 1) {
                                    type = 2;
                                    Looper.prepare();
                                    showToast("提交成功");
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("RESULT", type);
                                    setResult(3, intent.putExtras(bundle));
                                    try {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(300);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Looper.loop();
                                } else {
                                    Looper.prepare();
                                    showToast("提交失败");
                                    Looper.loop();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFail(Throwable throwable) {
                            Looper.prepare();  // Can't create handler inside thread that has not called Looper.prepare()。
                            showToast("上传失败");
                            Looper.loop();
                        }
                    });
        } else {
            if (cropImageUri == null && TextUtils.isEmpty(mWxId.getText().toString())) {
                showToast("请输入微信号");
                return;
            }
            if (cropImageUri == null) {
                showToast("请上传微信二维码");
                return;
            }
            if (TextUtils.isEmpty(mWxId.getText().toString())) {
                showToast("请输入微信号");
                return;
            }
        }
    }

    private void upInfo() {
        if (BearMallAplication.getInstance().getUser() != null) {
            RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberInfo(new HashMap<>()), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        UserInfo userInfo = BearMallAplication.getInstance().getUser();
                        UserInfo.DataBean dataBean = userInfo.getData();
                        MemberBeanResponse response = new Gson().fromJson(data, MemberBeanResponse.class);
                        UserInfo.DataBean.MemberBean memberBean = response.getData();
                        UserInfo.Identity identity = response.getIdentity();
                        dataBean.setMember(memberBean);
                        userInfo.setData(dataBean);
                        userInfo.setIdentity(identity);
                        BearMallAplication.getInstance().setUser(userInfo);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNotNetWork() {

                }

                @Override
                public void onFail(Throwable e) {
                }
            });

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST:
                    try {
                        startPhotoZoom(imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case CODE_GALLERY_REQUEST:
                    if (data != null) {
                        List<Bitmap> map = new ArrayList<>();
                        ArrayList<String> backPics = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                        for (int i = 0; i < backPics.size(); i++) {
                            try {
                                File file = new File(backPics.get(i));
                                cropImageUri = Uri.fromFile(file);
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), cropImageUri                     );
                                map.add(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        mWxImage.setImageBitmap(map.get(0));
                    }
                    break;
                case CROP_PICTURE:
                    try {
                        Bitmap headShot = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                        mWxImage.setImageBitmap(headShot);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public boolean isChinese(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c))
                return true;// 有一个中文字符就返回
        }
        return false;
    }

    public boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof WXInfor) {
            WXInfor wxInfor = (WXInfor) data;
            if (wxInfor.getData() != null) {
                mWxId.setText(wxInfor.getData().getWeixin());
                mWxId.setSelection(mWxId.length());
                Glide.with(BinDingWXActivity.this)
                        .asBitmap()
                        .load(wxInfor.getData().getWxQrcode())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                mWxImage.setImageBitmap(resource);
                                saveBitmap(resource, "wxImage");
                                Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() +
                                        "/InvitationPoster/wxImage" + ".jpg"));
                                cropImageUri = uri;
                            }
                        });
            }
        }
        hiddenLoadingView();
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
    }

    private void saveBitmap(Bitmap bitmap, String tmplName) {
        String appDir = Environment.getExternalStorageDirectory() + "/InvitationPoster/";
        try {
            File dirFile = new File(appDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File file = new File(appDir, tmplName + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sett_cancel://修改头像
                WindowUtils.dismissBrightness(BinDingWXActivity.this);
                break;
            case R.id.sett_album://相册
                setHealdImage(0);
                WindowUtils.dismissBrightness(BinDingWXActivity.this);
                break;
            case R.id.sett_camera://相机
                WindowUtils.dismissBrightness(BinDingWXActivity.this);
                setHealdImage(1);
                break;
        }
    }

    public void startPhotoZoom(Uri uri) {
        File CropPhoto = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        try {
            if (CropPhoto.exists()) {
                CropPhoto.delete();
            }
            CropPhoto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropImageUri = Uri.fromFile(CropPhoto);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }

    //调用相机，相册
    public void setHealdImage(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        PermissonUtil.checkPermission(BinDingWXActivity.this, new PermissionListener() {
            @Override
            public void havePermission() {
                if (id == 0) {
                    ImageSelector.builder()
                            .useCamera(false) // 设置是否使用拍照
                            .setCrop(true)
                            .setSingle(true)  //设置是否单选
                            .start(BinDingWXActivity.this, CODE_GALLERY_REQUEST);
                }
                if (id == 1) {
                    File file = new File(SAVED_IMAGE_DIR_PATH + "/" + System.currentTimeMillis() + ".png");
                    //通过FileProvider创建一个content类型的Uri
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(BinDingWXActivity.this, "com.bearmall.app", file);
                        Intent intentCamera = new Intent();
                        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        //将拍照结果保存至photo_file的Uri中，不保留在相册中
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intentCamera, CODE_CAMERA_REQUEST);
                    } else {
                        imageUri = Uri.fromFile(file);
                        Intent intentCamera = new Intent();
                        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        //将拍照结果保存至photo_file的Uri中，不保留在相册中
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intentCamera, CODE_CAMERA_REQUEST);
                    }
                }
            }

            @Override
            public void requestPermissionFail() {
                showToast("获取权限失败");
            }
        }, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE});
    }
}
