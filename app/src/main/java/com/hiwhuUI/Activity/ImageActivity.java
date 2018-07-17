package com.hiwhuUI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hiwhu.hiwhuclient.R;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = (ImageView)findViewById(R.id.imageView);
        //显示图片？
    }

    public void imageOnClick(View view){
        switch (view.getId()){
            case R.id.button_return:
                Intent intent = new Intent(ImageActivity.this, com_updateActivity.class);
                startActivity(intent);
                break;
            case R.id.button_delete:
                com_updateActivity.UPDATE_OR_CHANGE = 1;
                //如何删除图片？
                break;
        }
    }
}
