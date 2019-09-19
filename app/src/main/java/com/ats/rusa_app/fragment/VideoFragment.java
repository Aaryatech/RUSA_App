package com.ats.rusa_app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.YoutubePlayerActivity;
import com.ats.rusa_app.adapter.ContentAdapter;
import com.ats.rusa_app.adapter.RvGalleryAdapter;
import com.ats.rusa_app.adapter.VideosAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.PageData;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.Config;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

public class VideoFragment extends Fragment {

    public YouTubeThumbnailView ytThumb;
    public TextView tvHeading, tvNoRecord;

    private RecyclerView rvVideo;

    private String slugName;
    int languageId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        tvNoRecord = view.findViewById(R.id.tvNoRecord);
        tvHeading = view.findViewById(R.id.tvHeading);
        tvHeading.setText("Videos");

        rvVideo = view.findViewById(R.id.rvVideo);

        ytThumb = view.findViewById(R.id.ytThumb);
        Log.e("", "");

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


/*
        ytThumb.initialize(Config.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo("8DMF0U6xV78");
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });


        ytThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
                intent.putExtra("video", "8DMF0U6xV78");
                getContext().startActivity(intent);
            }
        });
*/


        return view;
    }


    public void getPageData(final String slugName, int langId) {


        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PageData> listCall = Constants.myInterface.getPageData(slugName, langId,authHeader);
            listCall.enqueue(new Callback<PageData>() {
                @Override
                public void onResponse(Call<PageData> call, Response<PageData> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PAGE DATA : ", " - " + response.body());

                            PageData model = response.body();
                            model.setSlugName(slugName);


                            if (model.getCmsContentList() != null) {

                                if (model.getCmsContentList().size() > 0) {

                                    ArrayList<GallaryDetailList> videoList = new ArrayList<>();

                                    for (int i = 0; i < model.getCmsContentList().size(); i++) {


                                        String iframeStr = model.getCmsContentList().get(i).getPageDesc();
                                        Log.e("STRING ", "---------- " + iframeStr);

                                        int index = (iframeStr.lastIndexOf("src"));
                                        Log.e("FIRST INDEX : ", "-------------------- " + index);
                                        int firstIndex = (iframeStr.indexOf('"', index)) + 1;
                                        Log.e("LAST INDEX : ", "-------------------- " + firstIndex);
                                        int lastIndex = iframeStr.indexOf('"', firstIndex);


                                        Log.e("VIDEO URL : ", "------------------------ " + iframeStr.substring(firstIndex, lastIndex));

                                        final String code = extractYTId(iframeStr.substring(firstIndex, lastIndex)).replace("\\", "");

                                        Log.e("VIDEO CODE : ", "------------------------ " + code);

                                        GallaryDetailList videoModel = new GallaryDetailList(1, 1, 1, 1, "1", "video 1", "aa", "", "", 1, "", "", 1, 1, 1, 1, 1, 1, "" + code, "");
                                        videoList.add(videoModel);


                                    }

                                    VideosAdapter adapter = new VideosAdapter(videoList, getContext());
                                    // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                    //holder.galleryRecyclerView.setLayoutManager(mLayoutManager);
                                    rvVideo.setLayoutManager(new GridLayoutManager(getContext(), 2));
                                    rvVideo.setItemAnimator(new DefaultItemAnimator());
                                    rvVideo.setAdapter(adapter);

                                }

                            } else {
                                tvNoRecord.setVisibility(View.VISIBLE);
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

    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }


}
