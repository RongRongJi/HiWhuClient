package com.hiwhuUI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hiwhu.hiwhuclient.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import HttpConnect.HttpUtil;
import data.staticData;
import okhttp3.Call;
import okhttp3.Response;

public class data_editActivity extends AppCompatActivity {
    private String activity_id;
    private String ref_comment_id;
    private String student_id;
    private String ref_comment_content;
    private String fromUserID;
    private String data;
    final int COMMENT = 0;//写评论
    final int REF = 1;//回复评论
    private int type;

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
        //student_id = staticData.getStudentID();
        if(staticData.getUserType()==1) fromUserID=staticData.getStudentID();
        else fromUserID=staticData.getSponsorID();
        activity_id = getIntent().getStringExtra("activity_id");
        ref_comment_id = getIntent().getStringExtra("ref_comment_id");
        ref_comment_content = getIntent().getStringExtra("ref_comment_content");

        //接收上个活动并显示
        EditText et = (EditText) findViewById(R.id.edit_data);
        String name_pre = getIntent().getStringExtra("data");
        et.setHint(name_pre);
        et.setSelection(et.getText().length());

        if(ref_comment_id!=null){
            type = REF;
            et.setHint("回复："+ref_comment_content);
            title.setText("回复信息");
        }
        else if(activity_id!=null) {
            type = COMMENT;
            title.setText("写评论");
        }
        else title.setText("编辑资料");

        //确定
        Button bt2 = (Button)findViewById(R.id.btn2_data_edit);
        Button bt1 = (Button)findViewById(R.id.btn1_data_edit);
        //确定返回
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)findViewById(R.id.edit_data);
                data = editText.getText().toString();
                String content = null;
                try {
                    content = java.net.URLEncoder.encode(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(type==COMMENT){
                    String url = staticData.getUrl() + "/AddCommentServlet?fromUserID="+fromUserID
                            +"&content="+content+"&activityID="+activity_id;
                    HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if(s.equals("failed")){
                                Jump(0);
                            }else{
                                Jump(1);
                            }
                        }
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("error",e.toString());
                        }
                    });
                }else if(type==REF){
                    String url = staticData.getUrl() + "/ReplyCommentServlet?fromUserID="+fromUserID
                            +"&refCommentID="+ref_comment_id
                            +"&content="+content+"&activityID="+activity_id;
                    Log.e("url--",url);
                    HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if(s.equals("failed")){
                                Jump(0);
                            }else{
                                Jump(1);
                            }
                        }
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("error",e.toString());
                        }
                    });
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("data",data);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
        //取消返回
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    private void Jump(final int flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(0==flag){
                    Toast.makeText(data_editActivity.this,"发表失败",Toast.LENGTH_SHORT).show();
                }else if(1==flag){
                    Toast.makeText(data_editActivity.this,"发表成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
