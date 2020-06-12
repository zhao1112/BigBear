package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbcoupon.ui.bean.ArticeleListInfor;
import com.bbcoupon.ui.bean.ArticleInfor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/1
 */
public class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArticeleListInfor.DataBean> list;
    private Context context;
    private RoundedCorners roundedCorner = new RoundedCorners(15);
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorner);

    public ArticleListAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    public void addData(List<ArticeleListInfor.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteData() {
        this.list.clear();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_school_article, parent, false);
        return new ArticleListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArticleListHolder articleListHolder = (ArticleListHolder) holder;
        if (list.get(position).getIsOverhead() == 1) {
            articleListHolder.art_one.setVisibility(View.VISIBLE);
        } else {
            articleListHolder.art_one.setVisibility(View.GONE);
        }

        articleListHolder.sc_title.setText(list.get(position).getTitle());

        articleListHolder.sc_live_sume.setText(list.get(position).getLikes_num() + "点赞 ");
        articleListHolder.sc_see_sume.setText(" " + list.get(position).getViews_num() + "浏览");
        articleListHolder.sc_time.setText(list.get(position).getReleaseTime());
        if (list.get(position).getType() == 0) {
            Glide.with(context)
                    .load(list.get(position).getUrl())
                    .apply(options)
                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                    .into(articleListHolder.sc_image);
            articleListHolder.sc_video_image.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(list.get(position).getCoverimage())
                    .apply(options)
                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                    .into(articleListHolder.sc_image);
            articleListHolder.sc_video_image.setVisibility(View.VISIBLE);
        }
        articleListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onArticleList != null) {
                    try {
                        if (list.get(position).getType() == 0) {
                            onArticleList.onListId(list.get(position).getId(), list.get(position).getTitle(), list.get(position).getUrl());
                        } else {
                            onArticleList.onListId(list.get(position).getId(), list.get(position).getTitle(),
                                    list.get(position).getCoverimage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ArticleListHolder extends RecyclerView.ViewHolder {

        private final TextView sc_title;
        private final TextView sc_live_sume;
        private final TextView sc_see_sume;
        private final TextView art_one;
        private final TextView sc_time;
        private final ImageView sc_image;
        private final ImageView sc_video_image;

        public ArticleListHolder(View itemView) {
            super(itemView);
            sc_title = itemView.findViewById(R.id.sc_title);
            sc_live_sume = itemView.findViewById(R.id.sc_live_sume);
            sc_see_sume = itemView.findViewById(R.id.sc_see_sume);
            sc_time = itemView.findViewById(R.id.sc_time);
            sc_image = itemView.findViewById(R.id.sc_image);
            sc_video_image = itemView.findViewById(R.id.sc_video_image);
            art_one = itemView.findViewById(R.id.art_one);
        }
    }

    public interface OnArticleList {
        void onListId(int id, String title, String url);
    }

    public OnArticleList onArticleList;

    public void setOnArticleList(OnArticleList onArticleList) {
        this.onArticleList = onArticleList;
    }

}
