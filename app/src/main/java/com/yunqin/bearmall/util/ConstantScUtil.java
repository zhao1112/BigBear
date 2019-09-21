package com.yunqin.bearmall.util;


import android.util.Log;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2019/9/21
 */
public class ConstantScUtil {

//    private JSONObject mJSONObject;

//    public ConstantScUtil() {
//        mJSONObject = new JSONObject();
//    }
//
//    public void putString(String key, String value) {
//        try {
//            mJSONObject.put(key, value);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sensorsData(String sensors) {
//        SensorsDataAPI.sharedInstance().track(sensors, mJSONObject);
//    }

    public static void sensorsTrack(String eventName, Map<String, String> props) {
        if (props != null && props.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            for (String key : props.keySet()) {
                try {
                    if (!StringUtils.isEmpty(key)) {
                        try {
                            jsonObject.put(key, props.get(key));
                            Log.i("sensorsTrack", key);
                            Log.i("sensorsTrack", props.get(key));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            SensorsDataAPI.sharedInstance().track(eventName, jsonObject);
        }
    }
}
