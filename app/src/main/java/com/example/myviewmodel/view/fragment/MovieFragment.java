package com.example.myviewmodel.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myviewmodel.R;
import com.example.myviewmodel.adapter.MovieFragmentAdapter;
import com.example.myviewmodel.api.ApiConfig;
import com.example.myviewmodel.data.source.local.entity.MovieEntity;
import com.example.myviewmodel.viewModel.MovieViewModel;
import com.example.myviewmodel.viewModel.ViewModelFactory;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;


public class MovieFragment extends Fragment {
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    private List<MovieEntity> movies;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }



    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvMovie);
        shimmerFrameLayout = view.findViewById(R.id.shimmerContainer);
        if (getActivity()!= null){
            ViewModelFactory factory = ViewModelFactory.getInstance(this.getActivity().getApplication());
            MovieViewModel movieViewModel = new ViewModelProvider(this,factory).get(MovieViewModel.class);
            MovieFragmentAdapter movieFragmentAdapter = new MovieFragmentAdapter(getActivity());
            movieViewModel.movieLiveData().observe(getViewLifecycleOwner(),movies-> {
                    movieFragmentAdapter.setListMovie(movies.data);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.smoothScrollToPosition(0);
                    recyclerView.setAdapter(movieFragmentAdapter);
                    shimmerFrameLayout.stopShimmer();
                    movieFragmentAdapter.notifyDataSetChanged();
            });
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }
    }

}