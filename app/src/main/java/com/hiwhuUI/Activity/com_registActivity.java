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

public class com_registActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_regist);
        final EditText sponsorName = (EditText)findViewById(R.id.name);
        final EditText phoneNum = (EditText)findViewById(R.id.tel);
        final EditText passwrod = (EditText)findViewById(R.id.pwd);
        final EditText introduction = (EditText)findViewById(R.id.description);
        Button registButton = (Button)findViewById(R.id.btn_com_regist);

        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = staticData.getUrl()+"/AddSponsorServlet";
                String name = sponsorName.getText().toString();
                String tel = phoneNum.getText().toString();
                String pwd = passwrod.getText().toString();
                String desc = introduction.getText().toString();
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
                    Toast.makeText(com_registActivity.this,"您已经成功注册！",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(com_registActivity.this,"注册失败！",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
