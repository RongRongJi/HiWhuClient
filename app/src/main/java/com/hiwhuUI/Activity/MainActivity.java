package com.hiwhuUI.Activity;

/**
 * created by 赵紫微
 * modified by 刘劭荣
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;


import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.util.navigationFragment;

import java.util.Timer;
import java.util.TimerTask;

import data.staticData;

public class MainActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private BottomNavigationView navigation;
    private int userType = staticData.getUserType();
    private TextView textView;
    private FragmentManager fragmentManager;
    private int current;
    private navigationFragment home;
    private navigationFragment notice;
    private navigationFragment user;
    private boolean isExit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        textView = (TextView) findViewById(R.id.toolbar_main_text);
        textView.setText("活动");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.search:
                        Intent intent1 = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.add:
                        Intent intent2 = new Intent(MainActivity.this, com_updateActivity.class);
                        startActivity(intent2);
                        return true;
                }
                return false;
            }
        });


        home = navigationFragment.newInstance("主页");
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container_main, home).show(home).commit();
        current = 0;

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                if (current == 0) return true;
                                if(staticData.getUserType()==2){
                                    staticData.setSponsorCanOpera(false);
                                }
                                current = 0;
                                if(notice!=null) transaction.hide(notice);
                                if(user!=null) transaction.hide(user);
                                transaction.show(home).commit();
                                textView.setText("活动");
                                toolbar.getMenu().findItem(R.id.search).setVisible(true);
                                if (userType == 2) {
                                    toolbar.getMenu().findItem(R.id.add).setVisible(true);
                                }
                                return true;

                            case R.id.navigation_notice:
                                if (current == 1) return true;
                                else current = 1;
                                if(staticData.getUserType()==2){
                                    staticData.setSponsorCanOpera(false);
                                }
                                if(home!=null) transaction.hide(home);
                                if(user!=null) transaction.hide(user);
                                if (notice == null) {
                                    notice = navigationFragment.newInstance("消息");
                                    transaction.add(R.id.container_main,notice).commit();
                                }
                                else transaction.show(notice).commit();
                                textView.setText("消息");
                                toolbar.getMenu().findItem(R.id.search).setVisible(false);
                                toolbar.getMenu().findItem(R.id.add).setVisible(false);
                                return true;

                            case R.id.navigation_user:
                                if (current == 2) return true;
                                else current = 2;
                                if(staticData.getUserType()==2){
                                    staticData.setSponsorCanOpera(true);
                                }
                                if(home!=null) transaction.hide(home);
                                if(notice!=null) transaction.hide(notice);
                                if (user == null) {
                                    user = navigationFragment.newInstance("我的");
                                    transaction.add(R.id.container_main,user).commit();
                                }
                                else transaction.show(user).commit();
                                textView.setText("我的");
                                toolbar.getMenu().findItem(R.id.search).setVisible(false);
                                toolbar.getMenu().findItem(R.id.add).setVisible(false);
                                return true;
                        }
                        return false;
                    }
                });
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(!isExit) {
                isExit = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            } else {
                moveTaskToBack(false);
                //finish();
            }
        }
        return false;
    }
}