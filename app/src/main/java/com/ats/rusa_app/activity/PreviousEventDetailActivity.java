package com.ats.rusa_app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.PrevEvent;
import com.ats.rusa_app.util.HtmlHttpImageGetter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class PreviousEventDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tv_eventName, tv_eventVenu, tv_eventDate;
    HtmlTextView tvEventDesc;
    PrevEvent previousEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_event_detail);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        setTitle("Previous Event");

        imageView = (ImageView) findViewById(R.id.iv_baner);
        tv_eventName = (TextView) findViewById(R.id.tvEventName);
        tv_eventVenu = (TextView) findViewById(R.id.tvEventVenu);
        tv_eventDate = (TextView) findViewById(R.id.tvEventDate);
        tvEventDesc = findViewById(R.id.tvEventDesc);

        String previousStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        previousEvent = gson.fromJson(previousStr, PrevEvent.class);
        Log.e("responce","-----------------------"+previousEvent);

        tv_eventName.setText(previousEvent.getHeading());
        //tv_eventVenu.setText("" + previousEvent.get());
        tv_eventDate.setText("" + previousEvent.getDate());
        tvEventDesc.setHtml(previousEvent.getDescriptions(), new HtmlHttpImageGetter(tvEventDesc));

        try {
            String imageUri = Constants.GALLERY_URL + "" + previousEvent.getFeaturedImage();
            Log.e("URI", "-----------" + imageUri);
            Picasso.with(getApplicationContext()).load(imageUri).placeholder(getResources().getDrawable(R.drawable.logo_new)).into(imageView);
        } catch (Exception e) {
            Log.e("Exception  : ", "-----------" + e.getMessage());
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
