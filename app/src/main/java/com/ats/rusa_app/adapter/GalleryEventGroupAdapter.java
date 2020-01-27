package com.ats.rusa_app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.fragment.GalleryDisplayFragment;
import com.ats.rusa_app.fragment.GalleryEventDetailsFragment;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.GetGalleryCategory;
import com.ats.rusa_app.model.ImageListByCategory;
import com.ats.rusa_app.model.VideoList;
import com.ats.rusa_app.util.ConnectivityDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryEventGroupAdapter extends RecyclerView.Adapter<GalleryEventGroupAdapter.MyViewHolder> {

    private ArrayList<ImageListByCategory> groupList;
    private ArrayList<GallaryDetailList> galleryList;
    private ArrayList<VideoList> videoList;
    private Context context;

    public GalleryEventGroupAdapter(ArrayList<ImageListByCategory> groupList, Context context) {
        this.groupList = groupList;
        this.context = context;
    }

    public GalleryEventGroupAdapter(ArrayList<ImageListByCategory> groupList, ArrayList<GallaryDetailList> galleryList, ArrayList<VideoList> videoList, Context context) {
        this.groupList = groupList;
        this.galleryList = galleryList;
        this.videoList = videoList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvImgCount, tvVideoCount;
        private ImageView ivImg;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvImgCount = view.findViewById(R.id.tvImgCount);
            tvVideoCount = view.findViewById(R.id.tvVideoCount);
            ivImg = view.findViewById(R.id.ivImg);
            linearLayout = view.findViewById(R.id.linearLayout);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_gallery_event_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ImageListByCategory model = groupList.get(position);

        holder.tvName.setText("" + model.getCateName());
        holder.tvImgCount.setText("" + model.getPicCount());
        holder.tvVideoCount.setText("" + model.getVideoCount());

        try {
            Picasso.with(context).load(Constants.GALLERY_URL + model.getFileName()).placeholder(R.drawable.img_placeholder).into(holder.ivImg);
        } catch (Exception e) {
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity activity = (MainActivity) context;


                ArrayList<GallaryDetailList> picList = new ArrayList<>();
                ArrayList<VideoList> vidList = new ArrayList<>();

                try {

                    if (galleryList != null) {
                        if (galleryList.size() > 0) {
                            for (int i = 0; i < galleryList.size(); i++) {
                                if (model.getGalleryCatId() == galleryList.get(i).getGalleryCatId()) {
                                    picList.add(galleryList.get(i));
                                }
                            }

                        }
                    }

                    if (videoList != null) {
                        if (videoList.size() > 0) {
                            for (int i = 0; i < videoList.size(); i++) {
                                if (model.getGalleryCatId() == videoList.get(i).getGalleryCatId()) {
                                    vidList.add(videoList.get(i));
                                }
                            }

                        }
                    }

                } catch (Exception e) {
                    //Log.e("GAL EVENT GRP ADPT", "------------------------ EXCEPTION : " + e.getMessage());
                   // e.printStackTrace();
                }


                Gson gsonPic = new Gson();
                Gson gsonVideo = new Gson();
                String strGallery = gsonPic.toJson(picList);
                String strVideo = gsonVideo.toJson(vidList);

                //Log.e("PIC ", "------------------ " + picList);
                //Log.e("VID ", "------------------ " + vidList);

                if (!Constants.isOnline(context)) {

                    new ConnectivityDialog(context).show();

                } else {


                    Fragment adf = new GalleryDisplayFragment();
                    Bundle args = new Bundle();
                    args.putString("title", model.getCateName());
                    args.putString("gallery", strGallery);
                    args.putString("video", strVideo);
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "GalleryEventDetailFragment").commit();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

}
