package com.hiwhuUI.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hiwhu.hiwhuclient.R;

import java.io.File;

import HttpConnect.GetCurrentSponsor;
import HttpConnect.UploadImg;
import data.staticData;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.baidu.mapapi.BMapManager.getContext;

public class stu_dataActivity extends AppCompatActivity {
    public static final int CHOOSE_PHOTO = 2;

    @SuppressLint("ClickableViewAccessibility")
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

        head.setClickable(true);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                Uri uri = data.getData();
                Log.e("uri", uri.toString());
                ContentResolver cr = this.getContentResolver();
                File file = new File(selectPic(uri));
                Upload upload = new Upload(file);
                upload.execute(staticData.getUrl()+"/ChangeHeadImageServlet?studentID="+staticData.student.getStudentID());
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String selectPic(Uri selectImageUri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }


    //实现异步操作接口
    class Upload extends AsyncTask<String,Void,String> {
        File file;
        public Upload(File file){
            this.file = file;
        }
        @Override
        protected String doInBackground(String... strings) {
            return UploadImg.uploadFile(file,strings[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("return---", s);
            if(s.equals("succeed")){
                GetCurrentSponsor.GetSponsorInit();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置圆形头像
                        ImageView head = (ImageView)findViewById(R.id.stu_h_head);
                        Glide.with(getContext()).load(staticData.getUrl()+"/"+staticData.sponsor.getHeadProtrait()).skipMemoryCache(true) // 不使用内存缓存
                                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                                .bitmapTransform(new CropCircleTransformation(getContext()))
                                .into(head);
                    }
                });
            }
        }

    }

}
