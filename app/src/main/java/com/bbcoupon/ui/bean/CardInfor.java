package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/5/21
 */
public class CardInfor  {

    private int id;
    private String sex;

    public CardInfor(int id, String cardNo) {
        this.id = id;
        this.sex = cardNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNo() {
        return sex;
    }

    public void setCardNo(String cardNo) {
        this.sex = cardNo;
    }

}
