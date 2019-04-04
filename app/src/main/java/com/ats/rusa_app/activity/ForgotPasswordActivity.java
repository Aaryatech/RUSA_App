package com.ats.rusa_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.util.CommonDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
EditText ed_email,ed_phoneNo;
Button btn_forgotPassword;
TextView tv_backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ed_email=(EditText)findViewById(R.id.ed_userEmail);
        ed_phoneNo=(EditText)findViewById(R.id.ed_phoneNo);
        btn_forgotPassword=(Button)findViewById(R.id.btn_resetPassword);
        tv_backToLogin=(TextView)findViewById(R.id.tv_backToLogin);

        btn_forgotPassword.setOnClickListener(this);
        tv_backToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_backToLogin)
        {
            Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.btn_resetPassword)
        {
            boolean isValidEmail = false,isValidPhone = false;
            String email,mob;
            email = ed_email.getText().toString().trim();
            mob = ed_phoneNo.getText().toString().trim();

            if (!email.isEmpty()) {
                if (!isValidEmailAddress(email)) {
                    ed_email.setError("invalid email");
                } else {
                    ed_email.setError(null);
                    isValidEmail = true;
                }
            } else {
                isValidEmail = true;
            }

//            if (email.isEmpty()) {
//                ed_email.setError("required");
//            }  else {
//                isValidEmail = true;
//                ed_email.setError(null);
//            }
//            if (mob.isEmpty()) {
//                ed_phoneNo.setError("required");
//            }
//            else {
//                ed_phoneNo.setError(null);
//                isValidPhone = true;
//            }

            if (mob.isEmpty()) {
                ed_phoneNo.setError("required");
            } else if (mob.length() != 10) {
                ed_phoneNo.setError("required 10 digits");
            } else if (mob.equalsIgnoreCase("0000000000")) {
                ed_phoneNo.setError("invalid number");
            }else {
                ed_phoneNo.setError(null);
                isValidPhone = true;
            }

            if(isValidEmail && isValidPhone)
            {
                getForgotPassword(email,mob);
            }
        }
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    private void getForgotPassword(String email, String mob) {
        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(ForgotPasswordActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Login> listCall = Constants.myInterface.getForgotPass(email,mob);
            listCall.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    try {
                        if (response.body() != null) {

                            // Log.e("RESEND VERIFY : ", " - " + response.body().toString());
                            Login resendOTP=response.body();
                            Log.e("FORGOT PASS : ", " - " + resendOTP);
                            Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                            startActivity(intent);
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(ForgotPasswordActivity.this, "Invalid Email and Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, "Invalid Email and Mobile Number", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 reset : ", "-----------" + t.getMessage());
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid Email and Mobile Number", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }
}
