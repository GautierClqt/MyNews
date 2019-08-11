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

import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;
import com.cliquet.gautier.mynews.Models.PojoCommon.Results;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

import java.util.List;

public class FragmentDisplayer extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    private int fragmentPageNumber;

    private List<Results> results;

    private ArticlesElements articlesElements = new ArticlesElements();

    private RecyclerView recyclerView;
    private TextView failTextView;
    private ViewPager viewPager;

    public static FragmentDisplayer newInstance(int fragmentPageNumber) {
        FragmentDisplayer mFragmentDisplayer = new FragmentDisplayer();

        Bundle args = new Bundle();
        args.putInt("fragment_page_number", fragmentPageNumber);
        mFragmentDisplayer.setArguments(args);
        return mFragmentDisplayer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentPageNumber = getArguments().getInt("fragment_page_number", 0);
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
        switch(fragmentPageNumber) {
            case 0: NYtimesCalls.fetchArticles(this, "home",  fragmentPageNumber);
            break;
            case 1: NYtimesCalls.fetchArticles(this, "", fragmentPageNumber);
            //case 1: NYtimesCalls.fetchArticles(this, "business", fragmentPageNumber);
            break;
            case 2: NYtimesCalls.fetchArticles(this, "sports", fragmentPageNumber);
            break;
            default: break;
        }
    }


    @Override
    public void onResponse(@Nullable PojoMaster mPojoMaster) {
        //getting all elements from the request and setting Elements object for further use
        if (mPojoMaster != null) {
            results = mPojoMaster.getResults();
        }

        List<Articles> mArticles = null;
        switch (fragmentPageNumber) {
            case 0:
                mArticles = articlesElements.settingListsPojoTopStories(results);
                break;
            case 1:
                mArticles = articlesElements.settingListsMostPopular(results);
//            case 1: call = nYTimesService.getTopStories(section);
                break;
            case 2:
                mArticles = articlesElements.settingListsPojoTopStories(results);
                break;
            default:
                break;
        }

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