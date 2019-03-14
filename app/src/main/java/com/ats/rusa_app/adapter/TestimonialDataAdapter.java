package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.TestimonialWebviewActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.DetailNewsList;
import com.ats.rusa_app.model.TestImonialList;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class TestimonialDataAdapter extends RecyclerView.Adapter<TestimonialDataAdapter.MyViewHolder> {

    private ArrayList<TestImonialList> testmonialList;
    private Context context;

    public TestimonialDataAdapter(ArrayList<TestImonialList> testmonialList, Context context) {
        this.testmonialList = testmonialList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvReadMore;
        public HtmlTextView tvDesc;
        public ImageView ivImg;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);
            ivImg = view.findViewById(R.id.ivImg);
            linearLayout = view.findViewById(R.id.linearLayout);
            tvReadMore = view.findViewById(R.id.tvReadMore);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_testimonial_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TestImonialList model = testmonialList.get(position);

        holder.tvTitle.setText("" + model.getFromName());
        holder.tvDesc.setHtml("" + model.getMessage(), new HtmlHttpImageGetter(holder.tvDesc));

        try {
            Picasso.with(context).load(Constants.GALLERY_URL + model.getImageName()).placeholder(R.drawable.rusa_logo).into(holder.ivImg);
        } catch (Exception e) {
        }

        holder.tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent = new Intent(context, TestimonialWebviewActivity.class);
                intent.putExtra("model", json);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return testmonialList.size();
    }

}