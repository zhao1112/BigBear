package com.newversions.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Create By Master
 * On 2019/2/15 15:17
 */
public class VerticalViewPager extends ViewGroup {

    int currentPage = 1;

    /**
     * 速度轨迹追踪器
     */
    private VelocityTracker mVelocityTracker;

    /**
     * 此次计算速度你想要的最大值
     */
    private final int mMaxVelocity;

    /**
     * 第一个触点的id， 此时可能有多个触点，但至少一个
     */
    private int mPointerId;

    /**
     * 计算出的竖向滚动速率
     */
    private float velocityY;

    /**
     * 手指横向滑动的速率临界值，大于这个值时，不考虑手指滑动的距离，直接滚动到最左边或者最右边
     */
    private int criticalVelocityY = 2500;

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mYDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mYMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mYLastMove;

    /**
     * 界面可滚动的顶部边界
     */
    private int topBorder;

    /**
     * 界面可滚动的底部边界
     */
    private int bottomBorder;


    /**
     * 子控件的高度（这里每个子控件高度都一样，都是match_parent）
     */
    private int childHeight;


    /**
     * 手指是否是向下滑动
     */
    private boolean shouZhiXiangXiaHuaDong;
    private int childCount;

    @Override
    public boolean isFocused() {
        return true;
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 第一步，创建Scroller的实例
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        //此次计算速度你想要的最大值
        mMaxVelocity = ViewConfiguration.get(context).getMaximumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            /**
             * 当前子控件之前的所有子控件的总宽度
             */
            int preChildViewTotalHeight = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 为ScrollerLayout中的每一个子控件在竖直方向上进行布局
                if (i == 0) {
                    childView.layout(
                            0,
                            0,
                            childView.getMeasuredWidth(),
                            childView.getMeasuredHeight());

                } else {
                    childView.layout(
                            0,
                            preChildViewTotalHeight,
                            childView.getMeasuredWidth(),
                            preChildViewTotalHeight + childView.getMeasuredHeight());
                }
                preChildViewTotalHeight += childView.getMeasuredHeight();

            }
            // 初始化上下边界值
            topBorder = getChildAt(0).getTop();
            bottomBorder = getChildAt(getChildCount() - 1).getBottom();

            childHeight = getChildAt(0).getMeasuredHeight();


        }
    }


    private int downX;
    private int downY;

    //    告诉此ScrollLayout的父布局，什么时候该拦截触摸事件，什么时候不该拦截触摸事件
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //让当前ScrollerLayout对应的父控件不要去拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                //请求父控件ViewPager拦截触摸事件，ViewPager左右滚动时，不要触发该布局的上下滑动
                if (Math.abs(moveY - downY) < Math.abs(moveX - downX)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //请求父控件ViewPager不要拦截触摸事件，ScrollerLayout自己可以上下滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;

            case MotionEvent.ACTION_CANCEL:


                break;
            case MotionEvent.ACTION_UP:


                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    //   ScrollLayout告诉自己什么时候要拦截内部子View的触摸事件，什么时候不要拦截内部子View的触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //▲▲▲1.求第一个触点的id， 此时可能有多个触点，但至少一个
                mPointerId = ev.getPointerId(0);
                mYDown = ev.getRawY();
                mYLastMove = mYDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mYMove = ev.getRawY();
                float diff = Math.abs(mYMove - mYDown);
                mYLastMove = mYMove;
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //▲▲▲2.向VelocityTracker添加MotionEvent
        acquireVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                //▲▲▲3.求伪瞬时速度
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                velocityY = mVelocityTracker.getYVelocity(mPointerId);

                mYMove = event.getRawY();
                int scrolledY = (int) (mYLastMove - mYMove);//注意取的是负值，因为是整个布局在动，而不是控件在动


                if (getScrollY() + scrolledY < topBorder) {// 如果已经在最上端了，就不让再往上滑动了(重点一、边界判断，直接照着这个模板抄就行)
                    scrollTo(0, topBorder);
                    return true;//★★★★★★★★★★★★★★★★这里返回true或者false实践证明都可以，但是不能什么都不返回。
                } else if (getScrollY() + getHeight() + scrolledY > bottomBorder) {//如果已经在最底部了，就不让再往底部滑动了
                    scrollTo(0, bottomBorder - getHeight());
                    return true;//★★★★★★★★★★★★★★★★★这里返回true或者false实践证明都可以，但是不能什么都不返回。
                }

                scrollBy(0, scrolledY);//手指move时，布局跟着滚动
                if (mYDown <= mYMove) {//★★★判断手指向上滑动，还是向下滑动，要用mYDown，而不是mYLastMove
                    shouZhiXiangXiaHuaDong = true;//手指往下滑动
                } else {
                    shouZhiXiangXiaHuaDong = false;//手指往上滑动
                }
                mYLastMove = mYMove;
                break;
            case MotionEvent.ACTION_UP:
//        4.▲▲▲释放VelocityTracker
                releaseVelocityTracker();

                // 第二步，当手指抬起时，根据当前的滚动值以及滚动方向来判定应该滚动到哪个子控件的界面，并且记得调用invalidate();
                if (Math.abs(velocityY) > criticalVelocityY) {//当手指滑动速度快时，按照速度方向直接翻页
//          重点二、快速滑动时，如何判断当前显示的是第几个控件，并且再次包含边界判断（必须包含边界判断，因为前面的边界判断，只适用于低速滑动时）
                    if (shouZhiXiangXiaHuaDong) {
                        if (currentPage > 1) {//★★★★★★★★边界限制，防止滑倒第一个，还继续滑动，注意★（currentPage-2）
                            mScroller.startScroll(0, getScrollY(), 0, childHeight * (currentPage - 2) - getScrollY());
                            currentPage--;
                        }
                    } else {
                        if (currentPage < childCount) {//★★★★★★★边界限制，防止滑倒最后一个，还继续滑动，注意★currentPage
                            mScroller.startScroll(0, getScrollY(), 0, childHeight * currentPage - getScrollY());
                            currentPage++;
                        }
                    }
                    Log.e("eee", currentPage + "");
                } else {//当手指滑动速度不够快时，按照松手时，已经滑动的位置来决定翻页

//       重点三、低速滑动时，如何根据位置来判断当前显示的是第几个控件，（这里不必再次进行边界判断，因为第一次的边界判断，在这里会起到作用）
                    if ((getScrollY() >= childHeight * (currentPage - 1) + childHeight / 2 && !shouZhiXiangXiaHuaDong)) {
//           手指向上滑动并且，滚动距离过了屏幕一半的距离
                        mScroller.startScroll(0, getScrollY(), 0, childHeight * (currentPage) - getScrollY());
                        currentPage++;

                    } else if ((getScrollY() < childHeight * (currentPage - 1) + childHeight / 2 && !shouZhiXiangXiaHuaDong)) {
//           手指向上滑动并且，滚动距离没有过屏幕一半的距离
                        mScroller.startScroll(0, getScrollY(), 0, childHeight * (currentPage - 1) - getScrollY());

                    } else if
                            ((getScrollY() <= childHeight * (currentPage - 2) + childHeight / 2
                                    && shouZhiXiangXiaHuaDong)) {
//            手指向下滑动并且，滚动距离过了屏幕一半的距离
                        mScroller.startScroll(0, getScrollY(), 0, childHeight * (currentPage - 2) - getScrollY());
                        currentPage--;
                    } else if
                            ((getScrollY() > childHeight * (currentPage - 2) + childHeight / 2
                                    && shouZhiXiangXiaHuaDong)) {
//            手指向下滑动并且，滚动距离没有过屏幕一半的距离
                        mScroller.startScroll(0, getScrollY(), 0, childHeight * (currentPage - 1) - getScrollY());
                    }






          /* if ((getScrollY() >= childHeight && !shouZhiXiangXiaHuaDong)//手指往左滑动，并且滑动完全显示第二个控件时，viewgroup滑动到最右端
              || ((getScrollY() >= (totalChildHeight - firstChildHeight - lastChildHeight) && shouZhiXiangXiaHuaDong))) {//手指往右滑动，并且当滑动没有完全隐藏最后一个控件时，viewgroup滑动到最右端
//          当滚动值大于某个数字时(大于第二个控件的宽度，即完全显示第二个控件时)并且是向左滑动，让这个viewgroup滑动到整个Viewgroup的最右侧，
//          因为右侧的所有控件宽度是600，而现在已经滑动的距离是getScrollX，
//          那么，还应该继续滑动的距离是600-getScrollX()，这里正值表示向右滑动
            mScroller.startScroll(0,getScrollY(), 0, (totalChildHeight - firstChildHeight) - getScrollY());
          } else if ((getScrollY() <= (totalChildHeight - firstChildHeight - lastChildHeight) && shouZhiXiangXiaHuaDong)//手指往右滑动，并且当滑动完全隐藏最后一个控件时，viewgroup滑动到最左端
              || (getScrollY() <= childHeight && !shouZhiXiangXiaHuaDong)) {//手指往左滑动，并且滑动没有完全显示第二个控件时，viewgroup滑动到最左端

//          当滚动值小于某个数字时，让这个viewgroup滑动到整个Viewgroup的最左侧，
//          因为滑动到最左侧时，就是让整个viewgroup的滑动量为0，而现在已经滑动的距离是getScrollX，
//          那么，还应该继续滑动的距离是0-getScrollX()，这里负值表示向左滑动
            mScroller.startScroll(0,getScrollY(), 0, 0 - getScrollY());
          }*/
                }
//        必须调用invalidate()重绘
                invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:
//       5.▲▲▲释放VelocityTracker
                releaseVelocityTracker();

                break;
        }
        return super.onTouchEvent(event);
    }




    public void scroolToTop(){
        shouZhiXiangXiaHuaDong = true;
        mScroller.startScroll(0, getScrollY(), 0, childHeight * (currentPage - 2) - getScrollY());
        currentPage--;
        invalidate();
    }

    public void scroolToBottom(){
        shouZhiXiangXiaHuaDong = false;
        mScroller.startScroll(0, getScrollY(), 0, childHeight * (currentPage) - getScrollY());
        currentPage++;
        invalidate();
    }




    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * @param event 向VelocityTracker添加MotionEvent
     * @see VelocityTracker#obtain()
     * @see VelocityTracker#addMovement(MotionEvent)
     */
    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 释放VelocityTracker
     *
     * @see VelocityTracker#clear()
     * @see VelocityTracker#recycle()
     */
    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


}
