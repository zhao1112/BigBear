package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.eventbus.UpdateBankEvent;
import com.yunqin.bearmall.eventbus.UpdateBankListEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 * 验证完成
 */

public class VerifySuccessActivity extends AppCompatActivity implements View.OnClickListener {


    private final int codeTimes = 3;

    private TextView _tip;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VerifySuccessActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_success);

        EventBus.getDefault().post(new UpdateBankEvent(true));
        EventBus.getDefault().post(new UpdateBankListEvent(true));
        
        _tip = findViewById(R.id._tip);

        findViewById(R.id.new_version_back).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);


        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(codeTimes - 1)
                .map(aLong -> codeTimes - aLong)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        _tip.setText(value + "秒后自动返回");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        VerifySuccessActivity.this.finish();
                    }
                });


    }

    @Override
    public void onClick(View view) {
        VerifySuccessActivity.this.finish();
    }
}
