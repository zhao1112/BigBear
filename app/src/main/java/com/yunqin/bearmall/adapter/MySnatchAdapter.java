package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.MySnatch;
import com.yunqin.bearmall.bean.SnatchContent;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.bean.ZeroMyItemBean;
import com.yunqin.bearmall.eventbus.PartSnatchEvent;
import com.yunqin.bearmall.inter.OnPartSnatchListener;
import com.yunqin.bearmall.ui.activity.PropertyActivity;
import com.yunqin.bearmall.ui.dialog.BuySnatchDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by chenchen on 2018/8/7.
 */

public class MySnatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,OnPartSnatchListener{

    private Activity context;
    private List<Treasure> datas;

    public MySnatchAdapter(Activity context, List<Treasure> datas) {
        this.context = context;
        this.datas = datas;

        mCountdownVHList = new SparseArray<>();
        startRefreshTime();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0){

            View view = LayoutInflater.from(context).inflate(R.layout.item_my_snatch_unstart,parent,false);

            return new UnStartViewHolder(view);

        }else if (viewType == 1){

            View view = LayoutInflater.from(context).inflate(R.layout.item_my_snatch_has_result,parent,false);

            return new FinishedViewHolder(view);
        }else {

            View view = LayoutInflater.from(context).inflate(R.layout.layout_empty_view,parent,false);

            return new EmptyViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try {
            if (getItemViewType(position) == 2){

                EmptyViewHolder mHolder = (EmptyViewHolder) holder;

                mHolder.emptyText.setText("暂无夺宝哦");

            }else {
                Treasure info = datas.get(position);

                int status = info.getStatus();

                if (status == 1){

                    UnStartViewHolder viewHolder = (UnStartViewHolder) holder;

                    viewHolder.bindData(info);

                    if (info.getRestTime() > 0) {
                        synchronized (mCountdownVHList) {
                            mCountdownVHList.put(position, viewHolder);
                        }
                    }

                    Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(info.getImg()).into(viewHolder.imageView);

                    viewHolder.titleView.setText(info.getName());

                    viewHolder.joinView.setText(String.format("已有%d人次参与",info.getParticipationPerson()));

                    viewHolder.buyCountView.setText(String.format("已购买%d份",info.getPurchaseCount()));

                    viewHolder.btPre.setText(String.format("每份BC%s",info.getCost()));

                    viewHolder.prePreceView.setTag(position);

                    viewHolder.prePreceView.setOnClickListener(this);

                }else {

                    FinishedViewHolder viewHolder = (FinishedViewHolder) holder;

                    Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(info.getImg()).into(viewHolder.imageView);

                    viewHolder.titleView.setText(info.getName());

                    int isReward = info.getIsReward();

                    viewHolder.showWalletView.setTag(position);

                    viewHolder.rejoinView.setTag(position);

                    viewHolder.rejoinView.setOnClickListener(this);

                    viewHolder.showWalletView.setOnClickListener(this);

                    viewHolder.joinNubView.setText(String.format("已有%d人次参与",info.getParticipationPerson()));

                    viewHolder.buyCountView.setText(String.format("已购买%d份",info.getPurchaseCount()));

                    if (isReward == 1){

                        viewHolder.descView.setText(String.format("恭喜您，已中%s糖果大礼包",info.getReward()));

                        viewHolder.descView.setTextColor(Color.parseColor("#F5A623"));

                        viewHolder.resultView.setText("已中奖");

                        viewHolder.coverView.setBackgroundColor(Color.parseColor("#F5A623"));

                        viewHolder.showWalletView.setVisibility(View.VISIBLE);

                    }else {

                        viewHolder.resultView.setText("未中奖");

                        viewHolder.descView.setText("很遗憾，你未中奖");

                        viewHolder.descView.setTextColor(Color.parseColor("#999999"));

                        viewHolder.coverView.setBackgroundColor(Color.parseColor("#999999"));

                        viewHolder.showWalletView.setVisibility(View.INVISIBLE);

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return (datas == null || datas.isEmpty())?1:datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (datas == null || datas.isEmpty()){

            return 2;
        }

       Treasure info = datas.get(position);

        int status = info.getStatus();

        if (status == 1){
            return 0;
        }else{

            return 1;
        }

    }

    @Override
    public void onClick(View v) {

        int position = (int) v.getTag();

        Treasure treasure = datas.get(position);

        switch (v.getId()){

            case R.id.snatch_bottom_layout:

                BuySnatchDialog dialog = new BuySnatchDialog(context,treasure,this);
                dialog.setTempPostition(position);
                dialog.show();

                break;

            case R.id.item_show_sweet:
                PropertyActivity.startPropertyActivity(context,1, "-1", "-1",null,null);
                break;

            case R.id.item_my_snatch_pre_finish:

                EventBus.getDefault().post(new PartSnatchEvent(0,false));

                break;

        }

    }

    @Override
    public void onBuySuccess(int postition, int count) {
        Treasure info = datas.get(postition);
        info.setParticipationPerson(info.getParticipationPerson()+count);
        info.setPurchaseCount(info.getPurchaseCount()+count);
        notifyItemChanged(postition);
    }

    @Override
    public void onBuyFailer(String msg) {

    }


    class UnStartViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_my_snatch_image)
        ImageView imageView;

        @BindView(R.id.item_my_snatch_title)
        TextView titleView;

        @BindView(R.id.join_num)
        TextView joinView;

        @BindView(R.id.buy_count)
        TextView buyCountView;

        @BindView(R.id.item_my_snatch_time)
        CountdownView finishTimeView;

        @BindView(R.id.snatch_bottom_layout)
        LinearLayout prePreceView;

        @BindView(R.id.bt_pre)
        TextView btPre;

        private Treasure treasureInfo;

        public UnStartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(Treasure treasureInfo){

            this.treasureInfo = treasureInfo;
            if (treasureInfo.getRestTime() > 0) {
                refreshTime(System.currentTimeMillis());
            } else {
                finishTimeView.allShowZero();
            }

        }

        public void refreshTime(long curTimeMillis) {

            if (null == treasureInfo || treasureInfo.getRestTime() <= 0) return;

            finishTimeView.updateShow(treasureInfo.getEndTime() - curTimeMillis);
        }


        public Treasure getBean() {
            return treasureInfo;
        }
    }



    class FinishedViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_my_snatch_image)
        ImageView imageView;

        @BindView(R.id.image_alph)
        View coverView;

        @BindView(R.id.item_my_snatch_title)
        TextView titleView;

        @BindView(R.id.result)
        TextView resultView;

        @BindView(R.id.desc)
        TextView descView;

        @BindView(R.id.item_show_sweet)
        Button showWalletView;

        @BindView(R.id.item_my_snatch_pre_finish)
        Button rejoinView;

        @BindView(R.id.join_num)
        TextView joinNubView;

        @BindView(R.id.buy_count)
        TextView buyCountView;

        public FinishedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }


    class EmptyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.empty_text)
        TextView emptyText;


        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    private SparseArray<UnStartViewHolder> mCountdownVHList;
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
                    UnStartViewHolder curMyViewHolder = mCountdownVHList.get(key);
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
                    EventBus.getDefault().post(new PartSnatchEvent(1,true));
                }
            }
        }
    };




}
