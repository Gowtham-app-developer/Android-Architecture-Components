package com.gowtham.moviesearchapplication.retrofit;


import com.gowtham.moviesearchapplication.model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("search/movie?page=1&include_adult=false")
    Call<MovieModel> doGetMovieList(@Query("api_key") String api_key, @Query("language") String language, @Query("query") String query);

    @GET("trending/all/day")
    Call<MovieModel> GetMovieList(@Query("api_key") String api_key);
}
