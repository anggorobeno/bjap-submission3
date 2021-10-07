package com.example.myviewmodel.data;

import androidx.lifecycle.LiveData;

import com.example.myviewmodel.data.source.local.entity.MovieEntity;
import com.example.myviewmodel.data.source.local.entity.TvEntity;
import com.example.myviewmodel.data.source.remote.response.movie.MovieResult;
import com.example.myviewmodel.data.source.remote.response.tv.TvResult;
import com.example.myviewmodel.vo.Resource;

import java.util.List;

public interface MovieDataSource {
    LiveData<Resource<List<MovieEntity>>> movieLiveData();
    LiveData<Resource<List<TvEntity>>> tvLiveData();
    LiveData<Resource<MovieEntity>> selectedMovieLiveData(long movieId);
    LiveData<Resource<TvEntity>> selectedTvLiveData(long tvId);
    void setFaveMovie(MovieEntity movieEntity, boolean state);
    void setFaveTv(TvEntity tvEntity,boolean state);



}
