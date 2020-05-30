package com.yunqin.bearmall.base;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.bean.BaseInfor;
import com.bbcoupon.ui.bean.ContentInfor;
import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.bean.SearchInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.bbcoupon.util.CopyTextUtil;
import com.bbcoupon.util.WindowUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.activity.ProductSumActivity2;
import com.newversions.tbk.utils.StringUtils;
import com.newversions.tbk.view.SearchDialog;
import com.umeng.analytics.MobclickAgent;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.AuntTao;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StatuBarUtils;
import com.yunqin.bearmall.widget.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    public boolean isDestroyed = false;
    //普通Dialog的Loading
    protected LoadingView loadingProgress;
    protected Toast mToast;
    private ClipboardManager clipboardManager;
    private static final String KEY = "k";
    private static final String SPLIT = ",";

    public abstract int layoutId();

    public abstract void init();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        ButterKnife.bind(this);
//        StatusBarUtil.setImmersiveStatusBar(this, true);
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
                    searchDialog();
                }
            }, 500);
        }

    }

    private void searchDialog() {
        if (BearMallAplication.getInstance().getUser() == null) {
            return;
        }
        if (BearMallAplication.isFirst && BearMallAplication.isFirst2) {
            Log.e("BearMallAplication", "searchDialog: " + BearMallAplication.isFirst + "------" + BearMallAplication.isFirst2);
            return;
        }
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = clipboardManager.getPrimaryClip();
        if (data == null || data.getItemCount() == 0) {
            return;
        }
        ClipData.Item item = data.getItemAt(0);
        if (item == null || item.getText() == null || "null".equals(item.getText().toString())) {
            Log.e("BearMallAplication", "searchDialog: 2");
            return;
        }
        content = item.getText().toString();
        if (CopyTextUtil.isSameContent(this, content)) {
            return;
        }
        if (content.trim().isEmpty()) {
            return;
        }
        if (!TextUtils.isEmpty(content)) {
            Map<String, String> map = new HashMap<>();
            map.put("content", content);
            map.put("access_token", ConstantUtil.getAsetToken());
            RetrofitApi.request(BaseActivity.this, RetrofitApi.createApi(Api.class).onSuperSearch(map),
                    new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        JSONObject object = new JSONObject(data);
                        int type = object.optInt("type");
                        if (type == 1) {
                            Log.e("onSuperSearch", data);
                            SearchInfor searchInfor = new Gson().fromJson(data, SearchInfor.class);
                            if (searchInfor == null) {
                                WindowUtils.dismissBrightness(BaseActivity.this);
                                return;
                            }
                            WindowUtils.dismissBrightness(BaseActivity.this);
                            PopupWindow popupWindow = WindowUtils.ShowVirtual(BaseActivity.this, R.layout.item_popup_search,
                                    R.style.bottom_animation, 2);
                            popupWindow.getContentView().findViewById(R.id.goods_id).setVisibility(View.VISIBLE);
                            ImageView image = popupWindow.getContentView().findViewById(R.id.image);
                            TextView content = popupWindow.getContentView().findViewById(R.id.content);
                            TextView commision = popupWindow.getContentView().findViewById(R.id.commision);
                            TextView price = popupWindow.getContentView().findViewById(R.id.price);
                            Glide.with(BaseActivity.this)
                                    .load(searchInfor.getData().getImage())
                                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                                    .into(image);
                            content.setText(StringUtils.addImageLabel(BaseActivity.this, "1".equals(searchInfor.getData().getTmall()) ?
                                    R.mipmap.icon_tmall : R.mipmap.icon_taobao1, searchInfor.getData().getTitle()));
                            commision.setText("收益¥" + searchInfor.getData().getTkfee3());
                            price.setText("" + searchInfor.getData().getDiscountPrice());
                            popupWindow.getContentView().findViewById(R.id.goodsid).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                    clipboardManager.setText(null);
                                    BearMallAplication.isFirst = true;
                                    BearMallAplication.isFirst2 = true;
                                    GoodsDetailActivity.startGoodsDetailActivity(BaseActivity.this,
                                            searchInfor.getData().getItemId(), Constants.GOODS_TYPE_TBK_SEARCH,
                                            Constants.COMMISSION_TYPE_THREE);
                                    WindowUtils.dismissBrightness(BaseActivity.this);
                                }
                            });
                            popupWindow.getContentView().findViewById(R.id.search_buy).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                    clipboardManager.setText(null);
                                    BearMallAplication.isFirst = true;
                                    BearMallAplication.isFirst2 = true;
                                    if (searchInfor.getData().getUrl() != null) {
                                        ArouseTaoBao arouseTaoBao = new ArouseTaoBao(BaseActivity.this);
                                        if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
                                            arouseTaoBao.openTaoBao(searchInfor.getData().getUrl());
                                        } else {
                                            showToast("请先下载淘宝");
                                            hiddenLoadingView();
                                        }
                                    } else {
                                        AuntTao auntTao = new AuntTao();
                                        auntTao.setContext(BaseActivity.this);
                                        auntTao.AuntTabo();
                                    }
                                    WindowUtils.dismissBrightness(BaseActivity.this);
                                }
                            });
                            popupWindow.getContentView().findViewById(R.id.sea_close).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                    clipboardManager.setText(null);
                                    BearMallAplication.isFirst = true;
                                    BearMallAplication.isFirst2 = true;
                                    WindowUtils.dismissBrightness(BaseActivity.this);
                                }
                            });
                        }
                        if (type == 2) {
                            Log.e("onSuperSearch", data);
                            ContentInfor requestInfor = new Gson().fromJson(data, ContentInfor.class);
                            if (requestInfor != null && requestInfor.getData() != null) {
                                WindowUtils.dismissBrightness(BaseActivity.this);
                                PopupWindow popupWindow = WindowUtils.ShowVirtual(BaseActivity.this, R.layout.item_popup_search,
                                        R.style.bottom_animation, 2);
                                popupWindow.getContentView().findViewById(R.id.search_conten).setVisibility(View.VISIBLE);
                                TextView sea_conten = popupWindow.getContentView().findViewById(R.id.sea_conten);
                                sea_conten.setText(requestInfor.getData());
                                popupWindow.getContentView().findViewById(R.id.cleosn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                        clipboardManager.setText(null);
                                        BearMallAplication.isFirst = true;
                                        BearMallAplication.isFirst2 = true;
                                        WindowUtils.dismissBrightness(BaseActivity.this);
                                    }
                                });
                                popupWindow.getContentView().findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                        clipboardManager.setText(null);
                                        BearMallAplication.isFirst = true;
                                        BearMallAplication.isFirst2 = true;
                                        ProductSumActivity2.startProductSumActivity2(BaseActivity.this, requestInfor.getData(), 8,
                                                requestInfor.getData()
                                                , "1");
                                        WindowUtils.dismissBrightness(BaseActivity.this);
                                    }
                                });
                                popupWindow.getContentView().findViewById(R.id.sea_close).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                        clipboardManager.setText(null);
                                        BearMallAplication.isFirst = true;
                                        BearMallAplication.isFirst2 = true;
                                        WindowUtils.dismissBrightness(BaseActivity.this);
                                    }
                                });
                            } else {
                                WindowUtils.dismissBrightness(BaseActivity.this);
                            }
                        }
                        if (type == 3) {
                            Log.e("onSuperSearch", data);
                            BaseInfor baseInfor = new Gson().fromJson(data, BaseInfor.class);
                            if (baseInfor != null && baseInfor.getData() != null) {
                                WindowUtils.dismissBrightness(BaseActivity.this);
                                PopupWindow popupWindow = WindowUtils.ShowVirtual(BaseActivity.this, R.layout.item_popup_search,
                                        R.style.bottom_animation, 2);
                                popupWindow.getContentView().findViewById(R.id.search_conten).setVisibility(View.VISIBLE);
                                TextView sea_conten = popupWindow.getContentView().findViewById(R.id.sea_conten);
                                sea_conten.setText(baseInfor.getData());
                                popupWindow.getContentView().findViewById(R.id.cleosn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                        clipboardManager.setText(null);
                                        BearMallAplication.isFirst = true;
                                        BearMallAplication.isFirst2 = true;
                                        WindowUtils.dismissBrightness(BaseActivity.this);
                                    }
                                });
                                popupWindow.getContentView().findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                        clipboardManager.setText(null);
                                        BearMallAplication.isFirst = true;
                                        BearMallAplication.isFirst2 = true;
                                        assemblyData(baseInfor.getData());
                                        ProductSumActivity2.startProductSumActivity2(BaseActivity.this, baseInfor.getData(), 8,
                                                baseInfor.getData()
                                                , "2");
                                        WindowUtils.dismissBrightness(BaseActivity.this);
                                    }
                                });
                                popupWindow.getContentView().findViewById(R.id.sea_close).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clipboardManager.setPrimaryClip(clipboardManager.getPrimaryClip());
                                        clipboardManager.setText(null);
                                        BearMallAplication.isFirst = true;
                                        BearMallAplication.isFirst2 = true;
                                        WindowUtils.dismissBrightness(BaseActivity.this);
                                    }
                                });
                            } else {
                                WindowUtils.dismissBrightness(BaseActivity.this);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNotNetWork() {
                    Log.e("onSuperSearch", "onNotNetWork");
                }

                @Override
                public void onFail(Throwable e) {
                    Log.e("onSuperSearch", e.getMessage());
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private String content;
    private SearchDialog mSearchDialog = null;


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

    private void assemblyData(String data) {
        String content = (String) SharedPreferencesHelper.get(this, KEY, "");
        if ("".equals(content)) {
            SharedPreferencesHelper.put(this, KEY, data);
        } else if (content.contains(data)) {
            SharedPreferencesHelper.put(this, KEY, data + SPLIT + removeData(data));
        } else {
            SharedPreferencesHelper.put(this, KEY, data + SPLIT + content);
        }
    }

    private String removeData(String str) {
        String currData = "";
        String content = (String) SharedPreferencesHelper.get(this, KEY, "");
        if (content.contains(str + SPLIT)) {
            currData = content.replace(str + SPLIT, "");
        } else {
            currData = content.replace(str, "");
        }
        SharedPreferencesHelper.put(this, KEY, currData);
        return currData;
    }
}
