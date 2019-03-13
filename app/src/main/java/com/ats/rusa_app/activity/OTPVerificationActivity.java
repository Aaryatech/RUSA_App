package com.ats.rusa_app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.rusa_app.R;

public class OTPVerificationActivity extends AppCompatActivity {
public EditText ed_verificationOTP;
public Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        ed_verificationOTP=(EditText)findViewById(R.id.ed_verifyOTP);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        Bundle bundle = getIntent().getExtras();
        final String smsCode = bundle.getString("code");
        Log.e("CODE","------------"+smsCode);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=ed_verificationOTP.getText().toString();
                if(smsCode.equals(code))
                {
                    Toast.makeText(OTPVerificationActivity.this, "Verify OTP", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OTPVerificationActivity.this, "Failed Verify OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
