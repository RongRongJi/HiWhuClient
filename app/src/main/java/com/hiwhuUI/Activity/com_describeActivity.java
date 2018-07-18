package com.hiwhuUI.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hiwhu.hiwhuclient.R;
import java.io.File;
import java.io.IOException;
import HttpConnect.GetCurrentSponsor;
import HttpConnect.UploadImg;
import data.staticData;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.baidu.mapapi.BMapManager.getContext;

public class com_describeActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int CHANGE_NAME = 3;
    public static final int CHANGE_TELE = 4;
    public static final int CHANGE_DESCRIBE =5;
    private  PopupWindow pop = null;//弹窗
    private Uri imageUri;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_describe);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_com_describe);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout relativeLayout1 = (LinearLayout) findViewById(R.id.com_describe_p1);
        LinearLayout relativeLayout2 = (LinearLayout) findViewById(R.id.com_describe_p2);
        LinearLayout relativeLayout3 = (LinearLayout) findViewById(R.id.com_describe_p3);
        TextView tv5 = (TextView)findViewById(R.id.text2_com_describe_p4);

        ImageView headImage = (ImageView)findViewById(R.id.imag_com_describe_p1);
        //设置圆形头像
        Glide.with(this).load(staticData.getUrl()+"/"+staticData.sponsor.getHeadProtrait()).skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(headImage);

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
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });

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
                    public void onClick(View v) {
                        Intent intent = new Intent();
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
        TextView comName = (TextView)findViewById(R.id.text2_com_describe_p2);
        comName.setText(staticData.sponsor.getSponsorName());
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.text2_com_describe_p2);
                String name = (String) tv.getText();
                Intent intent = new Intent(com_describeActivity.this,data_editActivity.class);
                intent.putExtra("data",name);
                intent.putExtra("type",3);
                startActivityForResult(intent,CHANGE_NAME);
            }
        });
        //联系方式
        TextView telNum = (TextView)findViewById(R.id.text2_com_describe_p3);
        telNum.setText(staticData.sponsor.getPhoneNum());
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.text2_com_describe_p3);
                String tele = (String) tv.getText();
                Intent intent = new Intent(com_describeActivity.this,data_editActivity.class);
                intent.putExtra("data",tele);
                intent.putExtra("type",4);
                startActivityForResult(intent,CHANGE_TELE);

            }
        });
        //简介
        TextView comIntroduction = (TextView)findViewById(R.id.text2_com_describe_p4);
        comIntroduction.setText(staticData.sponsor.getIntroduction());
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.text2_com_describe_p4);
                String dc = (String) text.getHint();
                Intent intent = new Intent(com_describeActivity.this,data_editActivity.class);
                intent.putExtra("data",dc);
                intent.putExtra("type",5);
                startActivityForResult(intent,CHANGE_DESCRIBE);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            GetCurrentSponsor.GetSponsorInit();
            TextView comName = (TextView)findViewById(R.id.text2_com_describe_p2);
            comName.setText(staticData.sponsor.getSponsorName());
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    File file = new File(getExternalCacheDir(),"output_image.jpg");
                    Upload upload = new Upload(file);
                    upload.execute(staticData.getUrl()+"/ChangeHeadImageServlet?type=2&sponsorID="+staticData.sponsor.getSponsorID());
                }
                break;

            case CHOOSE_PHOTO:
                Uri uri = data.getData();
                Log.e("uri", uri.toString());
                ContentResolver cr = this.getContentResolver();
                File file = new File(selectPic(uri));
                Upload upload = new Upload(file);
                upload.execute(staticData.getUrl()+"/ChangeHeadImageServlet?type=2&sponsorID="+staticData.sponsor.getSponsorID());
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
                        ImageView headImage = (ImageView)findViewById(R.id.imag_com_describe_p1);
                        Glide.with(getContext()).load(staticData.getUrl()+"/"+staticData.sponsor.getHeadProtrait()).skipMemoryCache(true) // 不使用内存缓存
                                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                                .bitmapTransform(new CropCircleTransformation(getContext()))
                                .into(headImage);
                    }
                });
            }
        }


    }

}





