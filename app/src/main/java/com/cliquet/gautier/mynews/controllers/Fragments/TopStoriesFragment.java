package com.cliquet.gautier.mynews.controllers.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class TopStoriesFragment extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    private List<Result> result;

    private ArticlesElements articlesElements = new ArticlesElements();
    private List<Articles> articles = new ArrayList<>();

    private RecyclerView recyclerView;

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_top_stories, container, false);

        this.executeHttpRequestWithRetrofit();

        recyclerView = view.findViewById(R.id.fragment_top_stories_recycler);

        return view;
    }

    //Actions
    private void executeHttpRequestWithRetrofit() {
        this.updateUiWhenStartingHttpRequest();
        NYtimesCalls.fetchTopStoriesArticles(this, "home");
    }


    @Override
    public void onResponse(@Nullable PojoTopStories pojoTopStories) {
        //getting all elements from the request and setting Elements object for further use
        if (pojoTopStories != null) {
            result = pojoTopStories.getResults();
        }
        articles = articlesElements.settingListsPojoTopStories(result);

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(this.getContext(), articles);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
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
