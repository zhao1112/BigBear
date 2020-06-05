package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbcoupon.ui.bean.ArticleInfor;
import com.bbcoupon.ui.bean.CommentInfor;
import com.bbcoupon.ui.bean.SchoolInfor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/1
 */
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CommentInfor.DataBean> list;
    private Context context;

    public ArticleAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    public void addData(List<CommentInfor.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteData() {
        this.list.clear();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArticleHolder articleHolder = (ArticleHolder) holder;
        Glide.with(context)
                .load(list.get(position).getIconUrl())
                .into(articleHolder.co_image);
        articleHolder.co_name.setText(list.get(position).getNickName());
        articleHolder.co_conten.setText(list.get(position).getComments());
        articleHolder.co_time.setText(list.get(position).getCommentTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ArticleHolder extends RecyclerView.ViewHolder {

        private final TextView co_name, co_conten, co_time;
        private final CircleImageView co_image;

        public ArticleHolder(View itemView) {
            super(itemView);
            co_name = itemView.findViewById(R.id.co_name);
            co_image = itemView.findViewById(R.id.co_image);
            co_conten = itemView.findViewById(R.id.co_conten);
            co_time = itemView.findViewById(R.id.co_time);
        }
    }

}
