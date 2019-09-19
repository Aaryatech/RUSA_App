package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.YoutubePlayerActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.util.Config;
import com.ats.rusa_app.util.ConnectivityDialog;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {

    private ArrayList<GallaryDetailList> videoList;
    private Context context;

    public VideosAdapter(ArrayList<GallaryDetailList> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public YouTubeThumbnailView ytThumb;

        public MyViewHolder(View view) {
            super(view);
            ytThumb = view.findViewById(R.id.ytThumb);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_youtube_videos, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GallaryDetailList model = videoList.get(position);

        holder.ytThumb.initialize(Config.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(model.getExVar1());

                Log.e("Vedio model","----------------"+model.getFileName());
                //model.getExVar1()
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


        holder.ytThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Constants.isOnline(context)) {

                    new ConnectivityDialog(context).show();

                } else {
                    Intent intent = new Intent(context, YoutubePlayerActivity.class);
                    intent.putExtra("video", model.getExVar1());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
