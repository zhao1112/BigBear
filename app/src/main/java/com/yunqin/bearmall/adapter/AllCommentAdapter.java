package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.CommentBean;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/7/27
 * @Describe
 */
public class AllCommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommentBean.DataBean.ReviewListBean> dataList = new ArrayList<>();

    public AllCommentAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CommentBean.DataBean.ReviewListBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();

    }

    public List<CommentBean.DataBean.ReviewListBean> listBeanGet() {
        return dataList;
    }

    public void addData(List<CommentBean.DataBean.ReviewListBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("WangLsit", dataList.get(position).getReviewImages() == null ? true + "=====================position=" + position : false + "=====================position=" + position);
        if (dataList.get(position).getReviewImages() == null || dataList.get(position).getReviewImages().isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        Log.e("WangLsit", convertView == null ? true + "=====================convertView=" + position : false + "=====================convertView=" + position);
        if (viewType == 1) {
            convertView = getHaveImageView(position, convertView, parent);
        } else if (viewType == 0) {
            convertView = getNoImageView(position, convertView, parent);
        }
        return convertView;
    }


    private View getHaveImageView(final int position, View convertView,
                                  ViewGroup parent) {
        HaveImageViewHolder haveImageViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment_have_image, null);
            haveImageViewHolder = new HaveImageViewHolder(convertView, position);
            convertView.setTag(haveImageViewHolder);
        } else {
            haveImageViewHolder = (HaveImageViewHolder) convertView.getTag();
        }
        try {
            final CommentBean.DataBean.ReviewListBean reviewListBean = dataList.get(position);
            haveImageViewHolder.content.setText(reviewListBean.getContent());
            haveImageViewHolder.goods_name.setText(reviewListBean.getProductName());
            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_shopcar_product)).load(reviewListBean.getProductImages().getThumbnail()).into(haveImageViewHolder.goods_pic);
            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(reviewListBean.getIconUrl()).into(haveImageViewHolder.head_img);
            haveImageViewHolder.name.setText(reviewListBean.getNickName());
            haveImageViewHolder.time.setText(reviewListBean.getCreatedDate());
            String goodsSpe = "";
            for (int i = 0; i < reviewListBean.getSpecifications().size(); i++) {
                goodsSpe = reviewListBean.getSpecifications().get(i) + " ";
            }
            haveImageViewHolder.goods_color.setText(goodsSpe);

            if (haveImageViewHolder.images_layout.getChildCount() > 0) {
                return convertView;
            }
            for (int i = 0; i < reviewListBean.getReviewImages().size(); i++) {
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(162, 162);
                //设置自己需要的距离
                lp.leftMargin = 40;
                lp.gravity = Gravity.CENTER_VERTICAL;
                imageView.setLayoutParams(lp);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment)).load(reviewListBean.getReviewImages().get(i).getThumbnail()).into(imageView);
                haveImageViewHolder.images_layout.addView(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }


    private View getNoImageView(final int position, View convertView,
                                ViewGroup parent) {
        NoImageViewHolder noImageViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment_no_image, null);
            noImageViewHolder = new NoImageViewHolder(convertView, position);
            convertView.setTag(noImageViewHolder);
        } else {
            noImageViewHolder = (NoImageViewHolder) convertView.getTag();
        }
        final CommentBean.DataBean.ReviewListBean reviewListBean = dataList.get(position);
        noImageViewHolder.content.setText(reviewListBean.getContent());
        noImageViewHolder.goods_name.setText(reviewListBean.getProductName());
        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment)).load(reviewListBean.getProductImages().getThumbnail()).into(noImageViewHolder.goods_pic);
        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(reviewListBean.getIconUrl()).into(noImageViewHolder.head_img);
        noImageViewHolder.name.setText(reviewListBean.getNickName());
        noImageViewHolder.time.setText(reviewListBean.getCreatedDate());
        String goodsSpe = "";
        for (int i = 0; i < reviewListBean.getSpecifications().size(); i++) {
            goodsSpe = reviewListBean.getSpecifications().get(i) + " ";
        }
        noImageViewHolder.goods_color.setText(goodsSpe);

        return convertView;
    }


    class HaveImageViewHolder {
        @BindView(R.id.head_img)
        CircleImageView head_img;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.time)
        TextView time;

        @BindView(R.id.goods_pic)
        ImageView goods_pic;

        @BindView(R.id.goods_name)
        TextView goods_name;

        @BindView(R.id.goods_color)
        TextView goods_color;

        @BindView(R.id.images_layout)
        LinearLayout images_layout;

        @BindView(R.id.content)
        TextView content;

        public HaveImageViewHolder(View itemView, int position) {
            ButterKnife.bind(this, itemView);
        }
    }

    class NoImageViewHolder {
        @BindView(R.id.head_img)
        CircleImageView head_img;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.time)
        TextView time;

        @BindView(R.id.goods_pic)
        ImageView goods_pic;

        @BindView(R.id.goods_name)
        TextView goods_name;

        @BindView(R.id.goods_color)
        TextView goods_color;

        @BindView(R.id.content)
        TextView content;

        public NoImageViewHolder(View itemView, int position) {
            ButterKnife.bind(this, itemView);
        }
    }

}
