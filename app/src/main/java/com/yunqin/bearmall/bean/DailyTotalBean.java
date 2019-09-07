package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/8/14
 * @Describe
 */
public class DailyTotalBean {


    /**
     * msg : 请求成功
     * code : 1
     * data : {"dailyTaskInfo":{"needCount":30,"num":0,"dayReward":"100.00","monthReward":"1000.00","totalCount":30}}
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
         * dailyTaskInfo : {"needCount":30,"num":0,"dayReward":"100.00","monthReward":"1000.00","totalCount":30}
         */

        private DailyTaskInfoBean dailyTaskInfo;

        public DailyTaskInfoBean getDailyTaskInfo() {
            return dailyTaskInfo;
        }

        public void setDailyTaskInfo(DailyTaskInfoBean dailyTaskInfo) {
            this.dailyTaskInfo = dailyTaskInfo;
        }

        public static class DailyTaskInfoBean {
            /**
             * needCount : 30
             * num : 0
             * dayReward : 100.00
             * monthReward : 1000.00
             * totalCount : 30
             */

            private int needCount;
            private int num;
            private String dayReward;
            private String monthReward;
            private int totalCount;

            public int getNeedCount() {
                return needCount;
            }

            public void setNeedCount(int needCount) {
                this.needCount = needCount;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getDayReward() {
                return dayReward;
            }

            public void setDayReward(String dayReward) {
                this.dayReward = dayReward;
            }

            public String getMonthReward() {
                return monthReward;
            }

            public void setMonthReward(String monthReward) {
                this.monthReward = monthReward;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }
        }
    }
}
