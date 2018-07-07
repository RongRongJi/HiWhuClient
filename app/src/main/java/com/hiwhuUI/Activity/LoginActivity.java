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

import HttpConnect.HttpUtil;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView1 = (TextView) findViewById(R.id.text_reg_stu);
        TextView textView2 = (TextView) findViewById(R.id.text_reg_com);
        final EditText username = (EditText) findViewById(R.id.edit_username);
        final EditText password = (EditText) findViewById(R.id.edit_password);
        Button loginButton = (Button) findViewById(R.id.btn_login);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, stu_registActivity.class);
                startActivity(intent);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, com_registActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.23.1:8080/LoginServlet";
                String account = username.getText().toString();
                String pwd = password.getText().toString();
                try {
                    account = java.net.URLEncoder.encode(account, "UTF-8");
                    Log.e("uesrname---", account);
                    url = url + "?account=" + account + "&password=" + pwd;
                    Log.e("url----", url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.e("return---", s);
                        if (s.equals("succeed")) {
                            Jump(true);
                        } else {
                            Jump(false);
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("error",e.toString());
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
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
