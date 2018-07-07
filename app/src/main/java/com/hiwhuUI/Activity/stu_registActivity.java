package com.hiwhuUI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.hiwhu.hiwhuclient.R;

import java.io.IOException;
import java.net.*;

import HttpConnect.HttpUtil;
import data.staticData;
import okhttp3.Call;
import okhttp3.Response;

public class stu_registActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_regist);

        final EditText stuIdtext = (EditText) findViewById(R.id.p1_edit_stu_num);
        final EditText userNametext = (EditText) findViewById(R.id.p2_edit_stu_username);
        final EditText passwordText = (EditText) findViewById(R.id.p3_edit_stu_password);
        Button button1 = (Button) findViewById(R.id.btn_stu_regist);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = staticData.getUrl()+"/AddStudentServlet";
                String studentId = stuIdtext.getText().toString();
                String username = userNametext.getText().toString();
                String password = passwordText.getText().toString();
                try{
                    username = java.net.URLEncoder.encode(username,"UTF-8");
                    Log.e("uesrname---",username);
                    url = url+"?studentID="+studentId+"&userName="+ username +"&password="+password;
                    Log.e("url----",url);
                }catch(Exception e){
                    e.printStackTrace();
                }
               HttpUtil.sendOkHttpRequest(url,new okhttp3.Callback(){
                   @Override
                   public void onResponse(Call call, Response response) throws IOException {
                       String s = response.body().string();
                       Log.e("return---",s);
                       if(s.equals("succeed\r\n")){
                           Jump(true);
                       }else{
                           Jump(false);
                       }
                   }

                   @Override
                   public void onFailure(Call call, IOException e) {
                   }
               });

            }
        });
    }

    public void Jump(final boolean flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(flag){
                    Toast.makeText(stu_registActivity.this,"您已经成功注册！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(stu_registActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(stu_registActivity.this,"注册失败！",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
