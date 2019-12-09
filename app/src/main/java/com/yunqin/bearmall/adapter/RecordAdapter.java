package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SweetRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenchen on 2018/7/21.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SweetRecord.RecordBean> datas;
    private Context context;
    private int type;

    public RecordAdapter(List<SweetRecord.RecordBean> datas, Context context,int type) {
        this.datas = datas;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sweet_record,parent,false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try {
            RecordHolder recordHolder = (RecordHolder) holder;
            SweetRecord.RecordBean recordBean = datas.get(position);
            recordHolder.createDateView.setText(recordBean.getCreatedDate());
            recordHolder.memoView.setText(recordBean.getMemo());
            String credit = (type==0?"+"+recordBean.getCredit():recordBean.getDebit()) ;
            if (type == 0){

            }else {
                recordHolder.bearView.setImageResource(R.drawable.icon_bear_gray);
                recordHolder.creditView.setTextColor(Color.parseColor("#999999"));
            }
            recordHolder.creditView.setText(credit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    class RecordHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_memo)
        TextView memoView;
        @BindView(R.id.item_credit)
        TextView creditView;
        @BindView(R.id.item_date)
        TextView createDateView;
        @BindView(R.id.item_image)
        ImageView bearView;

        public RecordHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
