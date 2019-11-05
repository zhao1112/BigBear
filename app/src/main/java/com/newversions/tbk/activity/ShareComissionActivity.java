package com.newversions.tbk.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.newversions.tbk.Constants;
import com.newversions.tbk.entity.GoodDetailEntity;
import com.newversions.tbk.entity.ShareGoodsEntity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.ConstantScUtil;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * 分享赚佣金
 */
public class ShareComissionActivity extends BaseActivity implements PlatformActionListener {

    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.et_share_msg)
    EditText etShareMsg;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    private String taoToken;
    private String qCodeUrl;
    private GoodDetailEntity.GoodDetailBean goodDetailBean;
    Platform platform = null;

    @Override
    public int layoutId() {
        return R.layout.activity_share_comission;
    }

    @Override
    public void init() {
        goodDetailBean = (GoodDetailEntity.GoodDetailBean) getIntent().getSerializableExtra(Constants.INTENT_KEY_DATA);
        Map<String, String> map = new HashMap<>();
        map.put("goodsId", goodDetailBean.getItemId());
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getShareMsg(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                ShareGoodsEntity shareGoodsEntity = new Gson().fromJson(data, ShareGoodsEntity.class);
                if (shareGoodsEntity.getCode() == 2) {
                    // TODO: 2019/8/15 0015 shouquan
                    Intent intent = new Intent(ShareComissionActivity.this, WebActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_URL, shareGoodsEntity.getTaoToken());
                    intent.putExtra(Constants.INTENT_KEY_TITLE, "淘宝授权");
                    startActivity(intent);
                    finish();
                    return;
                }
                qCodeUrl = shareGoodsEntity.getTaoQcodeUrl();
                taoToken = shareGoodsEntity.getTaoToken();
                hiddenLoadingView();
                etShareMsg.setText(Html.fromHtml("   <b>" + goodDetailBean.getName() + "</b><br>" +
                        "【原价】  " + goodDetailBean.getPrice() + "元" + "<br>" +
                        "【券后价】  " + goodDetailBean.getDiscountPrice() + "元<br>" +
                        "【使用大熊商城再省】  " + goodDetailBean.getCommision() + "<br><br><br>" +
                        "复制这条信息" + "，{" + taoToken + "}，打开【手机tao宝】即可查看<br>"));
                etShareMsg.setSelection(etShareMsg.getText().length());
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });


        tvCopy.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (BearMallAplication.getInstance().getUser() != null) {
                cm.setText(etShareMsg.getText().toString() + "");
                Toast.makeText(ShareComissionActivity.this, "复制成功", Toast.LENGTH_LONG).show();
            }
        });
        toolbarBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) (displaysMetrics.density * leftDip);
        int right = (int)
                (displaysMetrics.density * rightDip);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @OnClick({R.id.lin_share_weixin, R.id.lin_share_pengyouquan, R.id.lin_share_qq, R.id.lin_share_qq_qzone, R.id.lin_share_weibo})
    public void onViewClicked(View btn) {

        showLoading();
        View view = getLayoutInflater().inflate(R.layout.view_bitmap_maker, null, false);
        ImageView im_content = view.findViewById(R.id.iv_imge);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_yuanjia = view.findViewById(R.id.tv_yuanjia);
        TextView tv_quan = view.findViewById(R.id.tv_quan);
        TextView tv_quanhoujia = view.findViewById(R.id.tv_quanhoujia);
        ImageView im_erweima = view.findViewById(R.id.tv_erweima);
        tv_title.setText(goodDetailBean.getName());
        tv_yuanjia.setText("原价￥：" + goodDetailBean.getPrice());
        tv_quanhoujia.setText("￥：" + goodDetailBean.getDiscountPrice());
        tv_quan.setText(goodDetailBean.getCouponAmount() + "元券");
        Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(goodDetailBean.getOutIcon()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                im_content.setImageDrawable(resource);
                Glide.with(ShareComissionActivity.this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(qCodeUrl).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        hiddenLoadingView();
                        im_erweima.setImageDrawable(resource);
                        Bitmap bitmap = createBitmap3(view);
                        saveBmp2Gallery(bitmap, System.currentTimeMillis() + "", ShareComissionActivity.this);
                        Platform.ShareParams sp = new Platform.ShareParams();
                        sp.setImageData(bitmap);
                        sp.setShareType(Platform.SHARE_IMAGE);
                        switch (btn.getId()) {
                            case R.id.lin_share_weixin:
                                platform = ShareSDK.getPlatform(Wechat.NAME);
                                break;
                            case R.id.lin_share_pengyouquan:
                                platform = ShareSDK.getPlatform(WechatMoments.NAME);
                                break;
                            case R.id.lin_share_qq:
                                platform = ShareSDK.getPlatform(QQ.NAME);
                                break;
                            case R.id.lin_share_qq_qzone:
                                platform = ShareSDK.getPlatform(QZone.NAME);
                                break;
                            case R.id.lin_share_weibo:
                                platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                                break;
                        }
                        if (platform != null)
                            platform.share(sp);
                        platform.setPlatformActionListener(ShareComissionActivity.this);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        hiddenLoadingView();
                    }
                });

            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                hiddenLoadingView();
            }
        });
    }

    private void createView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public Bitmap createBitmap3(View v) {

        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bitMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitMap);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);
        return bitMap;
    }

    public static void saveBmp2Gallery(Bitmap bmp, String picName, Context mContext) {
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (goodDetailBean != null) {
            ConstantScUtil.searchShareType(goodDetailBean.getId() + "", goodDetailBean.getName(), goodDetailBean.getSellerName(),
                    goodDetailBean.getCouponAmount() + "", goodDetailBean + "", goodDetailBean.getPrice() + "",
                    goodDetailBean.getDiscountPrice() + "", platform.getName(), "true");
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        if (goodDetailBean != null) {
            ConstantScUtil.searchShareType(goodDetailBean.getId() + "", goodDetailBean.getName(), goodDetailBean.getSellerName(),
                    goodDetailBean.getCouponAmount() + "", goodDetailBean + "", goodDetailBean.getPrice() + "",
                    goodDetailBean.getDiscountPrice() + "", platform.getName(), "false");
        }
    }

    @Override
    public void onCancel(Platform platform, int i) {
        if (goodDetailBean != null) {
            ConstantScUtil.searchShareType(goodDetailBean.getId() + "", goodDetailBean.getName(), goodDetailBean.getSellerName(),
                    goodDetailBean.getCouponAmount() + "", goodDetailBean + "", goodDetailBean.getPrice() + "",
                    goodDetailBean.getDiscountPrice() + "", platform.getName(), "false");
        }
    }
}
