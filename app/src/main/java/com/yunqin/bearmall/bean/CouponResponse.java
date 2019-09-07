package com.yunqin.bearmall.bean;

import java.util.List;

public class CouponResponse extends BaseResponseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

       private int has_more;
       private int totalCount;
       private List<Coupon> usersTicketList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<Coupon> getUsersTicketList() {
            return usersTicketList;
        }

        public void setUsersTicketList(List<Coupon> usersTicketList) {
            this.usersTicketList = usersTicketList;
        }
    }


}
