package com.ats.rusa_app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.FeedbackSave;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.PrevEvent;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener  {
    public RadioGroup rg;
    String selectedtext;
    EditText ed_msg;
    Button btn_save;
    PrevEvent previousEvent;
    Login loginUser;
    public RadioButton rb_verySatisfied,rb_satisfied,rb_neutral,rb_dissatisfied,rb_veryDissatisfied;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle(""+getResources().getString(R.string.app_name));


        rg=(RadioGroup)findViewById(R.id.rg);
        rb_verySatisfied=(RadioButton)findViewById(R.id.rb_verySatisfied);
        rb_satisfied=(RadioButton)findViewById(R.id.rb_Satisfied);
        rb_neutral=(RadioButton)findViewById(R.id.rb_neutral);
        rb_dissatisfied=(RadioButton)findViewById(R.id.rb_disSatisfied);
        rb_veryDissatisfied=(RadioButton)findViewById(R.id.rb_veryDisSatisfied);
        ed_msg=(EditText)findViewById(R.id.ed_Msg);
        btn_save=(Button)findViewById(R.id.btn_saveFeedback);

        String previousStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        previousEvent = gson.fromJson(previousStr, PrevEvent.class);
        //Log.e("responce","-----------------------"+previousEvent);

        String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);
        loginUser = gson.fromJson(userStr, Login.class);
        //Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int idx = group.indexOfChild(radioButton);
                RadioButton r = (RadioButton) group.getChildAt(idx);
                selectedtext = r.getText().toString();
                //Log.e("Log Radio", "----------" + r.getText());
                //Log.e("Log Radio1", "----------" + selectedtext);

            }
        });

        if(rg.getCheckedRadioButtonId()==-1){
            rb_neutral.setChecked(true);
            //     boolean checked = ((RadioButton) view).isChecked();
            selectedtext= "Neutral";
        }

        btn_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_saveFeedback)
        {
            if(selectedtext.equals("Very Satisfied"))
            {
                String msg=ed_msg.getText().toString();
                getFeedbackSave(previousEvent.getNewsblogsId(),loginUser.getRegId(),msg,5);
            }else if(selectedtext.equals("Satisfied"))
            {
                String msg=ed_msg.getText().toString();
                getFeedbackSave(previousEvent.getNewsblogsId(),loginUser.getRegId(),msg,4);
            }else if(selectedtext.equals("Neutral"))
            {
                String msg=ed_msg.getText().toString();
                getFeedbackSave(previousEvent.getNewsblogsId(),loginUser.getRegId(),msg,3);
            }else if(selectedtext.equals("Dissatisfied"))
            {
                String msg=ed_msg.getText().toString();

                if (msg.isEmpty()) {
                    ed_msg.setError("required");
                }  else {
                    ed_msg.setError(null);
                    getFeedbackSave(previousEvent.getNewsblogsId(),loginUser.getRegId(),msg,2);
                }

            }else if(selectedtext.equals("Very Dissatisfied"))
            {
                String msg=ed_msg.getText().toString();

                if (msg.isEmpty()) {
                    ed_msg.setError("required");
                }  else {
                    ed_msg.setError(null);
                    getFeedbackSave(previousEvent.getNewsblogsId(),loginUser.getRegId(),msg,1);

                }
            }
        }
    }
    private void getFeedbackSave(Integer newsblogsId, Integer regId, String msg, int value) {
        //Log.e("PARAMETERS : ", "        EVENT ID : " + newsblogsId + "      REG ID : " + regId+ "      MSG : " + msg+ "      VALUE : " + value);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(FeedbackActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<FeedbackSave> listCall = Constants.myInterface.getUpdateEventFeedback(newsblogsId,regId,msg,value,authHeader);
            listCall.enqueue(new Callback<FeedbackSave>() {
                @Override
                public void onResponse(Call<FeedbackSave> call, Response<FeedbackSave> response) {
                    try {
                        if (response.body() != null) {

                           // Toast.makeText(getApplicationContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                            //Log.e("FEEDBACK SAVE","-----------------------------"+response.body());
                            AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("");
                            builder.setMessage("Feedback has being save successfully");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();
                                    Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                                    intent.putExtra("Feedback", "Feedback");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                            commonDialog.dismiss();
                        }else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        //e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<FeedbackSave> call, Throwable t) {
                    commonDialog.dismiss();
                    ////Log.e("onFailure : ", "-----------" + t.getMessage());
                    //t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
