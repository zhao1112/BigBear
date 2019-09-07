package com.yunqin.bearmall.ui.activity;

import android.view.View;

import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.bean.ActivityMessage;
import com.yunqin.bearmall.bean.ActivityMessageData;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class EventMessageActivity extends BaseMessageActivity<ActivityMessage, ActivityMessageData> implements View.OnClickListener {
    @Override
    protected int getCategory() {
        return 3;
    }

    @Override
    protected void onLoadedServerData(ActivityMessageData object) {

        adapter.setOnClickListener(this);

        if (object != null) {

            if (object.getCode() == 1) {

                ActivityMessageData.DataBean bean = object.getData();

                setCanLoadMore(bean.getHas_more() == 1);

                List<ActivityMessage> messages = bean.getList();

                changeData(messages);

            }

        }

    }

    @Override
    protected void onStartDetails(int article_id) {

    }

    @Override
    String setBarTitle() {
        return "活动优惠";
    }

    @Override
    public void onClick(View v) {
        try {
            List<ActivityMessage> messages = adapter.getData();
            int index = (int) v.getTag();
            String article_id = messages.get(index).getArticle_id();
            if (article_id == null || "".equals(article_id) || "null".equals(article_id)) {
                return;
            }
            String guidelUrl = BuildConfig.BASE_URL + "/view/findArticleDetailPage?article_id=" + article_id;
            VanguardListPageActivity.startH5Activity(this, guidelUrl, "活动详情");
        } catch (Exception e) {

        }
    }
}
