package com.ats.rusa_app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.model.PreviousEvent;
import com.google.gson.Gson;

public class PreviousEventDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tv_eventName,tv_eventVenu,tv_eventDate;
    PreviousEvent previousEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_event_detail);
        imageView=(ImageView)findViewById(R.id.iv_baner);
        tv_eventName=(TextView)findViewById(R.id.tvEventName);
        tv_eventVenu=(TextView)findViewById(R.id.tvEventVenu);
        tv_eventDate=(TextView)findViewById(R.id.tvEventDate);

        String previousStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        previousEvent = gson.fromJson(previousStr, PreviousEvent.class);
        Log.e("responce","-----------------------"+previousEvent);

        tv_eventName.setText(previousEvent.getHeading());
        tv_eventVenu.setText("" + previousEvent.getEventLocation());
        tv_eventDate.setText("" + previousEvent.getEventDateFrom());
//        String imageUri =""+previousEvent.getFeaturedImage();
//        Log.e("URI", "-----------" + imageUri);
//        Picasso.with(getContext()).load(imageUri).into(imageView);
    }
}
