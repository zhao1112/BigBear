package com.yunqin.bearmall.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.util.StatuBarUtils;
import com.yunqin.bearmall.widget.LoadingView;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import cn.example.lamor.Faceplate;

import static android.content.Context.WINDOW_SERVICE;

public abstract class BaseFragment extends Fragment {


    protected LoadingView loadingProgress;
    private Toast mToast;

    private View rootView;

    public abstract int layoutId();

    public abstract void init();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(layoutId(), container, false);
        resetDensity(getActivity(), 750);
        ButterKnife.bind(this, rootView);
        Faceplate.getDefault().performInjectLayoutLayers(this);
        init();
        return rootView;
    }

    public static void resetDensity(Context context, float designWidth) {
        if (context == null)
            return;

        Point size = new Point();
        ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        Resources resources = context.getResources();

        resources.getDisplayMetrics().xdpi = size.x / designWidth * 72f;

        DisplayMetrics metrics = getMetricsOnMiui(context.getResources());
        if (metrics != null)
            metrics.xdpi = size.x / designWidth * 72f;
    }

    //解决MIUI更改框架导致的MIUI7+Android5.1.1上出现的失效问题(以及极少数基于这部分miui去掉art然后置入xposed的手机)
    private static DisplayMetrics getMetricsOnMiui(Resources resources) {
        if ("MiuiResources".equals(resources.getClass().getSimpleName()) || "XResources".equals(resources.getClass().getSimpleName())) {
            try {
                Field field = Resources.class.getDeclaredField("mTmpMetrics");
                field.setAccessible(true);
                return (DisplayMetrics) field.get(resources);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public void immerseStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getActivity().getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //android 把状态栏的图标设置成黑色（白色）
    public void setDarkStatusIcon(boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getActivity().getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * 显示Toast消息
     *
     * @param message
     */
    public void showToast(String message) {
        this.showToast(message, Gravity.CENTER);
    }

    /**
     * 显示Toast消息
     *
     * @param messageResourceID
     */
    public void showToast(int messageResourceID) {
        showToast(getString(messageResourceID), Gravity.CENTER);
    }

    public void showToast(String message, int gravity) {

        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        }
        mToast.setGravity(gravity, 0, 0);
        mToast.setText(message);
        mToast.show();
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
     * @param messageID 默认为“加载中...”
     * @return
     */
    public void showLoading(int messageID) {
        showLoading(null, getString(messageID));
    }

    /**
     * 显示加载中对话框
     *
     * @param message 默认为“加载中...”
     * @return
     */
    public void showLoading(String message) {
        showLoading(null, message);
    }

    /**
     * 显示加载中对话框
     *
     * @param titleID
     * @param messageID 默认为“加载中...”
     * @return
     */
    public void showLoading(int titleID, int messageID) {
        showLoading(getString(titleID), getString(messageID));
    }

    /**
     * 显示加载中对话框
     *
     * @param title
     * @param message 默认为“加载中...”
     * @return
     */
    public void showLoading(String title, String message) {


        if (message == null) {
            message = getResources().getString(R.string.loding_defult);
        }

        if (loadingProgress == null) {
            loadingProgress = LoadingView.createDialog(getActivity());
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
    }

    /**
     * 隐藏加载框
     */
    public void hiddenLoadingView() {
        try {
            if (loadingProgress != null) {
                if (!getActivity().isFinishing()) {
                    loadingProgress.dismiss();
                    loadingProgress = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBar() {
        /**
         * 获取状态栏高度
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }


    /**
     * @param color
     * @param dark  true:标题栏字体黑色 false：标题栏字体白色
     */
    public void setStatusBarColor(int color, boolean dark) {
        StatuBarUtils.setStatusBarDarkTheme(getActivity(), dark);
        StatuBarUtils.setStatusBarColor(getActivity(), getResources().getColor(color));
    }


    public void setTranslucentStatus() {
        //设置状态栏透明
        StatuBarUtils.setTranslucentStatus(getActivity());
    }
}
