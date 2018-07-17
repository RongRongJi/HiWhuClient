package com.hiwhuUI.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hiwhu.hiwhuclient.R;

import java.io.IOException;

import HttpConnect.HttpUtil;
import data.staticData;
import okhttp3.Call;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    EditText name;
    EditText number;
    EditText phone;
    EditText qq;
    final int APPLIED = 0;
    final int SUCCEED = 1;
    final int FAILED = 2;
    final int TIMEOUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //更改标题栏
        TextView title = (TextView)findViewById(R.id.text_title) ;
        title.setText("报名");
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

        name = (EditText)findViewById(R.id.p1_edit_stu_num);
        number = (EditText)findViewById(R.id.p2_edit_stu_username);
        phone = (EditText)findViewById(R.id.p3_edit_stu_password);
        qq = (EditText)findViewById(R.id.p4_edit_stu_ident);
        Button submit =(Button)findViewById(R.id.btn_confirm);
        Button cancel = (Button)findViewById(R.id.button_backward);
        final String activityID = getIntent().getStringExtra("activity_id");
        //Toast.makeText(SignupActivity.this, activity_id, Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = staticData.getUrl()+"/StuApplyActivityServlet";
                String studentID = number.getText().toString();
                String studentName = name.getText().toString();
                String phoneNum = phone.getText().toString();
                String qqNum = qq.getText().toString();
                try{
                    studentName = java.net.URLEncoder.encode(studentName,"UTF-8");
                    Log.e("uesrname---",studentName);
                    url = url+"?studentID="+studentID+"&activityID="+activityID+"&studentName="+studentName
                            +"&phoneNum="+phoneNum+"&qqNum="+qqNum;
                    Log.e("url----",url);
                }catch(Exception e){
                    e.printStackTrace();
                }
                HttpUtil.sendOkHttpRequest(url,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.e("return---",s);
                        if(s.equals("applied")){
                            Jump(APPLIED);
                        }else if(s.equals("succeed")){
                            Jump(SUCCEED);
                        }else if(s.equals("timeout")){
                            Jump(TIMEOUT);
                        }else{
                            Jump(FAILED);
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                });
            }
        });
    }

    private void Jump(final int flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (flag){
                    case APPLIED:
                        Toast.makeText(SignupActivity.this,"您已申请该活动！",Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCEED:
                        Toast.makeText(SignupActivity.this,"您已申请成功！",Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case FAILED:
                        Toast.makeText(SignupActivity.this,"活动申请失败！",Toast.LENGTH_SHORT).show();
                        break;
                    case TIMEOUT:
                        Toast.makeText(SignupActivity.this,"报名已截止！",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}