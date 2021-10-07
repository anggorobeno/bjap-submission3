package com.example.myviewmodel.data.source.local;

import androidx.lifecycle.LiveData;

import com.example.myviewmodel.data.source.local.entity.MovieEntity;
import com.example.myviewmodel.data.source.local.entity.TvEntity;
import com.example.myviewmodel.data.source.local.room.MovieDao;

import java.util.List;

public class LocalDataSource {
    private static LocalDataSource INSTANCE;
    private final MovieDao movieDao;

    public LocalDataSource(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
    public static LocalDataSource getInstance(MovieDao movieDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(movieDao);
        }
        return INSTANCE;
    }

    // Movie
    public LiveData<MovieEntity> getMovieById(long id){
       return movieDao.getMovieById(id);
    }
    public LiveData<List<MovieEntity>> getAllMovie(){
        return movieDao.getAllMovie();
    }
    public void insertMovie(List<MovieEntity> movieEntities){
        movieDao.insertMovie(movieEntities);
    }
    public void setFaveMovie(MovieEntity movieEntity,boolean state){
        movieEntity.setFave(state);
        movieDao.updateMovies(movieEntity);
    }

    // TV
    public LiveData<TvEntity> getTvById(long id){
        return movieDao.getTvById(id);
    }
    public LiveData<List<TvEntity>> getAllTv(){
        return movieDao.getAllTv();
    }
    public void insertTv(List<TvEntity> tvEntities){
        movieDao.insertTv(tvEntities);
    }
    public void setFaveTv(TvEntity tvEntity,boolean state){
        tvEntity.setFave(state);
        movieDao.updateTv(tvEntity);
    }


}
