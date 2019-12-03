package com.yunqin.bearmall.ui.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.FansItemAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.StairFans;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2019/11/28
 */
public class FansFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;

    private String title;
    private int type;
    private FansItemAdapter mFansItemAdapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_fans;
    }

    @Override
    public void init() {

        Bundle arguments = getArguments();
        title = arguments.getString("title");

        if (title.equals("全部")) {
            type = 0;
        } else if (title.equals("直邀粉丝")) {
            type = 1;
        } else if (title.equals("推荐粉丝")) {
            type = 2;
        }

        mFansItemAdapter = new FansItemAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mFansItemAdapter);

        showLoading();
        getFans();
    }

    private void getFans() {
        Map<String, String> map = new HashMap<>();
        map.put("customerId", "");
        map.put("openTime", "0");
        map.put("openCount", "0");
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).StairFans(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
//                StairFans stairFans = new Gson().fromJson(data, StairFans.class);
//                if (type == 0) {
//                    if (stairFans.getData().getOneList().size() > 0 && stairFans.getData().getOneList() != null
//                            || stairFans.getData().getTwoList().size() > 0 && stairFans.getData().getTwoList() != null) {
//                        mNulldata.setVisibility(View.GONE);
//                        mFansItemAdapter.addFansOne(stairFans.getData().getOneList());
//                        mFansItemAdapter.addFansTwo(stairFans.getData().getTwoList());
//                    } else {
//                        mNulldata.setVisibility(View.VISIBLE);
//                    }
//                } else if (type == 1) {
//                    if (stairFans.getData().getOneList().size() > 0 && stairFans.getData().getOneList() != null) {
//                        mNulldata.setVisibility(View.GONE);
//                        mFansItemAdapter.addFansOne(stairFans.getData().getOneList());
//                    } else {
//                        mNulldata.setVisibility(View.VISIBLE);
//                    }
//                } else if (type == 2) {
//                    if (stairFans.getData().getTwoList().size() > 0 && stairFans.getData().getTwoList() != null) {
//                        mNulldata.setVisibility(View.GONE);
//                        mFansItemAdapter.addFansTwo(stairFans.getData().getTwoList());
//                    } else {
//                        mNulldata.setVisibility(View.VISIBLE);
//                    }
//                }
                hiddenLoadingView();
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                mNulldata.setVisibility(View.VISIBLE);
                Log.i("SecondFans", "onNotNetWork: ");
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                mNulldata.setVisibility(View.VISIBLE);
                Log.i("SecondFans", "onFail: ");
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
