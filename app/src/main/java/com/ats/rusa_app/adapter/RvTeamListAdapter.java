package com.ats.rusa_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.TeamList;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class RvTeamListAdapter extends RecyclerView.Adapter<RvTeamListAdapter.MyViewHolder> {
    private ArrayList<TeamList> teamList;
    private Context context;

    public RvTeamListAdapter(ArrayList<TeamList> teamList, Context context) {
        this.teamList = teamList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_rv_team_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final TeamList model = teamList.get(i);
        Log.e("Model TEAM", "----------------" + model);

        try {
            String imageUri = Constants.GALLERY_URL + model.getImageName();
            Picasso.with(context).load(imageUri).placeholder(context.getResources().getDrawable(R.drawable.img_placeholder)).into(myViewHolder.ivImg);
        } catch (Exception e) {
        }
        myViewHolder.tvName.setText(model.getFromName());
        myViewHolder.tvDesg.setText(model.getDesignation());


    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImg;
        public TextView tvName, tvDesg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDesg = (TextView) itemView.findViewById(R.id.tvDesg);
        }
    }
}
