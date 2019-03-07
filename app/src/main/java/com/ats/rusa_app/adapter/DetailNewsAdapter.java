package com.ats.rusa_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.DetailNewsList;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class DetailNewsAdapter extends RecyclerView.Adapter<DetailNewsAdapter.MyViewHolder> {

    private ArrayList<DetailNewsList> newsList;
    private Context context;

    public DetailNewsAdapter(ArrayList<DetailNewsList> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public HtmlTextView tvDesc;
        public ImageView ivImg;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);
            ivImg = view.findViewById(R.id.ivImg);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_detail_news, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DetailNewsList model = newsList.get(position);

        holder.tvTitle.setText("" + model.getHeading());
        holder.tvDesc.setHtml("" + model.getDescriptions());

        try {
            Glide
                    .with(context)
                    .load(Constants.GALLERY_URL+model.getFeaturedImage())
                    .centerCrop()
                    .into(holder.ivImg);
        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}