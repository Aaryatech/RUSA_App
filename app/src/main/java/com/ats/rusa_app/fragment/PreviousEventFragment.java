package com.ats.rusa_app.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.PreviousEventAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.interfaces.PreviousEventsInterface;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.PrevEvent;
import com.ats.rusa_app.model.TokenInfo;
import com.ats.rusa_app.sqlite.DatabaseHandler;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousEventFragment extends Fragment implements PreviousEventsInterface {
    public RecyclerView recyclerView;
    int languageId;
    Login loginUser;
    DatabaseHandler dbHelper;
    ArrayList<PrevEvent> previousEventList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_event, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        dbHelper=new DatabaseHandler(getActivity());

        String langId = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }

       // getPrevoiusEvent(languageId);
        return view;
    }

    private void getPrevoiusEvent(int languageId,int regId) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;


            Call<ArrayList<PrevEvent>> listCall = Constants.myInterface.getAllPreviousEventWithApllied(languageId,regId,token,authHeader);
            listCall.enqueue(new Callback<ArrayList<PrevEvent>>() {
                @Override
                public void onResponse(Call<ArrayList<PrevEvent>> call, Response<ArrayList<PrevEvent>> response) {
                    try {
                        if (response.body() != null) {


                            //Log.e("PREVIOUS EVENT LIST : ", " - " + response.body());
                            previousEventList.clear();
                            previousEventList = response.body();

                            PreviousEventAdapter adapter = new PreviousEventAdapter(previousEventList, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                            commonDialog.dismiss();

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
                public void onFailure(Call<ArrayList<PrevEvent>> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fragmentBecameVisible() {
        String langId = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }

        try {
//            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
//            Gson gson = new Gson();
//            loginUser = gson.fromJson(userStr, Login.class);
                loginUser = dbHelper.getLoginData();
                //Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

            int regId = loginUser.getRegId();
            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            getCheckToken(loginUser.getRegId(),token);

            //getPrevoiusEvent(languageId, regId);

        }catch (Exception e)
        {
            //Log.e("onFailure : ", "-----------" + e.getMessage());
           // e.printStackTrace();
        }
    }

    private void getCheckToken(final Integer regId, String token) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<TokenInfo> listCall = Constants.myInterface.tokenConfirmation(regId,token,authHeader);
            listCall.enqueue(new Callback<TokenInfo>() {
                @Override
                public void onResponse(Call<TokenInfo> call, Response<TokenInfo> response) {
                    // Log.e("Responce","--------------------------------------------------"+response.body());
                    try {
                        //if (response.body() == null) {

                            if(!response.body().getError()) {

                                getPrevoiusEvent(languageId, regId);

                            }else{
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


                       // }
                        commonDialog.dismiss();

                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<TokenInfo> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                    //  t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }
}
