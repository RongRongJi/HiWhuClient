package com.hiwhuUI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.hiwhu.hiwhuclient.R;

public class stu_registActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_regist);

        Button button1 = (Button) findViewById(R.id.btn_stu_regist);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(stu_registActivity.this,"您已经成功注册！",Toast.LENGTH_LONG).show();
            }
        });
    }
}
