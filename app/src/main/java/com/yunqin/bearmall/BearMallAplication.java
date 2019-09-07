package com.yunqin.bearmall;


import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.iBookStar.views.YmConfig;
import com.mob.MobApplication;
import com.mob.MobSDK;
import com.newversions.tbk.utils.HomeListener;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Address;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.util.FilePutGetUtils;
import com.yunqin.bearmall.util.RudenessScreenHelper;

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


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
                isFirst=false;
            }

            @Override
            public void longHome() {
                Log.d("app", "longHome");
                isFirst=false;
            }

            @Override
            public void home() {
                Log.d("app", "home");
                isFirst=false;
            }
        });
        mHomeListen.startListen();
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
