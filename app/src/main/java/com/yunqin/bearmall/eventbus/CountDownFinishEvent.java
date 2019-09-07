package com.yunqin.bearmall.eventbus;

/**
 * @author AYWang
 * @create 2018/8/29
 * @Describe
 */
public class CountDownFinishEvent {
    private int whichRefresh;

    public CountDownFinishEvent(int whichRefresh){
        this.whichRefresh = whichRefresh;
    }
    public int getWhichRefresh(){
        return whichRefresh;
    }
}
