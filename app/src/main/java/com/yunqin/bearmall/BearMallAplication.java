package com.yunqin.bearmall;


import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.iBookStar.views.YmConfig;
import com.lcodecore.tkrefreshlayout.utils.LogUtil;
import com.mob.MobApplication;
import com.mob.MobSDK;
import com.newversions.tbk.utils.HomeListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLogUtils;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Address;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.util.CommonUtil;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ContextHelper;
import com.yunqin.bearmall.util.DeviceInfo;
import com.yunqin.bearmall.util.DeviceInfoHelper;
import com.yunqin.bearmall.util.DeviceInfoInit;
import com.yunqin.bearmall.util.FilePutGetUtils;
import com.yunqin.bearmall.util.RudenessScreenHelper;

import java.net.URLEncoder;
import java.util.HashMap;

import cn.example.lamor.AppContextLike;

/**
 * @author Master
 */
public class
BearMallAplication extends MobApplication {

    private static BearMallAplication instance;
    private static RequestOptions options;
    public static boolean isFirst = true;

    public static Address mAddress;
    private ActivityStack mActivityStack = new ActivityStack();
    private HomeListener mHomeListen;

    private String _channel = "official";


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ContextHelper.init(this);
        initUM();
        MultiDex.install(this);
        new RudenessScreenHelper(this, 750).activate();

        AppContextLike.getDefault().onCreate(this);
        // 添加异常捕获
//        CrashHandler.getInstance().init(this);

//        FoxSDK.init(this);
        //阅读初始化
        YmConfig.initNovel(this, "8148");
//阿里百川初始化
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });

        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String PUSH_APPID = applicationInfo.metaData.getString("PUSH_APPID");
            String PUSH_APPKEY = applicationInfo.metaData.getString("PUSH_APPKEY");
            String PUSH_APPSECRET = applicationInfo.metaData.getString("PUSH_APPSECRET");

            Log.e("TAG-APPLICATION", "url--> " + BuildConfig.BASE_URL);
            Log.e("TAG-APPLICATION", "PUSH_APPID--> " + PUSH_APPID);
            Log.e("TAG-APPLICATION", "PUSH_APPKEY--> " + PUSH_APPKEY);
            Log.e("TAG-APPLICATION", "PUSH_APPSECRET--> " + PUSH_APPSECRET);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        registerActivityLifecycleCallbacks(mActivityStack);

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getAreaList(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    mAddress = new Gson().fromJson(data, Address.class);
                } catch (JsonSyntaxException e) {
                    mAddress = null;
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                mAddress = null;
            }
        });

        MobSDK.init(this);
        mHomeListen = new HomeListener(this);
        mHomeListen.setInterface(new HomeListener.KeyFun() {
            @Override
            public void recent() {
                Log.d("app", "recent");
                isFirst = false;
            }

            @Override
            public void longHome() {
                Log.d("app", "longHome");
                isFirst = false;
            }

            @Override
            public void home() {
                Log.d("app", "home");
                isFirst = false;
            }
        });
        mHomeListen.startListen();
    }

    public HashMap<String, String> getDevParams() {
        DeviceInfoInit deviceInfoInit = new DeviceInfoInit(this, null);
        DeviceInfo deviceInfo = deviceInfoInit.getmDeviceInfo();
        final DeviceInfoHelper toolkit = DeviceInfoHelper.defaultHelper();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("deviceName", deviceInfo.getProductModel());
        map.put("manufacturer", toolkit.getManufacturer());
        map.put("brand", deviceInfo.getProductBrand());
        map.put("resolution", deviceInfo.getResolution());
        map.put("osVersion", String.valueOf(deviceInfo.getOsVersion()));
        map.put("networkType", CommonUtil.getNetworkType(this));
        map.put("mac", deviceInfo.getMac());
        map.put("cid", _channel);
        map.put("version", "V" + toolkit.getAppVersionName() + "." + String.valueOf(toolkit.getAppVersionCode()));
        map.put("deviceModel", URLEncoder.encode(toolkit.getMobileType()));
        map.put("aid", CommonUtil.getAid());
        map.put("idfa", "");
        if (DeviceConfig.getImei(this) != null) {
            map.put("imei", DeviceConfig.getImei(this));
        } else {
            map.put("imei", "");
        }
        map.put("pkgName", CommonUtil.getPackageName(this));

//        Log.i("getDevParams", "deviceName: " + deviceInfo.getProductModel());
//        Log.i("getDevParams", "manufacturer: " + toolkit.getManufacturer());
//        Log.i("getDevParams", "brand: " + deviceInfo.getProductBrand());
//        Log.i("getDevParams", "resolution: " + deviceInfo.getResolution());
//        Log.i("getDevParams", "osVersion: " + String.valueOf(deviceInfo.getOsVersion()));
//        Log.i("getDevParams", "networkType: " +  CommonUtil.getNetworkType(this));
//        Log.i("getDevParams", "mac: " + deviceInfo.getMac());
//        Log.i("getDevParams", "cid: " + _channel);
//        Log.i("getDevParams", "version: " + "V" + toolkit.getAppVersionName() + "." + String.valueOf(toolkit.getAppVersionCode()));
//        Log.i("getDevParams", "deviceModel: " + URLEncoder.encode(toolkit.getMobileType()));
//        Log.i("getDevParams", "aid: " + CommonUtil.getAid());
//        Log.i("getDevParams", "imei: " +  DeviceConfig.getImei(this));

        return map;
    }

    public static BearMallAplication get() {
        return instance;
    }

    private void initUM() {
        /**
         * 初始化common库
         * 参数1:上下文，必须的参数，不能为空
         * 参数2:友盟 app key，非必须参数，如果Manifest文件中已配置app key，该参数可以传空，则使用Manifest中配置的app key，否则该参数必须传入
         * 参数3:友盟 channel，非必须参数，如果Manifest文件中已配置channel，该参数可以传空，则使用Manifest中配置的channel，否则该参数必须传入，channel命名请详见channel渠道命名规范
         * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
         */
        //如果AndroidManifest.xml清单配置中没有设置appkey和channel，则可以在这里设置
        //        UMConfigure.init(this, "58edcfeb310c93091c000be2", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
        //        "1fe6a20054bcef865eeb0991ee84525b");
        try {
            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ApplicationInfo appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            _channel = appInfo.metaData.getString("UMENG_CHANNEL");
            LogUtil.i(" channel: " + _channel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (BuildConfig.DEBUG) {
            /**
             * 设置组件化的Log开关
             * 参数: boolean 默认为false，如需查看LOG设置为true
             */
            UMConfigure.setLogEnabled(true);
        }
    }

    @Override
    public void onTerminate() {
        mHomeListen.stopListen();
        super.onTerminate();
    }

    public static BearMallAplication getInstance() {
        return instance;
    }


    public static RequestOptions getOptions(int resouseID) {
        if (options == null) {
            options = new RequestOptions()
                    //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                    .centerCrop()
                    .dontAnimate()
                    //跳过内存缓存
                    .skipMemoryCache(false)
                    //缓存所有版本的图像
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        options.placeholder(resouseID).error(resouseID);
        return options;
    }

    private UserInfo user;

    public void setNullUser() {
        user = null;
        if (FilePutGetUtils.exists(this.getApplicationContext(), "app_user.json")) {
            FilePutGetUtils.deleteFile(this.getApplicationContext(), "app_user.json");
        }
    }

    public synchronized UserInfo getUser() {
        if (user == null) {
            String content = FilePutGetUtils.readFile(this.getApplicationContext(), "app_user.json");
            try {
                if (content != null && !content.equals("")) {
                    user = new Gson().fromJson(content, UserInfo.class);
                }
            } catch (Exception e) {
            }
        }
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
        String jsonObject = new Gson().toJson(user);
        FilePutGetUtils.writeFile(getApplicationContext(), "app_user.json", jsonObject);
    }

    public static Address getAddress() {
        return mAddress;
    }


    public ActivityStack getActivityStack() {
        return mActivityStack;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        new RudenessScreenHelper(this, 750).activate();
    }
}
