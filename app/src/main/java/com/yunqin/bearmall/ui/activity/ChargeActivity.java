package com.yunqin.bearmall.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newversions.CardListWebActivity;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.CustomRotateAnim;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.fragment.ChargeFragment;
import com.yunqin.bearmall.ui.fragment.LiuliangFragment;
import com.yunqin.bearmall.util.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ChargeActivity extends ContainFragmentActivity implements ChargeFragment.OnGetChargeDataListener {

    @BindView(R.id.phone_num)
    EditText phoneNumView;
    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.operator)
    TextView operatorView;

    @BindView(R.id.left_line)
    View leftLine;
    @BindView(R.id.right_line)
    View rightLine;
    private ChargeFragment chargeFragment;

    private LiuliangFragment liuliangFragment;

    @Override
    public int layoutId() {
        return R.layout.activity_charge;
    }

    @Override
    public void init() {
        super.init();
        titleView.setText("充值中心");

        chargeFragment = ChargeFragment.instance();
        liuliangFragment = LiuliangFragment.instance();
        replaceFragment(R.id.fragment_container, chargeFragment);
        leftLine.setVisibility(View.VISIBLE);
        rightLine.setVisibility(View.GONE);
        chargeFragment.setOnGetChargeDataListener(this);

        UserInfo userInfo = BearMallAplication.getInstance().getUser();
        if (userInfo != null) {
            UserInfo.DataBean dataBean = userInfo.getData();
            String mobile = dataBean.getMember().getMobile();
            phoneNumView.setText(mobile);
            chargeFragment.getPhoenNum(mobile);
            phoneNumView.setSelection(phoneNumView.length());
        }
        showAnimation();

        phoneNumView.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (chargeFragment != null) {
            chargeFragment.loadData();
        }
    }

    @BindView(R.id.red_package_layout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.red_package_img)
    ImageView imageView;

    /**
     * 设置动画
     */
    private void showAnimation() {
        // 获取自定义动画实例
        CustomRotateAnim rotateAnim = CustomRotateAnim.getCustomRotateAnim();
        // 一次动画执行1秒
        rotateAnim.setDuration(1000);
        // 设置为循环播放
        rotateAnim.setRepeatCount(-1);
        // 设置为匀速
        rotateAnim.setInterpolator(new LinearInterpolator());
        // 开始播放动画
        imageView.startAnimation(rotateAnim);
    }

    @OnClick({R.id.toolbar_back, R.id.telephone_charge, R.id.telephone_flow,
            R.id.red_package_close,
            R.id.red_package_img
    })
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.red_package_close:
                constraintLayout.setVisibility(View.GONE);
                break;
            case R.id.red_package_img:
                // TODO 待定 URL
                CardListWebActivity.startActivity(ChargeActivity.this, AdConstants.STRING_CHARGE_ACTIVITY, "");
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.telephone_charge:
                leftLine.setVisibility(View.VISIBLE);
                rightLine.setVisibility(View.GONE);
                replaceFragment(R.id.fragment_container, chargeFragment);
                break;
            case R.id.telephone_flow:
                leftLine.setVisibility(View.GONE);
                rightLine.setVisibility(View.VISIBLE);
                replaceFragment(R.id.fragment_container, liuliangFragment);
                break;
        }

    }

    @Override
    public void onGetData(String mobile, int carrierType) {
        phoneNumView.setText(mobile);
        String type;
        switch (carrierType) {
            case 0:
            default:
                type = "中国移动";
                break;
            case 1:
                type = "中国电信";
                break;
            case 2:
                type = "中国联通";
                break;
        }
        operatorView.setText(type);
        phoneNumView.setSelection(phoneNumView.length());
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.e("CharSequence", "afterTextChanged: " + editable.toString());
            if (chargeFragment != null) {
                chargeFragment.getPhoenNum(editable.toString());
            }
            if (editable.toString().length() == 11) {
                operatorView.setText(CommonUtils.validateMobile(editable.toString()));
                operatorView.setTextColor(getResources().getColor(R.color.product_brand_name_color));
            }else {
                operatorView.setText("请输入正确的手机号");
                operatorView.setTextColor(getResources().getColor(R.color.red));
            }
        }
    };
}
