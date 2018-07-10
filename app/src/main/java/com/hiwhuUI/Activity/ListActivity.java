package com.hiwhuUI.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.util.ListFragment;

public class ListActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_test);
        toolbar.setTitle("");
        TextView textView = (TextView) findViewById(R.id.toolbar_test_text);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

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
                transaction.replace(R.id.container_list, ListFragment.newInstance("审核"));
                textView.setText("审核");
                break;
            case 6:
                transaction.replace(R.id.container_list, ListFragment.newInstance("发布历史"));
                textView.setText("发布历史");
                break;
            case 7:
                transaction.replace(R.id.container_list, ListFragment.newInstance("审核"));
                textView.setText("审核");
                break;
        }
        transaction.addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        finish();
    }


}