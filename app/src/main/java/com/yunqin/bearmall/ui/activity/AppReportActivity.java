package com.yunqin.bearmall.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.activity.SplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Master
 * @create 2018/8/16 19:30
 */
public class AppReportActivity extends AppCompatActivity {

    @BindView(R.id.error_content)
    TextView mErrorContentTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_report);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String errorContent = intent.getStringExtra("error_data");
        if (errorContent == null) {
            finish();
            return;
        }
        mErrorContentTextView.setText(errorContent);
    }


    @OnClick({R.id.button_left, R.id.button_right})
    public void restartApp(View view) {
        switch (view.getId()) {
            case R.id.button_left:
                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                PendingIntent restartIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, restartIntent);

                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            case R.id.button_right:
                // TODO 上报错误信息
                break;
            default:
                break;
        }

    }


}
