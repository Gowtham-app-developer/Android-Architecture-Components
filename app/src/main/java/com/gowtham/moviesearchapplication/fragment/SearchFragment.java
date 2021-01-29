package com.gowtham.moviesearchapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gowtham.moviesearchapplication.DetailActivity;
import com.gowtham.moviesearchapplication.R;
import com.gowtham.moviesearchapplication.adapter.IHandleRowClick;
import com.gowtham.moviesearchapplication.adapter.RecyclerViewAdapter;
import com.gowtham.moviesearchapplication.constants.AppConstants;
import com.gowtham.moviesearchapplication.model.MovieDetailsModel;
import com.gowtham.moviesearchapplication.utils.AppUtils;
import com.gowtham.moviesearchapplication.utils.LogUtils;
import com.gowtham.moviesearchapplication.view_model.MovieListActivityViewModel;
import com.gowtham.moviesearchapplication.view_model.SearchActivityViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private RecyclerViewAdapter adapter;
    @BindView(R.id.listRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvnoresult)
    TextView tvNoResult;
    @BindView(R.id.etsearch)
    EditText ev_search;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    private void GetMovieListViewModel() {
        if (AppUtils.isConnectionAvailable(getActivity())) {
            MovieListActivityViewModel movieListActivityViewModel = ViewModelProviders.of(this).get(MovieListActivityViewModel.class);
            movieListActivityViewModel.getMovieDataList();

            final Observer<List<MovieDetailsModel>> mListObserver = new Observer<List<MovieDetailsModel>>() {
                @Override
                public void onChanged(@Nullable final List<MovieDetailsModel> updatedList) {
                    LogUtils.log(TAG, "SearchFragment onChanged called : updatedList : " + updatedList);

                    if (updatedList != null && updatedList.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNoResult.setVisibility(View.GONE);
                        adapter.setUpdatedData(updatedList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        tvNoResult.setVisibility(View.VISIBLE);
                        tvNoResult.setText(getActivity().getResources().getString(R.string.no_result));
                    }
                }
            };
            movieListActivityViewModel.getMovieDataList().observe(this, mListObserver);

        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
            tvNoResult.setText(getActivity().getResources().getString(R.string.no_internet));
        }
    }

    /**
     * Initialize the view model and observer for live data.
     */
    private void initViewModel() {
        if (AppUtils.isConnectionAvailable(getActivity())) {

            SearchActivityViewModel searchActivityViewModel = ViewModelProviders.of(this).get(SearchActivityViewModel.class);
            searchActivityViewModel.getMovieDataList(ev_search.getText().toString());

            final Observer<List<MovieDetailsModel>> listObserver = new Observer<List<MovieDetailsModel>>() {
                @Override
                public void onChanged(@Nullable final List<MovieDetailsModel> updatedList) {
                    LogUtils.log(TAG, "SearchFragment onChanged called : updatedList : " + updatedList);

                    if (updatedList != null && updatedList.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNoResult.setVisibility(View.GONE);
                        adapter.setUpdatedData(updatedList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        tvNoResult.setVisibility(View.VISIBLE);
                        tvNoResult.setText(getActivity().getResources().getString(R.string.no_result));
                    }
                }
            };
            searchActivityViewModel.getMovieDataList(ev_search.getText().toString()).observe(this, listObserver);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
            tvNoResult.setText(getActivity().getResources().getString(R.string.no_internet));
        }
    }

    /**
     * Fragment lifecycle method
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        setRecyclerViewAdapter();
        addTextListener();
        checkInternetConnectivity();
        GetMovieListViewModel();
        return rootView;
    }

    /**
     * Set the adapter to the Recycler view.
     */
    private void setRecyclerViewAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity(), null, new IHandleRowClick() {
            @Override
            public void handleRowClick(MovieDetailsModel model) {
                LogUtils.log(TAG, "====model title :" + model.getTitle() + ", release date  :" + model.getRelease_date());
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConstants.DATA, model.toJSONString());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * Check the internet connectivity
     */
    private void checkInternetConnectivity() {
        if (!AppUtils.isConnectionAvailable(getActivity())) {
            recyclerView.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
            tvNoResult.setText(getActivity().getResources().getString(R.string.no_internet));
        }
    }

    /**
     * Edittext text change listener for search movie
     */
    private void addTextListener() {
        ev_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ev_search != null && !TextUtils.isEmpty(ev_search.getText().toString()) && ev_search.getText().toString().length() > 1) {
                    initViewModel();
                }else {
                    GetMovieListViewModel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
