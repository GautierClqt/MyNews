package com.cliquet.gautier.mynews.controllers.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.Models.ArticlesElements;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;
import com.cliquet.gautier.mynews.Utils.OnBottomReachedListener;
import com.cliquet.gautier.mynews.controllers.Fragments.RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;


public class ArticlesSearch extends AppCompatActivity implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    ArticlesElements articlesElements = new ArticlesElements();

    Response response = new Response();
    HashMap<String, String> searchQueries = new HashMap<>();

    private ArrayList<Articles> articles = new ArrayList<>();

    Gson gson = new Gson();

    RecyclerView recyclerView;
    TextView failTextView;
    ImageView logoImageView;

    RecyclerViewAdapter adapter;
    String jsonQueriesHM;

    boolean stopRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recycler);

        Intent intent = getIntent();

        failTextView = findViewById(R.id.activity_articles_search_failEditText);
        logoImageView = findViewById(R.id.activity_articles_search_imagelogo);

        jsonQueriesHM = intent.getStringExtra("hashmap");
        searchQueries = gson.fromJson(jsonQueriesHM, new TypeToken<HashMap<String, String>>(){}.getType());

        boolean stopRequest = articlesElements.getStopRequest();

        initRecyclerView();

        executeHttpRequestWithRetrofit();
    }

    //Actions
    private void executeHttpRequestWithRetrofit() {
        if(articlesElements.getCurrentPage() == 0) {
            updateUiWhenStartingHttpRequest();
        }
        NYtimesCalls.fetchArticles(this, jsonQueriesHM, 3);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.activity_articles_search_recycler);
        adapter = new RecyclerViewAdapter(this, articles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                stopRequest = articlesElements.getStopRequest();
                if(!stopRequest) {
                    articlesElements.setCurrentPage(articlesElements.getCurrentPage() + 1);
                    searchQueries.put("page", String.valueOf(articlesElements.getCurrentPage()));
                    jsonQueriesHM = gson.toJson(searchQueries);
                    executeHttpRequestWithRetrofit();
                }
            }
        });
    }

    private void updateUiWhenStartingHttpRequest(){
        failTextView.setVisibility(View.VISIBLE);
        failTextView.setText(R.string.waiting_request);
    }

    @Override
    public void onResponse(PojoMaster pojoMaster) {

        if (pojoMaster != null) {
            response = pojoMaster.getResponse();
        }
        if(response.getDocs().size() == 0){
            this.updateUiWhenStopingHttpRequest(getString(R.string.error_bad_request));

        }
        else {
            articles = articlesElements.settingListsPojoArticleSearch(response);
            recyclerView.setVisibility(View.VISIBLE);
            logoImageView.setVisibility(View.GONE);
            failTextView.setVisibility(View.GONE);
            adapter.setArticles(articles);
        }
    }

    @Override
    public void onFailure() {
        this.updateUiWhenStopingHttpRequest(getString(R.string.error_failure));
    }

    private void updateUiWhenStopingHttpRequest(String message){
        recyclerView.setVisibility(View.GONE);
        logoImageView.setVisibility(View.VISIBLE);
        failTextView.setVisibility(View.VISIBLE);
        failTextView.setText(message);
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
