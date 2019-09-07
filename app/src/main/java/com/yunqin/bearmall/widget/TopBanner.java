package com.yunqin.bearmall.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;

import java.util.List;

import mlxy.utils.App;

/**
 * @author Master
 */
public class TopBanner extends RelativeLayout {


    private static final int WHAT_AUTO_PLAY = 1000;
    //Point位置
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    /**
     * 指示器点的真正容器，水平的线性布局
     */
    private LinearLayout mPointRealContainerLl;

    private ViewPager mViewPager;
    //网络图片资源
    private List<String> mImageUrls;
    //是否只有一张图片
    private boolean mIsOneImg = false;
    //是否可以自动播放
    private boolean mAutoPlayAble = true;
    //是否正在播放
    private boolean mIsAutoPlaying = false;
    //自动播放时间 s
    private int mAutoPalyTime = 3000;
    //当前页面位置
    private int mCurrentPositon;
    //指示点位置
    private int mPointPosition = RIGHT;
    //指示点资源
    private int mPointDrawableResId = R.drawable.selector_bgabanner_point;
    //指示容器背景
    private Drawable mPointContainerBackgroundDrawable;
    //指示容器布局规则
    private LayoutParams mPointRealContainerLp;

    //指示点是否可见
    private boolean mPointsIsVisible = true;


    private Handler mAutoPlayHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mCurrentPositon++;
            mViewPager.setCurrentItem(mCurrentPositon);
            mAutoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
        }
    };


    public TopBanner(Context context) {
        this(context, null);
    }

    public TopBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setLayout(context);
    }

    /**
     * 设置布局
     *
     * @param context
     */
    public void setLayout(Context context) {
        //关闭view的OverScroll
        setOverScrollMode(OVER_SCROLL_NEVER);
        if (mPointContainerBackgroundDrawable == null) {
            mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#00aaaaaa"));
        }
        //添加ViewPager
        mViewPager = new ViewPager(context);
        addView(mViewPager, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // 设置指示器背景容器
        RelativeLayout pointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
        } else {
            pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
        }

        // 设置内边距
        pointContainerRl.setPadding(20, 10, 40, 10);
        // 设置指示器容器布局及位置
        LayoutParams pointContainerLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(pointContainerRl, pointContainerLp);

        // 设置指示器容器
        mPointRealContainerLl = new LinearLayout(context);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
        mPointRealContainerLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPointRealContainerLp.bottomMargin = 20;
        pointContainerRl.addView(mPointRealContainerLl, mPointRealContainerLp);
        //设置指示器容器是否可见
        if (mPointRealContainerLl != null) {
            if (mPointsIsVisible) {
                mPointRealContainerLl.setVisibility(View.VISIBLE);
            } else {
                mPointRealContainerLl.setVisibility(View.GONE);
            }
        }

        // 设置指示器布局的位置
        if (mPointPosition == CENTER) {
            mPointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else if (mPointPosition == LEFT) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (mPointPosition == RIGHT) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
    }

    public void setImagesUrl(List<String> urls) {
        this.mImageUrls = urls;
        if (urls.size() <= 1) {
            mIsOneImg = true;
        }
        // 初始化ViewPager
        initViewPager();
    }

    private void initViewPager() {
        if (!mIsOneImg) {
            addPoints();
        }
        // 设置Adapter
        BannerPageAdapter bannerPageAdapter = new BannerPageAdapter();
        mViewPager.setAdapter(bannerPageAdapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        // 跳转到首页
        if (!mIsOneImg) {
            startAutoPlay();
        }

    }

    /**
     * 开始轮播
     */
    private void startAutoPlay() {
        if (mAutoPlayAble && !mIsAutoPlaying) {
            mIsAutoPlaying = true;
            mAutoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
        }
    }

    /**
     * 添加指示点
     */
    private void addPoints() {
        mPointRealContainerLl.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
        ImageView mImageView;
        int length = mImageUrls.size();
        for (int i = 0; i < length; i++) {
            mImageView = new ImageView(getContext());
            mImageView.setLayoutParams(lp);
            mImageView.setImageResource(mPointDrawableResId);
            mPointRealContainerLl.addView(mImageView);
        }
        switchToPoint(0);
    }

    /**
     * 跳转位置
     *
     * @param currentPoint
     */
    private void switchToPoint(int currentPoint) {
        for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
            mPointRealContainerLl.getChildAt(i).setEnabled(false);
        }
        mPointRealContainerLl.getChildAt(currentPoint).setEnabled(true);
    }

    /**
     * 活动监听
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPositon = position % (mImageUrls.size() + 2);
            switchToPoint(toRealPosition(mCurrentPositon));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                int current = mViewPager.getCurrentItem();
                int lastReal = mViewPager.getAdapter().getCount() - 2;
                if (current == 0) {
                    mViewPager.setCurrentItem(lastReal, false);
                } else if (current == lastReal + 1) {
                    mViewPager.setCurrentItem(1, false);
                }
            }
        }
    };

    /**
     * 自定义Adapter
     */
    private class BannerPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mIsOneImg) {
                return 1;
            }
            return mImageUrls.size() + 2;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(toRealPosition(position));
                    }
                }
            });
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Glide.with(getContext()).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_banner)).load(mImageUrls.get(toRealPosition(position))).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            if (object != null) {
                object = null;
            }
        }
    }

    /**
     * 返回真实的位置
     *
     * @param position
     * @return
     */
    private int toRealPosition(int position) {
        int realPosition = (position - 1) % mImageUrls.size();
        if (realPosition < 0) {
            realPosition += mImageUrls.size();
        }
        return realPosition;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
