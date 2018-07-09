package com.hiwhuUI.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hiwhu.hiwhuclient.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class com_describeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_describe);
        ImageView head = (ImageView) findViewById(R.id.img_com_describe_p1);
        Glide.with(this).load(R.drawable.com_data_head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(head);
    }
}
