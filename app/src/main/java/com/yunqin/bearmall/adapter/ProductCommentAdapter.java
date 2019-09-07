package com.yunqin.bearmall.adapter;

import android.content.Context;
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
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductCommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductDetail.ReviewList> mReviewList;
    private LayoutInflater mInflater;

    public ProductCommentAdapter(Context context, List<ProductDetail.ReviewList> reviewList) {
        this.mContext = context;
        this.mReviewList = reviewList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<ProductDetail.ReviewList> mReviewList) {
        this.mReviewList = mReviewList;
        notifyDataSetChanged();
    }

    public void addData(List<ProductDetail.ReviewList> mReviewList) {
        this.mReviewList.addAll(mReviewList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mReviewList.get(position).getReviewImages() != null &&
                mReviewList.get(position).getReviewImages().size() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getCount() {
        return mReviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return mReviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HasImgViewHolder hasImgViewHolder;
        NoImgViewHolder noImgViewHolder;
        try {
            ProductDetail.ReviewList reviewList = mReviewList.get(position);
            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case 0:
                        convertView = mInflater.inflate(R.layout.item_product_comment_no_img, null);
                        noImgViewHolder = new NoImgViewHolder();

                        noImgViewHolder.circleImageView = (CircleImageView) convertView.findViewById(R.id.comment_icon);
                        noImgViewHolder.nickName = (TextView) convertView.findViewById(R.id.comment_nickname);
                        noImgViewHolder.commentTime = (TextView) convertView.findViewById(R.id.comment_time);
                        noImgViewHolder.content = (TextView) convertView.findViewById(R.id.comment_content);
                        setNoImgData(noImgViewHolder, reviewList);
                        convertView.setTag(noImgViewHolder);

                        break;

                    case 1:
                        convertView = mInflater.inflate(R.layout.item_product_comment_has_img, null);
                        hasImgViewHolder = new HasImgViewHolder();
                        hasImgViewHolder.circleImageView = (CircleImageView) convertView.findViewById(R.id.comment_icon);
                        hasImgViewHolder.nickName = (TextView) convertView.findViewById(R.id.comment_nickname);
                        hasImgViewHolder.commentTime = (TextView) convertView.findViewById(R.id.comment_time);
                        hasImgViewHolder.content = (TextView) convertView.findViewById(R.id.comment_content);

                        hasImgViewHolder.commentImageLayout = (LinearLayout) convertView.findViewById(R.id.comment_img_layout);
                        hasImgViewHolder.img1 = (ImageView) convertView.findViewById(R.id.comment_img1);
                        hasImgViewHolder.img2 = (ImageView) convertView.findViewById(R.id.comment_img2);
                        hasImgViewHolder.img3 = (ImageView) convertView.findViewById(R.id.comment_img3);
                        hasImgViewHolder.img4 = (ImageView) convertView.findViewById(R.id.comment_img4);

                        hasImgViewHolder.imgCount = (TextView) convertView.findViewById(R.id.comment_img_count);
                        setHasImgData(hasImgViewHolder, reviewList);

                        hasImgViewHolder.commentImageLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                return;
    //                            Toast.makeText(mContext,"查看评论大图",Toast.LENGTH_LONG).show();
                            }
                        });

                        convertView.setTag(hasImgViewHolder);

                        break;
                    default:
                        break;
                }
            } else {
                switch (type) {
                    case 0:
                        noImgViewHolder = (NoImgViewHolder) convertView.getTag();
                        setNoImgData(noImgViewHolder,reviewList);
                        break;

                    case 1:
                        hasImgViewHolder = (HasImgViewHolder) convertView.getTag();
                        setHasImgData(hasImgViewHolder,reviewList);
                        break;

                    default:
                        break;

                }
            }
        } catch (Exception e) {

        }
        return convertView;
    }

    private void setNoImgData(NoImgViewHolder noImgViewHolder, ProductDetail.ReviewList reviewList) {
        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(reviewList.getIconUrl()).into(noImgViewHolder.circleImageView);
        noImgViewHolder.nickName.setText(reviewList.getNickName());
        noImgViewHolder.commentTime.setText(reviewList.getCreatedDate().substring(0,reviewList.getCreatedDate().indexOf(" ")));
        noImgViewHolder.content.setText(reviewList.getContent());
    }

    private void setHasImgData(HasImgViewHolder hasImgViewHolder, ProductDetail.ReviewList reviewList) {
        List<ProductDetail.ReviewImages> reviewImages = reviewList.getReviewImages();
        if(reviewImages != null){
            Collections.sort(reviewImages, new Comparator<ProductDetail.ReviewImages>() {

                @Override
                public int compare(ProductDetail.ReviewImages o1, ProductDetail.ReviewImages o2) {
                    // 按照学生的年龄进行升序排列
                    if (o1.getOrder() > o2.getOrder()) {
                        return 1;
                    }
                    if (o1.getOrder() == o2.getOrder()) {
                        return 0;
                    }
                    return -1;
                }
            });

//        Log.e("升序排序后",reviewImages.toString());

            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(reviewList.getIconUrl()).into(hasImgViewHolder.circleImageView);
            hasImgViewHolder.nickName.setText(reviewList.getNickName());
            hasImgViewHolder.commentTime.setText(reviewList.getCreatedDate().substring(0,reviewList.getCreatedDate().indexOf(" ")));
            hasImgViewHolder.content.setText(reviewList.getContent());

            if (reviewList.getReviewImages().size() > 4) {
                hasImgViewHolder.imgCount.setVisibility(View.VISIBLE);
                hasImgViewHolder.imgCount.setText("共" + reviewList.getReviewImages().size() + "张");

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getThumbnail()).into(hasImgViewHolder.img1);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(1).getThumbnail()).into(hasImgViewHolder.img2);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(2).getThumbnail()).into(hasImgViewHolder.img3);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(3).getThumbnail()).into(hasImgViewHolder.img4);

            } else if (reviewList.getReviewImages().size() == 4) {
                hasImgViewHolder.imgCount.setVisibility(View.GONE);

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getThumbnail()).into(hasImgViewHolder.img1);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(1).getThumbnail()).into(hasImgViewHolder.img2);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(2).getThumbnail()).into(hasImgViewHolder.img3);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(3).getThumbnail()).into(hasImgViewHolder.img4);
            }else if (reviewList.getReviewImages().size() == 3) {
                hasImgViewHolder.imgCount.setVisibility(View.GONE);

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getThumbnail()).into(hasImgViewHolder.img1);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(1).getThumbnail()).into(hasImgViewHolder.img2);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(2).getThumbnail()).into(hasImgViewHolder.img3);
            }else if (reviewList.getReviewImages().size() == 4) {
                hasImgViewHolder.imgCount.setVisibility(View.GONE);

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getThumbnail()).into(hasImgViewHolder.img1);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(1).getThumbnail()).into(hasImgViewHolder.img2);
            }else if (reviewList.getReviewImages().size() == 1) {
                hasImgViewHolder.imgCount.setVisibility(View.GONE);

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getThumbnail()).into(hasImgViewHolder.img1);
            }
        }
    }


    class HasImgViewHolder {
        private TextView content, nickName, commentTime;
        private ImageView img1, img2, img3, img4;
        private CircleImageView circleImageView;
        private TextView imgCount;
        private LinearLayout commentImageLayout;

    }

    class NoImgViewHolder {
        private TextView content, nickName,commentTime;
        private CircleImageView circleImageView;

    }


}
