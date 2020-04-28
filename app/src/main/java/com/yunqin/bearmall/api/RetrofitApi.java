package com.yunqin.bearmall.api;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.RefreshToken;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.NetUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Master
 */
public class RetrofitApi {

    private static OkHttpClient mOkHttpClient;


    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory getsslsocket() {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient != null) {
            return mOkHttpClient;
        }

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(CommonUtils.TAG, "请求接口" + message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(new ParameterInterceptor())
                .addNetworkInterceptor(logInterceptor)
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(getsslsocket())
                .followRedirects(true)
                .retryOnConnectionFailure(true)
                .build();
    }

    // 创建网络接口请求实例
    public static <T> T createApi(Class<T> service) {

        Gson gson = new GsonBuilder().setLenient().create();
        GsonConverterFactory mGsonConverterFactory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(getOkHttpClient())
//                .addConverterFactory(mGsonConverterFactory)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }


    // 创建网络接口请求实例
    public static <T> T contenApi(Class<T> service, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    public static void request(Context mContext, Observable<String> observable, final IResponseListener listener) {

        if (!NetUtils.isConnected(mContext)) {
            if (listener != null) {
                listener.onNotNetWork();
            }
            Toast.makeText(mContext, "请检查网络!", Toast.LENGTH_SHORT).show();
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                               @Override
                               public void onError(Throwable e) {
                                   if (listener != null) {
                                       listener.onFail(e);
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }


                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(String data) {
                                   try {
                                       JSONObject jsonObject = new JSONObject(data);
                                       if (jsonObject.optInt("code") == 1 || jsonObject.optInt("code") == 2) {
                                           if (listener != null) {
                                               listener.onSuccess(data);
                                           }
                                       } else if (jsonObject.optInt("code") == -3) {
                                           // TODO 刷新 token
                                           Log.e(CommonUtils.TAG, "Token过期...");
                                           RefreshToken.init(mContext, observable, listener);
                                       } else if (jsonObject.optInt("code") == -2) {
                                           Log.e(CommonUtils.TAG, "請求返回结果导致登录");
                                           LoginActivity.starActivity((Activity) mContext);
                                           BearMallAplication.getInstance().setNullUser();
                                       } else {
                                           if (listener != null) {
                                               listener.onNotNetWork();
                                               listener.onFail(new Exception(data));
                                           }
                                           String msg = jsonObject.optString("msg");
                                           if (!"重复请求".equals(msg) && !TextUtils.isEmpty(msg)) {
                                               Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   } catch (JSONException e) {
                                       listener.onNotNetWork();
                                   }

                               }
                           }
                );
    }

    public static void request2(Context mContext, Observable<String> observable, final IResponseListener listener) {

        if (!NetUtils.isConnected(mContext)) {
            if (listener != null) {
                listener.onNotNetWork();
            }
            Toast.makeText(mContext, "请检查网络!", Toast.LENGTH_SHORT).show();
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                               @Override
                               public void onError(Throwable e) {
                                   listener.onNotNetWork();
                                   if (listener != null) {
                                       listener.onFail(e);
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }


                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(String data) {
                                   try {
                                       JSONObject jsonObject = new JSONObject(data);
                                       if (jsonObject.optInt("code") == 1) {
                                           if (listener != null) {
                                               listener.onSuccess(data);
                                           }
                                       } else if (jsonObject.optInt("code") == -3) {
                                           // TODO 刷新 token
                                           Log.e(CommonUtils.TAG, "刷新Token过期...跳转登陆页面");
                                           LoginActivity.starActivity((Activity) mContext);
                                           BearMallAplication.getInstance().setNullUser();
                                       } else if (jsonObject.optInt("code") == -2) {
                                           if (listener != null) {
                                               listener.onFail(new Exception(data));
                                           }
                                           Log.e(CommonUtils.TAG, "請求返回结果导致登录");
                                           LoginActivity.starActivity((Activity) mContext);
                                           BearMallAplication.getInstance().setNullUser();
                                       } else {
                                           if (listener != null) {
                                               listener.onNotNetWork();
                                               listener.onFail(new Exception(data));
                                           }
                                           String msg = jsonObject.optString("msg");
                                           if (!"重复请求".equals(msg)&& !TextUtils.isEmpty(msg)) {
                                               Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   } catch (JSONException e) {
                                       listener.onNotNetWork();
                                   }

                               }
                           }
                );
    }

    public static void request3(Context mContext, Observable<String> observable, final IResponseListener listener) {

        if (!NetUtils.isConnected(mContext)) {
            if (listener != null) {
                listener.onNotNetWork();
            }
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                               @Override
                               public void onError(Throwable e) {
                                   if (listener != null) {
                                       listener.onFail(e);
                                   }
                               }

                               @Override
                               public void onComplete() {
                               }

                               @Override
                               public void onSubscribe(Disposable disposable) {
                               }

                               @Override
                               public void onNext(String data) {
                                   try {
                                       if (listener != null) {
                                           listener.onSuccess(data);
                                       }
                                   } catch (Exception e) {
                                       if (listener != null) {
                                           listener.onFail(e);
                                       }
                                   }
                               }
                           }
                );
    }

    public static void request4(Context mContext, Observable<String> observable, final IResponseListener listener) {

        if (!NetUtils.isConnected(mContext)) {
            if (listener != null) {
                listener.onNotNetWork();
            }
            Toast.makeText(mContext, "请检查网络!", Toast.LENGTH_SHORT).show();
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                               @Override
                               public void onError(Throwable e) {
                                   listener.onNotNetWork();
                                   if (listener != null) {
                                       listener.onFail(e);
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }


                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(String data) {
                                   try {
                                       JSONObject jsonObject = new JSONObject(data);
                                       if (jsonObject.optInt("code") == 1) {
                                           if (listener != null) {
                                               listener.onSuccess(data);
                                           }
                                       } else if (jsonObject.optInt("code") == -3) {
                                           // TODO 刷新 token
                                           Log.e(CommonUtils.TAG, "Token过期...");
                                           RefreshToken.init(mContext, observable, listener);
                                       } else if (jsonObject.optInt("code") == -2) {
                                           Log.e(CommonUtils.TAG, "請求返回结果导致登录");
                                           LoginActivity.starActivity((Activity) mContext);
                                           BearMallAplication.getInstance().setNullUser();
                                       } else {
                                           if (listener != null) {
                                               listener.onNotNetWork();
                                               listener.onFail(new Exception(data));
                                           }
                                       }
                                   } catch (JSONException e) {
                                       listener.onNotNetWork();
                                   }
                               }
                           }
                );
    }

    public static void request5(Context mContext, Observable<String> observable, final IResponseListener listener) {

        if (!NetUtils.isConnected(mContext)) {
            if (listener != null) {
                listener.onNotNetWork();
            }
            Toast.makeText(mContext, "请检查网络!", Toast.LENGTH_SHORT).show();
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                               @Override
                               public void onError(Throwable e) {
                                   listener.onNotNetWork();
                                   if (listener != null) {
                                       listener.onFail(e);
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }


                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(String data) {
                                   try {
                                       listener.onSuccess(data);
                                   } catch (JSONException e) {
                                       listener.onNotNetWork();
                                   }

                               }
                           }
                );
    }

    public static void requestImageCode(Context mContext, Observable<ResponseBody> observable, final ImageCodeResponseListener listener) {
        if (!NetUtils.isConnected(mContext)) {
            // TODO 无网络链接
            Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(ResponseBody responseBody) {
                                   try {
                                       if (listener != null) {
                                           listener.onSuccess(BitmapFactory.decodeStream(responseBody.byteStream()));
                                       }
                                   } catch (Exception e) {
                                   }

                               }

                               @Override
                               public void onError(Throwable e) {
                                   Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
                                   if (listener != null) {
                                       listener.onFail(e);
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }


    public interface IResponseListener {
        void onSuccess(String data) throws JSONException;

        void onNotNetWork();

        void onFail(Throwable e);
    }

    public interface ImageCodeResponseListener {
        void onSuccess(Bitmap bitmap);

        void onFail(Throwable e);
    }

}
