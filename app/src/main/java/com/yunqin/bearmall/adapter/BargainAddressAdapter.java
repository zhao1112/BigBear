package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.ui.activity.BargainFreeShareActivity;

import java.util.List;

public class BargainAddressAdapter extends BaseAdapter{

    private Context mContext;
    private List<AddressListBean.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    public BargainAddressAdapter(Context context, List<AddressListBean.DataBean> list) {
        this.mContext = context;
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<AddressListBean.DataBean> list){
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressListBean.DataBean dataBean = mList.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_bargain_free_list_address,null);
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.bargain_address_name);
            viewHolder.tv_phone = (TextView)convertView.findViewById(R.id.bargain_address_phone);
            viewHolder.tv_address = (TextView)convertView.findViewById(R.id.bargain_address_detail);
            viewHolder.img_default = (ImageView)convertView.findViewById(R.id.bargain_address_default);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_name.setText(dataBean.getConsignee());
        viewHolder.tv_phone.setText(dataBean.getPhone());
        viewHolder.tv_address.setText(dataBean.getAreaName()+dataBean.getAddress());
        if(dataBean.isIsDefault()){
            viewHolder.img_default.setVisibility(View.VISIBLE);
        }else{
            viewHolder.img_default.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onReceiverIdListener != null){
                    onReceiverIdListener.onReceiverId(dataBean);
                }
            }
        });


        return convertView;
    }


    class ViewHolder{
        TextView tv_name,tv_phone,tv_address;
        ImageView img_default;
    }

    public OnReceiverIdListener onReceiverIdListener;

    public interface OnReceiverIdListener{
        void onReceiverId(AddressListBean.DataBean receiver);
    }

    public void setOnReceiverIdListener(OnReceiverIdListener onReceiverIdListener) {
        this.onReceiverIdListener = onReceiverIdListener;
    }
}
