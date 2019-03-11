package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.NewDetail;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder>{
    private ArrayList<NewDetail> newsList;
    private Context context;

    public NewsFeedAdapter(ArrayList<NewDetail> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsFeedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_feed_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedAdapter.MyViewHolder myViewHolder, int i) {
        final NewDetail model = newsList.get(i);
        String imageUri = Constants.GALLERY_URL + model.getFeaturedImage();
        Picasso.with(context).load(imageUri).into(myViewHolder.imageView);
        myViewHolder.tv_newsTitle.setText(model.getHeading());
        myViewHolder.tv_newsDisc.setHtml(model.getDescriptions(), new HtmlHttpImageGetter(myViewHolder.tv_newsDisc));
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""+model.getNewsSourceUrlName()));
                    context.startActivity(browserIntent);
                }catch (Exception e)
                {
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
        public HtmlTextView tv_newsDisc;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.iv_news);
            tv_newsTitle=(TextView)itemView.findViewById(R.id.tv_newsTitle);
            tv_newsDisc=(HtmlTextView)itemView.findViewById(R.id.tv_newsDiscription);
            cardView=(CardView)itemView.findViewById(R.id.cardView);
        }
    }
}
