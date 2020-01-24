package com.ats.rusa_app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.fragment.GalleryEventDetailsFragment;
import com.ats.rusa_app.model.Gallery;
import com.ats.rusa_app.util.ConnectivityDialog;

import java.util.ArrayList;

public class GalleryEventCountAdapter extends RecyclerView.Adapter<GalleryEventCountAdapter.MyViewHolder> {
    private ArrayList<Gallery> eventList;
    private Context context;
    private String slug;

    public GalleryEventCountAdapter(ArrayList<Gallery> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

   /* public GalleryEventCountAdapter(ArrayList<Gallery> eventList, Context context, String slug) {
        this.eventList = eventList;
        this.context = context;
        this.slug = slug;
    }*/

    @NonNull
    @Override
    public GalleryEventCountAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gallery_event_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryEventCountAdapter.MyViewHolder myViewHolder, int i) {
        final Gallery model = eventList.get(i);

        myViewHolder.tvName.setText(""+model.getCatName());
        myViewHolder.tvVedio.setText(""+model.getVideoCount());
        myViewHolder.tvImages.setText(""+model.getImgCount());
        myViewHolder.tvCatrgori.setText(""+model.getCategoryCount());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Constants.isOnline(context)) {

                    new ConnectivityDialog(context).show();

                } else {

                    MainActivity activity = (MainActivity) context;

                    Fragment adf = new GalleryEventDetailsFragment();
                    Bundle args = new Bundle();
                    args.putString("slugName", model.getPageSlug());
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "PhotoGalleryFragment").commit();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvVedio,tvImages,tvCatrgori;
        public LinearLayout linearLayout;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_EventTitle);
            tvVedio = itemView.findViewById(R.id.tv_video);
            tvImages = itemView.findViewById(R.id.tv_images);
            tvCatrgori = itemView.findViewById(R.id.tv_categori);
           cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
