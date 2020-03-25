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
import com.bumptech.glide.request.RequestOptions;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SuperSearch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2019/10/30
 */
public class SuperSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<SuperSearch.DataBean> list;

    public SuperSearchAdapter(Context context, List<SuperSearch.DataBean> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_priduct_sum, parent, false);
        return new SuperSearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SuperSearchHolder searchHolder = (SuperSearchHolder) holder;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(searchHolder.shous.getLayoutParams());

        if (position % 2 == 0) {
            params.setMargins(10, 0, 5, 15);
        } else {
            params.setMargins(5, 0, 10, 15);
        }
        searchHolder.shous.setLayoutParams(params);

        searchHolder.qh_5.setVisibility(View.GONE);
        Glide.with(mContext)
                .load(list.get(position).getPict_url())
                .apply(new RequestOptions().placeholder(R.drawable.default_product))
                .into(searchHolder.mPro_image);
        searchHolder.mPro_title.setText(list.get(position).getTao_title());

        searchHolder.mSeller_name.setText(StringUtils.addImageLabel(mContext, list.get(position).getUser_type().equals("1") ?
                R.mipmap.icon_tmall :
                R.mipmap.icon_taobao1, list.get(position).getShop_title()));
        searchHolder.mPro_quan.setText(list.get(position).getCoupon_info_money() + "元券");
        Double aDouble = Double.valueOf(list.get(position).getTkfee3());
        BigDecimal bigDecimal = new BigDecimal(aDouble);
        String mon = bigDecimal.setScale(2, RoundingMode.DOWN).toString();

        searchHolder.mTv_commision.setText("预估收益¥" + mon);
        searchHolder.mPro_yuanjia.setText("¥" + list.get(position).getSize());
        searchHolder.mPro_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        searchHolder.mXiaoliang.setText("月销" + list.get(position).getVolume());
        searchHolder.mQuanhoujia.setText("" + list.get(position).getQuanhou_jiage());

        searchHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getTao_id() != null && mId != null) {
                    mId.getGoodsId(list.get(position).getTao_id());
                }
            }
        });
    }

    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class SuperSearchHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout shous;
        private final ImageView mPro_image;
        private final TextView mPro_title;
        private final TextView mSeller_name;
        private final TextView mPro_quan;
        private final TextView mTv_commision;
        private final TextView mPro_yuanjia;
        private final TextView mXiaoliang;
        private final TextView mQuanhoujia;
        private final TextView qh_5;

        public SuperSearchHolder(View itemView) {
            super(itemView);
            shous = itemView.findViewById(R.id.shous);
            mPro_image = itemView.findViewById(R.id.item_home_pro_image);
            mPro_title = itemView.findViewById(R.id.item_home_pro_title);
            mSeller_name = itemView.findViewById(R.id.item_seller_name);
            mPro_quan = itemView.findViewById(R.id.item_home_pro_quan);
            mTv_commision = itemView.findViewById(R.id.tv_commision);
            mPro_yuanjia = itemView.findViewById(R.id.item_home_pro_yuanjia);
            mXiaoliang = itemView.findViewById(R.id.item_home_xiaoliang);
            mQuanhoujia = itemView.findViewById(R.id.item_home_pro_quanhoujia);
            qh_5 = itemView.findViewById(R.id.qh_5);
        }
    }

    public interface goodsId {
        void getGoodsId(String id);
    }

    private goodsId mId;

    public void setgoodsId(goodsId mId) {
        this.mId = mId;
    }
}
