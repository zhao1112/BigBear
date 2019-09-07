package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ProductDetail;

import java.util.ArrayList;
import java.util.List;

import mlxy.utils.T;

public class SkuListAdapter extends RecyclerView.Adapter<SkuListAdapter.ViewHolder> {

    private Context mContext;
    private ProductDetail.SkuList mSkuListObject;
    private List<ProductDetail.SpecificationValues> mListItemRepetition;
    private int mIndex;
    private LayoutInflater mInflater;
    /***
     *
     * @param context
     * @param skuListObject
     * @param index---第几个规格分类
     */
    public SkuListAdapter(Context context, ProductDetail.SkuList skuListObject, List<ProductDetail.SpecificationValues> listItemRepetition, int index) {
        this.mContext = context;
        this.mSkuListObject = skuListObject;
        this.mListItemRepetition = listItemRepetition;
        this.mIndex = index;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_sku_list, null);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            if (mListItemRepetition.get(position).getId() == mSkuListObject.getSpecificationValues().get(mIndex).getId()) {
                holder.value.setTextColor(mContext.getResources().getColor(R.color.main_color));
                holder.value.setBackgroundResource(R.drawable.item_sku_list_selector);
            } else {
                holder.value.setTextColor(mContext.getResources().getColor(R.color.product_customer_collect));
                holder.value.setBackgroundResource(R.drawable.item_sku_list_normal);
            }

            holder.value.setText(mListItemRepetition.get(position).getValue());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductDetail.SpecificationValues specificationValues = mListItemRepetition.get(position);

                    //通过改变mSkuListObject里规格列表的值，来达到按钮点击改变颜色的目的，同时也会造成Dialog中的mSkuList里的数据的改变，造成后续遍历的时候，无法获取正确的数据
                    mSkuListObject.getSpecificationValues().remove(mIndex);
                    mSkuListObject.getSpecificationValues().add(mIndex,specificationValues);


                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClickObject(mSkuListObject.getSpecificationValues());

                        notifyDataSetChanged();
                    }
                }
            });
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListItemRepetition.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView value;

        public ViewHolder(View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.sku_list_btn);
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClickObject(List<ProductDetail.SpecificationValues> specificationValues);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
