package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener{

   EditText edPrevPass,edNewPass,edConfPass;
   Button btnChangePass;
   Login loginUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        edPrevPass=(EditText) view.findViewById(R.id.ed_prePass);
        edNewPass=(EditText) view.findViewById(R.id.ed_newPass);
        edConfPass=(EditText) view.findViewById(R.id.ed_confPass);
        btnChangePass=(Button)view.findViewById(R.id.btn_changePass);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("LOGIN_ACTIVITY : ", "--------USER-------" + loginUser);

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

            Log.e("LoginPass","---------------"+strLoginPass);
            strPrevPass=edPrevPass.getText().toString();
            strNewPass=edNewPass.getText().toString();
            strConfPass=edConfPass.getText().toString();
            if(!strLoginPass.equals(strPrevPass))
            {
               edPrevPass.setError("Wrong Password");
            }else{
                edPrevPass.setError(null);
                isValidPrevPass=true;
            }
            if(!strNewPass.equals(strConfPass))
            {
                edNewPass.setError("New Password and Confirme password Not Match");

            }else{
                edNewPass.setError(null);
                isValidNewPass=true;
            }
            if (isValidPrevPass && isValidNewPass)
            {
                getChangePass(loginUser.getRegId(),strNewPass);
            }
        }
    }

    private void getChangePass(Integer regId, String strNewPass) {
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.changePassword(regId,strNewPass);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            Toast.makeText(getActivity(), "Update Password", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
