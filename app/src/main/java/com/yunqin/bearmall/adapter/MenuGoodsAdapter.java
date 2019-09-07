package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.MenuGoods;
import com.yunqin.bearmall.widget.PriceView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Master
 */
public class MenuGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private MenuGoods mMenuGoods;
    private Context mContext;

    public void setData(MenuGoods mMenuGoods) {
        // TODO: 2018/7/30 wangzhiwei
        this.mMenuGoods = mMenuGoods;
        notifyDataSetChanged();
    }

    public MenuGoods getData() {
        return mMenuGoods;
    }


    public void addData(MenuGoods mMenuGoods) {
        // TODO: 2018/7/30 wangzhiwei
        this.mMenuGoods.getData().getProductList().addAll(mMenuGoods.getData().getProductList());
        notifyDataSetChanged();
    }

    public MenuGoodsAdapter(Context context) {
        this.mContext = context;
    }


    public int getItemColumnCount(int position) {

        if (mMenuGoods != null && mMenuGoods.getData() != null && mMenuGoods.getData().getProductList() != null
                && mMenuGoods.getData().getProductList().size() > 0) {
            return 1;
        }

        return 2;
    }


    public MenuGoodsAdapter(Context context, MenuGoods menuGoods) {
        this.mContext = context;
        this.mMenuGoods = menuGoods;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu_goods_v, parent, false);
            return new NormalGoodsHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.network_error_layout, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {

            if (holder instanceof NormalGoodsHolder) {
                if (mMenuGoods != null && mMenuGoods.getData() != null && mMenuGoods.getData().getProductList() != null) {
                    holder.itemView.setOnClickListener(this);
                    holder.itemView.setTag(position);
                    MenuGoods.DataBean.ProductListBean productListBean = mMenuGoods.getData().getProductList().get(position);
                    ((NormalGoodsHolder) holder).mGoodName.setText(productListBean.getProductName());
                    ((NormalGoodsHolder) holder).buyCountView.setText(productListBean.getSales() + "人已购");
                    ((NormalGoodsHolder) holder).scoreView.setText(productListBean.getScore() + "分");
                    if (productListBean.getProductImages() != null) {
                        MenuGoods.DataBean.ProductListBean.ProductImagesBean productImages = productListBean.getProductImages();
                        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(productImages.getSource()).into(((NormalGoodsHolder) holder).mImageView);
                    }
                    ((NormalGoodsHolder) holder).priceView.setPrice(productListBean.getMembershipPrice(), productListBean.getPrice(), productListBean.isSurportMsp(), productListBean.getIsDiscount(), productListBean.getModel(), productListBean.getSourceMembershipPrice(), productListBean.getSourcePrice());

                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        if (mMenuGoods != null && mMenuGoods.getData() != null && mMenuGoods.getData().getProductList() != null
                && mMenuGoods.getData().getProductList().size() > 0) {
            return mMenuGoods.getData().getProductList().size();
        }
        return 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (mMenuGoods != null && mMenuGoods.getData() != null && mMenuGoods.getData().getProductList() != null
                && mMenuGoods.getData().getProductList().size() > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, NewProductDetailActivity.class);
        intent.putExtra("productId", "" + mMenuGoods.getData().getProductList().get(((int) v.getTag())).getProduct_id());
        intent.putExtra("sku_id", "");
        mContext.startActivity(intent);
    }


    class NormalGoodsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_image)
        ImageView mImageView;
        @BindView(R.id.goods_title)
        TextView mGoodName;
        @BindView(R.id.preice_view)
        PriceView priceView;
        @BindView(R.id.goods_buy_count)
        TextView buyCountView;
        @BindView(R.id.goods_score)
        TextView scoreView;


        public NormalGoodsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {

        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }


}
