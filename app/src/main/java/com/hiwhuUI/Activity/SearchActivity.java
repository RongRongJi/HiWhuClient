package com.hiwhuUI.Activity;

/**
 * created by 赵紫微
 * modified by 刘劭荣
 */
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.util.ListFragment;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toolbar跟menu
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        searchView.setQueryHint("请输入活动名");
        searchView.setIconifiedByDefault(false);
        SearchView.SearchAutoComplete text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        text.setHintTextColor(Color.WHITE);
        text.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
                //搜索活动
                transaction.replace(R.id.container_search_list, ListFragment.newInstance("搜索",query));
                transaction.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
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
}