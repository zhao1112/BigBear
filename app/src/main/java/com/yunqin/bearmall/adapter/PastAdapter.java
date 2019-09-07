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
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.bean.ZeroPastData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenchen on 2018/8/25.
 */

public class PastAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<T> data;
    private int type;

    public PastAdapter(Context context, List<T> data,int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_past,parent,false);

        return new PastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PastViewHolder mHolder = (PastViewHolder) holder;

        int i = position%data.size();

        try {
            if (type == 0){

                Treasure treasure = (Treasure) data.get(position%data.size());

                mHolder.nickNameView.setText(treasure.getNickName());

                mHolder.openTimeView.setText(treasure.getParticipationDate());

                mHolder.goodsNameView.setText("+"+treasure.getReward());

                mHolder.userNumView.setText(treasure.getMember_id());

                Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(treasure.getIconUrl()).into(mHolder.avaterImage);

                Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment)).load(treasure.getImg()).into(mHolder.goodsImage);

            }else {

                ZeroPastData.ZeroPast past = (ZeroPastData.ZeroPast) data.get(position%data.size());

                mHolder.nickNameView.setText(past.getNickName());

                mHolder.openTimeView.setText(past.getEndDate());

                mHolder.goodsNameView.setText(past.getProductName());

                mHolder.userNumView.setText(past.getBigBearNumber());

                Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(past.getIconUrl()).into(mHolder.avaterImage);

                Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment)).load(past.getProductImages().getThumbnail()).into(mHolder.goodsImage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        int count =  (data==null||data.isEmpty())?0:data.size();
        return count;
    }


    class PastViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image)
        ImageView avaterImage;
        @BindView(R.id.nic_name)
        TextView nickNameView;
        @BindView(R.id.user_num)
        TextView userNumView;
        @BindView(R.id.open_time)
        TextView openTimeView;
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @BindView(R.id.goods_name)
        TextView goodsNameView;

        public PastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
