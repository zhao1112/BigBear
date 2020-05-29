package com.bbcoupon.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.MakeAdapter;
import com.bbcoupon.ui.bean.MakeInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.WindowUtils;
import com.bbcoupon.widget.MakeScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.newversions.CardListWebActivity;
import com.newversions.tbk.utils.BannerClicker;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.MessageItemCount;
import com.yunqin.bearmall.eventbus.ChangeFragment;
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity2;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.PropertyActivity;
import com.yunqin.bearmall.ui.activity.SearchActivity;
import com.yunqin.bearmall.ui.activity.SweetRecordActivity;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.DotView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.fragment
 * @DATE 2020/5/27
 */
public class MakeFragment extends BaseFragment implements RequestContract.RequestView, View.OnClickListener {


    @BindView(R.id.dot_view)
    DotView mDotView;
    @BindView(R.id.make_message)
    RelativeLayout mMakeMessage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;
    @BindView(R.id.ll_title_top)
    LinearLayout ll_title_top;
    @BindView(R.id.sv_content)
    MakeScrollView sv_content;
    @BindView(R.id.v_title_line_1)
    View v_title_line_1;
    @BindView(R.id.v_title_line)
    View v_title_line;
    @BindView(R.id.make_banlance)
    TextView mMakeBanlance;
    @BindView(R.id.make_CreditSum)
    TextView mMakeCreditSum;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.make_claimed)
    TextView mMakeClaimed;
    @BindView(R.id.make_receive)
    TextView mMakeReceive;
    @BindView(R.id.data_recycle)
    RecyclerView mDataRecycle;
    @BindView(R.id.make_task)
    TextView mMakeTask;
    @BindView(R.id.make_notice)
    ImageView make_notice;

    private RequestPresenter presenter;
    private RoundedCorners roundedCorners = new RoundedCorners(15);
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    private MakeAdapter makeAdapter;
    private MakeInfor.DataBean.TaskBean task;
    private List<MakeInfor.DataBean.BannerBean> bannerList;

    @Override
    public int layoutId() {
        return R.layout.fragment_make;
    }

    @Override
    public void init() {

        initView();

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        makeAdapter = new MakeAdapter(getActivity());
        mDataRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataRecycle.setNestedScrollingEnabled(false);
        mDataRecycle.setAdapter(makeAdapter);

        //点击处理
        makeAdapter.setOnMakeClick(new MakeAdapter.OnMakeClick() {
            @Override
            public void setMakeData(int i) {
                setOnMakeData(i);
            }
        });

        //banner点击
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (bannerList != null && bannerList.size() > 0) {
                    BannerClicker.bannerClick(getActivity(), bannerList.get(position).getTargetType(),
                            bannerList.get(position).getTarget(), bannerList.get(position).getTitle());
                }
            }
        });
    }

    private void setOnMakeData(int i) {
        switch (i) {
            case 0://签到
                if (task.getUsersRegisterFinish() == 0) {
                    DialogUtils.signInDialog(getActivity());
                }
                break;
            case 1://邀请好友
                StarActivityUtil.starActivity(getActivity(), InvitationActivity2.class);
                //TODO[邀请好友]
                Map<String, String> map_type = new HashMap<>();
                map_type.put("entrance_type", "赚钱中心：邀请好友");
                ConstantScUtil.sensorsTrack("inviteClick", map_type);
                break;
            case 2://转发素材
                if (task.getMemberShareInfoFinish() == 0) {
                    SharedPreferencesHelper.put(getActivity(), "SELECT_TYPE", true);
                    EventBus.getDefault().post(new ChangeFragment(1));
                }
                break;
            case 3://转发商品
                if (task.getMemberShareProductFinish() == 0) {
                    EventBus.getDefault().post(new ChangeFragment(1));
                }
                break;
            case 4://幸运大抽奖
                if (task.getLuckyDrawFinish() == 0) {
                    CardListWebActivity.startActivity(getActivity(), AdConstants.STRING_XING_YUN_DA_ZHUAN_PAN, "幸运大抽奖");
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BearMallAplication.getInstance().getUser() != null) {
            presenter.onTaskAllRewardNew(getActivity());
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
            presenter.onMessageCount(getActivity(), timeMap);
        }
    }

    private void initView() {
        ll_title.getBackground().mutate().setAlpha(0);
        // 设置状态栏高度
        int statusBarHeight = this.getResources().getDimensionPixelSize(this.getResources().getIdentifier("status_bar_height", "dimen",
                "android"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        v_title_line.setLayoutParams(params);
        v_title_line_1.setLayoutParams(params);
        // 设置滑动
        sv_content.setOnScrollistener(new MakeScrollView.OnScrollistener() {
            @Override
            public void onScroll(int startY, int endY) {
                //根据scrollview滑动更改标题栏透明度
                changeAphla(startY, endY);
            }
        });
    }

    /**
     * 根据内容窗体的移动改变标题栏背景透明度
     *
     * @param startY scrollview开始滑动的y坐标（相对值）
     * @param endY   scrollview结束滑动的y坐标（相对值）
     */
    private void changeAphla(int startY, int endY) {
        //获取标题高度
        int titleHeight = ll_title.getMeasuredHeight();
        //获取背景高度
        int backHeight = ll_title_top.getMeasuredHeight() / 2;

        //获取控件的绝对位置坐标
        int[] location = new int[2];
        ll_title_top.getLocationInWindow(location);
        //从屏幕顶部到控件顶部的坐标位置Y
        int currentY = location[1];
        //表示回到原位（滑动到顶部）
        if (currentY >= 0) {
            ll_title.getBackground().mutate().setAlpha(0);
            title.setTextColor(getResources().getColor(R.color.white));
            make_notice.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.make_notice));
            setStatusBarColor(R.color.TRANSPARENT, false);
        }

        Log.e("zpan", "=titleHeight=" + titleHeight + "=backHeight=" + backHeight + "=currentY=" + currentY);
        //颜色透明度改变
        if (currentY < titleHeight && currentY >= -(backHeight - titleHeight)) {
            //计算出滚动过程中改变的透明值
            double y = Math.abs(currentY) * 1.0;
            double height = (backHeight - titleHeight) * 1.0;
            int changeValue = (int) (y / height * 255);

            Log.e("zpan", "changeValue=" + changeValue);
            //判断是向上还是向下
            if (endY > startY) {    //向上;透明度值增加，实际透明度减小
                ll_title.getBackground().mutate().setAlpha(changeValue);
            } else if (endY < startY) {     //向下；透明度值减小，实际透明度增加
                ll_title.getBackground().mutate().setAlpha(changeValue);
            }
        }
        //红色背景移除屏幕
        if (currentY < -(backHeight - titleHeight)) {
            ll_title.getBackground().mutate().setAlpha(255);
            title.setTextColor(getResources().getColor(R.color.home_select_color));
            make_notice.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.make_b_notice));
            setStatusBarColor(R.color.TRANSPARENT, true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof MakeInfor) {
            MakeInfor makeInfor = (MakeInfor) data;
            setTobData(makeInfor.getData());
            setListData(makeInfor.getData().getDayOfTask(), makeInfor.getData().getTask());
            setTakeData(makeInfor.getData().getTask());
        }
        if (data instanceof MessageItemCount) {
            MessageItemCount messageItemCount = (MessageItemCount) data;
            mDotView.setShowNum(messageItemCount.getData().getUnreadMessageCount());
        }
        hiddenLoadingView();
    }

    //设置完成任务数量
    private void setTakeData(MakeInfor.DataBean.TaskBean task) {
        this.task = task;
        int sum = 0;
        if (task != null) {
            if (task.getUsersRegisterFinish() == 1) {
                sum = sum + 1;
            }
            if (task.getMemberShareInfoFinish() == 1) {
                sum = sum + 1;
            }
            if (task.getMemberShareProductFinish() == 1) {
                sum = sum + 1;
            }
            if (task.getLuckyDrawFinish() == 1) {
                sum = sum + 1;
            }
        }
        mMakeTask.setText(sum + "/5");
    }

    //设置基本信息
    private void setTobData(MakeInfor.DataBean data) {
        mMakeBanlance.setText(data.getBCbanlance() + "");
        mMakeCreditSum.setText(data.getTodayCreditSum() + "");
        //设置banner
        addBannerList(data.getBanner());
        //设置糖果数
        int claimed = data.getRestReward() - data.getTodayCreditSum();
        mMakeClaimed.setText(claimed + "");
        mMakeReceive.setText(data.getTodayCreditSum() + "");
    }

    //设置日常任务
    private void setListData(List<MakeInfor.DataBean.DayOfTaskBean> dayOfTask, MakeInfor.DataBean.TaskBean taskList) {
        if (dayOfTask != null && dayOfTask.size() > 0 && taskList != null) {
            makeAdapter.clearListData();
            makeAdapter.addListData(dayOfTask, taskList);
        }
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
    }

    //设置Banner
    private void addBannerList(List<MakeInfor.DataBean.BannerBean> bannerOne) {
        this.bannerList = bannerOne;
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < bannerOne.size(); i++) {
            stringList.add(bannerOne.get(i).getImage());
        }
        banner.setImages(stringList);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
    }

    @OnClick({R.id.mine_banlance, R.id.today_banlance, R.id.new_menu_1, R.id.new_menu_2, R.id.new_menu_3, R.id.new_menu_4,
            R.id.make_message, R.id.make_tip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_banlance://我的糖果
                PropertyActivity.startPropertyActivity(getActivity(), 2, "", "", null, null);
                break;
            case R.id.today_banlance://今天糖果
                SweetRecordActivity.startIncomeActivity(0, null, getActivity());
                break;
            case R.id.new_menu_1://红包0元兑
                EventBus.getDefault().post(new ChangeFragment(3));
                //TODO[点击0元兑]
                ConstantScUtil.exchangeClick();
                break;
            case R.id.new_menu_2://佣金提现
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(getActivity());
                    return;
                }
                PropertyActivity.startPropertyActivity(getActivity(), 1, null, null, null, null);
                break;
            case R.id.new_menu_3://邀请好友
                StarActivityUtil.starActivity(getActivity(), InvitationActivity2.class);
                //TODO[邀请好友]
                Map<String, String> map_type = new HashMap<>();
                map_type.put("entrance_type", "赚钱中心：邀请好友");
                ConstantScUtil.sensorsTrack("inviteClick", map_type);
                break;
            case R.id.new_menu_4://幸运大抽奖
                CardListWebActivity.startActivity(getActivity(), AdConstants.STRING_XING_YUN_DA_ZHUAN_PAN, "幸运大抽奖");
                break;
            case R.id.make_message://消息
                InformationFragmentActivity.start(getActivity());
                break;
            case R.id.make_tip://规则
                PopupWindow popupWindow = WindowUtils.ShowVirtual(getActivity(), R.layout.item_make_rule_pop, R.style.bottom_animation, 1);
                popupWindow.getContentView().findViewById(R.id.make_rule_cancel).setOnClickListener(this);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_rule_cancel:
                WindowUtils.dismissBrightness(getActivity());
                break;
        }
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load(path)
                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                    .apply(options)
                    .into(imageView);
        }
    }
}
