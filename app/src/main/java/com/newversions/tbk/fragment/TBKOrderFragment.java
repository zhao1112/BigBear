package com.newversions.tbk.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bbcoupon.util.WindowUtils;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.TaoBaoOrderTabAdapter;
import com.yunqin.bearmall.base.BaseFragment;

import butterknife.BindView;

public class TBKOrderFragment extends BaseFragment {

    @BindView(R.id.x_table_layout)
    TabLayout mXTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.select_order)
    TextView mSelectOrder;

    private static final String[] tbTabs = new String[]{"全部", "待返佣", "已到账", "已失效"};
    private TaoBaoOrderTabAdapter mHotAdapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void init() {
        initTabLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("#dong", "onResume:3");
    }

    private void initTabLayout() {

        initView("我的订单");
        initAdapter();

        mSelectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mSelectOrder", "onTabReselected: " + mXTabLayout.getSelectedTabPosition());
                setSelect(v);
            }
        });
    }

    private void initAdapter() {
        mHotAdapter = new TaoBaoOrderTabAdapter(getActivity(), getChildFragmentManager());
        mViewPager.setAdapter(mHotAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mXTabLayout));
        mXTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void initView(String all) {
        mXTabLayout.removeAllTabs();
        tbTabs[0] = all;
        for (int i = 0; i < tbTabs.length; i++) {
            View v = getLayoutInflater().inflate(R.layout.item_tab_view, null);
            OrderTabViewHolder holder = new OrderTabViewHolder(v);
            v.setTag(holder);
            holder.tv.setText(tbTabs[i]);
            holder.tv.setTextColor(getResources().getColor(R.color.home_select_color));
            if (i == 0) {
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                holder.im.setImageResource(R.mipmap.order_screen);
                holder.im.setTag(true);
            } else {
                holder.im.setImageResource(R.mipmap.se_down);
            }
            mXTabLayout.addTab(mXTabLayout.newTab().setCustomView(v));
        }
        mXTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("onTabSelected", "onTabSelected: " + tab.getPosition());
                OrderTabViewHolder holder = (OrderTabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                if (tab.getPosition() == 0) {
                    holder.im.setImageResource(R.mipmap.order_screen);
                } else {
                    holder.im.setImageResource(R.mipmap.se_down);
                }
                holder.im.setTag(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e("onTabSelected", "onTabUnselected: " + tab.getPosition());
                OrderTabViewHolder holder = (OrderTabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.home_select_color));
                if (tab.getPosition() == 0) {
                    holder.im.setImageResource(R.mipmap.order_screen);
                } else {
                    holder.im.setImageResource(R.mipmap.se_down);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("onTabSelected", "onTabReselected: " + tab.getPosition());
                OrderTabViewHolder holder = (OrderTabViewHolder) tab.getCustomView().getTag();
                holder.tv.setTextColor(getResources().getColor(R.color.colorAccent));
                if (tab.getPosition() == 0) {
                    holder.im.setImageResource(R.mipmap.order_screen);
                    holder.im.setTag(false);
                } else {
                    holder.im.setImageResource(R.mipmap.se_down);
                    holder.im.setTag(false);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class OrderTabViewHolder {

        TextView tv;
        ImageView im;

        OrderTabViewHolder(View v) {
            tv = v.findViewById(R.id.textView);
            im = v.findViewById(R.id.imageView);
        }
    }

    public void setSelect(View v) {
        if (mXTabLayout.getSelectedTabPosition() == 0) {
            WindowUtils.dimBackground(getActivity(), 1.0f, 0.3f);
            View view = View.inflate(getActivity(), R.layout.item_popup_order, null);
            PopupWindow popupWindow = new PopupWindow(view);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());//设置背景
            popupWindow.setOutsideTouchable(true);//点击外面窗口消失
            popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);//设置动画
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popupWindow.showAsDropDown(v);//在v的下面
            //显示在下方
            popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowUtils.dimBackground(getActivity(), 0.5f, 1.0f);
                }
            });
            RadioGroup order_group = view.findViewById(R.id.order_group);
            RadioButton order_all = view.findViewById(R.id.order_all);
            RadioButton order_mine = view.findViewById(R.id.order_mine);
            RadioButton order_team = view.findViewById(R.id.order_team);
            order_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.order_all:
                            initView("全部");
                            mHotAdapter.setOrder("0");
                            popupWindow.dismiss();
                            break;
                        case R.id.order_mine:
                            initView("我的订单");
                            mHotAdapter.setOrder("1");
                            popupWindow.dismiss();
                            break;
                        case R.id.order_team:
                            initView("粉丝订单");
                            mHotAdapter.setOrder("2");
                            popupWindow.dismiss();
                            break;
                    }
                }
            });
        } else {
            mXTabLayout.getTabAt(0).select();
        }
    }
}
