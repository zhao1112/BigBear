package com.yunqin.bearmall.eventbus;

public class ProductMessageEvent {

    private String message;

    public  ProductMessageEvent(String message){
        this.message=message;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
