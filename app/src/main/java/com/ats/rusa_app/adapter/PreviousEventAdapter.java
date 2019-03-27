package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.PreviousEventDetailActivity;
import com.ats.rusa_app.model.PreviousEvent;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PreviousEventAdapter extends RecyclerView.Adapter<PreviousEventAdapter.MyViewHolder>{
    private ArrayList<PreviousEvent> previousList;
    private Context context;

    public PreviousEventAdapter(ArrayList<PreviousEvent> previousList, Context context) {
        this.previousList = previousList;
        this.context = context;
    }

    @NonNull
    @Override
    public PreviousEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_upcoming_event_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousEventAdapter.MyViewHolder myViewHolder, int i) {
        final PreviousEvent model= previousList.get(i);
        myViewHolder.tv_eventName.setText(model.getHeading());
        myViewHolder.tv_Fromdate.setText(model.getEventDateFrom());
        myViewHolder.tv_Todate.setText(""+model.getEventDateTo());

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
        public TextView tv_eventName,tv_Fromdate,tv_Todate;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_eventName=itemView.findViewById(R.id.tvEventName);
            tv_Fromdate=itemView.findViewById(R.id.tvFromdate);
            tv_Todate=itemView.findViewById(R.id.tvTodate);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
