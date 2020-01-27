package com.ats.rusa_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

public class ImageZoomActivity extends AppCompatActivity {

    private ZoomageView zoomageView;

    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        zoomageView = findViewById(R.id.myZoomageView);

        try {

            image = getIntent().getExtras().getString("image");
           // Log.e("IMAGE PATH : ", " " + image);

            Picasso.with(this).load(image).placeholder(ImageZoomActivity.this.getResources().getDrawable(R.drawable.img_placeholder)).into(zoomageView);


        } catch (Exception e) {
        }


    }
}
