package com.cliquet.gautier.mynews.controllers.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;

import com.cliquet.gautier.mynews.R;

public class DisplaySelectedArticleActivity extends AppCompatActivity {

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
