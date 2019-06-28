package com.cliquet.gautier.mynews.controllers.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.Models.Elements;
import com.cliquet.gautier.mynews.Models.PojoArticleSearch;
import com.cliquet.gautier.mynews.Models.Response;
import com.cliquet.gautier.mynews.Models.Result;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;
import com.cliquet.gautier.mynews.controllers.Fragments.RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArticlesSearch extends AppCompatActivity implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks2 {

    ArticlesElements articlesElements = new ArticlesElements();

    RecyclerView recyclerView;
    Elements elements = new Elements();
    Result result = new Result();
    Response response = new Response();
    HashMap<String, String> searchQueries = new HashMap<>();

    private ArrayList<ArrayList<String>> arraylists = new ArrayList<>();

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_search);

        Intent intent = getIntent();

        String jsonQueriesHM = intent.getStringExtra("hashmap");
        searchQueries = gson.fromJson(jsonQueriesHM, new TypeToken<HashMap<String, String>>(){}.getType());

        this.executeHttpRequestWithRetrofit();
    }

    //Actions
    private void executeHttpRequestWithRetrofit() {
        NYtimesCalls.getSearchedArticles(this, searchQueries);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_articles_search_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arraylists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResponse(PojoArticleSearch pojoArticleSearch) {

        if (pojoArticleSearch != null) {
            response = pojoArticleSearch.getResponse();
            //pojoArticleSearch.setResponse(response);
        }
        //elements.setResults(response);
        articlesElements.settingListsPojoArticleSearch(response);
        arraylists = articlesElements.getArraylists();

        initRecyclerView();
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
