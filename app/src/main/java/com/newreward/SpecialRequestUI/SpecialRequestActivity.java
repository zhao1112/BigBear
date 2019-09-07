package com.newreward.SpecialRequestUI;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newreward.bean.SpecialRequest;
import com.newreward.contract.SpecialRequestContract;
import com.newreward.presenter.SpecialRequestPresenter;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.util.CShareUtil;
import com.yunqin.bearmall.util.ShareUtilNew;
import com.yunqin.bearmall.util.ToastUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2019/1/22
 * @Describe
 */
public class SpecialRequestActivity extends BaseActivity implements SpecialRequestContract.UI {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.price_old)
    TextView price_old;

    @BindView(R.id.price_new)
    TextView price_new;

    @BindView(R.id.tip_b_1)
    TextView tip_b_1;
    @BindView(R.id.tip_b_2)
    TextView tip_b_2;
    @BindView(R.id.tip_b_3)
    TextView tip_b_3;
    @BindView(R.id.tip_b_4)
    TextView tip_b_4;

    private SpecialRequestContract.Presenter presenter;

    private ShareBean shareBean;

    @Override
    public int layoutId() {
        return R.layout.activity_special_request;
    }

    @OnClick({R.id.toolbar_back, R.id.tip_b_4, R.id.request_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                this.finish();
                break;
            case R.id.tip_b_4:
                startActivity(new Intent(this, RequestRecord.class));
                break;
            case R.id.request_btn:


                if (shareBean != null) {
                    CShareUtil.Share(SpecialRequestActivity.this, shareBean.getData());
                    return;
                }


                showLoading();

                Map<String, String> mHashMap = new HashMap<>();
                mHashMap.put("type", "7");
                RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getShareParams(mHashMap), new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        hiddenLoadingView();
                        shareBean = new Gson().fromJson(data, ShareBean.class);
                        CShareUtil.Share(SpecialRequestActivity.this, shareBean.getData());
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


                break;
        }
    }

    @Override
    public void init() {
        toolbar_title.setText("特价邀约");
        presenter = new SpecialRequestPresenter(this, this);
    }

    @Override
    public void setSpecPageInfo(String data) {
        //请求成功
        SpecialRequest specialRequest = new Gson().fromJson(data, SpecialRequest.class);
        SpecialRequest.DataBean.SpecInvitationPageInfoBean specInvitationPageInfoBean = specialRequest.getData().getSpecInvitationPageInfo();
        price_old.setText("原价" + specInvitationPageInfoBean.getShowPrice() + "元/半年");
        price_new.setText(specInvitationPageInfoBean.getRealPrice() + "");
        tip_b_1.setText(String.format("送出后您得  %s  元现金，好友得  %S  元话费", specInvitationPageInfoBean.getCommission(),
                specInvitationPageInfoBean.getRewardPhoneFee()));
        tip_b_2.setText(String.format("活动日期：%s - %s", specInvitationPageInfoBean.getActivityStartDate(),
                specInvitationPageInfoBean.getActivityEndDate()));
        tip_b_3.setText(String.format("本次活动还可邀请  %s  名好友", specInvitationPageInfoBean.getSpecInviteUsableCount()));
    }

    @Override
    public void setShareData(ShareBean.DataBean shareBean) {
        ShareUtilNew.Share(this, shareBean);
    }

    @Override
    public void onNetFail() {
        //请求失败
    }

    @Override
    public void getShareDataFaile() {
        ToastUtils.showToast(this, "获取分享数据失败");
    }
}
