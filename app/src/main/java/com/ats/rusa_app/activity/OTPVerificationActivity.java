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
import com.ats.rusa_app.model.Registration;
import com.ats.rusa_app.util.CommonDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationActivity extends AppCompatActivity {
public EditText ed_verificationOTP;
public Button btn_submit,btn_resend;
Registration registrationModel;
    String model;
    ArrayList<OTPVerification> otpVerifyList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        ed_verificationOTP=(EditText)findViewById(R.id.ed_verifyOTP);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_resend=(Button)findViewById(R.id.btn_resend);
        Bundle bundle = getIntent().getExtras();
        final String smsCode = bundle.getString("code");
         model = bundle.getString("model");
        Gson gson = new Gson();
        registrationModel = gson.fromJson(model, Registration.class);


        Log.e("CODE","------------"+smsCode);
        Log.e("model","------------"+registrationModel);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userCode=ed_verificationOTP.getText().toString();
                String UserUuid=registrationModel.getUserUuid();
                if(smsCode.equals(userCode))
                {
//                    Registration registration =new Registration(registrationModel.getRegId(),registrationModel.getUserUuid(),registrationModel.getUserType(),registrationModel.getEmails(),registrationModel.getAlternateEmail(),registrationModel.getUserPassword(),registrationModel.getName(),registrationModel.getAisheCode(),registrationModel.getCollegeName(),registrationModel.getUnversityName(),registrationModel.getDesignationName(),registrationModel.getDepartmentName(),registrationModel.getMobileNumber(),registrationModel.getAuthorizedPerson(),registrationModel.getDob(),registrationModel.getImageName(),registrationModel.getTokenId(),registrationModel.getRegisterVia(),registrationModel.getIsActive(),registrationModel.getDelStatus(),registrationModel.getAddDate(),registrationModel.getEditDate(),registrationModel.getEditByUserId(),registrationModel.getExInt1(),registrationModel.getExInt2(),registrationModel.getExVar1(),registrationModel.getExVar2(),registrationModel.getEmailCode(),registrationModel.getEmailVerified(),registrationModel.getSmsCode(),1,registrationModel.getEditByAdminuserId());

                    getVerifyOTP(userCode,UserUuid);
                }else{
                    Toast.makeText(OTPVerificationActivity.this, "Failed Verify OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
