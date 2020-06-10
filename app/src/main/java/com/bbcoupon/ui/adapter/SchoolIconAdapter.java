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
import android.widget.TextView;

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

    private List<SchoolInfor.Data2Bean> list;
    private Context context;

    public SchoolIconAdapter(Context context, List<SchoolInfor.Data2Bean> list) {
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
        Glide.with(context)
                .load(list.get(position).getImage())
                .apply(new RequestOptions().placeholder(R.drawable.default_product))
                .into(iconContentHolder.sc_icon);
        iconContentHolder.sc_title.setText(list.get(position).getName());
        iconContentHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIcon != null) {
                    Log.e("iconContentHolder", "onClick: ");
                    onIcon.setIcon(list.get(position).getId(),list.get(position).getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class IconContentHolder extends RecyclerView.ViewHolder {

        private final ImageView sc_icon;
        private final TextView sc_title;

        public IconContentHolder(View itemView) {
            super(itemView);
            sc_icon = itemView.findViewById(R.id.sc_icon);
            sc_title = itemView.findViewById(R.id.sc_title);
        }
    }


    public interface OnIcon {
        void setIcon(int id,String title);
    }

    public OnIcon onIcon;

    public void setOnIcon(OnIcon onIcon) {
        this.onIcon = onIcon;
    }

}
