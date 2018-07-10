package com.hiwhuUI.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hiwhu.hiwhuclient.R;

import java.io.File;
import java.io.IOException;

import HttpConnect.HttpUtil;
import HttpConnect.Upload;
import data.staticData;
import okhttp3.Call;
import okhttp3.Response;



public class com_updateActivity extends AppCompatActivity {


    //跳转信息
    final static int UPDATE_SUCCEED=2;
    final static int UPDATE_FAILED=3;
    final static int ADD_SUCCEED=4;
    final static int ADD_FAILED=5;
    final static int CODE = 1;
    //调用系统相册-选择图片
    private static final int IMAGE = 1;

    private TextView text_image;
    private TextView text_type;
    //private ImageView imageView;

    private Button beginDate;
    private Button beginTime;
    private Button endDate;
    private Button endTime;
    private Button beginDate_signup;
    private Button beginTime_signup;
    private Button endDate_signup;
    private Button endTime_signup;
    private String begDate,begTime,enDate,enTime,begDate_signup,begTime_signup,
                enDate_signup,enTime_signup;

    private RadioButton button_need;
    private ImageButton button_address;
    private ImageButton button_type;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_update);
        //隐藏默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        beginDate = (Button)findViewById(R.id.beginDate);
        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(com_updateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        begDate = String.format("%d-%d-%d",year,month+1,day);
                        beginDate.setText(begDate);
                    }
                },2018,6,23).show();
            }
        });

        beginTime = (Button)findViewById(R.id.beginTime);
        beginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(com_updateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        begTime = String.format("%d:%d",hour,minute);
                        beginTime.setText(begTime);
                    }
                },0,0,true).show();
            }
        });

        endDate = (Button)findViewById(R.id.endDate);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(com_updateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        enDate= String.format("%d-%d-%d",year,month+1,day);
                        endDate.setText(enDate);
                    }
                },2018,6,24).show();
            }
        });

        endTime = (Button)findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(com_updateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        enTime = String.format("%d:%d",hour,minute);
                        endTime.setText(enTime);
                    }
                },0,0,true).show();
            }
        });

        button_need = (RadioButton)findViewById(R.id.button_need);


        beginDate_signup = (Button)findViewById(R.id.beginDate_signup);
        beginDate_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button_need.isChecked()) {
                    new DatePickerDialog(com_updateActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            begDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                            beginDate_signup.setText(begDate_signup);
                        }
                    }, 2018, 6, 23).show();
                }
            }
        });

        beginTime_signup = (Button)findViewById(R.id.beginTime_signup);
        beginTime_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button_need.isChecked()) {
                    new TimePickerDialog(com_updateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            begTime_signup = String.format("%d:%d", hour, minute);
                            beginTime_signup.setText(begTime_signup);
                        }
                    }, 0, 0, true).show();
                }
            }
        });

        endDate_signup = (Button)findViewById(R.id.endDate_signup);
        endDate_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button_need.isChecked()) {
                    new DatePickerDialog(com_updateActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                            endDate_signup.setText(enDate_signup);
                        }
                    }, 2018, 6, 24).show();
                }
            }
        });

        endTime_signup = (Button)findViewById(R.id.endTime_signup);
        endTime_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button_need.isChecked()) {
                    new TimePickerDialog(com_updateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            enTime_signup = String.format("%d:%d", hour, minute);
                            endTime_signup.setText(enTime_signup);
                        }
                    }, 0, 0, true).show();
                }
            }
        });
        Button submit = (Button)findViewById(R.id.button_forward);
        final EditText actName = (EditText)findViewById(R.id.activityName);
        final EditText desc = (EditText)findViewById(R.id.introduction);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url;
                if(staticData.getCurrentActivity()==null){
                    url = staticData.getUrl()+"/UploadShipServlet?";
                }else{
                    url = staticData.getUrl()+"/UpdateActivityServlet"
                            +"?activityID="+staticData.getCurrentActivity()
                            +"&sponsorID="+staticData.getSponsorID()+"&";
                }
                String title = actName.getText().toString();
                String startTime = begDate+" "+begTime;
                String endTime = enDate +" "+enTime;
                String registrationStartTime = begDate_signup+" "+begTime_signup;
                String registrationEndTime = enDate_signup+" "+enTime_signup;
                String location=null;//还需要添加
                String activityProfile = desc.getText().toString();
                try {
                    title = java.net.URLEncoder.encode(title, "UTF-8");
                    startTime = java.net.URLEncoder.encode(startTime, "UTF-8");
                    endTime = java.net.URLEncoder.encode(endTime, "UTF-8");
                    registrationStartTime = java.net.URLEncoder.encode(registrationStartTime, "UTF-8");
                    registrationEndTime = java.net.URLEncoder.encode(registrationEndTime, "UTF-8");
                    //location = java.net.URLEncoder.encode(location,"UTF-8");
                    activityProfile = java.net.URLEncoder.encode(activityProfile,"UTF-8");
                    url = url+"title="+title
                            +"&startTime="+startTime
                            +"&endTime="+endTime
                            +"&registrationStartTime="+registrationStartTime
                            +"&registrationEndTime="+registrationEndTime
                            +"&location="+location
                            +"&activityProfile="+activityProfile
                            +"&sponsorID="+staticData.getSponsorID();
                    Log.e("url----", url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File file = new File(imagePath);
                Log.e("filepath--",imagePath);
                new Upload(file).execute(url);
                /*HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.e("return---", s);
                        if(s.equals("updatesucceed")){
                            Jump(UPDATE_SUCCEED);
                        }else if(s.equals("updatefailed")){
                            Jump(UPDATE_FAILED);
                        }else{
                            String[] turn = s.split("\\.");
                            if(turn.length==0){
                                Jump(ADD_FAILED);
                            }else if(turn[0].equals("succeed")){
                                staticData.setCurrentActivity(turn[1]);
                                //添加图片
                                //File file = new File(imagePath);
                                //Log.e("filepath--",imagePath);
                                //new Upload(file).execute(staticData.getUrl()+"/UploadShipServlet?"+
                                //    staticData.getCurrentActivity());
                                Jump(ADD_SUCCEED);
                            }else{
                                Jump(ADD_FAILED);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("error",e.toString());
                    }
                });*/
            }
        });
    }

    public void Jump(final int flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(ADD_SUCCEED == flag){
                    Toast.makeText(com_updateActivity.this,"活动已创建！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(com_updateActivity.this,com_ViewActivity.class);
                    startActivity(intent);
                }else if(ADD_FAILED == flag){
                    Toast.makeText(com_updateActivity.this,"活动创建失败！",Toast.LENGTH_LONG).show();
                }else if(UPDATE_FAILED == flag){
                    Toast.makeText(com_updateActivity.this,"更新失败！",Toast.LENGTH_LONG).show();
                }else if(UPDATE_SUCCEED == flag){
                    Toast.makeText(com_updateActivity.this,"更新成功！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(com_updateActivity.this,com_ViewActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_image:
                //调用相册
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_CONTACT = 101;
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //验证是否许可权限
                    for (String str : permissions) {
                        if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限
                            this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                            return;
                        }
                    }
                }
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
            case R.id.button_address:

                //Intent intent2 = new Intent(pdateActivity.this,BMapActivity.class);
                //startActivity(intent2);
                break;
            case R.id.button_type:
                AlertDialog.Builder builder = new AlertDialog.Builder(com_updateActivity.this);
                builder.setTitle("选择一个分类");
                //    指定下拉列表的显示数据
                final String[] type = {"竞赛", "体育", "文艺", "公益", "讲座","其他"};
                //    设置一个下拉的列表选择项
                builder.setItems(type, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Toast.makeText(com_updateActivity.this, "选择的城市为：" + type[which], Toast.LENGTH_SHORT).show();
                        text_type = (TextView)findViewById(R.id.text_type);
                        text_type.setText(type[which]);
                    }
                });
                builder.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case IMAGE:
                    if (resultCode == RESULT_OK) {
                        selectPic(data);
                    }
                    break;
            }
        }
    }

    private void selectPic(Intent intent) {
        Uri selectImageUri = intent.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);

        cursor.close();
        //imageView = (ImageView)findViewById(R.id.imageView);
        //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        text_image = (TextView)findViewById(R.id.text_image);
        text_image.setText("图片上传成功！");
    }

}
