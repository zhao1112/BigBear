package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/3/30
 */
public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private String[] listImage = null;

    //设置图片圆角角度
    private RoundedCorners roundedCorners = new RoundedCorners(10);
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

    public ImageAdapter(Context context, String[] listImage) {
        this.context = context;
        this.listImage = listImage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_iamge_business, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageHolder imageHolder = (ImageHolder) holder;

        Glide.with(context)
                .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small))
                .load(listImage[position])
                .apply(options).into(imageHolder.image_item);

        imageHolder.image_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeImage(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImage.length;
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        private final ImageView image_item;
        private final LinearLayout image_width;

        public ImageHolder(View itemView) {
            super(itemView);
            image_item = itemView.findViewById(R.id.image_item);
            image_width = itemView.findViewById(R.id.image_width);
        }
    }

    public void seeImage(int position) {

        ImageInfo imageInfo;
        final List<ImageInfo> imageInfoList = new ArrayList<>();
        for (int i = 0; i < listImage.length; i++) {
            imageInfo = new ImageInfo();
            imageInfo.setOriginUrl(listImage[i]);// 原图url
            imageInfo.setThumbnailUrl(listImage[i]);// 缩略图url
            imageInfoList.add(imageInfo);
        }

        ImagePreview.LoadStrategy loadStrategy = ImagePreview.LoadStrategy.Default;

        ImagePreview
                .getInstance()
                .setContext(context)
                .setIndex(position)
                .setImageInfoList(imageInfoList)
                .setLoadStrategy(loadStrategy)
                .setEnableDragClose(true)
                .setEnableUpDragClose(true)
                .setShowDownButton(true)
                .start();
    }

}
