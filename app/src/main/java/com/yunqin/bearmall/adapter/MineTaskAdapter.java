package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

public class MineTaskAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList = new ArrayList<>();




    public MineTaskAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return 10;//mList.size();
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
        View view = View.inflate(mContext, R.layout.item_task, null);


        return view;
    }

    private OnItemCallBack onItemCallBack;

    public interface OnItemCallBack {
        void itemText(String data);

        void itemImg(String data);
    }

    public void setOnItemCallBack(OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }


}