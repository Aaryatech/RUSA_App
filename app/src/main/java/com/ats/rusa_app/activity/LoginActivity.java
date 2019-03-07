package com.ats.rusa_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.rusa_app.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
public EditText ed_userName,ed_password;
public Button btn_login;
public TextView tv_forgotPass,tv_signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_userName=(EditText)findViewById(R.id.ed_userName);
        ed_password=(EditText)findViewById(R.id.ed_password);
        tv_forgotPass=(TextView)findViewById(R.id.tv_forgotPassword);
        tv_signUp=(TextView)findViewById(R.id.tv_signUp);
        btn_login=(Button)findViewById(R.id.btn_login);

        tv_forgotPass.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            String strUserName, strPass;
            boolean isValidUserName = false, isValidPass = false;

            strUserName = ed_userName.getText().toString().trim();
            strPass = ed_password.getText().toString().trim();

            if (strUserName.isEmpty()) {
                ed_userName.setError("required");
            }  else {
                ed_userName.setError(null);
                isValidUserName = true;
            }

            if (strPass.isEmpty()) {
                ed_password.setError("required");
            } else {
                ed_password.setError(null);
                isValidPass = true;
            }

            if (isValidUserName && isValidPass) {
               // doLogin(strUserName, strPass);
            }
        }else if(v.getId()==R.id.tv_forgotPassword)
        {

        }else if(v.getId()==R.id.tv_signUp)
        {
            Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
            startActivity(intent);
        }

    }
}
