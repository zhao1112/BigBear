package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ProductDetail;

import java.util.List;

public class BrandHotProductRecyclerViewAdapter extends RecyclerView.Adapter<BrandHotProductRecyclerViewAdapter.ViewHolder>{

    private OnItemClickListener onItemClickListener;

    private Context mContext;
    private List<ProductDetail.HotProductList> mHotProductListList;
    private LayoutInflater mInflater;
    public BrandHotProductRecyclerViewAdapter(Context context,List<ProductDetail.HotProductList> hotProductListList) {
        this.mContext = context;
        this.mHotProductListList = hotProductListList;
        mInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_brand_hot_produc,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            ProductDetail.HotProductList hotProductList= mHotProductListList.get(position);
            try{
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(hotProductList.getProductImages().getMedium()).into(holder.productImg);
            }catch (Exception e){
            }
            holder.price.setText("¥"+hotProductList.getPrice());
            holder.btPrice.setText("¥"+hotProductList.getPartPrice()+"+BC"+hotProductList.getPartBtAmount());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.OnItemClickObject(position,hotProductList);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return mHotProductListList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImg;
        private TextView price,btPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            productImg = (ImageView)itemView.findViewById(R.id.brand_hot_product_img);
            price = (TextView)itemView.findViewById(R.id.brand_hot_product_price);
            btPrice = (TextView)itemView.findViewById(R.id.brand_hot_product_bt_price);
        }
    }

    public interface OnItemClickListener{
        void OnItemClickObject(int postion,ProductDetail.HotProductList hotProductList);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
