package com.newversions.detail;

import java.util.List;

/**
 * Create By Master
 * On 2019/1/15 10:42
 */
public class NewReviewListBean {


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
        private List<ReviewListBean> reviewList;
        private int has_more;

        public List<ReviewListBean> getReviewList() {
            return reviewList;
        }

        public void setReviewList(List<ReviewListBean> reviewList) {
            this.reviewList = reviewList;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public static class ReviewListBean{


            private String content;
            private int review_id;
            private String nickName;
            private String iconUrl;
            private int score;
            private String createdDate;
            private java.util.List<ReviewImagesBean> reviewImages;
            private java.util.List<String> specifications;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public static class ReviewImagesBean {
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
