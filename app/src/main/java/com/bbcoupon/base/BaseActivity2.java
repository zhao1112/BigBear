package com.bbcoupon.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bbcoupon.widget.SuperSearch;
import com.umeng.analytics.MobclickAgent;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.util.StatuBarUtils;
import com.yunqin.bearmall.widget.LoadingView;

import butterknife.ButterKnife;

public abstract class BaseActivity2 extends AppCompatActivity {

    public boolean isDestroyed = false;
    protected LoadingView loadingProgress;
    protected Toast mToast;

    public abstract int layoutId();

    public abstract void init();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        ButterKnife.bind(this);
        init();
        Log.i("BaseActivity", "BaseActivity : " + getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (!"SplashActivity".equals(getClass().getSimpleName())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SuperSearch.searchDialog(BaseActivity2.this,loadingProgress);
                }
            },500);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //设置沉浸式
    @TargetApi(19)
    public void setbarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(this.getResources().getColor(R.color.color_my_1));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    //设置fragment布局沉浸式
    public void immerseStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //android 把状态栏的图标设置成黑色（白色）
    public void changStatusIconCollor(boolean setDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (setDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * 显示加载中对话框
     *
     * @return
     */
    public void showLoading() {
        showLoading(getResources().getString(R.string.loding_defult));
    }

    /**
     * 显示加载中对话框
     *
     * @param message default : "加载中..."
     * @return
     */
    public void showLoading(String message) {
        try {
            showLoading(null, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示加载中对话框
     *
     * @param title
     * @param message default : "加载中..."
     * @return
     */
    public void showLoading(String title, String message) {
        if (message == null) {
            message = getResources().getString(R.string.loding_defult);
        }

        if (loadingProgress == null) {
            loadingProgress = LoadingView.createDialog(this);
        }
        if (title == null) {
            loadingProgress.setTitle("");
        } else {
            loadingProgress.setTitle(title);
        }
        loadingProgress.setMessage(message);
        loadingProgress.show();
        loadingProgress.setCancelable(true);// 设置进度条是否可以按退回键取消

        // 设置点击进度对话框外的区域对话框不消失
        loadingProgress.setCanceledOnTouchOutside(false);

    }

    /**
     * 隐藏普通加载框
     */
    public void hiddenLoadingView() {
        if (loadingProgress != null) {
            loadingProgress.dismiss();
            loadingProgress = null;
        }
    }

    /**
     * 显示Toast消息
     *
     * @param message
     */
    public void showToast(String message) {
        try {
            this.showToast(message, Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示Toast消息
     *
     * @param messageResourceID
     */
    public void showToast(int messageResourceID) {
        try {
            showToast(getString(messageResourceID), Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(String message, int gravity) {
        try {
            if (isDestroyed) {
                return;
            }

            hiddenKeyboard();

            if (mToast == null) {
                mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
            }
            mToast.setGravity(gravity, 0, 0);
            mToast.setText(message);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hiddenKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView()
                        .getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }


    /**
     * @param color
     * @param dark  true:标题栏字体黑色 false：标题栏字体白色
     */
    public void setStatusBarColor(int color, boolean dark) {
        StatuBarUtils.setStatusBarDarkTheme(this, dark);
        StatuBarUtils.setStatusBarColor(this, getResources().getColor(color));
    }


    public void setTranslucentStatus() {
        //设置状态栏透明
        StatuBarUtils.setTranslucentStatus(this);
    }


}
