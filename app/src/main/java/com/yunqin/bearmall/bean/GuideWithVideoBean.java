package com.yunqin.bearmall.bean;

import java.util.List;

public class GuideWithVideoBean extends BaseResponseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class  DataBean{

        private List<GuideArticleList> guideArticleList;

        private int has_more;

        public List<GuideArticleList> getGuideArticleList() {
            return guideArticleList;
        }

        public int getHas_more() {
            return has_more;
        }
    }

}
