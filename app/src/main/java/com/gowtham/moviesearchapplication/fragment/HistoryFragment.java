package com.gowtham.moviesearchapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.gowtham.moviesearchapplication.utils.LogUtils;
import com.gowtham.moviesearchapplication.view_model.HistoryActivityViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fr agment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    private static final String TAG = "HistoryFragment";
    private RecyclerViewAdapter adapter;

    @BindView(R.id.listRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvnoresult)
    TextView tvNoResult;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment HistoryFragment.
     */
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.log(TAG, "onResume called");
        initViewModel();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.log(TAG, "onViewCreated called");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Initialize and request to get the data through view model.
     */
    private void initViewModel() {
        LogUtils.log(TAG,"historyfragment initViewModel called : ");
        HistoryActivityViewModel historyActivityViewModel = ViewModelProviders.of(this).get(HistoryActivityViewModel.class);
        historyActivityViewModel.getMovieDataList();

        final Observer<List<MovieDetailsModel>> listObserver = new Observer<List<MovieDetailsModel>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetailsModel> updatedList) {

                LogUtils.log(TAG,"historyfragment onChanged called : updatedList : " + updatedList);
                if(updatedList != null && updatedList.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNoResult.setVisibility(View.GONE);
                    adapter.setUpdatedData(updatedList);
                    recyclerView.setAdapter(adapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvNoResult.setVisibility(View.VISIBLE);
                }
            }
        };
        historyActivityViewModel.getMovieDataList().observe(this, listObserver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtils.log(TAG, "onCreateView called");
        View rootView  = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, rootView);
        setRecyclerViewAdapter();
        return rootView;
    }

    /**
     * Setting the adapter for recycler view
     */
    private void setRecyclerViewAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity(), null, new IHandleRowClick() {
            @Override
            public void handleRowClick(MovieDetailsModel model) {
                LogUtils.log(TAG, "====model title :" + model.getTitle() + ", release date  :" +model.getRelease_date());
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConstants.DATA, model.toJSONString());
                intent.putExtra(AppConstants.FROM,1);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

    }
}
