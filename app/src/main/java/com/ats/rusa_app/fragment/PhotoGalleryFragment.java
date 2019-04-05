package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.GalleryEventsAdapter;
import com.ats.rusa_app.adapter.RvGalleryAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.ContactUs;
import com.ats.rusa_app.model.GetGalleryCategory;
import com.ats.rusa_app.util.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoGalleryFragment extends Fragment {

    private RecyclerView rvEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        rvEvents = view.findViewById(R.id.rvEvents);

        getCatBySection(15);


        return view;
    }

    private void getCatBySection(int sectionId) {
        if (Constants.isOnline(getActivity())) {
            Log.e("PARAMETER : ", "---------------- SECTION ID : " + sectionId);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<List<GetGalleryCategory>> listCall = Constants.myInterface.getCatBySection(sectionId);
            listCall.enqueue(new Callback<List<GetGalleryCategory>>() {
                @Override
                public void onResponse(Call<List<GetGalleryCategory>> call, Response<List<GetGalleryCategory>> response) {
                    try {
                        if (response.body() != null) {

                            List<GetGalleryCategory> model = response.body();

                            Log.e("GALLERY CAT LIST", "-----------------------------" + model);

                            if (model != null) {
                                ArrayList<GetGalleryCategory> eventList = new ArrayList<>();

                                if (model.size() > 0) {
                                    for (int i = 0; i < model.size(); i++) {
                                        eventList.add(model.get(i));
                                    }
                                }

                                GalleryEventsAdapter adapter = new GalleryEventsAdapter(eventList, getContext());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                rvEvents.setLayoutManager(mLayoutManager);
//                                rvEvents.setLayoutManager(new GridLayoutManager(getContext(), 2));
                                rvEvents.setItemAnimator(new DefaultItemAnimator());
                                rvEvents.setAdapter(adapter);
                            }


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
                public void onFailure(Call<List<GetGalleryCategory>> call, Throwable t) {
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