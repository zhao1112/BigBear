package com.yunqin.bearmall.eventbus;

public class TrolleyCountEvent {
    private String message;
    private int count;
    public TrolleyCountEvent(String message) {
        this.message = message;
    }

    public  TrolleyCountEvent(int count){
        this.count=count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
