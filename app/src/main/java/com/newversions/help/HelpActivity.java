package com.newversions.help;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView recyclerView;
    private ViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_version_activity_help);
        recyclerView = findViewById(R.id.help_recycler_view);
        findViewById(R.id.new_version_back).setOnClickListener(this);
        viewAdapter = new ViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewAdapter);
        initRHttp();
    }

    private void initRHttp() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getQuestionList(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                HelpBean helpBean = new Gson().fromJson(data, HelpBean.class);
                viewAdapter.setData(helpBean.getData().getList());
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        finish();
    }


    class ViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_DATA = 0x01;
        private static final int TYPE_EMPTY = 0x02;

        private Context context;
        private List<HelpBean.DataBean.ListBean> list;

        public ViewAdapter(Context context) {
            this.context = context;
            list = new ArrayList<>();
        }

        public void setData(List<HelpBean.DataBean.ListBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.new_help_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).help_wen_ti.setText(list.get(position).getQuestion());
            ((ViewHolder) holder).help_da_an.setText(list.get(position).getAnswer());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView help_wen_ti;
            private TextView help_da_an;

            public ViewHolder(View itemView) {
                super(itemView);
                help_wen_ti = itemView.findViewById(R.id.help_wen_ti);
                help_da_an = itemView.findViewById(R.id.help_da_an);
            }
        }


    }


}
