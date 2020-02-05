package com.ats.rusa_app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.sqlite.DatabaseHandler;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edPrevPass, edNewPass, edConfPass;
    Button btnChangePass;
    Login loginUser;
    DatabaseHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edPrevPass = (EditText) findViewById(R.id.ed_prePass);
        edNewPass = (EditText) findViewById(R.id.ed_newPass);
        edConfPass = (EditText) findViewById(R.id.ed_confPass);
        btnChangePass = (Button) findViewById(R.id.btn_changePass);

        dbHelper = new DatabaseHandler(ChangePasswordActivity.this);


        setTitle(""+getResources().getString(R.string.app_name));

//        String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);
//        Gson gson = new Gson();
//        loginUser = gson.fromJson(userStr, Login.class);
        //Log.e("LOGIN_ACTIVITY : ", "--------USER-------" + loginUser);

        try {
            loginUser = dbHelper.getLoginData();
            //Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        }catch (Exception e)
        {
            //e.printStackTrace();
        }

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
        if (v.getId() == R.id.btn_changePass) {
            String strPrevPass, strNewPass, strConfPass, strLoginPass;
            boolean isValidPrevPass = false, isValidNewPass = false, isValidConfirmPass = false;
            strLoginPass = loginUser.getUserPassword();

            //Log.e("LoginPass", "---------------" + strLoginPass);

            strPrevPass = edPrevPass.getText().toString();
            strNewPass = edNewPass.getText().toString();
            strConfPass = edConfPass.getText().toString();


            if (strPrevPass.isEmpty()) {
                edPrevPass.setError("required");
            }
            /*else if (!strLoginPass.equals(strPrevPass)) {
                edPrevPass.setError("Wrong Password");
            }*/
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

            if (isValidPrevPass && isValidNewPass && isValidConfirmPass) {
               // getChangePass(loginUser.getRegId(), strNewPass);

                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    // e.printStackTrace();
                }
                byte[] messageDigest = md.digest(strPrevPass.getBytes());
                BigInteger number = new BigInteger(1, messageDigest);
                String hashtext = number.toString(16);

                getCheckPass(loginUser.getRegId(),hashtext,strNewPass);
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

    private void getCheckPass(Integer regId, String hashtext, final String strNewPass) {
        if (Constants.isOnline(ChangePasswordActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ChangePasswordActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(ChangePasswordActivity.this, CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            Call<Info> listCall = Constants.myInterface.checkPasswordByUserId(regId,hashtext,token,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                   // Log.e("RESPONCE","----------------------CHECK-------------------"+response.body());
                    try {
                        if (response.body() != null) {

                            if(!response.body().getError()) {

                                getChangePass(loginUser.getRegId(), strNewPass);

                            }else{

                                if (response.body().getRetmsg().equalsIgnoreCase("Unauthorized User")) {

                                            dbHelper.deleteData("user_data");
                                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();


                                }else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this, R.style.AlertDialogTheme);
                                    builder.setTitle("Alert");
                                    builder.setMessage("" + response.body().getMsg());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }

                            }

                            commonDialog.dismiss();
                        }else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        //  e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                    // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(ChangePasswordActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getChangePass(Integer regId, String strNewPass) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // e.printStackTrace();
        }
        byte[] messageDigest = md.digest(strNewPass.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(ChangePasswordActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(ChangePasswordActivity.this, CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            Call<Info> listCall = Constants.myInterface.changePassword(regId, hashtext,token,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {

                    //Log.e("RESPONCE","----------------------LOGIN-------------------"+response.body());
                    try {
                        if (response.body() != null) {

                            if(!response.body().getError()) {
                                //Toast.makeText(getApplicationContext(), "Update Password", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                commonDialog.dismiss();
                            }else{

                                if (response.body().getRetmsg().equalsIgnoreCase("Unauthorized User")) {

                                            dbHelper.deleteData("user_data");
                                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();


                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this, R.style.AlertDialogTheme);
                                    builder.setTitle("Alert");
                                    builder.setMessage("" + response.body().getMsg());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();


                                        }
                                    });
                                }

                                commonDialog.dismiss();

                            }
                        } else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                  //  t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
