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
import com.yunqin.bearmall.bean.PartnerFansBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartenrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<PartnerFansBean.DataBean.FansBean> list;
    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.mine_user_icon_defult)
            .error(R.drawable.mine_user_icon_defult)
            .centerCrop();
    private String phone;
    private String mFansLevel;

    public PartenrAdapter(Context context) {
        mContext = context;
        list = new ArrayList<>();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fans_adapter_item, parent, false);
        return new PartenrHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PartenrHolder partenrHolder = (PartenrHolder) holder;
        //图片架子啊
        Glide.with(mContext).load(list.get(position).getIconUrl()).apply(requestOptions).into(partenrHolder.mFansAdapterImage);
        //手机号
        String mobile = list.get(position).getMobile();
        if (mobile != null && !"".equals(mobile)) {

            if (isMobileNum(mobile)) {

                phone = mobile.substring(0, 3) + "****" + mobile.substring(7);
            }
        }
        partenrHolder.mFansAdapterPhone.setText(phone);
        //注册时间
        partenrHolder.mFansAdapterTime.setText("注册时间:" + list.get(position).getCreatedDate() + "");
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
        partenrHolder.mFansAdapterVip.setText(mFansLevel);

        partenrHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNextCheckListener!=null){
                    onNextCheckListener.onFansNet(list.get(position).getCreatedDate()+"",list.get(position).getId()+"",list.get(position).getIconUrl(),
                            list.get(position).getMobile()+"",list.get(position).getLevel()+"",list.get(position).getWeixin());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<PartnerFansBean.DataBean.FansBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
    }

    private class PartenrHolder extends RecyclerView.ViewHolder {
        private ImageView mFansAdapterImage, mFansAdapterNext;
        private TextView mFansAdapterPhone, mFansAdapterTime, mFansAdapterVip;

        public PartenrHolder(View itemView) {
            super(itemView);
            //粉丝图片
            mFansAdapterImage = itemView.findViewById(R.id.fans_adapter_image);
            //粉丝手机号
            mFansAdapterPhone = itemView.findViewById(R.id.fans_adapter_phone);
            //注册时间
            mFansAdapterTime = itemView.findViewById(R.id.fans_adapter_time);
            //粉丝等级
            mFansAdapterVip = itemView.findViewById(R.id.fans_adapter_vip);

            //下一步
            mFansAdapterNext = itemView.findViewById(R.id.fans_adapter_next);



        }
    }

    private boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private OnNextCheckListener onNextCheckListener;

    public interface OnNextCheckListener {
        void onFansNet(String createdDate,String id,String iconUrl,String mobile,String level,String wxID);
        void onAppoInNun();
    }

    public void setOnNextCheckListener(OnNextCheckListener nextCheckListener) {
        onNextCheckListener = nextCheckListener;
    }

}
