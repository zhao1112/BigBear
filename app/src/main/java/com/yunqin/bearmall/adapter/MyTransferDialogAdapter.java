package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Voucher;
import com.yunqin.bearmall.ui.activity.FindBTGiveWhoActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.LinTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class MyTransferDialogAdapter extends BaseAdapter {

    private Context mCOntext;
    private List<Voucher.DataBean.UsersTicketListBean>  listBean;

    public MyTransferDialogAdapter(Context mCOntext){
        this.mCOntext = mCOntext;
        listBean = new ArrayList<>();
    }

    public void setData(List<Voucher.DataBean.UsersTicketListBean>  listBean){
        this.listBean.clear();
        this.listBean.addAll(listBean);
        notifyDataSetChanged();

    }

    public List<Voucher.DataBean.UsersTicketListBean>  listBeanGet(){
        return  listBean;
    }

    public void addData(List<Voucher.DataBean.UsersTicketListBean>  listBean){
        this.listBean.addAll(listBean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        try {
            if(convertView == null){
                convertView = LayoutInflater.from(mCOntext).inflate(R.layout.voucher_item,null);
                viewHolder = new ViewHolder(convertView,position);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if(listBean.size()>0){
                viewHolder.use_it.setVisibility(View.INVISIBLE);
                viewHolder.lin_line.setVisibility(View.INVISIBLE);
                viewHolder.numbers.setText(listBean.get(position).getCount()+"次机会");
                viewHolder.transfer_bt_number.setText("转赠限额"+listBean.get(position).getAmount()+"BC");
                viewHolder.days.setText("还剩"+listBean.get(position).getExpireDays()+"天");
                viewHolder.time.setText("截至时间："+listBean.get(position).getLimitDate());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder{

        @BindView(R.id.numbers)
        TextView numbers;

        @BindView(R.id.transfer_bt_number)
        TextView transfer_bt_number;

        @BindView(R.id.days)
        TextView days;


        @BindView(R.id.time)
        TextView time;

        @BindView(R.id.use_it)
        LinearLayout use_it;

        @BindView(R.id.lin_line)
        View lin_line;

        public ViewHolder(View itemView,int position) {
            ButterKnife.bind(this, itemView);
        }
    }
}
