package com.hiwhuUI.Activity.message;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.com_viewActivity;
import com.hiwhuUI.Activity.function.RefreshableView;

import java.util.ArrayList;
import java.util.List;

import HttpConnect.GetRefCount;
import entity.CommentWithActivity;

public class stuReply extends AppCompatActivity {

        private List<comResult> RemindList = new ArrayList<>();
        private GetRefCount grc;
        ResultAdapter adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            RemindList = new ArrayList<>();
            setContentView(R.layout.activity_com_m_result);
            //更改标题栏
            TextView title = (TextView)findViewById(R.id.text_title) ;
            title.setText("评论回复");
            Button back = (Button)findViewById(R.id.button_backward);
            back.setText("返回");
            //返回消息主页
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Button forward = (Button)findViewById(R.id.button_forward);
            forward.setText(null);
            //隐藏默认标题栏
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.hide();
            }

            initComResult();
            adapter = new ResultAdapter(stuReply.this,R.layout.com_result_item,RemindList);
            ListView listView = (ListView)findViewById(R.id.result_list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(stuReply.this,com_viewActivity.class);
                    intent.putExtra("activity_id",grc.commentCountList.get(position).getActivityID());
                    startActivity(intent);
                }
            });

            //下拉刷新
            final RefreshableView refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
            refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
                @Override
                public void onRefresh() {
                    initComResult();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    refreshableView.finishRefreshing();
                }
            }, 0);
        }
        private void initComResult(){
            RemindList = new ArrayList<>();
            grc = GetRefCount.GetActivityInit();
            for(CommentWithActivity cwa : grc.commentCountList){
                comResult result = new comResult(cwa.getTitle(),R.drawable.jump1,String.valueOf(cwa.getCount()));
                RemindList.add(result);
            }
        }
}
