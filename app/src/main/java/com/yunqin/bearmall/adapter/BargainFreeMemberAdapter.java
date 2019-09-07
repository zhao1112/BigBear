package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.MemberBargainProductListBean;
import com.yunqin.bearmall.ui.activity.BargainFreeShareActivity;
import com.yunqin.bearmall.ui.activity.MineOrderActivity;
import com.yunqin.bearmall.util.TimeUtils;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import java.util.List;

public class BargainFreeMemberAdapter extends RecyclerView.Adapter {


//    private Context mContext;
//    private List<MemberBargainProductListBean.BargainRecordList> bargainRecordLists;
//    private LayoutInflater mInflater;
//    public BargainFreeMemberAdapter(Context context, List<MemberBargainProductListBean.BargainRecordList> bargainRecordLists) {
//        this.mContext = context;
//        this.bargainRecordLists = bargainRecordLists;
//        mInflater = LayoutInflater.from(mContext);
//
//    }
//
//    public int getListSize(){
//        return bargainRecordLists.size();
//    }
//
//    public void setData(List<MemberBargainProductListBean.BargainRecordList> bargainRecordLists) {
//        this.bargainRecordLists = bargainRecordLists;
//        notifyDataSetChanged();
//    }
//
//    public void addData(List<MemberBargainProductListBean.BargainRecordList> bargainRecordLists) {
//        this.bargainRecordLists.addAll(bargainRecordLists);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
//        return bargainRecordLists.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return bargainRecordLists.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 3;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//
//        if(bargainRecordLists.get(position).getStatus() == 0 ||
//                bargainRecordLists.get(position).getStatus() == 2){//过期
//            return 0;
//        }else if(bargainRecordLists.get(position).getStatus() == 1){//进行中
//            return 1;
//        }
//
////        else if(bargainRecordLists.get(position).getStatus() == 2){//已完成
////            return 2;
////        }
//
//        return super.getItemViewType(position);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        MemberBargainProductListBean.BargainRecordList bargainRecordList = bargainRecordLists.get(position);
//        ViewHolder0 viewHolder0 = null;
//        ViewHolder1 viewHolder1 = null;
//        int type = getItemViewType(position);
//        if(convertView == null){
//            switch (type){
//                case 0:
//                    //过期
//                    viewHolder0 = new ViewHolder0();
//                    convertView = mInflater.inflate(R.layout.item_bargain_free_product_my_expire, null);
//                    viewHolder0.productImg = convertView.findViewById(R.id.bargain_free_product_img_my_expire);
//                    viewHolder0.productName = convertView.findViewById(R.id.bargain_free_product_name_my_expire);
//                    viewHolder0.againLayoutExpire = convertView.findViewById(R.id.bargain_free_again_layout_expire);
//
//                    convertView.setTag(viewHolder0);
//                break;
//
//                case 1:
//                    //进行中
//                    viewHolder1 = new ViewHolder1();
//                    convertView = mInflater.inflate(R.layout.item_bargain_free_product_my,null);
//                    viewHolder1.productImg = (ImageView) convertView.findViewById(R.id.bargain_free_product_img_my);
//                    viewHolder1.productName = (TextView) convertView.findViewById(R.id.bargain_free_product_name_my);
//                    viewHolder1.productPrice = (TextView) convertView.findViewById(R.id.bargain_free_product_price_my);
//                    viewHolder1.productBarginPrice = (TextView) convertView.findViewById(R.id.bargain_free_current_price_my);
//                    viewHolder1.expireTime = (TextView) convertView.findViewById(R.id.bargain_free_expire_my);
//                    viewHolder1.product_count = (TextView) convertView.findViewById(R.id.bargain_free_product_count);
//
//                    convertView.setTag(viewHolder1);
//                    break;
//
////                case 2:
////
////                    break;
//            }
//        }else{
//            switch (type){
//                case 0:
//                    viewHolder0 = (ViewHolder0) convertView.getTag();
//                    break;
//
//                case 1:
//                    viewHolder1 = (ViewHolder1) convertView.getTag();
//                    break;
//
////                case 2:
////
////                    break;
//            }
//        }
//
//        switch (type){
//            case 0:
//                Glide.with(mContext).load(bargainRecordList.getProductImages().getThumbnail()).into(viewHolder0.productImg);
//                viewHolder0.productName.setText(bargainRecordList.getProductName());
//                viewHolder0.againLayoutExpire.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(onBargainFreeAgainlistener != null){
//                            onBargainFreeAgainlistener.onBargainFreeAgain();//重砍一次
//                        }
//                    }
//                });
//                break;
//
//            case 1:
//                Glide.with(mContext).load(bargainRecordList.getProductImages().getThumbnail()).into(viewHolder1.productImg);
//                viewHolder1.productName.setText(bargainRecordList.getProductName());
//                viewHolder1.productPrice.setText("¥"+bargainRecordList.getSourcePartPrice()+"+BC"+bargainRecordList.getSourcePartBtAmount());
//                viewHolder1.productBarginPrice.setText("¥"+bargainRecordList.getCurrentPartPrice()+"+BC"+bargainRecordList.getCurrentPartBtAmount());
//                viewHolder1.expireTime.setText(TimeUtils.generateTime(bargainRecordList.getExpireDate()));
//                viewHolder1.product_count.setText("已有"+bargainRecordList.getPersonalCount()+"人参加");
//
//                convertView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext,BargainFreeShareActivity.class);
//                        intent.putExtra("bargainRecord_id",bargainRecordList.getBargainRecord_id());
//                        mContext.startActivity(intent);
//                    }
//                });
//                break;
//
////            case 2:
////
////                break;
//        }
//
//        return convertView;
//    }

    //    class ViewHolder1 {
//        private ImageView productImg;
//        private TextView productName, productPrice, productBarginPrice;
//        private TextView expireTime;
//        private TextView product_count;
//    }

//    class ViewHolder0 {
//        private ImageView productImg;
//        private TextView productName;
//        private LinearLayout againLayoutExpire;
//    }


    private Context mContext;
    private List<MemberBargainProductListBean.BargainRecordList> bargainRecordLists;
    private LayoutInflater mInflater;

    public BargainFreeMemberAdapter(Context context, List<MemberBargainProductListBean.BargainRecordList> bargainRecordLists) {
        this.mContext = context;
        this.bargainRecordLists = bargainRecordLists;
        mInflater = LayoutInflater.from(mContext);

    }

    public int getListSize() {
        return bargainRecordLists.size();
    }

    public void setData(List<MemberBargainProductListBean.BargainRecordList> bargainRecordLists) {
        this.bargainRecordLists = bargainRecordLists;
        notifyDataSetChanged();
    }

    public void addData(List<MemberBargainProductListBean.BargainRecordList> bargainRecordLists) {
        this.bargainRecordLists.addAll(bargainRecordLists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (bargainRecordLists.get(position).getStatus() == 0) {//过期
            return 0;
        } else if (bargainRecordLists.get(position).getStatus() == 1) {//进行中
            return 1;
        } else if (bargainRecordLists.get(position).getStatus() == 2) {//已完成
            return 2;
        }

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = mInflater.inflate(R.layout.item_bargain_free_product_my_expire, null);
            return new ViewHolder0(view);
        } else if (viewType == 1) {
            view = mInflater.inflate(R.layout.item_bargain_free_product_my, null);
            return new ViewHolder1(view);
        } else if (viewType == 2) {
            view = mInflater.inflate(R.layout.item_bargain_free_my_get, null);
            return new ViewHolder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try {
            MemberBargainProductListBean.BargainRecordList bargainRecordList = bargainRecordLists.get(position);
            if (holder instanceof ViewHolder0) {
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;

                try {
                    Glide.with(mContext).load(bargainRecordList.getProductImages().getThumbnail()).into(viewHolder0.productImg);
                } catch (Exception e) {
                }
                viewHolder0.productName.setText(bargainRecordList.getProductName());
                viewHolder0.againLayoutExpire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onBargainFreeAgainlistener != null) {
                            onBargainFreeAgainlistener.onBargainFreeAgain();//重砍一次
                        }
                    }
                });
            } else if (holder instanceof ViewHolder1) {
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;

                try {
                    Glide.with(mContext).load(bargainRecordList.getProductImages().getThumbnail()).into(viewHolder1.productImg);
                } catch (Exception e) {
                }
                viewHolder1.productName.setText(bargainRecordList.getProductName());

                viewHolder1.productPrice.setText("¥" + bargainRecordList.getPrice());


                viewHolder1.productBarginPrice.setText("¥" + bargainRecordList.getCurrentPartPrice());
                viewHolder1.expireTime.setText(TimeUtils.generateTime(bargainRecordList.getExpireDate()));
                viewHolder1.product_count.setText("已有" + bargainRecordList.getPersonalCount() + "人参加");

                viewHolder1.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, BargainFreeShareActivity.class);
                    intent.putExtra("bargainRecord_id", bargainRecordList.getBargainRecord_id());
                    mContext.startActivity(intent);
                });
            } else if (holder instanceof ViewHolder2) {
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;

                try {
                    Glide.with(mContext).load(bargainRecordList.getProductImages().getThumbnail()).into(viewHolder2.productImg);
                } catch (Exception e) {
                }
                viewHolder2.productName.setText(bargainRecordList.getProductName());
                viewHolder2.product_count.setText("已有" + bargainRecordList.getPersonalCount() + "人参加");

                viewHolder2.look_order.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, MineOrderActivity.class);
                    mContext.startActivity(intent);
                });

                viewHolder2.join_again.setOnClickListener(v -> {
                    if (onBargainFreeAgainlistener != null) {
                        onBargainFreeAgainlistener.onBargainFreeAgain();//重砍一次
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return bargainRecordLists.size();
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView productName;
        private TextView product_count;
        private HighlightButton look_order, join_again;

        public ViewHolder2(View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.goods_img);
            productName = itemView.findViewById(R.id.goods_name);
            product_count = itemView.findViewById(R.id.bargain_free_product_count);
            look_order = itemView.findViewById(R.id.look_order);
            join_again = itemView.findViewById(R.id.join_again);

        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView productName, productPrice, productBarginPrice;
        private TextView expireTime;
        private TextView product_count;

        public ViewHolder1(View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.bargain_free_product_img_my);
            productName = itemView.findViewById(R.id.bargain_free_product_name_my);
            productPrice = itemView.findViewById(R.id.bargain_free_product_price_my);
            productBarginPrice = itemView.findViewById(R.id.bargain_free_current_price_my);
            expireTime = itemView.findViewById(R.id.bargain_free_expire_my);
            product_count = itemView.findViewById(R.id.bargain_free_product_count);

        }
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView productName;
        private LinearLayout againLayoutExpire;

        public ViewHolder0(View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.bargain_free_product_img_my_expire);
            productName = itemView.findViewById(R.id.bargain_free_product_name_my_expire);
            againLayoutExpire = itemView.findViewById(R.id.bargain_free_again_layout_expire);
        }
    }

    public OnBargainFreeAgainlistener onBargainFreeAgainlistener;

    public interface OnBargainFreeAgainlistener {
        void onBargainFreeAgain();
    }

    public void setOnBargainFreeAgainlistener(OnBargainFreeAgainlistener onBargainFreeAgainlistener) {
        this.onBargainFreeAgainlistener = onBargainFreeAgainlistener;
    }
}
