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
import com.ats.rusa_app.model.SuccessList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvSuccessStoryListAdapter extends RecyclerView.Adapter<RvSuccessStoryListAdapter.MyViewHolder> {
    private ArrayList<SuccessList> successLists;
    private Context context;

    public RvSuccessStoryListAdapter(ArrayList<SuccessList> successLists, Context context) {
        this.successLists = successLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_rv_success_story, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final SuccessList model = successLists.get(i);
       // Log.e("Model Success Story", "----------------" + model);

        try {
            String imageUri = Constants.GALLERY_URL + model.getImageName();
            Picasso.with(context).load(imageUri).placeholder(context.getResources().getDrawable(R.drawable.logo_new)).into(myViewHolder.ivImg);
        } catch (Exception e) {
        }
        myViewHolder.tvName.setText(model.getFromName());
        myViewHolder.tvDesc.setText(model.getMessage());
        myViewHolder.tvType.setText(model.getDesignation() + ", " + model.getLocation());


    }

    @Override
    public int getItemCount() {
        return successLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImg;
        public TextView tvName, tvDesc, tvType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
        }
    }
}