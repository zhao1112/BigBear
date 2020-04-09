package com.yunqin.bearmall.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

public interface Api {


    /**
     @GET("api/4/news/{data}") Observable<AllStories> getAllStories(@Path("data") String data);
     @FormUrlEncoded
     @POST("api/product/getMainCategory") Observable<Channel> getSubjectTitle(@FieldMap() Map<String, String> params);
     */

    /**
     * 获取主页 title
     *
     * @return
     */
    @POST("api/product/getMainCategory")
    Observable<String> getSubjectTitle();

    @POST("api/commodity/getNavigationBar")
    Observable<String> getSubjectTitle2();


    @POST("api/orders/validatePaymentPwdStatus")
    Observable<String> validatePaymentPwdStatus();


    /**
     * 获取图形验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/member/createValidCode")
    Observable<ResponseBody> getImageCode(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/member/memberRegister")
    Observable<String> userRegiest(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/member/forgetPassword")
    Observable<String> resetPwd(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/member/createSmsVCod")
    Observable<String> getMsgCode(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/member/memberSelectLogin")
    Observable<String> userLogin(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/commodity/fillInTheInvitationCode")
    Observable<String> fillCode(@FieldMap() Map<String, String> params);


    /**
     * 导航商品分类列表
     *
     * @return
     */
    @POST("api/product/getSecondaryCategory")
    Observable<String> getMenuData();

    /**
     * 导航商品分类列表
     *
     * @return
     */
    @POST("api/commodity/getChildCategory")
    Observable<String> getMenuDataTwo();

    /**
     * 获取商品详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/commodity/getGoodsDetails")
    Observable<String> getGoodsDetails(@FieldMap() Map<String, String> params);


    @POST("api/membercenter/getInvitationPageInfo")
    Observable<String> getInvitationPageInfo();

    //获取商品详情
    @POST("/api/product/getProductBasicInfo")
    Observable<String> getProductData(@QueryMap() Map<String, String> params);

    //商品收藏/取消收藏
    @POST("/api/product/setFavorite")
    Observable<String> setFavorite(@QueryMap() Map<String, String> params);

    //商品加入购物车
    @POST("/api/cart/joinCart")
    Observable<String> joinCart(@QueryMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/product/getProductReviewList")
    Observable<String> getProductReviewList(@FieldMap() Map<String, String> params);

    //获取购物车列表
    @POST("/api/cart/getCartItemList")
    Observable<String> getCartItemList(@QueryMap() Map<String, String> params);

    //获取购物车商品数
    @POST("/api/cart/getCartItemCount")
    Observable<String> getCartItemCount(@QueryMap() Map<String, String> params);

    //购物车商品项移入收藏
    @POST("/api/cart/removeCartItemsForFavorite")
    Observable<String> removeCartItemsForFavorite(@QueryMap() Map<String, String> params);

    //购物车商品项移除
    @POST("/api/cart/removeCartItems")
    Observable<String> removeCartItems(@QueryMap() Map<String, String> params);

    //购物车商品项数量调整
    @FormUrlEncoded
    @POST("/api/cart/setItemQuantity")
    Observable<String> setItemQuantity(@FieldMap() Map<String, String> params);

    //获取砍价免费拿商品列表
    @POST("/api/bargain/getBargainProductList")
    Observable<String> getBargainProductList(@QueryMap() Map<String, String> params);

    //获取会员砍价商品列表
    @POST("/api/bargain/getMemberBargainProductList")
    Observable<String> getMemberBargainProductList(@QueryMap() Map<String, String> params);

    //获取砍价免费拿商品详情
    @POST("/api/bargain/getBargainProductDetail")
    Observable<String> getBargainProductDetail(@QueryMap() Map<String, String> params);

    //获取当前砍价状态详情
    @POST("/api/bargain/getBargainDetails")
    Observable<String> getBargainDetails(@QueryMap() Map<String, String> params);

    //维护收货地址
    @FormUrlEncoded
    @POST("/api/bargain/addBargainReceiver")
    Observable<String> addBargainReceiver(@FieldMap() Map<String, String> params);

    //参与砍价活动
    @FormUrlEncoded
    @POST("/api/bargain/partBargain")
///api/article/partBargain
    Observable<String> partBargain(@FieldMap() Map<String, String> params);

    //提交砍价订单
    @FormUrlEncoded
    @POST("/api/article/addBargainOrders")
    Observable<String> addBargainOrders(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/product/getMenuProductList")
    Observable<String> getMenuProductList(@FieldMap() Map<String, String> params);

    /**
     * 导航商品分类店铺列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/store/getMenuStoreList")
    Observable<String> getMenuShopData(@FieldMap() Map<String, String> params);

    @POST("api/product/getHotSearchList")
    Observable<String> getHotSearchList();

    @FormUrlEncoded
    @POST("api/product/getRecommendProductList")
    Observable<String> getRecommendProductList(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/pointLog/getMemberCandiesInfo")
    Observable<String> getUserBTData(@FieldMap() Map<String, String> params);


    /**
     * 1.6.	获取店铺首页基础信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/store/getStoreBasicInfo")
    Observable<String> getShopData(@FieldMap() Map<String, String> params);

    /**
     * 1.6.	获取店铺首页基础信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/store/getStoreProduct")
    Observable<String> getShopGoodsData(@FieldMap() Map<String, String> params);


    /**
     * 收藏取消收藏
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/store/setFavorite")
    Observable<String> collectionShop(@FieldMap() Map<String, String> params);

    /**
     * 获取收益记录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/commodity/getBalance")
    Observable<String> getIncomRecord(@FieldMap() Map<String, String> params);

    /**
     * 获取商品列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/commodity/getByUrl")
    Observable<String> getGoodsList(@FieldMap() Map<String, String> params);


    /**
     * 获取淘宝跳转链接
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/commodity/getHighCommission")
    Observable<String> getHighCommission(@FieldMap() Map<String, String> params);

    /**
     * 模糊搜索
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/product/getSearchMatchList")
    Observable<String> getSearchMatchList(@FieldMap() Map<String, String> params);


    /**
     * 获取地址
     *
     * @return
     */
    @POST("api/receiver/getAreaList")
    Observable<String> getAreaList();

    /**
     * 2.9.	获取会员基础信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/member/getMemberInfo")
    Observable<String> getMemberInfo(@FieldMap() Map<String, String> params);

    /**
     * 获取用户ID
     */
    @FormUrlEncoded
    @POST("api/commodity/getUserId")
    Observable<String> getUserId(@FieldMap() Map<String, String> params);

    /**
     * 获取收货地址列表
     */
    @FormUrlEncoded
    @POST("api/receiver/getReceiverList")
    Observable<String> getReceiverList(@FieldMap() Map<String, String> params);


    /**
     * 添加收货地址
     */
    @FormUrlEncoded
    @POST("api/receiver/addReceiver")
    Observable<String> addReceiver(@FieldMap() Map<String, String> params);


    /**
     * 修改收货地址
     */
    @FormUrlEncoded
    @POST("api/receiver/editReceiver")
    Observable<String> editReceiver(@FieldMap() Map<String, String> params);

    /**
     * 删除收货地址
     */
    @FormUrlEncoded
    @POST("api/receiver/removeReceiver")
    Observable<String> removeReceiver(@FieldMap() Map<String, String> params);


    /**
     * 设置收货地址为默认
     */
    @FormUrlEncoded
    @POST("api/receiver/setDefaultReceiver")
    Observable<String> setDefaultReceiver(@FieldMap() Map<String, String> params);


    /**
     * 2.1.	获取用户绑定状态
     */
    @FormUrlEncoded
    @POST("api/member/getMemberBindStatus")
    Observable<String> getMemberBindStatus(@FieldMap() Map<String, String> params);

    /**
     * 会员手机号绑定
     */
    @FormUrlEncoded
    @POST("api/member/memberLoginBind")
    Observable<String> memberLoginBind(@FieldMap() Map<String, String> params);

    /**
     * 2.21.	获取会员赠送好友糖果记录
     */
    @FormUrlEncoded
    @POST("api/pointPresent/getPointToOther")
    Observable<String> getPointToOther(@FieldMap() Map<String, String> params);

    /**
     * 2.22.	获取会员被赠送糖果记录
     */
    @FormUrlEncoded
    @POST("api/pointPresent/receivePointToOther")
    Observable<String> receivePointToOther(@FieldMap() Map<String, String> params);


    /**
     * 2.24.	获取糖果赠送面板基础数据
     */
    @FormUrlEncoded
    @POST("api/pointPresent/getPresentPanelBasicINfo")
    Observable<String> getPresentPanelBasicINfo(@FieldMap() Map<String, String> params);

    /**
     * 2.23.	搜索赠送好友
     */
    @FormUrlEncoded
    @POST("api/pointPresent/findMemberToSend")
    Observable<String> findMemberToSend(@FieldMap() Map<String, String> params);

    /**
     * 2.25.	糖果赠送
     */
    @FormUrlEncoded
    @POST("api/pointPresent/sendPoint")
    Observable<String> sendPoint(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("/api/pointLog/getMemberIncomeDetails")
    Observable<String> getIncomeRecordList(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/pointLog/getMemberIncomeDetailsByType")
    Observable<String> getIncomeRecordListWithID(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/pointLog/getMemberPurchaseDetails")
    Observable<String> getOutcomeRecordList(@FieldMap() Map<String, String> params);

    /**
     * 解析复制的内容
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/api/commodity/findCommodityIdByTpwd1")
    Observable<String> findCommodityIdByTpwd(@FieldMap() Map<String, String> params);

    @POST("/api/pointLog/getMemberIncomeAllType")
    Observable<String> getMemberIncomeAllType();

    /**
     * 接口弃用
     */
    @FormUrlEncoded
    @POST("api/taokeorder/getTaokeOrder")
    Observable<String> getTBKOrderList(@FieldMap() Map<String, String> params);


    /**
     * 2.26.	获取用户券列表
     */
    @FormUrlEncoded
    @POST("api/usersticketlog/getMemberTicketDetails")
    Observable<String> getMemberTicketDetails(@FieldMap() Map<String, String> params);

    /**
     * 2.26.	获取用户券列表
     */
    @FormUrlEncoded
    @POST("api/usersticketlog/deleteMemberTicket")
    Observable<String> deleteMemberTicket(@FieldMap() Map<String, String> params);


    /**
     * 获取订单列表
     */
    @FormUrlEncoded
    @POST("api/orders/getOrdersList")
    Observable<String> getOrdersList(@FieldMap() Map<String, String> params);

    /**
     * 取消订单
     */
    @FormUrlEncoded
    @POST("api/orders/cancleOrders")
    Observable<String> cancleOrders(@FieldMap() Map<String, String> params);

    /**
     * 确认收货
     */
    @FormUrlEncoded
    @POST("api/orders/confirmOrders")
    Observable<String> confirmOrders(@FieldMap() Map<String, String> params);

    /**
     * 删除订单
     */
    @FormUrlEncoded
    @POST("api/orders/deleteOrders")
    Observable<String> deleteOrders(@FieldMap() Map<String, String> params);


    /**
     * 1.21.	获得用户收藏商品列表
     */
    @FormUrlEncoded
    @POST("api/product/getProductFavoriteList")
    Observable<String> getProductFavoriteList(@FieldMap() Map<String, String> params);

    /**
     * 1.20.	获得用户收藏店铺列表
     */
    @FormUrlEncoded
    @POST("api/store/getStoreFavoriteList")
    Observable<String> getStoreFavoriteList(@FieldMap() Map<String, String> params);

    /**
     * 1.20.	获得tbk收藏店铺列表
     */
    @FormUrlEncoded
    @POST("api/commodity/getTaobaoCommodityCollection")
    Observable<String> getTBKCollection(@FieldMap() Map<String, String> params);


    /**
     * 上传图片
     */
    @Multipart
    @POST("api/basic/uploadImgMultiFormat")
    Observable<String> uploadImgMultiFormat(@Part("access_token") String token, @PartMap Map<String, RequestBody> multipartBodyMap);

    /**
     * 2.28.	获取签到面板信息
     */
    @POST("api/usersSignIn/getSignCandiesRewardInfo")
    Observable<String> getSignCandiesRewardInfo();

    @POST("api/tiku/initTikuInfo")
    Observable<String> getInitTikuInfo();


    @FormUrlEncoded
    @POST("api/tiku/validateAndNextTi")
    Observable<String> getNextTi(@FieldMap() Map<String, String> params);

    /**
     * 2.29.	会员签到
     */
    @POST("api/usersSignIn/MemberRegister")
    Observable<String> MemberRegister();

    /**
     * 生成邀请海报
     */
    @POST("api/member/createInviteFriendsImage")
    Observable<String> createInviteFriendsImage();


    /**
     * 获取订单详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/orders/getOrderDetails")
    Observable<String> getOrderDetails(@FieldMap() Map<String, String> params);


    /**
     * 2.31.	获取会员邀请详情
     */
    @POST("api/member/getInviteDetails")
    Observable<String> getInviteDetails();

    /**
     * 1.34.	获取广告位广告列表
     */
    @FormUrlEncoded
    @POST("api/admobile/getAdMobileList")
///api/admobile/getAdMobileList
    Observable<String> getAdMobileList(@FieldMap() Map<String, String> params);

    /**
     * 1.29.	获取会员订单项待评价列表
     */
    @FormUrlEncoded
    @POST("api/review/getMemberToBeReviewList")
    Observable<String> getReviewBasicInfo(@FieldMap() Map<String, String> params);

    /**
     * 1.28.	获取会员已评价列表
     */
    @FormUrlEncoded
    @POST("api/review/getMemberReviewList")
    Observable<String> getMemberReviewList(@FieldMap() Map<String, String> params);


    /**
     * 获取售后列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/aftersales/getAfterSalesList")
    Observable<String> getAfterSalesList(@FieldMap() Map<String, String> params);


    /**
     * 申请售后服务界面
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/aftersales/getAfterSalesMain")
    Observable<String> getAfterSalesMain(@FieldMap() Map<String, String> params);


    /**
     * 获取弹窗数据
     */
    @FormUrlEncoded
    @POST("api/aftersales/getApplyReason")
    Observable<String> getApplyReason(@FieldMap() Map<String, String> params);

    /**
     * 提交售后
     */
    @FormUrlEncoded
    @POST("api/aftersales/applyAfterSales")
    Observable<String> applyAfterSales(@FieldMap() Map<String, String> params);

    /**
     * 售后详情
     */
    @FormUrlEncoded
    @POST("api/aftersales/getAfterSalesDetails")
    Observable<String> getAfterSalesDetails(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/guidearticle/getGuideArticleList")
    Observable<String> getArticleList(@FieldMap() Map<String, String> params);


    /**
     * 获取首页数据
     */
    @FormUrlEncoded
    @POST("api/home/getHomeListData")
    Observable<String> getHomeListData(@FieldMap() Map<String, String> params);


    /**
     * 淘宝客首页数据
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/commodity/getCommodityCategory")
    Observable<String> getTBKHomeListData(@FieldMap() Map<String, String> params);


    /**
     * 修改收藏列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/commodity/changeCollection")
    Observable<String> changeCollection(@FieldMap() Map<String, String> params);

    /**
     * 获取分享信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/commodity/getShareMsg")
    Observable<String> getShareMsg(@FieldMap() Map<String, String> params);

    /**
     * 获取淘客推荐商品
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/commodity/getRecommended")
    Observable<String> getTBKHomeGoodsListData(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/membermall/getMemberMallIndexData")
    Observable<String> getMemberMallIndexData(@FieldMap() Map<String, String> params);


    /**
     * 获取首页广告
     */
    @POST("api/home/getHomeAdData")
    Observable<String> getHomeAdData();

    /**
     * 获取默认地址
     */
    @POST("api/receiver/getDefaultReceiver")
    Observable<String> getDefaultReceiver();


    /**
     * 获取快递信息
     */
    @POST("api/shippingmethod/getShippingMethodList")
    Observable<String> getShippingMethodList();

    /**
     * 获取快递信息
     */
    @FormUrlEncoded
    @POST("api/areafreight/getAreaFreight")
    Observable<String> getAreaFreight(@FieldMap() Map<String, String> params);

    /**
     * 下单
     */
    @FormUrlEncoded
    @POST("api/orders/unifiedOrder")
    Observable<String> unifiedOrder(@FieldMap() Map<String, String> params);

    /**
     * 获取支付方式
     */
    @POST("api/payment/getPaymentMethod")
    Observable<String> getPaymentMethod();

    /**
     * 获取支付方式
     */
    @FormUrlEncoded
    @POST("api/orders/getPayParams")
    Observable<String> getPayParams(@FieldMap() Map<String, String> params);


    /**
     * 2.37.	获取拼团首页
     */
    @FormUrlEncoded
    @POST("api/grouppurchasing/getGrouppurchasingIndex")
    Observable<String> getGrouppurchasingIndex(@FieldMap() Map<String, String> params);

    /**
     * 查询订单状态
     */
    @FormUrlEncoded
    @POST("api/orders/queryOrderPayResult")
    Observable<String> queryOrderPayResult(@FieldMap() Map<String, String> params);

    /**
     * 2.39.	获取拼团详情
     */
    @FormUrlEncoded
    @POST("api/grouppurchasing/getGrouppurchasingItemDetails")
    Observable<String> getGrouppurchasingItemDetails(@FieldMap() Map<String, String> params);


    /**
     * 查询订单状态
     */
    @FormUrlEncoded
    @POST("api/cart/joinCartFromOrders")
    Observable<String> joinCartFromOrders(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/treasure/getTreasureBasicInfo")
    Observable<String> getTreasureBasicInfo(@FieldMap() Map<String, String> params);

    //2.41.	参与拼团
    @FormUrlEncoded
    @POST("api/grouppurchasing/partGrouppurchasing")
    Observable<String> partGrouppurchasing(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/treasure/getMemberTreasureInfo")
    Observable<String> getMemberTreasureInfo(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/treasure/getTreasureInfo")
    Observable<String> getTreasureInfo(@FieldMap() Map<String, String> params);

    /**
     * 2.40.	获取我的拼团记录
     */
    @FormUrlEncoded
    @POST("api/grouppurchasing/getMemberGrouppurchasingItemList")
    Observable<String> getMemberGrouppurchasingItemList(@FieldMap() Map<String, String> params);


    /**
     * 版本升级
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/appversion/checkForUpdate")
    Observable<String> checkForUpdate(@FieldMap() Map<String, String> params);

    /**
     * 1.53.	获取往期活动列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/article/getPastActivityList")
    Observable<String> getPastActivityList(@FieldMap() Map<String, String> params);


    /**
     * 1.45.	获取店铺首页活动列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/store/getStoreActivityList")
    Observable<String> getStoreActivityList(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/treasure/partTreasure")
    Observable<String> partTreasure(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/review/getReviewBasicInfo")
    Observable<String> getReviewBasicInfos(@FieldMap() Map<String, String> params);


    //1.57.	更新用户个推CID
    @FormUrlEncoded
    @POST("api/getui/updateGeTuiInfo")
    Observable<String> updateGeTuiInfo(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/pushmessage/getPushMessageList")
    Observable<String> getPushMessageList(@FieldMap() Map<String, String> params);


    /**
     * 2.44.	每日任务奖励信息
     *
     * @return
     */
    @POST("api/dailytask/getDailyTaskAllReward")
    Observable<String> getDailyTaskAllReward();

    /**
     * 2.45.	获取会员每日任务进度详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/dailytask/getDailyTaskInfo")
    Observable<String> getDailyTaskInfo(@FieldMap() Map<String, String> params);

    @POST("api/treasure/getTreasureTagInfo")
    Observable<String> getTreasureTagInfo();

    //1.59.	获取消息主页面最新消息数据
    @FormUrlEncoded
    @POST("api/pushmessage/getMainPushMessageInfo")
    Observable<String> getMainPushMessageInfo(@FieldMap() Map<String, String> params);

    //1.60.	获取未读消息总数量
    @FormUrlEncoded
    @POST("api/pushmessage/getUnreadMessageCount")
    Observable<String> getUnreadMessageCount(@FieldMap() Map<String, String> params);

    //1.62.	获取设置用户面板信息
    @FormUrlEncoded
    @POST("api/member/getSettingMemberInfo")
    Observable<String> getSettingMemberInfo(@FieldMap() Map<String, String> params);

    //1.63.	更新用户昵称
    @FormUrlEncoded
    @POST("api/member/updateMemberNickName")
    Observable<String> updateMemberNickName(@FieldMap() Map<String, String> params);

    //1.56.	APP设置一键反馈
    @FormUrlEncoded
    @POST("api/appsetting/feedback")
    Observable<String> feedback(@FieldMap() Map<String, String> params);

    //2.10.	会员第三方绑定
    @FormUrlEncoded
    @POST("api/member/memberThirdPartyBind")
    Observable<String> memberThirdPartyBind(@FieldMap() Map<String, String> params);//2.10.	会员第三方绑定

    //手机号绑定微信
    @FormUrlEncoded
    @POST("api/member/memberWeixinBind")
    Observable<String> memberWeixinBind(@FieldMap() Map<String, String> params);

    //2.5.	会员登出
    @FormUrlEncoded
    @POST("api/member/memberLoginOut")
    Observable<String> memberLoginOut(@FieldMap() Map<String, String> params);

    //4.2.	获取导购文章分享参数
    @FormUrlEncoded
    @POST("api/share/getShareParams")
    Observable<String> getShareParams(@FieldMap() Map<String, String> params);

    //53.	获取分享数据
    @FormUrlEncoded
    @POST("/api/bargain/getShareDetails")
    Observable<String> getShareDetails(@FieldMap() Map<String, String> params);

    //54、分享给别人
    @FormUrlEncoded
    @POST("/api/bargain/shareToOthers")
    Observable<String> shareToOthers(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/orders/getLogisticsList")
    Observable<String> getLogisticsList(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/grouppurchasing/getPastGrouppurchasingDetails")
    Observable<String> getPastGrouppurchasingDetails(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/treasure/getPastTreasureDetails")
    Observable<String> getPastTreasureDetails(@FieldMap() Map<String, String> params);


    //4.2.	获取导购文章分享参数
    @FormUrlEncoded
    @POST("api/share/callBackForShare")
    Observable<String> callBackForShare(@FieldMap() Map<String, String> params);

    //4.5.	获取往期成功砍价记录
    @FormUrlEncoded
    @POST("api/bargain/getPastBargainList")
    Observable<String> getPastBargainList(@FieldMap() Map<String, String> params);

    //4.5.	获取往期成功砍价记录
    @FormUrlEncoded
    @POST("api/bargain/getHelpOthersBargainInfo")
    Observable<String> getHelpOthersBargainInfo(@FieldMap() Map<String, String> params);

    //4.3.	帮好友砍价
    @FormUrlEncoded
    @POST("api/bargain/helpOthersBargain")
    Observable<String> helpOthersBargain(@FieldMap() Map<String, String> params);


    //3.7.	获取订单各状态的数量
    @FormUrlEncoded
    @POST("api/orders/getOrdersQuantity")
    Observable<String> getOrdersQuantity(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/basic/idCardModify")
    Observable<String> idCardModify(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/orders/isCrossBorder")
    Observable<String> isCrossBorder(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/orders/ordersBcPay")
    Observable<String> ordersBcPay(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/product/getProductQrInfoForShare")
    Observable<String> getProductQrInfoForShare(@FieldMap() Map<String, String> params);


    @POST("api/basic/getSysTimestampInfo")
    Observable<String> getSysTimestampInfo();

    @POST("api/helpcenter/getQuestionList")
    Observable<String> getQuestionList();


    @FormUrlEncoded
    @POST("api/auth/token/refresh")
    Observable<String> refreshToken(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/discountcampaign/getDiscountProductList")
    Observable<String> getDiscountProductList(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/member/setPayPassword")
    Observable<String> setPayPassword(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/member/validPayPassword")
    Observable<String> validPayPassword(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/member/getMemberBalanceDatils")
    Observable<String> getMemberBalanceDatils(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/member/updatePayPassword")
    Observable<String> updatePayPassword(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/member/validSmsCode")
    Observable<String> validSmsCode(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/membercenter/getMemberM1List")
    Observable<String> getMemberM1List(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/virtualrechargeproudct/getVirtualRechargeInfo")
    Observable<String> getVirtualRechargeInfo(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/membercenter/getMemberM2List")
    Observable<String> getMemberM2List(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/virtualproductorders/calculateVirtualOrderAmount")
    Observable<String> calculateVirtualOrderAmount(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/virtualproductorders/virtualRechargeOrder")
    Observable<String> virtualRechargeOrder(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/orders/calculateOrderAmount")
    Observable<String> calculateOrderAmount(@FieldMap() Map<String, String> params);

    //    6.23.	获取特价邀约页面数据
    //    请求URL： /api/membercenter/getSpecInvitationPageInfo
    @POST("api/membercenter/getSpecInvitationPageInfo")
    Observable<String> getSpecInvitationPageInfo();


    @POST("api/dailytask/getLuckyDrawInfo")
    Observable<String> getLuckyDrawInfo();

    @POST("api/dailytask/partLuckyDraw")
    Observable<String> partLuckyDraw();

    @FormUrlEncoded
    @POST("api/dailytask/acceptReward")
    Observable<String> acceptReward(@FieldMap() Map<String, String> params);


    //6.24.	获取会员特价邀约记录
    //    请求URL： /api/membercenter/getSpecInvitationRecord
    @POST("api/membercenter/getSpecInvitationRecord")
    Observable<String> getSpecInvitationRecord();


    @FormUrlEncoded
    @POST("api/membercenter/getOpenMemberPageInfo")
    Observable<String> getOpenMemberPageInfo(@FieldMap() Map<String, String> params);


    @POST("api/member/getMemberUnusedPrivilege")
    Observable<String> getMemberUnusedPrivilege();

    @FormUrlEncoded
    @POST("api/orders/balancePayment")
    Observable<String> balancePayment(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/orders/getVirtualOrderDetails")
    Observable<String> getVirtualOrderDetails(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/membercenter/getRenewMemberPageInfo")
    Observable<String> getRenewMemberPageInfo(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/membercenter/openMemberOrder")
    Observable<String> openMemberOrder(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/membercenter/getSpecOpenMemberPageInfo")
    Observable<String> getSpecOpenMemberPageInfo(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/member/getMemberTodayRewardDatils")
    Observable<String> getMemberTodayRewardDatils(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/userBank/userBankVaild")
    Observable<String> userBankVaild(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/userBank/createSmsCodeForVerifyBank")
    Observable<String> createSmsCodeForVerifyBank(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/userBank/validSmsCode")
    Observable<String> userBankvalidSmsCode(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/userBank/unbindUserBankCard")
    Observable<String> unbindUserBankCard(@FieldMap() Map<String, String> params);

    @POST("api/userBank/isBindBankCard")
    Observable<String> isBindBankCard();

    @POST("api/userBank/getUsersBankInfo")
    Observable<String> getUsersBankInfo();


    @FormUrlEncoded
    @POST("api/withdraw/calculateWithdrawAmount")
    Observable<String> calculateWithdrawAmount(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("api/withdraw/applyWithdraw")
    Observable<String> applyWithdraw(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/orders/unionPaymentPwdValid")
    Observable<String> unionPaymentPwdValid(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/withdraw/getMemberWithdrawDetails")
    Observable<String> getMemberWithdrawDetails(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/popupad/getPopupAdInfo")
    Observable<String> getPopupAdInfo(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/usercreditcard/getUserCreditCardRecordList")
    Observable<String> getUserCreditCardRecordList(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/loansupermarket/getLoanSupermarketData")
    Observable<String> getLoanSupermarketData(@FieldMap() Map<String, String> params);

    /**
     * 生成多张邀请海报
     */
    @POST("api/member/createInviteFriendsImageTwo")
    Observable<String> createManyInviteImage();


    /**
     * 收集初始化信息
     */
    @FormUrlEncoded
    @POST("api/basic/init")
    Observable<String> getInitMessage(@FieldMap() Map<String, String> params);

    /***
     * 判断是否可以0元兑
     * */
    @POST("api/taokeorder/checkzero")
    Observable<String> getCheckzero();

    /**
     * 会员新人0元购
     */
    @FormUrlEncoded
    @POST("api/member/updateFreeInfo")
    Observable<String> getUpdateFreeInfo(@FieldMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("api/taokeorder/clickurl2")
    Observable<String> clickurl(@FieldMap() Map<String, String> params);


    @FormUrlEncoded
    @POST("mtop.taobao.detail.getdesc/6.0")
    Observable<String> contenGoods(@FieldMap() Map<String, String> params);

    /**
     * 获取外网ip
     */
    @POST("cityjson")
    Observable<String> getip();

    /**
     * 0元购邀请福利
     */
    @POST("api/taokeorder/checkinvitation")
    Observable<String> getCheckinvitation();

    /**
     * 我的页面收益
     */
    @POST("api/commodity/getProfit")
    Observable<String> getProfit();

    /**
     * 我的页面  轮播图
     */
    @POST(" api/commodity/getLunboTu")
    Observable<String> getLunboTu();

    /**
     * 淘宝订单列表
     */
    @FormUrlEncoded
    @POST("api/taokeorder/getTaoOrderList")
    Observable<String> TaoOrderList(@FieldMap() Map<String, String> params);

    /**
     * 一级粉丝
     */
    @FormUrlEncoded
    @POST("api/userinfo/getUserStairFans")
    Observable<String> StairFans(@FieldMap() Map<String, String> params);

    /**
     * 收益记录
     */
    @POST("api/commodity/getIncomeRecord")
    Observable<String> IncomeRecord();


    /**
     * 推荐收益
     */
    @POST("api/commodity/getRecommendEarnings")
    Observable<String> MonthProfiteDetailed();

    /**
     * 分享收益
     */
    @POST("api/commodity/getSinceThePurchaseYield")
    Observable<String> DayProfiteDetailed();

    /**
     * 二级粉丝
     */
    @FormUrlEncoded
    @POST("api/userinfo/getUserSecondFans")
    Observable<String> SecondFans(@FieldMap() Map<String, String> params);

    /**
     * 粉丝详情
     */
    @FormUrlEncoded
    @POST("api/userinfo/getFansInfo")
    Observable<String> FansInfo(@FieldMap() Map<String, String> params);

    /**
     * vip升级条件
     */
    @POST("api/userinfo/userPromotion")
    Observable<String> getUserPromotion();

    /**
     * vip升级
     */
    @FormUrlEncoded
    @POST("api/userinfo/gradeDeduction")
    Observable<String> gradeDeduction(@FieldMap() Map<String, String> params);


    /**
     * 合伙人订单
     */
    @FormUrlEncoded
    @POST("api/partner/screenOrders")
    Observable<String> screenOrders(@FieldMap() Map<String, String> params);

    /**
     * 合伙人订单搜索
     */
    @FormUrlEncoded
    @POST("api/partner/searchOrders")
    Observable<String> partenerScreenOrders(@FieldMap() Map<String, String> params);

    /**
     * 合伙人粉丝
     */
    @FormUrlEncoded
    @POST("api/partner/screenFans")
    Observable<String> screenFans(@FieldMap() Map<String, String> params);

    /**
     * 合伙人粉丝总人数
     */
    @FormUrlEncoded
    @POST("api/partner/partnerFansCount")
    Observable<String> partnerFansCount(@FieldMap() Map<String, String> params);

    /**
     * 合伙人搜索粉丝
     */
    @FormUrlEncoded
    @POST("api/partner/searchFans")
    Observable<String> searchFans(@FieldMap() Map<String, String> params);

    /**
     * 合伙人任命次数
     */
    @FormUrlEncoded
    @POST("/api/partner/getAppointNum")
    Observable<String> getAppointNum(@FieldMap() Map<String, String> params);

    /**
     * 提升团长
     */
    @FormUrlEncoded
    @POST("api/partner/appointBigLeader")
    Observable<String> appointBigLeader(@FieldMap() Map<String, String> params);

    /**
     * 搜索列表
     */
    @FormUrlEncoded
    @POST("api/userinfo/getKeywordSearch")
    Observable<String> KeywordSearch(@FieldMap() Map<String, String> params);


    /**
     * 首頁新接口
     */
    @FormUrlEncoded
    @POST("api/commodity/getNewCommodityCategory")
    Observable<String> getNewCommodityCategory(@FieldMap() Map<String, String> params);

    /**
     * 搜索页轮播图
     */
    @FormUrlEncoded
    @POST("api/commodity/getSearchLunboTu")
    Observable<String> getSearchLunboTu(@FieldMap() Map<String, String> params);


    /**
     * 热销榜单
     */
    @FormUrlEncoded
    @POST("api/commodity/getHotSelling")
    Observable<String> getHotSelling(@FieldMap() Map<String, String> params);


    /**
     * 绑定微信
     */
    @FormUrlEncoded
    @POST("api/userinfo/uploadWxInfo")
    Observable<String> uploadWxInfo(@FieldMap() Map<String, String> params);


    /**
     * 粉丝接口
     */
    @FormUrlEncoded
    @POST("api/userinfo/getUserAllFans")
    Observable<String> getUserAllFans(@FieldMap() Map<String, String> params);

    /**
     * 拼多多订单
     */
    @FormUrlEncoded
    @POST("api/taokeorder/getPddOrderList")
    Observable<String> getPddOrderList(@FieldMap() Map<String, String> params);

    /**
     * 获取商学院分类
     */
    @FormUrlEncoded
    @POST("api/businesscollege/getBusinessCategory")
    Observable<String> getBusinessCategory(@FieldMap() Map<String, String> params);

    /**
     * 获取大熊爆款数据
     */
    @FormUrlEncoded
    @POST("api/businesscollege/getBusinessProduct")
    Observable<String> getBusinessProduct(@FieldMap() Map<String, String> params);

    /**
     * 增加分享数
     */
    @FormUrlEncoded
    @POST("api/businesscollege/BusinessShare")
    Observable<String> BusinessShare(@FieldMap() Map<String, String> params);

    /**
     * 获取宣传素材数据
     */
    @FormUrlEncoded
    @POST("api/businesscollege/getBusinessMaterial")
    Observable<String> getBusinessMaterial(@FieldMap() Map<String, String> params);


    /**
     * 绑定渠道关系ID
     */
    @FormUrlEncoded
    @POST("api/member/updateRid")
    Observable<String> updateRid(@FieldMap() Map<String, String> params);


    /**
     * 聚划算
     */
    @FormUrlEncoded
    @POST("api/commodity/getSecondHotSelling")
    Observable<String> getSecondHotSelling(@FieldMap() Map<String, String> params);

    /**
     * 搜索粉丝
     */
    @FormUrlEncoded
    @POST("api/userinfo/searchFansByMobile")
    Observable<String> searchFansByMobile(@FieldMap() Map<String, String> params);
}

