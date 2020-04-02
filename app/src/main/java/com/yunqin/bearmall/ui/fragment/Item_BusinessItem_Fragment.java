package com.yunqin.bearmall.ui.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BusinessAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ItemBusinessBean;
import com.yunqin.bearmall.util.PopUtil;
import com.yunqin.bearmall.widget.DownLoadImage;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import permison.PermissonUtil;
import permison.listener.PermissionListener;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2020/3/30
 */
public class Item_BusinessItem_Fragment extends BaseFragment {

    @BindView(R.id.item_bu_recycle)
    RecyclerView mItemBuRecycle;
    @BindView(R.id.item_bu_refreshLayout)
    TwinklingRefreshLayout mItemBuRefreshLayout;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    private String categoryid;
    private int page = 1;
    private BusinessAdapter businessAdapter;
    private PopUtil instance;
    private int refresh = 1;

    public static Item_BusinessItem_Fragment getInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("CATEGORYID", categoryId);
        Item_BusinessItem_Fragment fragment = new Item_BusinessItem_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_businessitem_item;
    }

    @Override
    public void init() {

        instance = PopUtil.getInstance();
        instance.setContext(getActivity());

        categoryid = getArguments().getString("CATEGORYID");

        mItemBuRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        mItemBuRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                refresh = 1;
                getBusinessProduct();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                refresh = 2;
                getBusinessProduct();
            }
        });

        mItemBuRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        businessAdapter = new BusinessAdapter(getActivity());
        mItemBuRecycle.setAdapter(businessAdapter);


        getBusinessProduct();

        mNulldata.setVisibility(View.VISIBLE);

        businessAdapter.setOnClickShare(new BusinessAdapter.onClickShare() {
            @Override
            public void share(String[] strings, String title, int id, int i) {
                Log.e("businessAdapter", "share: ");
                clickshare(strings, title, i);
                BusinessShare(id);
            }
        });

    }

    private void clickshare(String[] strings, String title, int i) {
        View popView = instance.getPopView(R.layout.popup_business_share, 1);
        popView.findViewById(R.id.clear_bus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instance.dismissPopupWindow();
            }
        });
        popView.findViewById(R.id.wx_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final IWXAPI api1 = WXAPIFactory.createWXAPI(getActivity(), null);
                api1.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api1.isWXAppInstalled()) {
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, title));
                    shareNormal(Wechat.NAME, strings);
                    instance.dismissPopupWindow();
                } else {
                    Toast.makeText(getActivity(), "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        popView.findViewById(R.id.moments_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    PermissonUtil.checkPermission(getActivity(), new PermissionListener() {
                        @Override
                        public void havePermission() {
                            ClipboardManager clipboardManager =
                                    (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, title));
                            downBusiness(strings, 1);
                            showLoading();
                            instance.dismissPopupWindow();
                        }

                        @Override
                        public void requestPermissionFail() {
                            showToast("缺少必要权限");
                        }
                    }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                    Item_BusinessItem_Fragment.this.instance.dismissPopupWindow();
                } else {
                    Toast.makeText(getActivity(), "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        popView.findViewById(R.id.qq_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isQQClientAvailable(getActivity())) {
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, title));
                    shareNormal(QQ.NAME, strings);
                    instance.dismissPopupWindow();
                } else {
                    Toast.makeText(getActivity(), "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        popView.findViewById(R.id.qq_moments_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isQQClientAvailable(getActivity())) {
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, title));
                    if (i == 0) {
                        shareQQ(QZone.NAME, strings);
                    } else {
                        shareQQImage(QZone.NAME, strings);
                    }
                    instance.dismissPopupWindow();
                } else {
                    Toast.makeText(getActivity(), "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        popView.findViewById(R.id.dwon_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissonUtil.checkPermission(getActivity(), new PermissionListener() {
                    @Override
                    public void havePermission() {
                        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, title));
                        downBusiness(strings, 2);
                        showLoading();
                        instance.dismissPopupWindow();
                    }

                    @Override
                    public void requestPermissionFail() {
                        showToast("缺少必要权限");
                    }
                }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        });
    }

    private void downBusiness(String[] strings, int i) {
        DownLoadImage dinstance = DownLoadImage.getInstance();
        dinstance.setContext(getActivity());
        dinstance.DownImageLength(strings);
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
                hiddenLoadingView();
                if (i == 1) {
                    View popView1 = instance.getPopView(R.layout.popup_business_dwon, 0);
                    popView1.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            instance.dismissPopupWindow();
                        }
                    });
                    popView1.findViewById(R.id.openwx).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(cmp);
                            startActivity(intent);
                            instance.dismissPopupWindow();
                        }
                    });
                } else {
                    showToast("已自动复制文案，图片已保存至相册");
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

    //普通的分享
    public static void shareQQ(String name, String[] strings) {//name 分享到那个平台
        HashMap<String, Object> optionMap = new HashMap<>();
        optionMap.put("Id", "5");
        optionMap.put("SortId", "5");
        optionMap.put("BypassApproval", true);
        optionMap.put("Enable", true);
        ShareSDK.setPlatformDevInfo(name, optionMap);

        Platform platform = ShareSDK.getPlatform(name);//获取平台对象
        Platform.ShareParams shareParams = new Platform.ShareParams();//分享的参数
        shareParams.setShareType(Platform.SHARE_IMAGE);//分享类型
        shareParams.setImageArray(strings);
        platform.share(shareParams);
    }

    public static void shareQQImage(String name, String[] strings) {//name 分享到那个平台
        Platform platform = ShareSDK.getPlatform(name);//获取平台对象
        Platform.ShareParams shareParams = new Platform.ShareParams();//分享的参数
        shareParams.setShareType(Platform.SHARE_IMAGE);//分享类型
        shareParams.setImageUrl(strings[0]);
        platform.share(shareParams);
    }

    //普通的分享
    public static void shareNormal(String name, String[] strings) {//name 分享到那个平台

        HashMap<String, Object> optionMap = new HashMap<>();
        optionMap.put("Id", "5");
        optionMap.put("SortId", "5");
        optionMap.put("BypassApproval", true);
        optionMap.put("Enable", true);
        ShareSDK.setPlatformDevInfo(name, optionMap);

        Platform platform = ShareSDK.getPlatform(name);//获取平台对象
        Platform.ShareParams shareParams = new Platform.ShareParams();//分享的参数
        shareParams.setImageArray(strings);
        shareParams.setShareType(Platform.SHARE_IMAGE);//分享类型
        platform.share(shareParams);
    }


    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void getBusinessProduct() {
        HashMap<String, String> map = new HashMap<>();
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData()
                != null && BearMallAplication.getInstance().getUser().getData().getToken()
                != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        map.put("categoryId", categoryid);
        map.put("itemType", "1");
        map.put("page", page + "");
        map.put("pageSize", "10");
        Log.e("ItemBusinessBean", categoryid);
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getBusinessProduct(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("ItemBusinessBean", data);
                ItemBusinessBean itemBusinessBean = new Gson().fromJson(data, ItemBusinessBean.class);
                if (itemBusinessBean != null && itemBusinessBean.getData() != null && itemBusinessBean.getData().size() > 0) {
                    if (refresh == 1) {
                        businessAdapter.clearData();
                    }
                    businessAdapter.addData(itemBusinessBean.getData());
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

    public void BusinessShare(int id) {
        HashMap<String, String> map = new HashMap<>();
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData()
                != null && BearMallAplication.getInstance().getUser().getData().getToken()
                != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        map.put("type", "0");
        map.put("id", id + "");
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).BusinessShare(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
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
        });
    }

}
