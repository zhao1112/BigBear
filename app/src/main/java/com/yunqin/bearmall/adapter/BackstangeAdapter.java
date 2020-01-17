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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BackstangeOrderBean;

import java.util.ArrayList;
import java.util.List;

public class BackstangeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BackstangeOrderBean.DataBean.OrdersBean> list;
    private RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_product)//图片加载出来前，显示的图片
            .fallback(R.drawable.default_product) //url为空的时候,显示的图片
            .error(R.drawable.default_product)//图片加载失败后，显示的图片
            .bitmapTransform(new RoundedCorners(3));


    public BackstangeAdapter(Context context) {
        mContext = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.backstangeadapteritem, parent, false);
        return new BackstangeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BackstangeHolder backstangeHolder = (BackstangeHolder) holder;
        //图片
        Glide.with(mContext).load(list.get(position) .getImage()).apply(mOptions).into(backstangeHolder.mBackAdpaterImage);
        backstangeHolder.mBackAdapterPrice.setText("+￥"+list.get(position).getSettlementAmount());
        backstangeHolder.mBackAdapterTitle.setText(list.get(position).getItemInfo());
        backstangeHolder.mBackAdpaterNumber.setText("订单编号:"+list.get(position).getOrderNo());
        //复制订单号
        backstangeHolder.mBackAdapterCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackstangeCopyListener!=null){
                    onBackstangeCopyListener.onCopyOrderNo(list.get(position).getOrderNo());
                }
            }
        });
        //跑马灯
        backstangeHolder.mBackAdpaterNumber.setSelected(true);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addData(List<BackstangeOrderBean.DataBean.OrdersBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
    }

    private class BackstangeHolder extends RecyclerView.ViewHolder {

        private final ImageView mBackAdpaterImage;
        private final TextView mBackAdapterTitle, mBackAdpaterNumber, mBackAdapterCopy, mBackAdapterPrice;

        public BackstangeHolder(View itemView) {
            super(itemView);
            //tup
            mBackAdpaterImage = itemView.findViewById(R.id.back_adapter_image);
            //标题
            mBackAdapterTitle = itemView.findViewById(R.id.back_adapter_title);
            //编号
            mBackAdpaterNumber = itemView.findViewById(R.id.back_adapter_number);
            //复制
            mBackAdapterCopy = itemView.findViewById(R.id.back_adapter_copy);
            //价格
            mBackAdapterPrice = itemView.findViewById(R.id.back_apdate_price);
        }
    }
    private OnBackstangeCopyListener onBackstangeCopyListener;

    public interface OnBackstangeCopyListener{
        void onCopyOrderNo(String orderNo);
   }
   public  void setOnBackstangeCopyListener(OnBackstangeCopyListener backstangeCopyListener){
        onBackstangeCopyListener=backstangeCopyListener;
   }

}
