package com.newversions.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.R;

/**
 * Create By Master
 * On 2019/3/7 14:11
 * 银行卡弹出框自定义View
 */
public class BankCardView extends LinearLayout {

    private Context mContext;
    private ImageView mBankImage;
    private TextView mBankName;
    private CheckBox mCheckBox;

    public BankCardView(Context context) {
        this(context, null);
    }

    public BankCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BankCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.bank_item, this, true);
        mBankImage = findViewById(R.id.img);
        mBankName = findViewById(R.id.bank_name);
        mCheckBox = findViewById(R.id.check_box);
    }

    public void setData(String imgUrl, String bankName) {
        Glide.with(mContext).load(imgUrl).into(mBankImage);
        mBankName.setText(bankName);

    }

    public void setChecked(boolean checked) {
        mCheckBox.setChecked(checked);
    }

    public void isChecked() {
        mCheckBox.isChecked();
    }


}
