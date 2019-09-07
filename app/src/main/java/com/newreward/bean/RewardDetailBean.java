package com.newreward.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2019/2/18
 * @Describe
 */
public class RewardDetailBean {

    /**
     * data : {"balance":"10.00","list":[{"createdDate":"2019-01-04 14:44:51","balanceChange":"+10.00","detail":"18513188768通过您的推广成为会员（M1）","source_id":12018,"type":1}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * balance : 10.00
         * list : [{"createdDate":"2019-01-04 14:44:51","balanceChange":"+10.00","detail":"18513188768通过您的推广成为会员（M1）","source_id":12018,"type":1}]
         */

        private String balance;
        private int has_more;
        private List<ListBean> todayRewardList;

        public boolean hasMore() {

            return has_more == 1;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public List<ListBean> getList() {
            return todayRewardList;
        }

        public void setList(List<ListBean> list) {
            this.todayRewardList = list;
        }

        public static class ListBean {
            /**
             * createdDate : 2019-01-04 14:44:51
             * balanceChange : +10.00
             * detail : 18513188768通过您的推广成为会员（M1）
             * source_id : 12018
             * type : 1
             */

            private String createdDate;
            private String balanceChange;
            private String detail;
            private int source_id;
            private int type;
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getBalanceChange() {
                return balanceChange;
            }

            public void setBalanceChange(String balanceChange) {
                this.balanceChange = balanceChange;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getSource_id() {
                return source_id;
            }

            public void setSource_id(int source_id) {
                this.source_id = source_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
