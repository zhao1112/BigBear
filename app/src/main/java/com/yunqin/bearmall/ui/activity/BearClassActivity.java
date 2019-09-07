package com.yunqin.bearmall.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BearFAQ;
import com.yunqin.bearmall.ui.activity.contract.BearClassContract;
import com.yunqin.bearmall.ui.activity.presenter.BearClassPresent;
import com.yunqin.bearmall.ui.fragment.QusetionFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by chenchen on 2018/7/25.
 */

public class BearClassActivity extends ContainFragmentActivity implements BearClassContract.IUI,QusetionFragment.OnAnwerItemClickedListener{


    @BindView(R.id.ti_total)
    TextView titotalNumTextVeiw;

    @BindView(R.id.ti_current)
    TextView tiCurrentTextView;

    @BindView(R.id.answer_ing)
    View answeringView;

    @BindView(R.id.answer_success)
    View successView;

    @BindView(R.id.answer_faile)
    View failView;

    @BindView(R.id.ti_earn_bt)
    TextView tiEarnBTText;

    @BindView(R.id.ti_timer)
    TextView tiTimerText;

    @BindView(R.id.ti_error_text)
    TextView tiErrorView;

    @BindView(R.id.ti_all_bt)
    TextView tiAllBT;

    @BindView(R.id.anser_all)
    View allView;

    @BindView(R.id.ti_error_top_text)
    TextView errorTopView;

    @BindView(R.id.toolbar_title)
    TextView titleView;

    private int perReward;
    private int allReward;
    private boolean isStart;
    private BearClassContract.IPresent present;
    private CompositeDisposable compositeDisposable;


    @Override
    public int layoutId() {
        return R.layout.activity_bear_class;
    }

    @Override
    public void init() {
        super.init();

        titleView.setText("答题任务");

        compositeDisposable = new CompositeDisposable();

        present = new BearClassPresent(this);

        present.start(this);

    }


    @OnClick(R.id.toolbar_back)
    void backClick(View view){

        if (isStart){

            new AlertDialog.Builder(this).setTitle("注意").setMessage("答题尚未结束，退出将消耗一次机会，确定退出吗？").setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();

        }else {
            finish();
        }

    }

    @Override
    public void onBackPressed() {

        if (isStart){
            new AlertDialog.Builder(this).setTitle("注意").setMessage("答题尚未结束，退出将消耗一次机会，确定退出吗？").setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();

        }else {
            finish();
        }
    }

    @Override
    public void onGetInitData(BearFAQ data) {

        try {
            BearFAQ.DataBean dataBean = data.getData();

//            if (!dataBean.isActive()){
//
//                return;
//            }
            if (dataBean.getIsFinished() == 1){
                isStart = false;

                errorTopView.setText("今日机会已用尽，明日再来吧");
                answeringView.setVisibility(View.GONE);
                failView.setVisibility(View.VISIBLE);
                tiErrorView.setText(String.format("%ds后返回上一页",3));
                finishTiDelay();

            }else {

                isStart = true;

                int clearanceReward = dataBean.getClearanceReward();

                int clearanceNumb = dataBean.getClearanceNumber();

                perReward = dataBean.getPerReward();

                allReward = clearanceReward;

                titotalNumTextVeiw.setText(String.format("共%d题",clearanceNumb));

                BearFAQ.QusetionDate current = dataBean.getCurrent();

                tiCurrentTextView.setText(String.format("正在回答第%d题",current.getTag()+1));

                showTi(current);

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void showTi( BearFAQ.QusetionDate current){

        if (current != null){

            QusetionFragment fragment = QusetionFragment.fragmentWithQusetion(current.getTag(),current.getTitle(),current.getOptions0(),current.getOptions1(),current.getOptions2(),current.getOptions3());

            fragment.setOnAnwerItemClickedListener(this);

            replaceFragment(R.id.fragment_container,fragment);

        }

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onGetNextTiData(BearFAQ data) {

        BearFAQ.DataBean dataBean = data.getData();

        int isAnswer = dataBean.getIsAnswer();

        int isFinished = dataBean.getIsFinished();

        //全部回答完毕 通过
        if ( isAnswer == 1 && dataBean.getIsFinished() == 1){

            isStart = false;
            answeringView.setVisibility(View.GONE);
            allView.setVisibility(View.VISIBLE);
            tiAllBT.setText(String.format("BC糖果+%d",allReward));

        }else if ( isAnswer == 0 && isFinished == 1){
            //回答错误
            isStart = false;
            answeringView.setVisibility(View.GONE);
            failView.setVisibility(View.VISIBLE);
            errorTopView.setText("抱歉回答错误 正确答案：" + dataBean.getLastRightAnswer());
            tiErrorView.setText(String.format("%ds后返回上一页",3));
            finishTiDelay();

        }else if (isAnswer == 1 && isFinished == 0){
            isStart = true;
            //下一题
            answeringView.setVisibility(View.GONE);
            successView.setVisibility(View.VISIBLE);
            tiTimerText.setText(String.format("%ds后进入下一题",3));
            tiEarnBTText.setText(String.format("BC糖果+%d",perReward));

            BearFAQ.QusetionDate current = dataBean.getCurrent();
            beginNextTiDelay(current);
            tiCurrentTextView.setText(String.format("正在回答第%d题",current.getTag()+1));

        }


    }

    private void finishTiDelay(){

        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        long remain = 2 - aLong;
                        if (remain != 0){
                            tiErrorView.setText(String.format("%ds后返回上一页",remain));
                        }
                        if (remain == 0){
                            disposable.dispose();
                            compositeDisposable.remove(disposable);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void beginNextTiDelay( BearFAQ.QusetionDate current) {

        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        long remain = 2 - aLong;
                        if (remain != 0){
                            tiTimerText.setText(String.format("%ds后进入下一题",remain));
                        }
                        if (remain == 0){
                            disposable.dispose();
                            compositeDisposable.remove(disposable);
                            answeringView.setVisibility(View.VISIBLE);
                            successView.setVisibility(View.GONE);
                            showTi(current);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onAnswerClicked(int tag, String answer) {

        present.onRequestNextTI(tag,answer);

    }
}
