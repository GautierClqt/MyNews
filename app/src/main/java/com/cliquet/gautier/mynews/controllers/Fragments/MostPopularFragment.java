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
import android.widget.TextView;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.Models.PojoMostPopular.PojoMostPopular;
import com.cliquet.gautier.mynews.Models.PojoMostPopular.Results;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

import java.util.List;

public class MostPopularFragment extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks3 {

    private List<Results> result;

    private ArticlesElements articlesElements = new ArticlesElements();

    private RecyclerView recyclerView;
    private TextView textView;

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);

        this.executeHttpRequestWithRetrofit();

        recyclerView = view.findViewById(R.id.fragment_most_popular_recyclerview);
        textView = view.findViewById(R.id.fragment_top_stories_failEditText);

        return view;
    }

    private void executeHttpRequestWithRetrofit() {
        this.updateUiWhenStartingHttpRequest();
        NYtimesCalls.fetchMostPopularArticles(this);
    }


    @Override
    public void onResponse(@Nullable PojoMostPopular pojoMostPopular) {
        //getting all elements from the request and setting Elements object for further use
        if (pojoMostPopular != null) {
            result = pojoMostPopular.getResults();
        }
        List<Articles> articles = articlesElements.settingListsMostPopular(result);

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(this.getContext(), articles);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onFailure() {
        this.updateUiWhenStopingHttpRequest(getString(R.string.failure));
    }

    @Override
    public void onPreExecute() {
        textView.setText(R.string.onPreExecute);

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
        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(response);
    }
}
