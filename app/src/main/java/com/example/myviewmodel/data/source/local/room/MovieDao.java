package com.example.myviewmodel.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myviewmodel.data.source.local.entity.MovieEntity;
import com.example.myviewmodel.data.source.local.entity.TvEntity;

import java.util.List;

@Dao
public interface MovieDao {
    // Movie
    @Query("SELECT * FROM movieTable WHERE idMovie = :id")
    LiveData<MovieEntity> getMovieById(long id);

    @Query("SELECT * FROM movieTable ORDER BY title ASC")
    LiveData<List<MovieEntity>> getAllMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(List<MovieEntity> movieEntities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovies(MovieEntity movieEntity);


    // TV
    @Query("SELECT * FROM tvTable WHERE idTv = :id")
    LiveData<TvEntity> getTvById(long id);

    @Query("SELECT * FROM tvTable ORDER BY title ASC")
    LiveData<List<TvEntity>> getAllTv();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTv(List<TvEntity> tvEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateTv(TvEntity tvEntity);
}
