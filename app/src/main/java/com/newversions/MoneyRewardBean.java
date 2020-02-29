package com.newversions;

import java.util.List;

/**
 * Create By Master
 * On 2019/2/19 16:12
 */
public class MoneyRewardBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"has_more":0,"list":[{"orderNo":"212132122","title":"您在2019-07-27 15:00:54下单成功，预估佣金12元,预计结算时间:收货后次月25日结算","value":"12","specifications":"21.00","content":"淘宝"},{"orderNo":"546339910557977185","title":"您在2019-07-27 15:01:20下单成功，预估佣金10元,预计结算时间:收货后次月25日结算","value":"10","specifications":"2.28","content":"天猫"},{"orderNo":"546339910557977185","title":"您在2019-07-27 15:01:20下单成功，预估佣金10元,预计结算时间:收货后次月25日结算","value":"10","specifications":"2.28","content":null},{"orderNo":"553702050359902038","title":"您在2019-12-02 14:49:12下单成功，预估佣金20元,预计结算时间:收货后次月25日结算","value":"20","specifications":null,"content":null},{"orderNo":null,"title":"您在2018-09-15 10:00:29下单成功，预估返佣25元,预计结算时间:收货后次月25日结算","value":"25","specifications":null,"content":null},{"orderNo":null,"title":"您于2019-09-26 09:48:35下单成功，预估返佣30元","value":"+30","specifications":null,"content":null},{"orderNo":null,"title":"您于2019-09-26 09:48:35下单成功，预估返佣30元","value":"+30","specifications":null,"content":null},{"orderNo":null,"title":"佣金消息","value":"+20","specifications":null,"content":"您于2020-02-26 16:55:08下单成功，预估返佣20元"},{"orderNo":null,"title":"佣金消息","value":null,"specifications":null,"content":"您于元"}]}
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
         * has_more : 0
         * list : [{"orderNo":"212132122","title":"您在2019-07-27 15:00:54下单成功，预估佣金12元,预计结算时间:收货后次月25日结算","value":"12","specifications":"21.00","content":"淘宝"},{"orderNo":"546339910557977185","title":"您在2019-07-27 15:01:20下单成功，预估佣金10元,预计结算时间:收货后次月25日结算","value":"10","specifications":"2.28","content":"天猫"},{"orderNo":"546339910557977185","title":"您在2019-07-27 15:01:20下单成功，预估佣金10元,预计结算时间:收货后次月25日结算","value":"10","specifications":"2.28","content":null},{"orderNo":"553702050359902038","title":"您在2019-12-02 14:49:12下单成功，预估佣金20元,预计结算时间:收货后次月25日结算","value":"20","specifications":null,"content":null},{"orderNo":null,"title":"您在2018-09-15 10:00:29下单成功，预估返佣25元,预计结算时间:收货后次月25日结算","value":"25","specifications":null,"content":null},{"orderNo":null,"title":"您于2019-09-26 09:48:35下单成功，预估返佣30元","value":"+30","specifications":null,"content":null},{"orderNo":null,"title":"您于2019-09-26 09:48:35下单成功，预估返佣30元","value":"+30","specifications":null,"content":null},{"orderNo":null,"title":"佣金消息","value":"+20","specifications":null,"content":"您于2020-02-26 16:55:08下单成功，预估返佣20元"},{"orderNo":null,"title":"佣金消息","value":null,"specifications":null,"content":"您于元"}]
         */

        private int has_more;
        private List<ListBean> list;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * orderNo : 212132122
             * title : 您在2019-07-27 15:00:54下单成功，预估佣金12元,预计结算时间:收货后次月25日结算
             * value : 12
             * specifications : 21.00
             * content : 淘宝
             */

            private String orderNo;
            private String title;
            private String value;
            private String specifications;
            private String content;

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getSpecifications() {
                return specifications;
            }

            public void setSpecifications(String specifications) {
                this.specifications = specifications;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
