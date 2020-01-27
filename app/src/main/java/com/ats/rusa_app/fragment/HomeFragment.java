package com.ats.rusa_app.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.ConnectionCheckActivity;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.adapter.CompSliderAdapter;
import com.ats.rusa_app.adapter.NewsAndNotificationAdapter;
import com.ats.rusa_app.adapter.NewsFeedAdapter;
import com.ats.rusa_app.adapter.TestimonialAdapter;
import com.ats.rusa_app.adapter.UpcominEventAdapter;
import com.ats.rusa_app.adapter.YoutubeVideosAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.CompanyModel;
import com.ats.rusa_app.model.Detail;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.MenuModel;
import com.ats.rusa_app.model.NewDetail;
import com.ats.rusa_app.model.PhotoList;
import com.ats.rusa_app.model.TestImonialList;
import com.ats.rusa_app.model.Testimonials;
import com.ats.rusa_app.model.UpcomingEvent;
import com.ats.rusa_app.model.VideoList;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.ats.rusa_app.util.TouchyWebView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private SliderLayout sliderLayout;
    public RelativeLayout relativeLayout_baner;
    public ImageView iv_baner;
    private TouchyWebView wvTwitter, wvFb;
    private Button btn_click;
    public TextView tv_banerName;

    private FloatingActionButton fabTwitter, fabFb;

    private RecyclerView testomonial_recyclerView, new_recyclerView, comp_recyclerView, video_recyclerView, rvNewsAndNotify;
    private LinearLayoutManager linearLayoutManager;

    final ArrayList<Testimonials> list = new ArrayList<>();
    ArrayList<NewDetail> newsList = new ArrayList<>();
    ArrayList<TestImonialList> testimonialList = new ArrayList<>();
    ArrayList<PhotoList> galleryList = new ArrayList<>();
    ArrayList<CompanyModel> companyList = new ArrayList<>();
    ArrayList<Detail> homeList = new ArrayList<>();
    // ArrayList<Detail> homeVideoList = new ArrayList<>();

    ArrayList<UpcomingEvent> upcomingEventList = new ArrayList<>();

    int languageId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout = view.findViewById(R.id.slider);
        testomonial_recyclerView = view.findViewById(R.id.testominal_recyclerView);
        new_recyclerView = view.findViewById(R.id.news_recyclerView);
        comp_recyclerView = view.findViewById(R.id.comp_recyclerView);
        rvNewsAndNotify = view.findViewById(R.id.rvNewsAndNotify);
        relativeLayout_baner = view.findViewById(R.id.relativeLayout_baner);
        iv_baner = view.findViewById(R.id.iv_baner);
        wvTwitter = view.findViewById(R.id.wvTwitter);
        wvFb = view.findViewById(R.id.wvFb);
        video_recyclerView = view.findViewById(R.id.videos_recyclerView);
        btn_click = view.findViewById(R.id.btn_click);
        tv_banerName = view.findViewById(R.id.baner_name);

        fabTwitter = view.findViewById(R.id.fabTwitter);
        fabFb = view.findViewById(R.id.fabFb);
        fabTwitter.setOnClickListener(this);
        fabFb.setOnClickListener(this);
        btn_click.setOnClickListener(this);

        String langId = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }


        if (!Constants.isOnline(getContext())) {



            try {

                //------------------NEWS----------------------------
                String newsStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_HOME_NEWS);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<NewDetail>>() {
                }.getType();
                newsList.clear();
                newsList = gson.fromJson(newsStr, type);

                if (newsList != null) {
                    NewsFeedAdapter adapter = new NewsFeedAdapter(newsList, getContext());
                    new_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    new_recyclerView.setAdapter(adapter);
                }

                //----------------COMPANY-------------------------------
                String compStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_HOME_COMPANY);
                Gson cGson = new Gson();
                Type cType = new TypeToken<ArrayList<CompanyModel>>() {
                }.getType();
                companyList.clear();
                companyList = cGson.fromJson(compStr, cType);
                //Log.e("HOME------", "---------OFFLINE------------------- COMP - " + companyList);

                if (companyList != null) {
                    CompSliderAdapter cAdapter = new CompSliderAdapter(companyList, getContext());
                    comp_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    comp_recyclerView.setAdapter(cAdapter);
                }

                //-------------------HOME-----------------------------------
                String homeStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_HOME_DATA);
                Gson dGson = new Gson();
                Detail detail = dGson.fromJson(homeStr, Detail.class);

                ArrayList<GallaryDetailList> videoList = new ArrayList<>();
                try {
                    for (int i = 0; i < detail.getVideoList().size(); i++) {

                        VideoList video = detail.getVideoList().get(i);
                        String code = video.getFileName();

                        GallaryDetailList videoModel = new GallaryDetailList(1, 1, 1, 1, "1", "video 1", "aa", "", "", 1, "", "", 1, 1, 1, 1, 1, 1, "" + code, "");
                        videoList.add(videoModel);
                    }
                } catch (Exception e) {
                  //  e.printStackTrace();
                }

                if (videoList != null) {
                    YoutubeVideosAdapter yAdapter = new YoutubeVideosAdapter(videoList, getContext());
                    video_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    video_recyclerView.setAdapter(yAdapter);
                }

                //-----------------Testimonials---------------------------
                testimonialList.clear();

                if (detail.getTestimonialList().size() > 0) {
                    for (int i = 0; i < detail.getTestimonialList().size(); i++) {
                        testimonialList.add(detail.getTestimonialList().get(i));
                    }
                }

                if (testimonialList != null) {
                    TestimonialAdapter tAdapter = new TestimonialAdapter(testimonialList, getContext());
                    testomonial_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    testomonial_recyclerView.setAdapter(tAdapter);
                }


                //--------------------------GALLERY----------------------------------
                galleryList.clear();
                if (detail.getPhotoList().size() > 0) {
                    for (int i = 0; i < detail.getPhotoList().size(); i++) {
                        galleryList.add(detail.getPhotoList().get(i));
                    }
                }
                imageSlider(galleryList);


            } catch (Exception e) {
               // Log.e("Offline--", "------------------------- ERROR - " + e.getMessage());
               // e.printStackTrace();
            }

            //startActivity(new Intent(MainActivity.this, ConnectionCheckActivity.class));
            //finish();
        }


        getNewFeed(languageId);
        getCompSlider();
        // initYoutubeVideo("gG2npfpaqsY");
        getAllHomeData(languageId);
        getUpcomingEvent(languageId);
        //getVideoGallery();

        // video_recyclerView.setAdapter(adapter);

        String widgetInfo = "<a class=\"twitter-timeline\" href=\"http://twitter.com/RUSA_MH\"</a> " +
                "<div id=\"fb-root\"></div>" +
                "<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>";


        wvTwitter.setVerticalScrollBarEnabled(false);
        wvTwitter.setHorizontalScrollBarEnabled(false);
        wvTwitter.getSettings().setDomStorageEnabled(true);
        wvTwitter.getSettings().setJavaScriptEnabled(true);

        wvTwitter.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        wvTwitter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        wvFb.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        wvFb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        wvTwitter.loadDataWithBaseURL("http://twitter.com", widgetInfo, "text/html", "UTF-8", null);

        String widgetInfo1 = "<div class=\"fb-page\" data-href=\"https://www.facebook.com/RUSAMaharashtra\" data-tabs=\"timeline\" data-small-header=\"false\" data-adapt-container-width=\"true\" data-hide-cover=\"false\" data-show-facepile=\"true\"><blockquote cite=\"https://www.facebook.com/RUSAMaharashtra\" class=\"fb-xfbml-parse-ignore\"><a href=\"https://www.facebook.com/RUSAMaharashtra\">Ministry of Human Resource Development, Government of India</a></blockquote></div>" + "<script async defer crossorigin=\"anonymous\" src=\"https://connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v3.2\"></script>";

        wvFb.setVerticalScrollBarEnabled(false);
        wvFb.setHorizontalScrollBarEnabled(false);
        wvFb.getSettings().setDomStorageEnabled(true);
        wvFb.getSettings().setJavaScriptEnabled(true);
        wvFb.loadDataWithBaseURL("https://www.facebook.com", widgetInfo1, "text/html", "UTF-8", null);
        return view;
    }

    private void getAllHomeData(int langId) {


        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Detail> listCall = Constants.myInterface.getAllHomeData(langId, authHeader);
            listCall.enqueue(new Callback<Detail>() {
                @Override
                public void onResponse(Call<Detail> call, Response<Detail> response) {
                    try {
                        if (response.body() != null) {

                            //Log.e("HOME DATA : ", " - " + response.body());
                            homeList.clear();
                            Detail detail = response.body();

                            Gson dGson = new Gson();
                            String dJson = dGson.toJson(detail);
                            CustomSharedPreference.putString(getActivity(), CustomSharedPreference.KEY_HOME_DATA, dJson);


                            ArrayList<GallaryDetailList> videoList = new ArrayList<>();

                            try {
                                for (int i = 0; i < detail.getVideoList().size(); i++) {

                                    VideoList video = detail.getVideoList().get(i);

                                   /* String iframeStr = video.getFileName();
                                    //Log.e("STRING ", "---------- " + iframeStr);

                                    int index = (iframeStr.lastIndexOf("src"));
                                    //Log.e("FIRST INDEX : ", "-------------------- " + index);
                                    int firstIndex = (iframeStr.indexOf('"', index)) + 1;
                                    //Log.e("LAST INDEX : ", "-------------------- " + firstIndex);
                                    int lastIndex = iframeStr.indexOf('"', firstIndex);


                                    //Log.e("VIDEO URL : ", "------------------------ " + iframeStr.substring(firstIndex, lastIndex));

                                    String code = extractYTId(iframeStr.substring(firstIndex, lastIndex)).replace("\\", "");
                                   // String code = video.getFileName();

                                    //Log.e("VIDEO CODE : ", "------------------------ " + code);

                                    GallaryDetailList videoModel = new GallaryDetailList(1, 1, 1, 1, "1", "video 1", "aa", "", "", 1, "", "", 1, 1, 1, 1, 1, 1, "" + code, "");
                                    videoList.add(videoModel);*/

                                    String code = video.getFileName();

                                   // Log.e("VIDEO CODE : ", "------------------------ " + code);

                                    GallaryDetailList videoModel = new GallaryDetailList(1, 1, 1, 1, "1", "video 1", "aa", "", "", 1, "", "", 1, 1, 1, 1, 1, 1, "" + code, "");
                                    videoList.add(videoModel);
                                }
                            } catch (Exception e) {
                               // e.printStackTrace();
                            }


                            YoutubeVideosAdapter adapter = new YoutubeVideosAdapter(videoList, getContext());
                            video_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            video_recyclerView.setAdapter(adapter);

                            //Testimonials---------------------------

                            testimonialList.clear();

                            if (detail.getTestimonialList().size() > 0) {
                                for (int i = 0; i < detail.getTestimonialList().size(); i++) {
                                    testimonialList.add(detail.getTestimonialList().get(i));
                                }
                            }

                            //Log.e("Testimonial List: ", " -**************************************** " + detail.getTestimonialList());
                            TestimonialAdapter tAdapter = new TestimonialAdapter(testimonialList, getContext());
                            testomonial_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            testomonial_recyclerView.setAdapter(tAdapter);

                            galleryList.clear();

                            if (detail.getPhotoList().size() > 0) {
                                for (int i = 0; i < detail.getPhotoList().size(); i++) {
                                    galleryList.add(detail.getPhotoList().get(i));
                                }
                            }
                            //Log.e("Gallery List: ", " -**************************************** " + detail.getPhotoList());
                            imageSlider(galleryList);


                            if (detail.getBaner() != null) {
                                String imageUri = Constants.BANENR_URL + detail.getBaner().getSliderImage();
                                //Log.e("URI11111", "-----------" + imageUri);
                                try {
                                    Picasso.with(getContext()).load(imageUri).placeholder(R.drawable.slider).into(iv_baner);
                                } catch (Exception e) {
                                }
                                tv_banerName.setText(detail.getBaner().getSliderName());
                            }

                            if (detail.getNewsList().size() > 0) {
                                for (int i = 0; i < detail.getNewsList().size(); i++) {
                                    newsList.add(detail.getNewsList().get(i));
                                }
                            }

                            //Log.e("News List: ", " -**************************************** " + detail.getNewsList());
                            NewsFeedAdapter nAdapter = new NewsFeedAdapter(newsList, getContext());
                            new_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            new_recyclerView.setAdapter(nAdapter);

                            if (detail.getCompanyList().size() > 0) {
                                for (int i = 0; i < detail.getCompanyList().size(); i++) {
                                    companyList.add(detail.getCompanyList().get(i));
                                }
                            }
                            //Log.e("Company List: ", " -**************************************** " + detail.getCompanyList());
                            CompSliderAdapter cAdapter = new CompSliderAdapter(companyList, getContext());
                            comp_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            comp_recyclerView.setAdapter(cAdapter);

                            // homeList = response.body();
                            //Log.e("HOME LIST", "-------------------" + detail);
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Detail> call, Throwable t) {
                    commonDialog.dismiss();
                   // Log.e("onFailure1 : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }


    private void getCompSlider() {


        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<CompanyModel>> listCall = Constants.myInterface.getCompSlider(authHeader);
            listCall.enqueue(new Callback<ArrayList<CompanyModel>>() {
                @Override
                public void onResponse(Call<ArrayList<CompanyModel>> call, Response<ArrayList<CompanyModel>> response) {
                    try {
                        if (response.body() != null) {

                            //Log.e("Company responce : ", " - " + response.body());
                            companyList.clear();
                            companyList = response.body();

                            Gson cGson = new Gson();
                            String cJson = cGson.toJson(response.body());
                            CustomSharedPreference.putString(getActivity(), CustomSharedPreference.KEY_HOME_COMPANY, cJson);


                            CompSliderAdapter adapter = new CompSliderAdapter(companyList, getContext());
                            comp_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            comp_recyclerView.setAdapter(adapter);
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                           // Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                       // Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<CompanyModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure1 : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


    private void getNewFeed(int langId) {


        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<NewDetail>> listCall = Constants.myInterface.getNewsData(langId, authHeader);
            listCall.enqueue(new Callback<ArrayList<NewDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<NewDetail>> call, Response<ArrayList<NewDetail>> response) {
                    try {
                        if (response.body() != null) {

                            //Log.e("NEWS DATA : ", " - " + response.body());

                            Gson mGson = new Gson();
                            String mJson = mGson.toJson(response.body());
                            CustomSharedPreference.putString(getActivity(), CustomSharedPreference.KEY_HOME_NEWS, mJson);


                            newsList.clear();
                            newsList = response.body();
                            // NewDetail newDetail=response.body();
                            //newsList.add(newDetail);


                            NewsFeedAdapter adapter = new NewsFeedAdapter(newsList, getContext());
                            new_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            new_recyclerView.setAdapter(adapter);


                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                          //  Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                      //  Log.e("Exception : ", "-----------" + e.getMessage());
                      //  e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NewDetail>> call, Throwable t) {
                    commonDialog.dismiss();
                  //  Log.e("onFailure1 : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void initYoutubeVideo(final String url) {
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize("xxxxxxxxxxx", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer = youTubePlayer;
                    //youTubePlayer.setFullscreen(true);
                    youTubePlayer.loadVideo(url);
                    youTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {


            }
        });


    }

    private void imageSlider(ArrayList<PhotoList> galleryList) {

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("img", Constants.GALLERY_URL + "slider.jpg");
        for (int i = 0; i < galleryList.size(); i++) {

            String image = Constants.GALLERY_URL + galleryList.get(i).getFileName();
            String title = galleryList.get(i).getTitle();
            //String title = gallaryDetailLists.get(i).getTitle();
            url_maps.put(title, image);
            //Log.e("Gallery", "----------" + url_maps);
        }


        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //.setOnSliderClickListener(getActivity());

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.setCurrentPosition(1, true);


    }


    private void getUpcomingEvent(final int languageId) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<UpcomingEvent>> listCall = Constants.myInterface.getHomeNewsAndNotificationNew(languageId, authHeader);
            listCall.enqueue(new Callback<ArrayList<UpcomingEvent>>() {
                @Override
                public void onResponse(Call<ArrayList<UpcomingEvent>> call, Response<ArrayList<UpcomingEvent>> response) {
                    try {
                        if (response.body() != null) {

                            //Log.e("UPCOMING EVENT LIST : ", " - " + response.body());
                            upcomingEventList.clear();
                            upcomingEventList = response.body();


                            NewsAndNotificationAdapter adapter = new NewsAndNotificationAdapter(upcomingEventList, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            rvNewsAndNotify.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            rvNewsAndNotify.setAdapter(adapter);

                            if (upcomingEventList.size() < 10) {
                                getOldEvent(languageId);
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                          //  Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                       // Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<UpcomingEvent>> call, Throwable t) {
                    commonDialog.dismiss();
                   // Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getOldEvent(int languageId) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<UpcomingEvent>> listCall = Constants.myInterface.getHomeNewsAndNotificationOld(languageId, authHeader);
            listCall.enqueue(new Callback<ArrayList<UpcomingEvent>>() {
                @Override
                public void onResponse(Call<ArrayList<UpcomingEvent>> call, Response<ArrayList<UpcomingEvent>> response) {
                    try {
                        if (response.body() != null) {

                            //Log.e("UPCOMING EVENT LIST : ", " - " + response.body());
                            //upcomingEventList.clear();
                            //upcomingEventList = response.body();

                            ArrayList<UpcomingEvent> tempEventList = new ArrayList<>();
                            tempEventList = response.body();

                            for (int i = 0; i < tempEventList.size(); i++) {
                                if (upcomingEventList.size() < 10) {
                                    upcomingEventList.add(tempEventList.get(i));
                                }
                            }


                            NewsAndNotificationAdapter adapter = new NewsAndNotificationAdapter(upcomingEventList, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            rvNewsAndNotify.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            rvNewsAndNotify.setAdapter(adapter);


                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                           // //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                      //  Log.e("Exception : ", "-----------" + e.getMessage());
                      //  e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<UpcomingEvent>> call, Throwable t) {
                    commonDialog.dismiss();
                  //  Log.e("onFailure : ", "-----------" + t.getMessage());
                  //  t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabTwitter) {
            Intent intent = null;
            try {
                // get the Twitter app if possible
                getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=HRDMinistry"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Twitter app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/HRDMinistry"));
            }
            this.startActivity(intent);

        } else if (v.getId() == R.id.fabFb) {
            Uri uri = Uri.parse("https://www.facebook.com/HRDMinistry/?ref=bookmarks");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.facebook.katana");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/HRDMinistry/?ref=bookmarks")));
            }
        } else if (v.getId() == R.id.btn_click) {
            Fragment adf = new NewContentFragment();
            Bundle args = new Bundle();
            args.putString("slugName", "about-rusa9");
            adf.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
        }
    }


}
