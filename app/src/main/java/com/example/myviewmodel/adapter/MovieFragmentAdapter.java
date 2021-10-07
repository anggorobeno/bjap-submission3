package com.example.myviewmodel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myviewmodel.R;
import com.example.myviewmodel.data.source.local.entity.MovieEntity;
import com.example.myviewmodel.data.source.remote.response.movie.MovieResult;
import com.example.myviewmodel.utils.Constant;
import com.example.myviewmodel.view.activity.DetailMovieActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieFragmentAdapter extends RecyclerView.Adapter<MovieFragmentAdapter.ViewHolder> {
    final Activity activity;
    private List<MovieEntity> listMovie = new ArrayList<>();



    public MovieFragmentAdapter(Activity activity) {
        this.activity = activity;
    }
    public void setListMovie(List<MovieEntity> listMovie) {
        if (listMovie == null) return;
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
    }

    @NonNull
    @Override
    public MovieFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFragmentAdapter.ViewHolder holder, int position) {
        holder.bindData(listMovie.get(position));
        holder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailMovieActivity.class);
            intent.putExtra(Constant.EXTRA_MOVIE, listMovie.get(position));
            activity.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if (listMovie == null){
            Log.d("TAG", "getItemCount: null");
        }
        return listMovie.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final ImageView imgPoster;
        final TextView tvOverview;
        final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            linearLayout = itemView.findViewById(R.id.container);
            tvOverview = itemView.findViewById(R.id.tvOverview);
        }

        public void bindData(MovieEntity movieEntity) {
            tvTitle.setText(movieEntity.getTitle());
            tvOverview.setText(movieEntity.getOverview());
            Glide.with(itemView.getContext())
                    .load(Constant.TMDB_POSTER + movieEntity.getPosterPath())
                    .into(imgPoster);
        }
    }
}
