package com.ats.rusa_app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.PrevEvent;
import com.ats.rusa_app.model.PrevEventFeedback;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.ats.rusa_app.util.HtmlHttpImageGetter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousEventDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tv_eventName, tv_eventFeedback,tv_eventFeedbackValue, tv_eventDate;
    HtmlTextView tvEventDesc;
    PrevEvent previousEvent;
    Login loginUser;
    LinearLayout linearLayoutFeedback;
    PrevEventFeedback model;
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
        tv_eventFeedback = (TextView) findViewById(R.id.tvEventFeedback);
        tv_eventFeedbackValue = (TextView) findViewById(R.id.tvEventFeedbackValue);
        tv_eventDate = (TextView) findViewById(R.id.tvEventDate);
        tvEventDesc = findViewById(R.id.tvEventDesc);
        linearLayoutFeedback=findViewById(R.id.linearLayoutFeedback);
        
        String previousStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        previousEvent = gson.fromJson(previousStr, PrevEvent.class);
        Log.e("responce","-----------------------"+previousEvent);

        String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        getPrevEvetFeedback(loginUser.getRegId(),previousEvent.getNewsblogsId());

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
    private void getPrevEvetFeedback(int regId, Integer newsblogsId) {

        if (Constants.isOnline(getApplicationContext())) {
            Log.e("PARAMETER : ", "---------------- REG : " + regId+ "      Event : " + newsblogsId);

            final CommonDialog commonDialog = new CommonDialog(PreviousEventDetailActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<PrevEventFeedback> listCall = Constants.myInterface.getFeedbackByUserIdAndNewsblogsId(regId,newsblogsId);
            listCall.enqueue(new Callback<PrevEventFeedback>() {
                @Override
                public void onResponse(Call<PrevEventFeedback> call, Response<PrevEventFeedback> response) {
                    try {
                        if (response.body() != null) {

                             model = response.body();

                            if(model.getExInt1()==1)
                            {
                                linearLayoutFeedback.setVisibility(View.VISIBLE);
                            }
                            if(model.getExInt2()==1)
                            {
                                tv_eventFeedbackValue.setText("Very Dissatisfied");
                            }else if(model.getExInt2()==2)
                            {
                                tv_eventFeedbackValue.setText("Dissatisfied");
                            }else if(model.getExInt2()==3)
                            {
                                tv_eventFeedbackValue.setText("Neutral");
                            }else if(model.getExInt2()==4)
                            {
                                tv_eventFeedbackValue.setText("Satisfied");
                            }else if(model.getExInt2()==5)
                            {
                                tv_eventFeedbackValue.setText("Very Satisfied");
                            }
                            Log.e("EVENT FEEDBACK", "-----------------------------" + response.body());
                            Log.e("EVENT FEEDBACK MODEL", "-----------------------------" + model);
                            tv_eventFeedback.setText("" +model.getExVar1());
                            commonDialog.dismiss();
                            //Toast.makeText(EventDetailListActivity.this, "Applied for this Event", Toast.LENGTH_SHORT).show();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<PrevEventFeedback> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
