package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newversions.view.DrawMoneyDialog;
import com.newversions.view.IBankDialog;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.BankBean;
import com.yunqin.bearmall.util.CashierInputFilter;
import com.yunqin.bearmall.widget.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BalanceWithdrawalWxActivity extends BaseActivity implements View.OnClickListener {


    private LoadingView loadingProgress;
    private int selectPosition = 0;

    private void showLoadings() {
        if (loadingProgress == null) {
            loadingProgress = LoadingView.createDialog(this);
            loadingProgress.setCancelable(false);
            loadingProgress.setCanceledOnTouchOutside(false);
        }
        loadingProgress.show();
    }

    private void hideLoadings() {
        if (loadingProgress != null) {
            loadingProgress.dismiss();
        }
    }


    private String money;

    private TextView _yue;

    public static void startActivity(Context context, String money) {
        Intent intent = new Intent(context, BalanceWithdrawalWxActivity.class);
        intent.putExtra("money", money);
        context.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_balance_withdrawal;
    }

    @Override
    public void init() {
        money = getIntent().getStringExtra("money");

        initViews();
    }

    private EditText edit_text;
    private ImageView bank_icon;
    private TextView bank_name;

    private void initViews() {
        _yue = findViewById(R.id._yue);
        edit_text = findViewById(R.id.edit_text);
        bank_icon = findViewById(R.id.bank_icon);
        bank_name = findViewById(R.id.bank_name);
        _yue.setText(String.format("当前余额%s元，", money));

        findViewById(R.id.yhk_visible).setVisibility(View.GONE);
        findViewById(R.id.wx_visible).setVisibility(View.VISIBLE);


        findViewById(R.id.new_version_back).setOnClickListener(this);
        findViewById(R.id.select_bank_).setOnClickListener(this);
        findViewById(R.id.all_money).setOnClickListener(this);
        findViewById(R.id.ti_xian_btn).setOnClickListener(this);

        CashierInputFilter cashierInputFilter = new CashierInputFilter();
        InputFilter[] inputFilters = new InputFilter[]{cashierInputFilter};
        edit_text.setFilters(inputFilters);

//        initData(false);


    }

    private BankBean bankBean;

    private void initData(boolean showDialog) {
        showLoadings();
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getUsersBankInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hideLoadings();
                bankBean = new Gson().fromJson(data, BankBean.class);
                setBankData(bankBean.getData().getList().get(selectPosition));
                if (showDialog) {
                    showDialog();
                }
            }

            @Override
            public void onNotNetWork() {
                hideLoadings();
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadings();
            }
        });
    }

    private void setBankData(BankBean.DataBean.ListBean listBean) {
        bank_name.setText(String.format("%s(%s)", listBean.getBankName(), listBean.getBankCard().substring(listBean.getBankCard().length() - 4)));
        Glide.with(BalanceWithdrawalWxActivity.this).load(bankBean.getData().getList().get(0).getIcon()).into(bank_icon);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.new_version_back:
                finish();
                break;
            case R.id.select_bank_:

                if (bankBean == null) {
                    initData(true);
                } else {
                    showDialog();
                }

                break;
            case R.id.all_money:
                edit_text.setText(money);
                break;
            case R.id.ti_xian_btn:
                hiddenKeyboard();
                drawMoney();
                break;
            default:
                break;
        }

    }

    private void drawMoney() {

        if (edit_text.getText().toString().isEmpty()) {
            return;
        }

        double drawMoney = Double.parseDouble(edit_text.getText().toString());
        double money = Double.parseDouble(this.money);
        if (money >= drawMoney) {
            CalculationAmount(drawMoney);
        } else {
            Toast.makeText(this, "余额不足", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * @param drawMoney
     */

    private void CalculationAmount(double drawMoney) {

        showLoadings();

        Map<String, String> map = new HashMap<>();
        map.put("amount", drawMoney + "");

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).calculateWithdrawAmount(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                hideLoadings();

                JSONObject jsonObject = new JSONObject(data);

                if (jsonObject.optInt("code") != 1) {
                    Toast.makeText(BalanceWithdrawalWxActivity.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject js = jsonObject.optJSONObject("data");

                new DrawMoneyDialog.Builder(BalanceWithdrawalWxActivity.this)
                        .setCanceledOnTouchOutside(true)
                        .setCancelable(true)
                        .setDialogPosition(Gravity.CENTER)
                        .setAnimationResId(0)
                        .setOnDialogContent(String.format("额外扣除¥%S(%s)手续费", js.optString("serviceFee"), js.optString("serviceRatio")))
                        .setOnDialogPrice("¥" + drawMoney)
                        .setOnDialogItemClickListener(new DrawMoneyDialog.OnDialogItemClickListener() {
                            @Override
                            public void onDialogItemClick(final DrawMoneyDialog thisDialog, String data) {
                                showLoadings();

                                Map<String, String> mHashMap = new HashMap<>();
                                mHashMap.put("paymentPwd", data);

                                RetrofitApi.request(BalanceWithdrawalWxActivity.this, RetrofitApi.createApi(Api.class).validPayPassword(mHashMap), new RetrofitApi.IResponseListener() {
                                    @Override
                                    public void onSuccess(String data) throws JSONException {


                                        JSONObject jsonObject = new JSONObject(data);

                                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                                        String resValidCode = null;
                                        if (jsonObject1 != null) {
                                            resValidCode = jsonObject1.optString("resValidCode");
                                        }


                                        Map<String, String> wcnmap = new HashMap<>();
                                        wcnmap.put("amount", drawMoney + "");
                                        wcnmap.put("type", "0");
                                        wcnmap.put("resValidCode", resValidCode + "");


                                        RetrofitApi.request(getApplicationContext(), RetrofitApi.createApi(Api.class).applyWithdraw(wcnmap), new RetrofitApi.IResponseListener() {
                                            @Override
                                            public void onSuccess(String data) throws JSONException {

                                                WithdrawActivity.startActivity(BalanceWithdrawalWxActivity.this);
                                                BalanceWithdrawalWxActivity.this.finish();
                                            }

                                            @Override
                                            public void onNotNetWork() {
                                                thisDialog.dismiss();
                                            }

                                            @Override
                                            public void onFail(Throwable e) {
                                                thisDialog.dismiss();
                                            }
                                        });


                                    }

                                    @Override
                                    public void onNotNetWork() {
                                        hideLoadings();
                                    }

                                    @Override
                                    public void onFail(Throwable e) {
                                        hideLoadings();
                                        thisDialog.dismiss();

                                        new ICustomDialog.Builder(BalanceWithdrawalWxActivity.this)
                                                // 设置布局
                                                .setLayoutResId(R.layout.bank_psd_error)
                                                // 点击空白是否消失
                                                .setCanceledOnTouchOutside(true)
                                                // 点击返回键是否消失
                                                .setCancelable(true)
                                                // 设置Dialog的绝对位置
                                                .setDialogPosition(Gravity.CENTER)
                                                // 设置自定义动画
                                                .setAnimationResId(0)
                                                // 设置监听ID
                                                .setListenedItems(new int[]{R.id.ok})
                                                // 设置回掉
                                                .setOnDialogItemClickListener((thisDialog, clickView) -> {

                                                    if (edit_text.getText().toString().isEmpty()) {
                                                        return;
                                                    }
                                                    double drawMoney = Double.parseDouble(edit_text.getText().toString());
                                                    CalculationAmount(drawMoney);
                                                    thisDialog.dismiss();
                                                })
                                                .build().show();


                                    }
                                });


                            }
                        })
                        .build().show();


            }

            @Override
            public void onNotNetWork() {

                hideLoadings();

            }

            @Override
            public void onFail(Throwable e) {

                hideLoadings();

            }
        });


    }


    /**
     * 关闭键盘
     */
    public void hiddenKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void showDialog() {
        new IBankDialog.Builder(this)
                // 点击空白是否消失
                .setCanceledOnTouchOutside(true)
                // 点击返回键是否消失
                .setCancelable(true)
                // 设置Dialog的绝对位置
                .setDialogPosition(Gravity.BOTTOM)
                // 设置监听ID
                .setListenedItems(new int[]{R.id.add_bank})
                // 设置数据
                .setBankData(bankBean.getData().getList())
                .setSelectPosition(selectPosition)
                // 设置回掉
                .setOnDialogItemClickListener(new IBankDialog.OnDialogItemClickListener() {
                    @Override
                    public void onDialogItemClick(IBankDialog thisDialog, View clickView) {

                        if (R.id.add_bank == clickView.getId()) {
                            BankAddVerifyActivity.startActivity(BalanceWithdrawalWxActivity.this, BankAddVerifyActivity.BankVerify.BANK_ADD, "0");
                        } else {
                            selectPosition = (int) clickView.getTag();
                            setBankData(bankBean.getData().getList().get(selectPosition));
                        }
                        thisDialog.dismiss();
                    }
                })
                .build().show();
    }


}
