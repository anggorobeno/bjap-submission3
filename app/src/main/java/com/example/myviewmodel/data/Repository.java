package com.example.myviewmodel.data;

import androidx.lifecycle.LiveData;

import com.example.myviewmodel.data.source.local.LocalDataSource;
import com.example.myviewmodel.data.source.local.entity.MovieEntity;
import com.example.myviewmodel.data.source.local.entity.TvEntity;
import com.example.myviewmodel.data.source.remote.ApiResponse;
import com.example.myviewmodel.data.source.remote.RemoteDataSource;
import com.example.myviewmodel.data.source.remote.response.movie.MovieResult;
import com.example.myviewmodel.data.source.remote.response.tv.TvResult;
import com.example.myviewmodel.utils.AppExecutors;
import com.example.myviewmodel.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class Repository implements MovieDataSource {
    private static Repository INSTANCE = null;
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private final AppExecutors appExecutors;

    public Repository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource, AppExecutors appExecutors) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.appExecutors = appExecutors;
    }

    public static Repository getInstance(RemoteDataSource remoteDataSource, LocalDataSource localDataSource, AppExecutors appExecutors){
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                INSTANCE = new Repository(remoteDataSource,localDataSource,appExecutors);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<List<MovieEntity>>> movieLiveData() {
        return new NetworkBoundResource<List<MovieEntity>, List<MovieResult>>(appExecutors) {
            @Override
            protected LiveData<List<MovieEntity>> loadFromDB() {
                return localDataSource.getAllMovie();
            }

            @Override
            protected Boolean shouldFetch(List<MovieEntity> data) {
                return (data == null)||(data.size()==0);
            }

            @Override
            protected LiveData<ApiResponse<List<MovieResult>>> createCall() {
                return remoteDataSource.movieLiveData();
            }

            @Override
            protected void saveCallResult(List<MovieResult> data) {
                List<MovieEntity> movieEntities = new ArrayList<>();
                for (MovieResult movieResult : data){
                    MovieEntity movieEntity = new MovieEntity(movieResult.getId(),
                            movieResult.getOverview(),
                            movieResult.getTitle(),
                            movieResult.getPosterPath(),
                            movieResult.getBackdropPath(),
                            movieResult.getReleaseDate(),
                            movieResult.getVoteAverage(),
                            null);

                    movieEntities.add(movieEntity);
                }
                localDataSource.insertMovie(movieEntities);
            }
        }.asLiveData();
    }
    @Override
    public LiveData<Resource<List<TvEntity>>> tvLiveData() {
        return new NetworkBoundResource<List<TvEntity>, List<TvResult>>(appExecutors) {
            @Override
            protected LiveData<List<TvEntity>> loadFromDB() {
                return localDataSource.getAllTv();
            }

            @Override
            protected Boolean shouldFetch(List<TvEntity> data) {
                return (data == null)||(data.size()==0);
            }

            @Override
            protected LiveData<ApiResponse<List<TvResult>>> createCall() {
                return remoteDataSource.tvLiveData();
            }

            @Override
            protected void saveCallResult(List<TvResult> data) {
                List<TvEntity> tvEntities = new ArrayList<>();
                for (TvResult tvResult : data){
                    TvEntity tvEntity = new TvEntity(tvResult.getId(),
                            tvResult.getFirstAirDate(),
                            tvResult.getOverview(),
                            tvResult.getName(),
                            tvResult.getPosterPath(),
                            tvResult.getBackdropPath(),
                            tvResult.getVoteAverage(),
                            null);
                    tvEntities.add(tvEntity);
                }
                localDataSource.insertTv(tvEntities);
            }
        }.asLiveData();
    }
    @Override
    public LiveData<Resource<MovieEntity>> selectedMovieLiveData(long movieId) {
        return new NetworkBoundResource<MovieEntity, MovieResult>(appExecutors) {
            @Override
            protected LiveData<MovieEntity> loadFromDB() {
                return localDataSource.getMovieById(movieId);
            }

            @Override
            protected Boolean shouldFetch(MovieEntity data) {
                return (data == null);
            }

            @Override
            protected LiveData<ApiResponse<MovieResult>> createCall() {
                return remoteDataSource.selectedMovieLiveData(movieId);
            }

            @Override
            protected void saveCallResult(MovieResult data) {
                List<MovieEntity> movieEntities = new ArrayList<>();
                MovieEntity movieEntity = new MovieEntity(data.getId(),
                        data.getOverview(),
                        data.getTitle(),
                        data.getPosterPath(),
                        data.getBackdropPath(),
                        data.getReleaseDate(),
                        data.getVoteAverage(),
                        null);
                movieEntities.add(movieEntity);
                localDataSource.insertMovie(movieEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<TvEntity>> selectedTvLiveData(long tvId) {
        return new NetworkBoundResource<TvEntity, TvResult>(appExecutors) {
            @Override
            protected LiveData<TvEntity> loadFromDB() {
                return localDataSource.getTvById(tvId);
            }

            @Override
            protected Boolean shouldFetch(TvEntity data) {
                return (data == null);
            }

            @Override
            protected LiveData<ApiResponse<TvResult>> createCall() {
                return remoteDataSource.selectedTvLiveData(tvId);
            }

            @Override
            protected void saveCallResult(TvResult data) {
                TvEntity tvEntity = new TvEntity(data.getId(),
                        data.getFirstAirDate(),
                        data.getOverview(),
                        data.getName(),
                        data.getPosterPath(),
                        data.getBackdropPath(),
                        data.getVoteAverage(),
                        null);
                List<TvEntity> tvEntities = new ArrayList<>();
                tvEntities.add(tvEntity);
                localDataSource.insertTv(tvEntities);

            }
        }.asLiveData();
    }

    @Override
    public void setFaveMovie(MovieEntity movieEntity, boolean state) {
        Runnable runnable = () -> localDataSource.setFaveMovie(movieEntity, state);
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void setFaveTv(TvEntity tvEntity, boolean state) {
        Runnable runnable = () -> localDataSource.setFaveTv(tvEntity, state);
        appExecutors.diskIO().execute(runnable);
    }
}
