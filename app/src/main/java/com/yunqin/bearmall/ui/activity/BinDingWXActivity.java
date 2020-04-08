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
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BinDingWXActivity extends BaseActivity {

    @BindView(R.id.wx_id)
    DelEditText mWxId;
    @BindView(R.id.wx_image)
    ImageView mWxImage;

    public static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    public static final int REQUEST_CODE_CROP_IMAGE = 2;
    private Uri iconUri;
    private Uri cropImageUri = null;
    private Bitmap mBitmap = null;

    @Override
    public int layoutId() {
        return R.layout.activity_bin_ding_wx;
    }

    @Override
    public void init() {
        requestAllPower();//获取动态权限
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
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE);
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
                    mWxId.getText().toString(), new UpLoadHeadImage.OnUpLoadHeadImageCallBack() {
                        @Override
                        public void onSuccess(String data) {
                            Looper.prepare();  // Can't create handler inside thread that has not called Looper.prepare()。
                            upInfo();
                            try {
                                showToast("上传成功");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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


    //请求应用所需所有权限
    public void requestAllPower() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
    }

    /**
     * 裁减图片操作
     *
     * @param
     */
    private void startCropImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 使图片处于可裁剪状态
        intent.putExtra("crop", "true");
        // 裁剪框的比例（根据需要显示的图片比例进行设置）
        if (Build.MANUFACTURER.contains("HUAWEI")) {
            //硬件厂商为华为的，默认是圆形裁剪框，这里让它无法成圆形
            intent.putExtra("aspectX", 9999);
            intent.putExtra("aspectY", 9998);
        } else {
            //其他手机一般默认为方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }

        // 设置裁剪区域的形状，默认为矩形，也可设置为圆形，可能无效
        //intent.putExtra("circleCrop", true);

        // 让裁剪框支持缩放
        intent.putExtra("scale", true);

        // 传递原图路径
        File cropFile = new File(Environment.getExternalStorageDirectory() + "/crop_image.jpg");


        try {
            if (cropFile.exists()) {
                cropFile.delete();
            }
            cropFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cropImageUri = Uri.fromFile(cropFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);

        // 设置图片的输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // return-data=true传递的为缩略图，小米手机默认传递大图，所以会导致onActivityResult调用失败
        intent.putExtra("return-data", false);

        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent != null) {
            switch (requestCode) {
                // 将选择的图片进行裁剪
                case REQUEST_CODE_CHOOSE_IMAGE:
                    if (intent.getData() != null) {
                        iconUri = intent.getData();
                        startCropImage(iconUri);
                    }
                    break;
                case REQUEST_CODE_CROP_IMAGE:
                    if (resultCode == RESULT_OK) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                            mWxImage.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
                            mBitmap = bitmap;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
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


}
