package com.cliquet.gautier.mynews.controllers.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.OnBottomReachedListener;
import com.cliquet.gautier.mynews.controllers.Fragments.RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.VISIBLE;

public class ArticlesSearch extends AppCompatActivity implements NYtimesCalls.Callbacks {

    ArticlesElements mArticlesElements = new ArticlesElements();

    Response mResponse = new Response();
    HashMap<String, String> mSearchQueries = new HashMap<>();

    private ArrayList<Articles> mArticles = new ArrayList<>();

    Gson mGson = new Gson();

    RecyclerView recyclerView;
    TextView failTextView;
    Button refreshButton;

    RecyclerViewAdapter mAdapter;
    String mJsonQueriesHM;

    boolean mStopRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recycler);

        Intent intent = getIntent();

        initViews();

        mJsonQueriesHM = intent.getStringExtra("hashmap");
        mSearchQueries = mGson.fromJson(mJsonQueriesHM, new TypeToken<HashMap<String, String>>(){}.getType());

        initRecyclerView();

        executeHttpRequestWithRetrofit();
    }

    private void executeHttpRequestWithRetrofit() {
        if(mArticlesElements.getCurrentPage() == 0) {
            updateUiWhenStartingHttpRequest();
        }
        NYtimesCalls.fetchArticles(this, mJsonQueriesHM, 2);
    }

    private void initRecyclerView() {
        mAdapter = new RecyclerViewAdapter(this, mArticles);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                //the request will be send only if the last reached item is not the last article from the previous request result
                mStopRequest = mArticlesElements.getStopRequest();
                if(!mStopRequest) {
                    mArticlesElements.setCurrentPage(mArticlesElements.getCurrentPage() + 1);
                    mSearchQueries.put("page", String.valueOf(mArticlesElements.getCurrentPage()));
                    mJsonQueriesHM = mGson.toJson(mSearchQueries);
                    executeHttpRequestWithRetrofit();
                }
            }
        });
    }

    private void updateUiWhenStartingHttpRequest(){
        failTextView.setVisibility(View.VISIBLE);
        failTextView.setText(R.string.waiting_request);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.activity_articles_search_recycler);
        failTextView = findViewById(R.id.activity_articles_search_failEditText);
        refreshButton = findViewById(R.id.activity_articles_search_refreshButton);
    }

    @Override
    public void onResponse(PojoMaster pojoMaster) {

        if (pojoMaster != null) {
            mResponse = pojoMaster.getResponse();
        }
        if(mResponse.getDocs().size() == 0){
            this.updateUiWhenStopingHttpRequest(getString(R.string.error_bad_request));

        }
        else {
            mArticles = mArticlesElements.settingListsPojoArticleSearch(mResponse);
            recyclerView.setVisibility(View.VISIBLE);
            failTextView.setVisibility(View.GONE);
            refreshButton.setVisibility(View.GONE);
            mAdapter.setArticles(mArticles);
        }
    }

    @Override
    public void onFailure() {
        this.updateUiWhenStopingHttpRequest(getString(R.string.error_failure));
    }

    private void updateUiWhenStopingHttpRequest(String message){
        recyclerView.setVisibility(View.GONE);
        failTextView.setVisibility(View.VISIBLE);
        failTextView.setText(message);
        refreshButton.setVisibility(VISIBLE);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeHttpRequestWithRetrofit();
            }
        });
    }
}
