package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.CompSliderAdapter;
import com.ats.rusa_app.adapter.NewsFeedAdapter;
import com.ats.rusa_app.adapter.TestimonialsSliderAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Baner;
import com.ats.rusa_app.model.CompanyModel;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.NewDetail;
import com.ats.rusa_app.model.Testimonials;
import com.ats.rusa_app.util.CommonDialog;
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
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;
    public RelativeLayout relativeLayout_baner;
    public ImageView iv_baner;

    private RecyclerView recyclerView,new_recyclerView,comp_recyclerView;
    private LinearLayoutManager linearLayoutManager;

    final ArrayList<Testimonials> list = new ArrayList<>();
    ArrayList<NewDetail> newsList = new ArrayList<>();
    ArrayList<GallaryDetailList> galleryList = new ArrayList<>();
    ArrayList<CompanyModel> companyList = new ArrayList<>();
    ArrayList<Baner> banerList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        sliderLayout = view.findViewById(R.id.slider);
        recyclerView = view.findViewById(R.id.recyclerView);
        new_recyclerView = view.findViewById(R.id.news_recyclerView);
        comp_recyclerView=view.findViewById(R.id.comp_recyclerView);
        relativeLayout_baner=view.findViewById(R.id.relativeLayout_baner);
        iv_baner=view.findViewById(R.id.iv_baner);


        getImageGallery();
        getNewFeed();
        getCompSlider();
        getBaner();
        initYoutubeVideo("gG2npfpaqsY");

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Testimonials t1 = new Testimonials("AAA", "sdfdfgfdhgfdh");
        Testimonials t2 = new Testimonials("BBB", "sdfdfgfdhgfdh");
        Testimonials t3 = new Testimonials("CCC", "sdfdfgfdhgfdh");
        Testimonials t4 = new Testimonials("DDD", "sdfdfgfdhgfdh");

        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);

        TestimonialsSliderAdapter adapter = new TestimonialsSliderAdapter(list, getContext());
        recyclerView.setAdapter(adapter);

       autoScroll();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
            int firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition();
        if (firstItemVisible != 0 && firstItemVisible % list.size() == 0) {
                recyclerView.getLayoutManager().scrollToPosition(0);
            }
        }
    });


        return view;
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
                            Baner baner=response.body();
                                String imageUri =Constants.BANENR_URL +baner.getSliderImage();
                                Log.e("URI","-----------"+imageUri);
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
                            companyList=response.body();
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
                            galleryList=response.body();
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

    private void getNewFeed() {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<NewDetail>> listCall = Constants.myInterface.getNewsData(1);
            listCall.enqueue(new Callback<ArrayList<NewDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<NewDetail>> call, Response<ArrayList<NewDetail>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("NEWS DATA : ", " - " + response.body());
                            newsList.clear();
                            newsList=response.body();
                           // NewDetail newDetail=response.body();
                            //newsList.add(newDetail);


                           NewsFeedAdapter adapter = new NewsFeedAdapter(newsList, getContext());
                            new_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            new_recyclerView.setAdapter(adapter);

                            //                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());

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
        for(int i = 0; i< gallaryDetailLists.size(); i++)
        {
            String image=Constants.GALLERY_URL+gallaryDetailLists.get(i).getFileName();
            String title=gallaryDetailLists.get(i).getTitle();
            url_maps.put("",image);
            Log.e("Gallery","----------"+url_maps);

        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
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

    public void autoScroll() {

        final int scrollSpeed = 100;   // Scroll Speed in Milliseconds
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int x = 2;        // Pixels To Move/Scroll
            boolean flag = true;
            // Find Scroll Position By Accessing RecyclerView's LinearLayout's Visible Item Position
            int scrollPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            int arraySize = list.size();  // Gets RecyclerView's Adapter's Array Size

            @Override
            public void run() {
                if (scrollPosition < arraySize) {
                    if (scrollPosition == arraySize - 1) {
                        flag = false;
                    } else if (scrollPosition <= 1) {
                        flag = true;
                    }
                    if (!flag) {
                        try {
                            // Delay in Seconds So User Can Completely Read Till Last String
                            TimeUnit.SECONDS.sleep(1);
                            recyclerView.scrollToPosition(0);  // Jumps Back Scroll To Start Point
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // Know The Last Visible Item
                    scrollPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                    recyclerView.smoothScrollBy(x, 0);
                    handler.postDelayed(this, scrollSpeed);
                }
            }
        };
        handler.postDelayed(runnable, scrollSpeed);

    }



}
