package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.FullScreenViewActivity;
import com.ats.rusa_app.activity.ImageZoomActivity;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.FaqContentList;
import com.ats.rusa_app.model.GallaryDetailList;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class RvGalleryAdapter extends RecyclerView.Adapter<RvGalleryAdapter.MyViewHolder> {

    private ArrayList<GallaryDetailList> galleryList;
    private Context context;

    public RvGalleryAdapter(ArrayList<GallaryDetailList> galleryList, Context context) {
        this.galleryList = galleryList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        LinearLayout llImage, llVideo;
        YouTubeThumbnailView ytThumb;

        public MyViewHolder(View view) {
            super(view);
            ivImg = view.findViewById(R.id.ivImg);
            llImage = view.findViewById(R.id.llImage);
            llVideo = view.findViewById(R.id.llVideo);
            ytThumb = view.findViewById(R.id.ytThumb);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_gallery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GallaryDetailList model = galleryList.get(position);

        if (model.getTypeVideoImage().equals("3")) {
            //Log.e("TYPE : ", "- ------ 3");
            holder.llImage.setVisibility(View.VISIBLE);
            holder.llVideo.setVisibility(View.GONE);

            try {
                Picasso.with(context).load(Constants.GALLERY_URL + model.getFileName()).placeholder(R.drawable.img_placeholder).into(holder.ivImg);
            } catch (Exception e) {
            }

        } else if (model.getTypeVideoImage().equals("4")) {
            //Log.e("TYPE : ", "- ------ 4");
            holder.llImage.setVisibility(View.GONE);
            holder.llVideo.setVisibility(View.VISIBLE);

        }

        holder.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ImageZoomActivity.class);
//                intent.putExtra("image", Constants.GALLERY_URL + model.getFileName());
//                context.startActivity(intent);

                Gson gson = new Gson();
                String jsonStr = gson.toJson(galleryList);

                MainActivity activity = (MainActivity) context;



                Intent i = new Intent(activity, FullScreenViewActivity.class);
                i.putExtra("position", position);
                i.putExtra("gallery", jsonStr);
                activity.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

}
