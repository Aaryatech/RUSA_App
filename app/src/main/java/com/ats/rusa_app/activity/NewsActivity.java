package com.ats.rusa_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.NewDetail;
import com.ats.rusa_app.model.TestImonialList;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class NewsActivity extends AppCompatActivity {

    private LinearLayout llHtml, llWebview;
    private HtmlTextView tvHtmlText;
    private TextView tvTitle;
    private WebView webView;
    private ImageView ivImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        llHtml = findViewById(R.id.llHtml);
        llWebview = findViewById(R.id.llWebview);
        tvHtmlText = findViewById(R.id.tvHtmlText);
        tvTitle = findViewById(R.id.tvTitle);
        webView = findViewById(R.id.webView);
        ivImg = findViewById(R.id.ivImg);

        Gson gson = new Gson();

        String jsonStr = getIntent().getStringExtra("model");

        NewDetail model = gson.fromJson(jsonStr, NewDetail.class);

        if (model != null) {

            setTitle(""+getResources().getString(R.string.str_latest_news));
            tvTitle.setText(""+model.getHeading());

            try {
                Picasso.with(this).load(Constants.GALLERY_URL + model.getFeaturedImage()).placeholder(R.drawable.rusa_logo).into(ivImg);
            } catch (Exception e) {
            }

            String htmlText = model.getDescriptions();

            tvHtmlText.setHtml("" + htmlText, new HtmlHttpImageGetter(tvHtmlText));

            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(htmlText, "text/html", "utf-8");


            if (htmlText.contains("iframe")) {
                llHtml.setVisibility(View.GONE);
                llWebview.setVisibility(View.VISIBLE);
            } else {
                llWebview.setVisibility(View.GONE);
                llHtml.setVisibility(View.VISIBLE);
            }


        }


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}


