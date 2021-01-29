package com.gowtham.moviesearchapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gowtham.moviesearchapplication.constants.AppConstants;
import com.gowtham.moviesearchapplication.constants.URLConstants;
import com.gowtham.moviesearchapplication.database.AppDatabase;
import com.gowtham.moviesearchapplication.database.DatabaseInitializer;
import com.gowtham.moviesearchapplication.model.MovieDetailsModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_overview)
    TextView tv_overview;

    @BindView(R.id.tv_original_language)
    TextView tv_original_language;

    @BindView(R.id.tv_release_date)
    TextView tv_release_date;

    @BindView(R.id.iv_thumb)
    ImageView iv_thumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        iniUI();
    }

    /**
     * Display the ui components on UI.
     */
    private void iniUI() {
        MovieDetailsModel detailModel = new MovieDetailsModel();
        String json =  getIntent().getStringExtra(AppConstants.DATA);
        int history = getIntent().getIntExtra(AppConstants.FROM, 0);
        detailModel = MovieDetailsModel.getMovieDetailsModel(json);
        if(detailModel == null){
            tv_title.setText(getResources().getString(R.string.no_result));
            return;
        }
        Picasso.with(this).load(URLConstants.THUMBNAIL_URL + detailModel.getPoster_path()).placeholder(getResources().getDrawable(R.drawable.no_image)).into(iv_thumb);
        tv_title.setText(detailModel.getTitle());
        tv_overview.setText("Movie Overview - "+detailModel.getOverview());
        tv_original_language.setText("Movie Language - "+detailModel.getOriginal_language());
        tv_release_date.setText("Movie Release date - " + detailModel.getRelease_date());

        if(history == 0) {
            buildHistory(detailModel);
        }
    }

    /**
     * make a entry in db for history.
     * @param detailModel
     */
    private void buildHistory(MovieDetailsModel detailModel) {
        if(detailModel != null) {
            DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(DetailActivity.this), detailModel);
        }
    }
}
