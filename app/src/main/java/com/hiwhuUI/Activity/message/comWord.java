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
import com.hiwhuUI.Activity.stu_viewActivity;

import java.util.ArrayList;
import java.util.List;

import HttpConnect.GetCommentCount;
import entity.CommentWithActivity;

public class comWord extends AppCompatActivity {
    private List<comResult> wordList = new ArrayList<>();
    private GetCommentCount gcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_m_result);
        //更改标题栏
        TextView title = (TextView)findViewById(R.id.text_title) ;
        title.setText("收到的留言");
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
        ResultAdapter adapter = new ResultAdapter(comWord.this,R.layout.com_result_item,wordList);
        ListView listView = (ListView)findViewById(R.id.result_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(comWord.this,com_viewActivity.class);
                intent.putExtra("activity_id",gcc.commentCountList.get(position).getActivityID());
                startActivity(intent);
            }
        });
    }
    private void initComResult() {
        gcc = GetCommentCount.GetActivityInit();
        for (CommentWithActivity cwa : gcc.commentCountList) {
            comResult result = new comResult(cwa.getTitle(), R.drawable.jump1, String.valueOf(cwa.getCount()));
            wordList.add(result);
        }
    }
}
