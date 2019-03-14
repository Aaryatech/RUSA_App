package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.adapter.CompSliderAdapter;
import com.ats.rusa_app.adapter.NewsFeedAdapter;
import com.ats.rusa_app.adapter.TestimonialAdapter;
import com.ats.rusa_app.adapter.YoutubeVideosAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Baner;
import com.ats.rusa_app.model.CompanyModel;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.NewDetail;
import com.ats.rusa_app.model.Testimonials;
import com.ats.rusa_app.model.Testomonial;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.ats.rusa_app.util.TouchyWebView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private SliderLayout sliderLayout;
    public RelativeLayout relativeLayout_baner;
    public ImageView iv_baner;
    private TouchyWebView wvTwitter, wvFb;

    private FloatingActionButton fabTwitter, fabFb;

    private RecyclerView testomonial_recyclerView, new_recyclerView, comp_recyclerView,video_recyclerView;
    private LinearLayoutManager linearLayoutManager;

    final ArrayList<Testimonials> list = new ArrayList<>();
    ArrayList<NewDetail> newsList = new ArrayList<>();
    ArrayList<Testomonial> testimonialList = new ArrayList<>();
    ArrayList<GallaryDetailList> galleryList = new ArrayList<>();
    ArrayList<CompanyModel> companyList = new ArrayList<>();
    ArrayList<Baner> banerList = new ArrayList<>();

    int languageId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        sliderLayout = view.findViewById(R.id.slider);
        testomonial_recyclerView = view.findViewById(R.id.testominal_recyclerView);
        new_recyclerView = view.findViewById(R.id.news_recyclerView);
        comp_recyclerView = view.findViewById(R.id.comp_recyclerView);
        relativeLayout_baner = view.findViewById(R.id.relativeLayout_baner);
        iv_baner = view.findViewById(R.id.iv_baner);
        wvTwitter = view.findViewById(R.id.wvTwitter);
        wvFb = view.findViewById(R.id.wvFb);
        video_recyclerView = view.findViewById(R.id.videos_recyclerView);

        fabTwitter = view.findViewById(R.id.fabTwitter);
        fabFb = view.findViewById(R.id.fabFb);

        String langId = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }

        getImageGallery();
        getNewFeed(languageId);
        getCompSlider();
        getBaner();
        initYoutubeVideo("gG2npfpaqsY");
        getTestimonial();
        getVideoGallery();


        String widgetInfo = "<a class=\"twitter-timeline\" href=\"http://twitter.com/HRDMinistry\"</a> " +
                "<div id=\"fb-root\"></div>" +
                "<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>";


        wvTwitter.setVerticalScrollBarEnabled(false);
        wvTwitter.setHorizontalScrollBarEnabled(false);
        wvTwitter.getSettings().setDomStorageEnabled(true);
        wvTwitter.getSettings().setJavaScriptEnabled(true);
        wvTwitter.loadDataWithBaseURL("http://twitter.com", widgetInfo, "text/html", "UTF-8", null);

        String widgetInfo1 = "<div class=\"fb-page\" data-href=\"https://www.facebook.com/HRDMinistry\" data-tabs=\"timeline\" data-small-header=\"false\" data-adapt-container-width=\"true\" data-hide-cover=\"false\" data-show-facepile=\"true\"><blockquote cite=\"https://www.facebook.com/HRDMinistry\" class=\"fb-xfbml-parse-ignore\"><a href=\"https://www.facebook.com/HRDMinistry\">Ministry of Human Resource Development, Government of India</a></blockquote></div>" + "<script async defer crossorigin=\"anonymous\" src=\"https://connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v3.2\"></script>";

        wvFb.setVerticalScrollBarEnabled(false);
        wvFb.setHorizontalScrollBarEnabled(false);
        wvFb.getSettings().setDomStorageEnabled(true);
        wvFb.getSettings().setJavaScriptEnabled(true);
        wvFb.loadDataWithBaseURL("https://www.facebook.com", widgetInfo1, "text/html", "UTF-8", null);


        return view;
    }

    private void getTestimonial() {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Testomonial>> listCall = Constants.myInterface.getTestimonial();
            listCall.enqueue(new Callback<ArrayList<Testomonial>>() {
                @Override
                public void onResponse(Call<ArrayList<Testomonial>> call, Response<ArrayList<Testomonial>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("NEWS DATA : ", " - " + response.body());
                            testimonialList.clear();
                            testimonialList = response.body();
                            TestimonialAdapter adapter = new TestimonialAdapter(testimonialList, getContext());
                            testomonial_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            testomonial_recyclerView.setAdapter(adapter);

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
                public void onFailure(Call<ArrayList<Testomonial>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBaner() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Baner> listCall = Constants.myInterface.getBaner();
            listCall.enqueue(new Callback<Baner>() {
                @Override
                public void onResponse(Call<Baner> call, Response<Baner> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Banner responce : ", " - " + response.body());
                            banerList.clear();
                            // banerList=response.body();
                            Baner baner = response.body();
                            String imageUri = Constants.BANENR_URL + baner.getSliderImage();
                            Log.e("URI", "-----------" + imageUri);
                            Picasso.with(getContext()).load(imageUri).into(iv_baner);


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
                public void onFailure(Call<Baner> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getCompSlider() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<CompanyModel>> listCall = Constants.myInterface.getCompSlider();
            listCall.enqueue(new Callback<ArrayList<CompanyModel>>() {
                @Override
                public void onResponse(Call<ArrayList<CompanyModel>> call, Response<ArrayList<CompanyModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Company responce : ", " - " + response.body());
                            companyList.clear();
                            companyList = response.body();
                            CompSliderAdapter adapter = new CompSliderAdapter(companyList, getContext());
                            comp_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            comp_recyclerView.setAdapter(adapter);
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
                public void onFailure(Call<ArrayList<CompanyModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getImageGallery() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<GallaryDetailList>> listCall = Constants.myInterface.getImageGallery();
            listCall.enqueue(new Callback<ArrayList<GallaryDetailList>>() {
                @Override
                public void onResponse(Call<ArrayList<GallaryDetailList>> call, Response<ArrayList<GallaryDetailList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Gallery responce : ", " - " + response.body());
                            galleryList.clear();
                            galleryList = response.body();
                            imageSlider(galleryList);
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
                public void onFailure(Call<ArrayList<GallaryDetailList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getVideoGallery() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<GallaryDetailList>> listCall = Constants.myInterface.getVideoGallery();
            listCall.enqueue(new Callback<ArrayList<GallaryDetailList>>() {
                @Override
                public void onResponse(Call<ArrayList<GallaryDetailList>> call, Response<ArrayList<GallaryDetailList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Video responce : ", " - " + response.body());


                            YoutubeVideosAdapter adapter = new YoutubeVideosAdapter(response.body(), getContext());
                            video_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            video_recyclerView.setAdapter(adapter);


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
                public void onFailure(Call<ArrayList<GallaryDetailList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "-----------" + t.getMessage());
                    t.printStackTrace();
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

            Call<ArrayList<NewDetail>> listCall = Constants.myInterface.getNewsData(langId);
            listCall.enqueue(new Callback<ArrayList<NewDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<NewDetail>> call, Response<ArrayList<NewDetail>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("NEWS DATA : ", " - " + response.body());
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
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NewDetail>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "-----------" + t.getMessage());
                    t.printStackTrace();
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

    private void imageSlider(ArrayList<GallaryDetailList> gallaryDetailLists) {

        HashMap<String, String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < gallaryDetailLists.size(); i++) {
            String image = Constants.GALLERY_URL + gallaryDetailLists.get(i).getFileName();
            String title = gallaryDetailLists.get(i).getTitle();
            url_maps.put(title, image);
            Log.e("Gallery", "----------" + url_maps);

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
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);


//        url_maps.put("Hannibal", "https://images.pexels.com/photos/326055/pexels-photo-326055.jpeg?cs=srgb&dl=beautiful-blur-bright-326055.jpg&fm=jpg");
//        url_maps.put("Big Bang Theory", "https://resize.indiatvnews.com/en/resize/newbucket/715_-/2018/02/propose-1517999844.jpg");
//        url_maps.put("House of Cards", "https://cdn.pixabay.com/photo/2017/01/06/23/21/soap-bubble-1959327_960_720.jpg");
//        url_maps.put("Game of Thrones", "https://media.cntraveller.in/wp-content/uploads/2018/10/GettyImages-990972132-866x487.jpg");


//        for (String name : url_maps.keySet()) {
//            TextSliderView textSliderView = new TextSliderView(getActivity());
//            // initialize a SliderLayout
//            textSliderView
//                    .description(name)
//                    .image(url_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//            //.setOnSliderClickListener(getActivity());
//
//            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra", name);
//
//            sliderLayout.addSlider(textSliderView);
//        }
//        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        sliderLayout.setCustomAnimation(new DescriptionAnimation());
//        sliderLayout.setDuration(4000);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabTwitter) {

        } else if (v.getId() == R.id.fabFb) {

        }
    }

//    public void autoScroll() {
//
//        final int scrollSpeed = 100;   // Scroll Speed in Milliseconds
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            int x = 2;        // Pixels To Move/Scroll
//            boolean flag = true;
//            // Find Scroll Position By Accessing RecyclerView's LinearLayout's Visible Item Position
//            int scrollPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//            int arraySize = list.size();  // Gets RecyclerView's Adapter's Array Size
//
//            @Override
//            public void run() {
//                if (scrollPosition < arraySize) {
//                    if (scrollPosition == arraySize - 1) {
//                        flag = false;
//                    } else if (scrollPosition <= 1) {
//                        flag = true;
//                    }
//                    if (!flag) {
//                        try {
//                            // Delay in Seconds So User Can Completely Read Till Last String
//                            TimeUnit.SECONDS.sleep(1);
//                            recyclerView.scrollToPosition(0);  // Jumps Back Scroll To Start Point
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    // Know The Last Visible Item
//                    scrollPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//
//                    recyclerView.smoothScrollBy(x, 0);
//                    handler.postDelayed(this, scrollSpeed);
//                }
//            }
//        };
//        handler.postDelayed(runnable, scrollSpeed);
//
//    }


}
