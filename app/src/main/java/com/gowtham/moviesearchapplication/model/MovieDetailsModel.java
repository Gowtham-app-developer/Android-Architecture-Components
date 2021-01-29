package com.gowtham.moviesearchapplication.model;

import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gowtham.moviesearchapplication.constants.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "movie")
public class MovieDetailsModel {

    @ColumnInfo(name = "vote_count")
    private int vote_count;
    @PrimaryKey(autoGenerate = false)
    private int id;
    @ColumnInfo(name = "video")
    private boolean video;
    @ColumnInfo(name = "vote_average")
    private double vote_average;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "popularity")
    private double popularity;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @ColumnInfo(name = "original_language")
    private String original_language;
    @ColumnInfo(name = "original_title")
    private String original_title;
    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "release_date")
    private String release_date;

    public MovieDetailsModel() {

    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    public String toJSONString() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(AppConstants.ID, this.getId());
            jsonObject.put(AppConstants.vote_count, this.vote_count);
            jsonObject.put(AppConstants.video, this.video);
            jsonObject.put(AppConstants.vote_average, this.vote_average);
            jsonObject.put(AppConstants.TITLE, this.title);
            jsonObject.put(AppConstants.popularity, this.popularity);
            jsonObject.put(AppConstants.poster_path, this.poster_path);
            jsonObject.put(AppConstants.original_language, this.original_language);
            jsonObject.put(AppConstants.original_title, this.original_title);
            jsonObject.put(AppConstants.overview, this.overview);
            jsonObject.put(AppConstants.release_date, this.release_date);

            return jsonObject.toString();
        }catch (JSONException e){
            return null;
        }
    }

    public static MovieDetailsModel getMovieDetailsModel(String pJSONObject)  {
        try{
            if (!TextUtils.isEmpty(pJSONObject)) {
                MovieDetailsModel movieDetailsModel = new MovieDetailsModel();
                JSONObject jsonObject = new JSONObject(pJSONObject);
                movieDetailsModel.setId(getIntValueFromJSON(jsonObject, AppConstants.ID));
                movieDetailsModel.setVote_count(getIntValueFromJSON(jsonObject, AppConstants.vote_count));
                movieDetailsModel.setVideo(getBooleanValueFromJSON(jsonObject, AppConstants.video));
                movieDetailsModel.setVote_average(getDoubleValueFromJSON(jsonObject, AppConstants.vote_average));
                movieDetailsModel.setTitle(getStringValueFromJSON(jsonObject, AppConstants.TITLE));
                movieDetailsModel.setPopularity(getDoubleValueFromJSON(jsonObject, AppConstants.popularity));
                movieDetailsModel.setPoster_path(getStringValueFromJSON(jsonObject, AppConstants.poster_path));
                movieDetailsModel.setOriginal_language(getStringValueFromJSON(jsonObject, AppConstants.original_language));
                movieDetailsModel.setOriginal_title(getStringValueFromJSON(jsonObject, AppConstants.original_title));
                movieDetailsModel.setOverview(getStringValueFromJSON(jsonObject, AppConstants.overview));
                movieDetailsModel.setRelease_date(getStringValueFromJSON(jsonObject, AppConstants.release_date));
                return movieDetailsModel;
            }
        }catch(JSONException e){
            return null;
        }
        return null;
    }

    private static String getStringValueFromJSON(JSONObject pJSONObject, String pKey) throws JSONException {
        if (!TextUtils.isEmpty(pKey) && pJSONObject != null) {
            if (pJSONObject.has(pKey)) {
                return pJSONObject.getString(pKey);
            }
        }
        return null;
    }
    private static int getIntValueFromJSON(JSONObject pJSONObject, String pKey) throws JSONException {
        if (!TextUtils.isEmpty(pKey) && pJSONObject != null) {
            if (pJSONObject.has(pKey)) {
                return pJSONObject.getInt(pKey);
            }
        }
        return 0;
    }
    private static double getDoubleValueFromJSON(JSONObject pJSONObject, String pKey) throws JSONException {
        if (!TextUtils.isEmpty(pKey) && pJSONObject != null) {
            if (pJSONObject.has(pKey)) {
                return pJSONObject.getDouble(pKey);
            }
        }
        return 0l;
    }
    private static boolean getBooleanValueFromJSON(JSONObject pJSONObject, String pKey) throws JSONException {
        if (!TextUtils.isEmpty(pKey) && pJSONObject != null) {
            if (pJSONObject.has(pKey)) {
                return pJSONObject.getBoolean(pKey);
            }
        }
        return false;
    }
}
