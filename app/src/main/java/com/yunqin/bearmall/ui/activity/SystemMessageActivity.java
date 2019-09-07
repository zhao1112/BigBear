package com.yunqin.bearmall.ui.activity;

import com.yunqin.bearmall.bean.SystemMessage;
import com.yunqin.bearmall.bean.SystemMessageData;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class SystemMessageActivity extends BaseMessageActivity<SystemMessage,SystemMessageData> {
    @Override
    protected int getCategory() {
        return 2;
    }

    @Override
    protected void onLoadedServerData(SystemMessageData object) {
        // TODO 消息修改888
        if (object != null){
            if (object.getCode() == 1){
                SystemMessageData.DataBean bean = object.getData();
                setCanLoadMore(bean.getHas_more() == 1);
                List<SystemMessage> messages = bean.getList();
                changeData(messages);
            }
        }
    }

    @Override
    protected void onStartDetails(int article_id) {

    }

    @Override
    String setBarTitle() {
        return "系统通知";
    }
}
