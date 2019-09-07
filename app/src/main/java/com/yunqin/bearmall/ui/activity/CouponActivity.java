package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newversions.CardListWebActivity;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.fragment.CouponListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class CouponActivity extends ContainFragmentActivity implements CouponListFragment.OnLoadedCouponDataListener {

    public static void startCouponActivity(Context context) {

        Intent intent = new Intent(context, CouponActivity.class);
        context.startActivity(intent);

    }

    /**
     * @param context
     * @param requestCode
     * @param ticketType  0:现金抵扣卷 1:话费抵扣卷
     */
    public static void startCouponActivityWithResult(Activity context, int requestCode, int ticketType) {

        Intent intent = new Intent(context, CouponActivity.class);
        intent.putExtra("type", ticketType);
        context.startActivityForResult(intent, requestCode);
    }

    @BindView(R.id.toolbar_title)
    TextView titleView;

    @BindViews({R.id.left_text, R.id.center_text, R.id.right_text})
    List<TextView> textViews;

    @BindViews({R.id.left_line, R.id.center_line, R.id.right_line})
    List<View> lines;

    private List<CouponListFragment> fragments = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void init() {
        super.init();

        int type = getIntent().getIntExtra("type", -1);

        if (type == 1) {
            titleView.setText("话费优惠券");
        } else if (type == 0) {
            titleView.setText("现金优惠券");
        } else {
            titleView.setText("优惠券");
        }

        fragments.add(CouponListFragment.instance(0, type));
        fragments.add(CouponListFragment.instance(1, type));
        fragments.add(CouponListFragment.instance(2, type));

        addFragment(R.id.fragment_container, fragments.get(0));
        addFragment(R.id.fragment_container, fragments.get(1));
        addFragment(R.id.fragment_container, fragments.get(2));

        for (CouponListFragment fragment : fragments) {
            fragment.setOnLoadedCouponDataListener(this);
        }

        switchToPosition(0);
        switchFragment(0);

        showAnim();


    }

    private void showAnim() {
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_big_small);
        ad_bg_top_img.setAnimation(mAnimation);
        mAnimation.start();
    }


    private void switchToPosition(int position) {

        for (int i = 0; i < textViews.size(); i++) {

            if (position == i) {
                textViews.get(position).setSelected(true);
                lines.get(position).setVisibility(View.VISIBLE);
            } else {
                textViews.get(i).setSelected(false);
                lines.get(i).setVisibility(View.INVISIBLE);
            }

        }

    }

    private void switchFragment(int position) {

        for (int i = 0; i < fragments.size(); i++) {

            if (position == i) {
                showFragment(fragments.get(position));
            } else {
                hideFragment(fragments.get(i));
            }

        }

    }


    @BindView(R.id.more_yhq)
    RelativeLayout relativeLayout;

    @BindView(R.id.ad_bg_top_img)
    ImageView ad_bg_top_img;


    @OnClick({R.id.toolbar_back, R.id.left_text, R.id.center_text, R.id.right_text,
            R.id.more_yhq, R.id.ad_bg_top_img
    })
    public void onViewClick(View view) {

        switch (view.getId()) {

            case R.id.more_yhq:
                CardListWebActivity.startActivity(this, AdConstants.STRING_YOU_HUI_QUAN, "");
                break;
            case R.id.ad_bg_top_img:
                CardListWebActivity.startActivity(this, AdConstants.STRING_YOU_HUI_QUAN, "");
                break;


            case R.id.toolbar_back:

                finish();
                break;

            case R.id.left_text:
            case R.id.center_text:
            case R.id.right_text:
                int positionTag = Integer.parseInt(String.valueOf(view.getTag()));
                switchToPosition(positionTag);
                switchFragment(positionTag);
                break;

        }

    }


    @Override
    public void onLoadedData(int position, int totalCount) {
        String text = null;
        if (position == 0) {
            text = String.format("未使用(%d)", totalCount);
        } else if (position == 1) {
            text = String.format("已使用(%d)", totalCount);
        } else {
            text = String.format("已过期(%d)", totalCount);
        }
        textViews.get(position).setText(text);
    }

}
