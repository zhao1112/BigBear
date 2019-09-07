package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.PastAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.PastSnatchData;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.bean.ZeroPastData;
import com.yunqin.bearmall.ui.activity.contract.PastContract;
import com.yunqin.bearmall.ui.activity.presenter.PastPresenter;
import com.yunqin.bearmall.widget.AutoScrollLayoutManager;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenchen on 2018/8/25.
 */

public class PastActivity extends BaseActivity implements PastContract.IView{


    public static void startPastActivity(Activity context,int type){

        Intent intent = new Intent(context,PastActivity.class);

        intent.putExtra("TYPE",type);

        context.startActivity(intent);

    }

    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.top_image)
    ImageView topImage;
    @BindView(R.id.root)
    View rootView;
    @BindView(R.id.past_title)
    TextView pastTitle;
    @BindView(R.id.center_text)
    TextView centerTextView;
    @BindView(R.id.bottom_top_layout)
    View bottomTopLayout;
    @BindView(R.id.goods)
    TextView goodsView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    //0 夺宝   1 0元
    private int type;

    private PastPresenter pastPresenter;
    private int index = 1;
    private PastAdapter adapter;
    private List<Treasure> treasures = new ArrayList<>();
    private List<ZeroPastData.ZeroPast> pasts = new ArrayList<>();
    private CompositeDisposable compositeDisposable;

    @Override
    public int layoutId() {
        return R.layout.activity_past;
    }

    @Override
    public void init() {

        compositeDisposable = new CompositeDisposable();
        pastPresenter = new PastPresenter(this);
        pastPresenter.start(this);

        type = getIntent().getIntExtra("TYPE",0);

        if (type == 0){

            rootView.setBackground(getResources().getDrawable(R.drawable.shape_snatch_past_bg));
            topImage.setImageResource(R.drawable.icon_snatch_top_bg);
            pastTitle.setText("夺 宝 商 品");
            centerTextView.setText("商品夺宝\n千万+用户夺得好商品");
            bottomTopLayout.setBackgroundColor(Color.parseColor("#482F7F"));
            goodsView.setText("夺宝商品");
            pastPresenter.refreshSnatchPastData(index);

        }else {
            rootView.setBackground(getResources().getDrawable(R.drawable.shape_zero_past_bg));
            topImage.setImageResource(R.drawable.icon_zero_top_bg);
            pastTitle.setText("0 元 拼 团");
            centerTextView.setText("未中奖返参团糖果，并加送糖果\n千万+用户拼得好商品");
            bottomTopLayout.setBackgroundColor(Color.parseColor("#005134"));
            goodsView.setText("拼团商品");
            adapter = new PastAdapter<ZeroPastData.ZeroPast>(this,pasts,1);
            pastPresenter.refreshZeroPastData(index);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        titleView.setText("中奖名单");


    }

    @OnClick({R.id.toolbar_back})
    protected void onViewClicked(View view){

        switch (view.getId()){

            case R.id.toolbar_back:

                finish();

                break;

        }

    }

    @Override
    public void onLoadedSnatchData(PastSnatchData data) {
       try{
           if (data.getCode() == 1){

               PastSnatchData.DataBean bean =data.getData();
               List<Treasure> treasures =  bean.getPastRewardList();
               Collections.reverse(treasures);
               if (treasures!= null&&treasures.size()>4){
                   for (int i=0;i<4;i++){
                       this.treasures.add(treasures.get(0));
                       treasures.remove(0);
                   }
                   handleTreasure(treasures);
               }else {
                   this.treasures = treasures;
               }
               adapter = new PastAdapter<Treasure>(this,this.treasures,0);
               recyclerView.setAdapter(adapter);

           }
       }catch (Exception e){
           e.printStackTrace();
       }

    }

    private void handleTreasure(List<Treasure> treasures){

       Disposable disposable = Observable.interval(800,2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {

                    @Override
                    public void accept(Long aLong) throws Exception {

//                        if (aLong>treasures.size()){
//                            compositeDisposable.clear();
//                        }

                        int position = (int) (aLong%treasures.size());

                        Treasure treasure = treasures.get(position);

                        PastActivity.this.treasures.add(0,treasure);

                        adapter.notifyItemInserted(0);

                        recyclerView.scrollToPosition(0);


                    }
                });

       compositeDisposable.add(disposable);


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onLoadedZeroData(ZeroPastData data) {

        try{
            if (data.getCode() == 1){

                ZeroPastData.DataBean bean =data.getData();

                List<ZeroPastData.ZeroPast> pasts =  bean.getPastGroupRecordList();

                Collections.reverse(pasts);

                if (pasts!= null&&pasts.size()>4){
                    for (int i=0;i<4;i++){
                        this.pasts.add(pasts.get(0));
                        pasts.remove(0);
                    }
                    handleZeroPast(pasts);
                }else {
                    this.pasts = pasts;
                }

                adapter = new PastAdapter<ZeroPastData.ZeroPast>(this,this.pasts,1);

                recyclerView.setAdapter(adapter);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void handleZeroPast( List<ZeroPastData.ZeroPast> pasts){

        Disposable disposable = Observable.interval(800,2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {

                    @Override
                    public void accept(Long aLong) throws Exception {

//                        if (aLong > pasts.size()){
//                            compositeDisposable.clear();
//                        }

                        int position = (int) (aLong%pasts.size());

                        ZeroPastData.ZeroPast treasure = pasts.get(position);

                        PastActivity.this.pasts.add(0,treasure);

                        adapter.notifyItemInserted(0);

                        recyclerView.scrollToPosition(0);

                    }
                });
        compositeDisposable.add(disposable);

    }

}
