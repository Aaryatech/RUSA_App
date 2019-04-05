package com.ats.rusa_app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.ContentAdapter;
import com.ats.rusa_app.adapter.NotificationAdapter;
import com.ats.rusa_app.model.NotificationModel;
import com.ats.rusa_app.sqlite.DatabaseHandler;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rvList;
    DatabaseHandler db;

    private BroadcastReceiver mBroadcastReceiver;
    ArrayList<NotificationModel> notificationList=new ArrayList<>();
    NotificationAdapter adapter;

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

        adapter = new NotificationAdapter(notificationList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(adapter);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("REFRESH_NOTIFICATION")) {
                    handlePushNotification1(intent);
                }
            }
        };

    }


    @Override
    public void onPause() {

        LocalBroadcastManager.getInstance(NotificationActivity.this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        notificationList.clear();
        notificationList = db.getAllNotification();
        adapter.notifyDataSetChanged();

        Log.e("NOTIFICATION ACT","------------------- ON_RESUME");

        LocalBroadcastManager.getInstance(NotificationActivity.this).registerReceiver(mBroadcastReceiver,
                new IntentFilter("REFRESH_NOTIFICATION"));

    }


    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification1", "------------------------------------**********");

        notificationList.clear();
        notificationList=db.getAllNotification();

        adapter = new NotificationAdapter(notificationList, this);
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
