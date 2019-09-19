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

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.YoutubeVideosAdapter;
import com.ats.rusa_app.interfaces.VideosInterface;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.VideoList;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ats.rusa_app.fragment.GalleryDisplayFragment.staticVideosList;

public class VideosFragment extends Fragment implements VideosInterface {

    private RecyclerView rvVideos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        rvVideos = view.findViewById(R.id.rvVideos);


        return view;
    }

    @Override
    public void fragmentBecameVisible() {

        try {
            ArrayList<GallaryDetailList> videoList = new ArrayList<>();

            for (int i = 0; i < staticVideosList.size(); i++) {

                VideoList video = staticVideosList.get(i);

               /* String iframeStr = video.getFileName();
                Log.e("STRING ", "---------- " + iframeStr);

                int index = (iframeStr.lastIndexOf("src"));
                Log.e("FIRST INDEX : ", "-------------------- " + index);
                int firstIndex = (iframeStr.indexOf('"', index)) + 1;
                Log.e("LAST INDEX : ", "-------------------- " + firstIndex);
                int lastIndex = iframeStr.indexOf('"', firstIndex);


                Log.e("VIDEO URL : ", "------------------------ " + iframeStr.substring(firstIndex, lastIndex));

                String code = extractYTId(iframeStr.substring(firstIndex, lastIndex)).replace("\\", "");

                Log.e("VIDEO CODE : ", "------------------------ " + code);

                GallaryDetailList videoModel = new GallaryDetailList(1, 1, 1, 1, "1", "video 1", "aa", "", "", 1, "", "", 1, 1, 1, 1, 1, 1, "" + code, "");
                videoList.add(videoModel);*/

                String code =video.getFileName() ;

                Log.e("VIDEO CODE : ", "------------------------ " + code);

                GallaryDetailList videoModel = new GallaryDetailList(1, 1, 1, 1, "1", "video 1", "aa", "", "", 1, "", "", 1, 1, 1, 1, 1, 1, "" + code, "");
                videoList.add(videoModel);

            }

            YoutubeVideosAdapter adapter = new YoutubeVideosAdapter(videoList, getContext());
            rvVideos.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rvVideos.setItemAnimator(new DefaultItemAnimator());
            rvVideos.setAdapter(adapter);


        } catch (Exception e) {
            Log.e("Videos FRAG", "----------- EXCEPTION : " + e.getMessage());
            e.printStackTrace();
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
