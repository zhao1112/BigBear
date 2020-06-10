package com.bbcoupon.ui.bean;

import java.util.List;

import cn.sharesdk.onekeyshare.themes.classic.PRTHeader;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/5/28
 */
public class MakeInfor {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"restReward":67,"task":{"usersRegisterRewardMax":"3","friendInvitMAX":"2","memberShareInfoFinish":0,
     * "memberShareProductMAX":"20","memberShareInfoMAX":"10","luckyDrawFinish":0,"memberShareProductFinish":0,"luckyDrawMAX":35,
     * "usersRegisterFinish":1},"bCbanlance":"383.00","banner":[{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202005/5a8a0861-f415-4264-a230-de900f1d3cba.jpg","targetType":16,"id":2251,"title":"杀肖岩","target":"16"}],
     * "todayCreditSum":"3.00","dayOfTask":[{"task_name":"幸运大抽奖","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202005/81269ce7-8b30-4da3-9412-4ef1e1f635a2_source.png","sort":5,"title":"完成3次抽奖共可获得33个糖果"},{"task_name":"每日签到",
     * "img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202005/d5986f6b-68d4-49ec-8014-82b5c8340926_source.png",
     * "sort":1,"title":"每日签到成功即的100个糖果"},{"task_name":"转发宣传素材","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202005/fe07c5d2-02c7-4461-9586-518dcafe6341_source.png","sort":3,"title":"每次可获得30个糖果，每日上限300个糖果"},{"task_name
     * ":"转发商品","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202005/688c7881-f834-4483-87f8-80cd05166a69_source
     * .png","sort":4,"title":"每次可获得30个糖果，每日上限600个糖果"},{"task_name":"邀请好友","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202005/6e6d4506-4ca4-4347-8393-cefddab5536b_source.png","sort":2,"title":"好友注册登录成功后即得500个糖果"}]}
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
         * restReward : 67.0
         * task : {"usersRegisterRewardMax":"3","friendInvitMAX":"2","memberShareInfoFinish":0,"memberShareProductMAX":"20",
         * "memberShareInfoMAX":"10","luckyDrawFinish":0,"memberShareProductFinish":0,"luckyDrawMAX":35,"usersRegisterFinish":1}
         * bCbanlance : 383.00
         * banner : [{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
         * .com/upload/image/202005/5a8a0861-f415-4264-a230-de900f1d3cba.jpg","targetType":16,"id":2251,"title":"杀肖岩","target":"16"}]
         * todayCreditSum : 3.00
         * dayOfTask : [{"task_name":"幸运大抽奖","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
         * .com/upload/image/202005/81269ce7-8b30-4da3-9412-4ef1e1f635a2_source.png","sort":5,"title":"完成3次抽奖共可获得33个糖果"},{"task_name
         * ":"每日签到","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
         * .com/upload/image/202005/d5986f6b-68d4-49ec-8014-82b5c8340926_source.png","sort":1,"title":"每日签到成功即的100个糖果"},{"task_name
         * ":"转发宣传素材","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
         * .com/upload/image/202005/fe07c5d2-02c7-4461-9586-518dcafe6341_source.png","sort":3,"title":"每次可获得30个糖果，每日上限300个糖果"},{
         * "task_name":"转发商品","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
         * .com/upload/image/202005/688c7881-f834-4483-87f8-80cd05166a69_source.png","sort":4,"title":"每次可获得30个糖果，每日上限600个糖果"},{
         * "task_name":"邀请好友","img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
         * .com/upload/image/202005/6e6d4506-4ca4-4347-8393-cefddab5536b_source.png","sort":2,"title":"好友注册登录成功后即得500个糖果"}]
         */

        private int restReward;
        private TaskBean task;
        private int bCbanlance;
        private int todayCreditSum;
        private List<BannerBean> banner;
        private List<DayOfTaskBean> dayOfTask;
        private String rule;

        public int getbCbanlance() {
            return bCbanlance;
        }

        public void setbCbanlance(int bCbanlance) {
            this.bCbanlance = bCbanlance;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public int getRestReward() {
            return restReward;
        }

        public void setRestReward(int restReward) {
            this.restReward = restReward;
        }

        public TaskBean getTask() {
            return task;
        }

        public void setTask(TaskBean task) {
            this.task = task;
        }

        public int getBCbanlance() {
            return bCbanlance;
        }

        public void setBCbanlance(int bCbanlance) {
            this.bCbanlance = bCbanlance;
        }

        public int getTodayCreditSum() {
            return todayCreditSum;
        }

        public void setTodayCreditSum(int todayCreditSum) {
            this.todayCreditSum = todayCreditSum;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<DayOfTaskBean> getDayOfTask() {
            return dayOfTask;
        }

        public void setDayOfTask(List<DayOfTaskBean> dayOfTask) {
            this.dayOfTask = dayOfTask;
        }

        public static class TaskBean {
            /**
             * usersRegisterRewardMax : 3
             * friendInvitMAX : 2
             * memberShareInfoFinish : 0
             * memberShareProductMAX : 20
             * memberShareInfoMAX : 10
             * luckyDrawFinish : 0
             * memberShareProductFinish : 0
             * luckyDrawMAX : 35
             * usersRegisterFinish : 1
             */

            private String usersRegisterRewardMax;
            private String friendInvitMAX;
            private int memberShareInfoFinish;
            private String memberShareProductMAX;
            private String memberShareInfoMAX;
            private int luckyDrawFinish;
            private int memberShareProductFinish;
            private int luckyDrawMAX;
            private int usersRegisterFinish;

            public String getUsersRegisterRewardMax() {
                return usersRegisterRewardMax;
            }

            public void setUsersRegisterRewardMax(String usersRegisterRewardMax) {
                this.usersRegisterRewardMax = usersRegisterRewardMax;
            }

            public String getFriendInvitMAX() {
                return friendInvitMAX;
            }

            public void setFriendInvitMAX(String friendInvitMAX) {
                this.friendInvitMAX = friendInvitMAX;
            }

            public int getMemberShareInfoFinish() {
                return memberShareInfoFinish;
            }

            public void setMemberShareInfoFinish(int memberShareInfoFinish) {
                this.memberShareInfoFinish = memberShareInfoFinish;
            }

            public String getMemberShareProductMAX() {
                return memberShareProductMAX;
            }

            public void setMemberShareProductMAX(String memberShareProductMAX) {
                this.memberShareProductMAX = memberShareProductMAX;
            }

            public String getMemberShareInfoMAX() {
                return memberShareInfoMAX;
            }

            public void setMemberShareInfoMAX(String memberShareInfoMAX) {
                this.memberShareInfoMAX = memberShareInfoMAX;
            }

            public int getLuckyDrawFinish() {
                return luckyDrawFinish;
            }

            public void setLuckyDrawFinish(int luckyDrawFinish) {
                this.luckyDrawFinish = luckyDrawFinish;
            }

            public int getMemberShareProductFinish() {
                return memberShareProductFinish;
            }

            public void setMemberShareProductFinish(int memberShareProductFinish) {
                this.memberShareProductFinish = memberShareProductFinish;
            }

            public int getLuckyDrawMAX() {
                return luckyDrawMAX;
            }

            public void setLuckyDrawMAX(int luckyDrawMAX) {
                this.luckyDrawMAX = luckyDrawMAX;
            }

            public int getUsersRegisterFinish() {
                return usersRegisterFinish;
            }

            public void setUsersRegisterFinish(int usersRegisterFinish) {
                this.usersRegisterFinish = usersRegisterFinish;
            }
        }

        public static class BannerBean {
            /**
             * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202005/5a8a0861-f415-4264-a230-de900f1d3cba.jpg
             * targetType : 16
             * id : 2251
             * title : 杀肖岩
             * target : 16
             */

            private String image;
            private int targetType;
            private int id;
            private String title;
            private String target;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getTargetType() {
                return targetType;
            }

            public void setTargetType(int targetType) {
                this.targetType = targetType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }
        }

        public static class DayOfTaskBean {
            /**
             * task_name : 幸运大抽奖
             * img : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202005/81269ce7-8b30-4da3-9412-4ef1e1f635a2_source.png
             * sort : 5
             * title : 完成3次抽奖共可获得33个糖果
             */

            private String task_name;
            private String img;
            private int sort;
            private String title;

            public String getTask_name() {
                return task_name;
            }

            public void setTask_name(String task_name) {
                this.task_name = task_name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
