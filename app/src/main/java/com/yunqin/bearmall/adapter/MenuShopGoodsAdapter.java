package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ShopGoods;
import com.yunqin.bearmall.widget.PriceView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Master
 */
public class MenuShopGoodsAdapter extends RecyclerView.Adapter<MenuShopGoodsAdapter.NormalGoodsHolder> implements View.OnClickListener {


    private ShopGoods shopGoods;
    private Context mContext;


    public void setData(ShopGoods shopGoods) {
        this.shopGoods = shopGoods;
        notifyDataSetChanged();
    }

    public void addData(ShopGoods shopGoods) {
        if (shopGoods != null && shopGoods.getData() != null && shopGoods.getData().getProductList() != null) {
            this.shopGoods.getData().getProductList().addAll(shopGoods.getData().getProductList());
            notifyDataSetChanged();
        }
    }

    public ShopGoods getData() {
        return shopGoods;
    }

    public MenuShopGoodsAdapter(Context context, ShopGoods shopGoods) {
        this.mContext = context;
        this.shopGoods = shopGoods;
    }

    @NonNull
    @Override
    public NormalGoodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_goods_show, null);
        return new NormalGoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NormalGoodsHolder holder, int position) {
        try {
            if (shopGoods != null && shopGoods.getData() != null && shopGoods.getData().getProductList() != null) {
                holder.itemView.setOnClickListener(this);
                holder.itemView.setTag(position);
                ShopGoods.DataBean.ProductListBean productListBean = shopGoods.getData().getProductList().get(position);
                holder.mGoodName.setText(productListBean.getProductName());




                String memberPrice = productListBean.getMembershipPrice();
                String price = productListBean.getPrice();
                boolean surportMsp = productListBean.isSurportMsp();
                int isDiscount = productListBean.getIsDiscount();
                int model = productListBean.getModel();
                String sourceMemberPrice = productListBean.getSourceMembershipPrice();
                String sourceprice = productListBean.getSourcePrice();


                holder.priceView.setPrice(memberPrice,price,surportMsp,isDiscount,model,sourceMemberPrice,sourceprice);


//                holder.mRMBTextView.setText("￥" + productListBean.getPrice());
//                holder.mBTTextView.setText("￥" + productListBean.getPartPrice() + "+BC" + productListBean.getPartBtAmount());
                if (productListBean.getProductImages() != null) {
                    ShopGoods.DataBean.ProductListBean.ProductImagesBean productImages = productListBean.getProductImages();
                    Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(productImages.getSource()).into(holder.mImageView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (shopGoods != null && shopGoods.getData() != null && shopGoods.getData().getProductList() != null) {
            return shopGoods.getData().getProductList().size();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(mContext, NewProductDetailActivity.class);
        intent.putExtra("productId", ""+ shopGoods.getData().getProductList().get(((int) v.getTag())).getProduct_id());
        intent.putExtra("sku_id", "");
        mContext.startActivity(intent);


    }


    class NormalGoodsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_good)
        ImageView mImageView;

        @BindView(R.id.item_good_name)
        TextView mGoodName;



        @BindView(R.id.price_view)
        PriceView priceView;



        public NormalGoodsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
