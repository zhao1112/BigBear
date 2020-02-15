package com.newversions.detail.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.eventbus.OrderTypeUpdate;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.ui.activity.MineOrderActivity;
import com.yunqin.bearmall.ui.activity.PayActivity;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * 虚拟订单详情
 */
public class VirtualOrdersActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView status_title;
    private TextView status_time;
    private TextView phone;
    private TextView order_title;
    private TextView order_price;
    private TextView youhui_price;
    private TextView zhifu_price;
    private TextView ding_dan_code;
    private TextView da_xiong_code;
    private TextView chuang_jian_time;
    private TextView good_price;
    private ImageView imgage;


    private Button order_del;
    private Button order_go_pay;
    private Button order_cancel;
    private final String money = "¥";
    private LinearLayout order_bottom_layout;


    private boolean REFRESH_SUCCESS = false;

    private int order_id;
    private int type;

    public static void start(Context context, int order_id, int type) {
        Intent intent = new Intent(context, VirtualOrdersActivity.class);
        intent.putExtra("order_id", order_id);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_virtual_orders);
        order_id = getIntent().getIntExtra("order_id", -1);
        type = getIntent().getIntExtra("order_id", -1);
        findViews();
        initData();
    }

    private VirtualOrderBean virtualOrderBean;

    private void initData() {


        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orderProductType", "1");
        mHashMap.put("orders_id", String.valueOf(order_id));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getVirtualOrderDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                REFRESH_SUCCESS = true;
                virtualOrderBean = new Gson().fromJson(data, VirtualOrderBean.class);
                initViews();

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void initViews() {

        phone.setText(String.format("话费充值号码：%s", virtualOrderBean.getData().getVirtualOrderDetail().getMobile()));
        order_title.setText(virtualOrderBean.getData().getVirtualOrderDetail().getTitle());
        order_price.setText(money + virtualOrderBean.getData().getVirtualOrderDetail().getPrice());
        good_price.setText(money+ virtualOrderBean.getData().getVirtualOrderDetail().getPrice());
        youhui_price.setText(money + virtualOrderBean.getData().getVirtualOrderDetail().getUsersTicketDiscount());
        zhifu_price.setText(money+ virtualOrderBean.getData().getVirtualOrderDetail().getAmount());

        ding_dan_code.setText("订单编号：" + virtualOrderBean.getData().getVirtualOrderDetail().getOrderSn());
        da_xiong_code.setText("大熊交易号：" + virtualOrderBean.getData().getVirtualOrderDetail().getOutTradeNo());
        chuang_jian_time.setText("创建时间：" + virtualOrderBean.getData().getVirtualOrderDetail().getCreatedDate());


        try {
            Glide.with(this).load(virtualOrderBean.getData().getVirtualOrderDetail().getImg()).into(imgage);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (virtualOrderBean.getData().getVirtualOrderDetail().getStatus() == 0) {
// 待支付
            status_title.setText("等待买家付款");
            status_time.setText(String.format("剩%s自动关闭", virtualOrderBean.getData().getVirtualOrderDetail().getOrderRestTime()));

            order_del.setVisibility(View.GONE);

        } else if (virtualOrderBean.getData().getVirtualOrderDetail().getStatus() == 1 || virtualOrderBean.getData().getVirtualOrderDetail().getStatus() == 2) {
// 隐藏布局
            status_title.setText("买家已付款");
            status_time.setVisibility(View.GONE);

            order_bottom_layout.setVisibility(View.GONE);


        } else if (virtualOrderBean.getData().getVirtualOrderDetail().getStatus() == 3) {
// 已完成
            status_title.setText("交易已完成");
            status_time.setVisibility(View.GONE);

            order_bottom_layout.setVisibility(View.GONE);

        }


    }

    private void findViews() {
        status_title = findViewById(R.id.status_title);
        status_time = findViewById(R.id.status_time);
        phone = findViewById(R.id.phone);
        order_title = findViewById(R.id.order_title);
        order_price = findViewById(R.id.order_price);
        youhui_price = findViewById(R.id.youhui_price);
        zhifu_price = findViewById(R.id.zhifu_price);
        ding_dan_code = findViewById(R.id.ding_dan_code);
        da_xiong_code = findViewById(R.id.da_xiong_code);
        chuang_jian_time = findViewById(R.id.chuang_jian_time);
        good_price = findViewById(R.id.good_price);
        imgage = findViewById(R.id.imgage);
        order_bottom_layout = findViewById(R.id.order_bottom_layout);

        order_cancel = findViewById(R.id.order_cancel);
        order_cancel.setOnClickListener(this);
        order_go_pay = findViewById(R.id.order_go_pay);
        order_go_pay.setOnClickListener(this);
        order_del = findViewById(R.id.order_del);
        order_del.setOnClickListener(this);

        findViewById(R.id.new_version_back).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {


        // 更新列表

        if (view.getId() == R.id.new_version_back) {
            finish();
            return;
        }


        if (!REFRESH_SUCCESS) {
            return;
        }
        switch (view.getId()) {
            case R.id.order_cancel:
                showFengGouDialog("确认取消订单, ", new JoinZeroCallBack() {
                    @Override
                    public void sureBtn(int flag) {
                        quXiao();
                    }

                    @Override
                    public void canle() {

                    }
                });
                break;
            case R.id.order_go_pay:

                quZhifu();

                break;
            case R.id.order_del:
                showFengGouDialog("确认删除订单, ", new JoinZeroCallBack() {
                    @Override
                    public void sureBtn(int flag) {
                        shanChu();
                    }

                    @Override
                    public void canle() {

                    }
                });
                break;
        }
    }


    private void shanChu() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orderStatus", String.valueOf(virtualOrderBean.getData().getVirtualOrderDetail().getStatus()));
        mHashMap.put("orders_id", String.valueOf(virtualOrderBean.getData().getVirtualOrderDetail().getOrders_id()));
        mHashMap.put("orderProductType", String.valueOf(virtualOrderBean.getData().getOrderProductType()));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).deleteOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        EventBus.getDefault().post(new OrderTypeUpdate(type));
                        VirtualOrdersActivity.this.finish();
                    } else {
                        Toast.makeText(VirtualOrdersActivity.this, String.format("删除订单失败,请稍后再试!", data), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(VirtualOrdersActivity.this, String.format("删除订单失败,请稍后再试!", e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void quZhifu() {
        Bundle bundle = new Bundle();

        bundle.putString("btAmount", "0");
        bundle.putString("amount", virtualOrderBean.getData().getVirtualOrderDetail().getAmount());
        bundle.putString("outTradeNo", virtualOrderBean.getData().getVirtualOrderDetail().getOutTradeNo());
        bundle.putString("ordersId", virtualOrderBean.getData().getVirtualOrderDetail().getOrders_id() + "");
        bundle.putInt("isNeedPay", virtualOrderBean.getData().getVirtualOrderDetail().getIsNeedPay());
        bundle.putInt("orderProductType", 1);

        StarActivityUtil.starActivity(this, PayActivity.class, bundle);
    }

    private void quXiao() {


        Map<String, String> mHashMap = new HashMap<>();


        mHashMap.put("orderStatus", String.valueOf(virtualOrderBean.getData().getVirtualOrderDetail().getStatus()));
        mHashMap.put("orders_id", String.valueOf(virtualOrderBean.getData().getVirtualOrderDetail().getOrders_id()));
        mHashMap.put("orderProductType", String.valueOf(virtualOrderBean.getData().getOrderProductType()));


        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).cancleOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        EventBus.getDefault().post(new OrderTypeUpdate(type));
                        VirtualOrdersActivity.this.finish();
                    } else {
                        Toast.makeText(VirtualOrdersActivity.this, String.format("取消失败,请稍后再试!"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(VirtualOrdersActivity.this, String.format("取消失败,请稍后再试!", e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showFengGouDialog(String title, JoinZeroCallBack joinZeroCallBack) {

        StringBuffer stringBuffer = new StringBuffer(title);
        if (virtualOrderBean == null) {
        } else {
            try {
                stringBuffer.append("订单号：" + virtualOrderBean.getData().getVirtualOrderDetail().getOrderSn());
            } catch (Exception e) {
            }
        }
        DialogUtils.tipSearchDialog(this, 666, stringBuffer.toString(), joinZeroCallBack);
    }


}
