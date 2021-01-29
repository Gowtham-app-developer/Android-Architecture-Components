package com.gowtham.moviesearchapplication.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gowtham.moviesearchapplication.model.MovieDetailsModel;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<MovieDetailsModel> getAll();

    @Insert
    void insertAll(MovieDetailsModel movie);

}
