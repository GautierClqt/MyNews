package com.cliquet.gautier.mynews.controllers.Activities;

import android.content.Intent;

import com.cliquet.gautier.mynews.Utils.NotificationWorker;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cliquet.gautier.mynews.Utils.PageAdapter;
import com.cliquet.gautier.mynews.R;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest uploadWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 24, TimeUnit.HOURS).build();

        WorkManager.getInstance().enqueue(uploadWorkRequest);

        //3 - Configure ViewPager
        this.configureViewPagerAndTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //call correct menu activity according to the selected menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_searchicon_item:
                Intent searchArticleIntent = new Intent(this, SearchQueriesSelection.class);
                startActivity(searchArticleIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureViewPagerAndTabs() {
        //1 - Get ViewPager from layout
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        //2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        //Get TabLayout form layout
        TabLayout tabs = findViewById(R.id.activity_main_tabs);
        //Glue TablLayout and ViewPage together
        tabs.setupWithViewPager(pager);
        //Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);

        pager.setCurrentItem(2);
        int TEXT = 32565412;

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int TEST = 5;
                switch(position) {
                    case 0: TEST = 0;
                    case 1: TEST = 1;
                    case 2: TEST = 2;
                    //default: TEST = 5;
                }
                int SUPERTEST = 99;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
