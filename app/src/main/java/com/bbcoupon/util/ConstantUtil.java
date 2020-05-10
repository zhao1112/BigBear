package com.bbcoupon.util;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/5/6
 */
public class ConstantUtil {

    public static final String first_home = "FIRST_HOME";//第一次打开首页
    public static final String first_search = "FIRST_SEARCH";//第一次打开搜索页
    public static final String first_goodes = "FIRST_GOODES";//第一次打开搜索详情页

    public static final String money = "¥";

    public static final String download = "https://android.myapp.com/myapp/detail.htm?apkName=com.bbearmall.app&ADTAG=mobile#opened";

    public static final String DOUBLING_RULE = "1";//佣金翻倍规则

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
