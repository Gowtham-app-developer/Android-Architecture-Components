package com.gowtham.moviesearchapplication;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gowtham.moviesearchapplication.adapter.ViewPagerAdapter;
import com.gowtham.moviesearchapplication.fragment.HistoryFragment;
import com.gowtham.moviesearchapplication.fragment.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Seting up the adapter for view pager,
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SearchFragment.newInstance(), getResources().getString(R.string.tab_search_movie));
        adapter.addFragment(HistoryFragment.newInstance(), getResources().getString(R.string.tab_history));
        viewPager.setAdapter(adapter);
    }
}
