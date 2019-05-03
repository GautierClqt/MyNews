package com.cliquet.gautier.mynews.controllers.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cliquet.gautier.mynews.Models.NYTopStories;
import com.cliquet.gautier.mynews.Models.Result;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TopStoriesFragment extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    @BindView(R.id.fragment_top_stories_button)
    TextView textView;

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    //Actions
    @OnClick(R.id.fragment_top_stories_button)
    public void submit() {
        this.executeHttpRequestWithRetrofit();
    }

    private void executeHttpRequestWithRetrofit() {
        this.updateUiWhenStartingHttpRequest();
        NYtimesCalls.fetchArticle(this, "science");
    }

    @Override
    public void onResponse(@Nullable List<Result> section) {
        if (section != null) this.updateUiWithListOfArticles(section);
    }

    @Override
    public void onFailure() {
        this.updateUiWhenStopingHttpRequest("ERROR");
    }


    private void updateUiWhenStartingHttpRequest(){
        this.textView.setText("Downloadind");
    }

    private void updateUiWhenStopingHttpRequest(String response){
        this.textView.setText(response);
    }

    private void updateUiWithListOfArticles(List<Result> section) {
        StringBuilder mStringBuilder = new StringBuilder();
        for (Result result : section){
            mStringBuilder.append("-"+result.getTitle()+"\n");
        }
        updateUiWhenStopingHttpRequest(mStringBuilder.toString());
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
