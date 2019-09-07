package com.newversions.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newversions.detail.NewProductBean;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/1/17 17:33
 * 自定义商品详情显示的规格View
 */
public class ICustomView extends LinearLayout implements View.OnClickListener {

    private Context context;
    private TextView custom_title;
    private FlowLayout flow_layout;
    private List<TextView> views;
    private int viewId;
    private int oldId;
    private int newId;
    private String newValue;

    public ICustomView(Context context) {
        this(context, null);
    }

    public ICustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ICustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(context).inflate(R.layout.new_version_dialog_guige, this, true);
        views = new ArrayList<>();
        custom_title = findViewById(R.id.custom_title);
        flow_layout = findViewById(R.id.flow_layout);
    }

//    public void initData(int viewId, NewProductBean.DataBean.ProductBean.SpecificationItemsBean specificationItemsBean) {
//
//        flow_layout.removeAllViews();
//
//        this.viewId = viewId;
//        custom_title.setText(specificationItemsBean.getName());
//
//        for (int i = 0; i < specificationItemsBean.getEntries().size(); i++) {
//            if (specificationItemsBean.getEntries().get(i).isIsSelected()) {
//                LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.new_version_dialog_spc_tv, null);
//                TextView textView = view.findViewById(R.id.tv_aaa);
//                view.removeView(textView);
//
//                textView.setText(specificationItemsBean.getEntries().get(i).getValue());
//
//                textView.setTag(specificationItemsBean.getEntries().get(i).getId());
//                textView.setOnClickListener(this);
//
//                // 默认选中第一个,并重新计算 sku id
//                if (i == 0) {
//                    oldId = specificationItemsBean.getEntries().get(i).getId();
//                    newId = specificationItemsBean.getEntries().get(i).getId();
//
//                    textView.setTextColor(context.getResources().getColor(R.color.main_color));
//                    textView.setBackgroundResource(R.drawable.item_sku_list_selector);
//                } else {
//                    textView.setTextColor(context.getResources().getColor(R.color.product_customer_collect));
//                    textView.setBackgroundResource(R.drawable.item_sku_list_normal);
//                }
//                views.add(textView);
//                flow_layout.addView(textView);
//            }
//        }
//    }

    private String test = "----------***----------";

//    public void initData(int viewId, NewProductBean.DataBean.ProductBean.SpecificationItemsBean specificationItemsBean, String containData) {
//
//        flow_layout.removeAllViews();
//
//        this.viewId = viewId;
//        custom_title.setText(specificationItemsBean.getName());
//
//        for (int i = 0; i < specificationItemsBean.getEntries().size(); i++) {
//            if (specificationItemsBean.getEntries().get(i).isIsSelected() && containData.contains(String.valueOf(specificationItemsBean.getEntries().get(i).getId()))) {
//                LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.new_version_dialog_spc_tv, null);
//                TextView textView = view.findViewById(R.id.tv_aaa);
//                view.removeView(textView);
//
//                textView.setText(specificationItemsBean.getEntries().get(i).getValue());
//
//                textView.setTag(specificationItemsBean.getEntries().get(i).getId());
//                textView.setOnClickListener(this);
//
//                // 默认选中第一个,并重新计算 sku id
//                if (i == 0) {
//                    oldId = specificationItemsBean.getEntries().get(i).getId();
//                    newId = specificationItemsBean.getEntries().get(i).getId();
//
//                    textView.setTextColor(context.getResources().getColor(R.color.main_color));
//                    textView.setBackgroundResource(R.drawable.item_sku_list_selector);
//                } else {
//                    textView.setTextColor(context.getResources().getColor(R.color.product_customer_collect));
//                    textView.setBackgroundResource(R.drawable.item_sku_list_normal);
//                }
//                views.add(textView);
//                flow_layout.addView(textView);
//            }
//        }
//    }

    public void setData(int viewId, NewProductBean.DataBean.ProductBean.SpecificationItemsBean specificationItemsBean, String defId) {

        flow_layout.removeAllViews();

        this.viewId = viewId;
        custom_title.setText(specificationItemsBean.getName());
        String[] defIds = defId.split(":");

        for (int i = 0; i < specificationItemsBean.getEntries().size(); i++) {
            if (specificationItemsBean.getEntries().get(i).isIsSelected()) {
                LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.new_version_dialog_spc_tv, null);
                TextView textView = view.findViewById(R.id.tv_aaa);
                view.removeView(textView);

                textView.setText(specificationItemsBean.getEntries().get(i).getValue());

                textView.setTag(specificationItemsBean.getEntries().get(i).getId());
                textView.setOnClickListener(this);

                textView.setTextColor(context.getResources().getColor(R.color.product_customer_collect));
                textView.setBackgroundResource(R.drawable.item_sku_list_normal);

                for (int y = 0; y < defIds.length; y++) {
                    String id = String.valueOf(specificationItemsBean.getEntries().get(i).getId());
                    if (id.equals(defIds[y])) {
                        oldId = specificationItemsBean.getEntries().get(i).getId();
                        newId = specificationItemsBean.getEntries().get(i).getId();
                        newValue = specificationItemsBean.getEntries().get(i).getValue();
                        textView.setTextColor(context.getResources().getColor(R.color.main_color));
                        textView.setBackgroundResource(R.drawable.item_sku_list_selector);
                        break;
                    }
                }
                views.add(textView);
                flow_layout.addView(textView);
            }
        }
    }

    public void setData(int viewId, NewProductBean.DataBean.ProductBean.SpecificationItemsBean specificationItemsBean, String defId, String containData) {

        flow_layout.removeAllViews();

        this.viewId = viewId;
        custom_title.setText(specificationItemsBean.getName());
        String[] defIds = defId.split(":");
        boolean isDef = true;

        for (int i = 0; i < specificationItemsBean.getEntries().size(); i++) {
            if (specificationItemsBean.getEntries().get(i).isIsSelected() && containData.contains(String.valueOf(specificationItemsBean.getEntries().get(i).getId()))) {
                LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.new_version_dialog_spc_tv, null);
                TextView textView = view.findViewById(R.id.tv_aaa);
                view.removeView(textView);

                textView.setText(specificationItemsBean.getEntries().get(i).getValue());

                textView.setTag(specificationItemsBean.getEntries().get(i).getId());
                textView.setOnClickListener(this);

//                // 默认选中第一个,并重新计算 sku id
//                if (i == 0) {
//                    oldId = specificationItemsBean.getEntries().get(i).getId();
//                    newId = specificationItemsBean.getEntries().get(i).getId();
//
//                    textView.setTextColor(context.getResources().getColor(R.color.main_color));
//                    textView.setBackgroundResource(R.drawable.item_sku_list_selector);
//                } else {
                textView.setTextColor(context.getResources().getColor(R.color.product_customer_collect));
                textView.setBackgroundResource(R.drawable.item_sku_list_normal);
//                }


                for (int y = 0; y < defIds.length; y++) {
                    String id = String.valueOf(specificationItemsBean.getEntries().get(i).getId());
                    if (id.equals(defIds[y])) {
                        oldId = specificationItemsBean.getEntries().get(i).getId();
                        newId = specificationItemsBean.getEntries().get(i).getId();
                        newValue = specificationItemsBean.getEntries().get(i).getValue();
                        textView.setTextColor(context.getResources().getColor(R.color.main_color));
                        textView.setBackgroundResource(R.drawable.item_sku_list_selector);
                        isDef = false;
                        break;
                    }
                }

                views.add(textView);
                flow_layout.addView(textView);
            }
        }

        if (isDef) {
            oldId = -1;
            newId = -1;
            newValue = "";
        }


    }


    @Override
    public void onClick(View view) {
//        if (oldId == (int) view.getTag()) {
//            return;
//        }
        oldId = newId;
        newId = (int) view.getTag();
        onViewClickListener.onViewClick(viewId, oldId, newId);
    }


    public int getCurrentId() {
        return newId;
    }

    public String getCurrentValue() {
        return newValue;
    }

    private OnViewClickListener onViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public interface OnViewClickListener {
        void onViewClick(int viewId, int oldId, int newId);
    }


}
