package com.newversions.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.ImageLookActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewVersionProductCommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewReviewListBean.DataBean.ReviewListBean> mReviewList;
    private LayoutInflater mInflater;

    public NewVersionProductCommentAdapter(Context context, List<NewReviewListBean.DataBean.ReviewListBean> reviewList) {
        this.mContext = context;
        this.mReviewList = reviewList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<NewReviewListBean.DataBean.ReviewListBean> mReviewList) {
        this.mReviewList = mReviewList;
        notifyDataSetChanged();
    }

    public void addData(List<NewReviewListBean.DataBean.ReviewListBean> mReviewList) {
        this.mReviewList.addAll(mReviewList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (mReviewList.get(position) != null && mReviewList.get(position).getReviewImages() != null &&
                    mReviewList.get(position).getReviewImages().size() > 0) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    @Override
    public int getCount() {
        try {
            return mReviewList.size();
        } catch (Exception e) {
        }
        return 0;
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
            NewReviewListBean.DataBean.ReviewListBean reviewList = mReviewList.get(position);
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
                                //                            Toast.makeText(mContext,"查看评论大图",Toast.LENGTH_LONG).show();
                                return;

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
                        setNoImgData(noImgViewHolder, reviewList);
                        break;

                    case 1:
                        hasImgViewHolder = (HasImgViewHolder) convertView.getTag();
                        setHasImgData(hasImgViewHolder, reviewList);
                        break;

                    default:
                        break;

                }
            }
        } catch (Exception e) {

        }
        return convertView;
    }

    private void setNoImgData(NoImgViewHolder noImgViewHolder, NewReviewListBean.DataBean.ReviewListBean reviewList) {
        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(reviewList.getIconUrl()).into(noImgViewHolder.circleImageView);
        noImgViewHolder.nickName.setText(reviewList.getNickName());
        noImgViewHolder.commentTime.setText(reviewList.getCreatedDate().substring(0, reviewList.getCreatedDate().indexOf(" ")));
        noImgViewHolder.content.setText(reviewList.getContent());
    }

    private void setHasImgData(HasImgViewHolder hasImgViewHolder, NewReviewListBean.DataBean.ReviewListBean reviewList) {
        List<NewReviewListBean.DataBean.ReviewListBean.ReviewImagesBean> reviewImages = reviewList.getReviewImages();

        ArrayList<String> imageUrls = new ArrayList<>();

        if (reviewImages != null) {
            Collections.sort(reviewImages, new Comparator<NewReviewListBean.DataBean.ReviewListBean.ReviewImagesBean>() {

                @Override
                public int compare(NewReviewListBean.DataBean.ReviewListBean.ReviewImagesBean o1,
                                   NewReviewListBean.DataBean.ReviewListBean.ReviewImagesBean o2) {
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
            hasImgViewHolder.commentTime.setText(reviewList.getCreatedDate().substring(0, reviewList.getCreatedDate().indexOf(" ")));
            hasImgViewHolder.content.setText(reviewList.getContent());

            if (reviewList.getReviewImages().size() > 4) {
                hasImgViewHolder.imgCount.setVisibility(View.VISIBLE);
                hasImgViewHolder.imgCount.setText("共" + reviewList.getReviewImages().size() + "张");

                imageUrls.add(reviewImages.get(0).getSource());
                imageUrls.add(reviewImages.get(1).getSource());
                imageUrls.add(reviewImages.get(2).getSource());
                imageUrls.add(reviewImages.get(3).getSource());

                hasImgViewHolder.img1.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 0));
                hasImgViewHolder.img2.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 1));
                hasImgViewHolder.img3.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 2));
                hasImgViewHolder.img4.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 3));

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getSource()).into(hasImgViewHolder.img1);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(1).getSource()).into(hasImgViewHolder.img2);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(2).getSource()).into(hasImgViewHolder.img3);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(3).getSource()).into(hasImgViewHolder.img4);

            } else if (reviewList.getReviewImages().size() == 4) {
                hasImgViewHolder.imgCount.setVisibility(View.GONE);

                imageUrls.add(reviewImages.get(0).getSource());
                imageUrls.add(reviewImages.get(1).getSource());
                imageUrls.add(reviewImages.get(2).getSource());
                imageUrls.add(reviewImages.get(3).getSource());

                hasImgViewHolder.img1.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 0));
                hasImgViewHolder.img2.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 1));
                hasImgViewHolder.img3.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 2));
                hasImgViewHolder.img4.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 3));

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getSource()).into(hasImgViewHolder.img1);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(1).getSource()).into(hasImgViewHolder.img2);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(2).getSource()).into(hasImgViewHolder.img3);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(3).getSource()).into(hasImgViewHolder.img4);
            } else if (reviewList.getReviewImages().size() == 3) {
                hasImgViewHolder.imgCount.setVisibility(View.GONE);

                imageUrls.add(reviewImages.get(0).getSource());
                imageUrls.add(reviewImages.get(1).getSource());
                imageUrls.add(reviewImages.get(2).getSource());

                hasImgViewHolder.img1.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 0));
                hasImgViewHolder.img2.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 1));
                hasImgViewHolder.img3.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 2));

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getSource()).into(hasImgViewHolder.img1);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(1).getSource()).into(hasImgViewHolder.img2);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(2).getSource()).into(hasImgViewHolder.img3);
            } else if (reviewList.getReviewImages().size() == 2) {
                hasImgViewHolder.imgCount.setVisibility(View.GONE);

                imageUrls.add(reviewImages.get(0).getSource());
                imageUrls.add(reviewImages.get(1).getSource());
                hasImgViewHolder.img1.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 0));
                hasImgViewHolder.img2.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 1));

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getSource()).into(hasImgViewHolder.img1);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(1).getSource()).into(hasImgViewHolder.img2);
            } else if (reviewList.getReviewImages().size() == 1) {
                hasImgViewHolder.imgCount.setVisibility(View.GONE);

                imageUrls.add(reviewImages.get(0).getSource());
                hasImgViewHolder.img1.setOnClickListener(view -> ImageLookActivity.startImageActivity(mContext, imageUrls, 0));

                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewImages.get(0).getSource()).into(hasImgViewHolder.img1);
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
        private TextView content, nickName, commentTime;
        private CircleImageView circleImageView;

    }


}
