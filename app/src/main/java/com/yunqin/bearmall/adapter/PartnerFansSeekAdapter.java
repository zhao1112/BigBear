package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.PartnerFansSeekBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartnerFansSeekAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<PartnerFansSeekBean.DataBean.FansBean> list;
    private RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_product)//图片加载出来前，显示的图片
            .fallback(R.drawable.default_product) //url为空的时候,显示的图片
            .error(R.drawable.default_product)//图片加载失败后，显示的图片
            .bitmapTransform(new RoundedCorners(3));
    private String phone;
    private String mFansLevel;

    public PartnerFansSeekAdapter(Context context) {
        mContext = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.partner_fans_seek_item, parent, false);
        return new FansSeek(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FansSeek fansSeek = (FansSeek) holder;
        //图片
        Glide.with(mContext).load(list.get(position).getIconUrl()).apply(mOptions).into(fansSeek.mFanSeekAdapterImage);
        //时间
        fansSeek.mFanseekAdapterTime.setText("注册会员"+list.get(position).getCreatedDate()+"");
        //等级
        int level = list.get(position).getLevel();
        if (level == 0) {
            mFansLevel = "注册会员";
        } else if (level == 1) {
            mFansLevel = "超级会员";
        } else if (level == 2 || level == 4 || level == 4 || level == 5) {
            mFansLevel = "大团长";
        } else if (level == 6) {
            mFansLevel = "合伙人";
        }
        fansSeek.mFansSeekAdapterVip.setText(mFansLevel);
        //手机
        String mobile = list.get(position).getMobile();
        if (mobile != null && !"".equals(mobile)) {

            if (isMobileNum(mobile)) {

                phone = mobile.substring(0, 3) + "****" + mobile.substring(7);
            }
        }
        fansSeek.mFansSeekAdapterPhone.setText(phone);
        fansSeek.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFansNextCheckListener!=null){
                    onFansNextCheckListener.onFansNext(list.get(position).getCreatedDate()+"",list.get(position).getId()+"",list.get(position).getIconUrl(),
                            list.get(position).getMobile()+"",list.get(position).getLevel()+"");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<PartnerFansSeekBean.DataBean.FansBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
    }

    private class FansSeek extends RecyclerView.ViewHolder {
        private ImageView mFanSeekAdapterImage, mFanSeekAdapterNext;
        private TextView mFansSeekAdapterPhone, mFanseekAdapterTime, mFansSeekAdapterVip;

        public FansSeek(View itemView) {
            super(itemView);
            //粉丝图片
            mFanSeekAdapterImage = itemView.findViewById(R.id.fans_seek_adapter_image);
            //粉丝手机号
            mFansSeekAdapterPhone = itemView.findViewById(R.id.fans_seek_adapter_phone);
            //注册时间
            mFanseekAdapterTime = itemView.findViewById(R.id.fans_seek_adapter_time);
            //粉丝等级
            mFansSeekAdapterVip = itemView.findViewById(R.id.fans_seek_adapter_vip);
            //下一步
            mFanSeekAdapterNext = itemView.findViewById(R.id.fans_seek_adapter_next);
        }
    }

    private boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private OnFansNextCheckListener onFansNextCheckListener;

    public interface OnFansNextCheckListener {
        void onFansNext(String createdDate,String id,String iconUrl,String mobile,String level);
    }

    public void setOnFansNextCheckListener(OnFansNextCheckListener fansNextCheckListener) {
        onFansNextCheckListener = fansNextCheckListener;
    }

}
