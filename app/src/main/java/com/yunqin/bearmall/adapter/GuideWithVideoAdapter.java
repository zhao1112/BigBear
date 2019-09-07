package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.yunqin.bearmall.bean.GuideArticleList;
import com.yunqin.bearmall.ui.activity.VanguardListPageActivity;
import com.yunqin.bearmall.ui.activity.VideoDetailActivity;
import com.yunqin.bearmall.video.CustomVideo;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;

public class GuideWithVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<GuideArticleList> guideArticleLists;

    public GuideWithVideoAdapter(Context context, List<GuideArticleList> guideArticleLists) {
        this.context = context;
        this.guideArticleLists = guideArticleLists;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_guide_artical,parent,false);
            return new ArticalViewHolder(view);
        }else if (viewType == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.item_guide_video,parent,false);
            return new VideoViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.no_video_layout,parent,false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof EmptyViewHolder){

        }else if (holder instanceof ArticalViewHolder){

            handleArtical((ArticalViewHolder)holder,position);

        }else if (holder instanceof VideoViewHolder){

            handleVideo((VideoViewHolder)holder,position);

        }

    }


    private void handleArtical(ArticalViewHolder holder,int position){

        GuideArticleList article = guideArticleLists.get(position);
        if (article == null){

            return;
        }
        String title = article.getTitle();
        holder.titleView.setText(TextUtils.isEmpty(title)?"":title);

        String time = article.getCreatedDate();
        holder.timeView.setText(TextUtils.isEmpty(time)?"":time);

        Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_tuijian)).load(article.getImage()).into(holder.imageView);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump2H5((Integer) view.getTag());
            }
        });


    }

    private void jump2H5(int position){

        GuideArticleList article = guideArticleLists.get(position);

        String aid = String.valueOf(article.getGuideArticle_id());

        String guidelUrl = BuildConfig.BASE_URL+"/view/findGuideArticleDetailPage?guideArticle_id="+aid;

        VanguardListPageActivity.startH5Activity(context,guidelUrl,"导购详情");
    }



    private void handleVideo(VideoViewHolder holder,int position){

        GuideArticleList article = guideArticleLists.get(position);
        if (article == null){

            return;
        }
        String title = article.getTitle();
        holder.titleView.setText(TextUtils.isEmpty(title)?"":title);

        String time = article.getCreatedDate();
        holder.timeView.setText(TextUtils.isEmpty(time)?"":time);

        String videoUrl = article.getGuideVideo();
//        String videoUrl = "http://video.jiecao.fm/5/1/%E8%87%AA%E5%8F%96%E5%85%B6%E8%BE%B1.mp4";
//        article.setGuideVideo(videoUrl);
        if (!TextUtils.isEmpty(videoUrl)){
            holder.customVideo.setUp(videoUrl,null,Jzvd.SCREEN_WINDOW_LIST);
        }
        Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_tuijian)).load(article.getImage()).into(holder.customVideo.thumbImageView);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump2VideoDetail((Integer) view.getTag(),holder.customVideo.currentState);
            }
        });
    }

    private void jump2VideoDetail(int tag,int status){

        Intent intent = new Intent(context,VideoDetailActivity.class);
        intent.putExtra("Artical",guideArticleLists.get(tag));
        intent.putExtra("Status",status);
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return (guideArticleLists==null||guideArticleLists.isEmpty())?1:guideArticleLists.size();
    }


    /**
     *
     * viewtype:   0 imageView
     *             1 videoView
     *             2 emptyView
     */
    @Override
    public int getItemViewType(int position) {

        if (guideArticleLists==null||guideArticleLists.isEmpty()){
            return 2;
        }

        GuideArticleList list = guideArticleLists.get(position);
        return list.getType();
    }


    class ArticalViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.guide_title)
        TextView titleView;
        @BindView(R.id.guide_time)
        TextView timeView;
        @BindView(R.id.guide_image)
        ImageView imageView;

        public ArticalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.guide_title)
        TextView titleView;
        @BindView(R.id.guide_time)
        TextView timeView;
        @BindView(R.id.guide_video)
        CustomVideo customVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }




}
