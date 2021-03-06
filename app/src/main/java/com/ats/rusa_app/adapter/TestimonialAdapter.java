package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.TestimonialWebviewActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Detail;
import com.ats.rusa_app.model.TestImonialList;
import com.ats.rusa_app.util.ConnectivityDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class TestimonialAdapter extends RecyclerView.Adapter<TestimonialAdapter.MyViewHolder> {
    private ArrayList<TestImonialList> TestimonalList;
    private Context context;

    public TestimonialAdapter(ArrayList<TestImonialList> testimonalList, Context context) {
        TestimonalList = testimonalList;
        this.context = context;
    }

    @NonNull
    @Override
    public TestimonialAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.testimonial_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TestimonialAdapter.MyViewHolder myViewHolder, int i) {
        final TestImonialList model = TestimonalList.get(i);
        //Log.e("Model Testimonial", "-------****************************---------" + model);
        String imageUri = Constants.GALLERY_URL + model.getImageName();
        try {
            Picasso.with(context).load(imageUri).placeholder(context.getResources().getDrawable(R.drawable.profile_img)).into(myViewHolder.imageView);
        } catch (Exception e) {
        }
        myViewHolder.tv_Title.setText(model.getFromName());

        if (model.getExInt1()==1){

            myViewHolder.tv_Disc.setHtml(model.getMessage(), new HtmlHttpImageGetter(myViewHolder.tv_Disc));

        }else if (model.getExInt1()==2){

            myViewHolder.tv_Disc.setText("See more");

        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Gson gson = new Gson();
                    String str = gson.toJson(model);

                    if (!Constants.isOnline(context)) {

                        new ConnectivityDialog(context).show();

                    } else {
                        Intent intent = new Intent(context, TestimonialWebviewActivity.class);
                        intent.putExtra("model", str);
                        context.startActivity(intent);
                    }
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + model.getNewsSourceUrlName()));
//                    context.startActivity(browserIntent);
                } catch (Exception e) {
                   // Log.e("Exception : ", "-----------" + e.getMessage());
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return TestimonalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_Title;
        public HtmlTextView tv_Disc;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_testominal);
            tv_Title = (TextView) itemView.findViewById(R.id.tv_Title);
            tv_Disc = (HtmlTextView) itemView.findViewById(R.id.tv_Discription);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
