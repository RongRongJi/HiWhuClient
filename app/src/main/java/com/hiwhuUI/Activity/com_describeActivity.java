package com.hiwhuUI.Activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hiwhu.hiwhuclient.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class com_describeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_describe);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.com_describe_p2);
        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.com_describe_p3);

        ImageView head = (ImageView) findViewById(R.id.imag_com_describe_p1);
        //设置圆形头像
        Glide.with(this).load(R.drawable.com_data_head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(head);
        //更改头像
        //Intent intent = new Intent(Intent.ACTION_PICK,
            //    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           //     startActivityForResult(intent, IMAGE);
        //社团名称
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //联系方式
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
