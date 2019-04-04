package com.ats.rusa_app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.fragment.GalleryEventDetailsFragment;
import com.ats.rusa_app.model.FaqContentList;
import com.ats.rusa_app.model.GetGalleryCategory;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class GalleryEventsAdapter extends RecyclerView.Adapter<GalleryEventsAdapter.MyViewHolder> {

    private ArrayList<GetGalleryCategory> eventList;
    private Context context;

    public GalleryEventsAdapter(ArrayList<GetGalleryCategory> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            linearLayout = view.findViewById(R.id.linearLayout);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_gallery_events, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GetGalleryCategory model = eventList.get(position);

        holder.tvName.setText("" + model.getCatName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity activity = (MainActivity) context;

                Fragment adf = new GalleryEventDetailsFragment();
                Bundle args = new Bundle();
                args.putString("slugName", model.getSlugName());
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "PhotoGalleryFragment").commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
