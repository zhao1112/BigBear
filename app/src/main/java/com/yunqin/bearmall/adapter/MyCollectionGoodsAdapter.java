package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.CollectionGoods;
import com.yunqin.bearmall.widget.PriceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class MyCollectionGoodsAdapter extends BaseAdapter {

    private Context mCOntext;
    private List<CollectionGoods.DataBean.ProductFavoriteListBean>  listBean;

    public MyCollectionGoodsAdapter(Context mCOntext){
        this.mCOntext = mCOntext;
        listBean = new ArrayList<>();
    }

    public void setData(List<CollectionGoods.DataBean.ProductFavoriteListBean>  listBean){
        this.listBean.clear();
        this.listBean.addAll(listBean);
        notifyDataSetChanged();

    }

    public List<CollectionGoods.DataBean.ProductFavoriteListBean>  listBeanGet(){
        return  listBean;
    }

    public void addData(List<CollectionGoods.DataBean.ProductFavoriteListBean>  listBean){
        this.listBean.addAll(listBean);
        notifyDataSetChanged();
    }

    public void remove(int index){
        this.listBean.remove(index);
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
                convertView = LayoutInflater.from(mCOntext).inflate(R.layout.item_collection_goods,null);
                viewHolder = new ViewHolder(convertView,position);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if(listBean.size()>0){
                viewHolder.goods_name.setText(listBean.get(position).getProductName());


                String memberPrice = listBean.get(position).getMembershipPrice();
                String price = listBean.get(position).getPrice();
                boolean surportMsp = listBean.get(position).isSurportMsp();
                int isDiscount = listBean.get(position).getIsDiscount();
                int model = listBean.get(position).getModel();
                String sourceMemberPrice = listBean.get(position).getSourceMembershipPrice();
                String sourceprice = listBean.get(position).getSourcePrice();

                viewHolder.priceView.setPrice(memberPrice,price, surportMsp,isDiscount, model,sourceMemberPrice,sourceprice);

                Glide.with(mCOntext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(listBean.get(position).getProductImages().getThumbnail()).into(viewHolder.image_goods);
            }
        } catch (Exception e) {
        }
        return convertView;
    }

    class ViewHolder{

        @BindView(R.id.image_goods)
        ImageView image_goods;

        @BindView(R.id.goods_name)
        TextView goods_name;

        @BindView(R.id.price_view)
        PriceView priceView;



        public ViewHolder(View itemView,int position) {
            ButterKnife.bind(this, itemView);
        }
    }
}
