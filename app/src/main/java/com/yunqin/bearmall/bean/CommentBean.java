package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/27
 * @Describe
 */
public class CommentBean {

    /**
     * data : {"reviewList":[{"content":"很好","productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","order":0},"product_id":14,"reviewImages":[{"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_thumbnail.jpg","order":0}],"review_id":10005,"nickName":"大熊用户536893360","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg","score":3,"isAnonymous":0,"productName":"小米(MI) 小米 5X","createdDate":"2018-07-19 20:33:44","specifications":["黑色","32GB"]},{"content":"很好","productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","order":0},"product_id":14,"reviewImages":[{"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_thumbnail.jpg"}],"review_id":10004,"nickName":"大熊会员*****","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg","score":4,"isAnonymous":1,"productName":"小米(MI) 小米 5X","createdDate":"2018-07-19 20:26:11","specifications":["黑色","32GB"]},{"content":"","productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/8691990c-57ee-4bdf-9a7e-758e2ec09d0d_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/8691990c-57ee-4bdf-9a7e-758e2ec09d0d_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/8691990c-57ee-4bdf-9a7e-758e2ec09d0d_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/8691990c-57ee-4bdf-9a7e-758e2ec09d0d_thumbnail.jpg","order":0},"product_id":7,"reviewImages":null,"review_id":10002,"nickName":"大熊用户536893360","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg","score":5,"isAnonymous":0,"productName":"华为(HUAWEI) 畅享7","createdDate":"2018-07-17 18:35:43","specifications":["黑色","16GB"]}],"has_more":0}
     * code : 1
     * msg : 请求成功
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * reviewList : [{"content":"很好","productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","order":0},"product_id":14,"reviewImages":[{"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_thumbnail.jpg","order":0}],"review_id":10005,"nickName":"大熊用户536893360","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg","score":3,"isAnonymous":0,"productName":"小米(MI) 小米 5X","createdDate":"2018-07-19 20:33:44","specifications":["黑色","32GB"]},{"content":"很好","productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","order":0},"product_id":14,"reviewImages":[{"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_thumbnail.jpg"}],"review_id":10004,"nickName":"大熊会员*****","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg","score":4,"isAnonymous":1,"productName":"小米(MI) 小米 5X","createdDate":"2018-07-19 20:26:11","specifications":["黑色","32GB"]},{"content":"","productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/8691990c-57ee-4bdf-9a7e-758e2ec09d0d_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/8691990c-57ee-4bdf-9a7e-758e2ec09d0d_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/8691990c-57ee-4bdf-9a7e-758e2ec09d0d_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/8691990c-57ee-4bdf-9a7e-758e2ec09d0d_thumbnail.jpg","order":0},"product_id":7,"reviewImages":null,"review_id":10002,"nickName":"大熊用户536893360","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg","score":5,"isAnonymous":0,"productName":"华为(HUAWEI) 畅享7","createdDate":"2018-07-17 18:35:43","specifications":["黑色","16GB"]}]
         * has_more : 0
         */

        private int has_more;
        private List<ReviewListBean> reviewList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<ReviewListBean> getReviewList() {
            return reviewList;
        }

        public void setReviewList(List<ReviewListBean> reviewList) {
            this.reviewList = reviewList;
        }

        public static class ReviewListBean {
            /**
             * content : 很好
             * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","order":0}
             * product_id : 14
             * reviewImages : [{"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_thumbnail.jpg","order":0}]
             * review_id : 10005
             * nickName : 大熊用户536893360
             * iconUrl : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg
             * score : 3
             * isAnonymous : 0
             * productName : 小米(MI) 小米 5X
             * createdDate : 2018-07-19 20:33:44
             * specifications : ["黑色","32GB"]
             */

            private String content;
            private ProductImagesBean productImages;
            private int product_id;
            private int review_id;
            private String nickName;
            private String iconUrl;
            private int score;
            private int isAnonymous;
            private String productName;
            private String createdDate;
            private List<ReviewImagesBean> reviewImages;
            private List<String> specifications;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public ProductImagesBean getProductImages() {
                return productImages;
            }

            public void setProductImages(ProductImagesBean productImages) {
                this.productImages = productImages;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getReview_id() {
                return review_id;
            }

            public void setReview_id(int review_id) {
                this.review_id = review_id;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getIsAnonymous() {
                return isAnonymous;
            }

            public void setIsAnonymous(int isAnonymous) {
                this.isAnonymous = isAnonymous;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public List<ReviewImagesBean> getReviewImages() {
                return reviewImages;
            }

            public void setReviewImages(List<ReviewImagesBean> reviewImages) {
                this.reviewImages = reviewImages;
            }

            public List<String> getSpecifications() {
                return specifications;
            }

            public void setSpecifications(List<String> specifications) {
                this.specifications = specifications;
            }

            public static class ProductImagesBean {
                /**
                 * source : http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_source.jpg
                 * large : http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_large.jpg
                 * medium : http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_medium.jpg
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg
                 * order : 0
                 */

                private String source;
                private String large;
                private String medium;
                private String thumbnail;
                private int order;

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public int getOrder() {
                    return order;
                }

                public void setOrder(int order) {
                    this.order = order;
                }
            }

            public static class ReviewImagesBean {
                /**
                 * source : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_source.jpg
                 * large : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_large.jpg
                 * medium : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_medium.jpg
                 * thumbnail : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/cc8fbc63-8e55-4b3a-a574-5d7762884ae1_thumbnail.jpg
                 * order : 0
                 */

                private String source;
                private String large;
                private String medium;
                private String thumbnail;
                private int order;

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public int getOrder() {
                    return order;
                }

                public void setOrder(int order) {
                    this.order = order;
                }
            }
        }
    }
}
