package com.ats.rusa_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ats.rusa_app.R;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.sqlite.DatabaseHandler;
import com.ats.rusa_app.util.Constant;
import com.ats.rusa_app.util.CustomSharedPreference;

public class SplashActivity extends AppCompatActivity {
Login loginUser;
DatabaseHandler dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);

        initLayout();

//        String token= UUID.randomUUID().toString();
//
//        getUpdateToken(loginUser.getRegId(),token);
    }


    private void initLayout() {
        String languageToLoad;

        CustomSharedPreference.putString(SplashActivity.this, CustomSharedPreference.LANGUAGE_SELECTED, CustomSharedPreference.LANGUAGE_ENG_ID);


        int languageId = Integer.parseInt(CustomSharedPreference.LANGUAGE_ENG_ID);
        if(CustomSharedPreference.getString(SplashActivity.this,CustomSharedPreference.LANGUAGE_ENG)!=null)
        {

            if(languageId == 2)
            {
                languageToLoad = CustomSharedPreference.LANGUAGE_MAR;
            }
            else
            {
                languageToLoad = CustomSharedPreference.LANGUAGE_ENG;
            }

            Constant.yourLanguage(SplashActivity.this, "en");
             /*Locale locale = new Locale(languageToLoad);
             Locale.setDefault(locale);
             Configuration config = new Configuration();
             config.locale = locale;
             getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());*/
        }

//        if(CustomSharedPreference.getString(SplashActivity.this,CustomSharedPreference.LANGUAGE_ENG)!=null)
//        {
//            new Handler().postDelayed(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    //Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 5000);
//        }
//        else
//        {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 5000);
//        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                finish();
//            }
//        }, 3000);
//
//    }
}
