package com.ats.rusa_app.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.LoginActivity;
import com.ats.rusa_app.activity.MainActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener{

   EditText edPrevPass,edNewPass,edConfPass;
   Button btnChangePass;
   Login loginUser;
   DatabaseHandler dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        edPrevPass=(EditText) view.findViewById(R.id.ed_prePass);
        edNewPass=(EditText) view.findViewById(R.id.ed_newPass);
        edConfPass=(EditText) view.findViewById(R.id.ed_confPass);
        btnChangePass=(Button)view.findViewById(R.id.btn_changePass);

        dbHelper=new DatabaseHandler(getActivity());

//        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
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
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }

        btnChangePass.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_changePass)
        {
            String strPrevPass,strNewPass,strConfPass,strLoginPass;
            boolean isValidPrevPass = false,isValidNewPass = false;
            strLoginPass=loginUser.getUserPassword();

            //Log.e("LoginPass","---------------"+strLoginPass);
            strPrevPass=edPrevPass.getText().toString();
            strNewPass=edNewPass.getText().toString();
            strConfPass=edConfPass.getText().toString();

//            if(!strLoginPass.equals(strPrevPass))
//            {
//               edPrevPass.setError("Wrong Password");
//            }else{
//                edPrevPass.setError(null);
//                isValidPrevPass=true;
//            }

            if(strPrevPass.isEmpty())
            {
               edPrevPass.setError("Enter Password");
               edPrevPass.requestFocus();
            }else{
                edPrevPass.setError(null);
                isValidPrevPass=true;
            }

            if(strNewPass.isEmpty())
            {
                edNewPass.setError("Enter New Password");
                edNewPass.requestFocus();
            }else  if(!strNewPass.equals(strConfPass))
            {
                edNewPass.setError("New Password and Confirme password Not Match");

            }else{
                edNewPass.setError(null);
                isValidNewPass=true;
            }

//            if(!strNewPass.equals(strConfPass))
//            {
//                edNewPass.setError("New Password and Confirme password Not Match");
//
//            }else{
//                edNewPass.setError(null);
//                isValidNewPass=true;
//            }
            if (isValidPrevPass && isValidNewPass)
            {
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
                //getChangePass(loginUser.getRegId(),strNewPass);
            }
        }
    }

    private void getCheckPass(Integer regId, String hashtext, final String strNewPass) {
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            Call<Info> listCall = Constants.myInterface.checkPasswordByUserId(regId,hashtext,token,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            if(!response.body().getError()) {

                                getChangePass(loginUser.getRegId(), strNewPass);

                            }else{

                                if (response.body().getRetmsg().equalsIgnoreCase("Unauthorized User")) {

                                            dbHelper.deleteData("user_data");
                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            getActivity().finish();


                                }else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
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
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getChangePass(Integer regId, String strNewPass) {
        if (Constants.isOnline(getActivity())) {

            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                // e.printStackTrace();
            }
            byte[] messageDigest = md.digest(strNewPass.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            Call<Info> listCall = Constants.myInterface.changePassword(regId,hashtext,token,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            if(!response.body().getError()) {
                                Toast.makeText(getActivity(), "Update Password", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                commonDialog.dismiss();
                            }else{
                                if (response.body().getRetmsg().equalsIgnoreCase("Unauthorized User")) {

                                            dbHelper.deleteData("user_data");
                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            getActivity().finish();


                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
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
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
