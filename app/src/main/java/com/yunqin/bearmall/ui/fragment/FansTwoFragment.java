package com.yunqin.bearmall.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.FansItemAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.SecondFans;
import com.yunqin.bearmall.bean.StairFans;
import com.yunqin.bearmall.widget.OpenGoodsDetail;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2020/1/9
 */
public class FansTwoFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    @BindView(R.id.fans_twinkling)
    TwinklingRefreshLayout mFansTwinkling;

    private FansItemAdapter mFansItemAdapter;
    private int Secondpage = 1;
    private int pageSize = 10;
    private RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_product)//图片加载出来前，显示的图片
            .fallback(R.drawable.default_product) //url为空的时候,显示的图片
            .error(R.drawable.default_product)//图片加载失败后，显示的图片
            .circleCropTransform();
    private final String money = "¥";


    @Override
    public int layoutId() {
        return R.layout.fragment_fans;
    }

    @Override
    public void init() {
        showLoading();

        SecondFans();

        mFansItemAdapter = new FansItemAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mFansItemAdapter);

        mFansTwinkling.setHeaderView(new RefreshHeadView(getActivity()));
        mFansTwinkling.setBottomView(new RefreshBottomView(getActivity()));
        mFansTwinkling.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                Secondpage = 1;
                mFansItemAdapter.clearFansData();
                SecondFans();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                Secondpage++;
                SecondFans();
            }
        });

        mFansItemAdapter.setClickItem(new FansItemAdapter.OnClickItem() {
            @Override
            public void onFanshInfo(String customerId, String imageUrl, String phone, String creatTime, String wxId) {
                FansInfo(customerId, imageUrl, phone, creatTime, wxId);
            }
        });

    }

    //二级粉丝
    private void SecondFans() {
        Map<String, String> map = new HashMap<>();
        map.put("page", Secondpage + "");
        map.put("pageSize", pageSize + "");
        map.put("type", "2");
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getUserAllFans(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                StairFans secondFans = new Gson().fromJson(data, StairFans.class);
                if (secondFans.getData() != null) {
                    if (secondFans.getData().getList() != null && secondFans.getData().getList().size() > 0) {
                        mFansItemAdapter.addFansOne(secondFans.getData().getList());
                    } else {
                        mNulldata.setVisibility(View.VISIBLE);
                        mFansTwinkling.setBottomView(new RefreshFooterView(getActivity()));
                    }
                }
                hiddenLoadingView();
                mFansTwinkling.finishRefreshing();
                mFansTwinkling.finishLoadmore();
                mNulldata.setVisibility(View.GONE);
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                mFansTwinkling.finishRefreshing();
                mFansTwinkling.finishLoadmore();
                mNulldata.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                mFansTwinkling.finishRefreshing();
                mFansTwinkling.finishLoadmore();
                mNulldata.setVisibility(View.VISIBLE);
            }
        });
    }

    //粉丝详情
    public void FansInfo(String customerId, String imageUrl, String phone, String creatTime, String wxId) {
        Map<String, String> map = new HashMap<>();
        map.put("customerId", customerId);
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).FansInfo(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (data != null) {
                    JSONObject object = new JSONObject(data);
                    JSONObject dat = object.optJSONObject("data");
                    Double lastMonthIncome = dat.optDouble("lastMonthIncome");
                    Double cumulativeIncome = dat.optDouble("cumulativeIncome");
                    String recommendCode = dat.optString("recomendCode");
                    showFans(imageUrl, phone, creatTime, lastMonthIncome, cumulativeIncome, recommendCode, wxId);
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void showFans(String imageUrl, String phone, String creatTime, Double lastMonthIncome, Double cumulativeIncome,
                          String recommendCode, String wxId) {

        OpenGoodsDetail.lightoff(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fansh, null);
        ImageView toiamge = view.findViewById(R.id.toiamge);
        TextView phone2 = view.findViewById(R.id.phone);
        TextView code = view.findViewById(R.id.code_fans);
        TextView lastMonth = view.findViewById(R.id.lastMonthIncome);
        TextView cumulative = view.findViewById(R.id.cumulative);
        TextView creattime = view.findViewById(R.id.creattime);
        TextView copy_wxid = view.findViewById(R.id.wx_id_2);
        PopupWindow mPopupWindow = new PopupWindow();
        mPopupWindow.setContentView(view);
        mPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置背景图片， 必须设置，不然动画没作用
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.CenterAnimation);
        mPopupWindow.setFocusable(true);
        // 设置点击popupwindow外屏幕其它地方消失
        mPopupWindow.setOutsideTouchable(true);
        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mPopupWindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        Glide.with(getActivity()).load(imageUrl).apply(mOptions).into(toiamge);
        code.setText(recommendCode);
        phone2.setText(phone);
        lastMonth.setText(money + doubleToString(lastMonthIncome));
        cumulative.setText(money + doubleToString(cumulativeIncome));
        creattime.setText("注册时间 " + creatTime);
        if (!TextUtils.isEmpty(wxId) && !wxId.equals("null")) {
            copy_wxid.setText(wxId);
        }
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        view.findViewById(R.id.copy_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, recommendCode));
                showToast("复制成功");
            }
        });
        view.findViewById(R.id.copy_wxid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(wxId) && !wxId.equals("null ")) {
                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, wxId));
                    showToast("复制成功");
                } else {
                    showToast("还未填写微信号");
                }
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                OpenGoodsDetail.lighton(getActivity());
            }
        });
    }

    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
