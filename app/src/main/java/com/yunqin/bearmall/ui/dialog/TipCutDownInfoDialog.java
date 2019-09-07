package com.yunqin.bearmall.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.inter.FreeGetBtnCallBack;

/**
 * @author AYWang
 * @create 2018/8/28
 * @Describe
 */
public class TipCutDownInfoDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private Button faqikanjia_btn;
    private RelativeLayout dialog_cancel_layout;

    private FreeGetBtnCallBack freeGetBtnCallBack;

    private Animation mAnimation;

    TextView cut_down_how;
    ImageView head_img;


    public TipCutDownInfoDialog(@NonNull Context context, FreeGetBtnCallBack freeGetBtnCallBack) {
        super(context, R.style.ProductDialog);

        mContext = context;
        this.freeGetBtnCallBack = freeGetBtnCallBack;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cut_down_how_money, null);
        faqikanjia_btn = view.findViewById(R.id.faqikanjia_btn);
        dialog_cancel_layout = view.findViewById(R.id.dialog_cancel_layout);
        cut_down_how = view.findViewById(R.id.cut_down_how);
        head_img = view.findViewById(R.id.head_img);
        faqikanjia_btn.setOnClickListener(this);
        dialog_cancel_layout.setOnClickListener(this);


        mAnimation = AnimationUtils.loadAnimation(context, R.anim.beat_anim);
        faqikanjia_btn.setAnimation(mAnimation);
        mAnimation.start();


        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager m = ((Activity) context).getWindowManager();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display d = m.getDefaultDisplay();
        lp.width = (int) (d.getWidth() * 0.8);
        dialogWindow.setAttributes(lp);
    }


    public void show(String v1, String v2, String v3) {
        cut_down_how.setText(v1 + ",砍了" + v2 + "元");
        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(v3).into(head_img);
        show();
    }


    @Override
    public void show() {
        super.show();
        mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.beat_anim);
        faqikanjia_btn.setAnimation(mAnimation);
        mAnimation.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dialog_cancel_layout:
                cancel();
                break;

            case R.id.faqikanjia_btn:
                freeGetBtnCallBack.clickThisBtn();
                cancel();
                break;

            default:
                break;
        }
    }


}
