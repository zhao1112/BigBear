package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/4/26
 */
public class WithdrawalInfor {

    public String msg;
    public int code;
    public Data data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
       public String withdrawFrom;
       public String balance;

        public String getWithdrawFrom() {
            return withdrawFrom;
        }

        public void setWithdrawFrom(String withdrawFrom) {
            this.withdrawFrom = withdrawFrom;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
