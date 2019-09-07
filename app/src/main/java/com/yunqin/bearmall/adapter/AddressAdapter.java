package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.AddressListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Master
 */
public class AddressAdapter extends BaseAdapter {

    private Context mContext;
    private List<AddressListBean.DataBean> mList = new ArrayList<>();

    public void setData(List<AddressListBean.DataBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public List<AddressListBean.DataBean> getData() {
        return mList;
    }


    public AddressAdapter(Context context, List<AddressListBean.DataBean> list) {
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
        View view = View.inflate(mContext, R.layout.item_address, null);

        try {
            TextView address_name = view.findViewById(R.id.address_name);// 姓名
            TextView address_number = view.findViewById(R.id.address_number);// 手机号
            TextView address_detail = view.findViewById(R.id.address_detail);// 地址详情
            Button btn_del = view.findViewById(R.id.btn_del);// 删除
            btn_del.setTag(position);
            Button btn_edit = view.findViewById(R.id.btn_edit);// 编辑
            btn_edit.setTag(position);

            address_name.setText(mList.get(position).getConsignee());
            address_number.setText(mList.get(position).getPhone());

            address_detail.setText(mList.get(position).getAreaName() + " " + mList.get(position).getAddress());


            RelativeLayout check_box_bt = view.findViewById(R.id.check_box_bt);// CheckBox Layout
            check_box_bt.setTag(position);
            CheckBox check_box_bt_ = view.findViewById(R.id.check_box_bt_);// CheckBox

            check_box_bt_.setChecked(mList.get(position).isIsDefault());


            btn_del.setOnClickListener(v -> {
                if (onChildClickListener != null) {
                    onChildClickListener.onDelAddress(mList.get((int) v.getTag()));
                }
            });

            btn_edit.setOnClickListener(v -> {
                if (onChildClickListener != null) {
                    onChildClickListener.onEditAddress(mList.get((int) v.getTag()));
                }
            });
            check_box_bt.setOnClickListener(v -> {
                if (onChildClickListener != null) {
                    onChildClickListener.onDefaultAddress(mList.get((int) v.getTag()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private OnChildClickListener onChildClickListener;

    public interface OnChildClickListener {
        void onEditAddress(AddressListBean.DataBean bean);

        void onDelAddress(AddressListBean.DataBean bean);

        void onDefaultAddress(AddressListBean.DataBean bean);
    }

    public void setOnChildClickListener(OnChildClickListener childClickListener) {
        onChildClickListener = childClickListener;
    }

}