package com.yunqin.bearmall.eventbus;

public class ProductDetailMessageEvent {

    private String message;
    public ProductDetailMessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
