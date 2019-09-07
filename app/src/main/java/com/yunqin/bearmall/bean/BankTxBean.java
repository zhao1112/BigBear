package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Create By Master
 * On 2019/3/12 17:23
 */
public class BankTxBean {


    /**
     * msg : 请求成功
     * code : 1
     * data : {"withdrawList":[{"amount":"20.00","createdDate":"2019-03-05 16:55:45","bankCard":"123123123123","accountName":"詹先生","type":0,"status":0}],"has_more":0}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * withdrawList : [{"amount":"20.00","createdDate":"2019-03-05 16:55:45","bankCard":"123123123123","accountName":"詹先生","type":0,"status":0}]
         * has_more : 0
         */

        private int has_more;
        private List<WithdrawListBean> withdrawList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<WithdrawListBean> getWithdrawList() {
            return withdrawList;
        }

        public void setWithdrawList(List<WithdrawListBean> withdrawList) {
            this.withdrawList = withdrawList;
        }

        public static class WithdrawListBean {
            /**
             * amount : 20.00
             * createdDate : 2019-03-05 16:55:45
             * bankCard : 123123123123
             * accountName : 詹先生
             * type : 0
             * status : 0
             */

            private String amount;
            private String createdDate;
            private String bankCard;
            private String accountName;
            private int type;
            private int status;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getBankCard() {
                return bankCard;
            }

            public void setBankCard(String bankCard) {
                this.bankCard = bankCard;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
