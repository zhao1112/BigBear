package com.yunqin.bearmall.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.WuLiuAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.RefundDetailsBean;
import com.yunqin.bearmall.util.DialogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 售后退款
 *
 * @author Master
 */

public class RefundDetailsActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView textView;

    @BindView(R.id.th_type)
    TextView th_type;// 退款成功

    @BindView(R.id.tk_shijian)
    TextView tk_shijian;// 退款时间

    @BindView(R.id.tk_jine)
    TextView tk_jine;// 退款总金额

    @BindView(R.id.tk_yinhangka)
    TextView tk_yinhangka;// 退款银行卡


    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;


    @BindView(R.id.group)
    LinearLayout group;// 商品Group

    // ↓↓↓↓退款信息↓↓↓↓

    @BindView(R.id.tuikuanyuanyin)
    TextView tuikuanyuanyin;

    @BindView(R.id.tuikuanjine)
    TextView tuikuanjine;

    @BindView(R.id.shenqingshijian)
    TextView shenqingshijian;

    @BindView(R.id.dingdanbianhao)
    TextView dingdanbianhao;

    private String afterSales_id;


    @BindView(R.id.from_textView)
    TextView from_textView;


    @Override
    public int layoutId() {
        return R.layout.activity_refund_details;
    }

    @Override
    public void init() {
        textView.setText("退货/售后");
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        afterSales_id = bundle.getString("afterSales_id");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initData();
    }

    RefundDetailsBean mRefundDetailsBean;


    private void initData() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("afterSales_id", afterSales_id);
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getAfterSalesDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                mRefundDetailsBean = new Gson().fromJson(data, RefundDetailsBean.class);
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

        if (mRefundDetailsBean.getData().getAfterSalesDetails().getStatus() == 0) {
            th_type.setText("待审核");
            textView.setText("待审核");
//            mRecyclerView.setVisibility(View.GONE);
        } else if (mRefundDetailsBean.getData().getAfterSalesDetails().getStatus() == 1) {
            th_type.setText("审核通过");
            textView.setText("审核通过");
        } else if (mRefundDetailsBean.getData().getAfterSalesDetails().getStatus() == 2) {
            th_type.setText("审核失败");
            textView.setText("审核失败");
//            mRecyclerView.setVisibility(View.GONE);
        } else if (mRefundDetailsBean.getData().getAfterSalesDetails().getStatus() == 3) {
            th_type.setText("退款成功");
            textView.setText("退款成功");
        } else if (mRefundDetailsBean.getData().getAfterSalesDetails().getStatus() == 4) {
            th_type.setText("已取消");
            textView.setText("已取消");
//            mRecyclerView.setVisibility(View.GONE);
        }

        mRecyclerView.setAdapter(new WuLiuAdapter(RefundDetailsActivity.this, mRefundDetailsBean.getData().getAfterSalesDetails().getLogDetails()));

        String from = "";
        if (mRefundDetailsBean.getData().getAfterSalesDetails().getPayType() == 1) {
            from = "微信";
        } else if (mRefundDetailsBean.getData().getAfterSalesDetails().getPayType() == 2) {
            from = "支付宝";
        } else {

        }

        from_textView.setText("应退款金额");
//        from_textView.setText("返回" + from);


        tk_shijian.setText(mRefundDetailsBean.getData().getAfterSalesDetails().getApplyDate());
        tk_jine.setText("¥" + mRefundDetailsBean.getData().getAfterSalesDetails().getRefundAmount());
//        tk_yinhangka.setText("¥" + mRefundDetailsBean.getData().getAfterSalesDetails().getRefundAmount());
        tk_yinhangka.setText("¥" + mRefundDetailsBean.getData().getAfterSalesDetails().getRefundableAmount());

        tuikuanyuanyin.setText("退款原因：" + mRefundDetailsBean.getData().getAfterSalesDetails().getReason());
        tuikuanjine.setText("退款金额：¥" + mRefundDetailsBean.getData().getAfterSalesDetails().getRefundableAmount());
//        tuikuanjine.setText("退款金额：¥" + mRefundDetailsBean.getData().getAfterSalesDetails().getRefundAmount());
        dingdanbianhao.setText("订单编号：" + mRefundDetailsBean.getData().getAfterSalesDetails().getSn());
        shenqingshijian.setText("申请时间：" + mRefundDetailsBean.getData().getAfterSalesDetails().getApplyDate());


        for (int i = 0; i < mRefundDetailsBean.getData().getAfterSalesDetails().getAfterSalesItem().size(); i++) {

            RefundDetailsBean.DataBean.AfterSalesDetailsBean.AfterSalesItemBean itemBean =
                    mRefundDetailsBean.getData().getAfterSalesDetails().getAfterSalesItem().get(i);

            View view = View.inflate(this, R.layout.item_goods, null);
            ImageView goods_pic = view.findViewById(R.id.goods_pic);
            TextView goods_name = view.findViewById(R.id.goods_name);
            TextView goods_color = view.findViewById(R.id.goods_color);
            TextView goods_price = view.findViewById(R.id.goods_price);
            TextView goods_count = view.findViewById(R.id.goods_count);
            StringBuffer specificationItems = new StringBuffer();
            for (String content : itemBean.getSpecifications()) {
                specificationItems.append(content).append(" ");
            }
            Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_shopcar_product)).load(itemBean.getThumbnail()).into(goods_pic);
            goods_name.setText(itemBean.getOrderitemName());
            goods_color.setText(specificationItems);


            goods_price.setText(String.format("¥%s+BC%s", itemBean.getPrice(), itemBean.getBtAmount()));

            goods_count.setText(String.format("X%d", itemBean.getQuantity()));
            group.addView(view);
        }


//        jindu_view.setVisibility(View.GONE);
//
//        for (int i = 0; i < mRefundDetailsBean.getData().getAfterSalesDetails().getLogDetails().size(); i++) {
//            RefundDetailsBean.DataBean.AfterSalesDetailsBean.LogDetailsBean bean =
//                    mRefundDetailsBean.getData().getAfterSalesDetails().getLogDetails().get(i);
//            if (bean.getType() == 1) {
//                jindu_view.setVisibility(View.VISIBLE);
//                a_shijian.setText(bean.getCreatedDate());
//                view_1.setVisibility(View.GONE);
//                view_2.setVisibility(View.GONE);
//                b.setVisibility(View.GONE);
//                c.setVisibility(View.GONE);
//
//
//            } else if (bean.getType() == 2) {
//                jindu_view.setVisibility(View.VISIBLE);
//                b_shijian.setText(bean.getCreatedDate());
//                view_1.setVisibility(View.VISIBLE);
//                view_2.setVisibility(View.GONE);
//                b.setVisibility(View.VISIBLE);
//                c.setVisibility(View.GONE);
//
//            } else if (bean.getType() == 3) {
//                jindu_view.setVisibility(View.VISIBLE);
//                c_shijian.setText(bean.getCreatedDate());
//                view_1.setVisibility(View.VISIBLE);
//                view_2.setVisibility(View.VISIBLE);
//                b.setVisibility(View.VISIBLE);
//                c.setVisibility(View.VISIBLE);
//            }
//        }


    }


    @OnClick({R.id.toolbar_back, R.id.call_phone})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.call_phone:
                // TODO 拨打电话

                if (mRefundDetailsBean != null && mRefundDetailsBean.getData() != null && mRefundDetailsBean.getData().getAfterSalesDetails() != null) {
                    final String phone = mRefundDetailsBean.getData().getAfterSalesDetails().getServicePhone();
                    if (phone != null) {
                        DialogUtils.showCallPhoneDialog(this, phone);
                    }
                }


//                new AlertDialog.Builder(this)
//                        .setTitle("是否拨打电话")
//                        .setMessage(phone)
//                        .setNegativeButton("确定", (dialog, which) -> {
//                            Intent intent = new Intent(Intent.ACTION_DIAL);
//                            Uri data = Uri.parse("tel:" + phone);
//                            intent.setData(data);
//                            startActivity(intent);
//                        })
//                        .setNeutralButton("取消", (dialog, which) -> {
//                        })
//                        .show();
                break;
        }

    }

}
