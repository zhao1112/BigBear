package com.newreward.apdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yunqin.bearmall.R;

/**
 * Created by cxf on 2017/9/21.
 */

public class PrivilegeAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private int type = 2;

    public PrivilegeAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public  void  setType(int type){
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vh vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.n_v_item_goods_show2, parent, false);
            convertView.setTag(vh);
        } else {
            vh = (Vh) convertView.getTag();
        }

        return convertView;
    }

    private class Vh {
    }
}
