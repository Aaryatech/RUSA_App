package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.CmsContentList;
import com.ats.rusa_app.model.DetailNewsList;
import com.ats.rusa_app.model.DocumentUploadList;
import com.ats.rusa_app.model.FaqContentList;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.PageData;
import com.ats.rusa_app.model.SuccessList;
import com.ats.rusa_app.model.TeamList;
import com.ats.rusa_app.model.TestImonialList;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {

    private ArrayList<PageData> dataList;
    private Context context;

    public ContentAdapter(ArrayList<PageData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView cmsRecyclerView;
        public RecyclerView faqRecyclerView;
        public RecyclerView newsRecyclerView;
        public RecyclerView docRecyclerView;
        public RecyclerView testimonialRecyclerView;
        public RecyclerView galleryRecyclerView;
        public RecyclerView teamRecyclerView;
        public RecyclerView successStoryRecyclerView;

        public MyViewHolder(View view) {
            super(view);
            cmsRecyclerView = view.findViewById(R.id.cmsRecyclerView);
            faqRecyclerView = view.findViewById(R.id.faqRecyclerView);
            newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
            docRecyclerView = view.findViewById(R.id.docRecyclerView);
            testimonialRecyclerView = view.findViewById(R.id.testimonialRecyclerView);
            galleryRecyclerView = view.findViewById(R.id.galleryRecyclerView);
            teamRecyclerView = view.findViewById(R.id.teamRecyclerView);
            successStoryRecyclerView = view.findViewById(R.id.successStoryRecyclerView);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_content, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PageData model = dataList.get(position);

        try {

            if (model.getCmsContentList() != null) {
                holder.cmsRecyclerView.setVisibility(View.VISIBLE);
                //Log.e("CONTENT ADAPTER","**********************************************************");

                ArrayList<CmsContentList> cmsList = new ArrayList<>();
                for (int i = 0; i < model.getCmsContentList().size(); i++) {
                    cmsList.add(model.getCmsContentList().get(i));
                }

                CmsDataAdapter adapter = new CmsDataAdapter(cmsList, context,model.getSlugName());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.cmsRecyclerView.setLayoutManager(mLayoutManager);
                holder.cmsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.cmsRecyclerView.setAdapter(adapter);
            }

            if (model.getFaqContentList() != null) {
                holder.faqRecyclerView.setVisibility(View.VISIBLE);

                ArrayList<FaqContentList> faqList = new ArrayList<>();
                for (int i = 0; i < model.getFaqContentList().size(); i++) {
                    faqList.add(model.getFaqContentList().get(i));
                }

                FaqAdapter faqAdapter = new FaqAdapter(faqList, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.faqRecyclerView.setLayoutManager(mLayoutManager);
                holder.faqRecyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.faqRecyclerView.setAdapter(faqAdapter);

            }

            if (model.getDocumentUploadList() != null) {
                holder.docRecyclerView.setVisibility(View.VISIBLE);

                ArrayList<DocumentUploadList> docList = new ArrayList<>();
                for (int i = 0; i < model.getDocumentUploadList().size(); i++) {
                    docList.add(model.getDocumentUploadList().get(i));
                }

                DownloadAdapter adapter = new DownloadAdapter(docList, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.docRecyclerView.setLayoutManager(mLayoutManager);
                holder.docRecyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.docRecyclerView.setAdapter(adapter);

            }

            if (model.getDetailNewsList() != null) {
                holder.newsRecyclerView.setVisibility(View.VISIBLE);

                ArrayList<DetailNewsList> newsList = new ArrayList<>();
                for (int i = 0; i < model.getDetailNewsList().size(); i++) {
                    newsList.add(model.getDetailNewsList().get(i));
                }

                DetailNewsAdapter adapter = new DetailNewsAdapter(newsList, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.newsRecyclerView.setLayoutManager(mLayoutManager);
                holder.newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.newsRecyclerView.setAdapter(adapter);


            }

            if (model.getTestImonialList() != null) {
                holder.testimonialRecyclerView.setVisibility(View.VISIBLE);

                ArrayList<TestImonialList> testimonialList = new ArrayList<>();
                for (int i = 0; i < model.getTestImonialList().size(); i++) {
                    testimonialList.add(model.getTestImonialList().get(i));
                }

                TestimonialDataAdapter adapter = new TestimonialDataAdapter(testimonialList, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.testimonialRecyclerView.setLayoutManager(mLayoutManager);
                holder.testimonialRecyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.testimonialRecyclerView.setAdapter(adapter);
            }

            if (model.getGallaryDetailList() != null) {
                holder.galleryRecyclerView.setVisibility(View.VISIBLE);

                ArrayList<GallaryDetailList> galleryList = new ArrayList<>();
                for (int i = 0; i < model.getGallaryDetailList().size(); i++) {
                    galleryList.add(model.getGallaryDetailList().get(i));
                }

                RvGalleryAdapter adapter = new RvGalleryAdapter(galleryList, context);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                //holder.galleryRecyclerView.setLayoutManager(mLayoutManager);
                holder.galleryRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                holder.galleryRecyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.galleryRecyclerView.setAdapter(adapter);

            }

            if (model.getTeamList() != null) {
                holder.teamRecyclerView.setVisibility(View.VISIBLE);

                ArrayList<TeamList> teamList = new ArrayList<>();
                for (int i = 0; i < model.getTeamList().size(); i++) {
                    teamList.add(model.getTeamList().get(i));
                }

                //Log.e("TEAM LIST","-------------------- "+teamList);

                RvTeamListAdapter adapter = new RvTeamListAdapter(teamList, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.teamRecyclerView.setLayoutManager(mLayoutManager);
                holder.teamRecyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.teamRecyclerView.setAdapter(adapter);

            }


            if (model.getSuccessList() != null) {
                holder.successStoryRecyclerView.setVisibility(View.VISIBLE);

                ArrayList<SuccessList> successLists = new ArrayList<>();
                for (int i = 0; i < model.getSuccessList().size(); i++) {
                    successLists.add(model.getSuccessList().get(i));
                }

                //Log.e("SUCCESS STORY LIST","-------------------- "+successLists);

                RvSuccessStoryListAdapter adapter = new RvSuccessStoryListAdapter(successLists, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.successStoryRecyclerView.setLayoutManager(mLayoutManager);
                holder.successStoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.successStoryRecyclerView.setAdapter(adapter);

            }


        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
