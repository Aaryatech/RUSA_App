package com.ats.rusa_app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.YoutubePlayerActivity;
import com.ats.rusa_app.util.Config;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class YoutubeThmbFragment extends Fragment {

    private YouTubeThumbnailView yt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_youtube_thmb, container, false);

        yt = view.findViewById(R.id.yt);

        yt.initialize(Config.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo("gG2npfpaqsY");
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                        Toast.makeText(getContext(), "Valid URL", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        try {
                            Toast.makeText(getActivity(), "Not a valid youtube url.", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            //ex.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });


        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), YoutubePlayerActivity.class));
            }
        });

        return view;
    }

}
