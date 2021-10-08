package com.example.myviewmodel.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.myviewmodel.R;
import com.example.myviewmodel.adapter.BottomNavAdapter;
import com.example.myviewmodel.databinding.ActivityBottomNavBinding;

public class BottomNavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBottomNavBinding activityBottomNavBinding = ActivityBottomNavBinding.inflate(getLayoutInflater());
        setContentView(activityBottomNavBinding.getRoot());
        BottomNavAdapter bottomNavAdapter = new BottomNavAdapter(this);
        activityBottomNavBinding.viewPager.setAdapter(bottomNavAdapter);
        activityBottomNavBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                activityBottomNavBinding.bubbleTabBar.setSelected(position, false);
            }
        });
        activityBottomNavBinding.bubbleTabBar.addBubbleListener(id -> {
            if (id == R.id.menu_movie) {
                activityBottomNavBinding.viewPager.setCurrentItem(0);
            } else if (id == R.id.menu_tv) {
                activityBottomNavBinding.viewPager.setCurrentItem(1);
            } else if (id == R.id.menu_fav) {
                activityBottomNavBinding.viewPager.setCurrentItem(2);
            }
        });
    }
}