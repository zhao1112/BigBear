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
import com.newversions.NewOrderBean;
import com.newversions.NewOrderChildBean;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;

import java.util.List;

/**
 * @author Master
 */
public class MoreTypeViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<Object> mlist;

    public MoreTypeViewAdapter(Context mContext, List<Object> mlist) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderHolder(mInflater.inflate(R.layout.item_mall_title, parent, false));
            case TYPE_NORMAL:
                return new ViewHolder(mInflater.inflate(R.layout.item_goods, parent, false));
        }
        return null;
    }

    /**
     * List<NewOrderBean> list1
     * List<NewOrderChildBean> list2
     */


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof ViewHolder) {
                NewOrderChildBean newOrderChildBean = (NewOrderChildBean) mlist.get(position);
                ((ViewHolder) holder).iGoodsNameTextView.setText(newOrderChildBean.getName());
                ((ViewHolder) holder).iGoodsCountTextView.setText("X" + newOrderChildBean.getCount());
                try {
                    Glide.with(mContext)
                            .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_shopcar_product)).
                            load(newOrderChildBean.getImgUrl())
                            .into(((ViewHolder) holder).iGoodsImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                ((ViewHolder) holder).iGoodsPriceTextView.setText("¥" + newOrderChildBean.getPrice());
                // 需要判断是否是会员价支付


                ((ViewHolder) holder).iGoodsColorTypeTextView.setText(newOrderChildBean.getGuiGe());
            } else if (holder instanceof HeaderHolder) {
                NewOrderBean newOrderBean = (NewOrderBean) mlist.get(position);
                ((HeaderHolder) holder).iMallTitleTextView.setText(newOrderBean.getName());
                try {
                    Glide.with(mContext)
                            .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                            .load(newOrderBean.getImgUrl())
                            .into(((HeaderHolder) holder).iMallImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // ...
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mlist.get(position) instanceof NewOrderBean ? TYPE_HEADER : TYPE_NORMAL;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iGoodsImageView;
        private TextView iGoodsNameTextView;
        private TextView iGoodsColorTypeTextView;
        private TextView iGoodsPriceTextView;
        private TextView iGoodsCountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            iGoodsImageView = itemView.findViewById(R.id.goods_pic);
            iGoodsNameTextView = itemView.findViewById(R.id.goods_name);
            iGoodsColorTypeTextView = itemView.findViewById(R.id.goods_color);
            iGoodsPriceTextView = itemView.findViewById(R.id.goods_price);
            iGoodsCountTextView = itemView.findViewById(R.id.goods_count);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        private ImageView iMallImageView;
        private TextView iMallTitleTextView;

        public HeaderHolder(View itemView) {
            super(itemView);
            iMallImageView = itemView.findViewById(R.id.mall_pic);
            iMallTitleTextView = itemView.findViewById(R.id.mall_title);
        }
    }


}
