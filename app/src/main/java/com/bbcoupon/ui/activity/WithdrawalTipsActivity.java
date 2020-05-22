package com.bbcoupon.ui.activity;


import android.os.Bundle;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/21
 */
public class WithdrawalTipsActivity extends BaseActivity {

    @BindView(R.id.currentTime)
    TextView mCurrentTime;
    @BindView(R.id.endTime)
    TextView mEndTime;
    private long timedata;

    @Override
    public int layoutId() {
        return R.layout.item_popup_alip_details;
    }

    @Override
    public void init() {
        try {
            timedata = System.currentTimeMillis();

            //当前时间
            Date date = new Date(timedata);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currentTime = format.format(date);

            //两天后的时间
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(date);
            endTime.add(Calendar.DAY_OF_MONTH, 2);// 今天+2天
            Date tomorrow = endTime.getTime();
            String endtime = format.format(tomorrow);

            mCurrentTime.setText(currentTime);
            mEndTime.setText(endtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.clonse_de)
    public void onViewClicked() {
        finish();
    }

}
