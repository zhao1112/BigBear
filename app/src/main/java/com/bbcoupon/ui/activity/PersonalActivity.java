package com.bbcoupon.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bbcoupon.util.DialogUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.activity.contract.SettingContract;
import com.yunqin.bearmall.ui.activity.presenter.SettingPresenter;
import com.yunqin.bearmall.util.PopUtil;
import com.yunqin.bearmall.util.UpLoadHeadImage;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.DelEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import permison.PermissonUtil;
import permison.listener.PermissionListener;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon
 * @DATE 2020/4/21
 */
public class PersonalActivity extends BaseActivity implements View.OnClickListener, UpLoadHeadImage.OnUpLoadHeadImageCallBack,
        SettingContract.UI {

    @BindView(R.id.sett_head_img)
    CircleImageView mSettHeadImg;

    private PopUtil instance;
    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
    private Uri imageUri;
    private static final int CODE_CAMERA_REQUEST = 2;
    private static final int CODE_GALLERY_REQUEST = 3;
    //裁剪图片存放地址的Uri
    private Uri cropImageUri;
    private static final int CROP_PICTURE = 4;
    private String newNeckname;
    private DelEditText sett_nackname;
    private SettingPresenter presenter;
    private DialogUtil dialogUtil;
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<String> options2Items_01 = new ArrayList<>();
    private ArrayList<String> options2Items_02 = new ArrayList<>();


    @Override
    public int layoutId() {
        return R.layout.activity_persona;
    }

    @Override
    public void init() {

        presenter = new SettingPresenter(this, this);

        try {
            String heald_image = getIntent().getStringExtra("Heald_Image");
            Glide.with(this)
                    .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                    .load(heald_image).into(mSettHeadImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        options2Items_01.add("男");
        options2Items_02.add("女");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
    }

    @OnClick({R.id.toolbar_back, R.id.sett_head, R.id.sett_nickname, R.id.sett_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.sett_head:
                instance = PopUtil.getInstance();
                instance.setContext(PersonalActivity.this);
                View popView = instance.getPopView(R.layout.item_heard_pop, 0);
                popView.findViewById(R.id.sett_cancel).setOnClickListener(this);
                popView.findViewById(R.id.sett_album).setOnClickListener(this);
                popView.findViewById(R.id.sett_camera).setOnClickListener(this);
                break;
            case R.id.sett_nickname:
                dialogUtil = DialogUtil.getInstance();
                dialogUtil.setContext(PersonalActivity.this);
                View nackname = dialogUtil.getDialog(R.layout.item_sett_nackname_pop);
                nackname.findViewById(R.id.sett_cancel_nack).setOnClickListener(this);
                nackname.findViewById(R.id.sett_confirm).setOnClickListener(this);
                sett_nackname = nackname.findViewById(R.id.sett_nackname);
                break;
            case R.id.sett_sex:

                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        Toast.makeText(PersonalActivity.this, options2Items.get(options1).get(0), Toast.LENGTH_SHORT).show();
                        //返回的分别是三个级别的选中位置
                    }
                })
                        .setTitleText("")
                        .setContentTextSize(15)//设置滚轮文字大小
                        .setDividerColor(getResources().getColor(R.color.sex))//设置分割线的颜色
                        .setSelectOptions(0, 1)//默认选中项
                        .setBgColor(getResources().getColor(R.color.white))
                        .setTitleBgColor(getResources().getColor(R.color.white))
                        .setCancelColor(getResources().getColor(R.color.business))
                        .setSubmitColor(getResources().getColor(R.color.business))
                        .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setLabels("", "", "")
                        .setOutSideColor(0x00000000) //设置外部遮罩颜色
                        .build();
                pvOptions.setPicker(options2Items);//二级选择器
                pvOptions.show();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sett_cancel://修改头像
                instance.dismissPopupWindow();
                break;
            case R.id.sett_cancel_nack://修改名称
                dialogUtil.dismissDialog();
                break;
            case R.id.sett_album://相册
                instance.dismissPopupWindow();
                setHealdImage(0);
                break;
            case R.id.sett_camera://相机
                instance.dismissPopupWindow();
                setHealdImage(1);
                break;
            case R.id.sett_confirm:
                if (!TextUtils.isEmpty(sett_nackname.getText().toString())) {
                    showLoading();
                    newNeckname = sett_nackname.getText().toString();
                    Map map = new HashMap();
                    map.put("nickName", sett_nackname.getText().toString());
                    presenter.changeNickName(this, map);
                    dialogUtil.dismissDialog();
                } else {
                    showToast("昵称不能为空", Gravity.CENTER);
                }
                break;
        }
    }

    //调用相机，相册
    public void setHealdImage(int id) {
        PermissonUtil.checkPermission(PersonalActivity.this, new PermissionListener() {
            @Override
            public void havePermission() {
                if (id == 0) {
                    ImageSelector.builder()
                            .useCamera(false) // 设置是否使用拍照
                            .setCrop(true)
                            .setSingle(true)  //设置是否单选
                            .start(PersonalActivity.this, CODE_GALLERY_REQUEST);
                }
                if (id == 1) {
                    File file = new File(SAVED_IMAGE_DIR_PATH + "/" + System.currentTimeMillis() + ".png");
                    //通过FileProvider创建一个content类型的Uri
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(PersonalActivity.this, "com.bearmall.app", file);
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
        }, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE});
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
                                imageUri = Uri.fromFile(file);
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                map.add(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        mSettHeadImg.setImageBitmap(map.get(0));
                        upHeadImage(imageUri);
                    }
                    break;
                case CROP_PICTURE:
                    try {
                        Bitmap headShot = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                        mSettHeadImg.setImageBitmap(headShot);
                        upHeadImage(cropImageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
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
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }

    //上传头像
    public void upHeadImage(Uri imageUri) {
        showLoading();
        Map<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("a", "1");
        UpLoadHeadImage.okHttpUpLoadImage(this, imageUri, System.currentTimeMillis() + "-headImage", mHashMap, this);
    }

    @Override
    public void onSuccess(String data) {
        try {
            hiddenLoadingView();
            JSONObject jsonObject = new JSONObject(data);
            String url = jsonObject.getJSONObject("data").getString("iconUrl");
            UserInfo userInfo = BearMallAplication.getInstance().getUser();
            UserInfo.DataBean dataBean = userInfo.getData();
            UserInfo.DataBean.MemberBean memberBean = dataBean.getMember();
            memberBean.setIconUrl(url);
            dataBean.setMember(memberBean);
            userInfo.setData(dataBean);
            BearMallAplication.getInstance().setUser(userInfo);
            Looper.prepare();  // Can't create handler inside thread that has not called Looper.prepare()。
            showToast("上传成功");
            Looper.loop();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(Throwable throwable) {
        hiddenLoadingView();
        Looper.prepare();  // Can't create handler inside thread that has not called Looper.prepare()。
        showToast("上传失败");
        Looper.loop();
    }

    @Override
    public void bindWx() {

    }

    @Override
    public void outSussess() {

    }

    @Override
    public void outFail() {

    }

    @Override
    public void changeNickNameSussess() {
        hiddenLoadingView();
        UserInfo userInfo = BearMallAplication.getInstance().getUser();
        UserInfo.DataBean dataBean = userInfo.getData();
        UserInfo.DataBean.MemberBean memberBean = dataBean.getMember();
        if (!TextUtils.isEmpty(newNeckname)) {
            memberBean.setNickName(newNeckname);
        }
        dataBean.setMember(memberBean);
        userInfo.setData(dataBean);
        BearMallAplication.getInstance().setUser(userInfo);
        showToast("修改成功");
    }

    @Override
    public void attachData(String data) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFail() {

    }
}
