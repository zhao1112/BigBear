package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.bean.SnatchContent;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.eventbus.PartSnatchEvent;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.inter.OnPartSnatchListener;
import com.yunqin.bearmall.inter.ProductInstructionCallBack;
import com.yunqin.bearmall.inter.ScrollViewForActivityListener;
import com.yunqin.bearmall.ui.dialog.BuySnatchDialog;
import com.yunqin.bearmall.ui.dialog.DuoBaoInfoMationDialog;
import com.yunqin.bearmall.ui.dialog.PriceInstructionDialog;
import com.yunqin.bearmall.ui.dialog.ServiceInstructionDialog;
import com.yunqin.bearmall.ui.fragment.SnatchDetailFragment;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.ScreenUtils;
import com.yunqin.bearmall.util.ShareUtil;
import com.yunqin.bearmall.widget.DeficitScrollView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by chenchen on 2018/8/8.
 */

public class SnatchDetailActivity extends ContainFragmentActivity implements ProductInstructionCallBack,ScrollViewForActivityListener,OnPartSnatchListener {

    public static void startSnatchDetailActivity(Context context,Treasure treasure){

        Intent intent = new Intent(context,SnatchDetailActivity.class);

        intent.putExtra("OBJECT",treasure);

        context.startActivity(intent);

    }

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.transparent_status_head_layout)
    RelativeLayout transparentStatusHeadLayout;
    @BindView(R.id.product_detail_join_act)
    LinearLayout botomRightLayout;

    @BindView(R.id.buy_money)
    TextView buyView;
    @BindView(R.id.status_text)
    TextView statusView;
    @BindView(R.id.snatch_timer_view)
    CountdownView timerView;
    @BindView(R.id.pre_money)
    TextView preMoneyView;

    private ServiceInstructionDialog serviceInstructionDialog;
    private PriceInstructionDialog priceInstructionDialog;
    private DuoBaoInfoMationDialog duoBaoInfoMationDialog;
    private float mAppBarAlptha = 0f;
    private float mScreenWidth;
    private Treasure treasure;

    @Override
    public int layoutId() {
        return R.layout.activity_snatch_detail;
    }

    @Override
    public void init() {
        super.init();
        this.treasure = getIntent().getParcelableExtra("OBJECT");
        immerseStatusBar();//设置viewPager中fragment沉浸式状态栏
        changStatusIconCollor(true);//状态栏icon为深色
        mScreenWidth = (float) ScreenUtils.getScreenWidth(this);//获取屏幕宽度，用来计算appbar
        Treasure treasure = getIntent().getParcelableExtra("OBJECT");
        replaceFragment(R.id.fragment_container,SnatchDetailFragment.instance(treasure));

        timerView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                updateData();
            }
        });

        if (treasure!=null){
            updateData();
        }

    }

    private void updateData() {

        buyView.setText("BC"+treasure.getReward());
        preMoneyView.setText("每份 BC"+treasure.getCost());

        int status = treasure.getStatus();
        //已经结束
        if (status == 0){
            timerView.setVisibility(View.GONE);
            statusView.setText("本场已结束");
            botomRightLayout.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }else if (status == 1 || status == 2){
            botomRightLayout.setBackgroundColor(Color.parseColor("#23A064"));
            timerView.setVisibility(View.VISIBLE);
            if (status  == 2){
                statusView.setText("距本场开始");
            }else {
                statusView.setText("距本场结束");
            }
            timerView.stop();
            long  resetTime = treasure.getRestTime();
            timerView.start(resetTime);
        }

    }

    @OnClick({R.id.show_status_back_img,R.id.transparent_status_head_back,R.id.product_detail_join_act,R.id.transparent_status_share_img})
    protected void onViewCicked(View view){

        switch (view.getId()){

            case R.id.show_status_back_img:
            case R.id.transparent_status_head_back:

                finish();
                break;

            case R.id.product_detail_join_act:

                if (treasure != null ){

                    if (BearMallAplication.getInstance().getUser() == null) {
                        LoginActivity.starActivity(this);
                        return;
                    }
                    if (treasure.getStatus() == 1){
                        new BuySnatchDialog(this,this.treasure,this).show();
                    }else if (treasure.getStatus() == 2){
                        showToast("本场活动还未开始");
                    }else {
                        showToast("本场活动已结束");
                    }

                }
                break;

            case R.id.transparent_status_share_img:

                if (this.treasure != null){

                    showLoading("正在获取分享信息...");

                    getShareBean(treasure.getTreasure_id());

                }


                break;


        }


    }

    private void getShareBean(String id) {
        Map map = new HashMap();
        map.put("source_id",id);
        map.put("type",2+"");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getShareParams(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                hiddenLoadingView();
                try {
                    ShareBean shareBean = new Gson().fromJson(data,ShareBean.class);
                    ShareUtil.Share(SnatchDetailActivity.this,shareBean.getData(),false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });

    }

    @Override
    public void showInstructionDialog(int flag) {
        if (flag == 0) {
            if (serviceInstructionDialog == null) {
                serviceInstructionDialog = new ServiceInstructionDialog(this);
            } else {
                serviceInstructionDialog.show();
            }
        } else if (flag == 1) {

            if (duoBaoInfoMationDialog == null) {
                duoBaoInfoMationDialog = new DuoBaoInfoMationDialog(this);
            } else {
                duoBaoInfoMationDialog.show();
            }


        } else if (flag == 2) {

        } else if (flag == 3) {
            if (priceInstructionDialog == null) {
                priceInstructionDialog = new PriceInstructionDialog(this);
            } else {
                priceInstructionDialog.show();
            }
        }
    }

    @Override
    public void onScrollChanged(DeficitScrollView scrollView, int x, int y, int oldx, int oldy) {
        mAppBarAlptha = ((float) y) / mScreenWidth;
        if (mAppBarAlptha >= 1) {
            mAppBarAlptha = 1;
        } else {
            appbar.setAlpha(mAppBarAlptha);
            transparentStatusHeadLayout.setAlpha(1 - mAppBarAlptha);
        }
    }

    public void updateNetData(Treasure treasure) {
        this.treasure = treasure;
        updateData();
    }



    @Override
    public void onBuySuccess(int position,int count) {

        showToast("参与成功");

        finish();

        EventBus.getDefault().post(new PartSnatchEvent(1,true));


    }

    @Override
    public void onBuyFailer(String msg) {

    }
}
