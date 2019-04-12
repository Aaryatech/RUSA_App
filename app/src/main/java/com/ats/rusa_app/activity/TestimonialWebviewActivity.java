package com.ats.rusa_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.TestImonialList;
import com.ats.rusa_app.util.Config;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestimonialWebviewActivity extends AppCompatActivity {

    private LinearLayout llHtml, llWebview, llVideo;
    private HtmlTextView tvHtmlText;
    private TextView tvTitle;
    private WebView webView;
    private CircleImageView ivImg;
    private YouTubeThumbnailView ytThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_display);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        llHtml = findViewById(R.id.llHtml);
        llWebview = findViewById(R.id.llWebview);
        llVideo = findViewById(R.id.llVideo);
        tvHtmlText = findViewById(R.id.tvHtmlText);
        tvTitle = findViewById(R.id.tvTitle);
        webView = findViewById(R.id.webView);
        ivImg = findViewById(R.id.ivImg);
        ytThumb = findViewById(R.id.ytThumb);

        Gson gson = new Gson();

        String jsonStr = getIntent().getStringExtra("model");

        TestImonialList model = gson.fromJson(jsonStr, TestImonialList.class);

        if (model != null) {

            setTitle("Testimonials");
            tvTitle.setText("" + model.getFromName());

            try {
                Picasso.with(this).load(Constants.GALLERY_URL + model.getImageName()).placeholder(R.drawable.profile_img).into(ivImg);
            } catch (Exception e) {
            }

            String htmlText = model.getMessage();

            tvHtmlText.setHtml("" + model.getMessage(), new HtmlHttpImageGetter(tvHtmlText));

            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(htmlText, "text/html", "utf-8");


            if (htmlText.contains("iframe")) {


                String iframeStr = htmlText;
                Log.e("STRING ", "---------- " + iframeStr);

                int index = (iframeStr.lastIndexOf("src"));
                Log.e("FIRST INDEX : ", "-------------------- " + index);
                int firstIndex = (iframeStr.indexOf('"', index)) + 1;
                Log.e("LAST INDEX : ", "-------------------- " + firstIndex);
                int lastIndex = iframeStr.indexOf('"', firstIndex);


                Log.e("VIDEO URL : ", "------------------------ " + iframeStr.substring(firstIndex, lastIndex));

                final String code = extractYTId(iframeStr.substring(firstIndex, lastIndex)).replace("\\", "");

                Log.e("VIDEO CODE : ", "------------------------ " + code);

                // GallaryDetailList videoModel = new GallaryDetailList(1, 1, 1, 1, "1", "video 1", "aa", "", "", 1, "", "", 1, 1, 1, 1, 1, 1, "" + code, "");
                // videoList.add(videoModel);

                ytThumb.initialize(Config.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        youTubeThumbnailLoader.setVideo(code);

                        Log.e("Video model", "----------------" + code);
                        //model.getExVar1()
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                youTubeThumbnailLoader.release();
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });


                ytThumb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TestimonialWebviewActivity.this, YoutubePlayerActivity.class);
                        intent.putExtra("video", code);
                        startActivity(intent);
                    }
                });


                llHtml.setVisibility(View.GONE);
                llVideo.setVisibility(View.VISIBLE);
            } else {
                llVideo.setVisibility(View.GONE);
                llHtml.setVisibility(View.VISIBLE);
            }


        }


    }

    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
