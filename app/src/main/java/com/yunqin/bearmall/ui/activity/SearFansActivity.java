package com.yunqin.bearmall.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.StairFans;
import com.yunqin.bearmall.widget.DelEditText;
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
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity
 * @DATE 2020/4/9
 */
public class SearFansActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.input_content_text)
    DelEditText mInputContentText;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.fans_twinkling)
    TwinklingRefreshLayout mFansTwinkling;

    private FansItemAdapter mFansItemAdapter;
    private int page = 1;
    private int pageSize = 10;
    private int refresh = 0;
    private RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_product)//图片加载出来前，显示的图片
            .fallback(R.drawable.default_product) //url为空的时候,显示的图片
            .error(R.drawable.default_product)//图片加载失败后，显示的图片
            .circleCropTransform();
    private final String money = "¥";

    @Override
    public int layoutId() {
        return R.layout.activity_shearfans;
    }

    @Override
    public void init() {

        mInputContentText.setOnEditorActionListener(this);

        mFansItemAdapter = new FansItemAdapter(SearFansActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearFansActivity.this);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mFansItemAdapter);

        mFansTwinkling.setHeaderView(new RefreshHeadView(SearFansActivity.this));
        mFansTwinkling.setBottomView(new RefreshBottomView(SearFansActivity.this));
        mFansTwinkling.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (!TextUtils.isEmpty(mInputContentText.getText().toString())) {
                    page = 1;
                    refresh = 0;
                    StairFans();
                } else {
                    showToast("请输入搜索内容", Gravity.CENTER);
                }
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (!TextUtils.isEmpty(mInputContentText.getText().toString())) {
                    page++;
                    refresh = 1;
                    StairFans();
                } else {
                    showToast("请输入搜索内容", Gravity.CENTER);
                }
            }
        });

        mFansItemAdapter.setClickItem(new FansItemAdapter.OnClickItem() {
            @Override
            public void onFanshInfo(String customerId, String imageUrl, String phone, String creatTime, String wxId) {
                FansInfo(customerId, imageUrl, phone, creatTime, wxId);
            }
        });

    }


    @OnClick({R.id.search, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                if (!TextUtils.isEmpty(mInputContentText.getText().toString())) {
                    page = 1;
                    refresh = 0;
                    StairFans();
                } else {
                    showToast("请输入搜索内容", Gravity.CENTER);
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP:
                    // TODO 发送请求
                    if (mInputContentText.getText().toString().length() > 0) {
                        page = 1;
                        refresh = 0;
                        StairFans();
                    } else {
                        showToast("请输入搜索内容", Gravity.CENTER);
                    }
                    return true;
                default:
                    return true;
            }
        }
        return false;
    }

    private void StairFans() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("pageSize", pageSize + "");
        map.put("searchPar", mInputContentText.getText().toString());
        RetrofitApi.request(SearFansActivity.this, RetrofitApi.createApi(Api.class).searchFansByMobile(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        StairFans stairFans = new Gson().fromJson(data, StairFans.class);
                        if (stairFans.getData().getList() != null && stairFans.getData().getList().size() > 0) {
                            if (refresh == 0) {
                                mFansItemAdapter.clearFansData();
                            }
                            mFansItemAdapter.addFansOne(stairFans.getData().getList());
                        } else {
                            mFansTwinkling.setBottomView(new RefreshFooterView(SearFansActivity.this));
                        }
                        hiddenLoadingView();
                        mFansTwinkling.finishRefreshing();
                        mFansTwinkling.finishLoadmore();
                    }

                    @Override
                    public void onNotNetWork() {
                        hiddenLoadingView();
                        mFansTwinkling.finishRefreshing();
                        mFansTwinkling.finishLoadmore();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        hiddenLoadingView();
                        mFansTwinkling.finishRefreshing();
                        mFansTwinkling.finishLoadmore();
                    }
                });

    }

    public void FansInfo(String customerId, String imageUrl, String phone, String creatTime, String wxId) {
        Map<String, String> map = new HashMap<>();
        map.put("customerId", customerId);
        RetrofitApi.request(SearFansActivity.this, RetrofitApi.createApi(Api.class).FansInfo(map), new RetrofitApi.IResponseListener() {
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

        OpenGoodsDetail.lightoff(SearFansActivity.this);
        View view = LayoutInflater.from(SearFansActivity.this).inflate(R.layout.dialog_fansh, null);
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
        Glide.with(SearFansActivity.this).load(imageUrl).apply(mOptions).into(toiamge);
        code.setText(recommendCode);
        phone2.setText(phone);
        lastMonth.setText(money + doubleToString(lastMonthIncome));
        cumulative.setText(money + doubleToString(cumulativeIncome));
        creattime.setText("注册时间 " + creatTime);
        if (!TextUtils.isEmpty(wxId) && !wxId.equals("null ")) {
            copy_wxid.setText(wxId);
        } else {
            copy_wxid.setText("未填写");
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
                ClipboardManager clipboardManager = (ClipboardManager) SearFansActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, recommendCode));
                showToast("复制成功");
            }
        });
        view.findViewById(R.id.copy_wxid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(wxId) && !wxId.equals("null ")) {
                    ClipboardManager clipboardManager = (ClipboardManager) SearFansActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
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
                OpenGoodsDetail.lighton(SearFansActivity.this);
            }
        });
    }

    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
