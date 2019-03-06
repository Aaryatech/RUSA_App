package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.MenuGroup;
import com.ats.rusa_app.model.MenuModel;
import com.ats.rusa_app.model.PageData;
import com.ats.rusa_app.util.CommonDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentFragment extends Fragment {

    private TextView tvHeading;
    private RecyclerView recyclerView;
    private LinearLayout llWebview;
    private WebView webView;

    String slugName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        tvHeading = view.findViewById(R.id.tvHeading);
        recyclerView = view.findViewById(R.id.recyclerView);
        llWebview = view.findViewById(R.id.llWebview);
        webView = view.findViewById(R.id.webview);

        try {
            slugName = getArguments().getString("slugName");
            getPageData(slugName);
        } catch (Exception e) {
            Log.e("ContentFrag : ", " ----------- " + e.getMessage());
            e.printStackTrace();
        }

        return view;
    }

    public void getPageData(String slugName) {

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

                            if (model.getPageId() > 0) {

                                if (model.getCmsContentList() != null) {
                                    Toast.makeText(getContext(), "CMS", Toast.LENGTH_SHORT).show();
                                } else if (model.getFaqContentList() != null) {
                                    Toast.makeText(getContext(), "FAQS", Toast.LENGTH_SHORT).show();
                                } else if (model.getDocumentUploadList() != null) {
                                    Toast.makeText(getContext(), "DOC", Toast.LENGTH_SHORT).show();
                                } else if (model.getTestImonialList() != null) {
                                    Toast.makeText(getContext(), "TESTIMONIALS", Toast.LENGTH_SHORT).show();
                                } else if (model.getGallaryDetailList() != null) {
                                    Toast.makeText(getContext(), "GALLERY", Toast.LENGTH_SHORT).show();
                                } else if (model.getDetailNewsList() != null) {
                                    Toast.makeText(getContext(), "NEWS", Toast.LENGTH_SHORT).show();
                                }
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
