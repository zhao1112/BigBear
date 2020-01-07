package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SearchBean;
import com.yunqin.bearmall.widget.PriceView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private SearchBean mSearchBean;
    private Context mContext;


    public RecommendAdapter(Context context, SearchBean mSearchBean) {
        this.mContext = context;
        this.mSearchBean = mSearchBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NormalGoodsHolder(View.inflate(mContext, R.layout.n_v_item_goods_show2, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof NormalGoodsHolder) {

                SearchBean.DataBean.ProductListBean productListBean = mSearchBean.getData().getProductList().get(position);

                if (productListBean.isCrossBorder()) {
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setVisibility(View.VISIBLE);
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setImageResource(R.drawable.n_v_qqg);
                } else if (productListBean.isNew()) {
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setVisibility(View.VISIBLE);
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setImageResource(R.drawable.n_v_xp);
                } else {
                    ((NormalGoodsHolder) holder).n_v_t_img_t.setVisibility(View.GONE);
                }

                ((NormalGoodsHolder) holder).n_v_t_name.setText(productListBean.getProductName());

                try {
                    ((NormalGoodsHolder) holder).price_view.setPrice(productListBean.getMembershipPrice(), productListBean.getPrice(),
                            productListBean.isSurportMsp(), productListBean.getIsDiscount(),
                            productListBean.getModel(), productListBean.getSourceMembershipPrice(), productListBean.getSourcePrice());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((NormalGoodsHolder) holder).n_v_t_linear.setTag(productListBean);
                ((NormalGoodsHolder) holder).n_v_t_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent iIntent = new Intent(mContext, NewProductDetailActivity.class);
                        iIntent.putExtra("productId", "" + productListBean.getProduct_id());
                        iIntent.putExtra("sku_id", "");
                        Log.e("NormalGoodsHolder", "onClick: " + productListBean.getProduct_id());
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
        if (mSearchBean != null && mSearchBean.getData() != null && mSearchBean.getData().getProductList() != null) {
            return mSearchBean.getData().getProductList().size();
        }
        return 0;
    }


    @Override
    public void onClick(View v) {
        Intent iIntent = new Intent(mContext, NewProductDetailActivity.class);
        iIntent.putExtra("productId", "" + mSearchBean.getData().getProductList().get(((int) v.getTag())).getProduct_id());
        iIntent.putExtra("sku_id", "");
        mContext.startActivity(iIntent);
    }

    class NormalGoodsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.price_view)
        PriceView price_view;
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

}
