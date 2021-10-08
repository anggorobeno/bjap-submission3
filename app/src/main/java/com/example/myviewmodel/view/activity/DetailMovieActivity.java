package com.example.myviewmodel.view.activity;

import static androidx.swiperefreshlayout.widget.CircularProgressDrawable.LARGE;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.myviewmodel.R;
import com.example.myviewmodel.data.source.local.entity.MovieEntity;
import com.example.myviewmodel.utils.Constant;
import com.example.myviewmodel.viewModel.DetailViewModel;
import com.example.myviewmodel.viewModel.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailMovieActivity extends AppCompatActivity {
    ImageView imgBackdrop;
    TextView tvTitle;
    TextView tvDesc;
    MovieEntity selectedMovie;
    ImageView circleImageView;
    TextView tvReleaseDate;
    RatingBar ratingBar;
    FloatingActionButton fabFav;
    DetailViewModel detailViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tes_detail_bre);
        imgBackdrop = findViewById(R.id.detailPoster);
        circleImageView = findViewById(R.id.detailCircleImage);
        tvReleaseDate = findViewById(R.id.dateRelease);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.detailDesc);
        ratingBar = findViewById(R.id.ratingBar);
        fabFav = findViewById(R.id.fabFav);
        detailViewModel = obtainViewModel();
        selectedMovie = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);
        detailViewModel.setIdMovie(selectedMovie.getId());
        if (selectedMovie.getId() > 0){
            detailViewModel.getSelectedMovie().observe(this,movieEntityResource -> {
                if(movieEntityResource != null) {
                    switch (movieEntityResource.status) {
                        case SUCCESS:
                            if (movieEntityResource.data != null) {
                                populateMovie(selectedMovie);
                                break;
                            }
                        case LOADING:
                            break;
                        case ERROR:
                            Log.d("DetailMovieActivity", "onCreate: Error bre");
                    }
                }
            });
            setFave(selectedMovie);

        }
    }

    private DetailViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        return detailViewModel = new ViewModelProvider(this,factory).get(DetailViewModel.class);
    }

    private void setFave(MovieEntity selectedMovie) {
        if (selectedMovie.isFave()){
            fabFav.setImageResource(R.drawable.baseline_favorite_black_24dp);
        }
        detailViewModel.getSelectedMovie().observe(this,faveMovie ->{
            if(faveMovie != null) {
                switch (faveMovie.status) {
                    case SUCCESS:
                        if (faveMovie.data != null) {
                            Log.d("TAG", "setFave: tes");
                            boolean state = faveMovie.data.isFave();

                            fabFav.setOnClickListener(v->{
                                if (state) {
                                    fabFav.setImageResource(R.drawable.baseline_favorite_border_black_24dp);
                                    Snackbar.make(fabFav, "Film Anda telah dihapus dari favorit", Snackbar.LENGTH_LONG).show();
                                    detailViewModel.setFaveMovie();
                                    Log.d("TAG", "setFave: hapus fav");

                                } else {
                                    fabFav.setImageResource(R.drawable.baseline_favorite_black_24dp);
                                    Snackbar.make(fabFav, "Film Anda telah ditambahkan ke favorit", Snackbar.LENGTH_LONG).show();
                                    detailViewModel.setFaveMovie();
                                    Log.d("TAG", "setFave: tambah fav");

                                }
                        });
                        }
                        break;
                    case LOADING:
                        break;
                    case ERROR:
                        Log.d("DetailMovieActivity", "onCreate: Error bre");
                }
            }
        });
    }


    private void populateMovie(MovieEntity data) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStyle(LARGE);
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(this,R.color.red_400));
        circularProgressDrawable.start();
        Glide.with(this)
                .load(Constant.TMDB_BACKDROP + data.getBackdropPath())
                .placeholder(circularProgressDrawable)
                .into(imgBackdrop);
        tvTitle.setText(data.getTitle());
        ratingBar.setRating(data.getVoteAverage()/2);
        tvReleaseDate.setText(data.getReleaseDate());
        Glide.with(this)
                .load(Constant.TMDB_POSTER + data.getPosterPath())
                .into(circleImageView);
        if (data.getOverview() != null){
            tvDesc.setText(data.getOverview());
        }
        else tvDesc.setText(R.string.no_desc);
    }
}