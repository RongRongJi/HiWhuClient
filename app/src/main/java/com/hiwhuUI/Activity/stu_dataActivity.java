package com.hiwhuUI.Activity;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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
        //设置背景磨砂效果
        Glide.with(this).load(R.drawable.stu_data_back)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(back);
        //设置圆形头像
        Glide.with(this).load(R.drawable.stu_data_head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(head);



    }

}
