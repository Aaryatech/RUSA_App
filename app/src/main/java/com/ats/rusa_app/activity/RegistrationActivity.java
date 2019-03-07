package com.ats.rusa_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.rusa_app.R;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    public EditText ed_userName,ed_password,ed_phoneNo,ed_Name;
    public Button btn_registration;
    public TextView tv_signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ed_userName=(EditText)findViewById(R.id.ed_userName);
        ed_password=(EditText)findViewById(R.id.ed_password);
        ed_phoneNo=(EditText)findViewById(R.id.ed_phoneNo);
        ed_Name=(EditText)findViewById(R.id.ed_name);
        tv_signIn=(TextView)findViewById(R.id.tv_signIn);
        btn_registration=(Button)findViewById(R.id.btn_registration);


        tv_signIn.setOnClickListener(this);
        btn_registration.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registration) {
            String strUserName, strPass,strPhoneNo,strName;
            boolean isValidMobile = false, isValidPass = false,isValidUserName = false,isValidName = false;

            strUserName = ed_userName.getText().toString().trim();
            strPass = ed_password.getText().toString().trim();
            strPhoneNo = ed_phoneNo.getText().toString().trim();
            strName = ed_Name.getText().toString().trim();

            if (strUserName.isEmpty()) {
                ed_userName.setError("required");
            }  else {
                ed_userName.setError(null);
                isValidUserName = true;
            }
            if (strName.isEmpty()) {
                ed_Name.setError("required");
            }  else {
                ed_Name.setError(null);
                isValidName = true;
            }
            if (strPhoneNo.isEmpty()) {
                ed_phoneNo.setError("required");
            } else if (strPhoneNo.length() != 10) {
                ed_phoneNo.setError("required 10 digits");
            } else {
                ed_phoneNo.setError(null);
                isValidMobile = true;
            }
            if (strPass.isEmpty()) {
                ed_password.setError("required");
            } else {
                ed_password.setError(null);
                isValidPass = true;
            }

            if (isValidMobile && isValidPass && isValidName && isValidUserName) {
                // doLogin(strUserName, strPass);
            }
        }else if(v.getId()==R.id.tv_signIn)
        {
            Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
