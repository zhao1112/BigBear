package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.Menu;
import com.yunqin.bearmall.ui.activity.presenter.MenuActivityPersenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/7/12
 * @Describe
 */
public class MenuTwoAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_Content = 1;

    private Context mContext;
    List<Object> list = new ArrayList<>();

    private MenuActivityPersenter menuActivityPersenter;


    public MenuTwoAdapter(Context mContext,List<Menu.DataBean.SubCategoryBeanX> categoryBeanXList){
        this.mContext = mContext;
        for(int i =0;i<categoryBeanXList.size();i++){
            list.add(categoryBeanXList.get(i));
            for(int j =0;j<categoryBeanXList.get(i).getSubCategory().size();j++){
                list.add(categoryBeanXList.get(i).getSubCategory().get(j));
            }
        }
    }

    public void setPersenter(MenuActivityPersenter menuActivityPersenter) {
        this.menuActivityPersenter = menuActivityPersenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                return new HeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu_head_layout, parent, false));
            case TYPE_Content:
                return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu_content_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof HeaderHolder){
                try {
                    ((HeaderHolder) holder).title.setText(((Menu.DataBean.SubCategoryBeanX)list.get(position)).getName());
                } catch (Exception e) {
                }
            }else {
                try {
                    holder.itemView.setOnClickListener(this);
                    holder.itemView.setTag(position);
                    // TODO: 2018/7/12 显示网络图片
                    try{
                        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_shopcar_product)).load(((Menu.DataBean.SubCategoryBeanX.SubCategoryBean)list.get(position)).getImg()).into( ((ContentViewHolder)holder).img);
                    }catch (Exception e){
                    }
                    ((ContentViewHolder)holder).name.setText(((Menu.DataBean.SubCategoryBeanX.SubCategoryBean)list.get(position)).getName());
                } catch (Exception e) {
                }
            }
    }


    public int getAdapterType(int position) {
        int type = getItemViewType(position);
        return type == TYPE_HEAD ? 3 : 1;
    }


    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof Menu.DataBean.SubCategoryBeanX){
            return TYPE_HEAD;
        }else{
            return TYPE_Content;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        menuActivityPersenter.onRightItemClcik(index,list.get(index));
    }

    //内容
    public class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name)
        TextView name;
        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //头部
    public class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
