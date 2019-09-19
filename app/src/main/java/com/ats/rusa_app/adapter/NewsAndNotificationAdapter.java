package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.EventDetailListActivity;
import com.ats.rusa_app.activity.NewsActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.NewDetail;
import com.ats.rusa_app.model.UpcomingEvent;
import com.ats.rusa_app.util.ConnectivityDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class NewsAndNotificationAdapter extends RecyclerView.Adapter<NewsAndNotificationAdapter.MyViewHolder> {
    private ArrayList<UpcomingEvent> newsList;
    private Context context;

    public NewsAndNotificationAdapter(ArrayList<UpcomingEvent> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_feed_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final UpcomingEvent model = newsList.get(i);
        String imageUri = Constants.GALLERY_URL + model.getFeaturedImage();

        try {
            Picasso.with(context).load(imageUri).placeholder(context.getResources().getDrawable(R.drawable.logo_new)).into(myViewHolder.imageView);
        } catch (Exception e) {
        }


        myViewHolder.tv_newsTitle.setText(model.getHeading());
      //  myViewHolder.tv_newsDisc.setHtml(model.getDescriptions(), new HtmlHttpImageGetter(myViewHolder.tv_newsDisc));
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Gson gson = new Gson();
                    String json = gson.toJson(model);

                    if (!Constants.isOnline(context)) {

                        new ConnectivityDialog(context).show();

                    } else {
                        Intent intent = new Intent(context, EventDetailListActivity.class);
                        intent.putExtra("model", json);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e("Exception : ", "-----------" + e.getMessage());
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_newsTitle;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_news);
            tv_newsTitle = (TextView) itemView.findViewById(R.id.tv_newsTitle);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
