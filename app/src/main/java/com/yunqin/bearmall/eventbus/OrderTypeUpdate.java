package com.yunqin.bearmall.eventbus;

/**
 * @author Master
 * @create 2018/8/20 13:46
 */
public class OrderTypeUpdate {

    private int id;

    public OrderTypeUpdate(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
