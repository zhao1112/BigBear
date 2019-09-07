package com.yunqin.bearmall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ADGiveCandyActivity extends AppCompatActivity {


//    private FoxWallView mFoxWallView;
//    private FoxInfoStreamView mFoxWallView;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ADGiveCandyActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adgive_candy);

//        mFoxWallView = findViewById(R.id.app1);
//        mFoxWallView.setAdListener(new FoxListener() {
//            @Override
//            public void onReceiveAd() {
//                Log.e("TAG-CP", "onReceiveAd");
//            }
//
//            @Override
//            public void onFailedToReceiveAd() {
//                Log.e("TAG-CP", "onFailedToReceiveAd");
//            }
//
//            @Override
//            public void onLoadFailed() {
//                Log.e("TAG-CP", "onLoadFailed");
//            }
//
//            @Override
//            public void onCloseClick() {
//                Log.e("TAG-CP", "onCloseClick");
//            }
//
//            @Override
//            public void onAdClick() {
//                Log.e("TAG-CP", "onAdClick");
//            }
//
//            @Override
//            public void onAdExposure() {
//                Log.e("TAG-CP", "onAdExposure");
//            }
//        });
//
//        mFoxWallView.loadAd(289989);

    }


    @Override
    protected void onDestroy() {
//        if (mFoxWallView != null) {
//            mFoxWallView.destroy();
//        }
        super.onDestroy();
    }
}
