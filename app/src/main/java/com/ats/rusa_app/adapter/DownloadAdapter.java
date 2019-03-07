package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.WebViewActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.DocumentUploadList;
import com.ats.rusa_app.model.FaqContentList;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.MyViewHolder> {

    private ArrayList<DocumentUploadList> doctList;
    private Context context;

    public DownloadAdapter(ArrayList<DocumentUploadList> doctList, Context context) {
        this.doctList = doctList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public LinearLayout llFile;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            llFile = view.findViewById(R.id.llFile);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_download, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DocumentUploadList model = doctList.get(position);

        holder.tvName.setText("" + model.getExVar1());

        holder.llFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra("EXTRA_TABLE_HTML", Constants.PDF_URL + model.getFileName());
//                context.startActivity(intent);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""+Constants.PDF_URL+model.getFileName()));
                context.startActivity(browserIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return doctList.size();
    }


}