package com.yunqin.bearmall.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ItemBusinessBean;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/3/30
 */
public class BusinessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ItemBusinessBean.DataBean> list;

    //设置图片圆角角度
    private RoundedCorners roundedCorners = new RoundedCorners(10);
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

    public BusinessAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void addData(List<ItemBusinessBean.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adapter_business, parent, false);
        return new BusinessHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BusinessHolder businessHolder = (BusinessHolder) holder;

        businessHolder.bus_nickname.setText("大熊酷朋");
        businessHolder.bus_time.setText(list.get(position).getCreateTime());
        businessHolder.share_nume.setText(list.get(position).getShareNum() + "");
        businessHolder.bus_content.setText(Html.fromHtml(list.get(position).getItemDesc()));
        businessHolder.bus_coupon.setText("¥" + list.get(position).getPrice());
        businessHolder.bus_amount.setText("¥" + list.get(position).getCouponAmount());
        businessHolder.bus_password.setText("緮置这条口令,去【tao宝】下单");
        Glide.with(context)
                .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small))
                .load(list.get(position).getItemIcon())
                .apply(options).into(businessHolder.bus_image_min);
        businessHolder.bus_content_min.setText(list.get(position).getItemName());
        try {
            businessHolder.bus_profit.setText("¥" + CommonUtils.doubleToString(list.get(position).getCommision()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] images = list.get(position).getImages().split(",");
        businessHolder.bus_recycle.setLayoutManager(new GridLayoutManager(context, 3));
        ImageAdapter imageAdapter = new ImageAdapter(context, images);
        businessHolder.bus_recycle.setAdapter(imageAdapter);

        businessHolder.share_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickShare != null) {
                    mOnClickShare.share(images, businessHolder.bus_content.getText().toString().replace("</br>", "\n"), list.get(position).getId(), 0);
                    list.get(position).setShareNum(list.get(position).getShareNum() + 1);
                    notifyDataSetChanged();
                }
            }
        });

        businessHolder.shope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, list.get(position).getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_THREE);
                context.startActivity(intent);
            }
        });

        businessHolder.shear_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickShare != null) {
                    String[] strings = new String[]{list.get(position).getItemIcon()};
                    mOnClickShare.share(images, businessHolder.bus_content.getText().toString().replace("</br>", "\n"), list.get(position).getId(), 0);
                    list.get(position).setShareNum(list.get(position).getShareNum() + 1);
                    notifyDataSetChanged();
                }
            }
        });

        businessHolder.bus_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickShare != null) {
                    mOnClickShare.copy(list.get(position).getItemId());
                }
            }
        });

        businessHolder.bus_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null,
                        businessHolder.bus_content.getText().toString().replace("</br>", "\n")));
                Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BusinessHolder extends RecyclerView.ViewHolder {

        private final TextView bus_nickname, bus_time, share_nume, bus_content, bus_coupon, bus_amount, bus_profit, bus_password,
                bus_content_min, bus_copy;
        private final ImageView bus_image_min;
        private final CircleImageView bus_image;
        private final RecyclerView bus_recycle;
        private final LinearLayout share_bus, shear_min, shope;

        public BusinessHolder(View itemView) {
            super(itemView);
            bus_nickname = itemView.findViewById(R.id.bus_nickname);
            bus_time = itemView.findViewById(R.id.bus_time);
            share_nume = itemView.findViewById(R.id.share_nume);
            bus_content = itemView.findViewById(R.id.bus_content);
            bus_image_min = itemView.findViewById(R.id.bus_image_min);
            bus_image = itemView.findViewById(R.id.bus_image);
            bus_coupon = itemView.findViewById(R.id.bus_coupon);
            bus_amount = itemView.findViewById(R.id.bus_amount);
            bus_profit = itemView.findViewById(R.id.bus_profit);
            bus_password = itemView.findViewById(R.id.bus_password);
            bus_content_min = itemView.findViewById(R.id.bus_content_min);
            bus_recycle = itemView.findViewById(R.id.bus_recycle);
            share_bus = itemView.findViewById(R.id.share_bus);
            shear_min = itemView.findViewById(R.id.shear_min_s);
            bus_copy = itemView.findViewById(R.id.bus_copy);
            shope = itemView.findViewById(R.id.shope);
        }
    }

    public interface onClickShare {
        void share(String[] strings, String title, int id, int i);

        void copy(String id);
    }

    public onClickShare mOnClickShare;

    public void setOnClickShare(onClickShare mOnClickShare) {
        this.mOnClickShare = mOnClickShare;
    }
}
