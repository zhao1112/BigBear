package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newversions.NewOrderBean;
import com.newversions.NewOrderChildBean;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BargainFriendAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.BargainRecordBean;
import com.yunqin.bearmall.bean.BargainShareDetailBean;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.inter.ShareCallBack;
import com.yunqin.bearmall.ui.activity.contract.BargainFreeShareContact;
import com.yunqin.bearmall.ui.activity.presenter.BargainFreeShareFriendPresenter;
import com.yunqin.bearmall.ui.dialog.BargainAddressDialog;
import com.yunqin.bearmall.ui.dialog.BargainStrategyDialog;
import com.yunqin.bearmall.ui.dialog.ServiceInstructionDialog;
import com.yunqin.bearmall.util.BargainShareUtil;
import com.yunqin.bearmall.util.TimeUtils;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.ScrollListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yunqin.bearmall.ui.fragment.TrolleyFragment.BARGAIN_RECORD_ID;
import static com.yunqin.bearmall.ui.fragment.TrolleyFragment.ORDER_TYPE;

public class BargainFreeShareActivity extends BaseActivity implements BargainFreeShareContact.UI {

    public static final String BARGAINRECORD_ID = "bargainRecord_id";

    @BindView(R.id.custom_recommend_view)//推荐
            CustomRecommendView customRecommendView;


    @BindView(R.id.bargain_free_rule)//导航栏右侧免单攻略，由于和砍价免费拿首页使用同一个布局，所以这里隐藏该控件
            TextView bargain_free_rule;

    @BindView(R.id.bargain_share_user_head_img)//用户头像
            CircleImageView user_head_img;

    @BindView(R.id.bargain_share_user_name)//用户昵称
            TextView user_name;

    @BindView(R.id.bargain_share_product_img)//商品图片
            ImageView product_img;

    @BindView(R.id.bargain_share_product_name)//商品名称
            TextView product_name;

    @BindView(R.id.bargain_share_product_count)//多少人已免费拿
            TextView product_count;

    @BindView(R.id.bargain_share_original_price)//大熊零售价
            TextView original_price;

    @BindView(R.id.bargain_share_address)//地址
            TextView address_tv;

    @BindView(R.id.bargain_share_already_price)//已砍价格
            TextView already_price;

    @BindView(R.id.bargain_share_expire)//距离砍价结束
            TextView expire;

    @BindView(R.id.bargain_share_current_price)//当前价格
            TextView current_price;

    @BindView(R.id.bargain_share_friend_bargain_list)//砍价列表
            ScrollListView friend_bargain_list;

    @BindView(R.id.bargain_free_share_like_img)//收藏图标
            ImageView like_img;

    @BindView(R.id.bargain_free_share_like_tv_isFavorite)//收藏文字
            TextView like_tv;

    @BindView(R.id.bargain_free_share_original_price_tv)//原价
            TextView original_price_tv;

    @BindView(R.id.bargain_free_share_current_price_tv)//当前价
            TextView current_price_tv;

    @BindView(R.id.bargain_share_progress)//砍价进度
            ProgressBar progress;

    @BindView(R.id.bargain_share_criticalRatio)//奖励比例临界点
            TextView criticalRatio;

    @BindView(R.id.bargain_share_invite_friend)//邀请好友砍价
            Button invite_friend;


    @OnClick({R.id.bargain_free_back_img_layout, R.id.bargain_share_price_explain, R.id.bargain_share_address, R.id.product_service_instruction,
            R.id.bargain_free_share_like_layout, R.id.bargain_free_share_original_price_layout, R.id.bargain_free_share_current_price_layout,
            R.id.bargain_share_invite_friend})
    public void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bargain_free_back_img_layout:
                finish();
                break;

            case R.id.bargain_share_price_explain:
                if (bargainStrategyDialog == null) {
                    bargainStrategyDialog = new BargainStrategyDialog(BargainFreeShareActivity.this);
                } else {
                    bargainStrategyDialog.show();
                }
                break;

            case R.id.bargain_share_address://选择地址
                showAddressDialog();
                break;

            case R.id.product_service_instruction://服务
                if (serviceInstructionDialog == null) {
                    serviceInstructionDialog = new ServiceInstructionDialog(BargainFreeShareActivity.this);
                } else {
                    serviceInstructionDialog.show();
                }
                break;

            case R.id.bargain_free_share_like_layout://收藏
                if (BearMallAplication.getInstance().getUser() != null) {
                    Map map = new HashMap();
                    map.clear();
                    map.put("product_id", String.valueOf(BargainDetails.getProduct_id()));
                    if (isFavorite == 0) {
                        isFavorite = 1;
                        map.put("isFavorite", String.valueOf(isFavorite));

                    } else if (isFavorite == 1) {
                        isFavorite = 0;
                        map.put("isFavorite", String.valueOf(isFavorite));
                    }
                    bargainFreeSharePresenter.setBargainFavorite(BargainFreeShareActivity.this, map);

                } else {
                    return;
                }

                break;

            case R.id.bargain_free_share_original_price_layout://原价


                if (isMember) {
                    addDataToOrderDeatil(false);
                } else {
                    if (isLimitMsdc) {

                        joinMember();

                    } else {
                        addDataToOrderDeatil(false);
                    }
                }
                break;
            case R.id.bargain_free_share_current_price_layout://当前价
                addDataToOrderDeatil(true);
                break;
            case R.id.bargain_share_invite_friend://分享好友

                // TODO 判断是否剩下0刀


                if (BargainDetails.getRestBargainCount() == 0) {
                    new ICustomDialog.Builder(BargainFreeShareActivity.this)
                            // 设置布局
                            .setLayoutResId(R.layout.end_of_the_bargain_layout)
                            // 点击空白是否消失
                            .setCanceledOnTouchOutside(false)
                            // 点击返回键是否消失
                            .setCancelable(false)
                            // 设置Dialog的绝对位置
                            .setDialogPosition(Gravity.CENTER)
                            // 设置自定义动画
                            .setAnimationResId(0)
                            // 设置监听ID
                            .setListenedItems(new int[]{R.id.cancel, R.id.ok})
                            // 设置回掉
                            .setOnDialogItemClickListener(new ICustomDialog.OnDialogItemClickListener() {
                                @Override
                                public void onDialogItemClick(ICustomDialog thisDialog, View clickView) {

                                    if (clickView.getId() == R.id.ok) {
                                        addDataToOrderDeatil(true);
                                    }
                                    thisDialog.dismiss();
                                }
                            })
                            .build().show();
                } else {
                    shareDiaog();
                }


                break;
        }
    }

    private void joinMember() {
        new ICustomDialog.Builder(this)
                // 设置布局
                .setLayoutResId(R.layout.join_member_layout)
                // 点击空白是否消失
                .setCanceledOnTouchOutside(false)
                // 点击返回键是否消失
                .setCancelable(false)
                // 设置Dialog的绝对位置
                .setDialogPosition(Gravity.CENTER)
                // 设置自定义动画
                .setAnimationResId(0)
                // 设置监听ID
                .setListenedItems(new int[]{R.id.join_member_no, R.id.join_member_ok})
                // 设置回掉
                .setOnDialogItemClickListener((thisDialog, clickView) -> {
                    if (clickView.getId() == R.id.join_member_ok) {
                        startActivity(new Intent(this, VipCenterActivity.class));
                    }
                    thisDialog.dismiss();
                })
                .build().show();
    }


    private BargainFreeShareFriendPresenter bargainFreeSharePresenter;
    private long bargainRecord_id;
    private List<BargainRecordBean.BargainJoinRecordList> BargainJoinRecordList;
    private BargainRecordBean.BargainDetails BargainDetails;
    private ProductDetail.Store store;
    private BargainFriendAdapter bargainFriendAdapter;
    private ServiceInstructionDialog serviceInstructionDialog;
    private int isFavorite;
    private AddressListBean.DataBean address;
    private BargainAddressDialog bargainAddressDialog;
    //    private BargainShareDialog bargainShareDialog;
    private int bargainCount;
    private long expireDate;
    private Animation mAnimation;
    private ShareBean shareBean = null;
    private BargainStrategyDialog bargainStrategyDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_bargain_free_share;
    }

    @Override
    public void init() {
        bargain_free_rule.setVisibility(View.GONE);

        bargainRecord_id = getIntent().getLongExtra(BARGAINRECORD_ID, 0l);

        bargainFreeSharePresenter = new BargainFreeShareFriendPresenter(BargainFreeShareActivity.this, this);
        Constans.params.clear();
        Constans.params.put(BARGAINRECORD_ID, bargainRecord_id + "");
        if (bargainRecord_id > 0) {
            bargainFreeSharePresenter.getBargainDetails(Constans.params);
        } else {
            return;
        }

//        immerseStatusBar();//设置viewPager中fragment沉浸式状态栏
//        changStatusIconCollor(true);//状态栏icon为深色

        mAnimation = AnimationUtils.loadAnimation(this, R.anim.beat_anim);
        invite_friend.setAnimation(mAnimation);
        mAnimation.start();

        customRecommendView.setTvBottom("推荐商品");
        customRecommendView.setDiviervisibility(View.VISIBLE);
        customRecommendView.setTopTextViewLeft();
        customRecommendView.setTopTextViewBgColor(BargainFreeShareActivity.this.getResources().getColor(R.color.white));
        customRecommendView.setTopTextViewHeight(BargainFreeShareActivity.this.getResources().getDimension(R.dimen.ds94));
        customRecommendView.hideTopLayout();

        customRecommendView.setManager(new GridLayoutManager(BargainFreeShareActivity.this, 2));
        customRecommendView.start(BargainFreeShareActivity.this);

    }

    public void shareDiaog() {
        //如果没有分享数据 ，则先获取分享数据
        if (shareBean == null) {
            showLoading();
            Constans.params.clear();
            Constans.params.put("source_id", BargainDetails.getBargainRecord_id() + "");
            Constans.params.put("type", 5 + "");
            bargainFreeSharePresenter.getShareData(BargainFreeShareActivity.this, Constans.params);
        } else {
            //如果有分享数据，则弹框
            share(shareBean);
        }
    }


    private boolean isLimitMsdc;
    private boolean isMember;

    @Override
    public void getBargainDetails(String data) {
        BargainRecordBean bargainRecordBean = new Gson().fromJson(data, BargainRecordBean.class);
        if (bargainRecordBean.getCode() == 1) {

            isMember = bargainRecordBean.getData().isMember();
            isLimitMsdc = bargainRecordBean.getData().getBargainDetails().isLimitMs();

            BargainRecordBean.DataBean dataBean = bargainRecordBean.getData();
            BargainJoinRecordList = dataBean.getBargainJoinRecordList();
            BargainDetails = dataBean.getBargainDetails();
            store = dataBean.getStore();

            Glide.with(BargainFreeShareActivity.this).load(BearMallAplication.getInstance().getUser().getData().getMember().getIconUrl()).into(user_head_img);
            user_name.setText(BearMallAplication.getInstance().getUser().getData().getMember().getNickName());

            Glide.with(BargainFreeShareActivity.this).load(BargainDetails.getProductImages().getThumbnail()).into(product_img);
            product_name.setText(BargainDetails.getProductName());
            product_count.setText("已有" + BargainDetails.getPersonalCount() + "人参加");

//            original_price.setText("¥" + BargainDetails.getSourcePartPrice() + "+BC" + BargainDetails.getSourcePartBtAmount());
            original_price.setText("¥" + BargainDetails.getPrice());

            if (BargainDetails.getArea() == null || BargainDetails.getArea().equals("")) {
                address_tv.setText("完善收货地址，砍成后即可发货");
            } else {
                address = new AddressListBean.DataBean();
                address.setAddress(BargainDetails.getAddress());
                address.setAreaName(BargainDetails.getArea());
                address.setConsignee(BargainDetails.getConsignee());
                address.setPhone(BargainDetails.getPhone());
                address.setReceiver_id((int) BargainDetails.getReceiver_id());
                address_tv.setText(BargainDetails.getConsignee() + "," + BargainDetails.getPhone() + "," + BargainDetails.getArea() + BargainDetails.getAddress());
            }

//            already_price.setText("已砍¥" + BargainDetails.getBargainedPrice() + "+BC" + BargainDetails.getBargainedBtAmount());
            already_price.setText("已砍¥" + BargainDetails.getBargainedPrice() + ",该商品金熊价为" + BargainDetails.getMemberShipPrice() + "元，距离砍到金熊价还差" + BargainDetails.getRestBargainCount() + "刀哦~");
            expireDate = BargainDetails.getRestTime();
            expire.setText(TimeUtils.generateTime(expireDate));
            countDow();

//            current_price.setText("¥" + BargainDetails.getCurrentPartPrice() + "+BC" + BargainDetails.getCurrentPartBtAmount());
            current_price.setText("¥" + BargainDetails.getCurrentPartPrice());

            if (bargainFriendAdapter == null) {
                bargainFriendAdapter = new BargainFriendAdapter(BargainFreeShareActivity.this, BargainJoinRecordList);
                friend_bargain_list.setAdapter(bargainFriendAdapter);
            } else {
                bargainFriendAdapter.notifyDataSetChanged();
            }

            isFavorite = BargainDetails.getIsFavorite();
            //设置是否收藏icon
            if (isFavorite == 0) {
                like_img.setImageResource(R.drawable.icon_like_normal);
                like_tv.setText("收藏");
            } else if (isFavorite == 1) {
                like_img.setImageResource(R.drawable.icon_like_selected);
                like_tv.setText("已收藏");
            }

            //设置商品原来的价格
            original_price_tv.setText("¥" + BargainDetails.getPrice());
            current_price_tv.setText("¥" + BargainDetails.getCurrentPartPrice());
            String bargainedRatio = BargainDetails.getBargainedRatio();
            String bargainedRatio_ = bargainedRatio.substring(0, bargainedRatio.length() - 1);
            float bargainedRatioFloat = Float.valueOf(bargainedRatio_);
            progress.setProgress((int) bargainedRatioFloat);
            bargainCount = BargainDetails.getBargainCount();

//            shareDiaog();


        }

    }

    public void countDow() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        expireDate = expireDate - 1000;
                        expire.setText(TimeUtils.generateTime(expireDate));
                    }
                });
            }
        }, 1000, 1000);
    }

//    public void showShareDialog(){
//        if(bargainShareDialog == null){
//            bargainShareDialog = new BargainShareDialog(BargainFreeShareActivity.this,BargainDetails,bargainCount);
//            bargainShareDialog.showDialog(bargainCount,"","");
//        }else{
//            bargainShareDialog.showDialog(bargainCount,"","");
//        }
//    }

    @Override
    public void setFavorite(String data) {
        hiddenLoadingView();
        if (isFavorite == 1) {
            like_img.setImageResource(R.drawable.icon_like_selected);
            like_tv.setText("已收藏");
            Toast.makeText(BargainFreeShareActivity.this, "收藏成功", Toast.LENGTH_LONG).show();
        } else {
            like_img.setImageResource(R.drawable.icon_like_normal);
            like_tv.setText("收藏");
            Toast.makeText(BargainFreeShareActivity.this, "取消成功", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void setReceiverList(String data) {
        hiddenLoadingView();
        if (bargainAddressDialog != null) {
            bargainAddressDialog.setData(data, 0);
        }
    }

    @Override
    public void addBargainReceiver(String data) {
        address_tv.setText(address.getConsignee() + "," + address.getPhone() + "," + address.getAreaName() + address.getAddress());
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 100 && data != null) {

            showAddressDialog();
        }
    }

    public void showAddressDialog() {
        if (bargainAddressDialog == null) {
            bargainAddressDialog = new BargainAddressDialog(BargainFreeShareActivity.this);
            bargainAddressDialog.setOnBargainFreePartListener(new BargainAddressDialog.OnBargainFreePartListener() {
                @Override
                public void onBargainFreePart(AddressListBean.DataBean receiver) {
                    address = receiver;
                    Constans.params.clear();
                    Constans.params.put("receiver_id", receiver.getReceiver_id() + "");
                    Constans.params.put("bargainRecord_id", bargainRecord_id + "");
                    bargainFreeSharePresenter.addBargainReceiver(BargainFreeShareActivity.this, Constans.params);
                }
            });
            bargainAddressDialog.showDialog();
            bargainFreeSharePresenter.getReceiverList(BargainFreeShareActivity.this);
        } else {
            bargainAddressDialog.showDialog();
            bargainFreeSharePresenter.getReceiverList(BargainFreeShareActivity.this);
        }

    }


    @Override
    public void share(ShareBean shareBean) {
        hiddenLoadingView();
        this.shareBean = shareBean;
        Share();
    }

    private void Share() {
        //显示分享弹框，获取分享结果
        BargainShareUtil.Share(this, shareBean.getData(), null, new ShareCallBack() {
            @Override
            public void onComplete() {
                //分享成功，获取数据
                Log.e("ShareDetails", "分享成功，开始重新获取分享弹框数据");

                Map<String, String> map = new HashMap();
                map.put("bargainRecord_id", bargainRecord_id + "");
                bargainFreeSharePresenter.shareToOthers(BargainFreeShareActivity.this, map);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onCancel() {

            }
        });

    }

    @Override
    public void shareToOthers(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                init();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private BargainShareDetailBean bargainShareDetailBean;

    @Override
    public void getShareDetails(BargainShareDetailBean shareBean) {
        bargainShareDetailBean = shareBean;

        Share();
    }

    public void addDataToOrderDeatil(boolean isOriginal) {//flase则直接以法币购买，true 当前价格

        if (BargainDetails == null) {
            Toast.makeText(this, "请求失败，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = BargainDetails.getProductName();
        String imgUrl = BargainDetails.getProductImages().getSource();
        String price = String.valueOf(BargainDetails.getPrice());
        int SkuId = (int) BargainDetails.getSku_id();
        int productId = (int) BargainDetails.getProduct_id();

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < BargainDetails.getSpecifications().size(); i++) {
            stringBuffer.append(BargainDetails.getSpecifications().get(i) + " ");
        }
        String guiGe = stringBuffer.toString();

        NewOrderChildBean newOrderChildBean = new NewOrderChildBean(name, imgUrl, price, guiGe, "1", productId, SkuId, 0);

        String storeName = store.getStore_name();
        String storeImg = store.getLogo();

        List<NewOrderChildBean> list = new ArrayList();
        list.add(newOrderChildBean);
        NewOrderBean newOrderBean = new NewOrderBean(storeName, storeImg, list);
        List<NewOrderBean> newOrderBeans = new ArrayList<>();
        newOrderBeans.add(newOrderBean);

        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra(ConfirmActivity.INTENT_DATA, (ArrayList) newOrderBeans);
        if (isOriginal) {
            intent.putExtra("type_order", "3");
            intent.putExtra("address", address);
        } else {
            intent.putExtra("type_order", "0");
        }

        intent.putExtra(BARGAIN_RECORD_ID, bargainRecord_id + "");
        intent.putExtra(ORDER_TYPE, "kanjia");

        startActivity(intent);
    }


}
