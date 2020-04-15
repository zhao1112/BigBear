package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.DailyIncomeBean;
import com.yunqin.bearmall.bean.MonthlyBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/4/14
 */
public class MonthlyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MonthlyBean.DataBean> list = new ArrayList<>();

    public MonthlyAdapter(Context context) {
        this.context = context;
    }

    public void addDataLis(List<MonthlyBean.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearDataList() {
        this.list.clear();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_monthly, parent, false);
        return new DailyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            DailyHolder dailyHolder = (DailyHolder) holder;
            dailyHolder.data.setText(list.get(position).getTime());
            dailyHolder.todayPaymentPens.setText(list.get(position).getPaymentNum() + "");
            dailyHolder.todayTransactionRevenue.setText(doubleToString(list.get(position).getPaymentMoney()));
            dailyHolder.confirm.setText(doubleToString(list.get(position).getTradingmoney()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DailyHolder extends RecyclerView.ViewHolder {

        private final TextView data, todayPaymentPens, todayTransactionRevenue, confirm;

        public DailyHolder(View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.data);
            todayPaymentPens = itemView.findViewById(R.id.todayPaymentPens);
            todayTransactionRevenue = itemView.findViewById(R.id.todayTransactionRevenue);
            confirm = itemView.findViewById(R.id.confirm);
        }
    }


    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
