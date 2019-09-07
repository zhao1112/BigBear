package com.yunqin.bearmall.eventbus;

public class ProductDetailCommentMessageEvent {

    private String message;
    public ProductDetailCommentMessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
