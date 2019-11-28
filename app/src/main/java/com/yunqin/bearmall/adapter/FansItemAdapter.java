package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.StairFans;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2019/11/28
 */
public class FansItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> list;
    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_product)//图片加载出来前，显示的图片
            .fallback(R.drawable.default_product) //url为空的时候,显示的图片
            .error(R.drawable.default_product)//图片加载失败后，显示的图片
            .circleCropTransform();

    public FansItemAdapter(Context context) {
        this.context = context;
        list = new LinkedList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof StairFans.DataBean.OneListBean) {
            return TYPE_ONE;
        } else if (list.get(position) instanceof StairFans.DataBean.TwoListBean) {
            return TYPE_TWO;
        }
        return TYPE_ONE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fans, parent, false);
        return new FansHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FansHoler fansHoler = (FansHoler) holder;
        switch (getItemViewType(position)) {
            case TYPE_ONE:
                StairFans.DataBean.OneListBean fansone = (StairFans.DataBean.OneListBean) list.get(position);
                String fansonephoe = phoe(fansone.getMobile());
                fansHoler.phone.setText(fansonephoe);
                fansHoler.time.setText(fansone.getCreatedDate());
                fansHoler.fansnumber.setText("粉丝" + fansone.getFans_count() + "人");
                Glide.with(context).load(fansone.getIconUrl()).apply(mOptions).into(fansHoler.image);
                break;
            case TYPE_TWO:
                StairFans.DataBean.TwoListBean fanstwo = (StairFans.DataBean.TwoListBean) list.get(position);
                String fanstwophoe = phoe(fanstwo.getMobile());
                fansHoler.phone.setText(fanstwophoe);
                fansHoler.time.setText(fanstwo.getCreatedDate());
                fansHoler.fansnumber.setText("粉丝" + fanstwo.getFans_count() + "人");
                Glide.with(context).load(fanstwo.getIconUrl()).apply(mOptions).into(fansHoler.image);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addFansOne(List<StairFans.DataBean.OneListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addFansTwo(List<StairFans.DataBean.TwoListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    private class FansHoler extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView phone, time, fansnumber;

        public FansHoler(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            phone = itemView.findViewById(R.id.phone);
            time = itemView.findViewById(R.id.time);
            fansnumber = itemView.findViewById(R.id.fansnumber);
        }
    }

    public String phoe(String args) {
        String phoneNumber = args.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phoneNumber;
    }

}
