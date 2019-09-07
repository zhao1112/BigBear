package com.newversions.hd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.IAdvClick;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newversions.hd.HdRecyclerAdapter.FRAGMENT_TYPE.TYPE_GOODS;
import static com.newversions.hd.HdRecyclerAdapter.FRAGMENT_TYPE.TYPE_HEADER;

/**
 * Create By Master
 * On 2019/1/9 16:28
 */
public class HdRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ImageView hdTopImageView;

    private List<ItemBean.DataBean.DiscountProductListBean> discountProductListBeans;


    public HdRecyclerAdapter(Context context) {
        this.context = context;
        discountProductListBeans = new ArrayList<>();
    }

    @IntDef({TYPE_HEADER, TYPE_GOODS})
    public @interface FRAGMENT_TYPE {
        int TYPE_HEADER = 0;
        int TYPE_GOODS = 1;
    }


    public void setData(List<ItemBean.DataBean.DiscountProductListBean> discountProductListBeans) {
        this.discountProductListBeans = discountProductListBeans;
        this.notifyDataSetChanged();
    }

    public void addData(List<ItemBean.DataBean.DiscountProductListBean> discountProductListBeans) {
        this.discountProductListBeans.addAll(discountProductListBeans);
        this.notifyDataSetChanged();
    }

    public void setTopImgData(BannerBean bannerBean) {
        if (bannerBean != null
                && bannerBean.getData() != null && bannerBean.getData().getAdMobileList() != null
                && bannerBean.getData().getAdMobileList().size() > 0) {
            Glide.with(context).load(bannerBean.getData().getAdMobileList().get(0).getImg()).into(hdTopImageView);

            hdTopImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    advClick(bannerBean.getData().getAdMobileList().get(0).getType(),
                            bannerBean.getData().getAdMobileList().get(0).getSkipType(),
                            (long) bannerBean.getData().getAdMobileList().get(0).getSource_id());
                }
            });
        } else {
            hdTopImageView.setOnClickListener(view -> {
            });
            Glide.with(context).load(R.drawable.default_tuijian).into(hdTopImageView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.n_v_hd_top_layout, parent, false);
            hdTopImageView = view.findViewById(R.id.n_v_hd_img);
            return new HeaderHolder(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.n_v_item_goods_tejia_lauout, parent, false);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GoodsHolder) {
            ItemBean.DataBean.DiscountProductListBean discountProduct = (ItemBean.DataBean.DiscountProductListBean) discountProductListBeans.get(position - 1);

            ((GoodsHolder) holder).n_v_dangqianjia.setText(String.format("¥%s", discountProduct.getPrice()));

            if (discountProduct.isIsSurportMsp()) {
                ((GoodsHolder) holder).n_v_bear_dangqianjia.setVisibility(View.VISIBLE);
                ((GoodsHolder) holder).n_v_bear_dangqianjia.setText(String.format("金熊价 ¥%s", discountProduct.getMembershipPrice()));
            } else {
                if (discountProduct.getIsDiscount() == 1) {
                    if (discountProduct.getModel() == 1) {
                        ((GoodsHolder) holder).n_v_bear_dangqianjia.setText(String.format("¥%s", discountProduct.getPrice()));
                        ((GoodsHolder) holder).n_v_bear_dangqianjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    } else {
                        ((GoodsHolder) holder).n_v_bear_dangqianjia.setText(String.format("¥%s", discountProduct.getSourcePrice()));
                        ((GoodsHolder) holder).n_v_bear_dangqianjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    }
                } else {
                    ((GoodsHolder) holder).n_v_bear_dangqianjia.setText(String.format("¥%s", discountProduct.getPrice()));
                    ((GoodsHolder) holder).n_v_bear_dangqianjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                }
            }

            if (discountProduct.getIsDiscount() == 1) {
                if (discountProduct.getModel() == 0) {
                    ((GoodsHolder) holder).n_v_yuanjia.setVisibility(View.VISIBLE);
                    ((GoodsHolder) holder).n_v_yuanjia.setText(String.format("¥%s", discountProduct.getSourcePrice()));
                    ((GoodsHolder) holder).n_v_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                } else if (discountProduct.getModel() == 1) {
                    ((GoodsHolder) holder).n_v_bear_yuanjia.setVisibility(View.VISIBLE);
                    ((GoodsHolder) holder).n_v_bear_yuanjia.setText(String.format("金熊价 ¥%s", discountProduct.getSourceMembershipPrice()));
                    ((GoodsHolder) holder).n_v_bear_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                } else if (discountProduct.getModel() == 2) {
                    ((GoodsHolder) holder).n_v_yuanjia.setVisibility(View.VISIBLE);
                    ((GoodsHolder) holder).n_v_yuanjia.setText(String.format("¥%s", discountProduct.getSourcePrice()));
                    ((GoodsHolder) holder).n_v_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                    ((GoodsHolder) holder).n_v_bear_yuanjia.setVisibility(View.VISIBLE);
                    ((GoodsHolder) holder).n_v_bear_yuanjia.setText(String.format("金熊价 ¥%s", discountProduct.getSourceMembershipPrice()));
                    ((GoodsHolder) holder).n_v_bear_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                } else {
                    ((GoodsHolder) holder).n_v_yuanjia.setVisibility(View.GONE);
                    ((GoodsHolder) holder).n_v_bear_yuanjia.setVisibility(View.GONE);
                }
            } else {
                ((GoodsHolder) holder).n_v_yuanjia.setVisibility(View.GONE);
                ((GoodsHolder) holder).n_v_bear_yuanjia.setVisibility(View.GONE);
            }

            ((GoodsHolder) holder).n_v_tj_name.setText(discountProduct.getProductName());
            ((GoodsHolder) holder).n_v_tj_maiguo.setText(String.format("%d人付款", discountProduct.getSales()));


            ((GoodsHolder) holder).n_v_tj_goumai.setTag(discountProduct);
            ((GoodsHolder) holder).n_v_tj_goumai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemBean.DataBean.DiscountProductListBean spBean = (ItemBean.DataBean.DiscountProductListBean) view.getTag();
                    Intent intent = new Intent(context, NewProductDetailActivity.class);
                    intent.putExtra("productId", "" + spBean.getProduct_id());
                    intent.putExtra("sku_id", "" + spBean.getSku_id());
                    context.startActivity(intent);
                }
            });

            ((GoodsHolder) holder).n_v_tj_xiangqing.setTag(discountProduct);
            ((GoodsHolder) holder).n_v_tj_xiangqing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemBean.DataBean.DiscountProductListBean spBean = (ItemBean.DataBean.DiscountProductListBean) view.getTag();
                    Intent intent = new Intent(context, NewProductDetailActivity.class);
                    intent.putExtra("productId", "" + spBean.getProduct_id());
                    intent.putExtra("sku_id", "" + spBean.getSku_id());
                    context.startActivity(intent);
                }
            });
            Glide.with(context).load(discountProduct.getProductImages().getSource()).into(((GoodsHolder) holder).n_v_tj_image);
        }


    }

    @Override
    public int getItemCount() {
        return discountProductListBeans.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_GOODS;
    }


    class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class GoodsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.n_v_tj_xiangqing)
        LinearLayout n_v_tj_xiangqing;
        @BindView(R.id.n_v_tj_goumai)
        Button n_v_tj_goumai;
        @BindView(R.id.n_v_tj_image)
        ImageView n_v_tj_image;
        @BindView(R.id.n_v_tj_maiguo)
        TextView n_v_tj_maiguo;
        @BindView(R.id.n_v_tj_name)
        TextView n_v_tj_name;
        @BindView(R.id.n_v_dangqianjia)
        TextView n_v_dangqianjia;
        @BindView(R.id.n_v_yuanjia)
        TextView n_v_yuanjia;
        @BindView(R.id.n_v_bear_dangqianjia)
        TextView n_v_bear_dangqianjia;
        @BindView(R.id.n_v_bear_yuanjia)
        TextView n_v_bear_yuanjia;

        public GoodsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private void advClick(int type, int skiptype, long sourceId) {
        IAdvClick.click(context, type, skiptype, sourceId);
    }
}
