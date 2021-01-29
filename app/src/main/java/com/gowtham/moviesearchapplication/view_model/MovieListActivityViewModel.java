package com.gowtham.moviesearchapplication.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gowtham.moviesearchapplication.constants.AppConstants;
import com.gowtham.moviesearchapplication.constants.URLConstants;
import com.gowtham.moviesearchapplication.model.MovieDetailsModel;
import com.gowtham.moviesearchapplication.model.MovieModel;
import com.gowtham.moviesearchapplication.retrofit.APIInterface;
import com.gowtham.moviesearchapplication.retrofit.NetworkClient;
import com.gowtham.moviesearchapplication.utils.LogUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivityViewModel extends AndroidViewModel {

    private static final String TAG = "MovieListActivityViewModel";
    private MutableLiveData<List<MovieDetailsModel>> movieDataList;

    /**
     * Constructor of View Model with app context.
     * @param application
     */
    public MovieListActivityViewModel(@NonNull Application application){
        super(application);
    }

    /**
     * to set the observable on this Mutable live data.
     * @return MutableLiveData<List<MovieDetailsModel>>
     */
    public MutableLiveData<List<MovieDetailsModel>>  getMovieDataList() {
        LogUtils.log(TAG,"getMovieDataList movieDataList called : " + movieDataList);
        if (movieDataList == null) {
            movieDataList = new MutableLiveData<>();
        }
        loadData();
        return movieDataList;
    }


    private void loadData() {
        APIInterface apiInterface = NetworkClient.getClient().create(APIInterface.class);

        Call<MovieModel> call = apiInterface.GetMovieList(URLConstants.API_KEY);

        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                LogUtils.log(TAG,"loadData onResponse called");

                if(response != null ) {
                    if (response.code() == 200) {
                        LogUtils.log(TAG, response.code() + "");
                        LogUtils.log(TAG, response.body() + "");
                        List<MovieDetailsModel> movieListModel = response.body().getListMovies();
                        if (movieListModel != null) {
                            movieDataList.setValue(response.body().getListMovies());
                        }else {
                            movieDataList.setValue(null);
                        }
                    }else {
                        LogUtils.log(TAG, response.code() + "");
                        movieDataList.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                LogUtils.log(TAG,"loadData onFailure called " + t.toString());
                movieDataList.setValue(null);
                call.cancel();

            }
        });
    }
}
