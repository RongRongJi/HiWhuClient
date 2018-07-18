package com.hiwhuUI.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.hiwhu.hiwhuclient.R;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        String picPath = intent.getStringExtra("picPath");

        //更改标题栏
        TextView title = (TextView)findViewById(R.id.text_title) ;
        title.setText("封面图片");
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
        forward.setText("删除");

        //隐藏默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }


        imageView = (ImageView)findViewById(R.id.imageView);
        //显示图片
        //Intent intent = getIntent();
        //String picPath = intent.getStringExtra("picPath");
        Log.d("ImageActivity",picPath);
        Uri uri = Uri.parse((String) picPath);
        imageView.setImageURI(uri);
    }

    public void imageOnClick(View view){
        switch (view.getId()){
            case R.id.button_backward:
                Intent intent = new Intent(ImageActivity.this, com_updateActivity.class);
                startActivity(intent);
                break;
            case R.id.button_forward:
                com_updateActivity.UPDATE_OR_CHANGE = 1;
                //删除图片
                //Intent intent2 = new Intent(ImageActivity.this,com_updateActivity.class);
                //picPath = null;
                //intent2.putExtra("deletePath",picPath);
                break;
        }
    }
}

