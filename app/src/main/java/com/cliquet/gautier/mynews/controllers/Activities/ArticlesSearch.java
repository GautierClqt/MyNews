package com.cliquet.gautier.mynews.controllers.Activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.Models.PojoArticleSearch.PojoArticleSearch;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;
import com.cliquet.gautier.mynews.Utils.OnBottomReachedListener;
import com.cliquet.gautier.mynews.controllers.Fragments.RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;


public class ArticlesSearch extends AppCompatActivity implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks2 {

    ArticlesElements articlesElements = new ArticlesElements();

    Response response = new Response();
    HashMap<String, String> searchQueries = new HashMap<>();

    private ArrayList<Articles> articles = new ArrayList<>();

    Gson gson = new Gson();

    RecyclerView recyclerView;

    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_search);

        Intent intent = getIntent();

        String jsonQueriesHM = intent.getStringExtra("hashmap");
        searchQueries = gson.fromJson(jsonQueriesHM, new TypeToken<HashMap<String, String>>(){}.getType());

        initRecyclerView();
        this.executeHttpRequestWithRetrofit();
    }

    //Actions
    private void executeHttpRequestWithRetrofit() {
        NYtimesCalls.fetchSearchArticles(this, searchQueries);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.activity_articles_search_recycler);
        adapter = new RecyclerViewAdapter(this, articles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                articlesElements.setCurrentPage(articlesElements.getCurrentPage()+1);
                searchQueries.put("page", String.valueOf(articlesElements.getCurrentPage()));
                executeHttpRequestWithRetrofit();
            }
        });
    }

    @Override
    public void onResponse(PojoArticleSearch pojoArticleSearch) {

        if (pojoArticleSearch != null) {
            response = pojoArticleSearch.getResponse();
        }
        articles = articlesElements.settingListsPojoArticleSearch(response);

        adapter.setArticles(articles);
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
}
