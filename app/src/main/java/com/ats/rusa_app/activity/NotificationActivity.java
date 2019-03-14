package com.ats.rusa_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.ContentAdapter;
import com.ats.rusa_app.adapter.NotificationAdapter;
import com.ats.rusa_app.model.NotificationModel;
import com.ats.rusa_app.sqlite.DatabaseHandler;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rvList;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle(""+getResources().getString(R.string.str_notification));

        rvList = findViewById(R.id.rvList);

        db = new DatabaseHandler(this);

        ArrayList<NotificationModel> notificationList = db.getAllNotification();

        NotificationAdapter adapter = new NotificationAdapter(notificationList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
