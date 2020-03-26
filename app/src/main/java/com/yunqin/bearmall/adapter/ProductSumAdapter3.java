package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.tbk.entity.GoodsEntity;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SearchData;
import com.yunqin.bearmall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/1/13
 */
public class ProductSumAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<SearchData.DataBean> list;

    public void addList(List<SearchData.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanList() {
        this.list.clear();
    }

    public ProductSumAdapter3(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_priduct_sum, parent, false);
        return new ProductSunHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductSunHolder productSunHolder = (ProductSunHolder) holder;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(productSunHolder.mHome_bg.getLayoutParams());
        if (position % 2 == 0) {
            params.setMargins(15, 0, 5, 10);
        } else {
            params.setMargins(5, 0, 15, 10);
        }
        productSunHolder.mHome_bg.setLayoutParams(params);
        productSunHolder.qh_5.setVisibility(View.GONE);
        productSunHolder.itemHomeProTitle.setText(list.get(position).getName());
        try {
            productSunHolder.itemHomeProYuanjia.setText("¥" + list.get(position).getPrice());
            productSunHolder.itemHomeProQuanhoujia.setText(list.get(position).getDiscountPrice() + "");
            productSunHolder.tvCommision.setText("预估收益¥" + list.get(position).getCommision());
            productSunHolder.itemHomeProQuan.setText(list.get(position).getCouponAmount() + "元券");
        } catch (Exception e) {
            e.printStackTrace();
        }
        productSunHolder.itemHomeProYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰
        Glide.with(context)
                .load(list.get(position).getOutIcon())
                .into(productSunHolder.itemHomeProImage);
        productSunHolder.itemHomeXiaoliang.setText("月销" + list.get(position).getSellNum());
        productSunHolder.itemSellerName.setText(StringUtils.addImageLabel(context, list.get(position).getTmall().equals("1") ?
                R.mipmap.icon_tmall : R.mipmap.icon_taobao1, list.get(position).getSellerName()));
        productSunHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickProductSumItem != null) {
                    mOnClickProductSumItem.onItem(position, list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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

    public interface onClickProductSumItem {
        void onItem(int position, SearchData.DataBean bean);
    }

    public onClickProductSumItem mOnClickProductSumItem;

    public void setOnClickProductSumItem(onClickProductSumItem onClickProductSumItem) {
        mOnClickProductSumItem = onClickProductSumItem;
    }
}
