package com.bbcoupon.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bbcoupon.base.ImageSelectInfor;
import com.bbcoupon.ui.adapter.ChoiceAdapter;
import com.bbcoupon.util.CopyTextUtil;
import com.bbcoupon.util.ShareUtils;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.ShareUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/6
 */
public class ChoiceShareActivity extends BaseActivity {

    @BindView(R.id.chouce_recycler)
    RecyclerView mChouceRecycler;
    @BindView(R.id.choices_select)
    CheckBox mChoicesSelect;
    @BindView(R.id.choose_image)
    TextView mChooseImage;
    @BindView(R.id.whole_image)
    TextView mWholeImage;
    @BindView(R.id.profit)
    TextView mProfit;
    @BindView(R.id.recommend_conten)
    EditText mRecommendConten;
    @BindView(R.id.goods_conten)
    TextView mGoodsConten;

    private ChoiceAdapter choiceAdapter;
    public List<ImageSelectInfor.ImageBean> list;

    @Override
    public int layoutId() {
        return R.layout.activity_choiceshare;
    }

    @Override
    public void init() {
        ImageSelectInfor imageList = (ImageSelectInfor) getIntent().getSerializableExtra("ImageList");
        String profit = getIntent().getStringExtra("Profit");

        if (imageList.getImageBean() != null && imageList.getImageBean().size() > 0) {
            list = imageList.getImageBean();
            mProfit.setText("您的预估收益为：" + profit + "元");
            mWholeImage.setText("/" + list.size());


            mChouceRecycler.setLayoutManager(new LinearLayoutManager(ChoiceShareActivity.this, RecyclerView.HORIZONTAL, false));
            choiceAdapter = new ChoiceAdapter(ChoiceShareActivity.this, list);
            mChouceRecycler.setAdapter(choiceAdapter);

            choiceAdapter.setOnWholeState(new ChoiceAdapter.OnWholeState() {
                @Override
                public void onWholeState(boolean isSelect) {
                    mChoicesSelect.setChecked(isSelect);
                }

                @Override
                public void onSelection(List<ImageSelectInfor.ImageBean> mList) {
                    list = mList;
                    int position = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isSelect()) {
                            position++;
                        }
                    }
                    mChooseImage.setText(position + "");
                }
            });
        }

    }


    @OnClick({R.id.toolbar_back, R.id.choices_select, R.id.recommend_copy, R.id.goodes_copy, R.id.wx_share, R.id.moments_share,
            R.id.qq_share, R.id.qq_moments_share,
            R.id.dwon_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.choices_select:
                choiceAdapter.wholeState(mChoicesSelect.isChecked());
                break;
            case R.id.recommend_copy:
                if (!TextUtils.isEmpty(mRecommendConten.getText().toString())) {
                    CopyTextUtil.CopyText(ChoiceShareActivity.this, mRecommendConten.getText().toString());
                    showToast("复制成功");
                }
                break;
            case R.id.goodes_copy:
                if (!TextUtils.isEmpty(mGoodsConten.getText().toString())) {
                    CopyTextUtil.CopyText(ChoiceShareActivity.this, mGoodsConten.getText().toString());
                    showToast("复制成功");
                }
                break;
            case R.id.wx_share:
                if (ShareUtils.isWXClientAvailable(ChoiceShareActivity.this)) {
                    String[] imageList = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        imageList[i] = list.get(i).getImage();
                    }
                    ShareUtils.MultiGraphShare(Wechat.NAME, imageList, mRecommendConten.getText().toString());
                } else {

                }
                break;
            case R.id.moments_share:
                break;
            case R.id.qq_share:
                break;
            case R.id.qq_moments_share:
                break;
            case R.id.dwon_share:
                break;
        }
    }

}
