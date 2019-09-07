package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.OrderDerails;
import com.yunqin.bearmall.eventbus.OrderTypeUpdate;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.PriceView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {


    /**
     * 头像
     */
    @BindView(R.id.detail_img)
    ImageView detail_img;
    /**
     * 订单状态
     * 等待付款...
     */
    @BindView(R.id.order_type)
    TextView order_type;
    /**
     * 剩余时间
     */
    @BindView(R.id.order_shijian)
    TextView order_shijian;

    @BindView(R.id.order_name)
    TextView order_name;

    @BindView(R.id.order_phone)
    TextView order_phone;
    @BindView(R.id.order_dizhi)
    TextView order_dizhi;
    @BindView(R.id.order_count_layout)
    LinearLayout order_count_layout;
    @BindView(R.id.bt_jiage)
    TextView bt_jiage;
    @BindView(R.id.yunfei)
    TextView yunfei;
    @BindView(R.id.rmb)
    TextView rmb;
    @BindView(R.id.xufukuan)
    TextView xufukuan;
    @BindView(R.id.fantangguo)
    TextView fantangguo;
    @BindView(R.id.order_bianhao)
    TextView order_bianhao;
    @BindView(R.id.bear_hao)
    TextView bear_hao;
    @BindView(R.id.order_time)
    TextView order_time;
    @BindView(R.id.toolbar_title)
    TextView mTextView;


    @BindView(R.id.custom_recommend_view)
    CustomRecommendView mCustomRecommendView;


    @BindView(R.id.order_apply_after_sale)
    Button order_apply_after_sale;

    @BindView(R.id.order_check_logistics)
    Button order_check_logistics;

    @BindView(R.id.order_appraise)
    Button order_appraise;

    @BindView(R.id.order_confirm_receipt)
    Button order_confirm_receipt;

    @BindView(R.id.order_cancel)
    Button order_cancel;

    @BindView(R.id.order_go_pay)
    Button order_go_pay;

    @BindView(R.id.order_del)
    Button order_del;

    @BindView(R.id.order_buy_again)
    Button order_buy_again;

    @BindView(R.id.order_check_info)
    Button order_check_info;


    @BindView(R.id.wuliu_view)
    LinearLayout wuliu_view;

    @BindView(R.id.wuliu_top_view)
    LinearLayout wuliu_top_view;

    @BindView(R.id.wuliu_center_view)
    LinearLayout wuliu_center_view;

    @BindView(R.id.wuliu_bottom_view)
    LinearLayout wuliu_bottom_view;

    @BindView(R.id.wuliu_top_view_text_view)
    TextView wuliu_top_view_text_view;

    @BindView(R.id.wuliu_bottom_view_text_view)
    TextView wuliu_bottom_view_text_view;

    @BindView(R.id.wuliu_center_view_text_view)
    TextView wuliu_center_view_text_view;


    @BindView(R.id.brand_icon)
    CircleImageView brand_icon;

    private int orders_id;


    public static void start(Context context, int orders_id, int ut_id, int orderProductType) {
        Bundle bundle = new Bundle();
        bundle.putInt("orders_id", orders_id);
        bundle.putInt("from_id", ut_id);
        bundle.putInt("orderProductType", orderProductType);
        StarActivityUtil.starActivity((Activity) context, OrderDetailsActivity.class, bundle);
    }


    @BindView(R.id.bottom_v)
    View bottom_v;

    @BindView(R.id.new_order_type_)
    LinearLayout new_order_type_;


    private int UPDATE_TYPE = 0;


    @Override
    public int layoutId() {
        return R.layout.details_order_layout;
    }

    @Override
    public void init() {
        mTextView.setText("订单详情");
        initData();
    }


    private void updateView() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orders_id", String.valueOf(orders_id));
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getOrderDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                OrderDerails orderDerails;
                try {
                    orderDerails = new Gson().fromJson(data, OrderDerails.class);
                    if (orderDerails.getCode() == 1) {
                        initViews(orderDerails);
                    } else {
                        Toast.makeText(OrderDetailsActivity.this, "获取详情失败,请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JsonSyntaxException e) {
                    orderDerails = null;
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(OrderDetailsActivity.this, "获取详情失败,请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int orderProductType;

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orders_id = bundle.getInt("orders_id", -1);
            UPDATE_TYPE = bundle.getInt("from_id");
            orderProductType = bundle.getInt("orderProductType");
        }
        if (orders_id == -1) {
            Toast.makeText(this, "出错了，请退出后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orders_id", String.valueOf(orders_id));
        mHashMap.put("orderProductType", String.valueOf(orderProductType));
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getOrderDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                OrderDerails orderDerails;
                try {
                    orderDerails = new Gson().fromJson(data, OrderDerails.class);
                    if (orderDerails.getCode() == 1) {
                        initViews(orderDerails);
                    } else {
                        Toast.makeText(OrderDetailsActivity.this, "获取详情失败,请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JsonSyntaxException e) {
                    orderDerails = null;
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(OrderDetailsActivity.this, "获取详情失败,请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private OrderDerails mOrderDerails;


    private void initViews(OrderDerails orderDerails) {

        if (orderProductType == 1) {
            new_order_type_.setVisibility(View.GONE);
        }


        mOrderDerails = orderDerails;
        setViewVisibility(orderDerails);
        for (int i = 0; i < orderDerails.getData().getOrderDetail().getOrderItemList().size(); i++) {
            View view = View.inflate(this, R.layout.item_goods, null);
            OrderDerails.DataBean.OrderDetailBean.OrderItemListBean bean =
                    orderDerails.getData().getOrderDetail().getOrderItemList().get(i);
            StringBuffer specifications = new StringBuffer();
            for (String content : bean.getSpecifications()) {
                specifications.append(content).append(" ");
            }
            TextView goods_name = view.findViewById(R.id.goods_name);
            TextView goods_color = view.findViewById(R.id.goods_color);
            TextView goods_price = view.findViewById(R.id.goods_price);
            TextView goods_count = view.findViewById(R.id.goods_count);
            ImageView goods_pic = view.findViewById(R.id.goods_pic);
            goods_name.setText(bean.getItemName());
            goods_color.setText(specifications.toString());
            String price = String.format("¥%s", bean.getPrice());
            String priceBt = String.format("¥%s+BC%s", bean.getPartPrice(), bean.getPartBtAmount());
            goods_price.setText(bean.getPaymentModel() == 0 ? price : priceBt);
            goods_count.setText(String.format("X%d", bean.getQuantity()));
            Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_shopcar_product)).load(bean.getThumbnail()).into(goods_pic);
            order_count_layout.addView(view);
        }


        mCustomRecommendView.hideTopLayout();
        mCustomRecommendView.setManager(new GridLayoutManager(this, 2));
        mCustomRecommendView.start(this);


    }

    private static final int DENGDAIFUKUAN = 0;
    private static final int DENGDAIFAHUO = 2;
    private static final int YIFAHUO = 3;
    private static final int YISHOUHUO = 4;
    private static final int YIWANCHENG = 5;
    private static final int YISHIBAI = 6;
    private static final int YIQUXIAO = 7;
    private static final int YIJUJUE = 8;

    private OrderDerails orderDerails;

    private void setViewVisibility(OrderDerails orderDerails) {
        hideButton();
        this.orderDerails = orderDerails;
        int status = orderDerails.getData().getOrderDetail().getStatus();

        if (status == DENGDAIFUKUAN) {
            if (orderDerails.getData().getOrderDetail().getIsExpire() == 0) {

                order_cancel.setVisibility(View.VISIBLE);
                order_go_pay.setVisibility(View.VISIBLE);
                // 设置监听事件
                order_cancel.setOnClickListener(this);
                order_go_pay.setOnClickListener(this);

                order_type.setText("等待买家付款");
                order_shijian.setText(String.format("剩%s自动关闭", orderDerails.getData().getOrderDetail().getOrderRestTime()));

                wuliu_view.setVisibility(View.GONE);
                detail_img.setBackgroundResource(R.drawable.icon_order_daifukuan);
            } else {

                detail_img.setBackgroundResource(R.drawable.icon_order_daifukuan);

                wuliu_view.setVisibility(View.GONE);
                order_type.setText("订单已过期");
                order_shijian.setVisibility(View.GONE);

                order_del.setVisibility(View.VISIBLE);
                order_del.setOnClickListener(this);

            }


        } else if (status == DENGDAIFAHUO) {
            wuliu_view.setVisibility(View.VISIBLE);
            order_buy_again.setVisibility(View.VISIBLE);


            if (orderDerails.getData().getOrderDetail().getIsAllowAfterSales() == 1) {
                order_apply_after_sale.setVisibility(View.VISIBLE);
            } else {
                order_apply_after_sale.setVisibility(View.GONE);
            }


            order_buy_again.setOnClickListener(this);
            order_apply_after_sale.setOnClickListener(this);

            wuliu_view.setVisibility(View.GONE);

            detail_img.setBackgroundResource(R.drawable.icon_order_daifahuo);

            order_type.setText("买家已付款");
            order_shijian.setVisibility(View.GONE);


        } else if (status == YIFAHUO) {
            wuliu_view.setVisibility(View.VISIBLE);
            OrderDerails.DataBean.OrderDetailBean.OrderShippingListBean orderShippingListBean = null;
            try {
                orderShippingListBean =
                        orderDerails.getData().getOrderDetail().getOrderShippingList().get(0);
            } catch (Exception e) {
                wuliu_view.setVisibility(View.GONE);
            }
            if (orderShippingListBean != null) {
                wuliu_top_view_text_view.setText(String.format("你的订单已由%s检获完毕,待出库已交付%s", orderDerails.getData().getStore().getStore_name(), orderShippingListBean.getDeliveryCorp()));
                wuliu_center_view_text_view.setText(orderShippingListBean.getTrackingNo());
                wuliu_bottom_view_text_view.setText(orderShippingListBean.getCreatedDate());
            }


            if (orderDerails.getData().getOrderDetail().getIsAllowAfterSales() == 1) {
                order_apply_after_sale.setVisibility(View.VISIBLE);
            } else {
                order_apply_after_sale.setVisibility(View.GONE);
            }


            order_check_logistics.setVisibility(View.VISIBLE);
            order_confirm_receipt.setVisibility(View.VISIBLE);
            order_buy_again.setVisibility(View.VISIBLE);


            order_apply_after_sale.setOnClickListener(this);
            order_check_logistics.setOnClickListener(this);
            order_confirm_receipt.setOnClickListener(this);
            order_buy_again.setOnClickListener(this);

            detail_img.setBackgroundResource(R.drawable.icon_order_daishouhuo);

            order_type.setText("卖家已发货");
            order_shijian.setText(String.format("剩%s自动确认", orderDerails.getData().getOrderDetail().getReceiveRestTime()));

        } else if (status == YISHOUHUO || status == YIWANCHENG) {

            if (orderDerails.getData().getOrderDetail().isReviewed()) {
                order_appraise.setVisibility(View.GONE);
            } else {
                order_appraise.setVisibility(View.VISIBLE);
            }


            if (orderDerails.getData().getOrderDetail().getIsAllowAfterSales() == 1) {
                order_apply_after_sale.setVisibility(View.VISIBLE);
            } else {
                order_apply_after_sale.setVisibility(View.GONE);
            }


            order_check_logistics.setVisibility(View.VISIBLE);

            order_buy_again.setVisibility(View.VISIBLE);


            order_check_logistics.setOnClickListener(this);
            order_apply_after_sale.setOnClickListener(this);
            order_appraise.setOnClickListener(this);
            order_buy_again.setOnClickListener(this);

            detail_img.setBackgroundResource(R.drawable.icon_order_finish);

            order_type.setText("交易已完成");
            order_shijian.setVisibility(View.GONE);


            wuliu_view.setVisibility(View.VISIBLE);
            OrderDerails.DataBean.OrderDetailBean.OrderShippingListBean orderShippingListBean = null;
            try {
                orderShippingListBean =
                        orderDerails.getData().getOrderDetail().getOrderShippingList().get(0);
            } catch (Exception e) {
                wuliu_view.setVisibility(View.GONE);
            }
            if (orderShippingListBean != null) {
                wuliu_top_view_text_view.setText("已签收");
                wuliu_center_view.setVisibility(View.GONE);
                wuliu_bottom_view_text_view.setText(orderShippingListBean.getCreatedDate());
            }

        } else if (status == YIQUXIAO) {

            wuliu_view.setVisibility(View.GONE);
            detail_img.setBackgroundResource(R.drawable.icon_order_daifukuan);
            order_type.setText("交易已取消");
            order_shijian.setVisibility(View.GONE);

//            order_del.setVisibility(View.VISIBLE);
            order_buy_again.setVisibility(View.VISIBLE);

            order_del.setOnClickListener(this);
            order_buy_again.setOnClickListener(this);

        } else if (status == YIJUJUE) {
            // TODO 拒绝
            order_type.setText("交易已拒绝");
            order_shijian.setVisibility(View.GONE);
            detail_img.setBackgroundResource(R.drawable.icon_order_daifukuan);
            bottom_v.setVisibility(View.GONE);

            wuliu_view.setVisibility(View.GONE);
            order_del.setVisibility(View.VISIBLE);
            order_del.setOnClickListener(this);

        } else if (status == YISHIBAI) {
            // TODO 失败

            order_type.setText("交易已失败");
            order_shijian.setVisibility(View.GONE);
            detail_img.setBackgroundResource(R.drawable.icon_order_daifukuan);
            bottom_v.setVisibility(View.GONE);

            wuliu_view.setVisibility(View.GONE);
            order_del.setVisibility(View.VISIBLE);
            order_del.setOnClickListener(this);
        }


        order_name.setText(orderDerails.getData().getOrderDetail().getConsignee());
        order_phone.setText(orderDerails.getData().getOrderDetail().getPhone());
        order_dizhi.setText(orderDerails.getData().getOrderDetail().getAreaName() +
                orderDerails.getData().getOrderDetail().getAddress());


        order_bianhao.setText(orderDerails.getData().getOrderDetail().getOrderSn());
        bear_hao.setText(orderDerails.getData().getOrderDetail().getOutTradeNo() + "");
        order_time.setText(orderDerails.getData().getOrderDetail().getCreatedDate());
        bt_jiage.setText("BC" + orderDerails.getData().getOrderDetail().getBtAmount());
        yunfei.setText("¥" + orderDerails.getData().getOrderDetail().getFreight());
        xufukuan.setText("¥" + orderDerails.getData().getOrderDetail().getAmount() + "+BC" + orderDerails.getData().getOrderDetail().getBtAmount());
        rmb.setText("¥" + orderDerails.getData().getOrderDetail().getPrice());

        fantangguo.setText(orderDerails.getData().getOrderDetail().getRewardPoint());

        if (orderDerails.getData() != null && orderDerails.getData().getStore() != null && orderDerails.getData().getStore().getLogo() != null) {
            Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(orderDerails.getData().getStore().getLogo()).into(brand_icon);
        }

        brand_name.setText(orderDerails.getData().getStore().getStore_name());
        brand_product_number.setText(orderDerails.getData().getStore().getProductNumber() + "");
        brand_sales_number.setText(orderDerails.getData().getStore().getProductSales() + "");

        List<OrderDerails.DataBean.StoreBean.HotProductListBean> asd = orderDerails.getData().getStore().getHotProductList();
        int size = asd.size() > 2 ? 2 : asd.size();
        if (size >= 1) {
            if (asd.get(0) != null && asd.get(0).getProductImages() != null && asd.get(0).getProductImages().getMedium() != null) {
                Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(asd.get(0).getProductImages().getMedium()).into(goods_1);
            }


            leftPriceView.setPrice(asd.get(0).getMembershipPrice(), asd.get(0).getPrice(),
                    asd.get(0).isSurportMsp(), asd.get(0).getIsDiscount(), asd.get(0).getModel(), asd.get(0).getSourceMembershipPrice(), asd.get(0).getSourcePrice());

        }

        if (size >= 2) {
            if (asd.get(1) != null && asd.get(1).getProductImages() != null && asd.get(1).getProductImages().getMedium() != null) {
                Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(asd.get(1).getProductImages().getMedium()).into(goods_2);
            }

            rightPriceView.setPrice(asd.get(1).getMembershipPrice(), asd.get(1).getPrice(),
                    asd.get(1).isSurportMsp(), asd.get(1).getIsDiscount(), asd.get(1).getModel(), asd.get(1).getSourceMembershipPrice(), asd.get(1).getSourcePrice());

        }
    }

    private void hideButton() {
        order_apply_after_sale.setVisibility(View.GONE);
        order_check_logistics.setVisibility(View.GONE);
        order_appraise.setVisibility(View.GONE);
        order_confirm_receipt.setVisibility(View.GONE);
        order_cancel.setVisibility(View.GONE);
        order_go_pay.setVisibility(View.GONE);
        order_del.setVisibility(View.GONE);
        order_buy_again.setVisibility(View.GONE);
        order_check_info.setVisibility(View.GONE);
    }


    @BindView(R.id.brand_name)
    TextView brand_name;
    @BindView(R.id.brand_product_number)
    TextView brand_product_number;
    @BindView(R.id.brand_sales_number)
    TextView brand_sales_number;

    @BindView(R.id.goods_1)
    ImageView goods_1;
    @BindView(R.id.goods_2)
    ImageView goods_2;


    @BindView(R.id.price_left)
    PriceView leftPriceView;

    @BindView(R.id.price_right)
    PriceView rightPriceView;


    @OnClick({R.id.toolbar_back, R.id.brand_shopping, R.id.left, R.id.right,
            R.id.lianxi_shangjia})
    public void onClickk(View view) {
        if (view.getId() == R.id.toolbar_back) {
            finish();
        } else if (view.getId() == R.id.brand_shopping) {
            Bundle bundle = new Bundle();
            bundle.putString("store_id", orderDerails.getData().getStore().getStore_id() + "");
            StarActivityUtil.starActivity(this, ShopActivity.class, bundle);
        } else if (view.getId() == R.id.left) {

            Intent intent = new Intent(this, NewProductDetailActivity.class);
            intent.putExtra("productId", "" + orderDerails.getData().getStore().getHotProductList().get(0).getProduct_id());
            intent.putExtra("sku_id", "");
            startActivity(intent);

        } else if (view.getId() == R.id.right) {


            Intent intent = new Intent(this, NewProductDetailActivity.class);
            intent.putExtra("productId", "" + orderDerails.getData().getStore().getHotProductList().get(1).getProduct_id());
            intent.putExtra("sku_id", "");
            startActivity(intent);


        } else if (view.getId() == R.id.lianxi_shangjia) {

            if (mOrderDerails != null && mOrderDerails.getData() != null && mOrderDerails.getData().getOrderDetail() != null) {
                final String phone = mOrderDerails.getData().getOrderDetail().getServicePhone();
                if (phone != null) {
                    DialogUtils.showCallPhoneDialog(this, phone);
                }
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_apply_after_sale:
                shenQingShouHou();
                break;
            case R.id.order_check_info:
                chaKanXiangQing();
                break;
            case R.id.order_buy_again:
                zaiCiGouMai();
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
            case R.id.order_go_pay:
                quZhifu();
                break;
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
            case R.id.order_confirm_receipt:
                showFengGouDialog("是否确认收货, ", new JoinZeroCallBack() {
                    @Override
                    public void sureBtn(int flag) {
                        querenshouhuo();
                    }

                    @Override
                    public void canle() {

                    }
                });
                break;
            case R.id.order_appraise:
                shaiDanPingJia();
                break;
            case R.id.order_check_logistics:
                // 查看物流
                LogisticsActivity.start(this, String.valueOf(orders_id));
                break;
            default:
                break;

        }
    }

    private void shenQingShouHou() {
        Bundle bundle = new Bundle();
        bundle.putString("Orders_id", String.valueOf(orders_id));
        bundle.putString("orderProductType", "" + orderProductType);

        Intent intent = new Intent(this, AfterSaleActivity.class);
        intent.putExtras(bundle);
        intent.putExtra("source_id", UPDATE_TYPE);
        startActivity(intent);
    }

    private void chaKanXiangQing() {

    }

    private void zaiCiGouMai() {


        showLoading();

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orders_id", orders_id + "");

        RetrofitApi.request(OrderDetailsActivity.this, RetrofitApi.createApi(Api.class).joinCartFromOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                JSONObject jsonObject = new JSONObject(data);
                int code = jsonObject.optInt("code");
                if (code == 1) {
                    Intent intent = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                    intent.putExtra("switchFragment", "TrolleyFragment");
                    startActivity(intent);
                } else {
                    Toast.makeText(OrderDetailsActivity.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(OrderDetailsActivity.this, "网络错误,请稍后重试!", Toast.LENGTH_SHORT).show();
                hiddenLoadingView();
            }
        });


    }

    private void shaiDanPingJia() {
        Bundle bundle = new Bundle();
        bundle.putInt("orders_id", orders_id);

        int orders_count = mOrderDerails.getData().getOrderDetail().getOrderItemList().size();
        bundle.putInt("orders_count", orders_count);
        for (int i = 0; i < orders_count; i++) {
            bundle.putString(String.format("icon%d", i), mOrderDerails.getData().getOrderDetail().getOrderItemList().get(i).getThumbnail());
            bundle.putInt(String.format("item%d", i), mOrderDerails.getData().getOrderDetail().getOrderItemList().get(i).getProduct_id());
            StringBuffer specificationItems = new StringBuffer();
            for (String content : mOrderDerails.getData().getOrderDetail().getOrderItemList().get(i).getSpecifications()) {
                specificationItems.append(content).append(",");
            }
            bundle.putString(String.format("SpecificationItems%d", i), specificationItems.toString());
        }
        EventBus.getDefault().post(new OrderTypeUpdate(UPDATE_TYPE));
        StarActivityUtil.starActivity(OrderDetailsActivity.this, CommentActivity.class, bundle);
    }

    private void querenshouhuo() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orderStatus", String.valueOf(mOrderDerails.getData().getOrderDetail().getStatus()));
        mHashMap.put("orders_id", String.valueOf(orders_id));
        mHashMap.put("orderProductType", String.valueOf(orderProductType));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).confirmOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        EventBus.getDefault().post(new OrderTypeUpdate(UPDATE_TYPE));
                        updateView();
                    } else {
                        Toast.makeText(OrderDetailsActivity.this, String.format("确认收货失败,请稍后再试!", data), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(OrderDetailsActivity.this, String.format("确认收货失败,请稍后再试!", e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void shanChu() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orderStatus", String.valueOf(mOrderDerails.getData().getOrderDetail().getStatus()));
        mHashMap.put("orders_id", String.valueOf(orders_id));
        mHashMap.put("orderProductType", String.valueOf(orderProductType));

        RetrofitApi.request(OrderDetailsActivity.this, RetrofitApi.createApi(Api.class).deleteOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        EventBus.getDefault().post(new OrderTypeUpdate(UPDATE_TYPE));
                        OrderDetailsActivity.this.finish();
                    } else {
                        Toast.makeText(OrderDetailsActivity.this, String.format("删除订单失败,请稍后再试!", data), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(OrderDetailsActivity.this, String.format("删除订单失败,请稍后再试!", e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void quZhifu() {
        Bundle bundle = new Bundle();
        bundle.putString("btAmount", mOrderDerails.getData().getOrderDetail().getBtAmount());
        bundle.putString("amount", mOrderDerails.getData().getOrderDetail().getAmount());
        bundle.putString("outTradeNo", mOrderDerails.getData().getOrderDetail().getOutTradeNo() + "");
        bundle.putString("ordersId", orders_id + "");
        bundle.putInt("isNeedPay", mOrderDerails.getData().getOrderDetail().getIsNeedPay());
        bundle.putInt("orderProductType", orderProductType);
        bundle.putBoolean("isBanlancePaid", mOrderDerails.getData().isBanlancePaid());


        StarActivityUtil.starActivity(this, PayActivity.class, bundle);
    }

    private void quXiao() {


        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orderStatus", String.valueOf(mOrderDerails.getData().getOrderDetail().getStatus()));
        mHashMap.put("orders_id", String.valueOf(orders_id));
        mHashMap.put("orderProductType", String.valueOf(orderProductType));


        RetrofitApi.request(OrderDetailsActivity.this, RetrofitApi.createApi(Api.class).cancleOrders(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        EventBus.getDefault().post(new OrderTypeUpdate(UPDATE_TYPE));

                        OrderDetailsActivity.this.finish();
                    } else {
                        Toast.makeText(OrderDetailsActivity.this, String.format("取消失败,请稍后再试!"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(OrderDetailsActivity.this, String.format("取消失败,请稍后再试!", e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showFengGouDialog(String title, JoinZeroCallBack joinZeroCallBack) {

        StringBuffer stringBuffer = new StringBuffer(title);
        if (mOrderDerails == null) {
        } else {
            try {
                stringBuffer.append("订单号：" + mOrderDerails.getData().getOrderDetail().getOrderSn());
            } catch (Exception e) {
            }
        }
        DialogUtils.tipSearchDialog(OrderDetailsActivity.this, 666, stringBuffer.toString(), joinZeroCallBack);
    }


}
