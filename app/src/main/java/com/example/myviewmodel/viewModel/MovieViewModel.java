package com.example.myviewmodel.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myviewmodel.data.Repository;
import com.example.myviewmodel.data.source.local.entity.MovieEntity;
import com.example.myviewmodel.data.source.remote.response.movie.MovieResult;
import com.example.myviewmodel.vo.Resource;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private final Repository repository;

    public MovieViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }
    public LiveData<Resource<List<MovieEntity>>> movieLiveData(){
        return repository.movieLiveData();
    }

}

