package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.newversions.CardListWebActivity;
import com.newversions.tbk.activity.IncomeRecordActivity;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.CustomRotateAnim;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.SweetRecordAllType;
import com.yunqin.bearmall.ui.activity.SweetRecordActivity;
import com.yunqin.bearmall.util.NetUtils;
import com.yunqin.bearmall.util.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenchen on 2018/7/23.
 */

public class RecordAllTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final String SIGN = "每日签到";
    private static final String SHARE = "分享资讯";
    private static final String INVITE = "邀请好友";
    private static final String TASK = "任务奖励";
    private static final String OTHER = "其他";
    private static final String GOODS = "分享商品";

    private String total;
    private String today;
    private Activity context;
    private List<SweetRecordAllType.DataBean> dataBeans;
    private OnRefreshClickListener onRefreshClickListener;

    public void setOnRefreshClickListener(OnRefreshClickListener onRefreshClickListener) {
        this.onRefreshClickListener = onRefreshClickListener;
    }

    public RecordAllTypeAdapter(String total, String today, Activity context, List<SweetRecordAllType.DataBean> dataBeans) {
        this.total = total;
        this.today = today;
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_record_type_header, parent, false);
            return new RecordAllHeaderHolder(view);
        } else if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_record_type_normal, parent, false);
            return new RecordAllTypeHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_net_error, parent, false);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = ScreenUtils.getScreenHeight(context) - 340 - 80;
            return new NoNetTypeHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (position == 0) {
                RecordAllHeaderHolder mHolder = (RecordAllHeaderHolder) holder;
                mHolder.todayView.setText(today);
                mHolder.totalView.setText(total);
                showAnimation(mHolder.red_package_img);
                mHolder.red_package_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mHolder.constraintLayout.setVisibility(View.GONE);
                    }
                });
                mHolder.red_package_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CardListWebActivity.startActivity(context, AdConstants.STRING_SHOU_YI_JI_LU, "");
                    }
                });
            } else if (NetUtils.isConnected(context)) {
                RecordAllTypeHolder mHolder = (RecordAllTypeHolder) holder;
                SweetRecordAllType.DataBean bean = dataBeans.get(position);
                mHolder.recordCountView.setText(bean.getCreditSum());
                mHolder.itemView.setTag(position);
                mHolder.itemView.setOnClickListener(this);
                switch (bean.getType()) {
                    case 0:
                        //每日签到
                        mHolder.recordContentView.setText(SIGN);
                        mHolder.imageView.setImageResource(R.drawable.icon_bt_sign);
                        break;
                    case 1:
                        //分享资讯
                        mHolder.recordContentView.setText(SHARE);
                        mHolder.imageView.setImageResource(R.drawable.icon_bt_info);
                        break;
                    case 2:
                        //邀请好友
                        mHolder.recordContentView.setText(INVITE);
                        mHolder.imageView.setImageResource(R.drawable.icon_bt_invite);
                        break;
                    case 3:
                        //购物
                        mHolder.recordContentView.setText("购物");
                        mHolder.imageView.setImageResource(R.mipmap.icon_shopping);
                        break;
//                    case 3:
//                        //任务奖励
//                        mHolder.recordContentView.setText(TASK);
//                        mHolder.imageView.setImageResource(R.drawable.icon_bt_task);
//                        break;
                    case 4:
                        //分享商品
                        mHolder.recordContentView.setText(GOODS);
                        mHolder.imageView.setImageResource(R.drawable.icon_bt_goods);
                        break;
                    case 5:
                        //阅读小说
                        mHolder.recordContentView.setText("阅读小说");
                        mHolder.imageView.setImageResource(R.mipmap.icon_book);
                        break;
                    case 6:
                        //幸运大抽奖
                        mHolder.recordContentView.setText("幸运大抽奖");
                        mHolder.imageView.setImageResource(R.mipmap.icon_gift);
                        break;
//                    case 5:
//                        //其他
//                        mHolder.recordContentView.setText(OTHER);
//                        mHolder.imageView.setImageResource(R.drawable.icon_bt_other);
//                        break;
                    case 7:
                        //幸运大抽奖
                        mHolder.recordContentView.setText("支出记录");
                        mHolder.imageView.setImageResource(R.mipmap.app_xiaofei);
                        break;
                }

            } else {
                NoNetTypeHolder mHolder = (NoNetTypeHolder) holder;
                mHolder.refreshBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onRefreshClickListener != null) {
                            onRefreshClickListener.onRefreshClicked();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        if (NetUtils.isConnected(context)) {
            return dataBeans == null ? 1 : dataBeans.size();
        } else {
            return 2;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : NetUtils.isConnected(context) ? 1 : 2;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (position == 4) {
            context.startActivity(new Intent(context, IncomeRecordActivity.class));
        } else {
            SweetRecordAllType.DataBean bean = dataBeans.get(position);
            SweetRecordActivity.startIncomeActivity(1, bean.getType() + "", context);
        }
    }

    class RecordAllHeaderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_total)
        TextView totalView;
        @BindView(R.id.text_today)
        TextView todayView;
        @BindView(R.id.red_package_layout)
        ConstraintLayout constraintLayout;
        @BindView(R.id.red_package_close)
        ImageView red_package_close;
        @BindView(R.id.red_package_img)
        ImageView red_package_img;

        public RecordAllHeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private void showAnimation(ImageView imageView) {
        // 获取自定义动画实例
        CustomRotateAnim rotateAnim = CustomRotateAnim.getCustomRotateAnim();
        // 一次动画执行1秒
        rotateAnim.setDuration(1000);
        // 设置为循环播放
        rotateAnim.setRepeatCount(-1);
        // 设置为匀速
        rotateAnim.setInterpolator(new LinearInterpolator());
        // 开始播放动画
        imageView.startAnimation(rotateAnim);
    }


    class RecordAllTypeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.record_image)
        ImageView imageView;
        @BindView(R.id.record_content)
        TextView recordContentView;
        @BindView(R.id.record_count)
        TextView recordCountView;
        public RecordAllTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class NoNetTypeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reset_load_data)
        Button refreshBtn;

        public NoNetTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    public interface OnRefreshClickListener {
        void onRefreshClicked();
    }


}
