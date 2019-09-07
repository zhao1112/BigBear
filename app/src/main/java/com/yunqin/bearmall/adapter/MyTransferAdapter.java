package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Voucher;
import com.yunqin.bearmall.eventbus.ProductMessageEvent;
import com.yunqin.bearmall.ui.activity.CommentActivity;
import com.yunqin.bearmall.ui.activity.FindBTGiveWhoActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.LinTextView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import mlxy.utils.L;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class MyTransferAdapter extends BaseAdapter {

    private Context mCOntext;
    private List<Voucher.DataBean.UsersTicketListBean>  listBean;

    public MyTransferAdapter(Context mCOntext){
        this.mCOntext = mCOntext;
        listBean = new ArrayList<>();
    }

    public void setData(List<Voucher.DataBean.UsersTicketListBean>  listBean){
        this.listBean.clear();
        this.listBean.addAll(listBean);
        notifyDataSetChanged();

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
                changeView(viewHolder,position);
                viewHolder.numbers.setText(listBean.get(position).getCount()+"次机会");
                viewHolder.transfer_bt_number.setText("转赠限额"+listBean.get(position).getAmount()+"BC");
                viewHolder.days.setText("还剩"+listBean.get(position).getExpireDays()+"天");
                viewHolder.time.setText("截至时间："+listBean.get(position).getLimitDate());

            }
        } catch (Exception e) {

        }
        return convertView;
    }

    private void changeView(ViewHolder viewHolder,int position){
        if(listBean.get(position).getStatus() == 0){
            viewHolder.voucher_bg.setBackgroundResource(R.drawable.voucher_bg_do);
            viewHolder.status.setVisibility(View.GONE);
            viewHolder.status_no.setVisibility(View.VISIBLE);
            viewHolder.status.setTextColor(Color.parseColor("#23A064"));
            viewHolder.days.setTextColor(Color.parseColor("#23A064"));
            viewHolder.transfer_bt_number.setTextColor(Color.parseColor("#333333"));
            viewHolder.time.setTextColor(Color.parseColor("#999999"));
        }else{
            viewHolder.voucher_bg.setBackgroundResource(R.drawable.voucher_bg_done);
            viewHolder.status.setVisibility(View.VISIBLE);
            if(listBean.get(position).getStatus() == 1){
                viewHolder.status.setText("已使用");
            }else {
                viewHolder.status.setText("已过期");
            }
            viewHolder.status_no.setVisibility(View.GONE);
            viewHolder.status.setTextColor(Color.parseColor("#CCCCCC"));
            viewHolder.days.setTextColor(Color.parseColor("#999999"));
            viewHolder.transfer_bt_number.setTextColor(Color.parseColor("#999999"));
            viewHolder.time.setTextColor(Color.parseColor("#CCCCCC"));
        }
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

        @BindView(R.id.status)
        LinTextView status;

        @BindView(R.id.use_it)
        LinearLayout use_it;

        @BindView(R.id.voucher_bg)
        LinearLayout voucher_bg;

        @BindView(R.id.status_no)
        LinearLayout status_no;

        @BindView(R.id.user_text)
        TextView user_text;

        @BindView(R.id.delet_text)
        TextView delet_text;

        public ViewHolder(View itemView,int position) {
            ButterKnife.bind(this, itemView);
            delet_text.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCOntext);
                builder.setTitle("确认删除此券吗？")
                        .setPositiveButton("确认", (dialog, which) -> deletVocher(position))
                        .setNegativeButton("取消", (dialog, which) -> {
                        });
                builder.create().show();
            });

            user_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018/7/23 带优惠券相关参数
                    Bundle bundle = new Bundle();
                    bundle.putString("usersTicket_id",listBean.get(position).getUsersTicket_id()+"");
                    bundle.putString("amount",listBean.get(position).getAmount()+"");
                    StarActivityUtil.starActivity((Activity) mCOntext, FindBTGiveWhoActivity.class,bundle);
                }
            });
        }
    }

    public void deletVocher(int position){
        Map map = new HashMap();
        map.put("usersTicket_id",listBean.get(position).getUsersTicket_id()+"");
        map.put("type",listBean.get(position).getType()+"");
        RetrofitApi.request(mCOntext, RetrofitApi.createApi(Api.class).deleteMemberTicket(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                listBean.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
