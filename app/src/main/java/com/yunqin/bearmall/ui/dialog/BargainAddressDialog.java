package com.yunqin.bearmall.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BargainAddressAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseDialog;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.ui.activity.AddAddrActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BargainAddressDialog extends BaseDialog implements View.OnClickListener, TextWatcher {
    private Context mContext;
    private RelativeLayout cancelLayout;
    private ListView listView;
    private RelativeLayout emptyLayout;
    private TextView addAddressTextView;
    private BargainAddressAdapter bargainAddressAdapter;


    private LinearLayout id_number_view;// 填写海关信息View
    private EditText input_id_number_edit;
    private Button id_number_commit;
    private FrameLayout frame_layout;
    private TextView dialog_title;


    public BargainAddressDialog(@NonNull Context context) {
        super(context, R.style.ProductDialog);
        mContext = context;

        //todo 王志韦  更改了list item样式
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bargain_address, null);
        cancelLayout = (RelativeLayout) view.findViewById(R.id.bargain_cancel_layout);
        listView = (ListView) view.findViewById(R.id.bargain_address_listview);
        emptyLayout = (RelativeLayout) view.findViewById(R.id.gargain_free_address_empty_layout);
        addAddressTextView = (TextView) view.findViewById(R.id.bargain_add_address);

        dialog_title = view.findViewById(R.id.dialog_title);

        id_number_view = view.findViewById(R.id.id_number_view);
        input_id_number_edit = view.findViewById(R.id.input_id_number_edit);
        id_number_commit = view.findViewById(R.id.id_number_commit);
        frame_layout = view.findViewById(R.id.frame_layout);
        id_number_commit.setOnClickListener(this);
        input_id_number_edit.addTextChangedListener(this);

        cancelLayout.setOnClickListener(this);
        addAddressTextView.setOnClickListener(this);

        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager m = ((Activity) context).getWindowManager();
        Window dialogWindow = getWindow();

        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display d = m.getDefaultDisplay();
        lp.width = (int) (d.getWidth() * 1);
        lp.height = (int) (d.getHeight() * 0.67);
        dialogWindow.setAttributes(lp);
    }

    public void showDialog() {
        show();
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bargain_cancel_layout:
                cancel();
                break;

            case R.id.bargain_add_address:
                intent = new Intent(mContext, AddAddrActivity.class);
                ((Activity) mContext).startActivityForResult(intent, 1);
                dismiss();
                break;
            case R.id.id_number_commit:

                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                goCommit();

                break;
            default:
                break;
        }
    }


    private AddressListBean.DataBean attachData;

    public void setData(String data, int flag) {

        AddressListBean bean = new Gson().fromJson(data, AddressListBean.class);
        if (bean.getCode() == 1 && bean.getData().size() > 0) {
            listView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);

            if (bargainAddressAdapter == null) {
                bargainAddressAdapter = new BargainAddressAdapter(mContext, bean.getData());
                listView.setAdapter(bargainAddressAdapter);
                bargainAddressAdapter.setOnReceiverIdListener(new BargainAddressAdapter.OnReceiverIdListener() {
                    @Override
                    public void onReceiverId(AddressListBean.DataBean receiver) {

                        // TODO 是否需要填写身份证号判断

                        attachData = receiver;

                        if (showIdNumber) {
                            // 需要
                            frame_layout.setVisibility(View.GONE);
                            id_number_view.setVisibility(View.VISIBLE);
                            addAddressTextView.setVisibility(View.GONE);

                            dialog_title.setText("完善信息");

                        } else {
                            // 不需要
                            cancel();
                            if (onBargainFreePart != null) {
                                onBargainFreePart.onBargainFreePart(receiver);
                            }
                        }


                    }
                });
            } else {
                bargainAddressAdapter.setData(bean.getData());
                bargainAddressAdapter.notifyDataSetChanged();
            }

        } else if (bean.getData().size() == 0) {
            if (flag == 2) {
                cancel();


            }
            listView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }


    public OnBargainFreePartListener onBargainFreePart;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            id_number_commit.setEnabled(true);
        } else {
            id_number_commit.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnBargainFreePartListener {
        void onBargainFreePart(AddressListBean.DataBean receiver);
    }

    public void setOnBargainFreePartListener(OnBargainFreePartListener onBargainFreePart) {
        this.onBargainFreePart = onBargainFreePart;
    }

    private IdNumber idNumber;

    public interface IdNumber {
        void onIdNumber(String idNumber);
    }

    public void setOnIdNumber(IdNumber idNumber) {
        this.idNumber = idNumber;
    }


    private boolean showIdNumber = false;

    public void setIdNumber(boolean show) {
        showIdNumber = show;
    }


    private void goCommit() {
        String idNumbers = input_id_number_edit.getText().toString();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("idCard", idNumbers);
        mHashMap.put("name", attachData.getConsignee());

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).idCardModify(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                String str = "认证失败";
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                String status = jsonObject1.optString("status", "");
                if (status.equals("01")) {
                    str = "认证通过！";
                    cancel();

                    if (idNumber != null) {
                        idNumber.onIdNumber(idNumbers);
                    }

                    if (onBargainFreePart != null) {
                        onBargainFreePart.onBargainFreePart(attachData);
                    }

                } else if (status.equals("02")) {
                    str = "实名认证不通过！";
                } else if (status.equals("202")) {
                    str = "无法验证！";
                } else if (status.equals("204")) {
                    str = "姓名格式不正确！";
                } else if (status.equals("205")) {
                    str = "身份证格式不正确！";
                }
                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotNetWork() {
                Toast.makeText(mContext, "请检查网络连接!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(mContext, "认证失败!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
