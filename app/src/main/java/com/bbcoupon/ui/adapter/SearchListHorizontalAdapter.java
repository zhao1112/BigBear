package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SearchData;

import java.util.ArrayList;
import java.util.List;

import static com.bbcoupon.ui.adapter.SearchListHorizontalAdapter.FRAGMENT_TYPE.VIEW_TYPE_ACTIVE_TITLE;
import static com.bbcoupon.ui.adapter.SearchListHorizontalAdapter.FRAGMENT_TYPE.VIEW_TYPE_IMAGE_FOUR;

public class SearchListHorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SearchData.ListBean> list;

    public void addList(List<SearchData.ListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanList() {
        this.list.clear();
    }

    public SearchListHorizontalAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_IMAGE_FOUR;
        } else {
            return VIEW_TYPE_ACTIVE_TITLE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_IMAGE_FOUR:
                view = LayoutInflater.from(context).inflate(R.layout.item_search_find, parent, false);
                return new OneHolder(view);
            case VIEW_TYPE_ACTIVE_TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.item_priduct_sum, parent, false);
                return new ProductSunHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_IMAGE_FOUR:
                break;
            case VIEW_TYPE_ACTIVE_TITLE:
                ProductSunHolder productSunHolder = (ProductSunHolder) holder;
                int i = position - 1;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(productSunHolder.mHome_bg.getLayoutParams());
                if (i % 2 == 0) {
                    params.setMargins(15, 0, 5, 10);
                } else {
                    params.setMargins(5, 0, 15, 10);
                }
                productSunHolder.mHome_bg.setLayoutParams(params);
                productSunHolder.qh_5.setVisibility(View.GONE);
                productSunHolder.itemHomeProTitle.setText(list.get(i).getName());
                try {
                    productSunHolder.itemHomeProYuanjia.setText("¥" + list.get(i).getPrice());
                    productSunHolder.itemHomeProQuanhoujia.setText(list.get(i).getDiscountPrice() + "");
                    productSunHolder.tvCommision.setText("预估收益¥" + list.get(i).getCommision());
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
                        if (mOnClickHorizontalItem != null) {
                            mOnClickHorizontalItem.onListItem(i, list.get(i));
                        }
                    }
                });
                break;
        }
    }

    @IntDef({VIEW_TYPE_ACTIVE_TITLE, VIEW_TYPE_IMAGE_FOUR})
    public @interface FRAGMENT_TYPE {
        int VIEW_TYPE_ACTIVE_TITLE = 5;
        int VIEW_TYPE_IMAGE_FOUR = 10;
    }

    public int getItemColumnCount(int position) {
        //  2019/7/13 0013 根据itemType返回跨的列数
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ACTIVE_TITLE:
                return 5;
            case VIEW_TYPE_IMAGE_FOUR:
                return 10;
            default:
                return 10;
        }
    }


    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    private class ProductSunHolder extends RecyclerView.ViewHolder {

        private final ImageView itemHomeProImage;
        private final TextView itemHomeProTitle, itemHomeProQuan, itemHomeXiaoliang, itemHomeProQuanhoujia, itemHomeProYuanjia,
                tvCommision, itemSellerName, qh_5;
        private final RelativeLayout mHome_bg;

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
            mHome_bg = itemView.findViewById(R.id.home_bg);
            qh_5 = itemView.findViewById(R.id.qh_5);
        }
    }

    private class OneHolder extends RecyclerView.ViewHolder {

        private final TextView goodes_name;

        public OneHolder(View itemView) {
            super(itemView);
            goodes_name = itemView.findViewById(R.id.goodes_name);
        }
    }

    public interface onClickHorizontalItem {
        void onListItem(int position, SearchData.ListBean bean);
    }

    public onClickHorizontalItem mOnClickHorizontalItem;

    public void setOnClickHorizontalItem(onClickHorizontalItem mOnClickHorizontalItem) {
        this.mOnClickHorizontalItem = mOnClickHorizontalItem;
    }
}
