package com.hiwhuUI.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.util.navigationAdapter;
import com.hiwhuUI.Activity.util.navigationFragment;

import data.staticData;

public class MainActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private BottomNavigationView navigation;
    private ViewPager navigation_viewPager;
    private MenuItem menuItem;
    private navigationAdapter navigation_adapter;
    private int userType = staticData.getUserType();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.search:
                        Intent intent1 = new Intent();
                        intent1.setClass(MainActivity.this, SearchActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.add:
                        Intent intent2 = new Intent();
                        Log.e("inhere---","here");
                        intent2.setClass(MainActivity.this, com_updateActivity.class);
                        startActivity(intent2);
                        return true;
                }
                return false;
            }
        });
        textView = (TextView)findViewById(R.id.toolbar_main_text);
        textView.setText("活动");


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                navigation_viewPager.setCurrentItem(0);
                                textView.setText("活动");
                                toolbar.getMenu().findItem(R.id.search).setVisible(true);
                                return true;
                            case R.id.navigation_notice:
                                navigation_viewPager.setCurrentItem(1);
                                textView.setText("消息");
                                toolbar.getMenu().findItem(R.id.search).setVisible(false);
                                toolbar.getMenu().findItem(R.id.add).setVisible(false);
                                return true;
                            case R.id.navigation_user:
                                navigation_viewPager.setCurrentItem(2);
                                textView.setText("我的");
                                toolbar.getMenu().findItem(R.id.search).setVisible(false);
                                toolbar.getMenu().findItem(R.id.add).setVisible(false);

                                return true;
                        }
                        return false;
                    }
                });

        navigation_viewPager = (ViewPager) findViewById(R.id.navigation_viewPager);
        navigation_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        navigation_adapter = new navigationAdapter(getSupportFragmentManager());

        navigation_adapter.addFragment(navigationFragment.newInstance("主页"));
        navigation_adapter.addFragment(navigationFragment.newInstance("消息"));
        navigation_adapter.addFragment(navigationFragment.newInstance("我的"));
        navigation_viewPager.setAdapter(navigation_adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toolbar跟menu
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        if(userType==1){
            toolbar.getMenu().findItem(R.id.add).setVisible(false);
        }
        return true;
    }
}