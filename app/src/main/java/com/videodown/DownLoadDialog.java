package com.videodown;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;

/**
 * @author Master
 * @create 2018/8/30 16:00
 * 常规Dialog
 */
public class DownLoadDialog {

    private Context iContext;
    private Dialog iDialog;
    private Display display;
    private Window iDialogWindow;

    private TextView iDialogTitle;
    private CompletedView iRingProgressBar;

    //    private Handler iHandler;
//    private static final int UPDATE_PROGRESS = 0x01;
//    private static final int DOWN_LOAD_SUCCESS = 0x02;
//    private static final int DOWN_LOAD_FAIL = 0x03;
//    private static final int FINISH = 0x04;
    private int mCurrentProgress = 0;
//    private final int mTotalProgress = 100;

    /**
     * 构造函数
     *
     * @param iContext
     */
    public DownLoadDialog(final Context iContext) {
        this.iContext = iContext;
        WindowManager windowManager = (WindowManager) iContext.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        iDialog = new Dialog(iContext, R.style.Custom_Dialog_Style);
        iDialogWindow = iDialog.getWindow();
    }

    public void downLoadFinish() {
        iRingProgressBar.setVisibility(View.GONE);
//        statusAnimator(iImageView);
//        iHandler.sendEmptyMessageDelayed(FINISH, 1000);
    }

    /**
     * 初始化View
     *
     * @return
     */
    public DownLoadDialog builder() {
        final View view = LayoutInflater.from(iContext).inflate(R.layout.tv_down_load_dialog, null, false);

        LinearLayout linearLayout = view.findViewById(R.id.ll_down_load_dialog);

        iDialogTitle = view.findViewById(R.id.tv_fresh_down_text);
        iRingProgressBar = view.findViewById(R.id.progress_ring_view);

        iDialog.setContentView(view);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(((int) (display.getWidth() * 0.75)), LinearLayout.LayoutParams.WRAP_CONTENT));
        iDialogWindow.setGravity(Gravity.CENTER);
        return this;
    }

    /**
     * 设置标题
     *
     * @param text
     * @return
     */
    public DownLoadDialog setTitle(String text) {
        if (text == null || "".equals(text)) {
            iDialogTitle.setText("TITLE");
        } else {
            iDialogTitle.setText(text);
        }
        return this;
    }

    /**
     * 设置标题，可配置颜色
     *
     * @param text
     * @param color
     * @return
     */
    public DownLoadDialog setTitle(String text, int color) {
        if (text == null || "".equals(text)) {
            iDialogTitle.setText("TITLE");
        } else {
            iDialogTitle.setText(text);
        }
        iDialogTitle.setTextColor(color);
        return this;
    }




    /**
     * 设置相对位置
     */
    public DownLoadDialog setGravity(int gravity) {
        iDialogWindow.setGravity(gravity);
        return this;
    }

    /**
     * 设置是否可以取消
     */
    public DownLoadDialog setCancelable(boolean cancelable) {
        iDialog.setCancelable(cancelable);
        return this;
    }

    /**
     * show
     */
    public void show() {
        iDialog.show();
        // TODO 开始下载
        mCurrentProgress = 0;
//        iHandler.sendEmptyMessage(UPDATE_PROGRESS);
    }

    /**
     * 隐藏Dialog
     */
    public void dismiss() {
        iDialog.dismiss();
        // TODO 取消下载
//        iHandler.removeMessages(UPDATE_PROGRESS);
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        iRingProgressBar.setProgress(progress);
    }

    /**
     * 背景动画
     *
     * @param bg
     */
    private void statusAnimator(View bg) {
        ObjectAnimator animatorScale = ObjectAnimator.ofFloat(bg, "scaleY", 0.2f, 1f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(bg, "Alpha", 0.0f, 1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorScale, animatorAlpha);
        animatorSet.setDuration(300);
        animatorSet.start();
    }


}
