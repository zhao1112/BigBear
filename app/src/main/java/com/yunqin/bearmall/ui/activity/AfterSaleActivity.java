package com.yunqin.bearmall.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.AfterSaleBean;
import com.yunqin.bearmall.bean.DialogBean;
import com.yunqin.bearmall.eventbus.OrderTypeUpdate;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 */
public class AfterSaleActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView textView;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.left)
    Button left;
    @BindView(R.id.right)
    Button right;


    @BindView(R.id.goods_container)
    LinearLayout goods_container;


    @BindView(R.id.order_name)
    TextView order_name;

    @BindView(R.id.order_phone)
    TextView order_phone;

    @BindView(R.id.order_dizhi)
    TextView order_dizhi;


    @BindView(R.id.commit)
    Button commit;


    @BindView(R.id.select_)
    LinearLayout linearLayout;

    @BindView(R.id.img)
    CircleImageView img;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.qing_xuan_ze)
    TextView qing_xuan_ze;


    private String orders_id;
    private String orderProductType;

    private List<String> lefts;
    private List<String> rights;
    private List<String> curr;

    private int source_id;


    @Override
    public int layoutId() {
        return R.layout.activity_after_sale;
    }

    @Override
    public void init() {

        source_id = getIntent().getIntExtra("source_id", 0);

        lefts = new ArrayList<>();
        rights = new ArrayList<>();
        set(true);
        textView.setText("申请售后服务");
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
        }
        orders_id = bundle.getString("Orders_id");
        orderProductType = bundle.getString("orderProductType");
        initData();
        get("1");

        // TODO 动态修改文字


    }


    @OnClick({R.id.left, R.id.right, R.id.select_, R.id.toolbar_back,
            R.id.commit, R.id.go_to_address})
    public void setColor(View view) {
        switch (view.getId()) {
            case R.id.left:
                set(true);
                if (lefts.size() == 0) {
                    get("1");
                }
                curr = lefts;
                break;
            case R.id.right:
                set(false);
                if (rights.size() == 0) {
                    get("2");
                }
                curr = rights;
                break;
            case R.id.select_:
                if (curr == null) {
                    return;
                }
                showDialog();
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.commit:
                // TODO 提交
                commit();
                break;
            case R.id.go_to_address:
                Bundle bundle = new Bundle();
                bundle.putBoolean("isClick", true);
                StarActivityUtil.startActivityForResult(this, AddressActivity.class, bundle, REFUND_CODE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFUND_CODE && resultCode == REFUND_CODE) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String areaName = data.getStringExtra("areaName");
            String address = data.getStringExtra("address");

            order_name.setText(name);
            order_phone.setText(phone);
            order_dizhi.setText(areaName + " " + address);
        }
    }

    private static final int REFUND_CODE = 66;

    private int getType() {
        return lefrOrRighr ? 1 : 2;
    }


    @BindView(R.id.wenti_miaoshu)
    EditText wenti_miaoshu;

    private void commit() {

        int reasonType = -1;
        String data = qing_xuan_ze.getText().toString();
        if (data != null && data.length() > 0) {
            if (getType() == 1) {
                for (int i = 0; i < leftReason.size(); i++) {
                    if (data.equals(leftReason.get(i).getReasonValue())) {
                        reasonType = leftReason.get(i).getReasonCode();
                        break;
                    }
                }
            } else {
                for (int i = 0; i < rightReason.size(); i++) {
                    if (data.equals(rightReason.get(i).getReasonValue())) {
                        reasonType = rightReason.get(i).getReasonCode();
                        break;
                    }
                }
            }
        }

        boolean isNext = true;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                isNext = false;
                break;
            }
        }

        if (isNext) {
            Toast.makeText(this, "请选择商品", Toast.LENGTH_SHORT).show();
            return;
        }


        if (reasonType == -1) {
            Toast.makeText(this, "请选择申请原因", Toast.LENGTH_SHORT).show();
            return;
        }

        if (wenti_miaoshu.getText().toString() == null || wenti_miaoshu.getText().toString().length() <= 0) {
            Toast.makeText(this, "请填写您遇到的问题", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading();

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("dType", getType() + "");
        mHashMap.put("reasonType", reasonType + "");
        mHashMap.put("reason", wenti_miaoshu.getText().toString());
        mHashMap.put("orders_id", orders_id);
        mHashMap.put("orderProductType", orderProductType);


        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < afterSaleBeans.getData().getAfterSalesMain().getOrderItemList().size(); i++) {
                if (list.get(i).isChecked()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("orderItem_id", afterSaleBeans.getData().getAfterSalesMain().getOrderItemList().get(i).getOrderItem_id());
                    jsonObject.put("quantitiy", afterSaleBeans.getData().getAfterSalesMain().getOrderItemList().get(i).getQuantity());
                    jsonArray.put(jsonObject);
                }
            }
            mHashMap.put("itemList", jsonArray.toString());
        } catch (Exception e) {
        }


        mHashMap.put("consignee", order_name.getText().toString());
        mHashMap.put("phone", order_phone.getText().toString());
        mHashMap.put("areaName", afterSaleBeans.getData().getAfterSalesMain().getAreaName());
        mHashMap.put("address", afterSaleBeans.getData().getAfterSalesMain().getAddress());
        mHashMap.put("store_id", afterSaleBeans.getData().getAfterSalesMain().getStore_id() + "");


        commit.setEnabled(false);


        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).applyAfterSales(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                hiddenLoadingView();
                StarActivityUtil.starActivity(AfterSaleActivity.this, RefundActivity.class);
                EventBus.getDefault().post(new OrderTypeUpdate(source_id));
                finish();
            }

            @Override
            public void onNotNetWork() {
            }

            @Override
            public void onFail(Throwable e) {
                commit.setEnabled(true);
                Toast.makeText(AfterSaleActivity.this, "申请失败,请稍后重试!", Toast.LENGTH_SHORT).show();
                hiddenLoadingView();
            }
        });


    }

    private boolean lefrOrRighr = true;


    private void set(boolean visibility) {
        lefrOrRighr = visibility;
        left.setTextColor(visibility ? getResources().getColor(R.color.main_color) : getResources().getColor(R.color.product_customer_collect));
        right.setTextColor(visibility ? getResources().getColor(R.color.product_customer_collect) : getResources().getColor(R.color.main_color));
        left.setBackground(visibility ? getResources().getDrawable(R.drawable.bg_green) : getResources().getDrawable(R.drawable.bg_normal));
        right.setBackground(visibility ? getResources().getDrawable(R.drawable.bg_normal) : getResources().getDrawable(R.drawable.bg_green));
        qing_xuan_ze.setText("请选择");
    }


    private Dialog bottomDialog;

    private void showDialog() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_circle, null);
        ListView listView = contentView.findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, 0, curr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                qing_xuan_ze.setText(curr.get(position));
                bottomDialog.dismiss();
            }
        });


        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }


    AfterSaleBean afterSaleBeans;

    private void initData() {

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orders_id", orders_id);
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getAfterSalesMain(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                afterSaleBeans = new Gson().fromJson(data, AfterSaleBean.class);
                initViews(afterSaleBeans);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                System.out.println(e.toString());
            }
        });
    }


    List<CheckBox> list = new ArrayList<>();


    private void initViews(AfterSaleBean afterSaleBean) {
        list.clear();

        title.setText(Html.fromHtml(String.format("本次售后服务由<font color=#23A064>%s</font>为您提供", afterSaleBean.getData().getAfterSalesMain().getStoreName())));

        order_name.setText(afterSaleBean.getData().getAfterSalesMain().getConsignee());
        order_phone.setText(afterSaleBean.getData().getAfterSalesMain().getPhone());
        order_dizhi.setText(afterSaleBean.getData().getAfterSalesMain().getAreaName() + " " +
                afterSaleBean.getData().getAfterSalesMain().getAddress());


        Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(afterSaleBean.getData().getAfterSalesMain().getLogo()).into(img);
        name.setText(afterSaleBean.getData().getAfterSalesMain().getStoreName());


        for (int i = 0; i < afterSaleBean.getData().getAfterSalesMain().getOrderItemList().size(); i++) {

            AfterSaleBean.DataBean.AfterSalesMainBean.OrderItemListBean itemBean =
                    afterSaleBean.getData().getAfterSalesMain().getOrderItemList().get(i);

            View view = View.inflate(this, R.layout.item_shouhou_goods, null);

            View view_ = view.findViewById(R.id.view_);

            TextView shou_hou = view.findViewById(R.id.shou_hou);
            ImageView goods_pic = view.findViewById(R.id.goods_pic);
            TextView goods_name = view.findViewById(R.id.goods_name);
            TextView goods_color = view.findViewById(R.id.goods_color);
            TextView goods_price = view.findViewById(R.id.goods_price);
            TextView goods_count = view.findViewById(R.id.goods_count);

            CheckBox checkBox = view.findViewById(R.id.check_box);
            list.add(checkBox);

            if (itemBean.getOrderItemStatus() == 9) {
//                view_.setVisibility(View.GONE);
                shou_hou.setVisibility(View.INVISIBLE);

                view.setOnClickListener(v -> checkBox.setChecked(!checkBox.isChecked()));
            } else {
                shou_hou.setVisibility(View.VISIBLE);
//                view_.setVisibility(View.VISIBLE);
                checkBox.setChecked(false);
                checkBox.setEnabled(false);
            }

            StringBuffer specificationItems = new StringBuffer();
            for (String content : itemBean.getSpecifications()) {
                specificationItems.append(content).append(" ");
            }

            Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(itemBean.getThumbnail()).into(goods_pic);
            goods_name.setText(itemBean.getItemName());
            goods_color.setText(specificationItems);

            if (itemBean.getPaymentModel() == 0) {
                goods_price.setText(String.format("￥%s", itemBean.getPrice()));
            } else {
                goods_price.setText(String.format("￥%s+BC%s", itemBean.getPartPrice(), itemBean.getPartBtAmount()));
            }
            goods_count.setText("X" + itemBean.getQuantity());
            goods_container.addView(view);
        }


    }


    List<DialogBean.DataBean.ReasonBean> leftReason;
    List<DialogBean.DataBean.ReasonBean> rightReason;


    private void get(String type) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("dtype", type);
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getApplyReason(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {

                DialogBean dialogBean = new Gson().fromJson(data, DialogBean.class);

                for (int i = 0; i < dialogBean.getData().getReason().size(); i++) {
                    if (type.equals("1")) {
                        leftReason = dialogBean.getData().getReason();
                        lefts.add(dialogBean.getData().getReason().get(i).getReasonValue());
                        curr = lefts;
                    } else {
                        rightReason = dialogBean.getData().getReason();
                        rights.add(dialogBean.getData().getReason().get(i).getReasonValue());
                        curr = rights;
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


}
