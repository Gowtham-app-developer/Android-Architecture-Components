package com.gowtham.moviesearchapplication.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gowtham.moviesearchapplication.database.AppDatabase;
import com.gowtham.moviesearchapplication.database.DatabaseInitializer;
import com.gowtham.moviesearchapplication.model.MovieDetailsModel;

import java.util.List;

public class HistoryActivityViewModel  extends AndroidViewModel {

    private static final String TAG = "HistoryActivityViewMode";
    private MutableLiveData<List<MovieDetailsModel>> movieDataList;

    /**
     * Constructor of View Model with app context.
     * @param application
     */
    public HistoryActivityViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * to set the observable on this Mutable live data.
     * @return MutableLiveData<List<MovieDetailsModel>>
     */
    public MutableLiveData<List<MovieDetailsModel>>  getMovieDataList() {
        if (movieDataList == null) {
            movieDataList = new MutableLiveData<>();
        }
        loadData();
        return movieDataList;
    }

    /**
     * Load Data from Db and build a list.
     */
    private void loadData() {
        List<MovieDetailsModel> data =   DatabaseInitializer.getAllMovies(AppDatabase.getAppDatabase(getApplication()));
        if(data != null) {
            movieDataList.setValue(data);
        } else {
            movieDataList.setValue(null);
        }
    }

}

