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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.BargainRecordBean;

public class BargainShareDialog extends Dialog implements View.OnClickListener{
    private TextView title_tv,bargain_tv;
    private ImageView wechat_friend,wechat_moments;
    private LinearLayout first_share_layout;
    private TextView first_share_tv;
    private TextView cancle;


    public BargainShareDialog(@NonNull Context context, BargainRecordBean.BargainDetails BargainDetails,int countShare) {
        super(context, R.style.ProductDialog);


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bargain_share,null);
        title_tv = view.findViewById(R.id.dialog_share_title_tv);
        bargain_tv = view.findViewById(R.id.dialog_share_bargain_tv);
        wechat_friend = view.findViewById(R.id.dialog_share_wechat_friend);
        wechat_moments = view.findViewById(R.id.dialog_share_qq);
        first_share_layout = view.findViewById(R.id.dialog_share_first_share_layout);
        first_share_tv = view.findViewById(R.id.dialog_share_first_share_tv);
        cancle = view.findViewById(R.id.dialog_share_cancle);

        showDialog(countShare,"","");

        cancle.setOnClickListener(this);

        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager m = ((Activity)context).getWindowManager();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display d = m.getDefaultDisplay();
        lp.width = (int) (d.getWidth() * 1);
        lp.height = (int) (d.getHeight() * 0.35);
        dialogWindow.setAttributes(lp);
    }

    public void showDialog(int count, String bargainedRatio,String bargainPrice){//砍价比例和砍价金额
        if(count == 1){
            title_tv.setText("邀请好友帮砍价");
            bargain_tv.setText("已砍大熊零售价的20%(¥10.55),分享成功后，可以多砍一刀哦");
            first_share_layout.setVisibility(View.GONE);
        }else if(count == 2 || count == 3){
            title_tv.setText("继续分享两个群聊，再多砍 ¥12.00");
            bargain_tv.setText("首次分享成功，多砍了总价的5%(¥11.83)");
            first_share_tv.setText(count-1+"");
            first_share_layout.setVisibility(View.VISIBLE);
        }else if(count >= 4){
            title_tv.setText("砍到50%，获得多砍 ¥4.00 奖励");
            bargain_tv.setText("继续分享，邀请更多好友帮砍");
            first_share_layout.setVisibility(View.GONE);
        }
        show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_share_cancle:
                cancel();
                break;

                default:
                    break;
        }
    }

    OnShowAddressDetailListenter onShowAddressDetailListenter;
    public interface OnShowAddressDetailListenter{
        void onShowAddressDetail(int flag);
    }

    public void setOnShowAddressDetailListenter(OnShowAddressDetailListenter onShowAddressDetailListenter) {
        this.onShowAddressDetailListenter = onShowAddressDetailListenter;
    }
}
