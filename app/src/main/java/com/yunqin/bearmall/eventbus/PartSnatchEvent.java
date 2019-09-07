package com.yunqin.bearmall.eventbus;

/**
 * Created by chenchen on 2018/8/11.
 */

public class PartSnatchEvent {

    private int postion;
    private boolean isRefresh;

    public PartSnatchEvent(int postion,boolean isRefresh) {
        this.postion = postion;
        this.isRefresh = isRefresh;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
