package com.yunqin.bearmall.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.PartenrAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.AppointNumberBean;
import com.yunqin.bearmall.bean.FansAppoint;
import com.yunqin.bearmall.bean.FansPeopleAlwaysBean;
import com.yunqin.bearmall.bean.PartnerFansBean;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.OpenGoodsDetail;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class FragmentFans extends BaseFragment {

    @BindView(R.id.partenr_fans_reclcler)
    RecyclerView mPartenrFansReclcler;
    @BindView(R.id.partenr_fans_refresh)
    TwinklingRefreshLayout mPartenrFansRefresh;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    @BindView(R.id.fans_headcount)
    TextView mFansHeadCount;
    @BindView(R.id.fans_super_members)
    TextView mFansuperMembers;
    @BindView(R.id.fans_big_head)
    TextView mFansBigHead;
    private String title;
    int type = 1;
    int page = 1;
    private int pageSize = 10;
    private boolean hasMore = true;
    private PartenrAdapter mPartenrAdapter;
    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.mine_user_icon_defult)
            .error(R.drawable.mine_user_icon_defult)
            .centerCrop();
    private TextView mFansPopLevel;
    private String phone;
    private final String money = "¥";
    private int mAppointNum;

    @Override
    public int layoutId() {
        return R.layout.fans_partenr;
    }

    @Override
    public void init() {


        mNulldata.setVisibility(View.GONE);

        Bundle arguments = getArguments();
        title = arguments.getString("title");

        if (title.equals("全部")) {
            type = 1;
        } else if (title.equals("大团长")) {
            type = 2;
        } else if (title.equals("超级会员")) {
            type = 3;
        }

        mPartenrAdapter = new PartenrAdapter(getContext());
        mPartenrFansReclcler.setLayoutManager(new LinearLayoutManager(getContext()));
        mPartenrFansReclcler.setAdapter(mPartenrAdapter);

        mPartenrAdapter.setOnNextCheckListener(new PartenrAdapter.OnNextCheckListener() {
            @Override
            public void onFansNet(String createdDate, String id, String iconUrl, String mobile, String level, String wxID) {
                FansNext(createdDate, id, iconUrl, mobile, level, wxID);
                AppoinNun(id);
            }

            @Override
            public void onAppoInNun() {

            }
        });


        mPartenrFansRefresh.setHeaderView(new RefreshHeadView(getActivity()));
        mPartenrFansRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                mPartenrAdapter.clearData();
                getTabFans();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    page++;
                    getTabFans();
                } else {
                    mPartenrFansRefresh.finishRefreshing();
                    mPartenrFansRefresh.finishLoadmore();
                }
            }
        });
        getTabFans();
    }

    private int state;

    //任命次数
    private void AppoinNun(String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("customerId", id);
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getAppointNum(hashMap), new RetrofitApi.IResponseListener() {

            @Override
            public void onSuccess(String data) throws JSONException {
                FansAppoint fansAppoint = new Gson().fromJson(data, FansAppoint.class);
                mAppointNum = fansAppoint.getAppointNum();
                state = fansAppoint.getState();
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    //粉丝详情
    private void FansNext(String createdDate, String id, String iconUrl, String mobile, String level, String wxID) {
        Map<String, String> map = new HashMap<>();
        map.put("customerId", id);
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).FansInfo(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (data != null) {
                    JSONObject object = new JSONObject(data);
                    JSONObject dat = object.optJSONObject("data");
                    Double lastMonthIncome = dat.optDouble("lastMonthIncome");
                    Double cumulativeIncome = dat.optDouble("cumulativeIncome");
                    String recommendCode = dat.optString("recomendCode");
                    showFans(iconUrl, mobile, createdDate, lastMonthIncome, cumulativeIncome, recommendCode, id, wxID);
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

    private boolean isClic = true;

    private void showFans(String iconUrl, String mobile, String createdDate, Double lastMonthIncome, Double cumulativeIncome,
                          String recommendCode, String id, String wxID) {

        OpenGoodsDetail.lightoff(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fans_pop_appoint, null);
        //头像
        CircleImageView mFansPopToiamge = view.findViewById(R.id.fans_pop_toiamge);
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
        TextView mFansPopButton = view.findViewById(R.id.fans_pop_button);
        //任命次数
        mFansPopLevel = view.findViewById(R.id.fans_pop_level);
        //微信
        TextView wxid_2 = view.findViewById(R.id.wxid_2);
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
        Glide.with(getActivity()).load(iconUrl).apply(requestOptions).into(mFansPopToiamge);
        //赋值手机号
        if (mobile != null && !"".equals(mobile)) {
            if (isMobileNum(mobile)) {
                phone = mobile.substring(0, 3) + "****" + mobile.substring(7);
            }
        }
        mFansPopLevel.setText("剩余" + mAppointNum + "个名额");
        mFansPopPhone.setText(phone);
        //赋值id
        mFansPopCodeFans.setText(recommendCode);
        //赋值上月收益
        mFansPopLastMonthIncome.setText(money + doubleToString(lastMonthIncome));
        //赋值预估收益
        mFansPopCumulative.setText(money + doubleToString(cumulativeIncome));
        //赋值注册时间
        mFansPopCreattime.setText("注册时间 " + createdDate);
        if (!TextUtils.isEmpty(wxID) && !wxID.equals("null ")) {
            wxid_2.setText(wxID);
        } else {
            wxid_2.setText("未填写");
        }

        if (state == 0) {
            isClic = true;
            mFansPopButton.setText("提升团长");
            mFansPopButton.setBackground(getResources().getDrawable(R.drawable.fans_pop_button_radous));
        } else if (state == 1) {
            isClic = false;
            mFansPopButton.setText("正在审核");
            mFansPopButton.setBackground(getResources().getDrawable(R.drawable.address_bg_vip));
        } else if (state == 2) {
            isClic = false;
            mFansPopButton.setText("审核失败");
            mFansPopButton.setBackground(getResources().getDrawable(R.drawable.address_bg_vip));
        }

        view.findViewById(R.id.fans_pop_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        view.findViewById(R.id.fans_pop_copy_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, recommendCode));
                showToast("复制成功");
            }
        });
        view.findViewById(R.id.fans_pop_copy_wxid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(wxID) && !wxID.equals("null ")) {
                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, wxID));
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

        mFansPopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClic) {
                    Map<String, String> map = new HashMap<>();
                    map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
                    map.put("BigLeaderId", id);
                    RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).appointBigLeader(map),
                            new RetrofitApi.IResponseListener() {
                                @Override
                                public void onSuccess(String data) throws JSONException {
                                    Log.e("appointBigLeader", data);
                                    AppointNumberBean appointNumberBean = new Gson().fromJson(data, AppointNumberBean.class);
                                    if (appointNumberBean.getCode() == 1) {
                                        if (appointNumberBean.getMsg().equals("请求成功")) {
                                            mFansPopButton.setText("正在审核");
                                            mFansPopButton.setBackground(getResources().getDrawable(R.drawable.address_bg_vip));
                                        } else {
                                            Toast.makeText(getContext(), appointNumberBean.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (appointNumberBean.getCode() == 0) {
                                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                                    } else if (appointNumberBean.getCode() == -2) {
                                        Toast.makeText(getContext(), "用户未登录", Toast.LENGTH_SHORT).show();
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

    private void getTabFans() {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("type", String.valueOf(type));

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).screenFans(map), new RetrofitApi.IResponseListener() {

            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("searchFans", data);
                PartnerFansBean partnerFansBean = new Gson().fromJson(data, PartnerFansBean.class);
                if (partnerFansBean.getData().getFans() != null && partnerFansBean.getData().getFans().size() > 0) {
                    mPartenrAdapter.addData(partnerFansBean.getData().getFans());
                    if (partnerFansBean.getData().getFans().size() >= 10) {
                        hasMore = true;
                        mPartenrFansRefresh.setBottomView(new RefreshBottomView(getActivity()));
                    } else {
                        hasMore = false;
                        mPartenrFansRefresh.setBottomView(new RefreshFooterView(getActivity()));
                    }
                    mNulldata.setVisibility(View.GONE);
                } else {
                    mNulldata.setVisibility(View.VISIBLE);
                }
                mPartenrFansRefresh.finishRefreshing();
                mPartenrFansRefresh.finishLoadmore();
                hiddenLoadingView();
            }

            @Override
            public void onNotNetWork() {
                mPartenrFansRefresh.finishRefreshing();
                mPartenrFansRefresh.finishLoadmore();
                hiddenLoadingView();
                mNulldata.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                mPartenrFansRefresh.finishRefreshing();
                mPartenrFansRefresh.finishLoadmore();
                hiddenLoadingView();
                mNulldata.setVisibility(View.VISIBLE);
            }
        });

        HashMap<String, String> fansmap = new HashMap<>();
        map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).partnerFansCount(fansmap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("partnerFansCount", data);
                FansPeopleAlwaysBean fansPeopleAlwaysBean = new Gson().fromJson(data, FansPeopleAlwaysBean.class);
                int count = fansPeopleAlwaysBean.getData().getCount();
                mFansHeadCount.setText("总人数:" + count + "人");
                int superMem = fansPeopleAlwaysBean.getData().getSuperMem();
                mFansuperMembers.setText("超级会员:" + superMem + "人");
                int bigLeader = fansPeopleAlwaysBean.getData().getBigLeader();
                mFansBigHead.setText("大团长:" + bigLeader + "人");
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
