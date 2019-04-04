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
import com.ats.rusa_app.adapter.ContentAdapter;
import com.ats.rusa_app.adapter.GalleryEventGroupAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.ImageListByCategory;
import com.ats.rusa_app.model.PageData;
import com.ats.rusa_app.model.VideoList;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryEventDetailsFragment extends Fragment {

    static String slugName;
    int languageId;
    private RecyclerView rvList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_event_details, container, false);

        rvList = view.findViewById(R.id.rvList);

        String langId = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }

        try {
            slugName = getArguments().getString("slugName");
        } catch (Exception e) {
            Log.e("GALLERY EVENT DET FRG","----------------- EXCEPTION : "+e.getMessage());
            e.printStackTrace();
        }

        getPageData(slugName, languageId);


        return view;
    }


    public void getPageData(final String slugName, int langId) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PageData> listCall = Constants.myInterface.getGalleryData(slugName, langId);
            listCall.enqueue(new Callback<PageData>() {
                @Override
                public void onResponse(Call<PageData> call, Response<PageData> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("GALLERY DATA : ", " - " + response.body());

                            PageData model = response.body();
                            model.setSlugName(slugName);

                            ArrayList<GallaryDetailList> piclist = new ArrayList<>();
                            ArrayList<VideoList> vidlist = new ArrayList<>();

                            if (model.getGallaryDetailList() != null) {
                                if (model.getGallaryDetailList().size() > 0) {
                                    for (int i = 0; i < model.getGallaryDetailList().size(); i++) {
                                        piclist.add(model.getGallaryDetailList().get(i));
                                    }
                                }
                            }

                            if (model.getVideoList() != null) {
                                if (model.getVideoList().size() > 0) {
                                    for (int i = 0; i < model.getVideoList().size(); i++) {
                                        vidlist.add(model.getVideoList().get(i));
                                    }
                                }
                            }

                            if (model.getImageListByCategory() != null) {

                                ArrayList<ImageListByCategory> catList = new ArrayList<>();
                                if (model.getImageListByCategory().size() > 0) {
                                    for (int i = 0; i < model.getImageListByCategory().size(); i++) {
                                        catList.add(model.getImageListByCategory().get(i));
                                    }
                                }

                                GalleryEventGroupAdapter adapter = new GalleryEventGroupAdapter(catList, piclist, vidlist, getContext());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                rvList.setLayoutManager(mLayoutManager);
                                rvList.setItemAnimator(new DefaultItemAnimator());
                                rvList.setAdapter(adapter);

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
                public void onFailure(Call<PageData> call, Throwable t) {
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
