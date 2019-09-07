package com.newversions.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.ImageLookActivity;
import com.newversions.detail.NewProductBean;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/1/14 10:16
 */
public class CommentImgView extends LinearLayout implements View.OnClickListener {

    private ImageView newVersionUserIcon;
    private TextView newVersionUserName;
    private TextView newVersionCommentTime;
    private TextView newVersionCommentData;
    private ImageView commentImg1;
    private ImageView commentImg2;
    private ImageView commentImg3;
    private ImageView commentImg4;
    private Context context;

    private LinearLayout new_version_img_container;
    private List<ImageView> commentImages;
    private ArrayList<String> imageUrls;
    private static final int IMAGEVIEW_TAG = R.id.tag_first;


    public CommentImgView(Context context) {
        this(context, null);
    }

    public CommentImgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        this.context = context;
        this.commentImages = new ArrayList<>();
        this.imageUrls = new ArrayList<>();
        LayoutInflater.from(context).inflate(R.layout.comment_img_view, this, true);
        newVersionUserIcon = findViewById(R.id.new_version_user_icon);
        newVersionUserName = findViewById(R.id.new_version_user_name);
        newVersionCommentTime = findViewById(R.id.new_version_comment_time);
        newVersionCommentData = findViewById(R.id.new_version_comment_data);
        new_version_img_container = findViewById(R.id.new_version_img_container);

        commentImg1 = findViewById(R.id.new_version_comment_img_1);
        commentImg2 = findViewById(R.id.new_version_comment_img_2);
        commentImg3 = findViewById(R.id.new_version_comment_img_3);
        commentImg4 = findViewById(R.id.new_version_comment_img_4);
        commentImages.add(commentImg1);
        commentImages.add(commentImg2);
        commentImages.add(commentImg3);
        commentImages.add(commentImg4);

    }


    public void setData(NewProductBean.DataBean.ReviewListBean reviewListBean) {

        newVersionUserName.setText(reviewListBean.getNickName());
        newVersionCommentTime.setText(reviewListBean.getCreatedDate());
        newVersionCommentData.setText(reviewListBean.getContent());

        try {
            Glide.with(context).load(reviewListBean.getIconUrl()).into(newVersionUserIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (reviewListBean.getReviewImages() != null && reviewListBean.getReviewImages().size() > 0) {
            for (int i = 0; i < 4; i++) {
                if (reviewListBean.getReviewImages().size() > i) {
                    commentImages.get(i).setVisibility(VISIBLE);
                    commentImages.get(i).setOnClickListener(this);
                    commentImages.get(i).setTag(IMAGEVIEW_TAG, i);
                    imageUrls.add(reviewListBean.getReviewImages().get(i).getSource());
                    try {
                        Glide.with(context).load(reviewListBean.getReviewImages().get(i).getSource()).into(commentImages.get(i));



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    commentImages.get(i).setVisibility(INVISIBLE);
                }
            }
        } else {
            new_version_img_container.setVisibility(GONE);
        }
    }


    @Override
    public void onClick(View view) {
        int pos = (int) view.getTag(IMAGEVIEW_TAG);
        ImageLookActivity.startImageActivity(context, imageUrls, pos);
    }
}
