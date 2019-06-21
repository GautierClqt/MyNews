package com.cliquet.gautier.mynews.controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cliquet.gautier.mynews.Models.PojoTopStories;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;

public class ArticlesSearch extends AppCompatActivity implements NetworkAsyncTask.Listeners, NYtimesCalls.Callbacks {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_search);

        this.executeHttpRequestWithRetrofit();
    }

    //Actions
    private void executeHttpRequestWithRetrofit() {
        NYtimesCalls.fetchArticle(this, "home");
    }

    private void initRecyclerView() {
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getContext(), mResults);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onResponse(PojoTopStories mPojoTopStories) {

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
