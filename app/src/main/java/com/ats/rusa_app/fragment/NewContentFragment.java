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
import com.ats.rusa_app.activity.MainActivity;
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
import com.ats.rusa_app.util.CustomSharedPreference;

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
    int languageId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_content, container, false);

        tvHeading = view.findViewById(R.id.tvHeading);
        tvNoRecord = view.findViewById(R.id.tvNoRecord);
        recyclerView = view.findViewById(R.id.recyclerView);

        String langId = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }

        try {
            slugName = getArguments().getString("slugName");
            getPageData(slugName, languageId);
        } catch (Exception e) {
            Log.e("ContentFrag : ", " ----------- " + e.getMessage());
            e.printStackTrace();
        }

        return view;
    }


    public void getPageData(final String slugName, int langId) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PageData> listCall = Constants.myInterface.getPageData(slugName, langId);
            listCall.enqueue(new Callback<PageData>() {
                @Override
                public void onResponse(Call<PageData> call, Response<PageData> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PAGE DATA : ", " - " + response.body());

                            PageData model = response.body();
                            model.setSlugName(slugName);
                            ArrayList<PageData> dataList = new ArrayList<>();
                            dataList.add(model);

                            if (model.getPageId() > 0) {

                                tvHeading.setText("" + model.getPageName());
                               // getActivity().setTitle("" + model.getPageName());

                                boolean flag = false;

                                if (model.getCmsContentList() == null && model.getGallaryDetailList() == null && model.getTestImonialList() == null && model.getDetailNewsList() == null && model.getDocumentUploadList() == null && model.getFaqContentList() == null && model.getTeamList() == null && model.getSuccessList() == null && model.getImageListByCategory() == null && model.getVideoList() == null) {
                                    tvNoRecord.setVisibility(View.VISIBLE);
                                } else if (model.getCmsContentList() != null) {
                                    if (model.getCmsContentList().size() == 0) {
                                        tvNoRecord.setVisibility(View.VISIBLE);
                                        flag = false;
                                    } else {
                                        flag = true;
                                    }
                                } else if (model.getGallaryDetailList() != null) {
                                    if (model.getGallaryDetailList().size() == 0) {
                                        tvNoRecord.setVisibility(View.VISIBLE);
                                        flag = false;
                                    } else {
                                        flag = true;
                                    }

                                } else if (model.getTestImonialList() != null) {
                                    if (model.getTestImonialList().size() == 0) {
                                        tvNoRecord.setVisibility(View.VISIBLE);
                                        flag = false;
                                    } else {
                                        flag = true;
                                    }
                                } else if (model.getFaqContentList() != null) {
                                    if (model.getFaqContentList().size() == 0) {
                                        tvNoRecord.setVisibility(View.VISIBLE);
                                        flag = false;
                                    } else {
                                        flag = true;
                                    }
                                } else if (model.getDocumentUploadList() != null) {
                                    if (model.getDocumentUploadList().size() == 0) {
                                        tvNoRecord.setVisibility(View.VISIBLE);
                                        flag = false;
                                    } else {
                                        flag = true;
                                    }
                                } else if (model.getDetailNewsList() != null) {
                                    if (model.getDetailNewsList().size() == 0) {
                                        tvNoRecord.setVisibility(View.VISIBLE);
                                        flag = false;
                                    } else {
                                        flag = true;
                                    }
                                }else if (model.getTeamList() != null) {
                                    Log.e("NEW CONTENT FRG","------------------ TEAM");
                                    if (model.getTeamList().size() == 0) {
                                        tvNoRecord.setVisibility(View.VISIBLE);
                                        flag = false;
                                    } else {
                                        flag = true;
                                    }
                                }else if (model.getSuccessList() != null) {
                                    Log.e("NEW CONTENT FRG","------------------ Success Stories");
                                    if (model.getSuccessList().size() == 0) {
                                        tvNoRecord.setVisibility(View.VISIBLE);
                                        flag = false;
                                    } else {
                                        flag = true;
                                    }
                                }

                                if (flag) {
                                    tvNoRecord.setVisibility(View.GONE);

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
