package com.yunqin.bearmall.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.AllCommentAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.WaitCommentBean;
import com.yunqin.bearmall.ui.activity.contract.AllCommentContract;
import com.yunqin.bearmall.ui.activity.presenter.AllCommentPresenter;
import com.yunqin.bearmall.ui.fragment.CollectionGoodsFragment;
import com.yunqin.bearmall.ui.fragment.CollectionShopFragment;
import com.yunqin.bearmall.ui.fragment.FragmentAllComment;
import com.yunqin.bearmall.ui.fragment.FragmentTextComment;
import com.yunqin.bearmall.ui.fragment.FriendsGiftsFragment;
import com.yunqin.bearmall.ui.fragment.GiveFriendsFragment;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/26
 * @Describe
 */
public class MyAllCommentActivity extends BaseActivity implements AllCommentContract.UI {

    @BindView(R.id.head_image)
    CircleImageView head_image;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.write_comment)
    Button write_comment;
    @BindView(R.id.content_left)
    TextView content_left;
    @BindView(R.id.view_left)
    View view_left;
    @BindView(R.id.content_right)
    TextView content_right;
    @BindView(R.id.view_right)
    View view_right;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.images_layout)
    LinearLayout images_layout;

    private AllCommentContract.Present present;
    FragmentManager mFragmentManager;
    FragmentAllComment fragmentAllComment;
    FragmentTextComment fragmentTextComment;

    @Override
    public int layoutId() {
        return R.layout.activity_my_all_comment;
    }

    @Override
    public void init() {
        initFragment();
        toolbar_title.setText("我的评价");
        present = new AllCommentPresenter(this, this);
        try {
            Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                    .load(BearMallAplication.getInstance().getUser().getData().getMember().getIconUrl()).into(head_image);
            user_name.setText(BearMallAplication.getInstance().getUser().getData().getMember().getNickName());
        } catch (Exception e) {
            throw new IllegalArgumentException("User信息为null");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        present.start(this);
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        switchFragment(1);
    }

    @OnClick({R.id.toolbar_back, R.id.layout_left, R.id.layout_right, R.id.write_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.layout_left:
                switchFragment(1);
                setBackground(true);
                break;
            case R.id.layout_right:
                switchFragment(2);
                setBackground(false);
                break;
            case R.id.write_comment:
                MineOrderActivity.openMineOrderActivity(this,true);
                this.finish();
                break;
        }
    }

    private final void setBackground(boolean visibility) {
        content_left.setTextColor(visibility ? getResources().getColor(R.color.main_color) : Color.parseColor("#666666"));
        view_left.setVisibility(visibility ? View.VISIBLE : View.GONE);
        content_right.setTextColor(!visibility ? getResources().getColor(R.color.main_color) : Color.parseColor("#666666"));
        view_right.setVisibility(!visibility ? View.VISIBLE : View.GONE);
    }

    private int showWhich ;

    private void switchFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (index == 1) {
            if(showWhich == 1){
                return;
            }

            showWhich = 1;
            if (fragmentAllComment == null) {
                fragmentAllComment = new FragmentAllComment();
                transaction.add(R.id.bt_content, fragmentAllComment);
            }
            if(fragmentTextComment != null){
                transaction.hide(fragmentTextComment);
            }
            transaction.hide(fragmentAllComment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(fragmentAllComment);
            transaction.commit();
        } else {
            if(showWhich == 2){
                return;
            }

            showWhich = 2;
            if (fragmentTextComment == null) {
                fragmentTextComment = new FragmentTextComment();
                transaction.add(R.id.bt_content, fragmentTextComment);
            }
            if(fragmentAllComment != null){
                transaction.hide(fragmentAllComment);
            }
            transaction.hide(fragmentTextComment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(fragmentTextComment);
            transaction.commit();
        }
    }


    @Override
    public void attachData(WaitCommentBean waitCommentBean) {
        images_layout.removeAllViews();
        //todo 待评价列表
        for (int i = 0; i < waitCommentBean.getData().getItemList().size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(162,
                    162);//
            //设置自己需要的距离
            lp.leftMargin = 40;
            lp.gravity = Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(lp);
            Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment)).load( waitCommentBean.getData().getItemList().get(i).getThumbnail()).into(imageView);
            images_layout.addView(imageView);
            int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToComment(waitCommentBean.getData().getItemList().get(finalI).getOrders_id());
                }
            });
        }
    }



    private void goToComment(int orders_id) {
        showLoading();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orders_id", String.valueOf(orders_id));
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getReviewBasicInfos(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                try {
                    hiddenLoadingView();
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        Bundle bundle = new Bundle();
                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                        bundle.putInt("orders_id", jsonObject1.optInt("orders_id"));

                        JSONArray jsonArray = jsonObject1.optJSONArray("itemList");
                        int orders_count = jsonArray.length();
                        bundle.putInt("orders_count", orders_count);
                        for (int i = 0; i < orders_count; i++) {
                            bundle.putString(String.format("icon%d", i), jsonArray.optJSONObject(i).optString("thumbnail"));
                            bundle.putInt(String.format("item%d", i), jsonArray.optJSONObject(i).optInt("product_id"));
                            StringBuffer specificationItems = new StringBuffer();
                            for (int j = 0; j < jsonArray.optJSONObject(i).optJSONArray("specifications").length(); j++) {
                                specificationItems.append(jsonArray.optJSONObject(i).optJSONArray("specifications").opt(j)).append(",");
                            }
                            bundle.putString(String.format("SpecificationItems%d", i), specificationItems.toString());
                        }
                        StarActivityUtil.starActivity(MyAllCommentActivity.this, CommentActivity.class, bundle);
                    } else {
                        Toast.makeText(MyAllCommentActivity.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MyAllCommentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        });
    }


}
