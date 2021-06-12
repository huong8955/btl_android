package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new ListFragment();
            }
            case 1:{
                return new CalendarFragment();
            }
            case 2:{
                return new WeatherFragment();
            }
            default:
                return new ListFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
