package com.yunqin.bearmall.ui.fragment;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import com.newversions.tbk.entity.ShareGoodsEntity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BusinessAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ItemBusinessBean;
import com.yunqin.bearmall.ui.activity.BCMessageActivity;
import com.yunqin.bearmall.util.AuntTao;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DownLoadImage;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2020/3/30
 */
public class Item_BusinessItem_Fragment extends BaseFragment implements RequestContract.RequestView, View.OnClickListener {

    @BindView(R.id.item_bu_recycle)
    RecyclerView mItemBuRecycle;
    @BindView(R.id.item_bu_refreshLayout)
    TwinklingRefreshLayout mItemBuRefreshLayout;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    @BindView(R.id.text_tip)
    TextView mTextTip;

    private String categoryid;
    private int page = 1;
    private BusinessAdapter businessAdapter;
    private int refresh = 2;
    private RequestPresenter requestPresenter;
    private String copyContent;
    private String[] imageLis;
    private Map<String, String> map;

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

        requestPresenter = new RequestPresenter();
        requestPresenter.setRelation(this);

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

        mTextTip.setText("数据加载中");
        mNulldata.setVisibility(View.VISIBLE);

        businessAdapter.setOnClickShare(new BusinessAdapter.onClickShare() {
            @Override
            public void share(String[] strings, String title, int goodesId, int position) {
                copyContent = title;//复制文案
                imageLis = strings;//分享图片集合
                if (imageLis != null && imageLis.length > 0) {
                    startSharing(goodesId);
                }
                //增加分享数量
                BusinessShare(goodesId);
            }

            @Override
            public void copy(String id) {
                //获得淘口令
                shearMsg(id);
            }

            @Override
            public void shearCopy() {
                showToast("复制成功", Gravity.CENTER);
            }
        });

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
                try {
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
                                WindowUtils.dismiss();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestPresenter.onCandySharing(getActivity(), map);
                                    }
                                },3000);
                            }
                        });
                    } else {
                        showToast("已自动复制文案，图片已保存至相册");
                        requestPresenter.onCandySharing(getActivity(), map);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                mTextTip.setText("暂无数据");
                mItemBuRefreshLayout.finishRefreshing();
                mItemBuRefreshLayout.finishLoadmore();
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                mTextTip.setText("暂无数据");
                mItemBuRefreshLayout.finishRefreshing();
                mItemBuRefreshLayout.finishLoadmore();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                mTextTip.setText("暂无数据");
                mItemBuRefreshLayout.finishRefreshing();
                mItemBuRefreshLayout.finishLoadmore();
            }
        });
    }

    //增加分享次数
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

    //获得淘口令
    public void shearMsg(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("goodsId", id);
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getShareMsg(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                ShareGoodsEntity shareGoodsEntity = new Gson().fromJson(data, ShareGoodsEntity.class);
                if (shareGoodsEntity.getCode() == 2) {
                    // TODO: 2019/8/15 0015 shouquan
                    AuntTao auntTao = new AuntTao();
                    auntTao.setContext(getActivity());
                    auntTao.AuntTabo();
                    return;
                }
                CopyTextUtil.CopyText(getActivity(), "復製这条口令" + "(" + shareGoodsEntity.getTaoToken() + "),去【tao寶】下单");
                showToast("复制淘口令成功", Gravity.CENTER);
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
                popupWindow.getContentView().setOnClickListener(new View.OnClickListener() {
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
