package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 * 提现完成
 */

public class WithdrawActivity extends AppCompatActivity implements View.OnClickListener {


    private final int codeTimes = 3;

    private TextView _tip;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        _tip = findViewById(R.id._tip);


        try {
            BearMallAplication.getInstance().getActivityStack().finishActivity(BalanceActivity.class);
            BearMallAplication.getInstance().getActivityStack().finishActivity(BalanceWithdrawalActivity.class);
            BearMallAplication.getInstance().getActivityStack().finishActivity(BalanceWithdrawalWxActivity.class);
        } catch (Exception e) {
        }

        findViewById(R.id.new_version_back).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);


        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(codeTimes)
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
                        WithdrawActivity.this.finish();
                    }
                });


    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
