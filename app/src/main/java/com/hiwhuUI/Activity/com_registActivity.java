package com.hiwhuUI.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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

public class com_registActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_regist);

        final EditText sponsorName = (EditText)findViewById(R.id.name);
        final EditText phoneNum = (EditText)findViewById(R.id.tel);
        final EditText password = (EditText)findViewById(R.id.pwd);
        final EditText introduction = (EditText)findViewById(R.id.description);
        final EditText Repassword = (EditText)findViewById(R.id.confirm_pwd) ;
        Button registButton = (Button)findViewById(R.id.btn_com_regist);

        //更改标题栏
        TextView title = (TextView)findViewById(R.id.text_title) ;
        title.setText("注册");
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



        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sponsorName.getText().toString();
                String tel = phoneNum.getText().toString();
                String pwd = password.getText().toString();
                String desc = introduction.getText().toString();
                String rpwd = Repassword.getText().toString();
                if(name.length() == 0 && tel.length() != 0 && desc.length() != 0 && pwd.length() != 0 && pwd.equals(rpwd)
                        ||name.length() != 0 && tel.length() == 0 && desc.length() != 0 && pwd.length() != 0 && pwd.equals(rpwd)
                        ||name.length() != 0 && tel.length() != 0 && desc.length() == 0 && pwd.length() != 0 && pwd.equals(rpwd)
                        ||name.length() != 0 && tel.length() != 0 && desc.length() != 0 && pwd.length() == 0 && pwd.equals(rpwd)) {
                    Toast.makeText(com_registActivity.this,"请检查是否还有未填写信息！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(name.length() != 0 && tel.length() != 0 && desc.length() != 0 && pwd.length() != 0 && !pwd.equals(rpwd)){
                    Toast.makeText(com_registActivity.this,"密码输入不一致！",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    String url = staticData.getUrl()+"/AddSponsorServlet";
                    try{
                        name = java.net.URLEncoder.encode(name,"UTF-8");
                        desc = java.net.URLEncoder.encode(desc,"UTF-8");
                        url = url+"?sponsorName="+name+"&phoneNum="+ tel +"&password="+pwd+
                                "&introduction="+desc;
                        Log.e("url----",url);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    HttpUtil.sendOkHttpRequest(url,new okhttp3.Callback(){
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            Log.e("return---",s);
                            if(s.contains("succeed")){
                                Jump(1);
                            }else{
                                Jump(3);
                            }
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                    });
                }
            }
        });
    }

    public void Jump(final int flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(flag == 1){
                    Toast.makeText(com_registActivity.this,"您已经成功注册！",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(com_registActivity.this,"注册失败！",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
