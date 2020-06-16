package com.bbcoupon.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunqin.bearmall.R;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/16
 */
public class SweetFanshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public SweetFanshAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_sweet_fansh, parent, false);
        return new SweeFanshtHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private class SweeFanshtHolder extends RecyclerView.ViewHolder {

        public SweeFanshtHolder(View itemView) {
            super(itemView);
        }

    }
}
