package com.ats.rusa_app.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.FullScreenImageAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.util.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FullScreenViewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FullScreenImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);
        setTitle(""+getResources().getString(R.string.app_name));

        viewPager = findViewById(R.id.pager);

        Gson gson = new Gson();

        String jsonReg = getIntent().getStringExtra("gallery");
        Type typeGallery = new TypeToken<ArrayList<GallaryDetailList>>() {
        }.getType();
        ArrayList<GallaryDetailList> galleryList = gson.fromJson(jsonReg, typeGallery);

        ArrayList<String> strImageArray = new ArrayList<>();

        if (galleryList != null) {
            if (galleryList.size() > 0) {

                for (int i = 0; i < galleryList.size(); i++) {
                    strImageArray.add("" + Constants.GALLERY_URL + "" + galleryList.get(i).getFileName());
                }

            }
        }


        adapter = new FullScreenImageAdapter(this, strImageArray);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int pos = getIntent().getIntExtra("position", 0);
        viewPager.setCurrentItem(pos);


    }
}
