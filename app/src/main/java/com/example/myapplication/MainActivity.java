package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        setupViewPager();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.it_list:{
                    viewPager.setCurrentItem(0);
                    break;
                }
                case R.id.it_calendar:{
                    viewPager.setCurrentItem(1);
                    break;
                }
                case R.id.it_weather:{
                    viewPager.setCurrentItem(2);
                    break;
                }
            }
            return true;
        });
    }

    public void setupViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:{
                        bottomNavigationView.getMenu().findItem(R.id.it_list).setChecked(true);
                        break;
                    }
                    case 1:{
                        bottomNavigationView.getMenu().findItem(R.id.it_calendar).setChecked(true);
                        break;
                    }
                    case 2:{
                        bottomNavigationView.getMenu().findItem(R.id.it_weather).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}