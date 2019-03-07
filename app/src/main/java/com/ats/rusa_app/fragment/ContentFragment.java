package com.ats.rusa_app.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.activity.WebViewActivity;
import com.ats.rusa_app.adapter.DetailNewsAdapter;
import com.ats.rusa_app.adapter.DownloadAdapter;
import com.ats.rusa_app.adapter.FaqAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.DetailNewsList;
import com.ats.rusa_app.model.DocumentUploadList;
import com.ats.rusa_app.model.FaqContentList;
import com.ats.rusa_app.model.MenuGroup;
import com.ats.rusa_app.model.MenuModel;
import com.ats.rusa_app.model.PageData;
import com.ats.rusa_app.util.CommonDialog;

import org.sufficientlysecure.htmltextview.ClickableTableSpan;
import org.sufficientlysecure.htmltextview.DrawTableLinkSpan;
import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class ContentFragment extends Fragment implements Html.ImageGetter {

    private TextView tvHeading, tvHtml;
    private RecyclerView recyclerView;
    private LinearLayout llWebview;
    private WebView webView;

    private HtmlTextView tvHtmlTxt;

    String slugName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        tvHeading = view.findViewById(R.id.tvHeading);
        recyclerView = view.findViewById(R.id.recyclerView);
        llWebview = view.findViewById(R.id.llWebview);


        webView = view.findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        tvHtml = view.findViewById(R.id.tvHtml);
        tvHtmlTxt = view.findViewById(R.id.tvHtmlTxt);


        //webView.setInitialScale(250);


        try {
            slugName = getArguments().getString("slugName");
            getPageData(slugName);
        } catch (Exception e) {
            Log.e("ContentFrag : ", " ----------- " + e.getMessage());
            e.printStackTrace();
        }

        return view;
    }

    public void getPageData(final String slugName) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PageData> listCall = Constants.myInterface.getPageData(slugName, 1);
            listCall.enqueue(new Callback<PageData>() {
                @Override
                public void onResponse(Call<PageData> call, Response<PageData> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PAGE DATA : ", " - " + response.body());

                            PageData model = response.body();

                            if (model.getPageId() > 0) {

                                tvHeading.setText("" + model.getPageName());
                                getActivity().setTitle("" + model.getPageName());

                                if (model.getCmsContentList() != null) {
                                    Toast.makeText(getContext(), "CMS", Toast.LENGTH_SHORT).show();

                                    llWebview.setVisibility(View.VISIBLE);

                                    final String mimeType = "text/html";
                                    final String encoding = "UTF-8";

                                    String htmlText = "<html>" +
                                            "<head>" +
                                            "<meta name='viewport' content='width=640, initial-scale=1'/>" +
                                            "</head>" +
                                            "<body style=\"margin: 0px;\">";

                                    if (model.getCmsContentList().size() > 0) {
                                        for (int i = 0; i < model.getCmsContentList().size(); i++) {
                                            htmlText = htmlText + "\n\n" + model.getCmsContentList().get(i).getPageDesc();
                                        }
                                    }

                                    htmlText = htmlText +
                                            "<p><img  src=\"http://tomcat.aaryatechindia.in:6435/media/other/2019-03-04_06:00:16_process follow.jpg\" style=\"width: 493px; height: 407px;\" /></p>" +
                                            "</body>" +
                                            "</html>";


                                    //webView.loadDataWithBaseURL("", htmlText, mimeType, encoding, "");

                                    webView.loadData(htmlText, "text/html", "utf-8");

                                    Spanned spanned = Html.fromHtml(htmlText, ContentFragment.this, null);
                                    tvHtml.setText(spanned);
                                    //tvHtml.setText(Html.fromHtml(htmlText));


                                    tvHtmlTxt.setClickableTableSpan(new ClickableTableSpanImpl());
                                    DrawTableLinkSpan drawTableLinkSpan = new DrawTableLinkSpan();
                                    drawTableLinkSpan.setTableLinkText("[tap for table]");
                                    tvHtmlTxt.setDrawTableLinkSpan(drawTableLinkSpan);

                                    // Best to use indentation that matches screen density.
                                    DisplayMetrics metrics = new DisplayMetrics();
                                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                                    tvHtmlTxt.setListIndentPx(metrics.density * 10);

                                    tvHtmlTxt.setHtml(htmlText, new HtmlHttpImageGetter(tvHtmlTxt));


                                } else if (model.getFaqContentList() != null) {
                                    Toast.makeText(getContext(), "FAQS", Toast.LENGTH_SHORT).show();

                                    llWebview.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);


                                    if (model.getFaqContentList().size() > 0) {

                                        ArrayList<FaqContentList> faqList = new ArrayList<>();
                                        for (int i = 0; i < model.getFaqContentList().size(); i++) {
                                            faqList.add(model.getFaqContentList().get(i));
                                        }

                                        FaqAdapter faqAdapter = new FaqAdapter(faqList, getContext());
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                        recyclerView.setLayoutManager(mLayoutManager);
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView.setAdapter(faqAdapter);
                                    }


                                } else if (model.getDocumentUploadList() != null) {
                                    Toast.makeText(getContext(), "DOC", Toast.LENGTH_SHORT).show();

                                    llWebview.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);


                                    if (model.getDocumentUploadList().size() > 0) {

                                        ArrayList<DocumentUploadList> docList = new ArrayList<>();
                                        for (int i = 0; i < model.getDocumentUploadList().size(); i++) {
                                            docList.add(model.getDocumentUploadList().get(i));
                                        }

                                        DownloadAdapter adapter = new DownloadAdapter(docList, getContext());
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                        recyclerView.setLayoutManager(mLayoutManager);
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView.setAdapter(adapter);
                                    }

                                } else if (model.getTestImonialList() != null) {
                                    Toast.makeText(getContext(), "TESTIMONIALS", Toast.LENGTH_SHORT).show();
                                } else if (model.getGallaryDetailList() != null) {
                                    Toast.makeText(getContext(), "GALLERY", Toast.LENGTH_SHORT).show();
                                } else if (model.getDetailNewsList() != null) {
                                    Toast.makeText(getContext(), "NEWS", Toast.LENGTH_SHORT).show();

                                    if (model.getDetailNewsList().size() > 0) {

                                        ArrayList<DetailNewsList> newsList = new ArrayList<>();
                                        for (int i = 0; i < model.getDetailNewsList().size(); i++) {
                                            newsList.add(model.getDetailNewsList().get(i));
                                        }

                                        DetailNewsAdapter adapter = new DetailNewsAdapter(newsList, getContext());
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                        recyclerView.setLayoutManager(mLayoutManager);
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView.setAdapter(adapter);
                                    }

                                }
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<PageData> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.drawable.ic_right_arrow);
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
                CharSequence t = tvHtml.getText();
                tvHtml.setText(t);
            }
        }
    }


    class ClickableTableSpanImpl extends ClickableTableSpan {
        @Override
        public ClickableTableSpan newInstance() {
            return new ClickableTableSpanImpl();
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("EXTRA_TABLE_HTML", getTableHtml());
            startActivity(intent);
        }
    }
}
