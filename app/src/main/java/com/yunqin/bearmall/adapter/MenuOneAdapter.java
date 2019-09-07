package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.Menu;
import com.yunqin.bearmall.ui.activity.presenter.MenuActivityPersenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/7/12
 * @Describe
 */
public class MenuOneAdapter extends RecyclerView.Adapter<MenuOneAdapter.ViewHolder> implements View.OnClickListener{

    private Context mContext;
    private List<Menu.DataBean> dataBeanList;


    public List<Menu.DataBean> getData(){
        return dataBeanList;
    }

    private int choosePosition = 0;

    private MenuActivityPersenter menuActivityPersenter;

    public MenuOneAdapter(Context mContext,List<Menu.DataBean> dataBeanList){
        this.mContext = mContext;
        this.dataBeanList = dataBeanList;
    }

    @Override
    public void onClick(View v) {
        setIsChoose((int) v.getTag());
        menuActivityPersenter.itemClick((int) v.getTag());
    }

    public void setPersenter(MenuActivityPersenter menuActivityPersenter) {
        this.menuActivityPersenter = menuActivityPersenter;
        menuActivityPersenter.itemClick(0);
    }

    /**
     * 设置是否选中
     */
    public void setIsChoose(int choosePosition) {
        this.choosePosition = choosePosition;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.menu_one_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.itemView.setOnClickListener(this);
            holder.itemView.setTag(position);

            holder.menu_one_name.setText(dataBeanList.get(position).getName());
            if (choosePosition == position){
                holder.left_line.setVisibility(View.VISIBLE);
                holder.menu_one_name.setTextColor(Color.parseColor("#23A064"));
            }else {
                holder.left_line.setVisibility(View.INVISIBLE);
                holder.menu_one_name.setTextColor(Color.parseColor("#666666"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_line)
        View left_line;
        @BindView(R.id.menu_one_name)
        TextView menu_one_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
