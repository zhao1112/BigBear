package com.yunqin.bearmall.update;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.inter.ApkDownloadListener;
import com.yunqin.bearmall.inter.CheckVersionCallBack;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author Master
 * @create 2018/8/7 10:29
 */
public class CheckForUpdateHelper {

    private Activity activity;
    private static final int DEFAULT_TIMEOUT = 10;
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bear.apk";

    private CheckVersionCallBack checkVersionCallBack;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public CheckForUpdateHelper() {
    }

    public CheckForUpdateHelper(CheckVersionCallBack checkVersionCallBack) {
        this.checkVersionCallBack = checkVersionCallBack;
    }

    public void checkForUpdate(Activity activity, int  isShowDialog) {
        this.activity = activity;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("type", "0");
        mHashMap.put("appVersion", CommonUtils.getVersionCode(activity) + "");


        RetrofitApi.request(activity, RetrofitApi.createApi(Api.class).checkForUpdate(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                JSONObject jsonObject = new JSONObject(data);
                int code = jsonObject.optInt("code");
                if (code == 1) {
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    int isNeedUpdate = jsonObject1.optInt("isNeedUpdate");//是否需要更新 0 否 1 是
                    int isHidePointFunc = jsonObject1.optInt("isHidePointFunc");//是否隐藏积分功能 0 否 1 是

                    SharedPreferencesHelper.put(activity,"IsHidePointFunc",isHidePointFunc);

                    if(isShowDialog == 0){
                        return;
                    }

                    if (isNeedUpdate == 1) {
                        JSONObject jsonObject2 = jsonObject1.optJSONObject("applicationversion");
                        int isForce = jsonObject2.optInt("isForce");
                        JSONArray jsonArray = jsonObject2.optJSONArray("memo");
                        List<String> lists = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            lists.add(jsonArray.getString(i));
                        }
                        String downLoadUrl = jsonObject2.optString("downloadUrl");
                        showUpdateDialog(activity, downLoadUrl, lists, isForce == 1);
                        if (checkVersionCallBack != null) {
                            checkVersionCallBack.success();
                        }
                    } else {
                        if (checkVersionCallBack != null) {
                            checkVersionCallBack.isTheNewVersion();
                        }
                    }
                }
            }

            @Override
            public void onNotNetWork() {
                if (checkVersionCallBack != null) {
                    checkVersionCallBack.isTheNewVersion();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (checkVersionCallBack != null) {
                    checkVersionCallBack.onerror();
                }
            }
        });
    }


    private ProgressBar iProgressBar;
    private Dialog updateDialog;

    private void showUpdateDialog(Activity activity, String downUrl, List<String> list, boolean compelUpdate) {

        updateDialog = new Dialog(activity, R.style.BottomDialog);
        View contentView = LayoutInflater.from(activity).inflate(R.layout.dialog_update, null);

        Button cancel = contentView.findViewById(R.id.cancel);

        if (compelUpdate) {
            cancel.setVisibility(View.INVISIBLE);
        }

        Button update = contentView.findViewById(R.id.update);
        LinearLayout button_group = contentView.findViewById(R.id.button_group);

        cancel.setOnClickListener(v -> updateDialog.dismiss());

        iProgressBar = contentView.findViewById(R.id.progress_bar);

        update.setOnClickListener(v -> {


            // TODO 判断是否有SD权限


            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                iProgressBar.setVisibility(View.VISIBLE);
                button_group.setVisibility(View.GONE);
//                downLoadApk("https://dl.google.com/dl/android/studio/install/3.1.4.0/android-studio-ide-173.4907809-windows.exe", apkDownloadListener);
                downLoadApk(downUrl, apkDownloadListener);
            }
        });


        ListView listView = contentView.findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, R.layout.update_layout, 0, list);
        listView.setAdapter(adapter);
        updateDialog.setContentView(contentView);

        updateDialog.setCanceledOnTouchOutside(false);
        updateDialog.setCancelable(false);
        updateDialog.getWindow().setGravity(Gravity.CENTER);
        updateDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        updateDialog.show();
    }


    private void downLoadApk(String apkUrl, ApkDownloadListener apkDownloadListener) {


        ApkDownloadInterceptor apkDownloadInterceptor = new ApkDownloadInterceptor(apkDownloadListener);

        OkHttpClient httpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(apkDownloadInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dl.google.com/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofit.create(DownloadService.class)
                .downloadApk(apkUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(responseBody -> responseBody.byteStream())
                .observeOn(Schedulers.computation())
                .doOnNext(inputStream -> writeFile(inputStream, filePath))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subject<InputStream>() {
                    @Override
                    public boolean hasObservers() {
                        return false;
                    }

                    @Override
                    public boolean hasThrowable() {
                        return false;
                    }

                    @Override
                    public boolean hasComplete() {
                        return false;
                    }

                    @Override
                    public Throwable getThrowable() {
                        return null;
                    }

                    @Override
                    protected void subscribeActual(Observer<? super InputStream> observer) {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        apkDownloadListener.onFinishDownload();

                    }

                    @Override
                    public void onError(Throwable e) {
                        apkDownloadListener.onFail(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param filePath
     */
    private void writeFile(InputStream inputString, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024 * 8];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();

        } catch (FileNotFoundException e) {
            apkDownloadListener.onFail(e);
        } catch (IOException e) {
            apkDownloadListener.onFail(e);
        }
    }


    private ApkDownloadListener apkDownloadListener = new ApkDownloadListener() {

        @Override
        public void onStartDownload() {

        }

        @Override
        public void onProgress(int progress) {
            if (iProgressBar != null) {
                iProgressBar.setProgress(progress);
            }
        }

        @Override
        public void onFinishDownload() {
            try {
                updateDialog.dismiss();
                File apkFile = new File(filePath);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(activity, "com.bearmall.app", apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                }
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFail(Throwable throwable) {

        }
    };


}
