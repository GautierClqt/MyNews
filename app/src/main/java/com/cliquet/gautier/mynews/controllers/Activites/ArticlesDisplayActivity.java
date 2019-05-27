package com.cliquet.gautier.mynews.controllers.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.cliquet.gautier.mynews.R;

public class ArticlesDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceActivity) {
        super.onCreate(savedInstanceActivity);
        setContentView(R.layout.layout_articles_display);

        WebView urlArticle;
        urlArticle = findViewById(R.id.layout_item_article_webview);

        //get the extra url send from RecyclerViews
        String mUrlArticle = (String) getIntent().getSerializableExtra("Url_Article");

        urlArticle.loadUrl(mUrlArticle);

    }
}
