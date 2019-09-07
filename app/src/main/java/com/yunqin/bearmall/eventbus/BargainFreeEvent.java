package com.yunqin.bearmall.eventbus;

public class BargainFreeEvent {
    private String message;

    public BargainFreeEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
