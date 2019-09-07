package com.newversions.push;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By Master
 * On 2019/1/18 17:00
 */
public class MyOneFragment extends BaseFragment {

    @BindView(R.id.search_edit_text)
    EditText search_edit_text;

    @BindView(R.id.fragment_tv_1)
    TextView fragment_tv_1;
    @BindView(R.id.fragment_tv_2)
    TextView fragment_tv_2;
    @BindView(R.id.fragment_tv_3)
    TextView fragment_tv_3;

    @BindView(R.id.push_recycler_view)
    RecyclerView push_recycler_view;

    private MyAdapter myAdapter;

    @Override
    public int layoutId() {
        return R.layout.new_push_fragment_mone;
    }

    @Override
    public void init() {
        search_edit_text.setHint("请输入M1进行查询");
        fragment_tv_1.setText("我的M1");
        fragment_tv_2.setText("拥有M2数量");
        fragment_tv_3.setText("返现金额");
        push_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter = new MyAdapter(getActivity());
        push_recycler_view.setAdapter(myAdapter);
        initData();
    }


    @OnClick({R.id.search_view})
    public void viewClick(View view) {
        initData();
    }


    private void initData() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("searchValue", search_edit_text.getText().toString());
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getMemberM1List(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
//                data = "{\n" +
//                        "\"msg\": \"请求成功\",\n" +
//                        "\"code\": 1,\n" +
//                        "\"data\":{\n" +
//                        "\"m1List\":[\n" +
//                        "{\n" +
//                        "\"buyingTime\": \"2018-12-24 12:56:29\",\n" +
//                        "\"m1Mobile\": \"15109990367\",\n" +
//                        "\"m2Count\": 0,\n" +
//                        "\"cashBack\": \"1.00\"\n" +
//                        "},\n" +
//                        "{\n" +
//                        "\"buyingTime\": \"2018-12-24 12:56:29\",\n" +
//                        "\"m1Mobile\": \"15101090367\",\n" +
//                        "\"m2Count\": 3,\n" +
//                        "\"cashBack\": \"1.00\"\n" +
//                        "}\n" +
//                        "]\n" +
//                        "}\n" +
//                        "}";


                M1Bean m1Bean = new Gson().fromJson(data, M1Bean.class);


                if (m1Bean != null && m1Bean.getData() != null) {
                    if (m1Bean.getData().getM1List() != null) {
                        myAdapter.setM1ListBeans(m1Bean.getData().getM1List());
                    } else {
                        myAdapter.setM1ListBeans(new ArrayList<>());
                    }
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


    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_DATA = 0x01;
        private static final int TYPE_EMPTY = 0x02;

        private Context context;
        private List<M1Bean.DataBean.M1ListBean> m1ListBeans;


        public MyAdapter(Context context) {
            this.context = context;
            m1ListBeans = new ArrayList<>();
        }

        public void setM1ListBeans(List<M1Bean.DataBean.M1ListBean> m1ListBeans) {
            if (m1ListBeans != null) {
                this.m1ListBeans = m1ListBeans;
                notifyDataSetChanged();
            }

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_DATA) {
                View view = LayoutInflater.from(context).inflate(R.layout.new_version_push_item, parent, false);
                return new MyViewHolder(view);
            } else if (viewType == TYPE_EMPTY) {
                View view = LayoutInflater.from(context).inflate(R.layout.new_version_empty_layout, parent, false);
                return new EmptyHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof MyViewHolder) {

                // 手机号中间四位替换成****
//                m1ListBeans.get(position).getM1Mobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                ((MyViewHolder) holder).tv_one.setText(m1ListBeans.get(position).getM1Mobile());
                ((MyViewHolder) holder).tv_two.setText(String.valueOf(m1ListBeans.get(position).getM2Count()));
                ((MyViewHolder) holder).tv_three.setText("¥" + m1ListBeans.get(position).getCashBack());
            }

        }

        @Override
        public int getItemCount() {
            return m1ListBeans.size() > 0 ? m1ListBeans.size() : 1;
        }

        @Override
        public int getItemViewType(int position) {
            return m1ListBeans.size() > 0 ? TYPE_DATA : TYPE_EMPTY;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_one;
            private TextView tv_two;
            private TextView tv_three;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_one = itemView.findViewById(R.id.tv_one);
                tv_two = itemView.findViewById(R.id.tv_two);
                tv_three = itemView.findViewById(R.id.tv_three);
            }
        }


        class EmptyHolder extends RecyclerView.ViewHolder {

            public EmptyHolder(View itemView) {
                super(itemView);
            }
        }
    }


}
