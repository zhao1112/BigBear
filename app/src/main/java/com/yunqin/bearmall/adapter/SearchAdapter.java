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

public class SearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private boolean showImg = true;

    public void setData(List<String> mList, boolean showImg) {
        this.mList = mList;
        this.showImg = showImg;
        notifyDataSetChanged();
    }


    public List<String> getData() {
        return mList;
    }


    public SearchAdapter(Context context, List<String> list) {
        this.mContext = context;
        if (list != null) {
            this.mList = list;
        }
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
        View view = View.inflate(mContext, R.layout.item_activity_search, null);
        try {
            TextView textView = view.findViewById(R.id.name);
            textView.setText(mList.get(position));
            textView.setTag(position);
            textView.setOnClickListener(v -> {
                if (onItemCallBack != null) {
                    onItemCallBack.itemText(mList.get((int) v.getTag()));
                }
            });

            RelativeLayout imageView = view.findViewById(R.id.close);
            imageView.setTag(position);
            imageView.setVisibility(showImg ? View.VISIBLE : View.GONE);
            imageView.setOnClickListener(v -> {
                if (onItemCallBack != null) {
                    onItemCallBack.itemImg(mList.get((int) v.getTag()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

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