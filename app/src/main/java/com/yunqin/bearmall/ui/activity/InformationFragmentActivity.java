package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yunqin.bearmall.R;

public class InformationFragmentActivity extends AppCompatActivity {


    public static void start(Context context) {
        Intent intent = new Intent(context, InformationFragmentActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_fragment);
    }
}
