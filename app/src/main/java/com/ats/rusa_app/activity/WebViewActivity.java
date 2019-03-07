package com.ats.rusa_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.ats.rusa_app.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView=findViewById(R.id.webview);
        //webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.setInitialScale(220);

        String tableData=getIntent().getStringExtra("EXTRA_TABLE_HTML");

        webView.loadData(tableData, "text/html", "utf-8");

        Log.e("Table Data : ","----------- "+tableData);

    }
}
