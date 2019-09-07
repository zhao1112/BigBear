package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.MenuGoods;
import com.yunqin.bearmall.widget.PriceView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Master
 */
public class SearchGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


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

    public SearchGoodsAdapter(Context context) {
        this.mContext = context;
    }


    public int getItemColumnCount(int position) {

        if (mMenuGoods != null && mMenuGoods.getData() != null && mMenuGoods.getData().getProductList() != null
                && mMenuGoods.getData().getProductList().size() > 0) {
            return 1;
        }

        return 2;
    }


    public SearchGoodsAdapter(Context context, MenuGoods menuGoods) {
        this.mContext = context;
        this.mMenuGoods = menuGoods;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.n_v_item_goods_show2, parent, false);
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

                MenuGoods.DataBean.ProductListBean productListBean = mMenuGoods.getData().getProductList().get(position);

                if (productListBean.isCrossBorder()) {
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setVisibility(View.VISIBLE);
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setImageResource(R.drawable.n_v_qqg);
                } else if (productListBean.getIsNew()) {
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setVisibility(View.VISIBLE);
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setImageResource(R.drawable.n_v_xp);
                } else {
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setVisibility(View.GONE);
                }

                ((NormalGoodsHolder) holder).n_v_t_name.setText(productListBean.getProductName());

              ((NormalGoodsHolder) holder).priceView.setPrice(productListBean.getMembershipPrice(),productListBean.getPrice()
                      ,productListBean.isSurportMsp(),productListBean.getIsDiscount(),productListBean.getModel()
                      ,productListBean.getSourceMembershipPrice(),productListBean.getSourcePrice());


                ((NormalGoodsHolder) holder).n_v_t_linear.setTag(productListBean);
                ((NormalGoodsHolder) holder).n_v_t_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent iIntent = new Intent(mContext, NewProductDetailActivity.class);
                        iIntent.putExtra("productId", "" + productListBean.getProduct_id());
                        iIntent.putExtra("sku_id", "");
                        mContext.startActivity(iIntent);


                    }
                });
                try {
                    Glide.with(mContext).load(productListBean.getProductImages().getSource()).into(((NormalGoodsHolder) holder).n_v_t_img);
                } catch (Exception e) {
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


        Intent iIntent = new Intent(mContext, NewProductDetailActivity.class);
        iIntent.putExtra("productId", "" + mMenuGoods.getData().getProductList().get(((int) v.getTag())).getProduct_id());
        iIntent.putExtra("sku_id", "");
        mContext.startActivity(iIntent);
    }


    class NormalGoodsHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.price_view)
        PriceView priceView;

        @BindView(R.id.n_v_t_name)
        TextView n_v_t_name;

        @BindView(R.id.n_v_t_img_t)
        ImageView n_v_t_img_t;
        @BindView(R.id.n_v_t_img)
        ImageView n_v_t_img;
        @BindView(R.id.n_v_t_linear)
        LinearLayout n_v_t_linear;


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
