package com.yunqin.bearmall.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.newversions.passwd.PwdActivity;
import com.newversions.passwd.SettingPwdActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SettingBean;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.eventbus.PayPasswordEvent;
import com.yunqin.bearmall.eventbus.TrolleyCountEvent;
import com.yunqin.bearmall.inter.ChangeHeadImageCallBack;
import com.yunqin.bearmall.inter.ChangeNickNameCallBack;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.ui.activity.contract.SettingContract;
import com.yunqin.bearmall.ui.activity.presenter.SettingPresenter;
import com.yunqin.bearmall.util.CacheClearManager;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.NetUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.util.UpLoadHeadImage;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author AYWang
 * @create 2018/8/8
 * @Describe
 */
public class SettingActivity extends BaseActivity implements SettingContract.UI, JoinZeroCallBack,
        ChangeNickNameCallBack, PlatformActionListener, ChangeHeadImageCallBack, UpLoadHeadImage.OnUpLoadHeadImageCallBack {
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.user_head_img)
    CircleImageView userHeadImg;
    @BindView(R.id.user_id_number)
    TextView userIdNumber;
    @BindView(R.id.user_level)
    TextView userLevel;
    @BindView(R.id.phone_number)
    TextView phoneNumber;
    @BindView(R.id.is_bind_wx)
    TextView isBindWx;
    @BindView(R.id.reset_pwd)
    LinearLayout resetPwd;
    @BindView(R.id.message_switch)
    SwitchButton messageSwitch;
    @BindView(R.id.cache_size)
    TextView cacheSize;
    @BindView(R.id.clear_cache)
    LinearLayout clearCache;
    @BindView(R.id.suggestion_back)
    LinearLayout suggestionBack;
    @BindView(R.id.about_bear)
    LinearLayout aboutBear;

    @BindView(R.id.out_layout)
    HighlightButton out_layout;

    @BindView(R.id.change_nickname_layout)
    LinearLayout change_nickname_layout;
    @BindView(R.id.bind_wx)
    LinearLayout bind_wx;

    @BindView(R.id.user_head_layout)
    LinearLayout user_head_layout;

    private SettingContract.Presenter presenter;

    private String cacheSizeNumber = "";

    private String nickName = "";

    private String userPhoneNumber = "";

    @Override
    public int layoutId() {
        return R.layout.activity_setting;
    }


    @Override
    protected void onResume() {
        super.onResume();
        // TODO 判断设置密码...
        presenter.start(this);
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        toolbarTitle.setText("账号与安全");
        try {
            cacheSizeNumber = CacheClearManager.getTotalCacheSize(SettingActivity.this);
            cacheSize.setText(cacheSizeNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading();
        presenter = new SettingPresenter(this, this);
        presenter.start(this);
        if (SharedPreferencesHelper.get(SettingActivity.this, "isPush", "0").equals("0")) {
            messageSwitch.setChecked(false);
        } else {
            messageSwitch.setChecked(true);
        }

        messageSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    SharedPreferencesHelper.put(SettingActivity.this, "isPush", "1");
                    showToast("已关闭消息推送");
                    PushManager.getInstance().turnOffPush(SettingActivity.this);
                } else {
                    SharedPreferencesHelper.put(SettingActivity.this, "isPush", "0");
                    showToast("已打开消息推送");
                    PushManager.getInstance().turnOnPush(SettingActivity.this);
                }
            }
        });
    }

    @OnClick({R.id.toolbar_back, R.id.out_layout, R.id.reset_pwd, R.id.clear_cache, R.id.change_nickname_layout
            , R.id.suggestion_back, R.id.bind_wx, R.id.about_bear, R.id.user_head_layout, R.id.ye_pay_mm})
    void onClick(View view) {
        if (!NetUtils.isConnected(this)) {
            Toast.makeText(this, "请检查网络!", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.ye_pay_mm:
                goToMiMa();
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.out_layout:
                showLoading();
                presenter.out(this);
                break;
            case R.id.reset_pwd:
                //todo 重置完密码 重新登陆
                RegiestOrForgetPwdActivity.starActivity(SettingActivity.this, RegiestOrForgetPwdActivity.FORMFORGETPWD, userPhoneNumber);
                break;
            case R.id.clear_cache:
                if (cacheSizeNumber.equals("0KB")) {
                    showToast("暂无缓存");
                    return;
                }
                DialogUtils.tipSearchDialog(this, 200, "当前共有缓存" + cacheSizeNumber, this);
                break;
            case R.id.change_nickname_layout:
                DialogUtils.changeNickName(this, this);
                break;
            case R.id.suggestion_back:
                StarActivityUtil.starActivity(this, SugestionBack.class);
                break;
            case R.id.bind_wx:
                if (isBindWx.getText().toString().equals("已绑定")) {
                    showToast("您已绑定微信");
                    return;
                }
                if (!isQQClientAvailable(this)) {
                    showToast("未安装微信");
                } else {
                    showLoading();
                    Platform platform = ShareSDK.getPlatform(Wechat.NAME);
                    platform.SSOSetting(false);
                    platform.setPlatformActionListener(this);
                    platform.authorize();
                }
                break;
            case R.id.about_bear:
                StarActivityUtil.starActivity(this, AboutBearMall.class);
                break;
            case R.id.user_head_layout:
                DialogUtils.changeHeadImage(this, this);
                break;
            default:
                break;
        }
    }

    private void goToMiMa() {

        if (isSetPayPwd) {
            startActivity(new Intent(SettingActivity.this, PwdActivity.class));
        } else {
            startActivity(new Intent(SettingActivity.this, SettingPwdActivity.class));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(PayPasswordEvent event) {
        isSetPayPwd = event.isSetPwd();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void changeNickNameSussess() {
        hiddenLoadingView();
        userIdNumber.setText(nickName);
        UserInfo userInfo = BearMallAplication.getInstance().getUser();
        UserInfo.DataBean dataBean = userInfo.getData();
        UserInfo.DataBean.MemberBean memberBean = dataBean.getMember();
        memberBean.setNickName(nickName);
        dataBean.setMember(memberBean);
        userInfo.setData(dataBean);
        BearMallAplication.getInstance().setUser(userInfo);
        showToast("修改成功");
    }


    private boolean isSetPayPwd = false;
    private boolean SettingBean = false;


    @Override
    public void attachData(String data) {
        hiddenLoadingView();
        SettingBean settingBean = new Gson().fromJson(data, SettingBean.class);
        userIdNumber.setText(settingBean.getData().getInfo().getNickName());
        Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                .load(settingBean.getData().getInfo().getIconUrl()).into(userHeadImg);
        phoneNumber.setText(settingBean.getData().getInfo().getMobile());
        userPhoneNumber = settingBean.getData().getInfo().getMobile();
        userLevel.setText(settingBean.getData().getInfo().isMember() ? "金熊会员" : "普通会员");

        isSetPayPwd = settingBean.getData().getInfo().isSetPayPwd();

        if (settingBean.getData().getInfo().getIsBindWx() == 1) {
            isBindWx.setText("已绑定");
        } else {
            isBindWx.setText("未绑定");
        }
    }

    @Override
    public void onError() {
        hiddenLoadingView();
    }

    @Override
    public void onFail() {
        showToast("修改失败");
        hiddenLoadingView();
    }

    @Override
    public void sureBtn(int flag) {
        if (flag == 200) {
            try {
                showLoading();
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        CacheClearManager.clearAllCache(SettingActivity.this);
                        Thread.sleep(3000);
                        e.onNext("123");
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String stringStringMap) {
                                hiddenLoadingView();
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                try {
                                    cacheSize.setText(CacheClearManager.getTotalCacheSize(SettingActivity.this));
                                } catch (Exception e) {

                                }
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void canle() {

    }

    @Override
    public void dialogCallBack(String nickName) {
        showLoading();
        Map map = new HashMap();
        map.put("nickName", nickName);
        this.nickName = nickName;
        presenter.changeNickName(this, map);
    }


    @Override
    public void bindWx() {
        isBindWx.setText("已绑定");
        showToast("绑定成功");
    }

    @Override
    public void outSussess() {
        hiddenLoadingView();
        showToast("退出登录");
        BearMallAplication.getInstance().setNullUser();
        SharedPreferencesHelper.clearWhichOne(SettingActivity.this, "isPush");
        EventBus.getDefault().post(new TrolleyCountEvent(0));
        finish();
    }

    @Override
    public void outFail() {
        hiddenLoadingView();
        showToast("退出失败");
    }

    /**
     * 第三方登陆回掉
     *
     * @param platform
     * @param i
     * @param hashMap
     */

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        hiddenLoadingView();
        Constans.params.clear();
        Constans.params.put("open_id", platform.getDb().get("unionid"));
        Constans.params.put("wxopen_id", platform.getDb().get("openid"));
        Constans.params.put("bindType", 1 + "");
        presenter.bindWx(this, Constans.params);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        hiddenLoadingView();
        showToast("绑定错误" + throwable.toString());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        hiddenLoadingView();
        showToast("绑定微信取消");
    }


    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void choseWhat(int flag) {
        //1 相机  2 相册
        if (flag == 1) {
            autoObtainCameraPermission();
        } else {
            ImageSelector.builder()
                    .useCamera(false) // 设置是否使用拍照
                    .setCrop(true)
                    .setSingle(true)  //设置是否单选
                    .start(SettingActivity.this, CODE_GALLERY_REQUEST); // 打开相册
        }
    }


    /**
     * 照相
     */
    /**
     * 拍照路径
     */
    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
    Uri imageUri;
    private static final int CODE_CAMERA_REQUEST = 2;
    private static final int CODE_GALLERY_REQUEST = 3;
    private static final int CROP_PICTURE = 4;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0;

    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "您已经拒绝过一次", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                File file = new File(SAVED_IMAGE_DIR_PATH + "/" + System.currentTimeMillis() + ".png");

                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(SettingActivity.this, "com.bearmall.app", file);
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
            } else {
                Toast.makeText(this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == CODE_CAMERA_REQUEST) {
                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                    userHeadImg.setImageBitmap(bitmap);
                    startPhotoZoom(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CODE_GALLERY_REQUEST) {
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
                    userHeadImg.setImageBitmap(map.get(0));
                    upHeadImage(imageUri);
                }
            } else if (requestCode == CROP_PICTURE) {
                try {
                    Bitmap headShot = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                    userHeadImg.setImageBitmap(headShot);
                    upHeadImage(cropImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        File file = new File(SAVED_IMAGE_DIR_PATH + "/" + System.currentTimeMillis() + ".png");

                        imageUri = Uri.fromFile(file);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            imageUri = FileProvider.getUriForFile(SettingActivity.this, "com.bearmall.app", file);
                        }

                        Intent intentCamera = new Intent();
                        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        //将拍照结果保存至photo_file的Uri中，不保留在相册中
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intentCamera, CODE_CAMERA_REQUEST);


                    } else {
                        Log.e("TAG", "设备没有SD卡");
                    }
                } else {
                    Log.e("TAG", "请允许打开相机！！");
                }

                break;
        }


    }


    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    //裁剪图片存放地址的Uri
    private Uri cropImageUri;

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


    public void upHeadImage(Uri imageUri) {
        showLoading();
        Map<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("a", "1");
        UpLoadHeadImage.okHttpUpLoadImage(this, imageUri, System.currentTimeMillis() + "-headImage", mHashMap, this);
    }


    @Override
    public void onSuccess(String data) {
        //上传头像成功
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
            presenter.start(this);
            showToast("上传成功");
            Looper.loop();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(Throwable throwable) {
        //上传头像失败
        hiddenLoadingView();
        Looper.prepare();  // Can't create handler inside thread that has not called Looper.prepare()。
        showToast("上传失败");
        Looper.loop();
    }
}
