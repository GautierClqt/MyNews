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

import com.cliquet.gautier.mynews.Models.PojoTopStories;
import com.cliquet.gautier.mynews.Models.Result;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Models.Elements;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopStoriesFragment extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    Elements elements = new Elements();
    Result result = new Result();
    List<Result> mResults;
    String mMultimedia;

    @BindView(R.id.fragment_top_stories_recycler)
    RecyclerView recyclerView;

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
        NYtimesCalls.fetchArticle(this, "home");
    }


    private void initRecyclerView() {
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getContext(), mResults);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onResponse(@Nullable PojoTopStories mPojoTopStories) {
        //getting all elements from the request and setting Elements object for further use
        if (mPojoTopStories != null) {
            mResults = mPojoTopStories.getResults();
            mMultimedia = result.getUrl();
        }
        elements.setResults(mResults);

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
