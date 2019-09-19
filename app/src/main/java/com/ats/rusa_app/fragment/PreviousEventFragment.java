package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.google.gson.Gson;

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
    ArrayList<PrevEvent> previousEventList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_event, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

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

            Call<ArrayList<PrevEvent>> listCall = Constants.myInterface.getAllPreviousEventWithApllied(languageId,regId,authHeader);
            listCall.enqueue(new Callback<ArrayList<PrevEvent>>() {
                @Override
                public void onResponse(Call<ArrayList<PrevEvent>> call, Response<ArrayList<PrevEvent>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PREVIOUS EVENT LIST : ", " - " + response.body());
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
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<PrevEvent>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
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
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            int regId = loginUser.getRegId();
            getPrevoiusEvent(languageId, regId);
        }catch (Exception e)
        {
            Log.e("onFailure : ", "-----------" + e.getMessage());
            e.printStackTrace();
        }
    }
}
