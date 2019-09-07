package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.bean.ZeroMyItemBean;
import com.yunqin.bearmall.eventbus.PartSnatchEvent;
import com.yunqin.bearmall.inter.OnPartSnatchListener;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.SnatchDetailActivity;
import com.yunqin.bearmall.ui.dialog.BuySnatchDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by chenchen on 2018/8/6.
 */

public class SnatchContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,OnPartSnatchListener{

    private Activity context;
    private List<Treasure> treasures;
    private int isToday;

    public SnatchContentAdapter(Activity context,List<Treasure> treasures,int isToday) {
        this.context = context;
        this.treasures = treasures;
        this.isToday = isToday;

        mCountdownVHList = new SparseArray<>();
        startRefreshTime();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){

            case 0:
                View view = LayoutInflater.from(context).inflate(R.layout.item_snatch_header,parent,false);

                return new SnatchHeaderHolder(view);

            case 1:
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_snatch_content,parent,false);

                return new SnatchViewHolder(view);

        }

    }

    @Override
    public int getItemViewType(int position) {

        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
//
//        if (position == 0){
//
//            holder.itemView.setOnClickListener(listener);
//
//        }else {

            Treasure treasure = treasures.get(position);

            SnatchViewHolder mHolder = (SnatchViewHolder) holder;

            mHolder.snatchTitleView.setText(treasure.getTreasureName());

            mHolder.snatchCenterItemView.setText(String.format("已有%d人次参加",treasure.getParticipationPerson()));

            mHolder.snatchPreView.setText("BC"+treasure.getCost());

            mHolder.snatchPriceView.setText("BC"+treasure.getReward());

            mHolder.itemView.setOnClickListener(listener);

            mHolder.bottomLayout.setOnClickListener(this);

            mHolder.bottomLayout.setTag(position);

            if (treasure.getStatus() == 1){

                mHolder.textView.setVisibility(View.VISIBLE);

                mHolder.countdownView.setVisibility(View.VISIBLE);

                mHolder.unstartTextView.setVisibility(View.INVISIBLE);

                mHolder.bindData(treasure);

                if (treasure.getRestTime() > 0) {
                    synchronized (mCountdownVHList) {
                        mCountdownVHList.put(position, mHolder);
                    }
                }


            }else if (treasure.getStatus() == 0){

                mHolder.textView.setVisibility(View.INVISIBLE);

                mHolder.countdownView.setVisibility(View.INVISIBLE);

                mHolder.unstartTextView.setVisibility(View.VISIBLE);

                mHolder.unstartTextView.setText("活动已经结束");

            }else {

                mHolder.textView.setVisibility(View.INVISIBLE);

                mHolder.countdownView.setVisibility(View.INVISIBLE);

                mHolder.unstartTextView.setVisibility(View.VISIBLE);

                mHolder.unstartTextView.setText("活动未开始");

            }

            Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(treasure.getImg()).into(mHolder.snatchItemImage);

//        }

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = (int) v.getTag();

//            if (position == 0){
//
//                Intent intent = new Intent(context, DailyTasksActivity.class);
//
//                context.startActivity(intent);
//
//            }else {

                Treasure treasure = treasures.get(position);

                treasure.setIsToday(isToday);

                SnatchDetailActivity.startSnatchDetailActivity(context,treasure);

//            }

        }
    };

    @Override
    public int getItemCount() {

        return treasures==null?1:treasures.size();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.snatch_bottom_layout:

                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(context);
                    return;
                }
                int position = (int) v.getTag();
                Treasure treasure = treasures.get(position);
                if (treasure.getStatus()==1){
                    BuySnatchDialog dialog = new BuySnatchDialog(context,treasure,this);
                    dialog.setTempPostition(position);
                    dialog.show();
                }else if (treasure.getStatus()==0){
                    Toast.makeText(context,"活动已结束",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"活动未开始",Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }

    @Override
    public void onBuySuccess(int position,int count) {

        Treasure treasure = treasures.get(position);

        treasure.setParticipationPerson(treasure.getParticipationPerson()+count);

        notifyItemChanged(position);

        EventBus.getDefault().post(new PartSnatchEvent(1,true));

    }

    @Override
    public void onBuyFailer(String msg) {

    }


    class SnatchViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.snatch_image)
        ImageView snatchItemImage;

        @BindView(R.id.snatch_title)
        TextView snatchTitleView;

        @BindView(R.id.bt_rmb)
        TextView snatchPriceView;

        @BindView(R.id.bt_pre)
        TextView snatchPreView;

        @BindView(R.id.snatch_center_text)
        TextView snatchCenterItemView;

        @BindView(R.id.snatch_bottom_layout)
        LinearLayout bottomLayout;

        @BindView(R.id.snatch_unstart_text)
        TextView unstartTextView;

        @BindView(R.id.item_snatch_time)
        CountdownView countdownView;

        @BindView(R.id.text_view)
        TextView textView;

        private Treasure treasureInfo;

        public SnatchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(Treasure treasureInfo){

            this.treasureInfo = treasureInfo;
            if (treasureInfo.getRestTime() > 0) {
                refreshTime(System.currentTimeMillis());
            } else {
                countdownView.allShowZero();
            }

        }

        public void refreshTime(long curTimeMillis) {

            if (null == treasureInfo || treasureInfo.getRestTime() <= 0) return;

            countdownView.updateShow(treasureInfo.getEndTime() - curTimeMillis);
        }


        public Treasure getBean() {
            return treasureInfo;
        }

    }

    class SnatchHeaderHolder extends RecyclerView.ViewHolder{


        public SnatchHeaderHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }


    private SparseArray<SnatchViewHolder> mCountdownVHList;
    private Handler mHandler = new Handler();
    private Timer mTimer;
    private boolean isCancel = true;
    public void startRefreshTime() {
        if (!isCancel) return;

        if (null != mTimer) {
            mTimer.cancel();
        }

        isCancel = false;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(mRefreshTimeRunnable);
            }
        }, 0, 10);
    }

    public void cancelRefreshTime() {
        isCancel = true;
        if (null != mTimer) {
            mTimer.cancel();
        }
        mHandler.removeCallbacks(mRefreshTimeRunnable);
    }

    private Runnable mRefreshTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCountdownVHList.size() == 0) return;

            synchronized (mCountdownVHList) {
                long currentTime = System.currentTimeMillis();
                int key;
                boolean isNeedRefresh = false;
                for (int i = 0; i < mCountdownVHList.size(); i++) {
                    key = mCountdownVHList.keyAt(i);
                    SnatchViewHolder curMyViewHolder = mCountdownVHList.get(key);
                    if (currentTime >= curMyViewHolder.getBean().getEndTime()) {
                        // 倒计时结束
                        isNeedRefresh = true;
                        curMyViewHolder.getBean().setRestTime(0);
                        mCountdownVHList.remove(key);
                        notifyDataSetChanged();
                    } else {
                        curMyViewHolder.refreshTime(currentTime);
                    }
                }

                if (isNeedRefresh){
                    EventBus.getDefault().post(new PartSnatchEvent(0,true));
                }
            }
        }
    };

}
