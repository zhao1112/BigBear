package com.yunqin.bearmall.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbcoupon.util.CopyTextUtil;
import com.bbcoupon.util.ImageUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.PropagandaBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

import static com.yunqin.bearmall.adapter.PropagandaAdapter.FRAGMENT_TYPE.IMAGE_TYPE;
import static com.yunqin.bearmall.adapter.PropagandaAdapter.FRAGMENT_TYPE.IMAGES_TYPE;
import static com.yunqin.bearmall.adapter.PropagandaAdapter.FRAGMENT_TYPE.VIDEO_TYPE;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/4/1
 */
public class PropagandaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PropagandaBean.DataBean> list;
    //设置图片圆角角度
    private RoundedCorners roundedCorners = new RoundedCorners(10);
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);


    public PropagandaAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void addData(List<PropagandaBean.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getVideos() != null && !TextUtils.isEmpty(list.get(position).getVideos())) {
            return VIDEO_TYPE;
        } else {
            String[] split = list.get(position).getImages().split(",");
            if (split.length == 1) {
                return IMAGE_TYPE;
            }
            if (split.length > 1) {
                return IMAGES_TYPE;
            }
        }
        return IMAGE_TYPE;
    }

    @IntDef({IMAGE_TYPE, IMAGES_TYPE, VIDEO_TYPE})
    public @interface FRAGMENT_TYPE {
        int IMAGE_TYPE = 0;
        int IMAGES_TYPE = 1;
        int VIDEO_TYPE = 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case IMAGE_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.item_propaganda_image, parent, false);
                return new ImageHolder(view);
            case IMAGES_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.item_propaganda_images, parent, false);
                return new ImagesHolder(view);
            case VIDEO_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.item_propaganda_video, parent, false);
                return new VideoHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case IMAGE_TYPE:
                ImageHolder imageHolder = (ImageHolder) holder;
                imageHolder.bus_time.setText(list.get(position).getLaunchTime());
                imageHolder.share_nume.setText(list.get(position).getShare_num() + "");
                if (!TextUtils.isEmpty(list.get(position).getMaterial_desc())) {
                    imageHolder.bus_content.setText(Html.fromHtml(list.get(position).getMaterial_desc()));
                }
                try {
                    String[] image = list.get(position).getImages().split(",");
                    Log.e("image_onBindViewHolder", image[0]);
                    try {
                        Glide.with(context)
                                .asBitmap()
                                .load(image[0])
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.default_product)
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                )
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        try {
                                            Bitmap bitmap = ImageUtil.ImageMatrix(resource, imageHolder.bus_content);
                                            imageHolder.bus_image_min.setImageBitmap(bitmap);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    imageHolder.bus_image_min.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            seeImage(image[0]);
                        }
                    });

                    imageHolder.share_bus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mOnClickShare != null) {
                                mOnClickShare.share(image, list.get(position).getId(), 1,
                                        imageHolder.bus_content.getText().toString().replace("</br>", "\n"));
                                list.get(position).setShare_num(list.get(position).getShare_num() + 1);
                                notifyDataSetChanged();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                imageHolder.bus_content.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        CopyTextUtil.CopyText(context, imageHolder.bus_content.getText().toString().replace("</br>", "\n"));
                        if (mOnClickShare != null) {
                            mOnClickShare.shareCrop();
                        }
                        return true;
                    }
                });

                break;
            case IMAGES_TYPE:
                ImagesHolder imagesHolder = (ImagesHolder) holder;
                imagesHolder.bus_time.setText(list.get(position).getLaunchTime());
                imagesHolder.share_nume.setText(list.get(position).getShare_num() + "");
                if (!TextUtils.isEmpty(list.get(position).getMaterial_desc())) {
                    imagesHolder.bus_content.setText(Html.fromHtml(list.get(position).getMaterial_desc()));
                }
                try {
                    String[] images = list.get(position).getImages().split(",");
                    imagesHolder.bus_recycle.setLayoutManager(new GridLayoutManager(context, 3));
                    ImageAdapter imageAdapter = new ImageAdapter(context, images);
                    imagesHolder.bus_recycle.setAdapter(imageAdapter);

                    imagesHolder.share_bus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mOnClickShare != null) {
                                mOnClickShare.share(images, list.get(position).getId(), 0,
                                        imagesHolder.bus_content.getText().toString().replace("</br>", "\n"));
                                list.get(position).setShare_num(list.get(position).getShare_num() + 1);
                                notifyDataSetChanged();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                imagesHolder.bus_content.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        CopyTextUtil.CopyText(context, imagesHolder.bus_content.getText().toString().replace("</br>", "\n"));
                        if (mOnClickShare != null) {
                            mOnClickShare.shareCrop();
                        }
                        return true;
                    }
                });
                break;
            case VIDEO_TYPE:
                VideoHolder videoHolder = (VideoHolder) holder;

                videoHolder.bus_time.setText(list.get(position).getLaunchTime());
                if (!TextUtils.isEmpty(list.get(position).getMaterial_desc())) {
                    videoHolder.share_nume.setText(list.get(position).getShare_num() + "");
                }
                videoHolder.bus_content.setText(Html.fromHtml(list.get(position).getMaterial_desc()));


                Glide.with(context)
                        .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small))
                        .load(list.get(position).getVideos_img())
                        .apply(options).into(videoHolder.bus_video);


                videoHolder.bus_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mVideoClick != null) {
                            mVideoClick.videoUrl(list.get(position).getVideos());
                        }
                    }
                });

                videoHolder.share_bus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnShearVideo != null) {
                            mOnShearVideo.videoUrl(list.get(position).getVideos(), list.get(position).getVideos_img(),
                                    videoHolder.bus_content.getText().toString().replace("</br>", "\n"), list.get(position).getId());
                            list.get(position).setShare_num(list.get(position).getShare_num() + 1);
                            notifyDataSetChanged();
                        }
                    }
                });

                videoHolder.bus_content.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        CopyTextUtil.CopyText(context, videoHolder.bus_content.getText().toString().replace("</br>", "\n"));
                        if (mOnClickShare != null) {
                            mOnClickShare.shareCrop();
                        }
                        return true;
                    }
                });
                break;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        public final TextView bus_time, share_nume, bus_content;
        public final ImageView bus_image_min;
        public final LinearLayout share_bus;
        public final View viewBitmap;

        public ImageHolder(View itemView) {
            super(itemView);
            bus_time = itemView.findViewById(R.id.bus_time);
            share_nume = itemView.findViewById(R.id.share_nume);
            bus_content = itemView.findViewById(R.id.bus_content);
            bus_image_min = itemView.findViewById(R.id.bus_image_min);
            share_bus = itemView.findViewById(R.id.share_bus);
            viewBitmap = itemView.findViewById(R.id.view_bitmap);
        }
    }

    public class ImagesHolder extends RecyclerView.ViewHolder {
        public final TextView bus_time, share_nume, bus_content;
        public final RecyclerView bus_recycle;
        public final LinearLayout share_bus;

        public ImagesHolder(View itemView) {
            super(itemView);
            bus_time = itemView.findViewById(R.id.bus_time);
            share_nume = itemView.findViewById(R.id.share_nume);
            share_bus = itemView.findViewById(R.id.share_bus);
            bus_content = itemView.findViewById(R.id.bus_content);
            bus_recycle = itemView.findViewById(R.id.bus_recycle);
        }
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        private final ImageView bus_video;
        public final TextView bus_time, share_nume, bus_content;
        public final LinearLayout share_bus;

        public VideoHolder(View itemView) {
            super(itemView);
            bus_video = itemView.findViewById(R.id.bus_video);
            bus_time = itemView.findViewById(R.id.bus_time);
            share_nume = itemView.findViewById(R.id.share_nume);
            bus_content = itemView.findViewById(R.id.bus_content);
            share_bus = itemView.findViewById(R.id.share_bus);
        }
    }

    public void seeImage(String position) {

        final List<ImageInfo> imageInfoList = new ArrayList<>();
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setOriginUrl(position);// 原图url
        imageInfo.setThumbnailUrl(position);// 缩略图url
        imageInfoList.add(imageInfo);

        ImagePreview.LoadStrategy loadStrategy = ImagePreview.LoadStrategy.Default;

        ImagePreview
                .getInstance()
                .setContext(context)
                .setIndex(0)
                .setImageInfoList(imageInfoList)
                .setLoadStrategy(loadStrategy)
                .setEnableDragClose(true)
                .setEnableUpDragClose(true)
                .setShowDownButton(true)
                .start();
    }

    public interface onClickShare {
        void share(String[] strings, int id, int i, String title);

        void shareCrop();
    }

    public onClickShare mOnClickShare;

    public void setOnClickShare(onClickShare mOnClickShare) {
        this.mOnClickShare = mOnClickShare;
    }

    public interface onVideoClick {
        void videoUrl(String url);
    }

    public onVideoClick mVideoClick;

    public void setOnVideoClick(onVideoClick mVideoClick) {
        this.mVideoClick = mVideoClick;
    }

    public interface onShearVideo {
        void videoUrl(String url, String image, String title, int goodesId);
    }

    public onShearVideo mOnShearVideo;

    public void setOnShearVideo(onShearVideo mOnShearVideo) {
        this.mOnShearVideo = mOnShearVideo;
    }

}
