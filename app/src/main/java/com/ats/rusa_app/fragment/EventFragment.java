package com.ats.rusa_app.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.ViewPagerAdapter;
import com.ats.rusa_app.interfaces.PhotosInterface;
import com.ats.rusa_app.interfaces.PreviousEventsInterface;
import com.ats.rusa_app.interfaces.UpcomingEventsInterface;
import com.ats.rusa_app.interfaces.VideosInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FragmentPagerAdapter adapterViewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
       // setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);


        adapterViewPager = new ViewPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapterViewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    tabLayout.setupWithViewPager(viewPager);
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
                    UpcomingEventsInterface fragmentUpcomingEvents = (UpcomingEventsInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentUpcomingEvents != null) {
                        fragmentUpcomingEvents.fragmentBecameVisible();
                    }
                } else if (position == 1) {
                    PreviousEventsInterface fragmentPreviousEvents = (PreviousEventsInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentPreviousEvents != null) {
                        fragmentPreviousEvents.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return view;
    }

   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new UpcomingEventFragment(), "Upcoming");
        adapter.addFragment(new PreviousEventFragment(), "Previous");
        viewPager.setAdapter(adapter);
    }*/

    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public ViewPagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new UpcomingEventFragment();
            } else {
                return new PreviousEventFragment();
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
                    return "Upcoming Events";
                case 1:
                    return "Previous Events";
                default:
                    return null;
            }
        }
    }

}
