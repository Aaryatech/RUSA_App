package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.ImageZoomActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.GallaryDetailList;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<GallaryDetailList> galleryList;
    private static LayoutInflater inflater = null;

    public GalleryAdapter(Context c, ArrayList<GallaryDetailList> galleryList) {
        this.mContext = c;
        this.galleryList = galleryList;
        this.inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return galleryList.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        ImageView ivImg;
        LinearLayout llImage, llVideo;
        YouTubeThumbnailView ytThumb;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final GallaryDetailList model = galleryList.get(position);

        Log.e("MODEL : ", "- ------ " + model);

        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.adapter_gallery, null);
        holder.ivImg = rowView.findViewById(R.id.ivImg);
        holder.ytThumb = rowView.findViewById(R.id.ytThumb);
        holder.llImage = rowView.findViewById(R.id.llImage);
        holder.llVideo = rowView.findViewById(R.id.llVideo);

        if (model.getTypeVideoImage().equals("3")) {
            Log.e("TYPE : ", "- ------ 3");
            holder.llImage.setVisibility(View.VISIBLE);
            holder.llVideo.setVisibility(View.GONE);

            try {
                Picasso.with(mContext).load(Constants.GALLERY_URL + model.getFileName()).placeholder(R.drawable.img_placeholder).into(holder.ivImg);
            } catch (Exception e) {
            }

        } else if (model.getTypeVideoImage().equals("4")) {
            Log.e("TYPE : ", "- ------ 4");
            holder.llImage.setVisibility(View.GONE);
            holder.llVideo.setVisibility(View.VISIBLE);

        }

        holder.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageZoomActivity.class);
                intent.putExtra("image", Constants.GALLERY_URL + model.getFileName());
                mContext.startActivity(intent);
            }
        });


        return rowView;
    }


}
