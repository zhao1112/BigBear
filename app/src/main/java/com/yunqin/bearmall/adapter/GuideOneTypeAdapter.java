package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.GuideBean;
import com.yunqin.bearmall.ui.activity.VanguardListPageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenchen on 2018/8/28.
 */

public class GuideOneTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<GuideBean.Article> datas;
    private Activity context;

    public GuideOneTypeAdapter(List<GuideBean.Article> datas, Activity context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        if ((datas == null || datas.isEmpty())){

            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){

           return  new GuideHeaderHolder(LayoutInflater.from(context).inflate(R.layout.item_guide_header,parent,false));
        }else {
            return new EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_empty_view,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 0){
            handleGroupHolder(position,holder);
        }

    }

    @Override
    public int getItemCount() {
        return (datas == null || datas.isEmpty())?1:datas.size();
    }

    private void handleGroupHolder(int groupPostion, RecyclerView.ViewHolder holder) {

        try {
            GuideHeaderHolder mHolder = (GuideHeaderHolder) holder;

            GuideBean.Article article = datas.get(groupPostion);

            mHolder.timeView.setText(article.getCreatedDate());

            mHolder.titleView.setText(article.getTitle());

            Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_tuijian)).load(article.getImage()).into(mHolder.titleImageView);

            holder.itemView.setTag(R.integer.type_group,groupPostion);

            holder.itemView.setOnClickListener(v -> {

                int group = (int) v.getTag(R.integer.type_group);

                GuideBean.Article article1 = datas.get(group);

                jump2H5(article1.getGuideArticle_id()+"", article1.getTitle());

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void jump2H5(String aid,String title){
        String guidelUrl;
        guidelUrl = BuildConfig.BASE_URL+"/view/findGuideArticleDetailPage?guideArticle_id="+aid;
        VanguardListPageActivity.startH5Activity(context,guidelUrl, "导购详情");
    }

    @Override
    public void onClick(View v) {

    }

    class EmptyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.empty_text)
        TextView emptyText;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            emptyText.setText("暂无推荐数据");
        }
    }

    class GuideHeaderHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.guide_header_time)
        TextView timeView;
        @BindView(R.id.guide_header_title)
        TextView titleView;
        @BindView(R.id.guide_header_image)
        ImageView titleImageView;

        public GuideHeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

}
