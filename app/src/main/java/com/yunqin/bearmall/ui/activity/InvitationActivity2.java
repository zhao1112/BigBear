package com.yunqin.bearmall.ui.activity;


import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.Invitationinfo;
import com.yunqin.bearmall.ui.dialog.NewInvitationDialog;
import com.yunqin.bearmall.util.GlideBlurformation;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.json.JSONException;

import java.util.List;

import cn.jzvd.Jzvd;


/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity.presenter
 * @DATE 2019/8/30
 */
public class InvitationActivity2 extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private XBanner mInvitation_banner;
    private XBanner mInvitation_botton_banner;
    private XBanner mInvitation_image;
    private List<String> mImageList;
    private LinearLayout mInvitation_show;
    private View mNot_net;
    private HighlightButton mReset_load_data;
    private RelativeLayout mApp_view_share;
    private LinearLayout app_share_bg;

    @Override
    public int layoutId() {
        return R.layout.activity_invitation2;
    }

    @Override
    public void init() {
        mInvitation_botton_banner = findViewById(R.id.app_invitation_botton_banner);
        mInvitation_banner = findViewById(R.id.app_invitation_banner);
        ImageView invitation_back = findViewById(R.id.app_invitation_back);
        mInvitation_image = findViewById(R.id.app_invitation_image);
        TextView invitation_share = findViewById(R.id.app_invitation_share);
        mInvitation_show = findViewById(R.id.app_invitation_show);
        mNot_net = findViewById(R.id.not_net);
        HighlightButton mReset_load_data = findViewById(R.id.reset_load_data);
        mApp_view_share = findViewById(R.id.app_view_share);
        RelativeLayout app_banner = findViewById(R.id.app_banner);
        app_share_bg = findViewById(R.id.app_share_bg);

        app_banner.post(new Runnable() {
            @Override
            public void run() {
                int width = app_banner.getWidth();
                int height = width * 500 / 360;
                ViewGroup.LayoutParams layoutParams = app_banner.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = height;
                app_banner.setLayoutParams(layoutParams);

            }
        });

        invitation_back.setOnClickListener(this);
        invitation_share.setOnClickListener(this);
        mReset_load_data.setOnClickListener(this);
        mInvitation_banner.setOnPageChangeListener(this);
        Log.i("invitation2", "init: 1");
        showLoading();
        getImageData();

        //XBanner默认显示点击事件
        mInvitation_banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                mInvitation_botton_banner.setBannerCurrentItem(position);
                app_share_bg.setBackgroundColor(getResources().getColor(R.color.home_select_color));
                mInvitation_show.setVisibility(View.GONE);
                mInvitation_botton_banner.setVisibility(View.VISIBLE);
                mInvitation_botton_banner.post(new Runnable() {
                    @Override
                    public void run() {
                        int width = mInvitation_botton_banner.getWidth();
                        int height = width * 640 / 360;
                        ViewGroup.LayoutParams layoutParams = mInvitation_botton_banner.getLayoutParams();
                        layoutParams.width = width;
                        layoutParams.height = height;
                        mInvitation_botton_banner.setLayoutParams(layoutParams);

                    }
                });
            }
        });

        //XBanner点击事件
        mInvitation_botton_banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                mInvitation_banner.setBannerCurrentItem(position);
                app_share_bg.setBackgroundColor(getResources().getColor(R.color.md_white));
                mInvitation_botton_banner.setVisibility(View.GONE);
                mInvitation_show.setVisibility(View.VISIBLE);
            }
        });
    }

    //拉取图片
    private void getImageData() {
        Log.i("invitation2", "init: 2");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).createManyInviteImage(), new RetrofitApi.IResponseListener() {

            @Override
            public void onSuccess(String data) throws JSONException {
                Log.i("invitation2", "init: 3" + data);
                mNot_net.setVisibility(View.GONE);
                mInvitation_banner.setVisibility(View.VISIBLE);
                mApp_view_share.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                Invitationinfo invitationinfo = gson.fromJson(data, Invitationinfo.class);
                Log.i("invitation2", "init: 3" + invitationinfo.toString());
                mImageList = invitationinfo.getData();
                for (int i = 0; i < mImageList.size(); i++) {
                    Log.i("mImageList", mImageList.get(i));
                }
                if (mImageList != null && mImageList.size() > 0) {
                    hiddenLoadingView();
                    setBannerImage(mImageList);
                }
            }

            @Override
            public void onNotNetWork() {
                Log.i("invitation2", "init: 4");
            }

            @Override
            public void onFail(Throwable e) {
                Log.i("Throwable", "onFail: ");
                mInvitation_banner.setVisibility(View.GONE);
                mApp_view_share.setVisibility(View.GONE);
                hiddenLoadingView();
                mNot_net.setVisibility(View.VISIBLE);
            }
        });
    }

    //XBanner设置图片
    private void setBannerImage(List<String> imageList) {
        Log.i("invitation2", "init: 5");
        if (imageList != null && imageList.size() > 0) {
            mInvitation_botton_banner.setData(mImageList, null);
            mInvitation_banner.setData(mImageList, null);
            mInvitation_image.setData(mImageList, null);
            mInvitation_banner.setBannerCurrentItem(1);
            mInvitation_image.setBannerCurrentItem(1);
            mInvitation_banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            int width = view.getWidth();
                            int height = width * 459 / 258;
                            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                            layoutParams.width = width;
                            layoutParams.height = height;
                            view.setLayoutParams(layoutParams);
                        }
                    });
                    Glide.with(InvitationActivity2.this)
                            .load(mImageList.get(position))
                            .apply(new RequestOptions().placeholder(R.drawable.default_product))
                            .apply(new RequestOptions().bitmapTransform(new RoundedCorners(20)))
                            .into((ImageView) view);
                }

            });
            mInvitation_botton_banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(InvitationActivity2.this)
                            .load(mImageList.get(position))
                            .apply(new RequestOptions().placeholder(R.drawable.default_product))
                            .into((ImageView) view);
                }
            });
            mInvitation_image.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(InvitationActivity2.this)
                            .load(mImageList.get(position))
                            .apply(new RequestOptions().placeholder(R.drawable.default_product))
                            .apply(new RequestOptions().placeholder(R.drawable.default_product).bitmapTransform(new GlideBlurformation(InvitationActivity2.this)))
                            .into((ImageView) view);
                }
            });
        }
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_invitation_back:
                finish();
                break;
            case R.id.app_invitation_share:
                //获取当前位置
                if (mImageList != null && mImageList.size() > 0) {
                    Log.i("onItemClick", "onItemClick: 1");
                    int bannerCurrentItem = mInvitation_banner.getBannerCurrentItem();
                    String imageUrl = mImageList.get(bannerCurrentItem);
                    if (imageUrl != null) {
                        Log.i("onItemClick", "onItemClick: 2");
                        NewInvitationDialog newInvitationDialog = new NewInvitationDialog(InvitationActivity2.this, imageUrl);
                        newInvitationDialog.showShareDialog(new NewInvitationDialog.setLiangDu() {
                            @Override
                            public void lightoff() {
                                lighton();
                            }
                        });
                        lightoff();
                    }
                }
                break;
            case R.id.reset_load_data:
                mNot_net.setVisibility(View.GONE);
                showLoading();
                getImageData();
                break;
        }
    }

    //XBanner滚动事件
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mInvitation_image.setBannerCurrentItem(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //设置手机屏幕亮度变暗
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
    }

    //设置手机屏幕亮度显示正常
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

    private long firstPressedTime;

    @Override
    public void onBackPressed() {
        if (mInvitation_botton_banner.getVisibility() == View.GONE) {
         finish();
        }else {
            int bannerCurrentItem = mInvitation_botton_banner.getBannerCurrentItem();
            mInvitation_banner.setBannerCurrentItem(bannerCurrentItem);
            mInvitation_image.setBannerCurrentItem(bannerCurrentItem);
            app_share_bg.setBackgroundColor(getResources().getColor(R.color.md_white));
            mInvitation_botton_banner.setVisibility(View.GONE);
            mInvitation_show.setVisibility(View.VISIBLE);
        }
    }
}



