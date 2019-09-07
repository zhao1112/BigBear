package com.yunqin.bearmall.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseDialog;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.inter.OnPartSnatchListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenchen on 2018/8/10.
 */

public class BuySnatchDialog extends BaseDialog {

    private Context mContext;
    private Treasure treasure;


    @BindView(R.id.add)
    Button addBtn;
    @BindView(R.id.cut)
    Button cut;
    @BindView(R.id.count)
    EditText countEditext;
    @BindView(R.id.pay_bc_num)
    TextView payView;

    private int buyCount = 1;
    private OnPartSnatchListener partTreasureListener;
    private int tempPostition;

    public void setTempPostition(int tempPostition) {
        this.tempPostition = tempPostition;
    }

    public BuySnatchDialog (@NonNull Context context, Treasure treasure, OnPartSnatchListener listener){

        super(context, R.style.ProductDialog);
        mContext = context;
        this.treasure = treasure;
        this.partTreasureListener = listener;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_buy_snatch,null);
        setContentView(view);
        ButterKnife.bind(this,view);
        setCanceledOnTouchOutside(true);
        countEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.toString();
                if (TextUtils.isEmpty(text)){
                    buyCount = 1;
                    countEditext.setText(buyCount+"");
                }else {
                    buyCount = Integer.parseInt(text);
                }

                float price = Float.parseFloat(treasure.getCost());

                float totla = buyCount * price;

                payView.setText(String.format("%.2f",totla));

            }
        });
        countEditext.setCursorVisible(false);
        countEditext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    countEditext.setCursorVisible(true);// 再次点击显示光标
                }
                return false;
            }
        });
        countEditext.setText("1");
        payView.setText(treasure.getCost());
        setCancelable(true);
        WindowManager m = ((Activity)context).getWindowManager();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display d = m.getDefaultDisplay();
        lp.width = (int) (d.getWidth() * 0.8);
        dialogWindow.setAttributes(lp);

    }

    public BuySnatchDialog(@NonNull Context context, Treasure treasure) {

        this(context,treasure,null);
    }


    @OnClick({R.id.add,R.id.cut,R.id.confirm,R.id.cancle})
    protected void onViewClicked(View view){

        switch (view.getId()){

            case R.id.add:

                if (buyCount<99999){
                    buyCount++;
                    updateCountEditext();
                }


                break;

            case R.id.cut:

                if (buyCount>1){
                    buyCount--;
                    updateCountEditext();
                }


                break;

            case R.id.confirm:

                view.setClickable(true);
                partTreasure();

                break;

            case R.id.cancle:

                dismiss();

                break;

        }

    }

    private void partTreasure() {

        Map<String,String> map = new HashMap<>();
        map.put("tag",treasure.getTag()+"");
        map.put("treasure_id",treasure.getTreasure_id());
        map.put("purchaseCount",buyCount+"");

        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).partTreasure(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

//                BasicData mData = new Gson().fromJson(data,BasicData.class);
                JSONObject jsonObject = new JSONObject(data);
                if ((int)jsonObject.get("code") == 1){

                    if (partTreasureListener != null){

                        partTreasureListener.onBuySuccess(tempPostition,buyCount);

                    }

                    dismiss();

                }else {

                    dismiss();

                    if (partTreasureListener != null){

                        partTreasureListener.onBuyFailer(jsonObject.getString("msg"));

                    }

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

    private void updateCountEditext() {

        countEditext.setText(buyCount+"");

        float price = Float.parseFloat(treasure.getCost());

        float totla = buyCount * price;

        payView.setText(String.format("%.2f",totla));

    }



    class BasicData{
        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


}
