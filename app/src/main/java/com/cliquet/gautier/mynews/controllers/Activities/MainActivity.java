package com.cliquet.gautier.mynews.controllers.Activities;

import android.content.Intent;
import com.cliquet.gautier.mynews.Utils.NotificationWorker;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.cliquet.gautier.mynews.Utils.PageAdapter;
import com.cliquet.gautier.mynews.R;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest uploadWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 24, TimeUnit.HOURS).build();

        WorkManager.getInstance().enqueue(uploadWorkRequest);

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("notification")
//                .setContentText("xxx nouveaux articles sont disponibles")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //3 - Configure ViewPager
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
        Intent searchArticleIntent = new Intent(this, SearchQueriesSelection.class);
        switch (item.getItemId()) {
            case R.id.menu_searchicon_item:
                searchArticleIntent.putExtra("actitivy_called", 0);
                startActivity(searchArticleIntent);
                return true;
            case R.id.menu_Notifications_menu:
                searchArticleIntent.putExtra("activity_called", 1);
                startActivity(searchArticleIntent);
                return true;
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

        pager.setCurrentItem(0);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch(id){
            case R.id.activity_main_drawer_topstories :
                break;
            case R.id.activity_main_drawer_mostpopular :
                break;
            case R.id.activity_main_drawer_favorite :
                break;
            case R.id.activity_main_drawer_articlesearch :
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void configureToolbar() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        this.navigationView = findViewById(R.id.activity_main_nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }
}

