package com.yunqin.bearmall.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.newversions.detail.NewProductDetailActivity;
import com.newversions.util.SharedPreferencesManager;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.eventbus.GetMessageEvent;
import com.yunqin.bearmall.eventbus.PopWindowEvent;
import com.yunqin.bearmall.util.INotificationUtil;
import com.yunqin.bearmall.util.SharedPreferencesHelper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author AYWang
 * @create 2018/8/13
 * @Describe
 */

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class BearMallIntentService extends GTIntentService {
    public BearMallIntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        try {
            String contentData = new String(msg.getPayload());
            JSONObject jsonObject = new JSONObject(contentData);
            String model = jsonObject.optString("model");
            if ("101".equals(model)) {
                String title = jsonObject.optString("title");
                String productId = jsonObject.optString("productId");
                String content = jsonObject.optString("content");
                Intent intent = new Intent(context, NewProductDetailActivity.class);
                intent.putExtra("productId", "" + productId);
                intent.putExtra("sku_id", "");
                // TODO 处理透传消息
                INotificationUtil.showNotification(context, intent, title, content);
            } else if ("102".equals(model)) {
                try {
                    if (BearMallAplication.getInstance().getUser() != null) {
                        String mobile = BearMallAplication.getInstance().getUser().getData().getMember().getMobile();
                        boolean isHomePageShow = jsonObject.optInt("isHomePageShow") == 1;
                        boolean isMePageShow = jsonObject.optInt("isMePageShow") == 1;
                        if (isHomePageShow) {
                            SharedPreferencesManager.clearWhichOne(context, mobile + "home");
                        }
                        if (isMePageShow) {
                            SharedPreferencesManager.clearWhichOne(context, mobile + "mine");
                        }
                        // TODO 发送EventBus 通知Activity显示弹框
                        EventBus.getDefault().post(new PopWindowEvent());
                    } else {
                        SharedPreferencesManager.clearAll(context);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "GTTransmitMessage" + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        //Assist_
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        SharedPreferencesHelper.put(context, "clientid", clientid);
        upGeTuiCid(clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.e(TAG, "onReceiveClientId -> " + "GTCmdMessage = " + cmdMessage);
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
        Log.e(TAG, "onReceiveClientId -> " + "GTNotificationMessage = " + msg);
        EventBus.getDefault().post(new GetMessageEvent());
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
        Log.e(TAG, "onReceiveClientId -> " + "GTNotificationMessage = " + msg);
    }


    public void upGeTuiCid(String cid) {
        Constans.params.clear();
        Constans.params.put("cid", cid);
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).updateGeTuiInfo(Constans.params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("clientid", "CID上传成功");
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
