package com.bbcoupon.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.bean.ImageSelectInfor;
import com.bbcoupon.ui.adapter.ChoiceAdapter;
import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.bbcoupon.util.CopyTextUtil;
import com.bbcoupon.util.DownloadUtil;
import com.bbcoupon.util.JurisdictionUtil;
import com.bbcoupon.util.ShareUtils;
import com.bbcoupon.util.UploadImageUtil;
import com.bbcoupon.util.WindowUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.newversions.tbk.Constants;
import com.newversions.tbk.entity.GoodDetailEntity;
import com.newversions.tbk.entity.ShareGoodsEntity;
import com.newversions.tbk.utils.StringUtils;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.BCMessageActivity;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.widget.DownLoadImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/6
 */
public class ChoiceShareActivity extends BaseActivity implements RequestContract.RequestView {

    @BindView(R.id.chouce_recycler)
    RecyclerView mChouceRecycler;
    @BindView(R.id.choices_select)
    CheckBox mChoicesSelect;
    @BindView(R.id.choose_image)
    TextView mChooseImage;
    @BindView(R.id.whole_image)
    TextView mWholeImage;
    @BindView(R.id.profit)
    TextView mProfit;
    @BindView(R.id.recommend_conten)
    EditText mRecommendConten;
    @BindView(R.id.goods_conten)
    TextView mGoodsConten;
    @BindView(R.id.c_one)
    CheckBox c_one;
    @BindView(R.id.c_two)
    CheckBox c_two;
    @BindView(R.id.c_three)
    CheckBox c_three;
    @BindView(R.id.c_four)
    CheckBox c_four;


    private ChoiceAdapter choiceAdapter;
    public List<ImageSelectInfor.ImageBean> list;
    private GoodDetailEntity.GoodDetailBean goodDetailBean;
    private RequestPresenter presenter;
    private String profit;
    private List<String> mStrings = new ArrayList<>();
    private Map<String, String> map;
    private String taoTokens;
    private String shareReason;
    private ShareGoodsEntity entity;

    @Override
    public int layoutId() {
        return R.layout.activity_choiceshare;
    }

    @Override
    public void init() {

        goodDetailBean = (GoodDetailEntity.GoodDetailBean) getIntent().getSerializableExtra(Constants.INTENT_KEY_DATA);
        entity = (ShareGoodsEntity) getIntent().getSerializableExtra("GOODSENTITY");
        profit = getIntent().getStringExtra("Profit");

        presenter = new RequestPresenter();
        presenter.setRelation(this);
        showLoading();
        if (goodDetailBean != null && entity != null) {
            addimageList(goodDetailBean.getImages(), entity.getTaoQcodeUrl());
            if (!TextUtils.isEmpty(entity.getShareReason())) {
                shareReason = entity.getShareReason();
            }
            taoTokens = entity.getTaoToken();
            setTwoConten(entity.getTaoToken());
            setOneConten(entity.getShareReason());
        } else {
            hiddenLoadingView();
            return;
        }


        map = new HashMap<>();
        map.put("type", "1");
        map.put("content", goodDetailBean.getItemId());


        ImageSelectInfor imageSelectInfor = new ImageSelectInfor();
        List<ImageSelectInfor.ImageBean> beanList = new ArrayList<>();
        for (int i = 0; i < goodDetailBean.getImages().size(); i++) {
            ImageSelectInfor.ImageBean imageBean = new ImageSelectInfor.ImageBean();
            if (i == 0) {
                Log.e("handleMessage", goodDetailBean.getImages().get(i));
                imageBean.setImage(goodDetailBean.getImages().get(i));
                imageBean.setSelect(true);
            } else {
                imageBean.setImage(goodDetailBean.getImages().get(i));
                imageBean.setSelect(false);
            }
            beanList.add(imageBean);
        }
        imageSelectInfor.setImageBean(beanList);

        mProfit.setText("您的预估收益为：" + profit + "元");
        mWholeImage.setText("/" + beanList.size());
        mChouceRecycler.setLayoutManager(new LinearLayoutManager(ChoiceShareActivity.this, RecyclerView.HORIZONTAL, false));
        choiceAdapter = new ChoiceAdapter(ChoiceShareActivity.this, beanList);
        mChouceRecycler.setAdapter(choiceAdapter);
        choiceAdapter.setOnWholeState(new ChoiceAdapter.OnWholeState() {
            @Override
            public void onWholeState(boolean isSelect) {
                mChoicesSelect.setChecked(isSelect);
            }

            @Override
            public void onSelection(List<ImageSelectInfor.ImageBean> mList) {
                list = mList;
                int position = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        position++;
                    }
                }
                mChooseImage.setText(position + "");
            }
        });
        mChooseImage.setText("1");
    }

    public void addimageList(List<String> images, String url) {
        mStrings = images;
        View view = getLayoutInflater().inflate(R.layout.view_bitmap_search, null, false);
        TextView b_title = view.findViewById(R.id.b_title);
        TextView b_price = view.findViewById(R.id.b_price);
        TextView b_price_original = view.findViewById(R.id.b_price_original);
        TextView b_volume = view.findViewById(R.id.b_volume);
        ImageView b_image = view.findViewById(R.id.b_image);
        TextView b_price2 = view.findViewById(R.id.b_price2);
        ImageView b_image2 = view.findViewById(R.id.b_image2);
        TextView comm = view.findViewById(R.id.comm);

        b_title.setText(StringUtils.addImageLabel(ChoiceShareActivity.this, goodDetailBean.getTmall() == 1 ? R.mipmap.icon_tmall :
                R.mipmap.icon_taobao1, goodDetailBean.getName()));
        String[] split = CommonUtils.doubleToString(goodDetailBean.getDiscountPrice()).split("\\.");
        String str2 = split[0] + ".<small>" + split[1] + "</small>";
        b_price.setText(Html.fromHtml(str2));
        b_price_original.setText("¥" + CommonUtils.doubleToString(goodDetailBean.getPrice()));
        b_price_original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        b_volume.setText("销量" + goodDetailBean.getSellNum());
        b_price2.setText(goodDetailBean.getDiscountPrice() + "");

        comm.setText(ConstantUtil.money + goodDetailBean.getCouponAmount());

        Glide.with(ChoiceShareActivity.this)
                .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product))
                .load(goodDetailBean.getOutIcon())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        b_image.setImageDrawable(resource);
                        Glide.with(ChoiceShareActivity.this)
                                .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product))
                                .load(url)
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource,
                                                                @Nullable Transition<? super Drawable> transition) {
                                        b_image2.setImageDrawable(resource);
                                        Bitmap bitmap3 = createBitmap3(view, ChoiceShareActivity.this);
                                        saveBitmap(bitmap3, "search");
                                        try {
                                            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() +
                                                    "/InvitationPoster/search" + ".jpg"));
                                            onUpLoadImage(uri);
                                            choiceAdapter.setImage(bitmap3);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
    }


    @OnClick({R.id.toolbar_back, R.id.choices_select, R.id.recommend_copy, R.id.goodes_copy, R.id.wx_share, R.id.moments_share,
            R.id.qq_share, R.id.qq_moments_share, R.id.rule, R.id.view_select, R.id.view_one, R.id.view_two, R.id.view_three,
            R.id.dwon_share, R.id.c_one, R.id.c_three, R.id.c_two, R.id.c_four, R.id.view_four, R.id.g_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.choices_select:
                choiceAdapter.wholeState(mChoicesSelect.isChecked());
                break;
            case R.id.view_select:
                mChoicesSelect.setChecked(!mChoicesSelect.isChecked());
                choiceAdapter.wholeState(mChoicesSelect.isChecked());
                break;
            case R.id.recommend_copy:
                if (!TextUtils.isEmpty(mRecommendConten.getText().toString())) {
                    CopyTextUtil.CopyText(ChoiceShareActivity.this, mRecommendConten.getText().toString());
                    showToast("复制成功");
                }
                break;
            case R.id.goodes_copy:
                if (!TextUtils.isEmpty(mGoodsConten.getText().toString())) {
                    CopyTextUtil.CopyText(ChoiceShareActivity.this, mGoodsConten.getText().toString());
                    showToast("复制成功");
                }
                break;
            case R.id.wx_share:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    builder.detectFileUriExposure();
                }
                mStrings.clear();
                try {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isSelect()) {
                            mStrings.add(list.get(i).getImage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CopyTextUtil.CopyText(ChoiceShareActivity.this, mRecommendConten.getText().toString());
                JurisdictionUtil.Jurisdiction(ChoiceShareActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    if (ShareUtils.isWXClientAvailable(ChoiceShareActivity.this)) {
                        if (mStrings.size() > 1) {
                            String[] searImage = new String[mStrings.size()];
                            for (int i = 0; i < mStrings.size(); i++) {
                                searImage[i] = mStrings.get(i);
                            }
                            DownloadUtil.onDownLoadImage(searImage);
                            DownloadUtil.setOnDownLoadBack(new DownloadUtil.OnDownLoadBack() {
                                @Override
                                public void onSuccess() {
                                    ArrayList<Uri> uris = new ArrayList<>();
                                    for (int i = 0; i < mStrings.size(); i++) {
                                        String uri =
                                                Environment.getExternalStorageDirectory() + "/SharedCache/" + "Wechat_sharing" + i + ".jpg";
                                        uris.add(Uri.fromFile(new File(uri)));
                                    }
                                    sharePic(uris);
                                }
                            });
                            showToast("推荐语文案已自动复制", Gravity.CENTER);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.onCandySharing(ChoiceShareActivity.this, map);
                                }
                            }, 3000);
                        } else if (mStrings.size() == 1) {
                            Platform platform = ShareUtils.shareContent(Wechat.NAME, mStrings.get(0));
                            platform.setPlatformActionListener(new MyPlatformActionListener());
                            showToast("推荐语文案已自动复制", Gravity.CENTER);
                        } else {
                            Toast.makeText(ChoiceShareActivity.this, "请至少选择一张图片", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChoiceShareActivity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.moments_share:
                mStrings.clear();
                try {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isSelect()) {
                            mStrings.add(list.get(i).getImage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CopyTextUtil.CopyText(ChoiceShareActivity.this, mRecommendConten.getText().toString());
                JurisdictionUtil.Jurisdiction(ChoiceShareActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    if (ShareUtils.isWXClientAvailable(ChoiceShareActivity.this)) {
                        if (mStrings.size() > 1) {
                            String[] searImage = new String[mStrings.size()];
                            for (int i = 0; i < mStrings.size(); i++) {
                                searImage[i] = mStrings.get(i);
                            }
                            downBusiness(searImage, 1, 1);
                            WindowUtils.Show(ChoiceShareActivity.this, R.layout.bus_dialog_image, 1);
                        } else if (mStrings.size() == 1) {
                            Platform platform = ShareUtils.shareContent(WechatMoments.NAME, mStrings.get(0));
                            platform.setPlatformActionListener(new MyPlatformActionListener());
                            showToast("推荐语文案已自动复制", Gravity.CENTER);
                        } else {
                            Toast.makeText(ChoiceShareActivity.this, "请至少选择一张图片", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChoiceShareActivity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.qq_share:
                try {
                    mStrings.clear();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isSelect()) {
                            mStrings.add(list.get(i).getImage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CopyTextUtil.CopyText(ChoiceShareActivity.this, mRecommendConten.getText().toString());
                JurisdictionUtil.Jurisdiction(ChoiceShareActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    if (ShareUtils.isQQClientAvailable(ChoiceShareActivity.this)) {
                        if (mStrings.size() > 1) {
                            String[] searImage = new String[mStrings.size()];
                            for (int i = 0; i < mStrings.size(); i++) {
                                searImage[i] = mStrings.get(i);
                            }
                            downBusiness(searImage, 1, 4);
                            WindowUtils.Show(ChoiceShareActivity.this, R.layout.bus_dialog_image, 1);
                        } else if (mStrings.size() == 1) {
                            String[] searImage = new String[mStrings.size()];
                            for (int i = 0; i < mStrings.size(); i++) {
                                searImage[i] = mStrings.get(i);
                            }
                            DownloadUtil.onDownLoadImage(searImage);
                            DownloadUtil.setOnDownLoadBack(new DownloadUtil.OnDownLoadBack() {
                                @Override
                                public void onSuccess() {
                                    String uri = null;
                                    for (int i = 0; i < mStrings.size(); i++) {
                                        uri = Environment.getExternalStorageDirectory() + "/SharedCache/" + "Wechat_sharing" + i + ".jpg";
                                    }
                                    Platform platform = ShareUtils.shareContentPath(QQ.NAME, uri);
                                    platform.setPlatformActionListener(new MyPlatformActionListener());
                                    showToast("推荐语文案已自动复制", Gravity.CENTER);
                                }
                            });
                        } else {
                            Toast.makeText(ChoiceShareActivity.this, "请至少选择一张图片", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChoiceShareActivity.this, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.qq_moments_share:
                try {
                    mStrings.clear();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isSelect()) {
                            mStrings.add(list.get(i).getImage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CopyTextUtil.CopyText(ChoiceShareActivity.this, mRecommendConten.getText().toString());
                JurisdictionUtil.Jurisdiction(ChoiceShareActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    if (ShareUtils.isQQClientAvailable(ChoiceShareActivity.this)) {
                        if (mStrings.size() > 1) {
                            String[] searImage = new String[mStrings.size()];
                            for (int i = 0; i < mStrings.size(); i++) {
                                searImage[i] = mStrings.get(i);
                            }
                            downBusiness(searImage, 1, 2);
                            WindowUtils.Show(ChoiceShareActivity.this, R.layout.bus_dialog_image, 1);
                        } else if (mStrings.size() == 1) {
                            String[] searImage = new String[mStrings.size()];
                            for (int i = 0; i < mStrings.size(); i++) {
                                searImage[i] = mStrings.get(i);
                            }
                            DownloadUtil.onDownLoadImage(searImage);
                            DownloadUtil.setOnDownLoadBack(new DownloadUtil.OnDownLoadBack() {
                                @Override
                                public void onSuccess() {
                                    String uri = null;
                                    for (int i = 0; i < mStrings.size(); i++) {
                                        uri = Environment.getExternalStorageDirectory() + "/SharedCache/" + "Wechat_sharing" + i + ".jpg";
                                    }
                                    Platform platform = ShareUtils.shareContentPath(QZone.NAME, uri);
                                    platform.setPlatformActionListener(new MyPlatformActionListener());
                                    showToast("推荐语文案已自动复制", Gravity.CENTER);
                                }
                            });
                        } else {
                            Toast.makeText(ChoiceShareActivity.this, "请至少选择一张图片", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChoiceShareActivity.this, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.dwon_share:
                try {
                    mStrings.clear();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isSelect()) {
                            mStrings.add(list.get(i).getImage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JurisdictionUtil.Jurisdiction(ChoiceShareActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    if (mStrings.size() > 0) {
                        String[] searImage = new String[mStrings.size()];
                        for (int i = 0; i < mStrings.size(); i++) {
                            searImage[i] = mStrings.get(i);
                        }
                        downBusiness(searImage, 2, 3);
                        WindowUtils.Show(ChoiceShareActivity.this, R.layout.bus_dialog_image, 1);
                    } else {
                        Toast.makeText(ChoiceShareActivity.this, "请至少选择一张图片", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                showToast("缺少必要权限");
                break;
            case R.id.view_one:
                c_one.setChecked(!c_one.isChecked());
                setOneConten(shareReason);
                break;
            case R.id.c_one:
                setOneConten(shareReason);
                break;
            case R.id.view_three:
                c_three.setChecked(!c_three.isChecked());
                if (!TextUtils.isEmpty(taoTokens)) {
                    setTwoConten(taoTokens);
                }
                break;
            case R.id.c_three:
                if (!TextUtils.isEmpty(taoTokens)) {
                    setTwoConten(taoTokens);
                }
                break;
            case R.id.view_two:
                c_two.setChecked(!c_two.isChecked());
                if (!TextUtils.isEmpty(taoTokens)) {
                    setTwoConten(taoTokens);
                }
                break;
            case R.id.c_two:
                if (!TextUtils.isEmpty(taoTokens)) {
                    setTwoConten(taoTokens);
                }
                break;
            case R.id.view_four:
                c_four.setChecked(!c_four.isChecked());
                if (!TextUtils.isEmpty(taoTokens)) {
                    setTwoConten(taoTokens);
                }
                break;
            case R.id.c_four:
                if (!TextUtils.isEmpty(taoTokens)) {
                    setTwoConten(taoTokens);
                }
                break;
            case R.id.rule:
                boolean fastClick = ConstantUtil.isFastClick();
                if (fastClick) {
                    try {
                        View show = WindowUtils.ShowBrightness(ChoiceShareActivity.this, R.layout.popup_choice, 1);
                        TextView clone = show.findViewById(R.id.clone);
                        clone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                WindowUtils.dismissBrightness(ChoiceShareActivity.this);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.g_three:
                boolean fastClick2 = ConstantUtil.isFastClick();
                if (fastClick2) {
                    Intent intent = new Intent(ChoiceShareActivity.this, SharingRulesActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof RequestInfor) {
            RequestInfor requestInfor = (RequestInfor) data;
            if (requestInfor.getCode() == 1) {
                PopupWindow popupWindow = WindowUtils.timeShowOnly(ChoiceShareActivity.this, R.layout.popup_tisp, R.style.TispAnim, 0);
                TextView value_tisp = popupWindow.getContentView().findViewById(R.id.value_tisp);
                value_tisp.setText("分享成功，获得" + requestInfor.getValue() + "个糖果，点击查看详情>>");
                popupWindow.getContentView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ChoiceShareActivity.this, BCMessageActivity.class));
                        WindowUtils.dismissOnly();
                    }
                });
            }
        }
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        Log.e("onNotNetWork", "onNotNetWork: ");
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
        Log.e("onNotNetWork", "onNotNetWork: ");
    }


    public Bitmap createBitmap3(View view, Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        view.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        int w = view.getWidth();
        int h = view.getHeight();
        Bitmap bitMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(bitMap);
        c.drawColor(Color.WHITE);
        view.layout(0, 0, w, h);
        view.draw(c);
        return bitMap;
    }

    private void saveBitmap(Bitmap bitmap, String tmplName) {
        String appDir = Environment.getExternalStorageDirectory() + "/InvitationPoster/";
        try {
            File dirFile = new File(appDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File file = new File(appDir, tmplName + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUpLoadImage(Uri uri) {
        UploadImageUtil.UploadImage(ChoiceShareActivity.this, uri, System.currentTimeMillis() + "",
                new UploadImageUtil.OnUpLoadmageCallBack() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("InvitationPoster", data);
                        RequestInfor requestInfor = new Gson().fromJson(data, RequestInfor.class);
                        if (requestInfor.getData() != null) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = requestInfor.getData();
                            mHandler.sendMessage(message);
                        }

                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }
                });
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String image = (String) msg.obj;
                    ImageSelectInfor imageSelectInfor = new ImageSelectInfor();
                    List<ImageSelectInfor.ImageBean> beanList = new ArrayList<>();
                    for (int i = 0; i < mStrings.size(); i++) {
                        ImageSelectInfor.ImageBean imageBean = new ImageSelectInfor.ImageBean();
                        if (i == 0) {
                            Log.e("handleMessage", image);
                            imageBean.setImage(image);
                            imageBean.setSelect(true);
                        } else {
                            imageBean.setImage(mStrings.get(i));
                            imageBean.setSelect(false);
                        }
                        beanList.add(imageBean);
                    }
                    imageSelectInfor.setImageBean(beanList);
                    list = beanList;
                    choiceAdapter.addData(list);
                    hiddenLoadingView();
                    break;
            }
        }
    };

    //下载图片
    private void downBusiness(String[] strings, int shareId, int contenId) {
        DownLoadImage dinstance = DownLoadImage.getInstance();
        dinstance.setContext(ChoiceShareActivity.this);
        dinstance.DownLoadImag(strings);
        dinstance.setOnDownLoadImage(new DownLoadImage.onDownLoadImage() {
            @Override
            public void progressMax(int value) {

            }

            @Override
            public void progressValue(int value, int contentLen) {

            }

            @Override
            public void progerssVisibility() {
                try {
                    WindowUtils.dismiss();
                    if (shareId == 1) {
                        View show = WindowUtils.ShowBrightness(ChoiceShareActivity.this, R.layout.popup_business_dwon, 1);
                        TextView textView = show.findViewById(R.id.openwx);
                        TextView title = show.findViewById(R.id.title);
                        TextView conten = show.findViewById(R.id.conten);
                        TextView conten2 = show.findViewById(R.id.conten2);
                        conten.setText("推荐语文案已自动复制");
                        conten2.setText("图片已保存到本地");
                        if (contenId == 1) {
                            textView.setText("打开朋友圈");
                            title.setText("去朋友圈分享");
                        }
                        if (contenId == 2) {
                            textView.setText("打开QQ空间");
                            title.setText("去QQ空间分享");
                        }
                        if (contenId == 4) {
                            textView.setText("打开QQ");
                            title.setText("去QQ分享");
                        }
                        show.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                WindowUtils.dismissBrightness(ChoiceShareActivity.this);
                                presenter.onCandySharing(ChoiceShareActivity.this, map);
                            }
                        });
                        show.findViewById(R.id.openwx).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (contenId == 1) {
                                    if (ShareUtils.isWXClientAvailable(ChoiceShareActivity.this)) {
                                        Intent intent = new Intent();
                                        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                        intent.setAction(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setComponent(cmp);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(ChoiceShareActivity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (contenId == 2 || contenId == 4) {
                                    if (ShareUtils.isQQClientAvailable(ChoiceShareActivity.this)) {
                                        Intent intent = ChoiceShareActivity.this.getPackageManager().getLaunchIntentForPackage("com" +
                                                ".tencent.mobileqq");
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(ChoiceShareActivity.this, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                WindowUtils.dismissBrightness(ChoiceShareActivity.this);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        presenter.onCandySharing(ChoiceShareActivity.this, map);
                                    }
                                }, 3000);
                            }
                        });
                    } else {
                        WindowUtils.dismiss();
                        showToast("图片已保存到本地");
                        presenter.onCandySharing(ChoiceShareActivity.this, map);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void downLiadImage(int imageLength) {

            }

            @Override
            public void downLoadValue(int value) {

            }
        });
    }

    public void setOneConten(String shareReason) {
        if (!TextUtils.isEmpty(shareReason)) {
            if (c_one.isChecked()) {
                mRecommendConten.setText("【" + goodDetailBean.getName() + "】" + "\n" + "【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                        "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "【下载大熊酷朋】下单返还" + profit + "元" + "\n" + "------ " +
                        "---------" + "\n" + shareReason);
            } else {
                mRecommendConten.setText("【" + goodDetailBean.getName() + "】" + "\n" + "【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                        "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "------ ---------" + "\n" + shareReason);
            }
        } else {
            if (c_one.isChecked()) {
                mRecommendConten.setText("【" + goodDetailBean.getName() + "】" + "\n" + "【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                        "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "【下载大熊酷朋】下单返还" + profit + "元" + "\n" + "------ " +
                        "---------");
            } else {
                mRecommendConten.setText("【" + goodDetailBean.getName() + "】" + "\n" + "【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                        "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "------ ---------");
            }
        }
    }

    public void setTwoConten(String taoToken) {
        //全选
        if (c_two.isChecked() && c_three.isChecked() && c_four.isChecked()) {
            mGoodsConten.setText("【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                    "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "【下载大熊酷朋】下单返还" + profit + "元" + "\n" + "【下载链接】" + ConstantUtil.download + "\n" +
                    "【邀请码】" + BearMallAplication.getInstance().getUser().getRecommendCode() + "\n" + "------ ---------" + "\n" + "復製评论" + "(" + taoToken + "),去【tao寶】下单");
        }
        //选择前两个
        if (c_two.isChecked() && c_three.isChecked() && !c_four.isChecked()) {
            mGoodsConten.setText("【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                    "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "【下载大熊酷朋】下单返还" + profit + "元" + "\n" + "【下载链接】" + ConstantUtil.download + "\n" + "------ ---------" + "\n" + "復製评论" + "(" + taoToken + "),去【tao寶】下单");

        }
        //选择第一个
        if (c_two.isChecked() && !c_three.isChecked() && !c_four.isChecked()) {
            mGoodsConten.setText("【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                    "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "【下载大熊酷朋】下单返还" + profit + "元" + "\n" + "------ ---------" + "\n" + "復製评论" + "(" + taoToken + "),去【tao寶】下单");

        }
        //全不选
        if (!c_two.isChecked() && !c_three.isChecked() && !c_four.isChecked()) {
            mGoodsConten.setText("【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                    "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "------ ---------" + "\n" + "復製评论" + "(" + taoToken + ")," +
                    "去【tao寶】下单");
        }
        //选择第一个和最后一个
        if (c_two.isChecked() && !c_three.isChecked() && c_four.isChecked()) {
            mGoodsConten.setText("【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                    "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "【下载大熊酷朋】下单返还" + profit + "元" + "\n" +
                    "【邀请码】" + BearMallAplication.getInstance().getUser().getRecommendCode() + "\n" + "------ ---------" + "\n" + "復製评论" + "(" + taoToken + "),去【tao寶】下单");

        }
        //选择后两个
        if (!c_two.isChecked() && c_three.isChecked() && c_four.isChecked()) {
            mGoodsConten.setText("【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                    "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "【下载链接】" + ConstantUtil.download + "\n" +
                    "【邀请码】" + BearMallAplication.getInstance().getUser().getRecommendCode() + "\n" + "------ ---------" + "\n" + "復製评论" + "(" + taoToken + "),去【tao寶】下单");
        }
        //选择最后一个
        if (!c_two.isChecked() && !c_three.isChecked() && c_four.isChecked()) {
            mGoodsConten.setText("【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                    "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" +
                    "【邀请码】" + BearMallAplication.getInstance().getUser().getRecommendCode() + "\n" + "------ ---------" + "\n" + "復製评论" + "(" + taoToken + "),去【tao寶】下单");
        }
        //选择中间一个
        if (!c_two.isChecked() && c_three.isChecked() && !c_four.isChecked()) {
            mGoodsConten.setText("【原价】" + goodDetailBean.getPrice() + "元" + "\n" +
                    "【券后价】" + goodDetailBean.getDiscountPrice() + "元" + "\n" + "【下载链接】" + ConstantUtil.download + "\n" + "------ " +
                    "---------" + "\n" + "復製评论" + "(" + taoToken + "),去【tao寶】下单");
        }

    }

    public void sharePic(ArrayList<Uri> imageUris) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    public class MyPlatformActionListener implements PlatformActionListener {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            presenter.onCandySharing(ChoiceShareActivity.this, map);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    }
}
