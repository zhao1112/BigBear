package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.activity.VanguardListPageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenchen on 2018/7/27.
 */

public class GuideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int HEADER = 0;
    private static final int ITEM = 1;
    private static final int FOOTER = 2;



    private List<GuideBean.Article> datas;
    private Activity context;

    public GuideAdapter( List<GuideBean.Article> datas, Activity context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){

            case HEADER:

                return new GuideHeaderHolder(LayoutInflater.from(context).inflate(R.layout.item_guide_header,parent,false));

            case ITEM:

                return new GuideItemHolder(LayoutInflater.from(context).inflate(R.layout.item_guide_normal,parent,false));

            case FOOTER:

                return new GuideFooterHolder(LayoutInflater.from(context).inflate(R.layout.item_guide_footer,parent,false));

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int type = judgeViewType(position);

        int groupPostion = getGropPositonWithItemPostion(position);

        switch (type){

            case HEADER:

                handleGroupHolder(groupPostion,holder);

                break;

            case ITEM:

                int childPosition = getChildPositionWithItemPostion(position);

                handleItemHolder(groupPostion,childPosition,holder);

                break;

            case FOOTER:

                handleFooterHolder(groupPostion,holder);

                break;

        }

    }


    private void handleGroupHolder(int groupPostion, RecyclerView.ViewHolder holder) {

        GuideHeaderHolder mHolder = (GuideHeaderHolder) holder;

        GuideBean.Article article = datas.get(groupPostion);

        mHolder.timeView.setText(article.getCreatedDate());

        mHolder.titleView.setText(article.getTitle());

        Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_tuijian)).load(article.getImage()).into(mHolder.titleImageView);

        holder.itemView.setTag(R.integer.type_group,groupPostion);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int group = (int) v.getTag(R.integer.type_group);

                GuideBean.Article article = datas.get(group);

                jump2H5(article.getGuideArticle_id()+"");

            }
        });

    }

    private void handleItemHolder(int groupPostion, int childPosition, RecyclerView.ViewHolder holder) {

        GuideItemHolder mHolder = (GuideItemHolder) holder;

        GuideBean.Article article = datas.get(groupPostion);

        List<GuideBean.Hot> hots = article.getHotList();

        GuideBean.Hot hot = hots.get(childPosition);

        mHolder.itemTitleView.setText(hot.getTitle());

        Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_tuijian_small)).load(hot.getImage()).into(mHolder.itemImageView);

        holder.itemView.setTag(R.integer.type_group,groupPostion);

        holder.itemView.setTag(R.integer.type_child,childPosition);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int group = (int) v.getTag(R.integer.type_group);

                int child = (int) v.getTag(R.integer.type_child);

                GuideBean.Article article = datas.get(group);

                List<GuideBean.Hot> hots = article.getHotList();

                GuideBean.Hot hot = hots.get(child);

                jump2H5(hot.getGuideArticle_id());

            }
        });


    }

    private void jump2H5(String aid){
//        UserInfo userInfo = BearMallAplication.getInstance().getUser();
        String guidelUrl;
//        if (userInfo != null){
//            try {
//                guidelUrl = BuildConfig.BASE_URL+"/view/findGuideArticleDetailPage?guideArticle_id="+aid+"&access_token="+userInfo.getData().getAccess_token();
//            }catch (Exception e){
//                guidelUrl = BuildConfig.BASE_URL+"/view/findGuideArticleDetailPage?guideArticle_id="+aid;
//            }
//        }else {
            guidelUrl = BuildConfig.BASE_URL+"/view/findGuideArticleDetailPage?guideArticle_id="+aid;
//        }
        VanguardListPageActivity.startH5Activity(context,guidelUrl,"导购详情");
    }


    private void handleFooterHolder(int groupPostion, RecyclerView.ViewHolder holder) {

        GuideFooterHolder mHolder = (GuideFooterHolder)holder;

        GuideBean.Article article = datas.get(groupPostion);

        mHolder.guideNameView.setText(article.getStoreName());

        Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_tuijian_small)).load(article.getLogo()).into(mHolder.guideFooterImage);

        if (article.isExpand()){

            mHolder.guideFooteExpandView.setText("收起");

            mHolder.guideFooterArrowView.setImageResource(R.drawable.icon_arrow_up);

        }else {

            mHolder.guideFooteExpandView.setText("展开更多");

            mHolder.guideFooterArrowView.setImageResource(R.drawable.icon_arrow_down);

        }


        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                article.setExpand(!datas.get(groupPostion).isExpand());

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public int getItemCount() {
        return countForDatas();
    }

    @Override
    public int getItemViewType(int position) {
        return judgeViewType(position);
    }

    private int countItem(int postion){

        GuideBean.Article data = datas.get(postion);

        List<GuideBean.Hot> hots = data.getHotList();

        int itemCount = (hots==null?0:hots.size());

        int count = (data.isExpand()?(2+itemCount):(2+(itemCount > 0?1:0)));

        return count;

    }


    private int getGropPositonWithItemPostion(int postion){

        int sum = 0;

        for (int i = 0;i<datas.size();i++){

            int total = sum + countItem(i);

            if (total > postion){

                return i;
            }else {

                sum = total;

            }

        }

        return -1;
    }

   private int countForDatas(){

        int sum = 0;

        for (int i = 0;i<datas.size();i++){

            sum += countItem(i);

        }

        return sum;
   }

    private int getChildPositionWithItemPostion(int position){

        int sum = 0;

        for (int i = 0;i<datas.size();i++){

            int expand = countItem(i);

            int total = sum + expand;

            if (total > position){

                return position - sum - 1;

            }else {

                sum = total;

            }

        }

        return -1;
    }


    private int judgeViewType(int position){

        int itemCount = 0;
        int groupCount = datas.size();

        for (int i = 0; i < groupCount; i++) {
            GuideBean.Article article = datas.get(i);

            itemCount += 1;
            if (position < itemCount){

                return HEADER;
            }

            List<GuideBean.Hot> hots = article.getHotList();

            itemCount += hots==null?0:article.isExpand()?hots.size():(hots.size()>0?1:0);

            if (position < itemCount) {
                return ITEM;
            }

            itemCount += 1;
            if (position < itemCount) {
                return FOOTER;
            }

        }

        return -1;
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

    class GuideItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.guide_item_text)
        TextView itemTitleView;
        @BindView(R.id.guide_item_image)
        ImageView itemImageView;

        public GuideItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }


    class GuideFooterHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.guide_footer_image)
        ImageView guideFooterImage;
        @BindView(R.id.guide_footer_name)
        TextView guideNameView;
        @BindView(R.id.guide_footer_arrow)
        ImageView guideFooterArrowView;
        @BindView(R.id.guide_footer_text)
        TextView guideFooteExpandView;

        public GuideFooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
