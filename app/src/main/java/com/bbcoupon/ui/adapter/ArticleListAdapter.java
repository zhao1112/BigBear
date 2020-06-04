package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbcoupon.ui.bean.ArticleInfor;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/1
 */
public class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private Context context;

    public ArticleListAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    public void addData(List<String> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_school_article, parent, false);
        return new ArticleListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private class ArticleListHolder extends RecyclerView.ViewHolder {

        public ArticleListHolder(View itemView) {
            super(itemView);

        }
    }

}
