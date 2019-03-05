package com.ats.rusa_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.model.Testimonials;

import java.util.ArrayList;

public class TestimonialsSliderAdapter extends RecyclerView.Adapter<TestimonialsSliderAdapter.MyViewHolder> {

    private ArrayList<Testimonials> testimonialsList;
    private Context context;

    public TestimonialsSliderAdapter(ArrayList<Testimonials> testimonialsList, Context context) {
        this.testimonialsList = testimonialsList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvMobile, tvAddress, tvEmail;
        public ImageView ivEdit;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvHead);
            tvMobile = view.findViewById(R.id.tvDesc);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_slider_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Testimonials model = testimonialsList.get(position);

        holder.tvName.setText(model.getName());
        holder.tvMobile.setText(model.getDesc());

    }

    @Override
    public int getItemCount() {
        return testimonialsList.size();
    }



}
