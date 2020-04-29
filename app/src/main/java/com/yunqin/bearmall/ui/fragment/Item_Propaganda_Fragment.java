package com.yunqin.bearmall.ui.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.CopyTextUtil;
import com.bbcoupon.util.JurisdictionUtil;
import com.bbcoupon.util.ShareUtils;
import com.bbcoupon.util.WindowUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.video.videoplayer.player.PlayerActivity;
import com.videodown.DownLoadDialog;
import com.videodown.http.DownLoad;
import com.videodown.http.bean.DownBean;
import com.videodown.http.i.OnDownloadListener;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.PropagandaAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.PropagandaBean;
import com.yunqin.bearmall.ui.activity.BCMessageActivity;
import com.yunqin.bearmall.util.NetUtils;
import com.yunqin.bearmall.util.PopUtil;
import com.yunqin.bearmall.util.PopUtil2;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.video.CustomVideo;
import com.yunqin.bearmall.widget.DownLoadImage;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import permison.PermissonUtil;
import permison.listener.PermissionListener;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2020/4/1
 */
public class Item_Propaganda_Fragment extends BaseFragment implements RequestContract.RequestView, View.OnClickListener {


    @BindView(R.id.item_bu_recycle)
    RecyclerView mItemBuRecycle;
    @BindView(R.id.item_bu_refreshLayout)
    TwinklingRefreshLayout mItemBuRefreshLayout;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;

    private String categoryid;
    private int page = 1;
    private PropagandaAdapter mPropagandaAdapter;
    private int refresh = 2;
    private String path;
    private RequestPresenter requestPresenter;
    private Map<String, String> map;
    private String copyContent;
    private String[] imageLis;

    public static Item_Propaganda_Fragment getInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("CATEGORYID", categoryId);
        Item_Propaganda_Fragment fragment = new Item_Propaganda_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_businessitem_item;
    }

    @Override
    public void init() {

        requestPresenter = new RequestPresenter();
        requestPresenter.setRelation(this);

        categoryid = getArguments().getString("CATEGORYID");

        mItemBuRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        mItemBuRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                refresh = 1;
                getBusinessMaterial();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                refresh = 2;
                getBusinessMaterial();
            }
        });

        mItemBuRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPropagandaAdapter = new PropagandaAdapter(getActivity());
        mItemBuRecycle.setAdapter(mPropagandaAdapter);
        //分享图片
        mPropagandaAdapter.setOnClickShare(new PropagandaAdapter.onClickShare() {
            @Override
            public void share(String[] strings, int goodesId, int i, String title) {
                copyContent = title;
                imageLis = strings;
                if (imageLis != null && imageLis.length > 0) {
                    startSharing(goodesId);
                }
                //增加数量
                BusinessShare(goodesId);
            }

            @Override
            public void shareCrop() {
                showToast("复制成功", Gravity.CENTER);
            }
        });
        //播放视频
        mPropagandaAdapter.setOnVideoClick(new PropagandaAdapter.onVideoClick() {
            @Override
            public void videoUrl(String url) {
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("VODEO_URL", url);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        //分享视频
        mPropagandaAdapter.setOnShearVideo(new PropagandaAdapter.onShearVideo() {
            @Override
            public void videoUrl(String url, String image, String title, int goodesId) {
//                shearVideo(url, image, title);
                //增加数量
                BusinessShare(goodesId);
            }
        });

        mNulldata.setVisibility(View.VISIBLE);
        getBusinessMaterial();
    }

    private void startSharing(int goodesId) {
        map = new HashMap<>();
        map.clear();
        map.put("type", "1");
        map.put("content", goodesId + "");
        View show = WindowUtils.ShowBrightness(getActivity(), R.layout.popup_business_share, 1);
        show.findViewById(R.id.clear_bus).setOnClickListener(this);
        show.findViewById(R.id.wx_share).setOnClickListener(this);
        show.findViewById(R.id.moments_share).setOnClickListener(this);
        show.findViewById(R.id.qq_share).setOnClickListener(this);
        show.findViewById(R.id.qq_moments_share).setOnClickListener(this);
        show.findViewById(R.id.dwon_share).setOnClickListener(this);
    }

//    public void shearVideo(String videoUrl, String image, String title) {
//        CopyTextUtil.CopyText(getActivity(), title);
//        View popView = instance.getPopView(R.layout.popup_business_share, 1);
//        popView.findViewById(R.id.clear_bus).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                instance.dismissPopupWindow();
//            }
//        });
//        popView.findViewById(R.id.wx_share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                download(videoUrl, 1, 1);
//                instance.dismissPopupWindow();
//            }
//        });
//        popView.findViewById(R.id.moments_share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                download(videoUrl, 1, 1);
//                instance.dismissPopupWindow();
//            }
//        });
//        popView.findViewById(R.id.qq_share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                download(videoUrl, 1, 2);
//                instance.dismissPopupWindow();
//            }
//        });
//        popView.findViewById(R.id.qq_moments_share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                download(videoUrl, 1, 2);
//                instance.dismissPopupWindow();
//            }
//        });
//        popView.findViewById(R.id.dwon_share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PermissonUtil.checkPermission(getActivity(), new PermissionListener() {
//                    @Override
//                    public void havePermission() {
//                        download(videoUrl, 1, 3);
//                    }
//
//                    @Override
//                    public void requestPermissionFail() {
//                        showToast("缺少必要权限");
//                    }
//                }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
//            }
//        });
//    }

    @Override
    public void onClick(View view) {
        CopyTextUtil.CopyText(getActivity(), copyContent);
        switch (view.getId()) {
            case R.id.clear_bus://关闭
                WindowUtils.dismissBrightness(getActivity());
                break;
            case R.id.wx_share://微信分享
                JurisdictionUtil.Jurisdiction(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    if (ShareUtils.isWXClientAvailable(getActivity())) {
                        ShareUtils.MultiGraphShare(Wechat.NAME, imageLis);
                        WindowUtils.dismissBrightness(getActivity());
                        showToast("文案已复制剪切板", Gravity.CENTER);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                requestPresenter.onCandySharing(getActivity(), map);
                            }
                        },3000);
                    } else {
                        Toast.makeText(getActivity(), "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.moments_share://朋友圈分享
                JurisdictionUtil.Jurisdiction(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    downBusiness(imageLis, 1, 1);
                    WindowUtils.dismissBrightness(getActivity());
                    WindowUtils.Show(getActivity(), R.layout.bus_dialog_image, 1);
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.qq_share://QQ分享
                JurisdictionUtil.Jurisdiction(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    downBusiness(imageLis, 1, 4);
                    WindowUtils.dismissBrightness(getActivity());
                    WindowUtils.Show(getActivity(), R.layout.bus_dialog_image, 1);
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.qq_moments_share://QQ空间分享
                JurisdictionUtil.Jurisdiction(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    downBusiness(imageLis, 1, 2);
                    WindowUtils.dismissBrightness(getActivity());
                    WindowUtils.Show(getActivity(), R.layout.bus_dialog_image, 1);
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.dwon_share://下载图片
                JurisdictionUtil.Jurisdiction(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    downBusiness(imageLis, 2, 3);
                    WindowUtils.dismissBrightness(getActivity());
                    WindowUtils.Show(getActivity(), R.layout.bus_dialog_image, 1);
                    return;
                }
                showToast("缺少必要权限");
                break;
        }
    }

    //下载图片
    private void downBusiness(String[] strings, int shareId, int contenId) {
        DownLoadImage dinstance = DownLoadImage.getInstance();
        dinstance.setContext(getActivity());
        dinstance.DownLoadImag(strings);
        dinstance.setOnDownLoadImage(new DownLoadImage.onDownLoadImage() {
            @Override
            public void progressMax(int value) {

            }

            @Override
            public void progressValue(int value, int contentLen) {

            }

            @Override
            public void progerssVisibility() {
                WindowUtils.dismiss();
                if (shareId == 1) {
                    View show = WindowUtils.Show(getActivity(), R.layout.popup_business_dwon, 1);
                    TextView textView = show.findViewById(R.id.openwx);
                    TextView title = show.findViewById(R.id.title);
                    if (contenId == 1) {
                        textView.setText("打开朋友圈");
                        title.setText("去朋友圈分享");
                    }
                    if (contenId == 2) {
                        textView.setText("打开QQ空间");
                        title.setText("去QQ空间分享");
                    }
                    if (contenId == 4) {
                        textView.setText("打开QQ");
                        title.setText("去QQ分享");
                    }
                    show.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPresenter.onCandySharing(getActivity(), map);
                            WindowUtils.dismiss();
                        }
                    });
                    show.findViewById(R.id.openwx).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (contenId == 1) {
                                if (ShareUtils.isWXClientAvailable(getActivity())) {
                                    Intent intent = new Intent();
                                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                    intent.setAction(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setComponent(cmp);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (contenId == 2 || contenId == 4) {
                                if (ShareUtils.isQQClientAvailable(getActivity())) {
                                    Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                                }
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    requestPresenter.onCandySharing(getActivity(), map);
                                }
                            }, 3000);
                            WindowUtils.dismiss();
                        }
                    });
                } else {
                    showToast("已自动复制文案，图片已保存至相册");
                    requestPresenter.onCandySharing(getActivity(), map);
                }
            }

            @Override
            public void downLiadImage(int imageLength) {

            }

            @Override
            public void downLoadValue(int value) {

            }
        });
    }

    //获取页面数据
    private void getBusinessMaterial() {
        HashMap<String, String> map = new HashMap<>();
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData()
                != null && BearMallAplication.getInstance().getUser().getData().getToken()
                != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        map.put("categoryId", categoryid);
        map.put("page", page + "");
        map.put("pageSize", "10");
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getBusinessMaterial(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                PropagandaBean propagandaBean = new Gson().fromJson(data, PropagandaBean.class);
                if (propagandaBean != null && propagandaBean.getData() != null && propagandaBean.getData().size() > 0) {
                    if (refresh == 1) {
                        mPropagandaAdapter.clearData();
                    }
                    mPropagandaAdapter.addData(propagandaBean.getData());
                    mNulldata.setVisibility(View.GONE);
                }
                mItemBuRefreshLayout.finishRefreshing();
                mItemBuRefreshLayout.finishLoadmore();
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                mItemBuRefreshLayout.finishRefreshing();
                mItemBuRefreshLayout.finishLoadmore();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                mItemBuRefreshLayout.finishRefreshing();
                mItemBuRefreshLayout.finishLoadmore();
            }
        });
    }

    //分享视频
    public void shearVideo(String plat, String videoUrl, String title, String subTitle, String imageUrl) {
        Platform.ShareParams wt1 = new Platform.ShareParams();
        wt1.setTitle(title);
        wt1.setShareType(Platform.SHARE_VIDEO);
        wt1.setText(subTitle);
        wt1.setImageUrl("https://t3.ftcdn.net/jpg/02/01/25/00/240_F_201250053_xMFe9Hax6w01gOiinRLEPX0Wt1zGCzYz.jpg");
        //  wt1.setUrl("http://m.93lj.com/sharelink/");
        //  wt1.setUrl("http://f1.webshare.mob.com/dvideo/demovideos.mp4");
        wt1.setUrl("https://cloud.video.taobao.com//play/u/1644241023/p/1/e/6/t/1/257445029587.mp4");

        Platform wechat = ShareSDK.getPlatform(plat);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                Log.d("ShareSDK", "onError ---->  分享失败" + arg2.toString());
                Log.d("ShareSDK", "ThreadID -----> : " + Thread.currentThread().getId());
            }

            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
                Log.d("ShareSDK", "onError ---->  分享cg" + arg2.toString());
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
                Log.d("ShareSDK", "onError ---->  分享xq");
            }
        });
        wechat.share(wt1);
    }

    //下载视频
    private synchronized void download(String video_url, int type, int opentype) {
        if (TextUtils.isEmpty(video_url)) {
            return;
        }

        DownBean downBean = new DownBean();
        downBean.setUrl(video_url);
        downBean.setVersion("1");
        String name = video_url.substring(video_url.lastIndexOf("/"), video_url.length());
        downBean.setName(name);

        DownLoadDialog downLoadDialog = new DownLoadDialog(getActivity()).builder();
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
                downLoadDialog.dismiss();
                path = o.getPath();
                if (type == 1) {
                    File file = new File(path);
                    ContentResolver localContentResolver = getActivity().getContentResolver();
                    ContentValues localContentValues = getVideoContentValues(getActivity(), file, System.currentTimeMillis());
                    Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri));
                    showToast("视频已保存到相册");
                }

                if (opentype == 1) {
                    if (ShareUtils.isWXClientAvailable(getActivity())) {
                        Intent intent = new Intent();
                        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                        intent.setAction(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setComponent(cmp);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    }
                }

                if (opentype == 2) {
                    if (ShareUtils.isQQClientAvailable(getActivity())) {
                        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(DownBean o) {

            }
        });
        downLoad.start(downBean);

    }

    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }

    //增加分享数量
    public void BusinessShare(int id) {
        HashMap<String, String> map = new HashMap<>();
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData()
                != null && BearMallAplication.getInstance().getUser().getData().getToken()
                != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        map.put("type", "1");
        map.put("id", id + "");
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).BusinessShare(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requestPresenter.setUntying(this);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof RequestInfor) {
            RequestInfor requestInfor = (RequestInfor) data;
            if (requestInfor.getCode() == 1) {
                PopupWindow popupWindow = WindowUtils.timeShowOnly(getActivity(), R.layout.popup_tisp, R.style.TispAnim, 0);
                TextView value_tisp = popupWindow.getContentView().findViewById(R.id.value_tisp);
                value_tisp.setText("分享成功，获得" + requestInfor.getValue() + "个糖果，点击查看详情>>");
                popupWindow.getContentView().findViewById(R.id.top_tisp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().startActivity(new Intent(getActivity(), BCMessageActivity.class));
                        WindowUtils.dismissOnly();
                    }
                });
            }
        }
    }

    @Override
    public void onNotNetWork() {

    }

    @Override
    public void onFail(Throwable e) {

    }

}
