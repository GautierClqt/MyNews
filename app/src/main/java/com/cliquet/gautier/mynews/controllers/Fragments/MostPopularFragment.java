package com.cliquet.gautier.mynews.controllers.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.Models.PojoMostPopular.PojoMostPopular;
import com.cliquet.gautier.mynews.Models.PojoMostPopular.Results;
import com.cliquet.gautier.mynews.Models.PojoTopStories.PojoTopStories;
import com.cliquet.gautier.mynews.Models.PojoTopStories.Result;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MostPopularFragment extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks3 {

    List<Results> results;

    ArticlesElements articlesElements = new ArticlesElements();
    private List<Articles> articles = new ArrayList<>();

    @BindView(R.id.fragment_most_popular_recyclerview)
    RecyclerView recyclerView;

    RecyclerViewAdapter adapter;


    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, view);

        this.executeHttpRequestWithRetrofit();

        return view;
    }

    private void executeHttpRequestWithRetrofit() {
        this.updateUiWhenStartingHttpRequest();
        NYtimesCalls.fetchMostPopularArticles(this);
    }

    private void initRecyclerView() {
        adapter = new RecyclerViewAdapter(this.getContext(), articles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onResponse(@Nullable PojoMostPopular pojoMostPopular) {
        //getting all elements from the request and setting Elements object for further use
        if (pojoMostPopular != null) {
            results = pojoMostPopular.getResults();
        }
        articles = articlesElements.settingListsMostPopular(results);

        initRecyclerView();
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(String succes) {

    }

    private void updateUiWhenStartingHttpRequest(){
    }

    private void updateUiWhenStopingHttpRequest(String response){
    }
}
