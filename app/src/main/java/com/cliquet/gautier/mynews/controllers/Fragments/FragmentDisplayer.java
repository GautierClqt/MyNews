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
import android.widget.Button;
import android.widget.TextView;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;

import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;
import com.cliquet.gautier.mynews.Models.PojoCommon.Results;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FragmentDisplayer extends Fragment implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    private int mFragmentPageNumber;

    private List<Results> mResults;

    private ArticlesElements articlesElements = new ArticlesElements();

    private RecyclerView recyclerView;
    private TextView failTextView;
    private Button refreshButton;

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
        mFragmentPageNumber = getArguments().getInt("fragment_page_number", 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recycler, container, false);

        initViews(view);

        this.executeHttpRequestWithRetrofit();
        return view;
    }

    private void executeHttpRequestWithRetrofit() {

        this.updateUiWhenStartingHttpRequest();
        switch(mFragmentPageNumber) {
            case 0: NYtimesCalls.fetchArticles(this, "home",  0);
            break;
            case 1: NYtimesCalls.fetchArticles(this, "", 1);
            break;
            case 2: NYtimesCalls.fetchArticles(this, "sports", 0);
            break;
            default: break;
        }
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.fragment_recycler_recyclerview);
        failTextView = view.findViewById(R.id.fragment_recycler_failEditText);
        refreshButton = view.findViewById(R.id.fragment_recycler_refreshButton);
    }


    @Override
    public void onResponse(@Nullable PojoMaster mPojoMaster) {

        recyclerView.setVisibility(VISIBLE);

        //getting all elements from the request and setting Elements object for further use
        int size = 0;
        if (mPojoMaster != null) {
            mResults = mPojoMaster.getResults();
            size = mResults.size();
        }

        if(size == 0) {
            this.updateUiWhenStopingHttpRequest(getString(R.string.error_no_article));
        }

        List<Articles> mArticles = null;
        switch (mFragmentPageNumber) {
            case 0:
                mArticles = articlesElements.settingListsPojoTopStories(mResults);
                break;
            case 1:
                mArticles = articlesElements.settingListsMostPopular(mResults);
                break;
            case 2:
                mArticles = articlesElements.settingListsPojoTopStories(mResults);
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
        this.updateUiWhenStopingHttpRequest(getString(R.string.error_failure));
    }

    private void updateUiWhenStartingHttpRequest(){
        failTextView.setText(R.string.waiting_request);
    }

    private void updateUiWhenStopingHttpRequest(String message){
        recyclerView.setVisibility(GONE);
        failTextView.setVisibility(VISIBLE);
        failTextView.setText(message);
        refreshButton.setVisibility(VISIBLE);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    @Override
    public void onPreExecute() {
        failTextView.setText(R.string.waiting_request);
    }

    @Override
    public void doInBackground() {
    }

    @Override
    public void onPostExecute(String succes) {
    }
}
