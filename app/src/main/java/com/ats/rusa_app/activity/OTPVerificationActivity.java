package com.ats.rusa_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.OTPVerification;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.model.ResendOTP;
import com.ats.rusa_app.util.CommonDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationActivity extends AppCompatActivity {
public EditText ed_verificationOTP;
public Button btn_submit,btn_resend;
Reg registrationModel;
     String smsCode;
    String model;
    ArrayList<ResendOTP> resendOTP = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        ed_verificationOTP=(EditText)findViewById(R.id.ed_verifyOTP);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_resend=(Button)findViewById(R.id.btn_resend);
        Bundle bundle = getIntent().getExtras();
       // final String smsCode = bundle.getString("code");
        String uuId = null;
        try {
              uuId = bundle.getString("uuCode");
        }catch (Exception e)
        {

        }
         model = bundle.getString("model");
        Gson gson = new Gson();
        registrationModel = gson.fromJson(model, Reg.class);
         smsCode = registrationModel.getSmsCode();
        Log.e("CODE","------------"+smsCode);
        Log.e("reg model","------------"+registrationModel);
//        Log.e("uuId","------------"+uuId);

        //getResendOTP(uuId);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userCode=ed_verificationOTP.getText().toString();
                String UserUuid=registrationModel.getUserUuid();
                boolean isValidOTP = false;

            if (userCode.isEmpty()) {
                ed_verificationOTP.setError("required");
            }  else {
                isValidOTP = true;
                ed_verificationOTP.setError(null);
            }

                if(isValidOTP) {

                    if (smsCode.equals(userCode)) {
                        getVerifyOTP(userCode, UserUuid);

                    } else {
                        Toast.makeText(OTPVerificationActivity.this, "Failed Verify OTP", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getResendOTP(registrationModel.getUserUuid());
            }
        });

    }

    private void getResendOTP(String userUuid) {
        Log.e("PARA","----------------"+userUuid);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(OTPVerificationActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ResendOTP> listCall = Constants.myInterface.verifyResendOtpResponse(userUuid);
            listCall.enqueue(new Callback<ResendOTP>() {
                @Override
                public void onResponse(Call<ResendOTP> call, Response<ResendOTP> response) {
                    try {
                        if (response.body() != null) {

                           // Log.e("RESEND VERIFY : ", " - " + response.body().toString());
                            ResendOTP resendOTP=response.body();
                            Log.e("RESEND VERIFY LIST : ", " - " + resendOTP);
                            smsCode=resendOTP.getReg().getSmsCode();
                            Log.e("RESEND CODE : ", " - " + smsCode);
                            // finish();
                            commonDialog.dismiss();

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
                public void onFailure(Call<ResendOTP> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 reset : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getVerifyOTP(String userCode, String userUuid) {
        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(OTPVerificationActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<OTPVerification> listCall = Constants.myInterface.verifyOtpResponse(userCode,userUuid);
            listCall.enqueue(new Callback<OTPVerification>() {
                @Override
                public void onResponse(Call<OTPVerification> call, Response<OTPVerification> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("VERIFY : ", " - " + response.body().toString());
                            OTPVerification otpVerification=response.body();
                            Log.e("VERIFY LIST : ", " - " + otpVerification);
                            Intent intent=new Intent(OTPVerificationActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                           // finish();
                            commonDialog.dismiss();

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
                public void onFailure(Call<OTPVerification> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
