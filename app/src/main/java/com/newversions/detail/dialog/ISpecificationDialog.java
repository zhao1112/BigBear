package com.newversions.detail.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.newversions.detail.NewProductBean;
import com.newversions.view.ICustomView;
import com.yunqin.bearmall.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/1/17 14:44
 */
public class ISpecificationDialog extends Dialog implements View.OnClickListener, ICustomView.OnViewClickListener {

    private LinearLayout spec_container;
    private ImageView dialog_product_img;
    private TextView dialog_specification_items_price;
    private TextView dialog_specification_items_parameter;
    private RelativeLayout dialog_specification_items_cancel;
    private Button new_version_ok;
    private OnDialogClickListener onDialogClickListener;
    private Context context;

    private int skuId = -1;
    private int quantity = 1;
    private List<ICustomView> iCustomViews;
    private boolean isMember;


    public ISpecificationDialog(@NonNull Context context,boolean isMember, OnDialogClickListener onDialogClickListener) {
        super(context, R.style.ProductDialog);
        this.context = context;
        this.isMember = isMember;
        this.onDialogClickListener = onDialogClickListener;
        iCustomViews = new ArrayList<>();
    }


    private String[] stringDefId;


    @Override
    public void onViewClick(int viewId, int oldId, int newId) {

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < iCustomViews.size(); i++) {
            stringBuffer.append(iCustomViews.get(i).getCurrentId() + ":");
        }

        stringDefId = stringBuffer.toString().split(":");

        for (int j = viewId; j < iCustomViews.size(); j++) {
            if (j == 0) {
                iCustomViews.get(j).setData(j, specificationItemsBeans.get(j), stringBuffer.toString());
            } else {
                String contains = iteratorSpecificationItemsBean(stringDefId[j - 1], skuListBeans);
                iCustomViews.get(j).setData(j, specificationItemsBeans.get(j), stringBuffer.toString(), contains);
            }
        }
        changeSetText();
    }

    public interface OnDialogClickListener {
        void onDialogClick(int sku_id, int quantity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate);
        setContentView(R.layout.new_version_product_detail_dialog_layout);

        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = display.getWidth() * 1;
        dialogWindow.setAttributes(layoutParams);

        spec_container = this.findViewById(R.id.spec_container);
        dialog_product_img = this.findViewById(R.id.dialog_product_img);
        dialog_specification_items_price = this.findViewById(R.id.dialog_specification_items_price);
        dialog_specification_items_parameter = this.findViewById(R.id.dialog_specification_items_parameter);
        dialog_specification_items_cancel = this.findViewById(R.id.dialog_specification_items_cancel);
        dialog_specification_items_cancel.setOnClickListener(this);
        new_version_ok = this.findViewById(R.id.new_version_ok);
        new_version_ok.setOnClickListener(this);
    }


    private List<NewProductBean.DataBean.SkuListBean> skuListBeans = new ArrayList<>();
    private List<NewProductBean.DataBean.ProductBean.SpecificationItemsBean> specificationItemsBeans = new ArrayList<>();

    public void setData(String imgUrl, List<NewProductBean.DataBean.ProductBean.SpecificationItemsBean> specificationItemsBeans1,
                        List<NewProductBean.DataBean.SkuListBean> skuListBeans1) {

        skuListBeans.clear();
        specificationItemsBeans.clear();
        this.skuListBeans.addAll(skuListBeans1);
        this.specificationItemsBeans.addAll(specificationItemsBeans1);

        iCustomViews.clear();
        Glide.with(context).load(imgUrl).into(dialog_product_img);

        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        for (int i = 0; i < skuListBeans.size(); i++) {
            if (skuListBeans.get(i).isIsDefault()) {
                skuId = skuListBeans.get(i).getSku_id();

                if(isMember){
                    dialog_specification_items_price.setText(String.format("价格：￥%s", skuListBeans.get(i).getMembershipPrice()));
                }else{
                    dialog_specification_items_price.setText(String.format("价格：￥%s", skuListBeans.get(i).getPrice()));
                }


                for (int j = 0; j < skuListBeans.get(i).getSpecificationValues().size(); j++) {
                    stringBuffer.append(skuListBeans.get(i).getSpecificationValues().get(j).getId() + ":");
                    stringBuffer1.append(skuListBeans.get(i).getSpecificationValues().get(j).getValue() + " ");
                }
                break;
            }
        }

        dialog_specification_items_parameter.setText("已选：" + stringBuffer1.toString());
        stringDefId = stringBuffer.toString().split(":");

        for (int i = 0; i < specificationItemsBeans.size(); i++) {
            if (i == 0) {
                ICustomView iCustomView = new ICustomView(context);
                iCustomView.setData(i, specificationItemsBeans.get(i), stringBuffer.toString());
//                iCustomView.initData(i, specificationItemsBeans.get(i));
                iCustomView.setOnViewClickListener(this);
                iCustomViews.add(iCustomView);
                spec_container.addView(iCustomView);
            } else {
                ICustomView iCustomView = new ICustomView(context);
                String contains = iteratorSpecificationItemsBean(stringDefId[i - 1], skuListBeans);
                iCustomView.setData(i, specificationItemsBeans.get(i), stringBuffer.toString(), contains);
                iCustomView.setOnViewClickListener(this);
                iCustomViews.add(iCustomView);
                spec_container.addView(iCustomView);
            }
        }

        spec_container.addView(initspecificationItemsTextView(context, "数量"));
        spec_container.addView(initspecificationItemsCountView(context));

    }


    private String iteratorSpecificationItemsBean(String defId, List<NewProductBean.DataBean.SkuListBean> skuListBeans) {

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < skuListBeans.size(); i++) {
            StringBuffer temporaryBuffer = new StringBuffer();
            boolean isAdd = false;
            for (int j = 0; j < skuListBeans.get(i).getSpecificationValues().size(); j++) {
                if (String.valueOf(skuListBeans.get(i).getSpecificationValues().get(j).getId()).equals(defId)) {
                    isAdd = true;
                } else {
                    temporaryBuffer.append(skuListBeans.get(i).getSpecificationValues().get(j).getId() + ":");
                }
            }
            if (isAdd) {
                stringBuffer.append(temporaryBuffer.toString());
            }
        }
        return stringBuffer.toString();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_specification_items_cancel:
                dismiss();
                break;
            case R.id.new_version_ok:

                if (skuId == -1) {
                    Toast.makeText(context, "请选择具体商品", Toast.LENGTH_SHORT).show();
                    return;
                }

                dismiss();
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < iCustomViews.size(); i++) {
                    stringBuffer.append(iCustomViews.get(i).getCurrentId() + ":");
                }




                onDialogClickListener.onDialogClick(skuId, quantity);
                break;
            case R.id.dialog_tv_minus:
                if (quantity == 1) {
                    return;
                }
                quantity--;
                if (quantity == 1) {
                    tv_minus.setTextColor(context.getResources().getColor(R.color.item_specification_item_minus_tv));
                    tv_minus.setBackgroundResource(R.drawable.specifition_item_minus_disabled);
                }
                tv_count.setText(String.valueOf(quantity));
                changeSetText();
                break;
            case R.id.dialog_tv_plus:
                tv_minus.setClickable(true);
                tv_minus.setTextColor(context.getResources().getColor(R.color.product_customer_collect));
                tv_minus.setBackgroundResource(R.drawable.specifition_item_minus_abled);


                int kucun = 0;

                for (int i = 0; i < skuListBeans.size(); i++) {
                    if (skuId == skuListBeans.get(i).getSku_id()) {
                        kucun = skuListBeans.get(i).getStock();
                    }
                }

                if (quantity >= kucun) {
                    Toast.makeText(context, "已达到库存上限", Toast.LENGTH_SHORT).show();
                    return;
                }

                quantity++;
                tv_count.setText(String.valueOf(quantity));
                changeSetText();
                break;
        }
    }


    /**
     * 添加 标题 TextView
     *
     * @param context
     * @param name
     * @return
     */
    private TextView initspecificationItemsTextView(Context context, String name) {
        LinearLayout viewCount = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.new_version_dialog_tv, null, false);
        TextView textView = viewCount.findViewById(R.id.dialog_specification_items_name_tv);
        viewCount.removeView(textView);
        textView.setText(name);
        return textView;
    }

    /**
     * 添加 标题 TextView
     *
     * @param context
     * @return
     */


    private TextView tv_minus;
    private TextView tv_count;
    private TextView tv_plus;

    private View initspecificationItemsCountView(Context context) {
        View viewCount = LayoutInflater.from(context).inflate(R.layout.dialog_specification_items_count, null);
        tv_minus = viewCount.findViewById(R.id.dialog_tv_minus);
        tv_count = viewCount.findViewById(R.id.dialog_tv_count);
        tv_plus = viewCount.findViewById(R.id.dialog_tv_plus);

        tv_minus.setOnClickListener(this);
        tv_plus.setOnClickListener(this);
        return viewCount;
    }


    /**
     * 改变价格
     * 改变选中规格
     * 改变图片
     */
    private void changeSetText() {
        setSkuId();
        if (skuId == -1) {
            dialog_specification_items_price.setText("价格：");
            dialog_specification_items_parameter.setText("已选：");
            return;
        }

        // 数量
        BigDecimal mCountBigDecimal = new BigDecimal(String.valueOf(quantity));
        // 价格
        BigDecimal mPriceBigDecimal = new BigDecimal(getSkuPrice());

        dialog_specification_items_price.setText(String.format("价格：￥%s", String.valueOf(mPriceBigDecimal.multiply(mCountBigDecimal).floatValue())));






        dialog_specification_items_parameter.setText(String.format("已选：%s", getSkuGuiGe()));


    }


    private String getSkuPrice() {
        String price = "";
        for (int i = 0; i < skuListBeans.size(); i++) {
            if (skuListBeans.get(i).getSku_id() == skuId) {
                if(isMember){
                    price = skuListBeans.get(i).getMembershipPrice();
                }else{
                    price = skuListBeans.get(i).getPrice();
                }
                // 更改IMG
                Glide.with(context).load(skuListBeans.get(i).getSkuImage()).into(dialog_product_img);
                break;
            }
        }
        return price;
    }

    private String getSkuGuiGe() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < iCustomViews.size(); i++) {
            stringBuffer.append(iCustomViews.get(i).getCurrentValue() + " ");
        }
        return stringBuffer.toString();
    }


    private void setSkuId() {

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < iCustomViews.size(); i++) {
            stringBuffer.append(iCustomViews.get(i).getCurrentId() + ";");
        }
        String ids = stringBuffer.toString();

        if (ids.contains("-1;")) {
            skuId = -1;
            return;
        }

        for (int i = 0; i < skuListBeans.size(); i++) {
            boolean isTrue = true;
            for (int y = 0; y < skuListBeans.get(i).getSpecificationValues().size(); y++) {
                if (!ids.contains(skuListBeans.get(i).getSpecificationValues().get(y).getId() + ";")) {
                    isTrue = false;
                    break;
                }
            }
            if (isTrue) {
                skuId = skuListBeans.get(i).getSku_id();
                break;
            }
        }
    }

}
