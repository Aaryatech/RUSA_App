package com.ats.rusa_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.fragment.ContentFragment;
import com.ats.rusa_app.model.CmsContentList;
import com.ats.rusa_app.model.DetailNewsList;
import com.ats.rusa_app.util.ClickableTableSpanImpl;
import com.ats.rusa_app.util.RvWebView;
import com.ats.rusa_app.util.TouchyWebView;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.DrawTableLinkSpan;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.constraint.Constraints.TAG;

public class CmsDataAdapter extends RecyclerView.Adapter<CmsDataAdapter.MyViewHolder> implements Html.ImageGetter {

    private ArrayList<CmsContentList> cmsList;
    private Context context;

    public CmsDataAdapter(ArrayList<CmsContentList> cmsList, Context context) {
        this.cmsList = cmsList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llWebview, llHtml;
        private WebView webView;
        private HtmlTextView tvHtmlTxt;
        private TextView tvHtml, tvPdf;
        private RvWebView rvWebview;
        private ImageView ivImg;
        private TouchyWebView tWebview;


        public MyViewHolder(View view) {
            super(view);
            llWebview = view.findViewById(R.id.llWebview);
            llHtml = view.findViewById(R.id.llHtml);
            webView = view.findViewById(R.id.webview);
            tvHtmlTxt = view.findViewById(R.id.tvHtmlTxt);
            tvHtml = view.findViewById(R.id.tvHtml);
            rvWebview = view.findViewById(R.id.rvWebview);
            ivImg = view.findViewById(R.id.ivImg);
            tWebview = view.findViewById(R.id.tWebview);
            tvPdf = view.findViewById(R.id.tvPdf);
            tWebview = view.findViewById(R.id.tWebview);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cms_data, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CmsContentList model = cmsList.get(position);
        Log.e("CMS", "------------------------ " + model);

        holder.llHtml.setVisibility(View.VISIBLE);

        holder.tvPdf.setPaintFlags(holder.tvPdf.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        WebSettings webSettings = holder.tWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // holder.tWebview.setWebChromeClient(new WebChromeClient());
        // holder.tWebview.setWebViewClient(new WebViewClient());
        // webSettings.setUseWideViewPort(true);
        // webSettings.setLoadWithOverviewMode(true);

        if (model.getFeaturedImage() != null) {
            if (model.getFeaturedImage().equals("")) {
                holder.ivImg.setVisibility(View.GONE);
            } else {
                try {
                    holder.ivImg.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(Constants.GALLERY_URL + model.getFeaturedImage()).placeholder(R.drawable.img_placeholder).into(holder.ivImg);
                } catch (Exception e) {
                }
            }
        }

        if (model.getDownloadPdf() != null) {
            if (model.getDownloadPdf().equals("")) {
                holder.tvPdf.setVisibility(View.GONE);
            } else {
                holder.tvPdf.setVisibility(View.VISIBLE);
                holder.tvPdf.setText("" + model.getDownloadPdf());
            }
        }

        holder.tvPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + Constants.PDF_URL + model.getDownloadPdf()));
                context.startActivity(browserIntent);
            }
        });

        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        String htmlText = "<html>" +
                "<head>" +
                "<meta name='viewport' content='width=640, initial-scale=1'/>" +
                "</head>" +
                "<body style=\"margin: 0px;\">";

        htmlText = htmlText + "\n\n" + model.getPageDesc();

        htmlText = htmlText +
                "</body>" +
                "</html>";


        //webView.loadDataWithBaseURL("", htmlText, mimeType, encoding, "");


        //htmlText.replaceAll("<iframe\\s+.*?\\s+src=(\".*?\").*?<\\/iframe>", "<a href=$1>CLICK TO WATCH</a>");

        holder.webView.loadData(htmlText, "text/html", "utf-8");
        holder.rvWebview.loadData(htmlText, "text/html", "utf-8");
        holder.tWebview.loadData(htmlText, "text/html", "utf-8");

        Spanned spanned = Html.fromHtml(htmlText, this, null);
        holder.tvHtml.setText(spanned);
        //tvHtml.setText(Html.fromHtml(htmlText));


        holder.tvHtmlTxt.setClickableTableSpan(new ClickableTableSpanImpl(context));
        DrawTableLinkSpan drawTableLinkSpan = new DrawTableLinkSpan();
        drawTableLinkSpan.setTableLinkText("View Table");
        holder.tvHtmlTxt.setDrawTableLinkSpan(drawTableLinkSpan);


        // Best to use indentation that matches screen density.
        DisplayMetrics metrics = new DisplayMetrics();

        MainActivity activity = (MainActivity) context;
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        holder.tvHtmlTxt.setListIndentPx(metrics.density * 10);

        try {
            holder.tvHtmlTxt.setHtml(htmlText, new HtmlHttpImageGetter(holder.tvHtmlTxt));
        } catch (Exception e) {
            Log.e("Hello", "-------------Hii");
            holder.llHtml.setVisibility(View.GONE);
            holder.llWebview.setVisibility(View.VISIBLE);

        }

        if (htmlText.contains("iframe")) {

            holder.llHtml.setVisibility(View.GONE);
            holder.llWebview.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return cmsList.size();
    }


    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = context.getResources().getDrawable(R.drawable.ic_right_arrow);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                // CharSequence t = tvHtml.getText();
                // tvHtml.setText(t);
            }
        }
    }

}
