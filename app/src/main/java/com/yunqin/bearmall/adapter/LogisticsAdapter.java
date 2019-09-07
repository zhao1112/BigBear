package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.Logistics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Master
 * @create 2018/8/23 19:35
 */
public class LogisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Logistics.LogisticsListBean> mLogistics;


    private final int VIEW_TYPE_ = 0;
    private final int VIEW_TYPE_TOP = 1;
    private final int VIEW_TYPE_CENTER = 2;
    private final int VIEW_TYPE_BOTTOM = 3;

    public LogisticsAdapter(Context mContext, List<Logistics.LogisticsListBean> logistics) {
        this.mContext = mContext;
        if (logistics != null) {
            this.mLogistics = logistics;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TOP) {
            return new TopViewHolder(View.inflate(mContext, R.layout.physical_distribution_layout_top, null));
        } else if (viewType == VIEW_TYPE_BOTTOM) {
            return new BottomViewHolder(View.inflate(mContext, R.layout.physical_distribution_layout_bottom, null));
        } else if (viewType == VIEW_TYPE_) {
            return new OneViewHolder(View.inflate(mContext, R.layout.physical_distribution_layout_, null));
        }
        return new CenterViewHolder(View.inflate(mContext, R.layout.physical_distribution_layout_center, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof TopViewHolder) {
                ((TopViewHolder) holder).title.setText(mLogistics.get(position).getContext());
                ((TopViewHolder) holder).time.setText(mLogistics.get(position).getTime());
            } else if (holder instanceof CenterViewHolder) {
                ((CenterViewHolder) holder).title.setText(mLogistics.get(position).getContext());
                ((CenterViewHolder) holder).time.setText(mLogistics.get(position).getTime());
            } else if (holder instanceof BottomViewHolder) {
                ((BottomViewHolder) holder).title.setText(mLogistics.get(position).getContext());
                ((BottomViewHolder) holder).time.setText(mLogistics.get(position).getTime());
            } else if (holder instanceof OneViewHolder) {
                ((OneViewHolder) holder).title.setText(mLogistics.get(position).getContext());
                ((OneViewHolder) holder).time.setText(mLogistics.get(position).getTime());
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return mLogistics.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mLogistics.size() - 1 == 0) {
            return VIEW_TYPE_;
        }
        if (position == 0) {
            return VIEW_TYPE_TOP;
        } else if (position == mLogistics.size() - 1) {
            return VIEW_TYPE_BOTTOM;
        }
        return VIEW_TYPE_CENTER;
    }

    class OneViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;

        public OneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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