package com.newversions.tbk.entity;

import java.util.List;

public class RecordEntity {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"incomeDetail":[{"createdDate":"2018-10-11 00:30:00","memo":"每日先锋","credit":"2888.00"},{"createdDate":"2018-10-11 00:30:00","memo":"每日先锋榜","credit":"888.00"}],"has_more":0,"incomeCount":2}
     * onTheDay : {"estimatedEarningsToday":0}
     * duringTheMonth : {"estimatedEarningsToday":0}
     */

    private String msg;
    private int code;
    private DataBean data;
    private OnTheDayBean onTheDay;
    private DuringTheMonthBean duringTheMonth;

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

    public OnTheDayBean getOnTheDay() {
        return onTheDay;
    }

    public void setOnTheDay(OnTheDayBean onTheDay) {
        this.onTheDay = onTheDay;
    }

    public DuringTheMonthBean getDuringTheMonth() {
        return duringTheMonth;
    }

    public void setDuringTheMonth(DuringTheMonthBean duringTheMonth) {
        this.duringTheMonth = duringTheMonth;
    }

    public static class DataBean {
        /**
         * incomeDetail : [{"createdDate":"2018-10-11 00:30:00","memo":"每日先锋","credit":"2888.00"},{"createdDate":"2018-10-11 00:30:00","memo":"每日先锋榜","credit":"888.00"}]
         * has_more : 0
         * incomeCount : 2
         */

        private int has_more;
        private int incomeCount;
        private List<IncomeDetailBean> incomeDetail;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public int getIncomeCount() {
            return incomeCount;
        }

        public void setIncomeCount(int incomeCount) {
            this.incomeCount = incomeCount;
        }

        public List<IncomeDetailBean> getIncomeDetail() {
            return incomeDetail;
        }

        public void setIncomeDetail(List<IncomeDetailBean> incomeDetail) {
            this.incomeDetail = incomeDetail;
        }

        public static class IncomeDetailBean {
            /**
             * createdDate : 2018-10-11 00:30:00
             * memo : 每日先锋
             * credit : 2888.00
             */

            private String createdDate;
            private String memo;
            private String credit;

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }
        }
    }

    public static class OnTheDayBean {
        /**
         * estimatedEarningsToday : 0
         */

        private double estimatedEarningsToday;

        public double getEstimatedEarningsToday() {
            return estimatedEarningsToday;
        }

        public void setEstimatedEarningsToday(double estimatedEarningsToday) {
            this.estimatedEarningsToday = estimatedEarningsToday;
        }
    }

    public static class DuringTheMonthBean {
        /**
         * estimatedEarningsToday : 0
         */

        private double estimatedEarningsToday;

        public double getEstimatedEarningsToday() {
            return estimatedEarningsToday;
        }

        public void setEstimatedEarningsToday(double estimatedEarningsToday) {
            this.estimatedEarningsToday = estimatedEarningsToday;
        }
    }
}
