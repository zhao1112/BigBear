package com.yunqin.bearmall.ui.activity;

import com.yunqin.bearmall.bean.BCMessage;
import com.yunqin.bearmall.bean.BCMessageData;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class BCMessageActivity extends BaseMessageActivity<BCMessage,BCMessageData> {


    @Override
    protected int getCategory() {
        return 0;
    }

    @Override
    protected void onLoadedServerData(BCMessageData object) {

        if (object != null){

            if (object.getCode() == 1){

                 BCMessageData.BCMessageDataBean bean = object.getData();

                 setCanLoadMore(bean.getHas_more() == 1);

                List<BCMessage> messages = bean.getList();

                changeData(messages);

            }
        }
    }

    @Override
    protected void onStartDetails(int article_id) {

    }

    @Override
    String setBarTitle() {
        return "糖果消息";
    }



}
