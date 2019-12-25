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
import com.newversions.InviteFriendActivity;
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
import com.yunqin.bearmall.eventbus.ChangeFragment;
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity2;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.PropertyActivity;
import com.yunqin.bearmall.ui.activity.SweetRecordActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyActivity;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.TopBanner;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By Master
 * On 2019/5/23 16:57
 * 赚钱中心Fragment
 */
public class MakeMoneyFragment1 extends BaseFragment {


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


    private List<TextView> textViews;
    private List<ImageView> imageaViews;


    private String todaySum;
    private String total;


    @Override
    public int layoutId() {
        return R.layout.fragment_make_money1;
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
            R.id.top_xiao_xi,
            R.id.toolbar_back
    })
    public void selectView(View view) {

        switch (view.getId()) {

            case R.id.toolbar_back:
                getActivity().finish();
                break;


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

                getActivity().finish();

                EventBus.getDefault().post(new ChangeFragment(0));
                break;
            case R.id.you_xi_zhuan_li_pin:// 转发文章按钮

                getActivity().finish();

                EventBus.getDefault().post(new ChangeFragment(1));
                break;
            case R.id.request_friends:// 邀请好友
//                VipCenterActivity.startVipCenterActivity(getActivity(), "", "");

                StarActivityUtil.starActivity(getActivity(), InvitationActivity.class);

                break;
            case R.id.mine_bottom_sign_in:// 签到按钮
                DialogUtils.signInDialog(getActivity());
                break;
            case R.id.new_menu_1:// 糖果0元兑换
                startActivity(new Intent(getActivity(), ZeroMoneyActivity.class));
                break;
            case R.id.new_menu_2:// 赏金提现

                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(getActivity());
                    return;
                }

                PropertyActivity.startPropertyActivity(getActivity(), 1, null, null, null, null);

                break;
            case R.id.new_menu_3:// 邀请好友
//                VipCenterActivity.startVipCenterActivity(getActivity(), "", "");

                InviteFriendActivity.startActivity(getActivity());

                break;
            case R.id.new_menu_4:// 领取信用卡

//                if (BearMallAplication.getInstance().getUser() == null) {
//                    LoginActivity.starActivity(getActivity());
//                    return;
//                }
//
//                String token = BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token();
//                String url = BuildConfig.BASE_URL + "/view/getCreditCardBankPage?access_token=" + token;
//
//                CardListWebActivity.startActivity(getActivity(), url, "信用卡申请");

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


                            InviteFriendActivity.startActivity(getActivity());

//                            VipCenterActivity.startVipCenterActivity(getActivity(), "", "");

//                            BannerBean.DataBean.AdMobileListBean adBean = lists1.get(position);
//                            int type = adBean.getType();
//                            int skipType = adBean.getSkipType();
//                            long sourceId = adBean.getSource_id();
//                            IAdvClick.click(getActivity(), type, skipType, sourceId);
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
            IAdvClick.click(getActivity(), adEarnMoneyListBean.getType(), adEarnMoneyListBean.getSkipType(), adEarnMoneyListBean.getSource_id(),null);
        });


    }


}
