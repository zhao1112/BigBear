package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.AddressAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.ui.activity.contract.AddressActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.AddressPresenter;
import com.yunqin.bearmall.util.NetUtils;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.example.lamor.Faceplate;
import cn.example.lamor.container.MultiStateConfigurator;
import cn.example.lamor.container.MultiStateContainer;
import cn.example.lamor.container.StateController;
import cn.example.lamor.utils.MultiStateLayout;

/**
 * @author Master
 */
public class AddressActivity extends BaseActivity implements AddressActivityContract.UI,
        View.OnClickListener, AddressAdapter.OnChildClickListener,
        AdapterView.OnItemClickListener, MultiStateContainer, MultiStateConfigurator {

    @BindView(R.id.address_list_view)
    ListView mListView;

    @BindView(R.id.add_address)
    Button mAddAddressButton;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.content)
    LinearLayout content;

    private boolean isClick = false;
    private static final int REQUESTCODE = 100;

    AddressActivityContract.Presenter presenter = new AddressPresenter(this);

    @Override
    public int layoutId() {
        return R.layout.activity_address;
    }

    @Override
    public void init() {
        Faceplate.getDefault().performInjectLayoutLayers(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            isClick = false;
        } else {
            isClick = bundle.getBoolean("isClick", false);
        }

        mAddAddressButton.setOnClickListener(this);
        toolbarTitle.setText("收货地址");
        mListView.setOnItemClickListener(this);
        presenter.start(this);
    }

    private AddressAdapter mAddressAdapter;

    @Override
    public void attachAdapter(AddressAdapter addressAdapter) {
        mStateController.showContent(false);
        mAddressAdapter = addressAdapter;
        addressAdapter.setOnChildClickListener(this);
        mListView.setAdapter(addressAdapter);
        View view = findViewById(R.id.empty_view);
        mListView.setEmptyView(view);
    }

    @Override
    public void onClick(View v) {

        if (!NetUtils.isConnected(this)) {
            Toast.makeText(this, "当前无网络连接!", Toast.LENGTH_SHORT).show();
            return;
        }
        StarActivityUtil.startActivityForResult(this, AddAddrActivity.class, REQUESTCODE);
    }

    @OnClick(R.id.toolbar_back)
    public void onFinish(View v) {
        if (isClick) {
            Intent intent = new Intent();
            setResult(NULL_, intent);
        }
        finish();
    }


    Intent intent;
    private static final int REFUND_CODE = 66;
    private static final int NULL_ = 77;


    @Override
    public void onBackPressed() {
        if (isClick) {
            Intent intent = new Intent();
            setResult(NULL_, intent);
        }
        finish();
    }

    @Override
    public void onEditAddress(AddressListBean.DataBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("name", bean.getConsignee());
        bundle.putString("number", bean.getPhone());
        bundle.putString("address", bean.getAreaName());
        bundle.putString("address_", bean.getAddress());
        bundle.putString("title", "编辑收货地址");
        bundle.putInt("area_id", bean.getArea_id());
        bundle.putBoolean("checked", bean.isIsDefault());
        bundle.putInt("receiver_id", bean.getReceiver_id());
        StarActivityUtil.startActivityForResult(this, AddAddrActivity.class, bundle, REQUESTCODE);

    }

    @Override
    public void onDelAddress(AddressListBean.DataBean bean) {
        showLoading();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("receiver_id", String.valueOf(bean.getReceiver_id()));
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).removeReceiver(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                hiddenLoadingView();
                presenter.performClick(bean, AddressActivityContract.PerformClick.DEL);
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

    @Override
    public void onDefaultAddress(AddressListBean.DataBean bean) {
        showLoading();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("receiver_id", String.valueOf(bean.getReceiver_id()));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).setDefaultReceiver(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                hiddenLoadingView();
                presenter.performClick(bean, AddressActivityContract.PerformClick.DEFAUL);
            }

            @Override
            public void onNotNetWork() {
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            int typeId = data.getIntExtra("type", 1);
            if (typeId == 0) {
                presenter.start(this);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isClick) {
            Intent intent = new Intent();
            List<AddressListBean.DataBean> lists = mAddressAdapter.getData();
            AddressListBean.DataBean mDataBean = lists.get(position);
            intent.putExtra("name", mDataBean.getConsignee());
            intent.putExtra("phone", mDataBean.getPhone());
            intent.putExtra("areaName", mDataBean.getAreaName());
            intent.putExtra("address", mDataBean.getAddress());
            intent.putExtra("area_id", mDataBean.getArea_id() + "");
            intent.putExtra("receiver_id", mDataBean.getReceiver_id() + "");

            setResult(REFUND_CODE, intent);
            finish();
        }
    }


    @Override
    public void showLoad() {
        showLoading();
    }

    @Override
    public void hideLoad() {
        hiddenLoadingView();
    }


    private StateController mStateController;


    @Override
    public void onNotNetWork() {
        mStateController.showError(false);
    }

    @Override
    public void attachState(MultiStateLayout multiStateLayout) {
        View view = LayoutInflater.from(this).inflate(R.layout.network_error_layout, multiStateLayout, false);
        HighlightButton mHighlightButton = view.findViewById(R.id.reset_load_data);
        mHighlightButton.setOnClickListener(v -> presenter.start(this));
        multiStateLayout.attachLayout(MultiStateLayout.STATE_ERROR, view);
    }

    @Override
    public Object getTarget() {
        return content;
    }

    @Override
    public void onRefTypeReturned(StateController stateController) {
        this.mStateController = stateController;
    }
}
