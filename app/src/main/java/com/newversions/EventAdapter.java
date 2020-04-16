package com.newversions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ActivityMessageData;
import com.yunqin.bearmall.ui.activity.ShopActivity;
import com.yunqin.bearmall.util.CornerTransform;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/2/19 14:50
 */
public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<ActivityMessageData.DataBean.ListBean> mLists;
    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo);

    public EventAdapter(Context mContext, List<ActivityMessageData.DataBean.ListBean> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
    }

    @Override
    public int getItemViewType(int position) {
        if (mLists.get(position).getTargetType() == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setData(List<ActivityMessageData.DataBean.ListBean> list) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }

        if (list != null) {
            mLists.clear();
            mLists.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void addData(List<ActivityMessageData.DataBean.ListBean> list) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }

        if (list != null) {
            mLists.addAll(list);
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_product_message_activity, parent, false);
            return new MYHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_product_message, parent, false);
            return new MYHolder2(view);
        }
    }

    //只是绘制左上角和右上角圆角
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MYHolder) {
            MYHolder myHolder = (MYHolder) holder;
            CornerTransform transformation4 = new CornerTransform(mContext, dip2px(mContext, 5));
            transformation4.setExceptCorner(false, false, false, false);
            Glide.with(mContext).load(mLists.get(position).getArticleImage()).apply(new RequestOptions().placeholder(R.drawable.default_product).transform(transformation4)).into(myHolder.image);
            myHolder.product_message_title.setText(mLists.get(position).getTitle());
            myHolder.product_message_content.setText(mLists.get(position).getContent());
            myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("store_id", mLists.get(position).getArticle_id());
                    StarActivityUtil.starActivity((Activity) mContext, ShopActivity.class, bundle);
                }
            });
        }
        if (holder instanceof MYHolder2) {
            MYHolder2 myHolder2 = (MYHolder2) holder;
            CornerTransform transformation4 = new CornerTransform(mContext, dip2px(mContext, 5));
            transformation4.setExceptCorner(false, false, false, false);
            Glide.with(mContext).load(mLists.get(position).getArticleImage()).apply(new RequestOptions().placeholder(R.drawable.default_product).transform(transformation4)).into(myHolder2.image);
            myHolder2.product_message_title.setText(mLists.get(position).getTitle());
            myHolder2.product_message_content.setText(mLists.get(position).getContent());
            myHolder2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_ID, mLists.get(position).getArticle_id());
                    intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                    intent.putExtra(Constants.INTENT_KEY_COMM, "0");
                    intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_THREE);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public class MYHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView product_message_title;
        TextView product_message_content;

        public MYHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            product_message_title = itemView.findViewById(R.id.product_message_title);
            product_message_content = itemView.findViewById(R.id.product_message_content);
        }
    }

    public class MYHolder2 extends RecyclerView.ViewHolder {

        ImageView image;
        TextView product_message_title;
        TextView product_message_content;

        public MYHolder2(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            product_message_title = itemView.findViewById(R.id.product_message_title);
            product_message_content = itemView.findViewById(R.id.product_message_content);
        }
    }
}
