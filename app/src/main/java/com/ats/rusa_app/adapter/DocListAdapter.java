package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.DocUpload;
import com.ats.rusa_app.util.ConnectivityDialog;

import java.util.ArrayList;

public class DocListAdapter extends RecyclerView.Adapter<DocListAdapter.MyViewHolder> {
    private ArrayList<DocUpload> docList;
    private Context context;

    public DocListAdapter(ArrayList<DocUpload> docList, Context context) {
        this.docList = docList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_doc_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final DocUpload model = docList.get(i);

        myViewHolder.tvName.setText("" + model.getTitle());
        myViewHolder.tvFile.setText("" + model.getTypeName());

        myViewHolder.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constants.isOnline(context)) {

                    new ConnectivityDialog(context).show();

                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + Constants.DOC_URL + model.getFileName()));
                    context.startActivity(browserIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return docList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvFile;
        public ImageView ivDownload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvFile = itemView.findViewById(R.id.tvType);
            ivDownload = itemView.findViewById(R.id.ivDownload);
        }
    }
}