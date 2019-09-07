package com.yunqin.bearmall.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.SnatchContent;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.bean.TreasureInfo;
import com.yunqin.bearmall.eventbus.PartSnatchEvent;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.inter.ProductInstructionCallBack;
import com.yunqin.bearmall.inter.ScrollViewForActivityListener;
import com.yunqin.bearmall.inter.ScrollViewListener;
import com.yunqin.bearmall.ui.activity.SnatchDetailActivity;
import com.yunqin.bearmall.ui.fragment.contract.SnatchDetailContract;
import com.yunqin.bearmall.ui.fragment.presenter.SnatchDetailPresenter;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.DeficitScrollView;
import com.yunqin.bearmall.widget.TipView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by chenchen on 2018/8/8.
 */

public class SnatchDetailFragment extends BaseFragment implements SnatchDetailContract.IView,ScrollViewListener{

    public static SnatchDetailFragment  instance(Treasure treasure){

        SnatchDetailFragment fragment = new SnatchDetailFragment();

        Bundle bundle = new Bundle();

        bundle.putParcelable("OBJECT",treasure);

        fragment.setArguments(bundle);

        return fragment;
    }

    @BindView(R.id.scrollView)
    DeficitScrollView deficitScrollView;
    @BindView(R.id.top_image)
    ImageView topImage;
    @BindView(R.id.join_num)
    TextView joinNumView;
    @BindView(R.id.snatch_timer_view)
    CountdownView timeView;
    @BindView(R.id.goods_name)
    TextView goodsNameView;
    @BindView(R.id.product_service_instruction)
    LinearLayout serviceLayout;
    @BindView(R.id.zero_rule_instruction)
    LinearLayout ruleLayout;
    @BindView(R.id.product_price_instruction)
    LinearLayout priceLayout;
    @BindView(R.id.center_text)
    TextView centerText;
    @BindView(R.id.snatch_head)
    ConstraintLayout headLayout;
    @BindView(R.id.custom_recommend_view)
    CustomRecommendView customRecommendView;
    @BindView(R.id.text_view)
    TextView statusView;

    @BindView(R.id.rolling_contianer)
    ConstraintLayout constraintLayout;
    @BindView(R.id.rolling_text)
    TipView tipView;

    private SnatchDetailPresenter presenter;
    private ScrollViewForActivityListener mScrollViewForActivityListener;
    private ProductInstructionCallBack productInstructionCallBack;

    @Override
    public int layoutId() {
        return R.layout.fragment_snatch_degtail;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductInstructionCallBack){
            this.productInstructionCallBack = (ProductInstructionCallBack) context;
        }
        if (context instanceof ScrollViewForActivityListener){
            mScrollViewForActivityListener = (ScrollViewForActivityListener) context;
        }
    }

    @Override
    public void init() {

        constraintLayout.setVisibility(View.GONE);

        presenter = new SnatchDetailPresenter(this);
        presenter.start(getContext());

        Bundle bundle = getArguments();
        if (bundle != null){
            Treasure treasure = bundle.getParcelable("OBJECT");
            if (treasure != null){
                setupTrasureData(treasure);
                presenter.loadData(treasure.getTag()+"",treasure.getTreasure_id(),treasure.getIsToday());
            }
        }

        centerText.setText("糖果夺宝");
        deficitScrollView.setScrollViewListener(this);
        headLayout.setFocusable(true);
        headLayout.setFocusableInTouchMode(true);
        headLayout.requestFocus();

        customRecommendView.setTvBottom("推荐商品");
        customRecommendView.setDiviervisibility(View.VISIBLE);
        customRecommendView.setTopTextViewLeft();
        customRecommendView.setTopTextViewBgColor(getActivity().getResources().getColor(R.color.white));
        customRecommendView.setTopTextViewHeight(getActivity().getResources().getDimension(R.dimen.ds94));
        customRecommendView.hideTopLayout();

        customRecommendView.setManager(new GridLayoutManager(getActivity(),2));
        customRecommendView.start(getActivity());

    }

    private void setupTrasureData(Treasure treasure) {

        timeView.stop();
        int status = treasure.getStatus();
        if (status == 2){
            statusView.setText("距本场开始");
        }else if (status == 1){
            statusView.setText("距本场结束");
        }else {
            statusView.setText("本场已结束");
            timeView.setVisibility(View.GONE);
        }

        try{
            Glide.with(getContext()).load(treasure.getImg()).into(topImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        joinNumView.setText(String.format("已有%d人次参加",treasure.getParticipationPerson()));
        goodsNameView.setText(treasure.getTreasureName());

        long  resetTime = treasure.getRestTime();
        timeView.start(resetTime);


    }

    @OnClick({R.id.product_service_instruction,R.id.zero_rule_instruction,R.id.product_price_instruction})
    protected void onViewClicked(View view){

        switch (view.getId()){

            case R.id.product_service_instruction:

                if(productInstructionCallBack != null){
                    productInstructionCallBack.showInstructionDialog(0);
                }

                break;

            case R.id.zero_rule_instruction:
                if(productInstructionCallBack != null){
                    productInstructionCallBack.showInstructionDialog(1);
                }
                break;

            case R.id.product_price_instruction:
                if(productInstructionCallBack != null){
                    productInstructionCallBack.showInstructionDialog(3);
                }
                break;

        }

    }

    @Override
    public void onLoadedData(TreasureInfo data) {

        if (data.getCode() == 1){
            TreasureInfo.DataBean bean = data.getData();
            Treasure treasure = bean.getTreasure();
            setupTrasureData(treasure);

           SnatchDetailActivity activity = (SnatchDetailActivity) getActivity();
           activity.updateNetData(treasure);

           List<TreasureInfo.RewardMember> members = bean.getRewardMember();
           if (members != null && !members.isEmpty()){
               constraintLayout.setVisibility(View.VISIBLE);
               List<String> tips = new ArrayList<>();
               for (int i = 0; i < members.size(); i++) {
                   TreasureInfo.RewardMember member = members.get(i);
                   tips.add(String.format("恭喜%s获得%s",member.getNickName(),member.getTreasureName()));
               }
               tipView.setTipList(tips);
           }

            if (treasure.getIsBuy() == 1){
                DialogUtils.tipSearchDialog(getActivity(), 100, "", new JoinZeroCallBack() {
                    @Override
                    public void sureBtn(int flag) {

                        EventBus.getDefault().post(new PartSnatchEvent(1,false));
                        getActivity().finish();

                    }

                    @Override
                    public void canle() {

                    }
                });
            }


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onScrollChanged(DeficitScrollView scrollView, int x, int y, int oldx, int oldy) {

        headLayout.scrollTo(x, -y / 2);
        if (mScrollViewForActivityListener != null) {
            mScrollViewForActivityListener.onScrollChanged(scrollView, x, y, oldx, oldy);//把frament中scrollview的滚动监听的数据返回给activity，来处理头部区域的透明度
        }
    }
}
