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
import com.ats.rusa_app.adapter.UpcominEventAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.interfaces.UpcomingEventsInterface;
import com.ats.rusa_app.model.UpcomingEvent;
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
public class UpcomingEventFragment extends Fragment implements UpcomingEventsInterface {
public RecyclerView recyclerView;
int languageId;

    ArrayList<UpcomingEvent> upcomingEventList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upcoming_event, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);

        String langId = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }

        getUpcomingEvent(languageId);
        return view;
    }

    private void getUpcomingEvent(int languageId) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<UpcomingEvent>> listCall = Constants.myInterface.getUpcomingEvent(languageId,authHeader);
            listCall.enqueue(new Callback<ArrayList<UpcomingEvent>>() {
                @Override
                public void onResponse(Call<ArrayList<UpcomingEvent>> call, Response<ArrayList<UpcomingEvent>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPCOMING EVENT LIST : ", " - " + response.body());
                            upcomingEventList.clear();
                            upcomingEventList = response.body();

                            UpcominEventAdapter adapter = new UpcominEventAdapter(upcomingEventList, getContext());
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
                public void onFailure(Call<ArrayList<UpcomingEvent>> call, Throwable t) {
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

        getUpcomingEvent(languageId);
    }
}
