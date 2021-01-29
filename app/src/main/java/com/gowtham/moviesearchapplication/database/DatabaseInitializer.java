package com.gowtham.moviesearchapplication.database;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.gowtham.moviesearchapplication.model.MovieDetailsModel;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, final MovieDetailsModel movie) {
        PopulateDbAsync task = new PopulateDbAsync(db, movie);
        task.execute();
    }

    public static List<MovieDetailsModel> getAllMovies(final AppDatabase db) {
        List<MovieDetailsModel> list = db.movieDao().getAll();
        return list;
    }

    private static MovieDetailsModel addMovie(final AppDatabase db, MovieDetailsModel movie) {

        List<MovieDetailsModel> listHistory = getAllMovies(db);
        boolean isUniqueId = false;
        if(listHistory != null && listHistory.size() > 0){
            for(MovieDetailsModel movieDetailsModel : listHistory) {
                int id =  movieDetailsModel.getId();
                if(id == movie.getId()) {
                    isUniqueId = true;
                }
            }
        }
        if(!isUniqueId) {
            db.movieDao().insertAll(movie);
        }
        return movie;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private final MovieDetailsModel mMovie;

        PopulateDbAsync(AppDatabase db, MovieDetailsModel movie) {
            mDb = db;
            mMovie = movie;
        }

        @Override
        protected Void doInBackground(final Void... params) {

            addMovie(mDb, mMovie);
            return null;
        }

    }
}
