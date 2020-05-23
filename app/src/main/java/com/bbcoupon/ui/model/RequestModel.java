package com.bbcoupon.ui.model;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.bbcoupon.ui.bean.AlipayInfor;
import com.bbcoupon.ui.bean.BaseInfor;
import com.bbcoupon.ui.bean.CustomInfor;
import com.bbcoupon.ui.bean.MeetingInfor;
import com.bbcoupon.ui.bean.MeetingShareInfor;
import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.bean.TutorInfor;
import com.bbcoupon.ui.bean.WXInfor;
import com.bbcoupon.ui.bean.WithdrawalInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.google.gson.Gson;
import com.newversions.tbk.entity.ShareGoodsEntity;
import com.newversions.view.DrawMoneyDialog;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.MemberBeanResponse;
import com.yunqin.bearmall.bean.SettingBean;
import com.yunqin.bearmall.ui.activity.BalanceWithdrawalWxActivity;
import com.yunqin.bearmall.ui.activity.PhoneLoginActivity;
import com.yunqin.bearmall.ui.activity.WithdrawActivity;
import com.yunqin.bearmall.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.model
 * @DATE 2020/4/22
 */
public class RequestModel implements RequestContract.RequestModel {

    //分享得糖果
    @Override
    public void onCandySharing(Context context, RequestContract.RequestView requestView, Map<String, String> map) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).shareArticleAndProduct(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                RequestInfor requestInfor = new Gson().fromJson(data, RequestInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(requestInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //获取导师微信
    @Override
    public void onTutorWx(Context context, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getTutorInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                TutorInfor requestInfor = new Gson().fromJson(data, TutorInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(requestInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //自定义商品
    @Override
    public void onCustom(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getCustomItemLibraryList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                CustomInfor customInfor = new Gson().fromJson(data, CustomInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(customInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //提现
    @Override
    public void onWithdrawal(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getUserbalance(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                WithdrawalInfor customInfor = new Gson().fromJson(data, WithdrawalInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(customInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //活动会场
    @Override
    public void onMeetingplace(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMeetingplace(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                MeetingInfor meetingInfor = new Gson().fromJson(data, MeetingInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(meetingInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //活动会场分享
    @Override
    public void ontMeetingplaceSearch(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMeetingplaceSearch(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                MeetingShareInfor meetingShareInfor = new Gson().fromJson(data, MeetingShareInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(meetingShareInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //获取详情页分享二维码
    @Override
    public void ontShareMsg(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getShareMsg(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                ShareGoodsEntity entity = new Gson().fromJson(data, ShareGoodsEntity.class);
                if (requestView != null) {
                    requestView.onSuccess(entity);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //获取短信验证码
    @Override
    public void onMsgCode(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMsgCode(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (requestView != null) {
                    requestView.onSuccess(new RequestInfor());
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //修改用户性别
    @Override
    public void onUpdateGender(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getUpdateGender(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (requestView != null) {
                    requestView.onSuccess(data);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    //获得绑定微信号
    @Override
    public void onUsersWXInfo(Context context, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getUsersWXInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                WXInfor wxInfor = new Gson().fromJson(data, WXInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(wxInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //修改手机号短信
    @Override
    public void onVerificationCode(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getVerificationCode(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                RequestInfor requestInfor = new Gson().fromJson(data, RequestInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(requestInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //验证修改手机号短信
    @Override
    public void onSmsVerificationCode(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getSmsVerificationCode(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (requestView != null) {
                    requestView.onSuccess(data);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //绑定手机号
    @Override
    public void onMobilePhone(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMobilePhone(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (requestView != null) {
                    requestView.onSuccess(data);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //绑定微信
    @Override
    public void onThirdPartyBind(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).memberThirdPartyBind(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (requestView != null) {
                    requestView.onSuccess(data);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //获取会员信息
    @Override
    public void onMemberInfo(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMemberInfo(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                MemberBeanResponse memberBeanResponse = new Gson().fromJson(data, MemberBeanResponse.class);
                if (requestView != null) {
                    requestView.onSuccess(memberBeanResponse);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //是否绑定银行卡
    @Override
    public void onisBindBankCard(Context context, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).isBindBankCard(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                RequestInfor requestInfor = new Gson().fromJson(data, RequestInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(requestInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //提现金额计算
    @Override
    public void onWithdrawAmount(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).calculateWithdrawAmount(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (requestView != null) {
                    requestView.onSuccess(data);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //校验是否绑定支付宝
    @Override
    public void onWithOutAlipay(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).onWithOutAlipay(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                AlipayInfor alipayInfor = new Gson().fromJson(data, AlipayInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(alipayInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //验证支付密码
    @Override
    public void validPayPassword(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).validPayPassword(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (requestView != null) {
                    requestView.onSuccess(data);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //绑定支付宝
    @Override
    public void onBindingAlipay(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).onBindingAlipay(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                BaseInfor baseInfor = new Gson().fromJson(data, BaseInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(baseInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //绑定支付宝验证码
    @Override
    public void onSmsVerification(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).onSmsVerificationCode(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (requestView != null) {
                    requestView.onSuccess(data);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //申请提现
    @Override
    public void onApplyWithdraw(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).applyWithdraw(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                BaseInfor baseInfor = new Gson().fromJson(data, BaseInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(baseInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //获取RSA公钥
    @Override
    public void onRsaPublickey(Context context, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).onRsaPublickey(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                RequestInfor requestInfor = new Gson().fromJson(data, RequestInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(requestInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //获取用户设置页信息
    @Override
    public void onSettingMemberInfo(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getSettingMemberInfo(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                SettingBean settingBean = new Gson().fromJson(data, SettingBean.class);
                if (requestView != null) {
                    requestView.onSuccess(settingBean);
                }

            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

}
