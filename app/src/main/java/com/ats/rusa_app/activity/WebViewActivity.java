package com.ats.rusa_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.ats.rusa_app.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    String tableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webview);
        //webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.setInitialScale(220);

        tableData = "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "   border-collapse: collapse;\n" +
                "\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>";

        tableData = tableData + (getIntent().getStringExtra("EXTRA_TABLE_HTML"));

        tableData = tableData + "</table>\n" +
                "                </body>\n" +
                "</html>";


        webView.loadData(tableData, "text/html", "utf-8");

        Log.e("Table Data : ", "----------- " + tableData);

    }
}
