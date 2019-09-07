package com.newreward.apdater;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newreward.bean.RecordBean;
import com.yunqin.bearmall.R;

import java.util.List;

/**
 * @author AYWang
 * @create 2019/1/22
 * @Describe
 */
public class RecordListAdapter extends BaseAdapter {

    private Context mContext;
    private List<RecordBean> recordBeanList;


    public RecordListAdapter(Context mContext, List<RecordBean> recordBeanList) {
        this.mContext = mContext;
        this.recordBeanList = recordBeanList;
    }

    @Override
    public int getCount() {
        return recordBeanList.size();
    }


    @Override
    public Object getItem(int position) {
        return recordBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HeadViewHolder headViewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.record_item, null);
            headViewHolder = new HeadViewHolder();
            headViewHolder.mobile =  (TextView) convertView.findViewById(R.id.mobile);
            headViewHolder.status = (TextView) convertView.findViewById(R.id.status);
            //setTag()
            convertView.setTag(headViewHolder);
        } else {
            //getTag();
            headViewHolder = (HeadViewHolder) convertView.getTag();
        }
        if(position == 0){
            headViewHolder.mobile.setText("被邀约用户");
            headViewHolder.status.setText("开通状态");
        }else {
            headViewHolder.mobile.setText(recordBeanList.get(position).getMobile());
            if(recordBeanList.get(position).getStatus() == 0){
                headViewHolder.status.setTextColor(Color.parseColor("#999999"));
                headViewHolder.status.setText("未开通");
            }else {
                headViewHolder.status.setTextColor(Color.parseColor("#333333"));
                headViewHolder.status.setText("已开通");
            }
        }
        return convertView;
    }

    class HeadViewHolder {
        TextView mobile;
        TextView status;
    }
}
