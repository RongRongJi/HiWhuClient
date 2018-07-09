package com.hiwhuUI.Activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hiwhu.hiwhuclient.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class stu_dataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_data);
        ImageView back = (ImageView) findViewById(R.id.stu_h_back);
        ImageView head = (ImageView) findViewById(R.id.stu_h_head);
        Button button1 = (Button) findViewById(R.id.stu_data_cancel) ;
        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.stu_data_unchecked);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.stu_data_checked);
        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.stu_data_taken);
        RelativeLayout relativeLayout4 = (RelativeLayout) findViewById(R.id.stu_data_store);
        //设置背景磨砂效果
        Glide.with(this).load(R.drawable.stu_data_back)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(back);
        //设置圆形头像
        Glide.with(this).load(R.drawable.stu_data_head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(head);
        //取消按钮
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new  Intent(stu_dataActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //待审核
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stu_dataActivity.this, ListActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });
        //待参加
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stu_dataActivity.this, ListActivity.class);
                intent.putExtra("id",2);
                startActivity(intent);
            }
        });
        //已参加
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stu_dataActivity.this, ListActivity.class);
                intent.putExtra("id",3);
                startActivity(intent);
            }
        });
        //收藏
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stu_dataActivity.this, ListActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);
            }
        });


    }

}
