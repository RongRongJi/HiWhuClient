package com.hiwhuUI.Activity;

/**
 * created by 赵紫微
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.util.ListFragment;

import java.util.List;

import HttpConnect.GetActivityBySponsorID;
import data.staticData;
import entity.Activity;

public class ListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_data_edit);
        toolbar.setTitle("");
        TextView textView = (TextView) findViewById(R.id.toolbar_data_edit_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction transaction = manager.beginTransaction();
        int id = getIntent().getIntExtra("id", 0);
        switch (id){
            case 1:
                transaction.replace(R.id.container_list, ListFragment.newInstance("待审核"));
                textView.setText("待审核");
                break;
            case 2:
                transaction.replace(R.id.container_list, ListFragment.newInstance("待参加"));
                textView.setText("待参加");
                break;
            case 3:
                transaction.replace(R.id.container_list, ListFragment.newInstance("已参加"));
                textView.setText("已参加");
                break;
            case 4:
                transaction.replace(R.id.container_list, ListFragment.newInstance("收藏"));
                textView.setText("收藏");
                break;
            case 5:
                transaction.replace(R.id.container_list, ListFragment.newInstance("审核活动"),"5");
                textView.setText("审核活动");
                break;
            case 6:
                transaction.replace(R.id.container_list, ListFragment.newInstance("发布历史"),"6");
                textView.setText("发布历史");
                break;
            case 7:
                textView.setText("审核人员");
                String activity_id = getIntent().getStringExtra("activity_id");
                transaction.replace(R.id.container_list, ListFragment.newInstance(activity_id));
                break;
            case 8:
                transaction.replace(R.id.container_list, ListFragment.newInstance("搜索"));
                textView.setText("搜索");
                break;
        }
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home){
            finish();
            return true;
        }
        else return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null) return;
        Fragment f = manager.findFragmentByTag("6");
        if(f!=null) {
            data.putExtra("tag",6);
            f.onActivityResult(requestCode, resultCode, data);
        }
        else {
            f = manager.findFragmentByTag("5");
            if(f!=null) {
                data.putExtra("tag",5);
                f.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}