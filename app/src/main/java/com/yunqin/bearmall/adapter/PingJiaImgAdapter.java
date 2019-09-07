package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PingJiaImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<Bitmap> list;



    public List<Bitmap> getData(){
        return list;
    }

    public void setData(List<Bitmap> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<Bitmap> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(Bitmap bitmap){
        this.list.add(bitmap);
        notifyDataSetChanged();
    }


    public PingJiaImgAdapter(Context mContext, List<Bitmap> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = View.inflate(parent.getContext(), R.layout.item_pingjia1, null);
            return new IViewHolder(view);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_pingjia, null);
            return new IViewHolder1(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof IViewHolder) {
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment)).load(list.get(position)).into(((IViewHolder) holder).imageView);
                ((IViewHolder) holder).mCircleImageView.setTag(position);
                ((IViewHolder) holder).mCircleImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClickDel(PingJiaImgAdapter.this, ((int) ((IViewHolder) holder).mCircleImageView.getTag()));
                        }
                    }
                });
            } else {
                holder.itemView.setTag(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClickAdd(PingJiaImgAdapter.this, ((int) holder.itemView.getTag()));
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() == position) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public void onClick(View v) {


    }


    class IViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_del)
        CircleImageView mCircleImageView;

        @BindView(R.id.img)
        ImageView imageView;

        public IViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class IViewHolder1 extends RecyclerView.ViewHolder {

        public IViewHolder1(View itemView) {
            super(itemView);
        }
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickDel(PingJiaImgAdapter pingJiaImgAdapter, int index);
        void onItemClickAdd(PingJiaImgAdapter pingJiaImgAdapter, int index);
    }

}
