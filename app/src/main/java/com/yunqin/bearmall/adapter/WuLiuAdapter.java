package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.RefundDetailsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Master
 * @create 2018/8/22 20:05
 */
public class WuLiuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<RefundDetailsBean.DataBean.AfterSalesDetailsBean.LogDetailsBean> mList;

    private final int VIEW_TYPE_TOP = 0;
    private final int VIEW_TYPE_CENTER = 1;
    private final int VIEW_TYPE_BOTTOM = 2;

    public WuLiuAdapter(Context mContext, List<RefundDetailsBean.DataBean.AfterSalesDetailsBean.LogDetailsBean> mList) {
        this.mContext = mContext;
        if (mList == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = mList;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TOP) {
            return new TopViewHolder(View.inflate(mContext, R.layout.physical_distribution_layout_top, null));
        } else if (viewType == VIEW_TYPE_BOTTOM) {
            return new BottomViewHolder(View.inflate(mContext, R.layout.physical_distribution_layout_bottom, null));
        }
        return new CenterViewHolder(View.inflate(mContext, R.layout.physical_distribution_layout_center, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof TopViewHolder) {
                ((TopViewHolder) holder).title.setText(mList.get(position).getType() + "");
                ((TopViewHolder) holder).time.setText(mList.get(position).getCreatedDate());
            } else if (holder instanceof CenterViewHolder) {
                ((CenterViewHolder) holder).title.setText(mList.get(position).getType() + "");
                ((CenterViewHolder) holder).time.setText(mList.get(position).getCreatedDate());
            } else if (holder instanceof BottomViewHolder) {
                ((BottomViewHolder) holder).title.setText(mList.get(position).getType() + "");
                ((BottomViewHolder) holder).time.setText(mList.get(position).getCreatedDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TOP;
        } else if (position == mList.size() - 1) {
            return VIEW_TYPE_BOTTOM;
        }
        return VIEW_TYPE_CENTER;
    }


    class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CenterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;

        public CenterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;

        public BottomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
