package com.yunqin.bearmall.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newversions.CardListWebActivity;
import com.newversions.IAdvClick;
import com.newreward.SpecialRequestUI.RewardDetailActivity;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.DayliTaskBCInfo;
import com.yunqin.bearmall.bean.MessageItemCount;
import com.yunqin.bearmall.eventbus.ChangeFragment;
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity2;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.PropertyActivity;
import com.yunqin.bearmall.ui.activity.SweetRecordActivity;
import com.yunqin.bearmall.ui.activity.ZeorExchangeActivity;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.DotView;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.TopBanner;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By Master
 * On 2019/5/23 16:57
 * 赚钱中心Fragment
 */
public class MakeMoneyFragment extends BaseFragment {

    @BindView(R.id.new_menu_1)
    LinearLayout menu1;
    @BindView(R.id.mine_today_bt_number)
    TextView today_tang_guo_shu;
    @BindView(R.id.shangjin_number)
    TextView today_shang_jin_shu;
    @BindView(R.id.mine_bt_number)
    TextView tang_guo_shu;
    @BindView(R.id.shangjin_qianbao)
    TextView shang_jin_shu;
    @BindView(R.id.tishi)
    TextView tishi;
    @BindView(R.id.banner_top)
    TopBanner banner;
    @BindView(R.id.sign_get_info)
    TextView tip1;
    @BindView(R.id.request_friends_reward_info)
    TextView tip2;
    @BindView(R.id.zhuan_fa_de_tg)
    TextView tip3;
    @BindView(R.id.share_zixun_get)
    TextView tip4;
    @BindView(R.id.textview_1)
    TextView textview_1;
    @BindView(R.id.image_1)
    ImageView image_1;
    @BindView(R.id.textview_2)
    TextView textview_2;
    @BindView(R.id.image_2)
    ImageView image_2;
    @BindView(R.id.textview_3)
    TextView textview_3;
    @BindView(R.id.image_3)
    ImageView image_3;
    @BindView(R.id.textview_4)
    TextView textview_4;
    @BindView(R.id.image_4)
    ImageView image_4;
    @BindView(R.id.textview_5)
    TextView textview_5;
    @BindView(R.id.image_5)
    ImageView image_5;
    @BindView(R.id.dot_view)
    DotView dot_view;

    private List<TextView> textViews;
    private List<ImageView> imageaViews;
    private String todaySum;
    private String total;

    @Override
    public int layoutId() {
        return R.layout.fragment_make_money;
    }

    @Override
    public void init() {
        textViews = new ArrayList<>();
        textViews.add(textview_1);
        textViews.add(textview_2);
        textViews.add(textview_3);
        textViews.add(textview_4);
        textViews.add(textview_5);
        imageaViews = new ArrayList<>();
        imageaViews.add(image_1);
        imageaViews.add(image_2);
        imageaViews.add(image_3);
        imageaViews.add(image_4);
        imageaViews.add(image_5);
    }

    @OnClick({
            R.id.today_bt,
            R.id.bt_wallet,
            R.id.shangjin_left,
            R.id.shangjin_right,
            R.id.new_menu_1,
            R.id.new_menu_2,
            R.id.new_menu_3,
            R.id.new_menu_4,
            R.id.mine_bottom_sign_in,
            R.id.request_friends,
            R.id.you_xi_zhuan_li_pin,
            R.id.share_zixun,
            R.id.dadachoujiang,
            R.id.da_chou_jiang,
            R.id.top_xiao_xi
    })
    public void selectView(View view) {

        if (BearMallAplication.getInstance().getUser() == null) {
            LoginActivity.starActivity(getActivity());
            return;
        }

        switch (view.getId()) {
            case R.id.top_xiao_xi:
                InformationFragmentActivity.start(getActivity());
                break;
            case R.id.da_chou_jiang:// 幸运大抽奖--> h5 广告页
                CardListWebActivity.startActivity(getActivity(), AdConstants.STRING_XING_YUN_DA_ZHUAN_PAN, "幸运大抽奖");
                break;
            case R.id.dadachoujiang:// 幸运大抽奖--> h5 广告页
                // TODO
                CardListWebActivity.startActivity(getActivity(), AdConstants.STRING_XING_YUN_DA_ZHUAN_PAN, "幸运大抽奖");
                break;
            case R.id.share_zixun:// 转发商品按钮
                EventBus.getDefault().post(new ChangeFragment(0));
                break;
            case R.id.you_xi_zhuan_li_pin:// 转发文章按钮
                EventBus.getDefault().post(new ChangeFragment(1));
                break;
            case R.id.request_friends:// 邀请好友
                StarActivityUtil.starActivity(getActivity(), InvitationActivity2.class);
                //TODO[邀请好友]
                Map<String, String> map = new HashMap<>();
                map.put("entrance_type", "赚钱中心：邀请好友赚糖果");
                ConstantScUtil.sensorsTrack("inviteClick", map);
                break;
            case R.id.mine_bottom_sign_in:// 签到按钮
                DialogUtils.signInDialog(getActivity());
                break;
            case R.id.new_menu_1:// 糖果0元兑换
                startActivity(new Intent(getActivity(), ZeorExchangeActivity.class));
                //TODO[点击0元兑]
                ConstantScUtil.exchangeClick();
                break;
            case R.id.new_menu_2:// 赏金提现
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(getActivity());
                    return;
                }
                PropertyActivity.startPropertyActivity(getActivity(), 1, null, null, null, null);
                break;
            case R.id.new_menu_3:// 邀请好友
                StarActivityUtil.starActivity(getActivity(), InvitationActivity2.class);
                //TODO[邀请好友]
                Map<String, String> map_type = new HashMap<>();
                map_type.put("entrance_type", "赚钱中心：邀请好友");
                ConstantScUtil.sensorsTrack("inviteClick", map_type);
                break;
            case R.id.new_menu_4:// 领取信用卡
//                if (BearMallAplication.getInstance().getUser() == null) {
//                    LoginActivity.starActivity(getActivity());
//                    return;
//                }
//                String token = BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token();
//                String url = BuildConfig.BASE_URL + "/view/getCreditCardBankPage?access_token=" + token;
//                CardListWebActivity.startActivity(getActivity(), url, "信用卡申请");
//                //TODO[信用卡申请]
//                ConstantScUtil.cardApply();
                CardListWebActivity.startActivity(getActivity(), AdConstants.STRING_XING_YUN_DA_ZHUAN_PAN, "幸运大抽奖");
                break;
            case R.id.shangjin_right:// 赏金钱包
                PropertyActivity.startPropertyActivity(getActivity(), 3, total, todaySum, null, null);
                break;
            case R.id.shangjin_left:// 今日获得赏金数
                RewardDetailActivity.startRewardDetailActivity(getActivity());
                break;
            case R.id.bt_wallet:// 糖果钱包
                PropertyActivity.startPropertyActivity(getActivity(), 2, total, todaySum, null, null);
                break;
            case R.id.today_bt:// 今日获得糖果数
                SweetRecordActivity.startIncomeActivity(0, null, getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BearMallAplication.getInstance().getUser() != null) {
            initDatas();
            requestData();
        }
    }

    @BindView(R.id.mine_bottom_sign_in)
    HighlightButton mine_bottom_sign_in;

    private void initDatas() {

        HashMap<String, String> mHashMap = new HashMap<>();
        mHashMap.put("positionType", "28");

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getDailyTaskAllReward(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {

                DayliTaskBCInfo dayliTaskBCInfo = new Gson().fromJson(data, DayliTaskBCInfo.class);
                DayliTaskBCInfo.DataBean.RewardDetailsBean detailsBean = dayliTaskBCInfo.getData().getRewardDetails();

                tishi.setText(String.format("今日还有%s糖果待领取，现已领取%s糖果:", detailsBean.getRestReward(), detailsBean.getTodayCreditSum()));

                tip1.setText(String.format("每日签到成功+%s糖果", detailsBean.getUsersRegisterRewardMax()));
                tip2.setText(String.format("好友注册即得%s个糖果", detailsBean.getFriendInvitReward()));
                tip3.setText(String.format("每日转发文章最高得%s个糖果", detailsBean.getMemberShareInfoReward()));
                tip4.setText(String.format("每日转发商品最高得%s个糖果", detailsBean.getMemberShareProduct()));


                today_shang_jin_shu.setText(String.valueOf(detailsBean.getTodayRewardMoney()));
                shang_jin_shu.setText(detailsBean.getBalance());
                today_tang_guo_shu.setText(detailsBean.getTodayCreditSum());
                tang_guo_shu.setText(detailsBean.getBCbanlance());


                if (dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList1().size() > 0) {
                    setAdData(dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList1().get(0), 0);
                }

                if (dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList2().size() > 0) {
                    setAdData(dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList2().get(0), 1);
                }

                if (dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList3().size() > 0) {
                    setAdData(dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList3().get(0), 2);
                }

                if (dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList4().size() > 0) {
                    setAdData(dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList4().get(0), 3);
                }

                if (dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList5().size() > 0) {
                    setAdData(dayliTaskBCInfo.getData().getAdRecord().getAdEarnMoneyList5().get(0), 4);
                }


                if (dayliTaskBCInfo.getData().getIsSignToday() == 1) {
                    mine_bottom_sign_in.setText("已签到");
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getAdMobileList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                if (bannerBean != null && bannerBean.getData() != null) {
                    List<BannerBean.DataBean.AdMobileListBean> lists1 = bannerBean.getData().getAdMobileList();

                    if (lists1 != null && lists1.size() > 0) {
                        List<String> adList = new ArrayList<>();
                        for (int i = 0; i < lists1.size(); i++) {
                            adList.add(lists1.get(i).getImg());
                        }
                        banner.setImagesUrl(adList);
                        banner.setOnItemClickListener(position -> {
                            StarActivityUtil.starActivity(getActivity(), InvitationActivity2.class);
                            //TODO[邀请好友]
                            ConstantScUtil.sensorsInviteFriends("赚钱中心：banner");
                            //TODO[banner点击]
                            ConstantScUtil.bannerClick("赚钱中心", "轮播图", lists1.get(position).getType() + "",
                                    lists1.get(position).getType() + "",
                                    lists1.get(position).getSource_id() + "", lists1.get(position).getImg(),
                                    lists1.get(position).getSkipType() + "");

                        });
                    }
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    private void setAdData(DayliTaskBCInfo.DataBean.AdRecordBean.AdEarnMoneyListBean adData, int pos) {

        textViews.get(pos).setVisibility(View.VISIBLE);
        imageaViews.get(pos).setVisibility(View.VISIBLE);

        textViews.get(pos).setText(adData.getName());
        Glide.with(this).load(adData.getImg()).into(imageaViews.get(pos));
        imageaViews.get(pos).setTag(R.id.tag_first, adData);
        imageaViews.get(pos).setOnClickListener(view -> {
            DayliTaskBCInfo.DataBean.AdRecordBean.AdEarnMoneyListBean adEarnMoneyListBean =
                    (DayliTaskBCInfo.DataBean.AdRecordBean.AdEarnMoneyListBean) view.getTag(R.id.tag_first);
            IAdvClick.click(getActivity(), adEarnMoneyListBean.getType(), adEarnMoneyListBean.getSkipType(),
                    adEarnMoneyListBean.getSource_id(), null);
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            requestData();
        }
    }

    public void requestData() {
        Map timeMap = new HashMap();
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) != 0l) {
            timeMap.put("lastTime0", (long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) != 0l) {
            timeMap.put("lastTime1", (long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) != 0l) {
            timeMap.put("lastTime2", (long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) != 0l) {
            timeMap.put("lastTime3", (long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) + "");
        }

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getUnreadMessageCount(timeMap),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) {
                        MessageItemCount messageItemCount = new Gson().fromJson(data, MessageItemCount.class);
                        int count = messageItemCount.getData().getUnreadMessageCount();
                        dot_view.setShowNum(count);
                    }

                    @Override
                    public void onNotNetWork() {

                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });
    }

}
