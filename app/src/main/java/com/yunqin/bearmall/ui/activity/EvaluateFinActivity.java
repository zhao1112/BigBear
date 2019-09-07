package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.WaitCommentBean;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评价完成Activity
 *
 * @author Master
 */
public class EvaluateFinActivity extends BaseActivity {


    @BindView(R.id.check_pingjia)
    TextView check_pingjia;

    @BindView(R.id.bottom_layout)
    LinearLayout bottom_layout;

    @BindView(R.id.list_view)
    ListView list_view;

    @BindView(R.id.toolbar_title)
    TextView textView;


    @Override
    public int layoutId() {
        return R.layout.activity_evaluate_fin;
    }

    @Override
    public void init() {
        textView.setText("评价成功");
        initNoEvaluationList();
    }

    private void initNoEvaluationList() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "1");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getReviewBasicInfo(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                WaitCommentBean waitCommentBean = new Gson().fromJson(data, WaitCommentBean.class);
                list_view.setAdapter(new IAdapter(EvaluateFinActivity.this, waitCommentBean));
            }

            @Override
            public void onNotNetWork() {
                bottom_layout.setVisibility(View.GONE);
            }

            @Override
            public void onFail(Throwable e) {
                bottom_layout.setVisibility(View.GONE);
            }
        });
    }


    @OnClick({R.id.toolbar_back, R.id.check_pingjia})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.check_pingjia:
                StarActivityUtil.starActivity(this, MyAllCommentActivity.class);
                finish();
                break;
            default:
                break;
        }

    }


    class IAdapter extends BaseAdapter {

        private Context mContext;
        private WaitCommentBean waitCommentBean;

        public IAdapter(Context context, WaitCommentBean waitCommentBean) {
            this.mContext = context;
            this.waitCommentBean = waitCommentBean;
        }

        @Override
        public int getCount() {
            if (waitCommentBean != null && waitCommentBean.getData() != null && waitCommentBean.getData().getItemList() != null) {
                return waitCommentBean.getData().getItemList().size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return waitCommentBean.getData().getItemList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            IViewHolder iViewHolder;

            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_daipingjia, null);
                iViewHolder = new IViewHolder(convertView);
                convertView.setTag(iViewHolder);
            } else {
                iViewHolder = (IViewHolder) convertView.getTag();
            }

            if (waitCommentBean.getData().getItemList().get(position) != null &&
                    waitCommentBean.getData().getItemList().get(position).getThumbnail() != null) {
                Glide.with(mContext)
                        .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_shopcar_product))
                        .load(waitCommentBean.getData().getItemList().get(position).getThumbnail())
                        .into(iViewHolder.img_pic);
            }
            iViewHolder.tv_title.setText(waitCommentBean.getData().getItemList().get(position).getItemName());
            iViewHolder.shaidan.setOnClickListener(v -> goToComment(waitCommentBean.getData().getItemList().get(position).getOrders_id()));
            return convertView;
        }


        class IViewHolder {
            ImageView img_pic;
            TextView tv_title;
            Button shaidan;

            public IViewHolder(View itemView) {
                img_pic = itemView.findViewById(R.id.img_pic);
                tv_title = itemView.findViewById(R.id.tv_title);
                shaidan = itemView.findViewById(R.id.shaidan);
            }
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    private void goToComment(int orders_id) {

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orders_id", String.valueOf(orders_id));
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getReviewBasicInfos(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                try {

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
                        StarActivityUtil.starActivity(EvaluateFinActivity.this, CommentActivity.class, bundle);
                        finish();
                    } else {
                        Toast.makeText(EvaluateFinActivity.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(EvaluateFinActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }


}
