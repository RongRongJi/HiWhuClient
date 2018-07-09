package com.hiwhuUI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hiwhu.hiwhuclient.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class com_dataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_data);
        ImageView back = (ImageView) findViewById(R.id.com_h_back);
        ImageView head = (ImageView) findViewById(R.id.com_h_head);
        Button button1 = (Button) findViewById(R.id.btn_com_data_cancel) ;
        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.com_data_describe);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.com_data_unchecked);
        //RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.com_data_review);
        RelativeLayout relativeLayout4 = (RelativeLayout) findViewById(R.id.com_data_history);
        //设置背景磨砂效果
        Glide.with(this).load(R.drawable.stu_data_back)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(back);
        //设置圆形头像
        Glide.with(this).load(R.drawable.com_data_head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(head);
        //社团简介跳转
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com_dataActivity.this,com_describeActivity.class);
                startActivity(intent);
            }
        });
        //取消按钮
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new  Intent(com_dataActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //审核报名人员
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com_dataActivity.this, ListActivity.class);
                intent.putExtra("id",5);
                startActivity(intent);
            }
        });
        //活动回顾
        //发布历史
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com_dataActivity.this, ListActivity.class);
                intent.putExtra("id",6);
                startActivity(intent);
            }
        });

    }

}
