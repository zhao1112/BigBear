package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.DailyIncomeBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/4/14
 */
public class DailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DailyIncomeBean.DataBean> list = new ArrayList<>();
    private int id;

    public DailyAdapter(Context context) {
        this.context = context;
    }

    public void addDataLis(List<DailyIncomeBean.DataBean> list, int id) {
        this.list.addAll(list);
        this.id = id;
        notifyDataSetChanged();
    }

    public void clearDataList() {
        this.list.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("onCreateViewHolder", "onCreateViewHolder: " + viewType);
        if (viewType == 0 || viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_adily_income, parent, false);
            return new DailyHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_adily_income_two, parent, false);
            return new DailyTwoHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DailyHolder) {
            DailyHolder dailyHolder = (DailyHolder) holder;
            dailyHolder.confirm.setText(doubleToString(list.get(position).getTradingmoney()));
            dailyHolder.PaymentPens.setText(list.get(position).getPaymentNum() + "");
            dailyHolder.Revenue.setText(doubleToString(list.get(position).getPaymentMoney()));
            dailyHolder.adily.setText(list.get(position).getTime());
            if (position == 0) {
                dailyHolder.adily.setBackground(context.getResources().getDrawable(R.mipmap.app_img_td));
            } else {
                dailyHolder.adily.setBackground(context.getResources().getDrawable(R.mipmap.app_img_blue));
            }
        }
        if (holder instanceof DailyTwoHolder) {
            DailyTwoHolder dailyTwoHolder = (DailyTwoHolder) holder;
            dailyTwoHolder.confirm.setText(doubleToString(list.get(position).getTradingmoney()));
            dailyTwoHolder.PaymentPens.setText(list.get(position).getPaymentNum() + "");
            dailyTwoHolder.Revenue.setText(doubleToString(list.get(position).getPaymentMoney()));
            dailyTwoHolder.data.setText(list.get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DailyHolder extends RecyclerView.ViewHolder {

        private final TextView adily, PaymentPens, Revenue, confirm;

        public DailyHolder(View itemView) {
            super(itemView);
            adily = itemView.findViewById(R.id.adily);
            PaymentPens = itemView.findViewById(R.id.PaymentPens);
            Revenue = itemView.findViewById(R.id.Revenue);
            confirm = itemView.findViewById(R.id.confirm);
        }
    }

    public class DailyTwoHolder extends RecyclerView.ViewHolder {

        private final TextView data, PaymentPens, Revenue, confirm;

        public DailyTwoHolder(View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.data);
            PaymentPens = itemView.findViewById(R.id.PaymentPens);
            Revenue = itemView.findViewById(R.id.Revenue);
            confirm = itemView.findViewById(R.id.confirm);
        }
    }

    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
