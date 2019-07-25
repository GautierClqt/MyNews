package com.cliquet.gautier.mynews.controllers.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.Models.PojoTopStories.PojoMaster;

import com.cliquet.gautier.mynews.Models.PojoTopStories.Result;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

import java.util.List;

public class TopStoriesFragment extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    private int fragementPageNumber;

    private List<Result> result;

    private ArticlesElements articlesElements = new ArticlesElements();

    private RecyclerView recyclerView;
    private TextView failTextView;
    private ViewPager viewPager;

    public static TopStoriesFragment newInstance(int fragementPageNumber) {
        TopStoriesFragment mTopStoriesFragment = new TopStoriesFragment();
        Bundle args = new Bundle();
        args.putInt("fragment_page_number", fragementPageNumber);
        mTopStoriesFragment.setArguments(args);
        return mTopStoriesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragementPageNumber = getArguments().getInt("fragment_page_number", 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_top_stories, container, false);

        this.executeHttpRequestWithRetrofit();

        recyclerView = view.findViewById(R.id.fragment_top_stories_recyclerview);
        failTextView = view.findViewById(R.id.fragment_top_stories_failEditText);
        viewPager = view.findViewById(R.id.activity_main_viewpager);

        return view;
    }

    //Actions
    private void executeHttpRequestWithRetrofit() {

        this.updateUiWhenStartingHttpRequest();
        switch(fragementPageNumber) {
            case 0: NYtimesCalls.fetchTopStoriesArticles(this, "home", 0);
            //case 1: NYtimesCalls.fetchMostPopularArticles((NYtimesCalls.Callbacks3) this);
            case 1: NYtimesCalls.fetchTopStoriesArticles(this, "home", 0);
            case 2: NYtimesCalls.fetchTopStoriesArticles(this, "sports", 2);
        }
    }


    @Override
    public void onResponse(@Nullable PojoMaster pojoTopStories) {
        //getting all elements from the request and setting Elements object for further use
        if (pojoTopStories != null) {
            result = pojoTopStories.getResults();
        }
        List<Articles> mArticles = articlesElements.settingListsPojoTopStories(result);

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(this.getContext(), mArticles);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onFailure() {
        this.updateUiWhenStopingHttpRequest(getString(R.string.failure));
    }

    private void updateUiWhenStartingHttpRequest(){
    }

    private void updateUiWhenStopingHttpRequest(String response){
        recyclerView.setVisibility(View.GONE);
        failTextView.setVisibility(View.VISIBLE);
        failTextView.setText(response);
    }

    @Override
    public void onPreExecute() {
        failTextView.setText(R.string.onPreExecute);
    }

    @Override
    public void doInBackground() {
    }

    @Override
    public void onPostExecute(String succes) {
    }
}
