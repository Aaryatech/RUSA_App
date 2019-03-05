package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.TestimonialsSliderAdapter;
import com.ats.rusa_app.model.Testimonials;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    final ArrayList<Testimonials> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        sliderLayout = view.findViewById(R.id.slider);

        imageSlider();
        initYoutubeVideo("gG2npfpaqsY");

        recyclerView = view.findViewById(R.id.recyclerView);

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

    private void imageSlider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "https://images.pexels.com/photos/326055/pexels-photo-326055.jpeg?cs=srgb&dl=beautiful-blur-bright-326055.jpg&fm=jpg");
        url_maps.put("Big Bang Theory", "https://resize.indiatvnews.com/en/resize/newbucket/715_-/2018/02/propose-1517999844.jpg");
        url_maps.put("House of Cards", "https://cdn.pixabay.com/photo/2017/01/06/23/21/soap-bubble-1959327_960_720.jpg");
        url_maps.put("Game of Thrones", "https://media.cntraveller.in/wp-content/uploads/2018/10/GettyImages-990972132-866x487.jpg");


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
