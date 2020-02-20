package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newversions.NewOrderBean;
import com.newversions.NewOrderChildBean;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MoreTypeViewAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.Coupon;
import com.yunqin.bearmall.ui.activity.contract.ConfirmActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.ConfirmActivityPresenter;
import com.yunqin.bearmall.util.NoSlideLinearLayoutManager;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 * 确认订单
 * <p>
 * <p>
 * 需要参数：
 * <p>
 * <p>
 * <p>
 * 店铺信息{商品,店铺名称,logo}
 * 商品信息{ 名称,规格,价格,数量,支付类型,图片,商品ID, SKU_ID等...}
 */
public class ConfirmActivity extends BaseActivity implements ConfirmActivityContract.UI {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.show_address)
    LinearLayout show_address;
    @BindView(R.id.add_address)
    LinearLayout add_address;
    @BindView(R.id.toolbar_title)
    TextView titleTextView;
    @BindView(R.id.tangguo)
    TextView tangguo;
    @BindView(R.id.yunfei)
    TextView yunfei;
    @BindView(R.id.rmb)
    TextView rmb;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.dizhi)
    TextView dizhi;
    @BindView(R.id.total_money)
    TextView total_money;
    @BindView(R.id.show_id_number_view)
    LinearLayout showIdNumberView;
    @BindView(R.id.input_id_number_view)
    LinearLayout inputIdNumberView;
    @BindView(R.id.input_id_number_edit)
    EditText inputIdNumberEditText;
    @BindView(R.id.id_number_commit)
    Button idNumberCommit;
    @BindView(R.id.id_number_tv)
    TextView idNumberTextView;
    @BindView(R.id.new_zhekou_jine)
    TextView new_zhekou_jine;
    @BindView(R.id.order_coupon_tv)
    TextView order_coupon_tv;
    @BindView(R.id.id_number_update)
    ImageView idNumberUpdate;

    private boolean showHaiWaiGou = false;
    private boolean yanZhengIdNumber = false;
    private String receiver_id = "";
    public static final String INTENT_DATA = "data";
    private static final int MO_REN_ZHI = 0;// 默认值
    private static final int WU_JING_WAI = 1;// 没有境外商品
    private static final int YOU_JING_WAI_WAIT = 2;// 有境外商品，未验证
    private static final int YOU_JING_WAI_SUCCESS = 3;// 有境外商品，验证通过
    private int hiType = MO_REN_ZHI;
    private static final int REFUND_CODE = 66;
    private static final int YOUHUIQUANREFUND_CODE = 666;
    private ConfirmActivityContract.Presenter presenter;
    // 修改接口添加字段 ** 开始
    private String ORDER_TYPE;// 0 普通订单  3 砍价订单
    private String address = "";// 地址
    private String consignee = "";// 收货人
    private String areaName = "";// 地区
    private String mPhone = "";// 电话
    private String bargainRecord_id = "";// 砍价记录ID
    private String userLog_id = "";// 优惠券ID
    // 修改接口添加字段 ** 结束
    private boolean isAddAddress = false;
    private int order_type_int;
    private AddressListBean.DataBean mDataBean = null;
    private List<NewOrderBean> list;

    @Override
    public int layoutId() {
        return R.layout.activity_confirm;
    }

    @Override
    public void init() {
        titleTextView.setText("确认订单");
        presenter = new ConfirmActivityPresenter(this, this);
        initPaymentMethod_id();

        list = (List<NewOrderBean>) getIntent().getSerializableExtra(INTENT_DATA);
        bargainRecord_id = getIntent().getStringExtra("bargainRecord_id");
        if (bargainRecord_id == null) {
            bargainRecord_id = "";
        }
        ORDER_TYPE = getIntent().getStringExtra("type_order");

        String order_type = getIntent().getStringExtra("order_type");
        if ("Trolley".equals(order_type)) {
            // 来自购物车
            order_type_int = 1;
        } else {
            order_type_int = 0;
        }
        try {
            mDataBean = (AddressListBean.DataBean) getIntent().getSerializableExtra("address");
            address = mDataBean.getAddress();
            consignee = mDataBean.getConsignee();
            areaName = mDataBean.getAreaName();
            mPhone = mDataBean.getPhone();
        } catch (Exception e) {
            mDataBean = null;
        }
        if (list == null) {
            finish();
            return;
        }
        initOrder();
        initLocation();
        initCoupon();
    }

    private void initCoupon() {

        Map<String, String> mHashMap = new HashMap<>();

        mHashMap.put("queryType", "0");
        mHashMap.put("ticketType", "0");

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberTicketDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                int mCouponCount = jsonObject1.optInt("totalCount");

                if (mCouponCount > 0) {
                    order_coupon_tv.setText(String.format("%d张可用", mCouponCount));
                } else {
                    order_coupon_tv.setText("暂无可用");
                }
            }

            @Override
            public void onNotNetWork() {
                order_coupon_tv.setText("暂无可用");
            }

            @Override
            public void onFail(Throwable e) {
                order_coupon_tv.setText("暂无可用");
            }
        });


    }

    private void initOrder() {
        // TODO 获取当前订单类型
        Map<String, String> mHashMap = new HashMap<>();
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getChildBeans().size(); j++) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("product_id", list.get(i).getChildBeans().get(j).getProductId());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                }
            }
        }

        mHashMap.put("productIdListStr", jsonArray.toString());
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).isCrossBorder(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                int is = jsonObject1.optInt("isCrossBorder");
                if (is == 0) {
                    hiType = WU_JING_WAI;
                    initLinear(false);
                } else {
                    hiType = YOU_JING_WAI_WAIT;
                    initLinear(true);
                }
            }

            @Override
            public void onNotNetWork() {
                hiType = MO_REN_ZHI;
            }

            @Override
            public void onFail(Throwable e) {
                hiType = MO_REN_ZHI;
            }
        });
    }


    private void initLinear(boolean show) {
        showHaiWaiGou = show;
        inputIdNumberView.setVisibility(show ? View.VISIBLE : View.GONE);
        inputIdNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    idNumberCommit.setEnabled(true);
                } else {
                    idNumberCommit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * 获取支付方式列表
     */

    private String paymentMethod_id = "1";


    private void initPaymentMethod_id() {


//        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getPaymentMethod(), new RetrofitApi.IResponseListener() {
//            @Override
//            public void onSuccess(String data) throws JSONException {
//                JSONObject jsonObject = new JSONObject(data);
//                JSONArray jsonArray = jsonObject.optJSONArray("data");
//                JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
//                paymentMethod_id = jsonObject2.optString("paymentMethod_id");
//            }
//
//            @Override
//            public void onNotNetWork() {
//
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//
//            }
//        });


    }


    private void initLocation() {

        if (mDataBean != null) {
            isAddAddress = true;
            show_address.setVisibility(View.VISIBLE);
            add_address.setVisibility(View.GONE);

            name.setText(mDataBean.getConsignee());
            phone.setText(mDataBean.getPhone());
            dizhi.setText(mDataBean.getAreaName() + " " + mDataBean.getAddress());

            receiver_id = mDataBean.getReceiver_id() + "";


            presenter.start(ORDER_TYPE, areaName, bargainRecord_id, userLog_id, list);


            return;
        }


        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getDefaultReceiver(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                int hasDefault = jsonObject1.optInt("hasDefault");
                if (hasDefault == 1) {

                    isAddAddress = true;

                    show_address.setVisibility(View.VISIBLE);
                    add_address.setVisibility(View.GONE);

                    JSONObject jsonObject2 = jsonObject1.optJSONObject("receiver");


                    address = jsonObject2.optString("address");
                    consignee = jsonObject2.optString("consignee");
                    areaName = jsonObject2.optString("areaName");
                    mPhone = jsonObject2.optString("phone");


                    name.setText(consignee);
                    phone.setText(mPhone);
                    dizhi.setText(areaName + " " + address);

                    receiver_id = jsonObject2.optInt("receiver_id") + "";

                    presenter.start(ORDER_TYPE, areaName, bargainRecord_id, userLog_id, list);

                } else {
                    presenter.start(ORDER_TYPE, areaName, bargainRecord_id, userLog_id, list);
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

                presenter.start(ORDER_TYPE, areaName, bargainRecord_id, userLog_id, list);

            }
        });
    }


    @Override
    public void attachAdapter(MoreTypeViewAdapter adapter) {
        mRecyclerView.setLayoutManager(new NoSlideLinearLayoutManager(ConfirmActivity.this));
        mRecyclerView.setAdapter(adapter);
    }

    private String tangGuoP = "";

    @Override
    public void tangGuo(String tangguo) {
        tangGuoP = tangguo;
        this.tangguo.setText("BC" + tangguo);
    }

    private String yunFeiP = "";

    @Override
    public void yunFei(String yunfei) {
        yunFeiP = yunfei;
        this.yunfei.setText("¥" + yunfei);
    }

    private String moneyP = "";

    @Override
    public void money(String rmb) {
        moneyP = rmb;
        this.rmb.setText("¥" + rmb);
    }

    private String totalMoneyP = "";

    @Override
    public void totalMoney(String totalmoney) {
        totalMoneyP = totalmoney;

        this.total_money.setText("应付：¥" + totalmoney);
    }


    @Override
    public void youhuiMoney(String totalmoney) {
        new_zhekou_jine.setText("-¥" + totalmoney);
    }

    @Override
    public void hideLoad(boolean error, int index) {
        hiddenLoadingView();
    }

    @Override
    public void showLoad() {
        showLoading();
    }


    private long lastTime = 0;


    @OnClick({R.id.new_zhekouquan, R.id.toolbar_back, R.id.add_address, R.id.show_address, R.id.go_pay,
            R.id.id_number_update, R.id.id_number_commit})
    public void select_(View view) {
        switch (view.getId()) {

            case R.id.new_zhekouquan:

                CouponActivity.startCouponActivityWithResult(this, YOUHUIQUANREFUND_CODE, 0);
// 优惠券

                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.add_address:
                Bundle bundle1 = new Bundle();
                bundle1.putBoolean("isClick", true);
                StarActivityUtil.startActivityForResult(this, AddressActivity.class, bundle1, REFUND_CODE);
                break;
            case R.id.show_address:
                Bundle bundle = new Bundle();
                bundle.putBoolean("isClick", true);
                StarActivityUtil.startActivityForResult(this, AddressActivity.class, bundle, REFUND_CODE);
                break;
            case R.id.go_pay:
                if (System.currentTimeMillis() - lastTime > 1000) {
                    lastTime = System.currentTimeMillis();
                } else {
                    return;
                }
                try {
                    if (!isAddAddress) {
                        Toast.makeText(this, "请先选择收货地址", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (hiType == MO_REN_ZHI) {
                        initOrder();
                        Toast.makeText(this, "网络错误,请重试!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (hiType == YOU_JING_WAI_WAIT) {
                        Toast.makeText(this, "请填写身份证号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 确认订单，下单接口
                    Map<String, String> mHashMap = new HashMap<>();
                    try {
                        mHashMap.put("idCard", inputIdNumberEditText.getText().toString());
                    } catch (Exception e) {

                    }
                    mHashMap.put("receiver_id", receiver_id);
                    mHashMap.put("paymentMethod_id", paymentMethod_id);
                    mHashMap.put("orderType", ORDER_TYPE);
                    mHashMap.put("address", address);
                    mHashMap.put("consignee", consignee);
                    mHashMap.put("areaName", areaName);
                    mHashMap.put("phone", mPhone);
                    mHashMap.put("bargainRecord_id", bargainRecord_id + "");
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < list.size(); i++) {
                        NewOrderBean newOrderBean = list.get(i);
                        for (int j = 0; j < newOrderBean.getChildBeans().size(); j++) {
                            try {
                                NewOrderChildBean newOrderChildBean = newOrderBean.getChildBeans().get(j);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("product_id", newOrderChildBean.getProductId());
                                jsonObject.put("sku_id", newOrderChildBean.getSkuId());
                                jsonObject.put("quantity", newOrderChildBean.getCount());
                                jsonObject.put("isMsp", newOrderChildBean.getiMap());
                                jsonArray.put(jsonObject);
                            } catch (Exception e) {
                            }
                        }
                    }
                    mHashMap.put("amount", totalMoneyP + "");
                    mHashMap.put("btAmount", tangGuoP);
                    mHashMap.put("orderItemList", jsonArray.toString());
                    mHashMap.put("isFromCart", order_type_int + "");
                    mHashMap.put("usersTicketLog_id", userLog_id);

                    RetrofitApi.request(this, RetrofitApi.createApi(Api.class).unifiedOrder(mHashMap), new RetrofitApi.IResponseListener() {
                        @Override
                        public void onSuccess(String data) throws JSONException {
                            hiddenLoadingView();
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                            Bundle bundle2 = new Bundle();
                            bundle2.putString("btAmount", jsonObject1.optString("btAmount"));
                            bundle2.putString("amount", jsonObject1.optString("amount"));
                            bundle2.putString("outTradeNo", jsonObject1.optString("outTradeNo"));
                            bundle2.putString("ordersId", jsonObject1.optString("ordersId"));
                            bundle2.putInt("isNeedPay", jsonObject1.optInt("isNeedPay"));
                            bundle2.putInt("orderProductType", jsonObject1.optInt("orderProductType"));

                            StarActivityUtil.starActivity(ConfirmActivity.this, PayActivity.class, bundle2);
                            ConfirmActivity.this.finish();
                        }

                        @Override
                        public void onNotNetWork() {

                        }

                        @Override
                        public void onFail(Throwable e) {
                            hiddenLoadingView();
                        }
                    });
                } catch (Exception e) {

                }
                break;
            case R.id.id_number_update:
                // TODO 重新输入身份证号

                inputIdNumberView.setVisibility(View.VISIBLE);
                showIdNumberView.setVisibility(View.GONE);
                inputIdNumberEditText.setText("");

                break;

            case R.id.id_number_commit:
                // TODO 提交验证身份证号

                goCommit();

                break;

            default:
                break;
        }
    }


    private void goCommit() {
        yanZhengIdNumber = false;

        showLoading();
        hiddenKeyboard();
        String idNumber = inputIdNumberEditText.getText().toString();

        if (consignee == null) {
            hiddenLoadingView();
            Toast.makeText(ConfirmActivity.this, "请先选择地址", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("idCard", idNumber);
        mHashMap.put("name", consignee);

        RetrofitApi.request(ConfirmActivity.this, RetrofitApi.createApi(Api.class).idCardModify(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                String str = "认证失败";
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                String status = jsonObject1.optString("status", "");
                if (status.equals("01")) {
                    inputIdNumberView.setVisibility(View.GONE);
                    showIdNumberView.setVisibility(View.VISIBLE);
                    idNumberTextView.setText(HideIdNumber(idNumber));
                    str = "认证通过！";
                    yanZhengIdNumber = true;
                    hiType = YOU_JING_WAI_SUCCESS;
                } else if (status.equals("02")) {
                    hiType = YOU_JING_WAI_WAIT;
                    str = "实名认证不通过！";
                } else if (status.equals("202")) {
                    hiType = YOU_JING_WAI_WAIT;
                    str = "无法验证！";
                } else if (status.equals("204")) {
                    hiType = YOU_JING_WAI_WAIT;
                    str = "姓名格式不正确！";
                } else if (status.equals("205")) {
                    hiType = YOU_JING_WAI_WAIT;
                    str = "身份证格式不正确！";
                }
                Toast.makeText(ConfirmActivity.this, str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                hiType = YOU_JING_WAI_WAIT;
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                hiType = YOU_JING_WAI_WAIT;
            }
        });
    }


    /**
     * 隐藏中间字符为*
     *
     * @param idNumber
     * @return
     */
    public static String HideIdNumber(String idNumber) {
        StringBuilder sb = new StringBuilder();
        int length = idNumber.length();
        if (!TextUtils.isEmpty(idNumber) && length > 10) {
            for (int i = 0; i < length; i++) {
                char c = idNumber.charAt(i);
                if (i > 3 && i < length - 4) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFUND_CODE && resultCode == REFUND_CODE) {
            show_address.setVisibility(View.VISIBLE);
            add_address.setVisibility(View.GONE);
            isAddAddress = true;

            consignee = data.getStringExtra("name");
            mPhone = data.getStringExtra("phone");
            areaName = data.getStringExtra("areaName");
            address = data.getStringExtra("address");
            receiver_id = data.getStringExtra("receiver_id");

            this.name.setText(consignee);
            this.phone.setText(mPhone);
            this.dizhi.setText(areaName + " " + address);

            presenter.refresh(ORDER_TYPE, areaName, bargainRecord_id, userLog_id, list);

        }

        if (requestCode == YOUHUIQUANREFUND_CODE && resultCode == Activity.RESULT_OK) {
            Coupon coupon = data.getParcelableExtra("COUPON");
            userLog_id = String.valueOf(coupon.getUsersTicket_id());
            // TODO 得到优惠券对象

            presenter.refresh(ORDER_TYPE, areaName, bargainRecord_id, userLog_id, list);

        }


    }
}
