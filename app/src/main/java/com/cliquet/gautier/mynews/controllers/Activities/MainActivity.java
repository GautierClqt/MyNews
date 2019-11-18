package com.cliquet.gautier.mynews.controllers.Activities;

import android.content.Intent;
import com.cliquet.gautier.mynews.Utils.NotificationWorker;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest uploadWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 24, TimeUnit.HOURS).build();

        WorkManager.getInstance().enqueue(uploadWorkRequest);

        this.configureViewPagerAndTabs();
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
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
            case R.id.menu_searchicon_item: launchSearchOrNotificationActivity(0);
                return true;
            case R.id.menu_notifications_item: launchSearchOrNotificationActivity(1);
                return true;
            case R.id.menu_about_item: Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchSearchOrNotificationActivity(int activitynbr) {
        Intent intent = new Intent(this, SearchQueriesSelectionActivity.class);
        intent.putExtra("actitivy_called", activitynbr);
        startActivity(intent);
    }


    private void configureViewPagerAndTabs() {
        mPager = findViewById(R.id.activity_main_viewpager);
        mPager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        TabLayout tabs = findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(mPager);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        mPager.setCurrentItem(0);
    }

    @Override
    public void onBackPressed() {
        if(this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch(id){
            case R.id.activity_main_drawer_topstories : mPager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_mostpopular : mPager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_favorite : mPager.setCurrentItem(2);
                break;
            case R.id.activity_main_drawer_articlesearch : launchSearchOrNotificationActivity(0);
                break;
            case R.id.activity_main_drawer_notifications : launchSearchOrNotificationActivity(1);
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void configureToolbar() {
        this.mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void configureDrawerLayout() {
        this.mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        NavigationView mNavigationView = findViewById(R.id.activity_main_nav_view);

        mNavigationView.setNavigationItemSelectedListener(this);
    }
}

