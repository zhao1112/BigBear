package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.eventbus.PartSnatchEvent;
import com.yunqin.bearmall.ui.dialog.DuoBaoInfoMationDialog;
import com.yunqin.bearmall.ui.fragment.MySnatchFragment;
import com.yunqin.bearmall.ui.fragment.SweetSnatchFragment;
import com.yunqin.bearmall.util.SharedPreferencesHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by chenchen on 2018/8/4.
 */

public class SweetSnatchActivity extends ContainFragmentActivity {

    public static void startSweetSnatchActivity(Context context, int showWhich) {

        Intent intent = new Intent(context, SweetSnatchActivity.class);

        intent.putExtra("POSITION", showWhich);

        context.startActivity(intent);

    }

    private SweetSnatchFragment sweetSnatchFragment;
    private MySnatchFragment mySnatchFragment;

    @BindViews({R.id.left_image, R.id.right_image})
    List<ImageView> images;
    @BindViews({R.id.left_text, R.id.right_text})
    List<TextView> textViews;
    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.toolbar_right_text)
    TextView rightTitleView;

    private int position;

    private DuoBaoInfoMationDialog duoBaoInfoMationDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_sweet_snatch;
    }

    @Override
    public void init() {

        super.init();

        rightTitleView.setVisibility(View.VISIBLE);

        rightTitleView.setTextColor(Color.parseColor("#999999"));

//        rightTitleView.setTextSize(getResources().getDimension(R.dimen.ds24));

        int postition = getIntent().getIntExtra("POSITION", 0);

        setSelect(postition, false);

        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(PartSnatchEvent messageEvent) {

        int position = messageEvent.getPostion();

        boolean isRefrsh = messageEvent.isRefresh();

        setSelect(position, isRefrsh);

    }


    private void setSelect(int position, boolean isRefresh) {

        if (position == 1) {
            if (BearMallAplication.getInstance().getUser() == null) {
                LoginActivity.starActivity(this);
                return;
            }
        }

        this.position = position;
        setTitleText(position);

        for (int i = 0; i < images.size(); i++) {
            if (position == i) {
                images.get(i).setSelected(true);
                textViews.get(i).setSelected(true);
            } else {
                images.get(i).setSelected(false);
                textViews.get(i).setSelected(false);
            }
        }

        switch (position) {

            case 0:

                if (sweetSnatchFragment == null || isRefresh) {

                    removeFragment(sweetSnatchFragment);

                    sweetSnatchFragment = new SweetSnatchFragment();

                    addFragment(R.id.fragment_container, sweetSnatchFragment);

                }

                hideFragment(mySnatchFragment);

                showFragment(sweetSnatchFragment);
                break;

            case 1:

                if (mySnatchFragment == null || isRefresh) {

                    removeFragment(mySnatchFragment);

                    mySnatchFragment = new MySnatchFragment();

                    addFragment(R.id.fragment_container, mySnatchFragment);

                }

                hideFragment(sweetSnatchFragment);

                showFragment(mySnatchFragment);

                break;

            default:
                break;

        }

    }

    @OnClick({R.id.toolbar_right_text})
    protected void rightClick(View view) {

//        showToast("点击了"+position);

        switch (position) {

            case 0:

                if (duoBaoInfoMationDialog == null) {
                    duoBaoInfoMationDialog = new DuoBaoInfoMationDialog(SweetSnatchActivity.this);
                } else {
                    duoBaoInfoMationDialog.show();
                }

                break;

            case 1:

                PastActivity.startPastActivity(this, 0);

                break;

        }

    }


    @OnClick({R.id.toolbar_back})
    protected void backClick(View view) {
        finish();
    }

    @OnClick({R.id.bottom_left, R.id.bottom_right})
    protected void switchClick(View view) {

        switch (view.getId()) {

            case R.id.bottom_left:

                setSelect(0, false);

                break;

            case R.id.bottom_right:

                setSelect(1, false);

                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void setTitleText(int position) {

        switch (position) {

            case 0:

                rightTitleView.setText("夺宝说明");

                if((int) SharedPreferencesHelper.get(this,"IsHidePointFunc",0) == 0){
                    titleView.setText("糖果夺宝");
                }else {
                    titleView.setText("糖果礼遇");
                }
//                titleView.setText("糖果夺宝");
                // TODO 要改回来
//                titleView.setText("糖果礼遇");

                break;

            case 1:

                rightTitleView.setText("往期详情");

                titleView.setText("我的夺宝");

                break;

        }

    }


}
