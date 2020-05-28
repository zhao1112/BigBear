package com.bbcoupon.ui.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bbcoupon.widget.MakeScrollView;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.widget.DotView;

import butterknife.BindView;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.fragment
 * @DATE 2020/5/27
 */
public class MakeFragment extends BaseFragment {


    @BindView(R.id.dot_view)
    DotView mDotView;
    @BindView(R.id.make_message)
    RelativeLayout mMakeMessage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;
    @BindView(R.id.ll_title_top)
    LinearLayout ll_title_top;
    @BindView(R.id.sv_content)
    MakeScrollView sv_content;
    @BindView(R.id.v_title_line_1)
    View v_title_line_1;
    @BindView(R.id.v_title_line)
    View v_title_line;

    @Override
    public int layoutId() {
        return R.layout.fragment_make;
    }

    @Override
    public void init() {

        ll_title.getBackground().mutate().setAlpha(0);
        // 设置状态栏高度
        int statusBarHeight = this.getResources().getDimensionPixelSize(this.getResources().getIdentifier("status_bar_height", "dimen",
                "android"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        v_title_line.setLayoutParams(params);
        v_title_line_1.setLayoutParams(params);
        // 设置滑动
        sv_content.setOnScrollistener(new MakeScrollView.OnScrollistener() {
            @Override
            public void onScroll(int startY, int endY) {
                //根据scrollview滑动更改标题栏透明度
                changeAphla(startY, endY);
            }
        });

    }

    /**
     * 根据内容窗体的移动改变标题栏背景透明度
     *
     * @param startY scrollview开始滑动的y坐标（相对值）
     * @param endY   scrollview结束滑动的y坐标（相对值）
     */
    private void changeAphla(int startY, int endY) {
        //获取标题高度
        int titleHeight = ll_title.getMeasuredHeight();
        //获取背景高度
        int backHeight = ll_title_top.getMeasuredHeight() / 2;

        //获取控件的绝对位置坐标
        int[] location = new int[2];
        ll_title_top.getLocationInWindow(location);
        //从屏幕顶部到控件顶部的坐标位置Y
        int currentY = location[1];
        //表示回到原位（滑动到顶部）
        if (currentY >= 0) {
            ll_title.getBackground().mutate().setAlpha(0);
            title.setTextColor(getResources().getColor(R.color.white));
        }

        Log.e("zpan", "=titleHeight=" + titleHeight + "=backHeight=" + backHeight + "=currentY=" + currentY);
        //颜色透明度改变
        if (currentY < titleHeight && currentY >= -(backHeight - titleHeight)) {
            //计算出滚动过程中改变的透明值
            double y = Math.abs(currentY) * 1.0;
            double height = (backHeight - titleHeight) * 1.0;
            int changeValue = (int) (y / height * 255);

            Log.e("zpan", "changeValue=" + changeValue);
            //判断是向上还是向下
            if (endY > startY) {    //向上;透明度值增加，实际透明度减小
                ll_title.getBackground().mutate().setAlpha(changeValue);
            } else if (endY < startY) {     //向下；透明度值减小，实际透明度增加
                ll_title.getBackground().mutate().setAlpha(changeValue);
            }
        }
        //红色背景移除屏幕
        if (currentY < -(backHeight - titleHeight)) {
            ll_title.getBackground().mutate().setAlpha(255);
            title.setTextColor(getResources().getColor(R.color.home_select_color));
        }
    }

}
