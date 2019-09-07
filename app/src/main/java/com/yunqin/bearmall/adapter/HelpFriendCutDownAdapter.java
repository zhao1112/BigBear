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
import com.yunqin.bearmall.bean.CutDownGetWhatBean;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.bean.ZeroPastData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mlxy.utils.T;

/**
 * @author AYWang
 * @create 2018/8/28
 * @Describe
 */
public class HelpFriendCutDownAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<T> data;

    public HelpFriendCutDownAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_help_cut_down_price, parent, false);

        return new HelpFriendCutDownViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        HelpFriendCutDownViewHolder mHolder = (HelpFriendCutDownViewHolder) holder;

        try {

            CutDownGetWhatBean.DataBean.BargainRecordListBean dataBean = (CutDownGetWhatBean.DataBean.BargainRecordListBean) data.get(position%data.size());

            mHolder.nickNameView.setText(dataBean.getNickName());

            mHolder.timeAgo.setText(dataBean.getTime());

            mHolder.goodsName.setText(dataBean.getContent());

            Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(dataBean.getIconUrl()).into(mHolder.avaterImage);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        int count = (data == null || data.isEmpty()) ? 0 : data.size();
        return count;
    }


    class HelpFriendCutDownViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView avaterImage;
        @BindView(R.id.nic_name)
        TextView nickNameView;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.time_ago)
        TextView timeAgo;

        public HelpFriendCutDownViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
