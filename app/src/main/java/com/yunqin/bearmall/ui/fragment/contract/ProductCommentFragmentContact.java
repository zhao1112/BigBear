package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.bean.ReviewListBean;
import com.yunqin.bearmall.bean.SweetsBt;

import java.util.Map;

public interface ProductCommentFragmentContact {

    interface UI{
        void  attachhData(ReviewListBean reviewList);
        void onHasMore(boolean hasMore);
        void onError();
    }
    interface presenter{
        void refresh(Map map);
    }
}
