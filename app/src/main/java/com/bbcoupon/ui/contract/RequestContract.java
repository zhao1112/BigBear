package com.bbcoupon.ui.contract;

import android.content.Context;

import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.contract
 * @DATE 2020/4/22
 */
public class RequestContract {

    public interface RequestView {
        void onSuccess(Object data);

        void onNotNetWork();

        void onFail(Throwable e);
    }

    public interface RequestPresenter<RequestView> {
        void setRelation(RequestView relation);

        void setUntying(RequestView untying);

        void onCandySharing(Context context, Map<String, String> map);

        void onTutorWx(Context context);

        void onCustom(Context context, Map<String, String> map);

        void onWithdrawal(Context context, Map<String, String> map);

        void onMeetingplace(Context context, Map<String, String> map);

        void ontMeetingplaceSearch(Context context, Map<String, String> map);

        void ontShareMsg(Context context, Map<String, String> map);

        void onMsgCode(Context context, Map<String, String> map);

        void onUpdateGender(Context context, Map<String, String> map);

        void onUsersWXInfo(Context context);

        void onVerificationCode(Context context, Map<String, String> map);

        void onSmsVerificationCode(Context context, Map<String, String> map);

        void onMobilePhone(Context context, Map<String, String> map);

        void onThirdPartyBind(Context context, Map<String, String> map);

        void onMemberInfo(Context context, Map<String, String> map);

        void onisBindBankCard(Context context);

        void onWithdrawAmount(Context context, Map<String, String> map);

        void onWithOutAlipay(Context context, Map<String, String> map);

        void validPayPassword(Context context, Map<String, String> map);

        void onBindingAlipay(Context context, Map<String, String> map);

        void onSmsVerification(Context context, Map<String, String> map);

        void onApplyWithdraw(Context context, Map<String, String> map);

        void onRsaPublickey(Context context);

        void onSettingMemberInfo(Context context, Map<String, String> map);

        void onSuperSearch(Context context, Map<String, String> map);

        void onKeywordSearch(Context context, Map<String, String> map);

        void onTaskAllRewardNew(Context context);

        void onMessageCount(Context context, Map<String, String> map);

        void onAllArticleList(Context context, Map<String, String> map);

        void onArticleList(Context context, Map<String, String> map);

        void onCommentList(Context context, Map<String, String> map);

        void onNumberOfDetails(Context context, Map<String, String> map);

        void onaddComment(Context context, Map<String, String> map);

        void onTheThumbsUp(Context context, Map<String, String> map);

        void onArticleListByWords(Context context, Map<String, String> map);

        void onHotSearchList(Context context);

        void onShareSumUp(Context context, Map<String, String> map);

        void onSchoolDetails(Context context, Map<String, String> map);
    }

    public interface RequestModel {
        void onCandySharing(Context context, RequestContract.RequestView requestView, Map<String, String> map);

        void onTutorWx(Context context, RequestContract.RequestView requestView);

        void onCustom(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onWithdrawal(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onMeetingplace(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void ontMeetingplaceSearch(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void ontShareMsg(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onMsgCode(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onUpdateGender(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onUsersWXInfo(Context context, RequestContract.RequestView requestView);

        void onVerificationCode(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onSmsVerificationCode(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onMobilePhone(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onThirdPartyBind(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onMemberInfo(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onisBindBankCard(Context context, RequestContract.RequestView requestView);

        void onWithdrawAmount(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onWithOutAlipay(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void validPayPassword(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onBindingAlipay(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onSmsVerification(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onApplyWithdraw(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onRsaPublickey(Context context, RequestContract.RequestView requestView);

        void onSettingMemberInfo(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onSuperSearch(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onKeywordSearch(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onTaskAllRewardNew(Context context, RequestContract.RequestView requestView);

        void onMessageCount(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onAllArticleList(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onArticleList(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onCommentList(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onNumberOfDetails(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onaddComment(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onTheThumbsUp(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onArticleListByWords(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onHotSearchList(Context context, RequestContract.RequestView requestView);

        void onShareSumUp(Context context, Map<String, String> map, RequestContract.RequestView requestView);

        void onSchoolDetails(Context context, Map<String, String> map, RequestContract.RequestView requestView);
    }
}
