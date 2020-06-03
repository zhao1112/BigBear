package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bbcoupon.ui.bean.SchoolInfor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/1
 */
public class SchoolIconAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SchoolInfor.Icon> list;
    private Context context;

    public SchoolIconAdapter(Context context, List<SchoolInfor.Icon> list) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_school_icon_conten, parent, false);
        return new IconContentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IconContentHolder iconContentHolder = (IconContentHolder) holder;
        Log.e("RecyclerView", list.get(position).getItem());
        Glide.with(context)
                .load(list.get(position).getItem())
                .apply(new RequestOptions().placeholder(R.drawable.default_product))
                .into(iconContentHolder.sc_icon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class IconContentHolder extends RecyclerView.ViewHolder {

        private final ImageView sc_icon;

        public IconContentHolder(View itemView) {
            super(itemView);
            sc_icon = itemView.findViewById(R.id.sc_icon);
        }

    }

}
