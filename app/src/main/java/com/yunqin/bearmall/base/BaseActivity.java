package com.yunqin.bearmall.base;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.activity.ProductSumActivity;
import com.newversions.tbk.utils.SharedPreferencesUtils;
import com.newversions.tbk.view.SearchDialog;
import com.umeng.analytics.MobclickAgent;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.SuperSearch;
import com.yunqin.bearmall.ui.activity.SuperSearchActivity;
import com.yunqin.bearmall.util.StatusBarUtil;
import com.yunqin.bearmall.widget.LoadingView;
import com.yunqin.bearmall.widget.OpenGoodsDetail;

import org.json.JSONException;

import java.util.HashMap;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    public boolean isDestroyed = false;
    //普通Dialog的Loading
    protected LoadingView loadingProgress;
    protected Toast mToast;

    public abstract int layoutId();

    public abstract void init();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("BaseActivity", "BaseActivity : " + getClass().getSimpleName());
        setContentView(layoutId());

        ButterKnife.bind(this);
        StatusBarUtil.setImmersiveStatusBar(this, true);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        searchDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private String content;
    private SearchDialog mSearchDialog = null;

    private void searchDialog() {
        try {
            Boolean bFirst = (Boolean) SharedPreferencesUtils.getParam(this, Constants.isFirstOpen, true);
            if (BearMallAplication.getInstance().getUser() == null) {
                return;
            }
            if (loadingProgress != null && loadingProgress.isShowing()) {
                return;
            }
            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData data = cm.getPrimaryClip();
            if (data == null || data.getItemCount() == 0) {
                return;
            }
            ClipData.Item item = data.getItemAt(0);
            if (item == null || item.getText() == null || "null".equals(item.getText().toString())) {
                return;
            }
            content = item.getText().toString();
            Log.d("TAG", "searchDialog:get--- " + content);
            if (!TextUtils.isEmpty(content)) {
                if (mSearchDialog == null) {
                    mSearchDialog = new SearchDialog(this, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                cm.setPrimaryClip(cm.getPrimaryClip());
                                cm.setText(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mSearchDialog.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                cm.setPrimaryClip(cm.getPrimaryClip());
                                cm.setText(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mSearchDialog.dismiss();
                            HashMap<String, String> map = new HashMap<>();
                            map.put("content", content);
                            Log.i("jsonObject", content);
                            RetrofitApi.request(BaseActivity.this, RetrofitApi.createApi(Api.class).findCommodityIdByTpwd(map),
                                    new RetrofitApi.IResponseListener() {
                                        @Override
                                        public void onSuccess(String data) throws JSONException {
                                            SuperSearch superSearch = new Gson().fromJson(data, SuperSearch.class);
                                            Log.i("onSuccess", data);
                                            if (superSearch.getCode() == 1) {
                                                if (superSearch.getData().size() <= 0) {
                                                    OpenGoodsDetail.showDialog(BaseActivity.this);
                                                    return;
                                                }
                                                if (superSearch.getData().size() == 1) {
                                                    GoodsDetailActivity.startGoodsDetailActivity(BaseActivity.this,
                                                            superSearch.getData().get(0).getTao_id(), Constants.GOODS_TYPE_TBK_SEARCH);
                                                } else {
                                                    SuperSearchActivity.openSuperSearchActivity(BaseActivity.this, superSearch, content);
                                                }
                                            } else if (superSearch.getCode() == 2) {
                                                OpenGoodsDetail.showDialog(BaseActivity.this);
                                            } else {
                                                ProductSumActivity.startProductSumActivity(BaseActivity.this, content, 8, content);
                                            }
                                        }

                                        @Override
                                        public void onNotNetWork() {
                                            Log.i("jsonObject", "---------");
                                        }

                                        @Override
                                        public void onFail(Throwable e) {
                                            Log.i("jsonObject", "---------");
                                        }
                                    });
                        }
                    });
                }
                if (!mSearchDialog.isShowing()) {
                    mSearchDialog.setMessage(content);
                    mSearchDialog.show();
                } else {
                    mSearchDialog.setMessage(content);
                }
                cm.setText(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        showLoading(null, message);
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

}
