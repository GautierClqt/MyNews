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
import com.cliquet.gautier.mynews.Models.PojoTopStories.PojoTopStories;

import com.cliquet.gautier.mynews.Models.PojoTopStories.Result;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopStoriesFragment extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    List<Result> result;

    ArticlesElements articlesElements = new ArticlesElements();
    private List<Articles> articles = new ArrayList<>();

    @BindView(R.id.fragment_top_stories_recycler)
    RecyclerView recyclerView;

    RecyclerViewAdapter adapter;

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);

        this.executeHttpRequestWithRetrofit();

        return view;
    }

    //Actions
    private void executeHttpRequestWithRetrofit() {
        this.updateUiWhenStartingHttpRequest();
        NYtimesCalls.fetchTopStoriesArticles(this, "home");
    }


    private void initRecyclerView() {
        adapter = new RecyclerViewAdapter(this.getContext(), articles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onResponse(@Nullable PojoTopStories pojoTopStories) {
        //getting all elements from the request and setting Elements object for further use
        if (pojoTopStories != null) {
            result = pojoTopStories.getResults();
        }
        articles = articlesElements.settingListsPojoTopStories(result);

        initRecyclerView();
    }

    @Override
    public void onFailure() {
        this.updateUiWhenStopingHttpRequest("ERROR");
    }

    private void updateUiWhenStartingHttpRequest(){
    }

    private void updateUiWhenStopingHttpRequest(String response){
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
}
