package com.ats.rusa_app.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.util.CommonDialog;

public class ConnectionCheckActivity extends AppCompatActivity {

    TextView tvRetry;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        setTitle(""+getResources().getString(R.string.app_name));
        tvRetry = findViewById(R.id.tvRetry);

        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constants.isOnline(ConnectionCheckActivity.this)) {
                    Intent intent = new Intent(ConnectionCheckActivity.this, MainActivity.class);
                    intent.putExtra("code", "SkipLogin");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{

                    final CommonDialog commonDialog = new CommonDialog(ConnectionCheckActivity.this, "Loading", "Please Wait...");
                    commonDialog.show();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           commonDialog.dismiss();
                        }
                    }, 3000);

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(ConnectionCheckActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
