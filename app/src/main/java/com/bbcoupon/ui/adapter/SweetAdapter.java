package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SweetRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/16
 */
public class SweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SweetRecord.RecordBean> incomeDetail;

    public SweetAdapter(Context context) {
        this.context = context;
        this.incomeDetail = new ArrayList<>();
    }

    public void addList(List<SweetRecord.RecordBean> incomeDetail) {
        this.incomeDetail.addAll(incomeDetail);
        notifyDataSetChanged();
    }

    public void deleteList() {
        this.incomeDetail.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_sweet, parent, false);
        return new SweetHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SweetHolder sweetHolder = (SweetHolder) holder;
        sweetHolder.sw_title.setText(incomeDetail.get(position).getMemo());
        sweetHolder.sw_time.setText(incomeDetail.get(position).getCreatedDate());
        sweetHolder.sw_sume.setText(incomeDetail.get(position).getCredit());
    }

    @Override
    public int getItemCount() {
        return incomeDetail.size();
    }

    private class SweetHolder extends RecyclerView.ViewHolder {

        private final TextView sw_title;
        private final TextView sw_time;
        private final TextView sw_sume;

        public SweetHolder(View itemView) {
            super(itemView);
            sw_title = itemView.findViewById(R.id.sw_title);
            sw_time = itemView.findViewById(R.id.sw_time);
            sw_sume = itemView.findViewById(R.id.sw_sume);
        }

    }
}
