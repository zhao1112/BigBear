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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.HotBean;

import java.util.ArrayList;
import java.util.List;

public class Juhuasuan_Time_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<HotBean.CommodityListBean> list;
    //设置图片圆角角度
    RoundedCorners roundedCorners = new RoundedCorners(8);
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    private String cid;

    public void addList(List<HotBean.CommodityListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanList() {
        this.list.clear();
    }

    public Juhuasuan_Time_Adapter(Context context, String cid) {
        this.context = context;
        this.list = new ArrayList<>();
        this.cid = cid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_juhuasuan, parent, false);
        return new ProductSunHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Juhuasuan_Time_Adapter.ProductSunHolder productSunHolder = (Juhuasuan_Time_Adapter.ProductSunHolder) holder;
        if (position == 0) {
            productSunHolder.text_one.setText("1");
            productSunHolder.text_one.setVisibility(View.VISIBLE);
        }
        if (position == 1) {
            productSunHolder.text_one.setText("2");
            productSunHolder.text_one.setVisibility(View.VISIBLE);
        }
        if (position == 2) {
            productSunHolder.text_one.setText("3");
            productSunHolder.text_one.setVisibility(View.VISIBLE);
        }

        productSunHolder.itemHomeProTitle.setText(list.get(position).getName());
        try {
            productSunHolder.itemHomeProYuanjia.setText("¥" + list.get(position).getPrice());
            productSunHolder.itemHomeProQuanhoujia.setText("¥" + list.get(position).getDiscountPrice());
            productSunHolder.tvCommision.setText("预估收益¥" + list.get(position).getCommision());
            productSunHolder.itemHomeProQuan.setText(list.get(position).getCouponAmount() + "元券");
        } catch (Exception e) {
            e.printStackTrace();
        }
        productSunHolder.itemHomeProYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰

        Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(list.get(position).getOutIcon()).apply(options).into(productSunHolder.itemHomeProImage);


        if (cid.equals("0")){
            productSunHolder.boot2.setBackground(context.getResources().getDrawable(R.drawable.app_ju_one));
            productSunHolder.bot_text.setBackground(context.getResources().getDrawable(R.drawable.app_juhu_one));
            productSunHolder.bot_text.setText("马上抢");
            productSunHolder.itemHomeXiaoliang.setTextColor(context.getResources().getColor(R.color.juone));
        }else {
            productSunHolder.boot2.setBackground(context.getResources().getDrawable(R.drawable.app_ju_two));
            productSunHolder.bot_text.setBackground(context.getResources().getDrawable(R.drawable.app_juhu_two));
            productSunHolder.bot_text.setText("即将开团");
            productSunHolder.itemHomeXiaoliang.setTextColor(context.getResources().getColor(R.color.jutwo));
        }

        productSunHolder.itemHomeXiaoliang.setText("已抢" + list.get(position).getSellNum());
        productSunHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickProductSumItem2 != null) {
                    if (cid.equals("0")){
                        mOnClickProductSumItem2.onItem(position, list.get(position));
                    }
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
                tvCommision, text_one,bot_text;
        private final RelativeLayout boot2;

        public ProductSunHolder(View itemView) {
            super(itemView);
            itemHomeProImage = itemView.findViewById(R.id.item_home_pro_image);
            itemHomeProTitle = itemView.findViewById(R.id.item_home_pro_title);
            itemHomeProQuan = itemView.findViewById(R.id.item_home_pro_quan);
            itemHomeXiaoliang = itemView.findViewById(R.id.item_home_xiaoliang);
            itemHomeProQuanhoujia = itemView.findViewById(R.id.item_home_pro_quanhoujia);
            itemHomeProYuanjia = itemView.findViewById(R.id.item_home_pro_yuanjia);
            tvCommision = itemView.findViewById(R.id.tv_commision);
            text_one = itemView.findViewById(R.id.text_one);
            boot2 = itemView.findViewById(R.id.boot2);
            bot_text = itemView.findViewById(R.id.bot_text);
        }
    }

    public interface onClickProductSumItem2 {
        void onItem(int position, HotBean.CommodityListBean bean);
    }

    public onClickProductSumItem2 mOnClickProductSumItem2;

    public void setOnClickProductSumItem(onClickProductSumItem2 onClickProductSumItem2) {
        mOnClickProductSumItem2 = onClickProductSumItem2;
    }
}
