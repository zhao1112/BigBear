package com.newversions.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Create By Master
 * On 2019/5/28 15:39
 */
public class ZhuanQianView extends LinearLayout {

    private Context context;


    private ImageView imageView;
    private TextView name;
    private ProgressBar progressBar;
    private TextView progressTv;


    private TextView tip1;
    private HighlightButton tip2;
    private TextView tip3;


    private HashMap<String, String> mHashMap;


    public ZhuanQianView(Context context) {
        this(context, null);
    }

    public ZhuanQianView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZhuanQianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.zhuan_qian_zhong_xin_dialig_item, this);
        initViews();
    }

    private void initViews() {
        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        progressBar = findViewById(R.id.progress_bar);
        progressTv = findViewById(R.id.progress_tv);
        tip1 = findViewById(R.id.yi_ling_qu);
        tip2 = findViewById(R.id.ling_qu);
        tip3 = findViewById(R.id.wei_wan_cheng);

        tip2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitApi.request(context, RetrofitApi.createApi(Api.class).acceptReward(mHashMap), new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        hideView();
                        tip1.setVisibility(VISIBLE);
                        tip1.setText("已领取 X" + mBtAmount);
                    }

                    @Override
                    public void onNotNetWork() {

                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });


            }
        });


    }


    private int mAdId;
    private int mBtAmount;


    public ZhuanQianView setData(String imgUrl, int count, int currentCount, String title, int id, int isReward, int isFinish,
                                 int btAmount) {

        mAdId = id;
        mBtAmount = btAmount;

        mHashMap = new HashMap<>();
        mHashMap.put("raffleConfig_id", "" + mAdId);


        Glide.with(context)
                .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                .load(imgUrl)
                .into(imageView);

        name.setText(title);
        progressTv.setText(String.format("%d/%d", currentCount, count));

        progressBar.setMax(count);
        progressBar.setProgress(currentCount);


        if (isReward == 1) {// 领取了
            tip1.setVisibility(VISIBLE);
            tip1.setText("已领取 X" + btAmount);
        } else {
            if (isFinish == 1) {// 完成了
                tip2.setVisibility(VISIBLE);
            } else {// 未完成
                tip3.setVisibility(VISIBLE);
            }
        }


        return this;
    }



    private void hideView(){
        tip1.setVisibility(GONE);
        tip2.setVisibility(GONE);
        tip3.setVisibility(GONE);
    }





}
