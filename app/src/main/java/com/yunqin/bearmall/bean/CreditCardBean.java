package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Create By Master
 * On 2019/3/28 17:06
 */
public class CreditCardBean {

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

        private int has_more;
        private List<CardListBean> cardList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<CardListBean> getCardList() {
            return cardList;
        }

        public void setCardList(List<CardListBean> cardList) {
            this.cardList = cardList;
        }

        public static class CardListBean {
            /**
             * cashbackStatus : 0
             * img : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201903/6898d1b7-93fc-43d9-8759-c3dda0550e3c.jpg
             * cardName : 工商银行
             * userCreditCardRecord_id : 2
             * isFirstCard : 0
             * applyTime : 2019-03-27 21:18:11
             * applyStatus : 0
             * desc : 查询
             */

            private int cashbackStatus;
            private String img;
            private String cardName;
            private int userCreditCardRecord_id;
            private int isFirstCard;
            private String applyTime;
            private int applyStatus;
            private String desc;

            public int getCashbackStatus() {
                return cashbackStatus;
            }

            public void setCashbackStatus(int cashbackStatus) {
                this.cashbackStatus = cashbackStatus;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public int getUserCreditCardRecord_id() {
                return userCreditCardRecord_id;
            }

            public void setUserCreditCardRecord_id(int userCreditCardRecord_id) {
                this.userCreditCardRecord_id = userCreditCardRecord_id;
            }

            public int getIsFirstCard() {
                return isFirstCard;
            }

            public void setIsFirstCard(int isFirstCard) {
                this.isFirstCard = isFirstCard;
            }

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public int getApplyStatus() {
                return applyStatus;
            }

            public void setApplyStatus(int applyStatus) {
                this.applyStatus = applyStatus;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
