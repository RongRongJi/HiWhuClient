package com.hiwhuUI.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hiwhu.hiwhuclient.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.transform.OutputKeys;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class com_describeActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private  PopupWindow pop = null;//弹窗
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_describe);

        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.com_describe_p1);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.com_describe_p2);
        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.com_describe_p3);

        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pop = new PopupWindow(com_describeActivity.this);
                view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);//加载布局
                pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);   //设置PopupWindow 一些参数
                pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setFocusable(true);
                pop.setOutsideTouchable(true);
                pop.setContentView(view);
                pop.showAtLocation(view, Gravity.BOTTOM,0,0);

                Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
                Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_photo);
                Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);

                //相机
                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                        try{
                            if(outputImage.exists()){
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        if(Build.VERSION.SDK_INT>=24){
                            imageUri = FileProvider.getUriForFile(com_describeActivity.this,
                                    "com.hiwhu.fileprovider",
                                    outputImage);

                        }else{
                            imageUri = Uri.fromFile(outputImage);
                        }
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent,TAKE_PHOTO);
                        pop.dismiss();
                    }
                });
                //相册
                bt2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {Intent intent = new Intent();
                        /* 开启Pictures画面Type设定为image */
                        intent.setType("image/*");
                        /* 使用Intent.ACTION_GET_CONTENT这个Action */
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        /* 取得相片后返回本画面 */
                        startActivityForResult(intent, CHOOSE_PHOTO);
                        pop.dismiss();
                    }
                });
                //取消
                bt3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                });

            }

        });

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        ImageView imageView = (ImageView) findViewById(R.id.imag_com_describe_p1);
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                Uri uri = data.getData();
                Log.e("uri", uri.toString());
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    ImageView imageView = (ImageView) findViewById(R.id.imag_com_describe_p1);
                    /* 将Bitmap设定到ImageView */
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}





