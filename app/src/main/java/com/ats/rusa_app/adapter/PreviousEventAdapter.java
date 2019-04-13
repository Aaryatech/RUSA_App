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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.FeedbackActivity;
import com.ats.rusa_app.activity.PreviousEventDetailActivity;
import com.ats.rusa_app.model.PrevEvent;
import com.google.gson.Gson;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class PreviousEventAdapter extends RecyclerView.Adapter<PreviousEventAdapter.MyViewHolder> {
    private ArrayList<PrevEvent> previousList;
    private Context context;

    public PreviousEventAdapter(ArrayList<PrevEvent> previousList, Context context) {
        this.previousList = previousList;
        this.context = context;
    }

    @NonNull
    @Override
    public PreviousEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_previous_event_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousEventAdapter.MyViewHolder myViewHolder, int i) {
        final PrevEvent model = previousList.get(i);

        myViewHolder.tv_eventName.setText(model.getHeading());
        myViewHolder.tv_Fromdate.setText(model.getDate());

        if (model.getApply() == 1 && model.getIsFeedback() == 0) {
            myViewHolder.linearLayout.setVisibility(View.VISIBLE);
        }

        try {
            if (model.getDescriptions() == null) {
                myViewHolder.tvDesc.setVisibility(View.GONE);
            } else if (model.getDescriptions().isEmpty()) {
                myViewHolder.tvDesc.setVisibility(View.GONE);
            } else {
                myViewHolder.tvDesc.setHtml("" + model.getDescriptions());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("UPCOMING EVE ADPT","------------------ EXCEPTION -"+e.getMessage());
        }

        if(model.getApply()==1 && model.getIsFeedback()==0) {
            myViewHolder.linearLayout.setVisibility(View.VISIBLE);
        }

        myViewHolder.tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);
                Intent intent = new Intent(context, FeedbackActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent=new Intent(context, PreviousEventDetailActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return previousList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_eventName, tv_Fromdate, tv_feedback;
        public HtmlTextView tvDesc;
        public CardView cardView;
        public LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_eventName = itemView.findViewById(R.id.tvEventName);
            tv_Fromdate = itemView.findViewById(R.id.tvFromdate);
            tv_feedback = itemView.findViewById(R.id.tv_feedback);
            linearLayout = itemView.findViewById(R.id.linearLayout_feedback);
            cardView = itemView.findViewById(R.id.cardView);
            tvDesc = itemView.findViewById(R.id.tvDesc);

        }
    }
}
