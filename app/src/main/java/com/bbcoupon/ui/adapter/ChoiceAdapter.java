package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbcoupon.base.ImageSelectInfor;
import com.bbcoupon.ui.bean.CustomInfor;
import com.bbcoupon.util.ImageUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

public class ChoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ImageSelectInfor.ImageBean> list;
    private Bitmap mBitmap;

    //设置图片圆角角度
    RoundedCorners roundedCorners = new RoundedCorners(8);
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);


    public ChoiceAdapter(Context context, List<ImageSelectInfor.ImageBean> list) {
        this.context = context;
        this.list = list;
        if (onWholeState != null) {
            onWholeState.onSelection(list);
        }
    }

    public void addData(List<ImageSelectInfor.ImageBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setImage(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choice_image, parent, false);
        return new ProductSunHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ProductSunHolder sunHolder = (ProductSunHolder) holder;

        if (position == 0) {
            sunHolder.er_content.setVisibility(View.VISIBLE);
        } else {
            sunHolder.er_content.setVisibility(View.GONE);
        }

        if (mBitmap != null && position == 0) {
            sunHolder.image_choice.setImageBitmap(mBitmap);
        } else {
            Glide.with(context)
                    .load(list.get(position).getImage())
                    .apply(options)
                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                    .into(sunHolder.image_choice);
        }

        sunHolder.select_image_choice.setChecked(list.get(position).isSelect());

        //全选
        sunHolder.select_image_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(position).setSelect(sunHolder.select_image_choice.isChecked());
                for (int i = 0; i < list.size(); i++) {
                    Log.e("select_image_choice", list.get(position).isSelect() + "");
                    if (!list.get(i).isSelect()) {
                        if (onWholeState != null) {
                            onWholeState.onWholeState(false);
                            onWholeState.onSelection(list);
                            return;
                        }
                    }
                }
                if (onWholeState != null) {
                    onWholeState.onWholeState(true);
                    onWholeState.onSelection(list);
                }
            }
        });

        //查看图片
        sunHolder.image_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeImages(list, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ProductSunHolder extends RecyclerView.ViewHolder {

        private final ImageView image_choice;
        private final CheckBox select_image_choice;
        private final TextView er_content;

        public ProductSunHolder(View itemView) {
            super(itemView);
            image_choice = itemView.findViewById(R.id.image_choice);
            select_image_choice = itemView.findViewById(R.id.select_image_choice);
            er_content = itemView.findViewById(R.id.er_content);
        }
    }


    public void wholeState(boolean isSelect) {
        for (int i = 0; i < list.size(); i++) {
            if (!isSelect && i == 0) {
                list.get(i).setSelect(true);
            } else {
                list.get(i).setSelect(isSelect);
            }
        }
        if (onWholeState != null) {
            onWholeState.onSelection(list);
        }
        notifyDataSetChanged();
    }

    public interface OnWholeState {
        void onWholeState(boolean isSelect);

        void onSelection(List<ImageSelectInfor.ImageBean> list);
    }

    public OnWholeState onWholeState;

    public void setOnWholeState(OnWholeState onWholeState) {
        this.onWholeState = onWholeState;
    }

    //图片查看

    public void seeImages(List<ImageSelectInfor.ImageBean> list, int position) {
        ImageInfo imageInfo;
        final List<ImageInfo> imageInfoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            imageInfo = new ImageInfo();
            imageInfo.setOriginUrl(list.get(i).getImage());// 原图url
            imageInfo.setThumbnailUrl(list.get(i).getImage());// 缩略图url
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
