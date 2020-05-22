package com.bbearmall.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.eventbus.Wx;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, "wx294c8fbc4541306b");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }


    private static final int WXCODESUCCESS = 706;// 成功
    private static final int WXCODECANCEL = 707;// 取消
    private static final int WXCODEFAIL = 708;// 失败


    @Override
    public void onResp(BaseResp resp) {
        Log.e("TAG", "onPayFinish, errCode = " + resp.errCode);


        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                EventBus.getDefault().post(new Wx(WXCODESUCCESS));
            } else if (resp.errCode == -2) {
                EventBus.getDefault().post(new Wx(WXCODECANCEL));
            } else {
                EventBus.getDefault().post(new Wx(WXCODEFAIL));
            }
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle(R.string.app_tip);
//            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//            builder.show();
        } else {
            EventBus.getDefault().post(new Wx(WXCODEFAIL));
        }
        finish();
    }
}