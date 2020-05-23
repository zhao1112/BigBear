package com.bbcoupon.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bbcoupon.ui.bean.CardInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.bbcoupon.util.DialogUtil;
import com.bbcoupon.util.WindowUtils;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.MemberBeanResponse;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.activity.contract.SettingContract;
import com.yunqin.bearmall.ui.activity.presenter.SettingPresenter;
import com.yunqin.bearmall.util.UpLoadHeadImage;
import com.yunqin.bearmall.widget.CircleImageView;

import org.jaaksi.pickerview.adapter.NumericWheelAdapter;
import org.jaaksi.pickerview.widget.BasePickerView;
import org.jaaksi.pickerview.widget.DefaultCenterDecoration;
import org.jaaksi.pickerview.widget.PickerView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        SettingContract.UI, RequestContract.RequestView {

    @BindView(R.id.sett_head_img)
    CircleImageView mSettHeadImg;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.sex)
    TextView mSex;

    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
    private Uri imageUri;
    private static final int CODE_CAMERA_REQUEST = 2;
    private static final int CODE_GALLERY_REQUEST = 3;
    //裁剪图片存放地址的Uri
    private Uri cropImageUri;
    private static final int CROP_PICTURE = 4;
    private String newNeckname;
    private EditText sett_nackname;
    private SettingPresenter presenter;
    private DialogUtil dialogUtil;
    private RequestPresenter mPresenter;
    private String genders;
    private ArrayList<CardInfor> cardItem = new ArrayList<>();
    private ImageView mDele;
    private PickerView<Integer> pickerView;
    private String gendersId;

    @Override
    public int layoutId() {
        return R.layout.activity_persona;
    }

    @Override
    public void init() {

        presenter = new SettingPresenter(this, this);
        mPresenter = new RequestPresenter();
        mPresenter.setRelation(this);
        mPresenter.onMemberInfo(this,new HashMap<>());

        try {
            String heald_image = getIntent().getStringExtra("Heald_Image");
            Glide.with(this)
                    .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                    .load(heald_image).into(mSettHeadImg);
            mName.setText(BearMallAplication.getInstance().getUser().getData().getMember().getNickName());
            if (BearMallAplication.getInstance().getUser().getData().getMember().getGender() != null) {
                mSex.setText(BearMallAplication.getInstance().getUser().getData().getMember().getGender());
            } else {
                mSex.setText("请选择");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardItem.add(new CardInfor(0, "男"));
        cardItem.add(new CardInfor(1, "女"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.setUntying(this);
    }

    @OnClick({R.id.toolbar_back, R.id.sett_head, R.id.sett_nickname, R.id.sett_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.sett_head:
                if (ConstantUtil.isFastClick()) {
                    PopupWindow popupWindow = WindowUtils.ShowVirtual(PersonalActivity.this, R.layout.item_heard_pop,
                            R.style.bottom_animation, 2);
                    popupWindow.getContentView().findViewById(R.id.sett_cancel).setOnClickListener(this);
                    popupWindow.getContentView().findViewById(R.id.sett_album).setOnClickListener(this);
                    popupWindow.getContentView().findViewById(R.id.sett_camera).setOnClickListener(this);
                }
                break;
            case R.id.sett_nickname:
                if (ConstantUtil.isFastClick()) {
                    dialogUtil = DialogUtil.getInstance();
                    dialogUtil.setContext(PersonalActivity.this);
                    View nackname = dialogUtil.getDialog(R.layout.item_sett_nackname_pop);
                    nackname.findViewById(R.id.sett_cancel_nack).setOnClickListener(this);
                    nackname.findViewById(R.id.sett_confirm).setOnClickListener(this);
                    sett_nackname = nackname.findViewById(R.id.sett_nackname);
                    sett_nackname.addTextChangedListener(textWatcher);
                    mDele = nackname.findViewById(R.id.dele);
                    mDele.setOnClickListener(this);
                }
                break;
            case R.id.sett_sex:
                PopupWindow popupWindow = WindowUtils.ShowVirtual(PersonalActivity.this, R.layout.pickerview_custom_options,
                        R.style.bottom_animation, 2);
                pickerView = popupWindow.getContentView().findViewById(R.id.pickerview);
                pickerView.setAdapter(new NumericWheelAdapter(1, 2));
                pickerView.setHorizontal(false);
                pickerView.setTextSize(15, 18);
                pickerView.setColor(getResources().getColor(R.color.sex_line_true), getResources().getColor(R.color.sex_line_false));
                pickerView.setIsCirculation(false);
                pickerView.setCanTap(false);
                pickerView.setDisallowInterceptTouch(false);
                pickerView.setVisibleItemCount(5);
                pickerView.setItemSize(50);
                pickerView.setFormatter(new BasePickerView.Formatter() {
                    @Override
                    public CharSequence format(BasePickerView pickerView, int position, CharSequence charSequence) {
                        return cardItem.get(position).getCardNo();
                    }
                });
                DefaultCenterDecoration centerDecoration =
                        new DefaultCenterDecoration(PersonalActivity.this).setLineColor(getResources().getColor(R.color.sex_line));
                pickerView.setCenterDecoration(centerDecoration);
                pickerView.setOnSelectedListener(new BasePickerView.OnSelectedListener() {
                    @Override
                    public void onSelected(BasePickerView pickerView, int position) {
                        gendersId = position + "";
                        Log.e("onSelected", position + "");
                    }
                });
                popupWindow.getContentView().findViewById(R.id.iv_cancel).setOnClickListener(this);
                popupWindow.getContentView().findViewById(R.id.tv_finish).setOnClickListener(this);
                break;
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (sett_nackname.getText().toString().length() > 0) {
                mDele.setVisibility(View.VISIBLE);
            } else {
                mDele.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sett_cancel://修改头像
                WindowUtils.dismissBrightness(PersonalActivity.this);
                break;
            case R.id.sett_cancel_nack://修改名称
                dialogUtil.dismissDialog();
                break;
            case R.id.sett_album://相册
                WindowUtils.dismissBrightness(PersonalActivity.this);
                setHealdImage(0);
                break;
            case R.id.sett_camera://相机
                WindowUtils.dismissBrightness(PersonalActivity.this);
                setHealdImage(1);
                break;
            case R.id.sett_confirm:
                if (sett_nackname.getText().toString().trim().isEmpty()) {
                    showToast("请输入正确昵称", Gravity.CENTER);
                    return;
                }
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
            case R.id.dele:
                sett_nackname.setText("");
                break;
            case R.id.iv_cancel:
                WindowUtils.dismissBrightness(PersonalActivity.this);
                break;
            case R.id.tv_finish:
                showLoading();
                Map<String, String> map = new HashMap<>();
                if (gendersId == null) {
                    map.put("gender", "0");
                    genders = "男";
                }
                if ("0".equals(gendersId)) {
                    map.put("gender", "0");
                    genders = "男";
                }
                if ("1".equals(gendersId)) {
                    map.put("gender", "1");
                    genders = "女";
                }
                mPresenter.onUpdateGender(PersonalActivity.this, map);
                WindowUtils.dismissBrightness(PersonalActivity.this);
                break;
        }
    }

    //调用相机，相册
    public void setHealdImage(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
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
        }, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE});
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
    public void onSuccess(Object data) {
        if (data instanceof String) {
            try {
                JSONObject jsonObject = new JSONObject((String) data);
                if (jsonObject.getInt("code") == 1) {
                    UserInfo userInfo = BearMallAplication.getInstance().getUser();
                    UserInfo.DataBean.MemberBean member = userInfo.getData().getMember();
                    member.setGender(genders);
                    BearMallAplication.getInstance().setUser(userInfo);
                    mSex.setText(BearMallAplication.getInstance().getUser().getData().getMember().getGender());
                    showToast("修改成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (data instanceof MemberBeanResponse) {
            MemberBeanResponse memberBeanResponse = (MemberBeanResponse) data;
            UserInfo userInfo = BearMallAplication.getInstance().getUser();
            UserInfo.DataBean dataBean = userInfo.getData();
            UserInfo.DataBean.MemberBean memberBean = memberBeanResponse.getData();
            UserInfo.Identity identity = memberBeanResponse.getIdentity();
            dataBean.setMember(memberBean);
            userInfo.setData(dataBean);
            userInfo.setIdentity(identity);
            BearMallAplication.getInstance().setUser(userInfo);
            mName.setText(BearMallAplication.getInstance().getUser().getData().getMember().getNickName());
            if (BearMallAplication.getInstance().getUser().getData().getMember().getGender() != null) {
                mSex.setText(BearMallAplication.getInstance().getUser().getData().getMember().getGender());
            } else {
                mSex.setText("请选择");
            }
        }
        hiddenLoadingView();
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
    }

    @Override
    public void onFail(Throwable throwable) {
        try {
            hiddenLoadingView();
            Looper.prepare();  // Can't create handler inside thread that has not called Looper.prepare()。
            showToast("上传失败");
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        mName.setText(BearMallAplication.getInstance().getUser().getData().getMember().getNickName());
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
