package com.yunqin.bearmall.eventbus;

/**
 * @author AYWang
 * @create 2018/7/18
 * @Describe
 */
public class VoucherEvent {
    private String id;
    private String amount;
   public VoucherEvent(String id,String amount){
       this.id = id;
       this.amount = amount;
   }

    public String getVoucherID(){
        return id;
    }

    public String getVoucherAmount(){
        return amount;
    }
}
