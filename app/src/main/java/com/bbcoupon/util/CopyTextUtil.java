package com.bbcoupon.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.yunqin.bearmall.util.SharedPreferencesHelper;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/4/27
 */
public class CopyTextUtil {

    public static void CopyText(Context context, String content) {
        if (!TextUtils.isEmpty(content)) {
            SharedPreferencesHelper.put(context, "COPYCONTENT", content);
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, content));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isSameContent(Context context) {
        String content = (String) SharedPreferencesHelper.get(context, "COPYCONTENT", "null");
        ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        if (data == null || data.getItemCount() == 0) {
            return false;
        }
        ClipData.Item item = data.getItemAt(0);
        if (item == null || item.getText() == null || "null".equals(item.getText().toString())) {
            Log.e("BearMallAplication", "searchDialog: 2");
            return false;
        }
        String contents = item.getText().toString();
        if (contents.equals(content)) {
            return true;
        } else {
            return false;
        }
    }
}
