package com.example.myviewmodel.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myviewmodel.data.Repository;
import com.example.myviewmodel.data.source.local.entity.TvEntity;
import com.example.myviewmodel.vo.Resource;

import java.util.List;

public class TvViewModel extends ViewModel {
    private final Repository repository;

    public TvViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<TvEntity>>> tvLiveData(){
        return repository.tvLiveData();
    }

}
