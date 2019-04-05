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
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText ed_userName, ed_password;
    public Button btn_login;
    public TextView tv_forgotPass, tv_signUp, tv_skipLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_userName = (EditText) findViewById(R.id.ed_userName);
        ed_password = (EditText) findViewById(R.id.ed_password);
        tv_forgotPass = (TextView) findViewById(R.id.tv_forgotPassword);
        tv_signUp = (TextView) findViewById(R.id.tv_signUp);
        tv_skipLogin = (TextView) findViewById(R.id.tv_skipLogin);
        btn_login = (Button) findViewById(R.id.btn_login);

        tv_forgotPass.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
        tv_skipLogin.setOnClickListener(this);
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
            } else {
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
                doLogin(strUserName, strPass);
            }
        } else if (v.getId() == R.id.tv_forgotPassword) {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_signUp) {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_skipLogin) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("code", "SkipLogin");
             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    private void doLogin(String strUserName, String strPass) {
        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(LoginActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Login> listCall = Constants.myInterface.getLogin(strUserName, strPass);
            listCall.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    try {
                        if (response.body() != null) {

                            // Log.e("RESEND VERIFY : ", " - " + response.body().toString());
                            Login userDetail = response.body();
                            Log.e("LOGIN RESPONCE : ", " - " + userDetail);

                            if (!userDetail.getError()) {
                                Gson gson = new Gson();
                                String json = gson.toJson(userDetail);
                                CustomSharedPreference.putString(LoginActivity.this, CustomSharedPreference.KEY_USER, json);

                                if (userDetail.getExInt1() != 1) {
                                    Intent intent1 = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent1);
                                    commonDialog.dismiss();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    commonDialog.dismiss();
                                }

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle("");
                                builder.setMessage("" + userDetail.getMsg());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        commonDialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                commonDialog.dismiss();
                            }


                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid Email and Password", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(LoginActivity.this, "Invalid Email and Password", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 reset : ", "-----------" + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Invalid Email and Password", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }


    }
}
