package com.example.myviewmodel.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.example.myviewmodel.adapter.TvFragmentAdapter;
import com.example.myviewmodel.api.ApiConfig;
import com.example.myviewmodel.viewModel.TvViewModel;
import com.example.myviewmodel.viewModel.ViewModelFactory;
import com.facebook.shimmer.ShimmerFrameLayout;


public class TvFragment extends Fragment {
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tv, container, false);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvTv);
        shimmerFrameLayout = view.findViewById(R.id.shimmerContainer);
        if (getActivity()!= null){
            ViewModelFactory factory = ViewModelFactory.getInstance(this.getActivity().getApplication());
            TvViewModel tvViewModel = new ViewModelProvider(this,factory).get(TvViewModel.class);
            TvFragmentAdapter tvFragmentAdapter = new TvFragmentAdapter(getActivity());
            tvViewModel.tvLiveData().observe(getViewLifecycleOwner(),tvResults -> {
                tvFragmentAdapter.setListTv(tvResults.data);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.smoothScrollToPosition(0);
                recyclerView.setAdapter(tvFragmentAdapter);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                tvFragmentAdapter.notifyDataSetChanged();
            });



        }
    }

}