package com.yunqin.bearmall.bean;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/4/13
 */
public class ProfitBean {

    private String msg;
    private int code;
    private Data data;

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

    public static class Data {
        //注册
        private double thisMonthConfirmReceipt;
        private double todayConfirmReceipt;
        private double todayIndividualPurchased;
        private double thisMonthIndividualPurchased;
        private int thisMonthPaymentPens;
        private int todayPaymentPens;
        //超级
        private double thisMonthRecommendEarnings;
        private double todayRecommendEarnings;
        private int thisMonthClinchADealNumber;
        private int todayClinchADealNumber;
        private double thisMonthfirmEarnings;
        private double todayConfirmEarnings;
        //大团长
        private double todayPredictioEarnings;
        private double thisMonthPredictioEarnings;
        private double thisMonthfirmPredictio;
        private int todayTeamPrediction;
        private int thisMonthTeamPrediction;
        private double todayConfirmPredictio;

        public double getTodayPredictioEarnings() {
            return todayPredictioEarnings;
        }

        public void setTodayPredictioEarnings(double todayPredictioEarnings) {
            this.todayPredictioEarnings = todayPredictioEarnings;
        }

        public double getThisMonthPredictioEarnings() {
            return thisMonthPredictioEarnings;
        }

        public void setThisMonthPredictioEarnings(double thisMonthPredictioEarnings) {
            this.thisMonthPredictioEarnings = thisMonthPredictioEarnings;
        }

        public double getThisMonthfirmPredictio() {
            return thisMonthfirmPredictio;
        }

        public void setThisMonthfirmPredictio(double thisMonthfirmPredictio) {
            this.thisMonthfirmPredictio = thisMonthfirmPredictio;
        }

        public int getTodayTeamPrediction() {
            return todayTeamPrediction;
        }

        public void setTodayTeamPrediction(int todayTeamPrediction) {
            this.todayTeamPrediction = todayTeamPrediction;
        }

        public int getThisMonthTeamPrediction() {
            return thisMonthTeamPrediction;
        }

        public void setThisMonthTeamPrediction(int thisMonthTeamPrediction) {
            this.thisMonthTeamPrediction = thisMonthTeamPrediction;
        }

        public double getTodayConfirmPredictio() {
            return todayConfirmPredictio;
        }

        public void setTodayConfirmPredictio(double todayConfirmPredictio) {
            this.todayConfirmPredictio = todayConfirmPredictio;
        }

        public double getThisMonthRecommendEarnings() {
            return thisMonthRecommendEarnings;
        }

        public void setThisMonthRecommendEarnings(double thisMonthRecommendEarnings) {
            this.thisMonthRecommendEarnings = thisMonthRecommendEarnings;
        }

        public double getTodayRecommendEarnings() {
            return todayRecommendEarnings;
        }

        public void setTodayRecommendEarnings(double todayRecommendEarnings) {
            this.todayRecommendEarnings = todayRecommendEarnings;
        }

        public int getThisMonthClinchADealNumber() {
            return thisMonthClinchADealNumber;
        }

        public void setThisMonthClinchADealNumber(int thisMonthClinchADealNumber) {
            this.thisMonthClinchADealNumber = thisMonthClinchADealNumber;
        }

        public int getTodayClinchADealNumber() {
            return todayClinchADealNumber;
        }

        public void setTodayClinchADealNumber(int todayClinchADealNumber) {
            this.todayClinchADealNumber = todayClinchADealNumber;
        }

        public double getThisMonthfirmEarnings() {
            return thisMonthfirmEarnings;
        }

        public void setThisMonthfirmEarnings(double thisMonthfirmEarnings) {
            this.thisMonthfirmEarnings = thisMonthfirmEarnings;
        }

        public double getTodayConfirmEarnings() {
            return todayConfirmEarnings;
        }

        public void setTodayConfirmEarnings(double todayConfirmEarnings) {
            this.todayConfirmEarnings = todayConfirmEarnings;
        }

        public double getThisMonthConfirmReceipt() {
            return thisMonthConfirmReceipt;
        }

        public void setThisMonthConfirmReceipt(double thisMonthConfirmReceipt) {
            this.thisMonthConfirmReceipt = thisMonthConfirmReceipt;
        }

        public double getTodayConfirmReceipt() {
            return todayConfirmReceipt;
        }

        public void setTodayConfirmReceipt(double todayConfirmReceipt) {
            this.todayConfirmReceipt = todayConfirmReceipt;
        }

        public double getTodayIndividualPurchased() {
            return todayIndividualPurchased;
        }

        public void setTodayIndividualPurchased(double todayIndividualPurchased) {
            this.todayIndividualPurchased = todayIndividualPurchased;
        }

        public double getThisMonthIndividualPurchased() {
            return thisMonthIndividualPurchased;
        }

        public void setThisMonthIndividualPurchased(double thisMonthIndividualPurchased) {
            this.thisMonthIndividualPurchased = thisMonthIndividualPurchased;
        }

        public int getThisMonthPaymentPens() {
            return thisMonthPaymentPens;
        }

        public void setThisMonthPaymentPens(int thisMonthPaymentPens) {
            this.thisMonthPaymentPens = thisMonthPaymentPens;
        }

        public int getTodayPaymentPens() {
            return todayPaymentPens;
        }

        public void setTodayPaymentPens(int todayPaymentPens) {
            this.todayPaymentPens = todayPaymentPens;
        }
    }

}
