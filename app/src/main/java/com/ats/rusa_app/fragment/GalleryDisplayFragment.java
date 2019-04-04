package com.ats.rusa_app.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.ViewPagerAdapter;
import com.ats.rusa_app.interfaces.PhotosInterface;
import com.ats.rusa_app.interfaces.VideosInterface;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.VideoList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GalleryDisplayFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tab;
    FragmentPagerAdapter adapterViewPager;

    public static ArrayList<GallaryDetailList> staticPhotosList;
    public static ArrayList<VideoList> staticVideosList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_display, container, false);

        String title = getArguments().getString("title");
        //getActivity().setTitle("" + title);

        try {
            String picStr = getArguments().getString("gallery");
            Gson gson = new Gson();
            Type typePic = new TypeToken<ArrayList<GallaryDetailList>>() {
            }.getType();
            staticPhotosList = gson.fromJson(picStr, typePic);
            Log.e("GAL DISP FRAG - "," ------------PIC-------------- "+staticPhotosList);
        } catch (Exception e) {
            Log.e("GAL DISP FRAG", "----------PIC------------- EXCEPTION : " + e.getMessage());
        }

        try {
            String vidStr = getArguments().getString("video");
            Gson gson = new Gson();
            Type typeVid = new TypeToken<ArrayList<VideoList>>() {
            }.getType();
            staticVideosList = gson.fromJson(vidStr, typeVid);
            Log.e("GAL DISP FRAG - "," -----------VID--------------- "+staticVideosList);
        } catch (Exception e) {
            Log.e("GAL DISP FRAG", "-------------VID---------- EXCEPTION : " + e.getMessage());
        }


        viewPager = view.findViewById(R.id.viewPager);
        tab = view.findViewById(R.id.tab);

        adapterViewPager = new ViewPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapterViewPager);
        tab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    tab.setupWithViewPager(viewPager);
                } catch (Exception e) {
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ////Log.e("POSITION : ", "----------------------" + position);

                if (position == 0) {
                    PhotosInterface fragmentPhotos = (PhotosInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentPhotos != null) {
                        fragmentPhotos.fragmentBecameVisible();
                    }
                } else if (position == 1) {
                    VideosInterface fragmentVideos = (VideosInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentVideos != null) {
                        fragmentVideos.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return view;
    }


    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public ViewPagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new PhotosFragment();
            } else {
                return new VideosFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Photos";
                case 1:
                    return "Videos";
                default:
                    return null;
            }
        }
    }

}
