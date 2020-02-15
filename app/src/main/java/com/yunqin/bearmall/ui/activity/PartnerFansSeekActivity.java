package com.yunqin.bearmall.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.PartenrAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.AppointNumberBean;
import com.yunqin.bearmall.bean.FansAppoint;
import com.yunqin.bearmall.bean.PartnerFansBean;
import com.yunqin.bearmall.widget.OpenGoodsDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartnerFansSeekActivity extends BaseActivity {


    private EditText mPartnerFansSeekText;
    private Button mPartnerFansSeekButton;
    private ImageView mPartenrFansSeekReturn;
    private RecyclerView mPartenrFansSeekRecyclerview;
    private ConstraintLayout mNulldata;
    private PartenrAdapter partnerFansSeekAdapter;
    private RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_product)//图片加载出来前，显示的图片
            .fallback(R.drawable.default_product) //url为空的时候,显示的图片
            .error(R.drawable.default_product)//图片加载失败后，显示的图片
            .circleCropTransform();
    private TextView mFansPopLevel;
    private String phone;
    private final String money = "¥";

    @Override
    public int layoutId() {
        return R.layout.activity_partner_seek;
    }

    @Override
    public void init() {
        setTranslucentStatus();
        //输入框
        mPartnerFansSeekText = findViewById(R.id.partner_fans_seek_text);
        //搜索按钮
        mPartnerFansSeekButton = findViewById(R.id.partner_fans_seek_button);
        //返回
        mPartenrFansSeekReturn = findViewById(R.id.partenr_fans_seek_return);
        //刷新
        //mPartenrOrderRefresh = findViewById(R.id.partenr_order_refresh);
        //条目
        mPartenrFansSeekRecyclerview = findViewById(R.id.partenr_fans_seek_recyclerview);
        mNulldata = findViewById(R.id.nulldata);

        mPartenrFansSeekReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        partnerFansSeekAdapter = new PartenrAdapter(this);
        mPartenrFansSeekRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mPartenrFansSeekRecyclerview.setAdapter(partnerFansSeekAdapter);

        initDate();
    }

    private void initDate() {
        mPartnerFansSeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partnerFansSeekAdapter.clearData();
                String seek = mPartnerFansSeekText.getText().toString();
                HashMap map = new HashMap<>();
                map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
                map.put("searchPar", seek);
                RetrofitApi.request(getApplicationContext(), RetrofitApi.createApi(Api.class).searchFans(map),
                        new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        PartnerFansBean partnerFansSeekBean = new Gson().fromJson(data, PartnerFansBean.class);
                        if (partnerFansSeekBean.getData().getFans() != null && partnerFansSeekBean.getData().getFans().size() > 0) {
                            partnerFansSeekAdapter.addData(partnerFansSeekBean.getData().getFans());
                            mNulldata.setVisibility(View.GONE);
                            hiddenLoadingView();
                        } else {
                            mNulldata.setVisibility(View.VISIBLE);
                        }
                        hiddenLoadingView();
                    }

                    @Override
                    public void onNotNetWork() {
                        hiddenLoadingView();
                        mNulldata.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFail(Throwable e) {
                        hiddenLoadingView();
                        mNulldata.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        partnerFansSeekAdapter.setOnNextCheckListener(new PartenrAdapter.OnNextCheckListener() {
            @Override
            public void onFansNet(String createdDate, String id, String iconUrl, String mobile, String level) {
                FansSeekNext(createdDate, id, iconUrl, mobile, level);
            }

            @Override
            public void onAppoInNun() {

            }
        });
    }

    private void FansSeekNext(String createdDate, String id, String iconUrl, String mobile, String level) {
        Map<String, String> map = new HashMap<>();
        map.put("customerId", id);
        RetrofitApi.request(getApplicationContext(), RetrofitApi.createApi(Api.class).FansInfo(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (data != null) {
                    JSONObject object = new JSONObject(data);
                    JSONObject dat = object.optJSONObject("data");
                    Double lastMonthIncome = dat.optDouble("lastMonthIncome");
                    Double cumulativeIncome = dat.optDouble("cumulativeIncome");
                    String recommendCode = dat.optString("recomendCode");
                    ShowFansSeek(iconUrl, mobile, createdDate, lastMonthIncome, cumulativeIncome, recommendCode, id);
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

    //任命次数
    private void AppoinNun() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        RetrofitApi.request(getApplicationContext(), RetrofitApi.createApi(Api.class).getAppointNum(hashMap),
                new RetrofitApi.IResponseListener() {

            @Override
            public void onSuccess(String data) throws JSONException {
                FansAppoint fansAppoint = new Gson().fromJson(data, FansAppoint.class);
                int appointNum = fansAppoint.getAppointNum();
                mFansPopLevel.setText("剩余" + appointNum + "个名额");
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void ShowFansSeek(String iconUrl, String mobile, String createdDate, Double lastMonthIncome, Double cumulativeIncome,
                              String recommendCode, String id) {

        OpenGoodsDetail.lightoff(this);
        View view = LayoutInflater.from(this).inflate(R.layout.fans_pop_appoint, null);
        //头像
        ImageView mFansPopToiamge = view.findViewById(R.id.fans_pop_toiamge);
        //手机号
        TextView mFansPopPhone = view.findViewById(R.id.fans_pop_phone);
        //id
        TextView mFansPopCodeFans = view.findViewById(R.id.fans_pop_code_fans);
        //上月收益
        TextView mFansPopLastMonthIncome = view.findViewById(R.id.fans_pop_lastMonthIncome);
        //预估收益
        TextView mFansPopCumulative = view.findViewById(R.id.fans_pop_cumulative);
        //注册时间
        TextView mFansPopCreattime = view.findViewById(R.id.fans_pop_creattime);

        //提升按钮

        Button mFansPopButton = view.findViewById(R.id.fans_pop_button);
        //任命次数
        mFansPopLevel = view.findViewById(R.id.fans_pop_level);
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

        //赋值
        //设置头像
        Glide.with(this).load(iconUrl).apply(mOptions).into(mFansPopToiamge);
        //赋值手机号
        if (mobile != null && !"".equals(mobile)) {

            if (isMobileNum(mobile)) {

                phone = mobile.substring(0, 3) + "****" + mobile.substring(7);
            }
        }
        mFansPopPhone.setText(phone);
        //赋值id
        mFansPopCodeFans.setText(recommendCode);
        //赋值上月收益
        mFansPopLastMonthIncome.setText(money + doubleToString(lastMonthIncome));
        //赋值预估收益
        mFansPopCumulative.setText(money + doubleToString(cumulativeIncome));
        //赋值注册时间
        mFansPopCreattime.setText("注册时间 " + createdDate);

        AppoinNun();

        view.findViewById(R.id.fans_pop_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        view.findViewById(R.id.fans_pop_copy_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, recommendCode));
                showToast("复制成功");
            }
        });
        view.findViewById(R.id.fans_pop_copy_wxid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("还未填写微信号");
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {


                OpenGoodsDetail.lighton(PartnerFansSeekActivity.this);
            }
        });
        mFansPopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
                map.put("BigLeaderId", id);
                RetrofitApi.request(getApplicationContext(), RetrofitApi.createApi(Api.class).appointBigLeader(map),
                        new RetrofitApi.IResponseListener() {

                    @Override
                    public void onSuccess(String data) throws JSONException {
                        Log.e("appointBigLeader", data);
                        Log.e("appointBigLeader", data);
                        AppointNumberBean appointNumberBean = new Gson().fromJson(data, AppointNumberBean.class);
                        if (appointNumberBean.getCode() == 1) {
                            if (appointNumberBean.getMsg().equals("此用户已是大团长")) {
                                Toast.makeText(getApplicationContext(), appointNumberBean.getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), appointNumberBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else if (appointNumberBean.getCode() == 0) {
                            Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                        } else if (appointNumberBean.getCode() == -2) {
                            Toast.makeText(getApplicationContext(), "用户未登录", Toast.LENGTH_SHORT).show();
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
        });
    }

    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    //手机号中间为*
    private boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}