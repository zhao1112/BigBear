package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbcoupon.ui.bean.MakeInfor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.adapter
 * @DATE 2020/5/29
 */
public class MakeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context contex;
    private List<MakeInfor.DataBean.DayOfTaskBean> list;
    private MakeInfor.DataBean.TaskBean taskLis;

    public MakeAdapter(Context contex) {
        this.contex = contex;
        this.list = new ArrayList<>();
        this.taskLis = new MakeInfor.DataBean.TaskBean();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.item_make, parent, false);
        return new MakeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MakeHolder makeHolder = (MakeHolder) holder;
        Glide.with(contex)
                .load(list.get(position).getImg())
                .apply(new RequestOptions().placeholder(R.drawable.default_product))
                .into(makeHolder.make_image);
        makeHolder.make_title.setText(list.get(position).getTask_name());
        makeHolder.make_content.setText(list.get(position).getTitle());

        //设置总糖果数
        switch (position) {
            case 0:
                makeHolder.make_sum.setText(" +" + taskLis.getUsersRegisterRewardMax());
                if (taskLis.getUsersRegisterFinish() == 0) {
                    makeHolder.make_clack.setText("签到");
                    makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.make_true));
                    makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.address_bg_green));
                } else {
                    makeHolder.make_clack.setText("已签到");
                    makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.white));
                    makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.make_bg));
                }
                break;
            case 1:
                makeHolder.make_sum.setText(" +" + taskLis.getFriendInvitMAX());
                makeHolder.make_clack.setText("立即分享");
                makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.make_true));
                makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.address_bg_green));
                break;
            case 2:
                makeHolder.make_sum.setText(" +" + taskLis.getMemberShareInfoMAX());
                if (taskLis.getMemberShareInfoFinish() == 0) {
                    makeHolder.make_clack.setText("立即分享");
                    makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.make_true));
                    makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.address_bg_green));
                } else {
                    makeHolder.make_clack.setText("已完成");
                    makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.white));
                    makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.make_bg));
                }
                break;
            case 3:
                makeHolder.make_sum.setText(" +" + taskLis.getMemberShareProductMAX());
                if (taskLis.getMemberShareProductFinish() == 0) {
                    makeHolder.make_clack.setText("立即分享");
                    makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.make_true));
                    makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.address_bg_green));
                } else {
                    makeHolder.make_clack.setText("已完成");
                    makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.white));
                    makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.make_bg));
                }
                break;
            case 4:
                makeHolder.make_sum.setText(" +" + taskLis.getLuckyDrawMAX());
                if (taskLis.getLuckyDrawFinish() == 0) {
                    makeHolder.make_clack.setText("立即领取");
                    makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.make_true));
                    makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.address_bg_green));
                } else {
                    makeHolder.make_clack.setText("已完成");
                    makeHolder.make_clack.setTextColor(contex.getResources().getColor(R.color.white));
                    makeHolder.make_clack.setBackground(contex.getResources().getDrawable(R.drawable.make_bg));
                }
                break;
        }

        //点击处理
        makeHolder.make_clack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMakeClick != null) {
                    onMakeClick.setMakeData(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addListData(List<MakeInfor.DataBean.DayOfTaskBean> list, MakeInfor.DataBean.TaskBean taskLis) {
        this.list.addAll(list);
        this.taskLis = taskLis;
        notifyDataSetChanged();
    }

    public void clearListData() {
        this.list.clear();
    }

    public class MakeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.make_image)
        ImageView make_image;
        @BindView(R.id.make_title)
        TextView make_title;
        @BindView(R.id.make_sum)
        TextView make_sum;
        @BindView(R.id.make_content)
        TextView make_content;
        @BindView(R.id.make_clack)
        TextView make_clack;

        public MakeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnMakeClick {
        void setMakeData(int i);
    }

    public OnMakeClick onMakeClick;

    public void setOnMakeClick(OnMakeClick onMakeClick) {
        this.onMakeClick = onMakeClick;
    }
}
