package com.newversions.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.activity.BargainFreeDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By Master
 * On 2019/1/3 16:13
 */
public class NewVersionKanJiaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<HomeBean.DataBean.BargainRecordBean.BargainListBean> list3;

    public NewVersionKanJiaAdapter(Context context, List<HomeBean.DataBean.BargainRecordBean.BargainListBean> list3) {
        this.context = context;
        this.list3 = list3;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.n_v_item_goods_show, parent, false);
        return new NormalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeBean.DataBean.BargainRecordBean.BargainListBean bargainListBean = list3.get(position);

        holder.itemView.setOnClickListener(this);

        if (holder instanceof NormalHolder) {

            ((NormalHolder) holder).kan_jia_gone.setVisibility(View.GONE);
            ((NormalHolder) holder).n_v_od.setVisibility(View.GONE);
            ((NormalHolder) holder).n_v_od_dlayout.setVisibility(View.GONE);
            ((NormalHolder) holder).n_v_od_klayout.setVisibility(View.VISIBLE);


            ((NormalHolder) holder).n_v_od_jiage.setText("¥" + bargainListBean.getPrice());

            ((NormalHolder) holder).n_v_od_name.setText(bargainListBean.getProductName());

            ((NormalHolder) holder).item_view.setTag(bargainListBean);
            ((NormalHolder) holder).item_view.setOnClickListener(this);

            try {
                Glide.with(context).load(bargainListBean.getProductImages().getSource()).into(((NormalHolder) holder).n_v_od_img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return list3.size();
    }

    @Override
    public void onClick(View view) {
        HomeBean.DataBean.BargainRecordBean.BargainListBean bargainListBean = (HomeBean.DataBean.BargainRecordBean.BargainListBean) view.getTag();
        Intent intent = new Intent(context, BargainFreeDetailActivity.class);
        intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, (long) bargainListBean.getBargainProduct_id());
        intent.putExtra(BargainFreeDetailActivity.BARGAIN_IS_ONGOING, false);
        context.startActivity(intent);
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.n_v_od_dlayout)
        LinearLayout n_v_od_dlayout;
        @BindView(R.id.n_v_od_klayout)
        LinearLayout n_v_od_klayout;
        @BindView(R.id.n_v_od_img)
        ImageView n_v_od_img;
        @BindView(R.id.n_v_od)
        ImageView n_v_od;
        @BindView(R.id.n_v_od_name)
        TextView n_v_od_name;
        @BindView(R.id.n_v_od_jiage)
        TextView n_v_od_jiage;
        @BindView(R.id.n_v_od_duijiage)
        TextView n_v_od_duijiage;
        @BindView(R.id.item_view)
        LinearLayout item_view;

        @BindView(R.id.kan_jia_gone)
        TextView kan_jia_gone;


        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
