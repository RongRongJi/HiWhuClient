package com.hiwhuUI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;

import data.staticData;

public class data_editActivity extends AppCompatActivity {
    private String activity_id;
    private String ref_comment_id;
    private String student_id;
    private String ref_comment_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_data_edit);
        toolbar.setTitle("");
        TextView title = (TextView) findViewById(R.id.toolbar_data_edit_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //发表评论需要 student_id,activity_id; 发表回复需要 student_id,activity_id,ref_comment_id;
        //发表回顾需要 sponsor_id,activity_id; String sponsor_id = staticData.getSponsorID();
        student_id = staticData.getStudentID();
        activity_id = getIntent().getStringExtra("activity_id");
        ref_comment_id = getIntent().getStringExtra("ref_comment_id");
        ref_comment_content = getIntent().getStringExtra("ref_comment_content");

        //接收上个活动并显示
        EditText et = (EditText) findViewById(R.id.edit_data);
        String name_pre = getIntent().getStringExtra("data");
        et.setHint(name_pre);
        et.setSelection(et.getText().length());

        if(ref_comment_id!=null){
            et.setHint("回复："+ref_comment_content);
            title.setText("回复信息");
        }
        else if(activity_id!=null) title.setText("写评论");
        else title.setText("编辑资料");

        //确定
        Button bt2 = (Button)findViewById(R.id.btn2_data_edit);
        Button bt1 = (Button)findViewById(R.id.btn1_data_edit);
        //确定返回
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText editText = (EditText)findViewById(R.id.edit_data);
                String data = editText.getText().toString();
                intent.putExtra("data",data);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        //取消返回
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(data_editActivity.this,com_describeActivity.class);
                startActivity(intent);
            }
        });
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
