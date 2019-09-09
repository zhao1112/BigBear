package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * 生成多张邀请图片
 *
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2019/8/30
 */
public class Invitationinfo {

    /**
     * msg : 请求成功
     * code : 1
     * data : ["https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/invitefriendsimage/20190829_AS34FDF_1.jpg",
     * "https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/invitefriendsimage/20190829_AS34FDF_2.jpg",
     * "https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/invitefriendsimage/20190829_AS34FDF_3.jpg",
     * "https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/invitefriendsimage/20190829_AS34FDF_4.jpg",
     * "https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/invitefriendsimage/20190829_AS34FDF_5.jpg"]
     */

    private String msg;
    private int code;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
