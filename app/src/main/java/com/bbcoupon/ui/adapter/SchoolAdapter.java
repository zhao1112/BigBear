package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bbcoupon.ui.bean.SchoolInfor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

import static com.bbcoupon.ui.adapter.SchoolAdapter.FRAGMENT_TYPE.SCHOOLBANNER;
import static com.bbcoupon.ui.adapter.SchoolAdapter.FRAGMENT_TYPE.SCHOOLICON;
import static com.bbcoupon.ui.adapter.SchoolAdapter.FRAGMENT_TYPE.SCHOOLLIST;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/1
 */
public class SchoolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> list;
    private Context context;

    public SchoolAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    public void addDataList(SchoolInfor schoolInfor) {
        list.clear();
        list.add(new BannerList(schoolInfor.getBanner()));
        list.add(new IconList(schoolInfor.getIconList()));
        list.addAll(schoolInfor.getList());
    }

    @IntDef({SCHOOLBANNER, SCHOOLICON, SCHOOLLIST})
    public @interface FRAGMENT_TYPE {
        int SCHOOLBANNER = 1;
        int SCHOOLICON = 2;
        int SCHOOLLIST = 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof BannerList) {
            return SCHOOLBANNER;
        }
        if (list.get(position) instanceof IconList) {
            return SCHOOLICON;
        }
        if (list.get(position) instanceof SchoolInfor.Data) {
            return SCHOOLLIST;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case SCHOOLBANNER:
                view = LayoutInflater.from(context).inflate(R.layout.item_school_banner, parent, false);
                return new BannerHolder(view);
            case SCHOOLICON:
                view = LayoutInflater.from(context).inflate(R.layout.item_school_icon, parent, false);
                return new IconHolder(view);
            case SCHOOLLIST:
                view = LayoutInflater.from(context).inflate(R.layout.item_school_article, parent, false);
                return new ArticleHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case SCHOOLBANNER:
                BannerHolder bannerHolder = (BannerHolder) holder;
                BannerList banner = (BannerList) list.get(position);
                bannerHolder.setBannerList(banner.bannerlist);
                break;
            case SCHOOLICON:
                IconHolder iconHolder = (IconHolder) holder;
                IconList icon = (IconList) list.get(position);
                iconHolder.sc_icon_recycle.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                SchoolIconAdapter schoolIconAdapter = new SchoolIconAdapter(context, icon.iconList);
                iconHolder.sc_icon_recycle.setAdapter(schoolIconAdapter);
                //设置滑动
                iconHolder.sc_icon_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int extent = recyclerView.computeHorizontalScrollExtent();
                        //整体的高度，注意是整体，包括在显示区域之外的。
                        int range = recyclerView.computeHorizontalScrollRange();
                        //已经滚动的距离，为0时表示已处于顶部。
                        int offset = recyclerView.computeHorizontalScrollOffset();
                        //计算出溢出部分的宽度，即屏幕外剩下的宽度
                        float maxEndX = range - extent;
                        //计算比例
                        float proportion = offset / maxEndX;
                        int layoutWidth = iconHolder.sc_icon_layout.getWidth();
                        int indicatorViewWidth = iconHolder.sc_icon_view.getWidth();
                        //可滑动的距离
                        int scrollableDistance = layoutWidth - indicatorViewWidth;
                        //设置滚动条移动
                        iconHolder.sc_icon_view.setTranslationX(scrollableDistance * proportion);
                    }
                });

                schoolIconAdapter.setOnIcon(new SchoolIconAdapter.OnIcon() {
                    @Override
                    public void setIcon() {
                        if (onArticle != null) {
                            Log.e("iconContentHolder", "onClick: 2");
                            onArticle.setArticelIcon();
                        }
                    }
                });
                break;
            case SCHOOLLIST:
                ArticleHolder articleHolder = (ArticleHolder) holder;
                if (position == 2) {
                    String str = " 置顶 " + "  代理模式做大熊酷朋，月入 上万不是梦";
                    int bstart = str.indexOf(" 置顶 ");
                    int bend = bstart + " 置顶 ".length();
                    SpannableStringBuilder style = new SpannableStringBuilder(str);
                    style.setSpan(new BackgroundColorSpan(Color.parseColor("#E60012")), bstart, bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new ForegroundColorSpan(Color.WHITE), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    articleHolder.sc_title.setText(style);
                } else {
                    articleHolder.sc_title.setText("代理模式做大熊酷朋，月入上万不 是梦");
                }

                articleHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onArticle != null) {
                            onArticle.setArticle();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class BannerHolder extends RecyclerView.ViewHolder {

        private final Banner sc_banner;

        public BannerHolder(View itemView) {
            super(itemView);
            sc_banner = itemView.findViewById(R.id.sc_banner);
        }

        public void setBannerList(List<String> url) {
            //设置自动轮播，默认为true
            sc_banner.isAutoPlay(true);
            //设置轮播时间
            sc_banner.setDelayTime(4000);
            //设置指示器位置（当banner模式中有指示器时）
            sc_banner.setIndicatorGravity(BannerConfig.RIGHT);
            sc_banner.setImages(url);
            sc_banner.setImageLoader(new GlideImageLoader());
            sc_banner.start();
        }
    }

    private class ArticleHolder extends RecyclerView.ViewHolder {

        private final TextView sc_title;

        public ArticleHolder(View itemView) {
            super(itemView);
            sc_title = itemView.findViewById(R.id.sc_title);
        }
    }

    private class IconHolder extends RecyclerView.ViewHolder {

        private final RecyclerView sc_icon_recycle;
        private final RelativeLayout sc_icon_layout;
        private final View sc_icon_view;

        public IconHolder(View itemView) {
            super(itemView);
            sc_icon_recycle = itemView.findViewById(R.id.sc_icon_recycle);
            sc_icon_layout = itemView.findViewById(R.id.sc_icon_layout);
            sc_icon_view = itemView.findViewById(R.id.sc_icon_view);
        }
    }

    private class IconList {
        List<SchoolInfor.Icon> iconList;

        IconList(List<SchoolInfor.Icon> iconList) {
            this.iconList = iconList;
        }
    }

    private class BannerList {
        List<String> bannerlist;

        BannerList(List<String> bannerlist) {
            this.bannerlist = bannerlist;
        }
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load(path)
                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                    .into(imageView);
        }
    }

    public interface OnArticle {
        void setArticle();

        void setArticelIcon();
    }

    public OnArticle onArticle;

    public void setOnArticle(OnArticle onArticle) {
        this.onArticle = onArticle;
    }

}
