package com.bbcoupon.widget;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bbcoupon.util.DialogUtil;
import com.google.gson.Gson;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.utils.SharedPreferencesUtils;
import com.newversions.tbk.view.SearchDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.SuperSearchActivity;
import com.yunqin.bearmall.widget.LoadingView;
import com.yunqin.bearmall.widget.OpenGoodsDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon
 * @DATE 2020/4/23
 */
public class SuperSearch {

    public static SearchDialog mSearchDialog = null;
    public static String content;

    public static void searchDialog(Context context, LoadingView loadingProgress) {
        try {
            Boolean bFirst = (Boolean) SharedPreferencesUtils.getParam(context, Constants.isFirstOpen, true);
            if (BearMallAplication.getInstance().getUser() == null) {
                return;
            }
            if (loadingProgress != null && loadingProgress.isShowing()) {
                return;
            }
            if (BearMallAplication.isFirst && BearMallAplication.isFirst2) {
                Log.e("BearMallAplication", "searchDialog: " + BearMallAplication.isFirst + "------" + BearMallAplication.isFirst2);
                return;
            }
            ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData data = cm.getPrimaryClip();
            if (data == null || data.getItemCount() == 0) {
                return;
            }
            ClipData.Item item = data.getItemAt(0);
            if (item == null || item.getText() == null || "null".equals(item.getText().toString())) {
                Log.e("BearMallAplication", "searchDialog: 2");
                return;
            }
            content = item.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                if (mSearchDialog == null) {
                    mSearchDialog = new SearchDialog(context, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                cm.setPrimaryClip(cm.getPrimaryClip());
                                cm.setText(null);
                                BearMallAplication.isFirst = true;
                                BearMallAplication.isFirst2 = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mSearchDialog.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                cm.setPrimaryClip(cm.getPrimaryClip());
                                cm.setText(null);
                                BearMallAplication.isFirst = true;
                                BearMallAplication.isFirst2 = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mSearchDialog.dismiss();
                            HashMap<String, String> map = new HashMap<>();
                            map.put("content", content);
                            if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
                                map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
                            }
                            Log.i("jsonObject", content);
                            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).findCommodityIdByTpwd(map),
                                    new RetrofitApi.IResponseListener() {
                                        @Override
                                        public void onSuccess(String data) throws JSONException {
                                            JSONObject object = new JSONObject(data);
                                            if (object != null) {
                                                if (object.optInt("code") == 1) {
                                                    com.yunqin.bearmall.bean.SuperSearch superSearch = new Gson().fromJson(data,
                                                            com.yunqin.bearmall.bean.SuperSearch.class);
                                                    if (superSearch != null && superSearch.getData().size() > 0) {
                                                        if (superSearch.getData().size() == 1) {
                                                            GoodsDetailActivity.startGoodsDetailActivity(context,
                                                                    superSearch.getData().get(0).getTao_id(),
                                                                    Constants.GOODS_TYPE_TBK_SEARCH, Constants.COMMISSION_TYPE_THREE);
                                                        } else {
                                                            SuperSearchActivity.openSuperSearchActivity(context, superSearch,
                                                                    content);
                                                        }
                                                    } else {
                                                        OpenGoodsDetail.showDialog(context);
                                                    }
                                                } else if (object.optInt("code") == 2) {
                                                    if (TextUtils.isEmpty(object.optString("data"))) {
                                                        OpenGoodsDetail.showDialog(context);
                                                    } else {
                                                        GoodsDetailActivity.startGoodsDetailActivity(context, object.optString(
                                                                "data"), Constants.GOODS_TYPE_TBK_SEARCH, Constants.COMMISSION_TYPE_THREE);
                                                    }
                                                } else {
                                                    OpenGoodsDetail.showDialog(context);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNotNetWork() {
                                            Log.i("jsonObject", "---------");
                                        }

                                        @Override
                                        public void onFail(Throwable e) {
                                            Log.i("jsonObject", "---------");
                                        }
                                    });
                        }
                    });
                }
                if (!mSearchDialog.isShowing()) {
                    mSearchDialog.setMessage(content);
                    mSearchDialog.show();
                } else {
                    mSearchDialog.setMessage(content);
                }
                cm.setText(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

