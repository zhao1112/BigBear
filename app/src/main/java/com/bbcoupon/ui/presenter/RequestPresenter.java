package com.bbcoupon.ui.presenter;

import android.content.Context;

import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.model.RequestModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.presenter
 * @DATE 2020/4/22
 */
public class RequestPresenter implements RequestContract.RequestPresenter<RequestContract.RequestView> {

    private Reference<RequestContract.RequestView> reference;
    private RequestContract.RequestView requestView;
    private RequestContract.RequestModel requestModel;

    @Override
    public void setRelation(RequestContract.RequestView relation) {
        this.requestView = relation;
        reference = new WeakReference<>(relation);
        requestModel = new RequestModel();
    }

    @Override
    public void setUntying(RequestContract.RequestView untying) {
        if (reference != null) {
            reference.clear();
        }
    }

    @Override
    public void onCandySharing(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onCandySharing(context, requestView, map);
        }
    }

    @Override
    public void onTutorWx(Context context) {
        if (requestView != null) {
            requestModel.onTutorWx(context, requestView);
        }
    }

    @Override
    public void onCustom(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onCustom(context, map, requestView);
        }
    }

    @Override
    public void onWithdrawal(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onWithdrawal(context, map, requestView);
        }
    }

    //活动会场
    @Override
    public void onMeetingplace(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onMeetingplace(context, map, requestView);
        }
    }

    //活动会场分享
    @Override
    public void ontMeetingplaceSearch(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.ontMeetingplaceSearch(context, map, requestView);
        }
    }

    //获取分享二维码
    @Override
    public void ontShareMsg(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.ontShareMsg(context, map, requestView);
        }
    }

    //获取短信验证码
    @Override
    public void onMsgCode(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onMsgCode(context, map, requestView);
        }
    }

    //修改用户性别
    @Override
    public void onUpdateGender(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onUpdateGender(context, map, requestView);
        }
    }

    //绑定微信详情
    @Override
    public void onUsersWXInfo(Context context) {
        if (requestView != null) {
            requestModel.onUsersWXInfo(context, requestView);
        }
    }

    //修改手机号短信
    @Override
    public void onVerificationCode(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onVerificationCode(context, map, requestView);
        }
    }

    //验证修改手机号短信
    @Override
    public void onSmsVerificationCode(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onSmsVerificationCode(context, map, requestView);
        }
    }

    //绑定手机号
    @Override
    public void onMobilePhone(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onMobilePhone(context, map, requestView);
        }
    }

    //绑定微信号
    @Override
    public void onThirdPartyBind(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onThirdPartyBind(context, map, requestView);
        }
    }

    //获取会员信息
    @Override
    public void onMemberInfo(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onMemberInfo(context, map, requestView);
        }
    }

    //是否绑定银行卡
    @Override
    public void onisBindBankCard(Context context) {
        if (requestView != null) {
            requestModel.onisBindBankCard(context, requestView);
        }
    }

    //提现金额计算
    @Override
    public void onWithdrawAmount(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onWithdrawAmount(context, map, requestView);
        }
    }

    //校验是否绑定支付宝
    @Override
    public void onWithOutAlipay(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onWithOutAlipay(context, map, requestView);
        }
    }

    //验证支付密码
    @Override
    public void validPayPassword(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.validPayPassword(context, map, requestView);
        }
    }

    //绑定支付宝
    @Override
    public void onBindingAlipay(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onBindingAlipay(context, map, requestView);
        }
    }

    //绑定支付宝短信验证
    @Override
    public void onSmsVerification(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onSmsVerification(context, map, requestView);
        }
    }

    //申请提现
    @Override
    public void onApplyWithdraw(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onApplyWithdraw(context, map, requestView);
        }
    }

    //获取RSA公钥
    @Override
    public void onRsaPublickey(Context context) {
        if (requestView != null) {
            requestModel.onRsaPublickey(context, requestView);
        }
    }

    //获取用户设置页信息
    @Override
    public void onSettingMemberInfo(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onSettingMemberInfo(context, map, requestView);
        }
    }

    //新超级搜索
    @Override
    public void onSuperSearch(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onSuperSearch(context, map, requestView);
        }
    }

    //搜索接口
    @Override
    public void onKeywordSearch(Context context, Map<String, String> map) {
        if (requestView != null) {
            requestModel.onKeywordSearch(context, map, requestView);
        }
    }

}
