package com.bbcoupon.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SearchData;

import java.util.ArrayList;
import java.util.List;

public class SearchListVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<SearchData.ListBean> list;
    private final int one = 1;
    private final int two = 2;

    public void addList(List<SearchData.ListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanList() {
        this.list.clear();
    }

    public SearchListVerticalAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return one;
        } else {
            return two;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case one:
                view = LayoutInflater.from(context).inflate(R.layout.item_search_find, parent, false);
                return new OneHolder(view);
            case two:
                view = LayoutInflater.from(context).inflate(R.layout.item_serach_2, parent, false);
                return new ProductSunHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case one:
                OneHolder oneHolder = (OneHolder) holder;
                break;
            case two:
                ProductSunHolder productSunHolder = (ProductSunHolder) holder;
                int i = position - 1;
                productSunHolder.itemHomeProTitle.setText(list.get(i).getName());
                try {
                    productSunHolder.itemHomeProQuanhoujia.setText(list.get(i).getDiscountPrice() + "");
                    productSunHolder.tvCommision.setText("预估收益¥" + list.get(i).getCommision());
                    productSunHolder.itemHomeProYuanjia.setText("¥" + list.get(i).getPrice());
                    productSunHolder.itemHomeProQuan.setText(list.get(i).getCouponAmount() + "元券");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                productSunHolder.itemHomeProYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰
                Glide.with(context)
                        .load(list.get(i).getOutIcon())
                        .into(productSunHolder.itemHomeProImage);
                productSunHolder.itemHomeXiaoliang.setText("月销" + list.get(i).getSellNum());
                productSunHolder.itemSellerName.setText(StringUtils.addImageLabel(context, list.get(i).getTmall().equals("1") ?
                        R.mipmap.icon_tmall : R.mipmap.icon_taobao1, list.get(i).getSellerName()));
                productSunHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnClickVerticalItem != null) {
                            mOnClickVerticalItem.onListItem(i, list.get(i));
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    private class ProductSunHolder extends RecyclerView.ViewHolder {

        private final ImageView itemHomeProImage;
        private final TextView itemHomeProTitle, itemHomeProQuan, itemHomeXiaoliang, itemHomeProQuanhoujia, itemHomeProYuanjia,
                tvCommision, itemSellerName;

        public ProductSunHolder(View itemView) {
            super(itemView);
            itemHomeProImage = itemView.findViewById(R.id.item_home_pro_image);
            itemHomeProTitle = itemView.findViewById(R.id.item_home_pro_title);
            itemHomeProQuan = itemView.findViewById(R.id.item_home_pro_quan);
            itemHomeXiaoliang = itemView.findViewById(R.id.item_home_xiaoliang);
            itemHomeProQuanhoujia = itemView.findViewById(R.id.item_home_pro_quanhoujia);
            itemHomeProYuanjia = itemView.findViewById(R.id.item_home_pro_yuanjia);
            tvCommision = itemView.findViewById(R.id.tv_commision);
            itemSellerName = itemView.findViewById(R.id.item_seller_name);
        }
    }

    private class OneHolder extends RecyclerView.ViewHolder {

        private final TextView goodes_name;

        public OneHolder(View itemView) {
            super(itemView);
            goodes_name = itemView.findViewById(R.id.goodes_name);
        }
    }

    public interface onClickVerticalItem {
        void onListItem(int position, SearchData.ListBean bean);
    }

    public onClickVerticalItem mOnClickVerticalItem;

    public void setOnClickProductSumItem(onClickVerticalItem mOnClickVerticalItem) {
        this.mOnClickVerticalItem = mOnClickVerticalItem;
    }
}
