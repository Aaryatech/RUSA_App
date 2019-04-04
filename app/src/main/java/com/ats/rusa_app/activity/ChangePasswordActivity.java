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
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edPrevPass,edNewPass,edConfPass;
    Button btnChangePass;
    Login loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edPrevPass=(EditText) findViewById(R.id.ed_prePass);
        edNewPass=(EditText) findViewById(R.id.ed_newPass);
        edConfPass=(EditText) findViewById(R.id.ed_confPass);
        btnChangePass=(Button)findViewById(R.id.btn_changePass);

        String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("LOGIN_ACTIVITY : ", "--------USER-------" + loginUser);

        if (loginUser == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        btnChangePass.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_changePass)
        {
            String strPrevPass,strNewPass,strConfPass,strLoginPass;
            boolean isValidPrevPass = false,isValidNewPass = false,isValidConfirmPass = false;
            strLoginPass=loginUser.getUserPassword();

            Log.e("LoginPass","---------------"+strLoginPass);

            strPrevPass=edPrevPass.getText().toString();
            strNewPass=edNewPass.getText().toString();
            strConfPass=edConfPass.getText().toString();


            if (strPrevPass.isEmpty()) {
                edPrevPass.setError("required");
            }else if(!strLoginPass.equals(strPrevPass))
            {
                edPrevPass.setError("Wrong Password");
            }
            else {
                edPrevPass.setError(null);
                isValidPrevPass = true;
            }


            if (strNewPass.isEmpty()) {
                edNewPass.setError("required");
            } else {
                edNewPass.setError(null);
                isValidNewPass = true;
            }

            if (strConfPass.isEmpty()) {
                edConfPass.setError("required");
            } else if (!strConfPass.equals(strNewPass)) {
                edConfPass.setError("password not matched");
            } else {
                edConfPass.setError(null);
                isValidConfirmPass = true;
            }

            if (isValidPrevPass && isValidNewPass && isValidConfirmPass)
            {
                getChangePass(loginUser.getRegId(),strNewPass);
            }


//            if(!strLoginPass.equals(strPrevPass))
//            {
//                edPrevPass.setError("Wrong Password");
//            }else{
//                edPrevPass.setError(null);
//                isValidPrevPass=true;
//            }
//            if(!strNewPass.equals(strConfPass))
//            {
//                edNewPass.setError("New Password and Confirme password Not Match");
//
//            }else{
//                edNewPass.setError(null);
//                isValidNewPass=true;
//            }

        }
    }
    private void getChangePass(Integer regId, String strNewPass) {
        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(ChangePasswordActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.changePassword(regId,strNewPass);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Toast.makeText(getApplicationContext(), "Update Password", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            commonDialog.dismiss();
                        }else {
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
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
