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
import com.yunqin.bearmall.bean.PartnerOrderSeekBean;

import java.util.ArrayList;
import java.util.List;

public class PartnerOrderSeekAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<PartnerOrderSeekBean.DataBean.OrdersBean> list;
    private RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_product)//图片加载出来前，显示的图片
            .fallback(R.drawable.default_product) //url为空的时候,显示的图片
            .error(R.drawable.default_product)//图片加载失败后，显示的图片
            .bitmapTransform(new RoundedCorners(3));

    public PartnerOrderSeekAdapter(Context context) {
        mContext = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public PartnerOrderSeekAdapter.SeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_seek_item, parent, false);

        return new SeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SeekViewHolder seekViewHolder = (SeekViewHolder) holder;
        //图片
        Glide.with(mContext).load(list.get(position).getImage()).apply(mOptions).into(seekViewHolder.mrRereekAdapterImage);
        //订单号
        seekViewHolder.mOrderSeekAdapterNumber.setText("订单编号:"+list.get(position).getOrderNo());
        //名称
        seekViewHolder.mOrderSeekAdapterTitle.setText(list.get(position).getItemInfo());
        //价格
        seekViewHolder.mOrderSeekApdatePrice.setText("+￥"+list.get(position).getSettlementAmount());
        //复制
        seekViewHolder.mOrderSeekAdapterCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderSeekCopyListener!=null){
                    onOrderSeekCopyListener.onOrderSeekCopy(list.get(position).getOrderNo());
                }
            }
        });
        seekViewHolder.mOrderSeekAdapterNumber.setSelected(true);
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<PartnerOrderSeekBean.DataBean.OrdersBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
    }

    private class SeekViewHolder extends RecyclerView.ViewHolder {
        private ImageView mrRereekAdapterImage;
        private TextView mOrderSeekAdapterTitle, mOrderSeekAdapterNumber, mOrderSeekAdapterCopy, mOrderSeekApdatePrice;

        public SeekViewHolder(View itemView) {
            super(itemView);
            //图片
            mrRereekAdapterImage = itemView.findViewById(R.id.order_seek_adapter_image);
            //名称
            mOrderSeekAdapterTitle = itemView.findViewById(R.id.order_seek_adapter_title);
            //订单号
            mOrderSeekAdapterNumber = itemView.findViewById(R.id.order_seek_adapter_number);
            //复制
            mOrderSeekAdapterCopy = itemView.findViewById(R.id.order_seek_adapter_copy);
            //价格
            mOrderSeekApdatePrice = itemView.findViewById(R.id.order_seek_apdate_price);

        }
    }
    private OnOrderSeekCopyListener onOrderSeekCopyListener;
    private interface  OnOrderSeekCopyListener {
        void onOrderSeekCopy(String orderseek);
    }
    public void setOnOrderSeekCopyListener (OnOrderSeekCopyListener orderSeekCopyListener){
            onOrderSeekCopyListener =orderSeekCopyListener;
    }
}
