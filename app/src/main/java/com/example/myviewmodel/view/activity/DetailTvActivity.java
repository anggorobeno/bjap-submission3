package com.example.myviewmodel.view.activity;

import static androidx.swiperefreshlayout.widget.CircularProgressDrawable.LARGE;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.myviewmodel.R;
import com.example.myviewmodel.data.source.local.entity.TvEntity;
import com.example.myviewmodel.utils.Constant;
import com.example.myviewmodel.viewModel.DetailViewModel;
import com.example.myviewmodel.viewModel.ViewModelFactory;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailTvActivity extends AppCompatActivity {
    ImageView imgBackdrop;
    TextView tvTitle;
    TextView tvDesc;
    TvEntity selectedTv;
    CircleImageView circleImageView;
    TextView tvAiringDate;
    RatingBar ratingBar;
    TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStyle(LARGE);
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(this,R.color.red_400));
        circularProgressDrawable.start();
        imgBackdrop = findViewById(R.id.detailPoster);
        circleImageView = findViewById(R.id.detailCircleImage);
        tvAiringDate = findViewById(R.id.dateRelease);
        date = findViewById(R.id.textView3);
        imgBackdrop = findViewById(R.id.detailPoster);
        ratingBar = findViewById(R.id.ratingBar);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.detailDesc);
        selectedTv = getIntent().getParcelableExtra(Constant.EXTRA_TV);
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        DetailViewModel detailViewModel = new ViewModelProvider(this,factory).get(DetailViewModel.class);
        detailViewModel.setIdTv(selectedTv.getId());
        if (selectedTv.getId() > 0){
            detailViewModel.getSelectedTv().observe(this,tvResult -> {
                Glide.with(this)
                        .load(Constant.TMDB_BACKDROP + selectedTv.getBackdropPath())
                        .placeholder(circularProgressDrawable)
                        .into(imgBackdrop);
                tvTitle.setText(selectedTv.getName());
                tvDesc.setText(selectedTv.getOverview());
                ratingBar.setRating(selectedTv.getVoteAverage()/2);
                tvAiringDate.setText(selectedTv.getFirstAirDate());
                date.setText(R.string.airing_date);
                Glide.with(this)
                        .load(Constant.TMDB_POSTER + selectedTv.getPosterPath())
                        .into(circleImageView);
                if (selectedTv.getOverview().isEmpty()){
                    tvDesc.setText(R.string.no_desc);
                }
                else tvDesc.setText(selectedTv.getOverview());

            });
        }

    }
}
