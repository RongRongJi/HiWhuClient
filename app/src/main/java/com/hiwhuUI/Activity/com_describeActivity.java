package com.hiwhuUI.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hiwhu.hiwhuclient.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import data.staticData;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class com_describeActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int CHANGE_NAME = 3;
    public static final int CHANGE_TELE = 4;
    public static final int CHANGE_DESCRIBE =5;
    private  PopupWindow pop = null;//弹窗
    private Uri imageUri;
    private Toolbar toolbar;
    private LinearLayout LinearLayout1;
    private LinearLayout LinearLayout2;
    private LinearLayout LinearLayout3;
    private LinearLayout LinearLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_describe);

        toolbar = (Toolbar) findViewById(R.id.toolbar_com_describe);
        toolbar.setTitle("");
        TextView textView = (TextView) findViewById(R.id.toolbar_com_describe_text);
        textView.setText("社团资料");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout1 = (LinearLayout) findViewById(R.id.com_describe_p1);
        LinearLayout2 = (LinearLayout) findViewById(R.id.com_describe_p2);
        LinearLayout3 = (LinearLayout) findViewById(R.id.com_describe_p3);
        LinearLayout4 = (LinearLayout) findViewById(R.id.com_describe_p4);

        ImageView img = (ImageView)findViewById(R.id.imag_com_describe_p1);
        Glide.with(this).load(staticData.getUrl()+"/"+staticData.sponsor.getHeadProtrait())
                .into(img);
        LinearLayout1.setOnClickListener(new View.OnClickListener() {
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

                TextView bt1 = (TextView) view.findViewById(R.id.item_popupwindows_camera);
                TextView bt2 = (TextView) view.findViewById(R.id.item_popupwindows_photo);
                TextView bt3 = (TextView) view.findViewById(R.id.item_popupwindows_cancel);

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

        TextView tv2 = (TextView) findViewById(R.id.text2_com_describe_p2);
        tv2.setText(staticData.sponsor.getSponsorName());
        //社团名称
        LinearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.text2_com_describe_p2);
                String name = (String) tv.getText();
                Intent intent = new Intent(com_describeActivity.this,data_editActivity.class);
                intent.putExtra("data",name);
                startActivityForResult(intent,CHANGE_NAME);
            }
        });

        TextView tv3 = (TextView) findViewById(R.id.text2_com_describe_p3);
        tv3.setText(staticData.sponsor.getPhoneNum());
        //联系方式
        LinearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.text2_com_describe_p3);
                String tele = (String) tv.getText();
                Intent intent = new Intent(com_describeActivity.this,data_editActivity.class);
                intent.putExtra("data",tele);
                startActivityForResult(intent,CHANGE_TELE);

            }
        });

        TextView text = (TextView) findViewById(R.id.text2_com_describe_p4);
        text.setText(staticData.sponsor.getIntroduction());
        //简介
        LinearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.text2_com_describe_p4);
                String dc = (String) text.getHint();
                Intent intent = new Intent(com_describeActivity.this,data_editActivity.class);
                intent.putExtra("data",dc);
                startActivityForResult(intent,CHANGE_DESCRIBE);
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
            case CHANGE_NAME:
                if (resultCode==RESULT_OK){
                    String returneddata = data.getStringExtra("data");
                    TextView tv = (TextView)findViewById(R.id.text2_com_describe_p2);
                    tv.setText(returneddata);
                }
                break;
            case CHANGE_TELE:
                if (resultCode==RESULT_OK){
                    String returneddata = data.getStringExtra("data");
                    TextView tv = (TextView)findViewById(R.id.text2_com_describe_p3);
                    tv.setText(returneddata);
                }
                break;
            case CHANGE_DESCRIBE:
                if (resultCode==RESULT_OK){
                    String returneddata = data.getStringExtra("data");
                    TextView tv5 = (TextView)findViewById(R.id.text2_com_describe_p4);
                    tv5.setText(returneddata);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home){
            finish();
            return true;
        }
        else return false;
    }



}





