package com.newversions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;

public class NewWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_new_web_view);

        WebView webView = findViewById(R.id.web_view);

        String storeId = getIntent().getStringExtra("store_id");

        String url = BuildConfig.BASE_URL + "/view/getBusinessQualification?store_id=" + storeId;

        webView.loadUrl(url);

        findViewById(R.id.new_version_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewWebViewActivity.this.finish();
            }
        });

    }
}
