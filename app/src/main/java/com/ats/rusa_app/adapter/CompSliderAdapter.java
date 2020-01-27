package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.CompanyModel;
import com.ats.rusa_app.util.ConnectivityDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CompSliderAdapter extends RecyclerView.Adapter<CompSliderAdapter.MyViewHolder> {
    private ArrayList<CompanyModel> compList;
    private Context context;

    public CompSliderAdapter(ArrayList<CompanyModel> compList, Context context) {
        this.compList = compList;
        this.context = context;
    }

    @NonNull
    @Override
    public CompSliderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comp_slider_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompSliderAdapter.MyViewHolder myViewHolder, int i) {
        final CompanyModel model = compList.get(i);
        String imageUri = Constants.BANENR_URL + model.getSliderImage();
        //Log.e("Image", "--------------" + imageUri);
        try {
            Picasso.with(context).load(imageUri).placeholder(context.getResources().getDrawable(R.drawable.imp_links_placeholder)).into(myViewHolder.imageView);

        } catch (Exception e) {
        }
        myViewHolder.linearLayout_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constants.isOnline(context)) {

                    new ConnectivityDialog(context).show();

                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + model.getUrlLink()));
                    context.startActivity(browserIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return compList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public LinearLayout linearLayout_comp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_comp);
            linearLayout_comp = (LinearLayout) itemView.findViewById(R.id.linearLayout_comp);
        }
    }
}
