package com.yunqin.bearmall.bean;

/**
 * Created by chenchen on 2018/7/25.
 */

public class BearFAQ {

    private int code;

    private String message;

    private DataBean data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private int clearanceReward;

        private QusetionDate current;

        private FinishData tikuLog;

        private int perReward;

        private int clearanceNumber;

        private int isFinished;

        private int isActived;

        private int isAnswer;

        private String lastRightAnswer;

        public int getIsActived() {
            return isActived;
        }

        public void setIsActived(int isActived) {
            this.isActived = isActived;
        }

        public String getLastRightAnswer() {
            return lastRightAnswer;
        }

        public void setLastRightAnswer(String lastRightAnswer) {
            this.lastRightAnswer = lastRightAnswer;
        }

        public int getIsAnswer() {
            return isAnswer;
        }

        public void setIsAnswer(int isAnswer) {
            this.isAnswer = isAnswer;
        }

        public int getClearanceReward() {
            return clearanceReward;
        }

        public void setClearanceReward(int clearanceReward) {
            this.clearanceReward = clearanceReward;
        }

        public QusetionDate getCurrent() {
            return current;
        }

        public void setCurrent(QusetionDate current) {
            this.current = current;
        }

        public FinishData getTikuLog() {
            return tikuLog;
        }

        public void setTikuLog(FinishData tikuLog) {
            this.tikuLog = tikuLog;
        }

        public int getPerReward() {
            return perReward;
        }

        public void setPerReward(int perReward) {
            this.perReward = perReward;
        }

        public int getClearanceNumber() {
            return clearanceNumber;
        }

        public void setClearanceNumber(int clearanceNumber) {
            this.clearanceNumber = clearanceNumber;
        }

        public int getIsFinished() {
            return isFinished;
        }

        public void setIsFinished(int isFinished) {
            this.isFinished = isFinished;
        }

        public int getIsAvtived() {
            return isActived;
        }

        public void setIsAvtived(int isActived) {
            this.isActived = isActived;
        }

        public boolean isActive(){

            return this.isActived == 1;
        }
    }


    public static class QusetionDate{

        private int tag;

        private String title;

        private String options0;

        private String options1;

        private String options2;

        private String options3;

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOptions0() {
            return options0;
        }

        public void setOptions0(String options0) {
            this.options0 = options0;
        }

        public String getOptions1() {
            return options1;
        }

        public void setOptions1(String options1) {
            this.options1 = options1;
        }

        public String getOptions2() {
            return options2;
        }

        public void setOptions2(String options2) {
            this.options2 = options2;
        }

        public String getOptions3() {
            return options3;
        }

        public void setOptions3(String options3) {
            this.options3 = options3;
        }
    }


    public static class FinishData{

        private int id;

        private int finishNumber;

        private int reward;

        private String createdDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFinishNumber() {
            return finishNumber;
        }

        public void setFinishNumber(int finishNumber) {
            this.finishNumber = finishNumber;
        }

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }
    }


}
