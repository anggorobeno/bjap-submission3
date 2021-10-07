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
import com.example.myviewmodel.data.source.local.entity.TvEntity;
import com.example.myviewmodel.data.source.remote.response.tv.TvResult;
import com.example.myviewmodel.utils.Constant;
import com.example.myviewmodel.view.activity.DetailTvActivity;

import java.util.ArrayList;
import java.util.List;

public class TvFragmentAdapter extends RecyclerView.Adapter<TvFragmentAdapter.ViewHolder> {
    final Activity activity;

    public TvFragmentAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListTv(List<TvEntity> listTv) {
        if (listTv == null) return;
        this.listTv.clear();
        this.listTv.addAll(listTv);
    }

    private List<TvEntity> listTv = new ArrayList<>();

    @NonNull
    @Override
    public TvFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvFragmentAdapter.ViewHolder holder, int position) {
        holder.bindData(listTv.get(position));
        holder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailTvActivity.class);
            intent.putExtra(Constant.EXTRA_TV, listTv.get(position));
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (listTv == null){
            Log.d("TAG", "getItemCount: null1");
        }
        return listTv.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvOverview;
        final ImageView imgPoster;
        final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            linearLayout = itemView.findViewById(R.id.container);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            imgPoster = itemView.findViewById(R.id.imgPoster);
        }

        public void bindData(TvEntity tvResult) {
            tvTitle.setText(tvResult.getName());
            tvOverview.setText(tvResult.getOverview());
            Glide.with(itemView.getContext())
                    .load(Constant.TMDB_POSTER + tvResult.getPosterPath())
                    .into(imgPoster);
        }
    }
}
