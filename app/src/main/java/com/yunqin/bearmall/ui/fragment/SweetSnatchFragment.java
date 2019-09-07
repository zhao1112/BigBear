package com.yunqin.bearmall.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.newversions.IAdvClick;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.SweetSnatchAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.TreasureData;
import com.yunqin.bearmall.ui.activity.DailyTasksActivity;
import com.yunqin.bearmall.ui.fragment.contract.SweetSnatchContract;
import com.yunqin.bearmall.ui.fragment.presenter.SweetSnatchPresnet;
import com.yunqin.bearmall.widget.TopBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenchen on 2018/8/4.
 */

public class SweetSnatchFragment extends BaseFragment implements SweetSnatchContract.IView{

    @BindView(R.id.viewpager)
    ViewPager pager;

    @BindView(R.id.jump_dalitask)
    View jump_dalitask;

    @BindView(R.id.xtablelayout)
    XTabLayout xtablelayout;

    @BindView(R.id.top)
    TopBanner top;

    private SweetSnatchAdapter adapter;

    private ViewHolder holder;
    private List<TreasureData.TreasureTag> tags;
    private SweetSnatchPresnet presnet;

    @Override
    public int layoutId() {
        return R.layout.fragment_sweet_snatch;
    }

    @Override
    public void init() {

        presnet = new SweetSnatchPresnet(this);
        presnet.start(getContext());
        presnet.getBannerData(getActivity());
        showLoading();
    }

    @OnClick(R.id.jump_dalitask)
    void jumpDaliTask(View view){
        DailyTasksActivity.starActivity(getActivity());
    }


    private void setTabView() {
        holder = null;
        for (int i = 0; i < tags.size(); i++) {
            TreasureData.TreasureTag tag = tags.get(i);
            //依次获取标签
            XTabLayout.Tab tab = xtablelayout.getTabAt(i);
            //为每个标签设置布局
            tab.setCustomView(R.layout.layout_xtab_item);
            holder = new ViewHolder(tab.getCustomView());
            //为标签填充数据
            holder.tvTabName.setText(tag.getTime());
            switch (tag.getTagStatus()){
                case 0:
                    holder.tvTabNumber.setText("已结束");
                    break;
                case 1:
                    holder.tvTabNumber.setText("正在进行");
                    break;
                case 2:
                    holder.tvTabNumber.setText("即将开始");
                    break;

            }

            //默认选择第一项
            if (i == 0){
                holder.tvTabName.setSelected(true);
                holder.tvTabNumber.setSelected(true);
            }
        }

        //tab选中的监听事件
        xtablelayout.addOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(true);
                holder.tvTabNumber.setSelected(true);
                //选中后字体变大
                //让Viewpager跟随TabLayout的标签切换
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(false);
                holder.tvTabNumber.setSelected(false);
                //恢复为默认字体大小
            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }

        });
    }



//    private void getTitle(int postition){
//
//        Calendar cal = Calendar.getInstance();
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int range = (hour - 8)/2;
//        String upText = null;
//        switch (postition){
//            case 0:
//                upText = "8:00";
//                break;
//
//            case 1:
//                upText = "10:00";
//                break;
//
//            case 2:
//
//                upText = "12:00";
//
//                break;
//
//            case 3:
//
//                upText = "14:00";
//
//                break;
//
//            case 4:
//
//                upText = "16:00";
//                break;
//
//            case 5:
//
//                upText = "18:00";
//                break;
//
//            case 6:
//                upText = "20:00";
//                break;
//        }
//
//        String downText = null;
//        if (range < 0){
//            downText = "即将开始";
//        }else if (range > 6){
//            downText = "已结束";
//        }else {
//            if (range == postition){
//                downText = "正在进行";
//            }else if (range<postition){
//                downText = "即将开始";
//            }else {
//                downText = "已结束";
//            }
//        }
//        tabs.add(upText);
//        tabNumbers.add(downText);
//
//    }

    @Override
    public void onLoadedData(TreasureData.DataBean tag) {

        hiddenLoadingView();

        List<TreasureData.TreasureTag> tags = tag.getTreasureList();

        this.tags = tags;
        if(isAdded()){
            adapter = new SweetSnatchAdapter(getChildFragmentManager(),this.tags);

            pager.setAdapter(adapter);

            xtablelayout.setupWithViewPager(pager);

            setTabView();
        }

    }

    @Override
    public void initBannerTop(BannerBean bannerBean) {
        if (bannerBean.getData().getAdMobileList() == null || bannerBean.getData().getAdMobileList().size() <= 0) {
            return;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < bannerBean.getData().getAdMobileList().size(); i++) {
            list.add(bannerBean.getData().getAdMobileList().get(i).getImg());
        }
        top.setImagesUrl(list);
        top.setOnItemClickListener(position -> {
            BannerBean.DataBean.AdMobileListBean bean = bannerBean.getData().getAdMobileList().get(position);
//                    0：普通商品 1：说明广告 2：导购文章 4：会员往期活动 5：0元拼团 6：砍价免费拿 7 糖果夺宝

            IAdvClick.click(getActivity(),bean.getType(),bean.getSkipType(),bean.getSource_id());

        });
    }

    @Override
    public void onLoadError() {
        hiddenLoadingView();
    }


    class ViewHolder{
        TextView tvTabName;
        TextView tvTabNumber;

        public ViewHolder(View tabView) {
            tvTabName = (TextView) tabView.findViewById(R.id.tv_tab_name);
            tvTabNumber = (TextView) tabView.findViewById(R.id.tv_tab_number);
        }
    }




}
