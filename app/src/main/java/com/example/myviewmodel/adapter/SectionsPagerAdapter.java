package com.example.myviewmodel.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myviewmodel.view.fragment.MovieFragment;
import com.example.myviewmodel.view.fragment.TvFragment;

public class SectionsPagerAdapter extends FragmentStateAdapter {
    public SectionsPagerAdapter(AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new TvFragment();
                break;
        }
        return fragment;
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
