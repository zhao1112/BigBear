package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SweetsBt;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 糖果赠送Adapter
 */
public class SweetsAdapter extends BaseAdapter {


    private Context context;
    private List<SweetsBt.DataBean.PresentListBean> data = new ArrayList<>();
    private int which;//是赠送还是被赠送

    public SweetsAdapter(Context context,int which) {
        this.context = context;
        this.which = which;
    }

    public void setData(List<SweetsBt.DataBean.PresentListBean> data) {
        this.data.clear();
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<SweetsBt.DataBean.PresentListBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IViewHolder iViewHolder = null;
        try {
            if(convertView == null){
                convertView = View.inflate(context, R.layout.item_sweets_record, null);
                iViewHolder = new IViewHolder(convertView);
                convertView.setTag(iViewHolder);
            }else {
                iViewHolder = (IViewHolder) convertView.getTag();
            }
            if(data.size()>0){
                Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(data.get(position).getIconUrl()).into(iViewHolder.head_img);
                iViewHolder.name.setText(data.get(position).getNickName());
                iViewHolder.bear_number.setText("大熊号："+data.get(position).getBigBearNumber());
                iViewHolder.time.setText(data.get(position).getCreatedDate());
                if(which == 1){
                    //支出
                    iViewHolder.give_number.setText(data.get(position).getDebit());
                }else {
                    //收入
                    iViewHolder.give_number.setText(data.get(position).getCredit());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }


    class IViewHolder {

        @BindView(R.id.head_img)
        CircleImageView head_img;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.bear_number)
        TextView bear_number;

        @BindView(R.id.time)
        TextView time;

        @BindView(R.id.give_number)
        TextView give_number;

        public IViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


}
