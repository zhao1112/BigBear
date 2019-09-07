package com.yunqin.bearmall.ui.activity;

import com.yunqin.bearmall.bean.BCMessage;
import com.yunqin.bearmall.bean.BCMessageData;
import com.yunqin.bearmall.bean.DealMessage;
import com.yunqin.bearmall.bean.DealMessageData;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class DealMessageActivity extends BaseMessageActivity<DealMessage,DealMessageData> {
    @Override
    protected int getCategory() {
        return 1;
    }

    @Override
    protected void onLoadedServerData(DealMessageData object) {

        if (object != null){

            if (object.getCode() == 1){

                DealMessageData.DataBean bean = object.getData();

                setCanLoadMore(bean.getHas_more() == 1);

                List<DealMessage> messages = bean.getList();

                changeData(messages);

            }

        }

    }

    @Override
    protected void onStartDetails(int article_id) {

    }

    @Override
    String setBarTitle() {
        return "交易提醒";
    }
}
