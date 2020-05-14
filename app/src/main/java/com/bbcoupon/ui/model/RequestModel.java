package com.bbcoupon.ui.model;

import android.content.Context;
import android.widget.Toast;

import com.bbcoupon.ui.bean.CustomInfor;
import com.bbcoupon.ui.bean.MeetingInfor;
import com.bbcoupon.ui.bean.MeetingShareInfor;
import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.bean.TutorInfor;
import com.bbcoupon.ui.bean.WithdrawalInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.google.gson.Gson;
import com.newversions.tbk.entity.ShareGoodsEntity;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.PhoneLoginActivity;
import com.yunqin.bearmall.util.CommonUtils;

import org.json.JSONException;

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
}
