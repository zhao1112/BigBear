package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BargainRecordBean;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.List;

public class BargainFriendAdapter extends BaseAdapter{

    private Context mContext;
    private List<BargainRecordBean.BargainJoinRecordList> bargainJoinRecordLists;
    private LayoutInflater mLayoutInflater;

    public BargainFriendAdapter(Context mContext, List<BargainRecordBean.BargainJoinRecordList> bargainJoinRecordLists) {
        this.mContext = mContext;
        this.bargainJoinRecordLists = bargainJoinRecordLists;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return bargainJoinRecordLists.size();
    }

    @Override
    public Object getItem(int position) {
        return bargainJoinRecordLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BargainRecordBean.BargainJoinRecordList bargainJoinRecord = bargainJoinRecordLists.get(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_bargain_friend,null);
            viewHolder.circleImageView = convertView.findViewById(R.id.item_bargain_friend_head_img);
            viewHolder.name = convertView.findViewById(R.id.item_bargain_friend_name);
            viewHolder.amount = convertView.findViewById(R.id.item_bargain_friend_amount);
            viewHolder.desc = convertView.findViewById(R.id.item_bargain_friend_desc);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            if(bargainJoinRecord.getIconUrl() == null || bargainJoinRecord.getIconUrl().equals("")){
                Glide.with(mContext).load(R.drawable.no_sign_in_img).into(viewHolder.circleImageView);
            }else{
                Glide.with(mContext).load(bargainJoinRecord.getIconUrl()).into(viewHolder.circleImageView);
            }
        } catch (Exception e) {
        }

        try {
            viewHolder.name.setText(bargainJoinRecord.getNickName());
            viewHolder.amount.setText("砍掉¥"+bargainJoinRecord.getBargainAmount());
            if(bargainJoinRecord.isSelf()){
                viewHolder.desc.setVisibility(View.GONE);
            }else{
                viewHolder.desc.setVisibility(View.VISIBLE);
                viewHolder.desc.setText(bargainJoinRecord.getDescription());
            }
        } catch (Exception e) {
        }


        return convertView;
    }

    class ViewHolder{
        CircleImageView circleImageView;
        TextView name;
        TextView desc;
        TextView amount;

    }
}
