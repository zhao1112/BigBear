package com.bbcoupon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SearchMatch;
import com.yunqin.bearmall.bean.SweetRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenchen on 2018/7/21.
 */

public class AssociationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchMatch.DataBean> datas;
    private Context context;

    public AssociationAdapter(Context context) {
        this.datas = new ArrayList<>();
        this.context = context;
    }

    public void addData(List<SearchMatch.DataBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_association, parent, false);
        return new AssociationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AssociationHolder recordHolder = (AssociationHolder) holder;
        recordHolder.conten_ass.setText(datas.get(position).getSearchValue());
        recordHolder.conten_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAssociationContent != null) {
                    onAssociationContent.setConten(datas.get(position).getSearchValue());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class AssociationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.conten_ass)
        TextView conten_ass;
        @BindView(R.id.conten_click)
        LinearLayout conten_click;

        public AssociationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnAssociationContent {
        void setConten(String conten);
    }

    public OnAssociationContent onAssociationContent;

    public void setOnAssociationContent(OnAssociationContent onAssociationContent) {
        this.onAssociationContent = onAssociationContent;
    }

}
