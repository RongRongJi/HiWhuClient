package com.hiwhuUI.Activity;

/**
 * created by 魏文含
 * modified by 王清玉
 */
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
    private String picPath = null;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        picPath = intent.getStringExtra("picPath");

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
        final Button forward = (Button)findViewById(R.id.button_forward);
        forward.setText("删除");
        forward.setVisibility(View.GONE);
        //删除图片
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com_updateActivity.UPDATE_OR_DELETE = 3;
                //textView = (TextView)findViewById(R.id.text_image);
                //textView.setText("点击上传封面图片");
                finish();
            }
        });

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
}

