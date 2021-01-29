package com.gowtham.moviesearchapplication.model;

import java.util.List;

public class MovieModel {

    private int page;
    private int total_result;
    private int total_pages;
    private List<MovieDetailsModel> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_result() {
        return total_result;
    }

    public void setTotal_result(int total_result) {
        this.total_result = total_result;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<MovieDetailsModel> getListMovies() {
        return results;
    }

    public void setListMovies(List<MovieDetailsModel> listMovies) {
        this.results = listMovies;
    }
}
