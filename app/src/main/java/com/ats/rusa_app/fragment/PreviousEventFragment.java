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
import com.ats.rusa_app.model.PreviousEvent;
import com.ats.rusa_app.util.CommonDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousEventFragment extends Fragment {
    public RecyclerView recyclerView;
    ArrayList<PreviousEvent> previousEventList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_previous_event, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        getPrevoiusEvent();
        return view;
    }

    private void getPrevoiusEvent() {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<PreviousEvent>> listCall = Constants.myInterface.getPreviousEvent(2);
            listCall.enqueue(new Callback<ArrayList<PreviousEvent>>() {
                @Override
                public void onResponse(Call<ArrayList<PreviousEvent>> call, Response<ArrayList<PreviousEvent>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPCOMING EVENT LIST : ", " - " + response.body());
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
                public void onFailure(Call<ArrayList<PreviousEvent>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
