package com.gowtham.moviesearchapplication.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gowtham.moviesearchapplication.constants.AppConstants;
import com.gowtham.moviesearchapplication.model.MovieDetailsModel;

@Database(entities = {MovieDetailsModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract MovieDao movieDao();

    /**
     * Creating the database
     * @param context
     * @return
     */
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppConstants.APP_DB_NAME)
                            // allow queries on the main thread.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
