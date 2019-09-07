package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BankBean;
import com.yunqin.bearmall.ui.activity.BankAddVerifyActivity;
import com.yunqin.bearmall.ui.activity.VanguardListPageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/2/28 18:41
 */
public class BankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BankBean.DataBean.ListBean> mBankBeans;

    private static final int BANK = 0x01;
    private static final int BANK_BOTTOM = 0x02;
    private static final int BANK_BOTTOM_TYPE = 0x03;

    private boolean IS_COMPILE = false;

    private BankBean.DataBean.ListBean lastBean;


    public BankAdapter(Context mContext) {
        this.mContext = mContext;
        mBankBeans = new ArrayList<>();
    }


    public void updatelist(boolean isCompile) {
        this.IS_COMPILE = isCompile;
        notifyDataSetChanged();
    }


    public List<BankBean.DataBean.ListBean> getData() {
        return mBankBeans;
    }

    public void setBankBeans(List<BankBean.DataBean.ListBean> bankBeans) {
        this.mBankBeans = bankBeans;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == BANK) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_bank_layout, parent, false);
            return new BankViewHolder(view);
        } else if (viewType == BANK_BOTTOM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_bank_bottom_layout, parent, false);
            return new BankBottomViewHolder(view);
        } else if (viewType == BANK_BOTTOM_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_bank_bottom2_layout, parent, false);
            return new BankBottom2ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BankViewHolder) {
            BankBean.DataBean.ListBean listBean = mBankBeans.get(position);

            ((BankViewHolder) holder).bankName.setText(listBean.getBankName());
            ((BankViewHolder) holder).bankNumber.setText(listBean.getBankCard());
            ((BankViewHolder) holder).bankType.setText(listBean.getCardType());

            Glide.with(mContext).load(listBean.getIcon()).into(((BankViewHolder) holder).bankIcon);
            Glide.with(mContext).load(listBean.getImage()).into(((BankViewHolder) holder).bankImgBg);

            if (IS_COMPILE) {
                ((BankViewHolder) holder).mCheckBoxContainer.setVisibility(View.VISIBLE);

                ((BankViewHolder) holder).mCheckBox.setChecked(listBean.isChecked());

                // 直接遍历，不做标记
                ((BankViewHolder) holder).mCheckBoxContainer.setTag(position);
                ((BankViewHolder) holder).mCheckBoxContainer.setOnClickListener(view -> {
                    int position1 = (int) view.getTag();
                    for (int i = 0; i < mBankBeans.size(); i++) {
                        if (i == position1) {
                            lastBean = mBankBeans.get(i);
                            mBankBeans.get(i).setChecked(true);
                        } else {
                            mBankBeans.get(i).setChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                });
            } else {
                ((BankViewHolder) holder).mCheckBoxContainer.setVisibility(View.GONE);
            }
        } else if (holder instanceof BankBottomViewHolder) {
            ((BankBottomViewHolder) holder).addBankLayout.setOnClickListener(view -> BankAddVerifyActivity.startActivity(mContext, BankAddVerifyActivity.BankVerify.BANK_ADD, "0"));
            ((BankBottomViewHolder) holder).bangding_guize.setOnClickListener(view -> {
                String guidelUrl = BuildConfig.BASE_URL + "/view/getBindBankcardRules";
                VanguardListPageActivity.startH5Activity(mContext, guidelUrl, "绑定规则");
            });
        } else if (holder instanceof BankBottom2ViewHolder) {
            ((BankBottom2ViewHolder) holder)._cancel.setOnClickListener(view -> updatelist(false));
            ((BankBottom2ViewHolder) holder)._ok.setOnClickListener(view -> {
                if (lastBean != null) {
                    BankAddVerifyActivity.startActivity(mContext, BankAddVerifyActivity.BankVerify.BANK_SUBTRACT, String.valueOf(lastBean.getUserBankId()));
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return mBankBeans.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mBankBeans.size()) {
            if (IS_COMPILE) {
                return BANK_BOTTOM_TYPE;
            }
            return BANK_BOTTOM;
        }
        return BANK;
    }


    class BankViewHolder extends RecyclerView.ViewHolder {

        private ImageView bankIcon;
        private TextView bankName;
        private TextView bankType;
        private TextView bankNumber;
        private ImageView bankImgBg;

        private CheckBox mCheckBox;
        private RelativeLayout mCheckBoxContainer;


        public BankViewHolder(View itemView) {
            super(itemView);
            bankIcon = itemView.findViewById(R.id.bank_icon);
            bankName = itemView.findViewById(R.id.bank_name);
            bankType = itemView.findViewById(R.id.bank_type);
            bankNumber = itemView.findViewById(R.id.bank_number);
            bankImgBg = itemView.findViewById(R.id.bank_bg);

            mCheckBox = itemView.findViewById(R.id.check_box);
            mCheckBoxContainer = itemView.findViewById(R.id.check_box_container);


        }
    }

    class BankBottomViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout addBankLayout;
        private TextView bangding_guize;

        public BankBottomViewHolder(View itemView) {
            super(itemView);
            addBankLayout = itemView.findViewById(R.id.add_bank_layout);
            bangding_guize = itemView.findViewById(R.id.bangding_guize);
        }
    }


    class BankBottom2ViewHolder extends RecyclerView.ViewHolder {

        private TextView _cancel;
        private TextView _ok;

        public BankBottom2ViewHolder(View itemView) {
            super(itemView);

            _cancel = itemView.findViewById(R.id._cancel);
            _ok = itemView.findViewById(R.id._ok);


        }
    }


}
