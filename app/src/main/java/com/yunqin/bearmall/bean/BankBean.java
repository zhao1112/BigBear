package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Create By Master
 * On 2019/2/28 19:39
 */
public class BankBean {


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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * bankCode : CMB
             * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201902/b4288111-4735-43af-966d-c1344af9656e.png
             * bankCard : **** **** **** 6043
             * userBankId : 10
             * cardType : 借记卡
             * bankName : 招商银行
             */

            private String bankCode;
            private String image;
            private String icon;
            private String bankCard;
            private int userBankId;
            private String cardType;
            private String bankName;

            private boolean checked = false;

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String bankCode) {
                this.bankCode = bankCode;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getBankCard() {
                return bankCard;
            }

            public void setBankCard(String bankCard) {
                this.bankCard = bankCard;
            }

            public int getUserBankId() {
                return userBankId;
            }

            public void setUserBankId(int userBankId) {
                this.userBankId = userBankId;
            }

            public String getCardType() {
                return cardType;
            }

            public void setCardType(String cardType) {
                this.cardType = cardType;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }
        }
    }
}
