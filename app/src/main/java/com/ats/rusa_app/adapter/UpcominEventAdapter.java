package com.ats.rusa_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.model.UpcomingEvent;

import java.util.ArrayList;

public class UpcominEventAdapter extends RecyclerView.Adapter<UpcominEventAdapter.MyViewHolder> {

    private ArrayList<UpcomingEvent> upcomingList;
    private Context context;

    public UpcominEventAdapter(ArrayList<UpcomingEvent> upcomingList, Context context) {
        this.upcomingList = upcomingList;
        this.context = context;
    }

    @NonNull
    @Override
    public UpcominEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_upcoming_event_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcominEventAdapter.MyViewHolder myViewHolder, int i) {
        UpcomingEvent model= upcomingList.get(i);
        myViewHolder.tv_eventName.setText(model.getExVar1());
        myViewHolder.tv_Fromdate.setText(model.getAddDate());
        myViewHolder.tv_Todate.setText(model.getEditDate());
    }

    @Override
    public int getItemCount() {
        return upcomingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_eventName,tv_Fromdate,tv_Todate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_eventName=itemView.findViewById(R.id.tvEventName);
            tv_Fromdate=itemView.findViewById(R.id.tvFromdate);
            tv_Todate=itemView.findViewById(R.id.tvTodate);
        }
    }
}
