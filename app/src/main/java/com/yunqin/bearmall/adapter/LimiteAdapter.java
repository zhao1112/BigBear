package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.newversions.tbk.entity.GoodsEntity;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/1/13
 */
public class LimiteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<GoodsEntity.CommodityBean> list;
    RoundedCorners roundedCorners = new RoundedCorners(8);
    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

    public void addList(List<GoodsEntity.CommodityBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanList() {
        this.list.clear();
    }

    public LimiteAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_limited, parent, false);
        return new ProductSunHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductSunHolder productSunHolder = (ProductSunHolder) holder;

        productSunHolder.itemHomeProTitle.setText(StringUtils.addImageLabel(context, list.get(position).getTmall() == 1 ?
                R.mipmap.icon_tmall : R.mipmap.icon_taobao1, list.get(position).getName()));
        try {
            productSunHolder.itemHomeProYuanjia.setText("¥" + list.get(position).getPrice());

            String[] split = CommonUtils.doubleToString(list.get(position).getDiscountPrice()).split("\\.");
            String str2 = split[0] + ".<small>" + split[1] + "</small>";
            productSunHolder.itemHomeProQuanhoujia.setText(Html.fromHtml(str2));

            productSunHolder.tvCommision.setText("分享赚：¥" + list.get(position).getCommision());
            productSunHolder.itemHomeProQuan.setText(list.get(position).getCouponAmount() + "元券");
        } catch (Exception e) {
            e.printStackTrace();
        }
        productSunHolder.itemHomeProYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰
        Glide.with(context)
                .load(list.get(position).getOutIcon())
                .apply(options)
                .into(productSunHolder.itemHomeProImage);
        productSunHolder.itemHomeXiaoliang.setText(list.get(position).getSellNum() + " ");
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
                tvCommision;
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
            mHome_bg = itemView.findViewById(R.id.home_bg);
        }
    }

    public interface onClickProductSumItem {
        void onItem(int position, GoodsEntity.CommodityBean bean);
    }

    public onClickProductSumItem mOnClickProductSumItem;

    public void setOnClickProductSumItem(onClickProductSumItem onClickProductSumItem) {
        mOnClickProductSumItem = onClickProductSumItem;
    }
}
