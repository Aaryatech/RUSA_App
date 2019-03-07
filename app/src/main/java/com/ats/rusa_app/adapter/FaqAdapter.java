package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.model.FaqContentList;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    private ArrayList<FaqContentList> faqtList;
    private Context context;

    public FaqAdapter(ArrayList<FaqContentList> faqtList, Context context) {
        this.faqtList = faqtList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public HtmlTextView tvQue, tvAns;

        public MyViewHolder(View view) {
            super(view);
            tvQue = view.findViewById(R.id.tvQue);
            tvAns = view.findViewById(R.id.tvAns);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_faq, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FaqContentList model = faqtList.get(position);

        holder.tvQue.setHtml(model.getFaqQue());
        holder.tvAns.setHtml(model.getFaqAns());


    }

    @Override
    public int getItemCount() {
        return faqtList.size();
    }


}
