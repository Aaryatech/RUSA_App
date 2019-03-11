package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.ContentAdapter;
import com.ats.rusa_app.adapter.DetailNewsAdapter;
import com.ats.rusa_app.adapter.DownloadAdapter;
import com.ats.rusa_app.adapter.FaqAdapter;
import com.ats.rusa_app.adapter.GalleryAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.DetailNewsList;
import com.ats.rusa_app.model.DocumentUploadList;
import com.ats.rusa_app.model.FaqContentList;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.PageData;
import com.ats.rusa_app.util.CommonDialog;

import org.sufficientlysecure.htmltextview.DrawTableLinkSpan;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewContentFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvHeading, tvNoRecord;

    private String slugName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_content, container, false);

        tvHeading = view.findViewById(R.id.tvHeading);
        tvNoRecord = view.findViewById(R.id.tvNoRecord);
        recyclerView = view.findViewById(R.id.recyclerView);

        try {
            slugName = getArguments().getString("slugName");
            getPageData(slugName);
        } catch (Exception e) {
            Log.e("ContentFrag : ", " ----------- " + e.getMessage());
            e.printStackTrace();
        }

        return view;
    }


    public void getPageData(final String slugName) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PageData> listCall = Constants.myInterface.getPageData(slugName, 1);
            listCall.enqueue(new Callback<PageData>() {
                @Override
                public void onResponse(Call<PageData> call, Response<PageData> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PAGE DATA : ", " - " + response.body());

                            PageData model = response.body();
                            ArrayList<PageData> dataList = new ArrayList<>();
                            dataList.add(model);

                            if (model.getPageId() > 0) {

                                tvHeading.setText("" + model.getPageName());
                                getActivity().setTitle("" + model.getPageName());

                                if (model.getCmsContentList() == null && model.getGallaryDetailList() == null && model.getTestImonialList() == null && model.getDetailNewsList() == null && model.getDocumentUploadList() == null && model.getFaqContentList() == null) {
                                    tvNoRecord.setVisibility(View.VISIBLE);
                                } else {

                                    ContentAdapter adapter = new ContentAdapter(dataList, getContext());
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(adapter);
                                }

                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            tvNoRecord.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                        tvNoRecord.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<PageData> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                    tvNoRecord.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
            tvNoRecord.setVisibility(View.VISIBLE);
        }

    }


}
