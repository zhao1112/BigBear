package com.yunqin.bearmall.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newversions.CardListWebActivity;
import com.newversions.tbk.BindWXCallBack;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.SignInBean;
import com.yunqin.bearmall.bean.SignInPage;
import com.yunqin.bearmall.inter.ChangeHeadImageCallBack;
import com.yunqin.bearmall.inter.ChangeNickNameCallBack;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.inter.loginWayCallBack;
import com.yunqin.bearmall.inter.sureAddressCallBack;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.Highlight.HighlightLinearLayout;
import com.yunqin.bearmall.widget.Highlight.HighlightRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class DialogUtils {

    public static void changeHeadImage(final Context activity, ChangeHeadImageCallBack changeHeadImageCallBack) {
        //创建对话框创建器
        final HighlightRelativeLayout canle, my_photos, take_photos;
        final Dialog dialog = new Dialog(activity, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(true);

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_change_head_image, null);
        canle = (HighlightRelativeLayout) view.findViewById(R.id.cancel);
        my_photos = (HighlightRelativeLayout) view.findViewById(R.id.my_photos);
        take_photos = (HighlightRelativeLayout) view.findViewById(R.id.take_photos);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        my_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHeadImageCallBack.choseWhat(2);
                dialog.dismiss();
            }
        });

        take_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHeadImageCallBack.choseWhat(1);
                dialog.dismiss();
            }
        });

        //添加取消按钮点击事件
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    //修改昵称
    public static void changeNickName(final Context activity, ChangeNickNameCallBack changeNickNameCallBack) {
        //创建对话框创建器
        final HighlightRelativeLayout canle, sure;
        final DelEditText delEditText;
        //创建对话框创建器
        final Dialog dialog = new Dialog(activity, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_change_nickname, null);
        canle = (HighlightRelativeLayout) view.findViewById(R.id.canle);
        sure = (HighlightRelativeLayout) view.findViewById(R.id.sure);
        delEditText = view.findViewById(R.id.nick_name_editext);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.getDecorView().setPadding(30, 0, 30, 0);


        //添加确定按钮点击事件
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delEditText.getText().toString().equals("")) {
                    Toast.makeText(activity, "请输入您要修改的昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                changeNickNameCallBack.dialogCallBack(delEditText.getText().toString());
                dialog.dismiss();
            }
        });

        //添加取消按钮点击事件
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    /**
     * 登录页面选择登陆方式dialog
     */
    public static void changeLoginWay(final Activity activity, final loginWayCallBack loginWayCallBack) {
        final HighlightLinearLayout qq_login, phone_login;
        final HighlightRelativeLayout cancel;
        final Dialog dialog = new Dialog(activity, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_login_way_chose, null);
        qq_login = view.findViewById(R.id.qq_login);
        phone_login = view.findViewById(R.id.phone_login);
        cancel = view.findViewById(R.id.cancel);

        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        qq_login.setOnClickListener(v -> {
            loginWayCallBack.loginWayResult(1);
            dialog.dismiss();

        });
        phone_login.setOnClickListener(v -> {
            loginWayCallBack.loginWayResult(2);
            dialog.dismiss();
        });
        //取消
        cancel.setOnClickListener(v -> dialog.dismiss());
    }


    public static void newUserRegiestGet(final Activity context) {
        final RelativeLayout layout;
        final TextView bc_number;
        //创建对话框创建器
        final Dialog dialog = new Dialog(context, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.new_user_bc_reward, null);
        layout = view.findViewById(R.id.layout);
        bc_number = view.findViewById(R.id.bc_number);
        bc_number.setText((String) SharedPreferencesHelper.get(context, "firstLoginReward", "199"));
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.getDecorView().setPadding(30, 0, 30, 0);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public static void tipAddressDialog(final Activity context, AddressListBean.DataBean dataBean, final sureAddressCallBack sureAddressCallBack) {
        //创建对话框创建器
        final HighlightRelativeLayout canle, sure;
        final TextView tip1, tip2;
        final Dialog dialog = new Dialog(context, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_tip, null);
        canle = (HighlightRelativeLayout) view.findViewById(R.id.canle);
        sure = (HighlightRelativeLayout) view.findViewById(R.id.sure);
        tip1 = view.findViewById(R.id.tip1);
        tip2 = view.findViewById(R.id.tip2);
        tip1.setText("确认收货地址");
        tip2.setText(dataBean.getConsignee() + " " + dataBean.getAreaName() + " " + dataBean.getAddress() + " " + dataBean.getPhone());

        dialog.setContentView(view);
        dialog.show();


        Window window = dialog.getWindow();
        window.getDecorView().setPadding(30, 0, 30, 0);

        //添加确定按钮点击事件
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureAddressCallBack.sureAddressBtn();
                dialog.dismiss();
            }
        });

        //添加取消按钮点击事件
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureAddressCallBack.canleSureAddress();
                dialog.dismiss();
            }
        });
    }


    public static void tipSearchDialog(final Activity context, int flag, String tipString, final JoinZeroCallBack joinZeroCallBack) {
        //创建对话框创建器
        final HighlightRelativeLayout canle, sure;
        final TextView tip1, tip2;
        final Dialog dialog = new Dialog(context, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_tip, null);
        canle = (HighlightRelativeLayout) view.findViewById(R.id.canle);
        sure = (HighlightRelativeLayout) view.findViewById(R.id.sure);
        tip1 = view.findViewById(R.id.tip1);
        tip2 = view.findViewById(R.id.tip2);

        if (flag == 1) {
            tip1.setText("兑换需要支付");
            tip2.setText(tipString);
        } else if (flag == 100) {
            tip1.setText("您已参与该商品夺宝，查看我的夺宝？");
            tip2.setVisibility(View.GONE);
        } else if (flag == 666) {
            String[] args = tipString.split(",");
            tip1.setText(args[0].trim());
            tip2.setText(args[1].trim());
        } else if (flag == 200) {
            tip1.setText("确认清理缓存吗？");
            tip2.setText(tipString);
        } else {
            tip1.setText("您已参加该商品兑换，查看我的兑换？");
            tip2.setVisibility(View.GONE);
        }

        dialog.setContentView(view);
        dialog.show();


        Window window = dialog.getWindow();
        window.getDecorView().setPadding(30, 0, 30, 0);

        //添加确定按钮点击事件
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinZeroCallBack.sureBtn(flag);
                dialog.dismiss();
            }
        });

        //添加取消按钮点击事件
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinZeroCallBack.canle();
                dialog.dismiss();
            }
        });
    }


    /**
     * 签到dialog
     */

    public static void signInDialog(final Context mContext) {
        final Dialog dialog = new Dialog(mContext, R.style.ProductDialog);
        final LinearLayout center_layout;
        final HighlightButton sign_in;
        final ImageView prgress_bar;
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_sign_in, null);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardListWebActivity.startActivity(mContext,AdConstants.STRING_QIAN_DAO,"");
            }
        });


        center_layout = view.findViewById(R.id.center_layout);
        sign_in = view.findViewById(R.id.sign_in);
        prgress_bar = view.findViewById(R.id.prgress_bar);

        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.sign_rote);

        LinearInterpolator lin = new LinearInterpolator();

        if (operatingAnim != null) {
            prgress_bar.startAnimation(operatingAnim);
        }

        operatingAnim.setInterpolator(lin);

        getSignInData(mContext, center_layout, dialog, view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();

        //设置diaolog显示的位置
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
//        sign_in.setOnClickListener(v -> {
//            //签到按钮的点击
//        });
    }

    private static List<View> signInfoView;

    //获取签到面板的信息
    private static void getSignInData(Context mContext, LinearLayout center_layout, Dialog dialog, View view) {
        signInfoView = new ArrayList<>();
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getSignCandiesRewardInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                SignInPage signInPage = new Gson().fromJson(data, SignInPage.class);
                for (int i = 1; i < 8; i++) {
                    View childView = LayoutInflater.from(mContext).inflate(R.layout.dialog_sign_in_item, null);
                    TextView bt_number_text_1 = childView.findViewById(R.id.bt_number_text_1);
                    TextView text_day = childView.findViewById(R.id.text_day);
                    ImageView img = childView.findViewById(R.id.img);
                    bt_number_text_1.setText("+" + signInPage.getData().getRewardList().get(i - 1).getRewardCount());
                    //0未签到 1已签到
                    if (signInPage.getData().getRewardList().get(i - 1).getIsSign() == 0) {
                        img.setImageResource(R.drawable.no_sign_in_img);
                        text_day.setText("第" + i + "天");
                        bt_number_text_1.setTextColor(Color.parseColor("#666666"));
                    } else {
                        img.setImageResource(R.drawable.sign_in_img);
                        text_day.setText("已领取");
                        bt_number_text_1.setTextColor(Color.parseColor("#23A064"));
                    }
                    //设置布局权重
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    childView.setLayoutParams(params);
                    center_layout.addView(childView);
                    signInfoView.add(childView);
                }
                //判断今日是否签到  0 未签到  1 签到
                if (signInPage.getData().getIsSignToday() == 0) {
                    getSignInSuccessData(mContext, view, dialog, 0);
                    view.findViewById(R.id.sign_in).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.success_layout).setVisibility(View.GONE);
                } else {
                    //请求签到成功的数据
                    view.findViewById(R.id.sign_in).setVisibility(View.GONE);
                    view.findViewById(R.id.success_layout).setVisibility(View.VISIBLE);
                    getSignInSuccessData(mContext, view, dialog, 1);
                }
            }

            @Override
            public void onNotNetWork() {
                new Handler().postDelayed(() -> dialog.dismiss(), 2000);
            }

            @Override
            public void onFail(Throwable e) {
                //打开dialog 如果网络错误或者请求错误 延时两秒关闭dialog
                new Handler().postDelayed(() -> dialog.dismiss(), 2000);
            }
        });
    }

    //得到签到成功的后的面板数据  和  点击签到按钮后的显示页面数据
    private static void getSignInSuccessData(Context mContext, View view, Dialog dialog, int flag) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).MemberRegister(), new RetrofitApi.IResponseListener() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onSuccess(String data) {
                try {
                    SignInBean signInBean = new Gson().fromJson(data, SignInBean.class);
                    View seccessLayout = view.findViewById(R.id.success_layout);
                    HighlightButton btn = view.findViewById(R.id.sign_in);
                    seccessLayout.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.GONE);
                    ((TextView) seccessLayout.findViewById(R.id.bt_number)).setText("+" + signInBean.getData().getUsersRegisterInfo().getReward());
                    ((TextView) seccessLayout.findViewById(R.id.time)).setText(signInBean.getData().getUsersRegisterInfo().getRegisterDate());
                    ((TextView) seccessLayout.findViewById(R.id.rank_text))
                            .setText(Html.fromHtml(mContext.getResources().getString(R.string.sign_rank,
                                    signInBean.getData().getRegisterRanking(),
                                    signInBean.getData().getRegisterTotalCount())));
//                    ((TextView) seccessLayout.findViewById(R.id.name))
//                            .setText(signInBean.getData().getVirtualCoinName());


//                    seccessLayout.findViewById(R.id.more_bi_).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            CardListWebActivity.startActivity(mContext,AdConstants.STRING_QIAN_DAO,"");
//
//                        }
//                    });


                    Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.xnb_)).load(signInBean.getData().getVirtualCoinPicture())
                            .into((ImageView) seccessLayout.findViewById(R.id.image));

                    setChildView(signInBean.getData().getUsersRegisterInfo().getSerialNumber() - 1);

                    if (flag == 0) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ToastGetBC.getInstence().show(mContext, "+" + signInBean.getData().getUsersRegisterInfo().getReward() + "BC");
                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    throw e;
                }
            }

            @Override
            public void onNotNetWork() {
                new Handler().postDelayed(() -> {
                    dialog.dismiss();
                }, 2000);
            }

            @Override
            public void onFail(Throwable e) {
                //打开dialog 如果网络错误或者请求错误 延时两秒关闭dialog
                new Handler().postDelayed(() -> {
                    dialog.dismiss();
                }, 2000);
            }
        });
    }

    private static void setChildView(int i) {
        View view = signInfoView.get(i);
        ((ImageView) view.findViewById(R.id.img)).setImageResource(R.drawable.sign_in_img);
        ((TextView) view.findViewById(R.id.text_day)).setText("已领取");
        ((TextView) view.findViewById(R.id.bt_number_text_1)).setTextColor(Color.parseColor("#23A064"));
    }


    public static void showCallPhoneDialog(final Activity context, String phone) {
        //创建对话框创建器
        final HighlightRelativeLayout canle, sure;
        final TextView tip1, tip2;
        final Dialog dialog = new Dialog(context, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_tip, null);
        canle = view.findViewById(R.id.canle);
        sure = view.findViewById(R.id.sure);
        tip1 = view.findViewById(R.id.tip1);
        tip2 = view.findViewById(R.id.tip2);

        tip1.setText("是否拨打电话");
        tip2.setText(phone);


        dialog.setContentView(view);
        dialog.show();


        Window window = dialog.getWindow();
        window.getDecorView().setPadding(30, 0, 30, 0);

        //添加确定按钮点击事件
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                context.startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    //绑定微信
    public static void showBindWXDialog(Activity context, BindWXCallBack callBack){
        final TextView  sure;

        final Dialog dialog = new Dialog(context, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bindwx, null);
        sure = view.findViewById(R.id.tv_ok);
        sure.setOnClickListener(v -> {
            if(callBack!=null) callBack.sure();
            dialog.dismiss();
//            if (!isQQClientAvailable(this)) {
//                showToast("未安装微信");
//            } else {
//                showLoading();
//                Platform platform = ShareSDK.getPlatform(Wechat.NAME);
//                platform.SSOSetting(false);
//                platform.setPlatformActionListener(this);
//                platform.authorize();
//            }
//
//            dialog.dismiss();
//            context.finish();
        });
        dialog.setContentView(view);
        dialog.show();
    }

}
