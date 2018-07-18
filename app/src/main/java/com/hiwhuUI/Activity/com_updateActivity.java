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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.Map.LocationActivity;
import com.hiwhuUI.Activity.process.DetailActivity;

import java.io.File;
import java.net.URLEncoder;

import HttpConnect.GetCurrentActivity;
import HttpConnect.UploadImg;
import data.staticData;


public class com_updateActivity extends AppCompatActivity {


    //跳转信息
    final static int UPDATE_SUCCEED=2;
    final static int UPDATE_FAILED=3;
    final static int ADD_SUCCEED=4;
    final static int ADD_FAILED=5;
    //调用1-系统相册 2-百度地图
    private static final int IMAGE_UPDATE = 1;
    private static final int ADDRESS = 2;
    private static final int IMAGE_DELETE = 3;
    static int UPDATE_OR_DELETE = 1;

    private TextView text_image;
    private TextView text_type;
    private TextView text_address;
    private ImageButton button_image;

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

    //服务器所需要的数据

    private String imagePath=null;
    private String activitytype = "0";
    private String title = null;
    private String startTime = null;
    private String endTime_String = null;
    private String registrationStartTime = "1000-12-31 1:0";//不需要报名的报名伪时间
    private String registrationEndTime = "1000-12-31 1:0";
    private String location=null;
    private Double latitude=null;
    private Double longitude=null;
    private String activityProfile = null;

    //时间选择器需要的限制
    private int begDate_year = 10000;
    private int begDate_month = 10000;
    private int begDate_day = 10000;
    private int begTime_hour = 10000;
    private int begTime_minute = 10000;
    private int enDate_year = 10000;
    private int enDate_month = 10000;
    private int enDate_day = 10000;
    private int enTime_hour = 10000;
    private int enTime_minute = 10000;
    private int begDate_signup_year = 10000;
    private int begDate_signup_month = 10000;
    private int begDate_signup_day = 10000;
    private int begTime_signup_hour = 10000;
    private int begTime_signup_minute = 10000;
    private int enDate_signup_year = 10000;
    private int enDate_signup_month = 10000;
    private int enDate_signup_day = 10000;
    private int enTime_signup_hour = 10000;
    private int enTime_signup_minute = 10000;
    private Boolean theSameDay = false;
    private Boolean theSameDay_signup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UPDATE_OR_DELETE = 1;

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_com_update);

        final String activity_id = getIntent().getStringExtra("activity_id");

        Button back = (Button)findViewById(R.id.button_backward);
        back.setText("返回");
        //返回消息主页
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //隐藏默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        button_image = (ImageButton)findViewById(R.id.button_image);

        beginDate = (Button)findViewById(R.id.beginDate);
        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(com_updateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        begDate_year = year;
                        begDate_month = month;
                        begDate_day = day;
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
                        begTime_hour = hour;
                        begTime_minute = minute;
                        if(minute<10) {
                            begTime = String.format("%d:0%d", hour, minute);
                        }else{
                            begTime = String.format("%d:%d", hour, minute);
                        }
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
                        enDate_year = year;
                        enDate_month = month;
                        enDate_day = day;
                        if(begDate_year!=10000&&begDate_month!=10000&&begDate_day!=10000) {
                            if (enDate_year == begDate_year) {
                                if (enDate_month == begDate_month) {
                                    if (enDate_day >= begDate_day) {
                                        if (enDate_day == begDate_day) {
                                            theSameDay = true;
                                        }else {
                                            theSameDay = false;
                                        }
                                        enDate = String.format("%d-%d-%d", year, month + 1, day);
                                        endDate.setText(enDate);
                                    } else {
                                        theSameDay = false;
                                        enDate_year = 10000;
                                        enDate_month = 10000;
                                        enDate_day = 10000;
                                        endDate.setText("请重新选择");
                                        Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                    }
                                }else if(enDate_month > begDate_month){
                                    theSameDay = false;
                                    enDate = String.format("%d-%d-%d", year, month + 1, day);
                                    endDate.setText(enDate);
                                }else {
                                    theSameDay = false;
                                    enDate_year = 10000;
                                    enDate_month = 10000;
                                    enDate_day = 10000;
                                    endDate.setText("请重新选择");
                                    Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                }
                            }else if(enDate_year > begDate_year){
                                theSameDay = false;
                                enDate = String.format("%d-%d-%d", year, month + 1, day);
                                endDate.setText(enDate);
                            } else {
                                theSameDay = false;
                                enDate_year = 10000;
                                enDate_month = 10000;
                                enDate_day = 10000;
                                endDate.setText("请重新选择");
                                Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            enDate_year = 10000;
                            enDate_month = 10000;
                            enDate_day = 10000;
                            Toast.makeText(com_updateActivity.this, "请先选择开始时间！", Toast.LENGTH_LONG).show();
                        }
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
                        enTime_hour = hour;
                        enTime_minute = minute;
                        if (begTime_hour!=10000&&begTime_minute!=10000){
                            if(enDate_year!=10000&&enDate_month!=10000&&enDate_day!=10000){
                                if (theSameDay) {
                                    if (enTime_hour >= begTime_hour) {
                                        if (enTime_hour == begTime_hour) {
                                            if (enTime_minute > begTime_minute) {
                                                if (minute < 10) {
                                                    enTime = String.format("%d:0%d", hour, minute);
                                                    endTime.setText(enTime);
                                                } else {
                                                    enTime = String.format("%d:%d", hour, minute);
                                                    endTime.setText(enTime);
                                                }
                                            } else {
                                                endTime.setText("请重新选择");
                                                enTime_hour = 10000;
                                                enTime_minute = 10000;
                                                Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            if (minute < 10) {
                                                enTime = String.format("%d:0%d", hour, minute);
                                                endTime.setText(enTime);
                                            } else {
                                                enTime = String.format("%d:%d", hour, minute);
                                                endTime.setText(enTime);
                                            }
                                        }
                                    } else {
                                        endTime.setText("请重新选择");
                                        enTime_hour = 10000;
                                        enTime_minute = 10000;
                                        Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    if (minute < 10) {
                                        enTime = String.format("%d:0%d", hour, minute);
                                        endTime.setText(enTime);
                                    } else {
                                        enTime = String.format("%d:%d", hour, minute);
                                        endTime.setText(enTime);
                                    }
                                }
                            }else {
                                enTime_hour = 10000;
                                enTime_minute = 10000;
                                Toast.makeText(com_updateActivity.this, "请先选择结束日期！", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            enTime_hour = 10000;
                            enTime_minute = 10000;
                            Toast.makeText(com_updateActivity.this, "请先选择开始时间！", Toast.LENGTH_LONG).show();
                        }
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
                            begDate_signup_year = year;
                            begDate_signup_month = month;
                            begDate_signup_day = day;
                            if(begDate_year!=10000&&begDate_month!=10000&&begDate_day!=10000&&begTime_hour!=10000&&begTime_minute!=10000
                                    &&enDate_year!=10000&&enDate_month!=10000&&enDate_day!=10000&&enTime_hour!=10000&&enTime_minute!=10000) {
                                if (begDate_signup_year < begDate_year) {
                                    begDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                    beginDate_signup.setText(begDate_signup);
                                } else if (begDate_signup_year == begDate_year) {
                                    if(begDate_signup_month == begDate_month){
                                        if(begDate_signup_day <= begDate_day){
                                            begDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                            beginDate_signup.setText(begDate_signup);
                                        }else {
                                            beginDate_signup.setText("请重新选择");
                                            begDate_signup_year = 10000;
                                            begDate_signup_month = 10000;
                                            begDate_signup_day = 10000;
                                            Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                        }
                                    }else if(begDate_signup_month < begDate_month){
                                        begDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                        beginDate_signup.setText(begDate_signup);
                                    }else {
                                        beginDate_signup.setText("请重新选择");
                                        begDate_signup_year = 10000;
                                        begDate_signup_month = 10000;
                                        begDate_signup_day = 10000;
                                        Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    beginDate_signup.setText("请重新选择");
                                    begDate_signup_year = 10000;
                                    begDate_signup_month = 10000;
                                    begDate_signup_day = 10000;
                                    Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                begDate_signup_year = 10000;
                                begDate_signup_month = 10000;
                                begDate_signup_day = 10000;
                                Toast.makeText(com_updateActivity.this, "请先选择活动时间！", Toast.LENGTH_LONG).show();
                            }
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
                            begTime_signup_hour = hour;
                            begTime_signup_minute = minute;
                            if(begDate_year!=10000&&begDate_month!=10000&&begDate_day!=10000&&begTime_hour!=10000&&begTime_minute!=10000
                                    &&enDate_year!=10000&&enDate_month!=10000&&enDate_day!=10000&&enTime_hour!=10000&&enTime_minute!=10000) {
                                if(minute<10) {
                                    begTime_signup = String.format("%d:0%d", hour, minute);
                                    beginTime_signup.setText(begTime_signup);
                                }else {
                                    begTime_signup = String.format("%d:%d", hour, minute);
                                    beginTime_signup.setText(begTime_signup);
                                }
                            }else{
                                begTime_signup_hour = 10000;
                                begTime_signup_minute = 10000;
                                Toast.makeText(com_updateActivity.this, "请先选择活动时间！", Toast.LENGTH_LONG).show();
                            }
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
                            enDate_signup_year = year;
                            enDate_signup_month = month;
                            enDate_signup_day = day;
                            if(begDate_year!=10000&&begDate_month!=10000&&begDate_day!=10000&&begTime_hour!=10000&&begTime_minute!=10000
                                    &&enDate_year!=10000&&enDate_month!=10000&&enDate_day!=10000&&enTime_hour!=10000&&enTime_minute!=10000) {
                                if(begDate_signup_year!=10000&&begDate_signup_month!=10000&&begDate_signup_day!=10000
                                        &&begTime_signup_hour!=10000&&begTime_minute!=10000){
                                    if(enDate_signup_year == begDate_signup_year){
                                        if(enDate_signup_month == begDate_signup_month){
                                            if(enDate_signup_day == begDate_signup_day){
                                                theSameDay_signup = true;
                                                enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                endDate_signup.setText(enDate_signup);
                                            }else if(enDate_signup_day > begDate_signup_day){
                                                if(enDate_signup_year == begDate_year){
                                                    if(enDate_signup_month == begDate_month){
                                                        if(enDate_signup_day <= begDate_day){
                                                            theSameDay_signup = false;
                                                            enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                            endDate_signup.setText(enDate_signup);
                                                        }else {
                                                            theSameDay_signup = false;
                                                            endDate_signup.setText("请重新选择");
                                                            enDate_signup_year = 10000;
                                                            enDate_signup_month = 10000;
                                                            enDate_signup_day = 10000;
                                                            Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                                        }
                                                    }else if(enDate_signup_month < begDate_month){
                                                        theSameDay_signup = false;
                                                        enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                        endDate_signup.setText(enDate_signup);
                                                    }else {
                                                        theSameDay_signup = false;
                                                        endDate_signup.setText("请重新选择");
                                                        enDate_signup_year = 10000;
                                                        enDate_signup_month = 10000;
                                                        enDate_signup_day = 10000;
                                                        Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                                    }
                                                }else {
                                                    theSameDay_signup = false;
                                                    enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                    endDate_signup.setText(enDate_signup);
                                                }
                                            }else {
                                                theSameDay_signup = false;
                                                endDate_signup.setText("请重新选择");
                                                enDate_signup_year = 10000;
                                                enDate_signup_month = 10000;
                                                enDate_signup_day = 10000;
                                                Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                            }
                                        }else if(enDate_signup_month > begDate_signup_month){
                                            if(enDate_signup_year < begDate_year) {
                                                theSameDay_signup = false;
                                                enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                endDate_signup.setText(enDate_signup);
                                            }else if(enDate_signup_year == begDate_year){
                                                if(enDate_signup_month == begDate_month){
                                                    if(enDate_signup_day <= begDate_day){
                                                        theSameDay_signup = false;
                                                        enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                        endDate_signup.setText(enDate_signup);
                                                    }else {
                                                        theSameDay_signup = false;
                                                        endDate_signup.setText("请重新选择");
                                                        enDate_signup_year = 10000;
                                                        enDate_signup_month = 10000;
                                                        enDate_signup_day = 10000;
                                                        Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                                    }
                                                }else if(enDate_signup_month < begDate_month){
                                                    theSameDay_signup = false;
                                                    enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                    endDate_signup.setText(enDate_signup);
                                                }else {
                                                    theSameDay_signup = false;
                                                    endDate_signup.setText("请重新选择");
                                                    enDate_signup_year = 10000;
                                                    enDate_signup_month = 10000;
                                                    enDate_signup_day = 10000;
                                                    Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                                }
                                            }else {
                                                theSameDay_signup = false;
                                                endDate_signup.setText("请重新选择");
                                                enDate_signup_year = 10000;
                                                enDate_signup_month = 10000;
                                                enDate_signup_day = 10000;
                                                Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                            }
                                        }else {
                                            theSameDay_signup = false;
                                            endDate_signup.setText("请重新选择");
                                            enDate_signup_year = 10000;
                                            enDate_signup_month = 10000;
                                            enDate_signup_day = 10000;
                                            Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                        }
                                    }else if(enDate_signup_year > begDate_signup_year){
                                        if(enDate_signup_year < begDate_year){
                                            theSameDay_signup = false;
                                            enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                            endDate_signup.setText(enDate_signup);
                                        }else if(enDate_signup_year == begDate_year){
                                            if(enDate_signup_month == begDate_month){
                                                if(enDate_signup_day == begDate_day){
                                                    theSameDay_signup = false;
                                                    enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                    endDate_signup.setText(enDate_signup);
                                                }else if(enDate_signup_day < begDate_day){
                                                    theSameDay_signup = false;
                                                    enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                    endDate_signup.setText(enDate_signup);
                                                }else {
                                                    theSameDay_signup = false;
                                                    endDate_signup.setText("请重新选择");
                                                    enDate_signup_year = 10000;
                                                    enDate_signup_month = 10000;
                                                    enDate_signup_day = 10000;
                                                    Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                                }
                                            }else if(enDate_signup_month < begDate_month){
                                                theSameDay_signup = false;
                                                enDate_signup = String.format("%d-%d-%d", year, month + 1, day);
                                                endDate_signup.setText(enDate_signup);
                                            }else {
                                                theSameDay_signup = false;
                                                endDate_signup.setText("请重新选择");
                                                enDate_signup_year = 10000;
                                                enDate_signup_month = 10000;
                                                enDate_signup_day = 10000;
                                                Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                            }
                                        }else {
                                            theSameDay_signup = false;
                                            endDate_signup.setText("请重新选择");
                                            enDate_signup_year = 10000;
                                            enDate_signup_month = 10000;
                                            enDate_signup_day = 10000;
                                            Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                        }
                                    }else {
                                        theSameDay_signup = false;
                                        endDate_signup.setText("请重新选择");
                                        enDate_signup_year = 10000;
                                        enDate_signup_month = 10000;
                                        enDate_signup_day = 10000;
                                        Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    enDate_signup_year = 10000;
                                    enDate_signup_month = 10000;
                                    enDate_signup_day = 10000;
                                    Toast.makeText(com_updateActivity.this, "请先选择报名开始时间！", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                begTime_signup_hour = 10000;
                                begTime_signup_minute = 10000;
                                Toast.makeText(com_updateActivity.this, "请先选择活动时间！", Toast.LENGTH_LONG).show();
                            }
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
                            enTime_signup_hour = hour;
                            enTime_signup_minute = minute;
                            if(begDate_year!=10000&&begDate_month!=10000&&begDate_day!=10000&&begTime_hour!=10000&&begTime_minute!=10000
                                    &&enDate_year!=10000&&enDate_month!=10000&&enDate_day!=10000&&enTime_hour!=10000&&enTime_minute!=10000) {
                                if(enDate_signup_year!=10000&&enDate_signup_month!=10000&&enDate_signup_day!=10000) {
                                    if(theSameDay_signup){
                                        if(enTime_signup_hour == begTime_signup_hour){
                                            if(enTime_signup_minute > begTime_signup_minute){
                                                if(minute<10) {
                                                    enTime_signup = String.format("%d:0%d", hour, minute);
                                                    endTime_signup.setText(enTime_signup);
                                                }else {
                                                    enTime_signup = String.format("%d:%d", hour, minute);
                                                    endTime_signup.setText(enTime_signup);
                                                }
                                            }else {
                                                endTime_signup.setText("请重新选择");
                                                enTime_signup_hour = 10000;
                                                enTime_signup_minute = 10000;
                                                Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                            }
                                        }else if(enTime_signup_hour < begTime_signup_hour){
                                            if(minute<10) {
                                                enTime_signup = String.format("%d:0%d", hour, minute);
                                                endTime_signup.setText(enTime_signup);
                                            }else {
                                                enTime_signup = String.format("%d:%d", hour, minute);
                                                endTime_signup.setText(enTime_signup);
                                            }
                                        }else {
                                            endTime_signup.setText("请重新选择");;
                                            enTime_signup_hour = 10000;
                                            enTime_signup_minute = 10000;
                                            Toast.makeText(com_updateActivity.this, "您选择的时间不合常理，请重新选择！", Toast.LENGTH_LONG).show();
                                        }
                                    }else {
                                        if(minute<10) {
                                            enTime_signup = String.format("%d:0%d", hour, minute);
                                            endTime_signup.setText(enTime_signup);
                                        }else {
                                            enTime_signup = String.format("%d:%d", hour, minute);
                                            endTime_signup.setText(enTime_signup);
                                        }
                                    }
                                }else {
                                    enTime_signup_hour = 10000;
                                    enTime_signup_minute = 10000;
                                    Toast.makeText(com_updateActivity.this, "请先选择报名截止日期！", Toast.LENGTH_LONG).show();
                                }
                            }else {
                                enTime_signup_hour = 10000;
                                enTime_signup_minute = 10000;
                                Toast.makeText(com_updateActivity.this, "请先选择活动时间！", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 0, 0, true).show();
                }
            }
        });

        text_address = (TextView)findViewById(R.id.text_address);

        Button submit = (Button)findViewById(R.id.button_forward);
        final EditText actName = (EditText)findViewById(R.id.activityName);
        final EditText desc = (EditText)findViewById(R.id.introduction);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.start(view.getContext(),10);
                String url;
                if(activity_id==null){
                    url = staticData.getUrl()+"/AddActivityServlet?";
                }else{
                    url = staticData.getUrl()+"/UpdateActivityServlet"
                            +"?activityID="+activity_id
                            +"&sponsorID="+staticData.getSponsorID()+"&";
                }
                title = actName.getText().toString();
                startTime = begDate+" "+begTime;
                endTime_String = enDate +" "+enTime;
                if(button_need.isChecked()){
                    registrationStartTime = begDate_signup+" "+begTime_signup;
                    registrationEndTime = enDate_signup+" "+enTime_signup;
                }
                activityProfile = desc.getText().toString();
                try {
                    title = java.net.URLEncoder.encode(title, "UTF-8");
                    startTime = java.net.URLEncoder.encode(startTime, "UTF-8");
                    endTime_String = java.net.URLEncoder.encode(endTime_String, "UTF-8");
                    registrationStartTime = java.net.URLEncoder.encode(registrationStartTime, "UTF-8");
                    registrationEndTime = java.net.URLEncoder.encode(registrationEndTime, "UTF-8");
                    location = java.net.URLEncoder.encode(location,"UTF-8");
                    Log.e("locationafter",location);
                    //经纬度latitude、longitude怎么传？
                    activityProfile = java.net.URLEncoder.encode(activityProfile,"UTF-8");
                    url = url+"title="+title
                            +"&startTime="+startTime
                            +"&endTime="+endTime_String
                            +"&registrationStartTime="+registrationStartTime
                            +"&registrationEndTime="+registrationEndTime
                            +"&location="+location
                            +"&activityProfile="+activityProfile
                            +"&sponsorID="+staticData.getSponsorID()
                            +"&type="+activitytype
                            +"&latitude="+latitude
                            +"&longitude="+longitude;
                    Log.e("url----", url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(null==imagePath){
                    Toast.makeText(com_updateActivity.this,"请添加图片！",Toast.LENGTH_LONG).show();
                }else{
                    File file = new File(imagePath);
                    Log.e("filepath--",imagePath);
                    Upload upload = new Upload(file);
                    upload.execute(url);
                }
            }
        });
    }

    public void initInfo(){
        String activity_id = getIntent().getStringExtra("activity_id");
        if(activity_id==null) return;
        else{
            GetCurrentActivity.GetActivityInit(activity_id);
        }
    }

    public void Jump(final int flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(ADD_SUCCEED == flag){
                    Toast.makeText(com_updateActivity.this,"活动已创建！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),com_viewActivity.class);
                    intent.putExtra("activity_id",staticData.getCurrentActivity());
                    startActivity(intent);
                    finish();
                }else if(ADD_FAILED == flag){
                    DetailActivity.detailActivity.finish();
                    Toast.makeText(com_updateActivity.this,"活动创建失败！",Toast.LENGTH_LONG).show();
                }else if(UPDATE_FAILED == flag){
                    DetailActivity.detailActivity.finish();
                    Toast.makeText(com_updateActivity.this,"更新失败！",Toast.LENGTH_LONG).show();
                }else if(UPDATE_SUCCEED == flag){
                    Toast.makeText(com_updateActivity.this,"更新成功！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),com_viewActivity.class);
                    intent.putExtra("activity_id",staticData.getCurrentActivity());
                    startActivity(intent);
                }
            }
        });

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_image:
                //调用相册
                switch (UPDATE_OR_DELETE) {
                    case IMAGE_UPDATE:
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
                        startActivityForResult(intent, UPDATE_OR_DELETE);
                        break;
                    case IMAGE_DELETE:
                        Intent intent1 = new Intent(com_updateActivity.this,ImageActivity.class);
                        intent1.putExtra("picPath",imagePath);
                        startActivityForResult(intent1, UPDATE_OR_DELETE);
                        break;
                    default:
                        break;
                }

                break;
            case R.id.button_address:
                Intent intent2 = new Intent(com_updateActivity.this, LocationActivity.class);
                startActivityForResult(intent2, ADDRESS);
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
                        activitytype = String.valueOf(which+1);
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

        if (requestCode == IMAGE_UPDATE && resultCode == Activity.RESULT_OK && data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case IMAGE_UPDATE:
                    if (resultCode == RESULT_OK) {
                        selectPic(data);
                    }
                    break;

            }
        }
        if (requestCode == IMAGE_DELETE && resultCode == Activity.RESULT_OK && data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case IMAGE_DELETE:
                    if (resultCode == RESULT_OK) {
                        UPDATE_OR_DELETE = 1;
                        imagePath = null;
                        text_image.setText("点击上传封面图片");
                        button_image.setImageDrawable(getResources().getDrawable(R.drawable.acu_picture));
                    }
                    break;

            }
        }
        if(requestCode == ADDRESS && resultCode == Activity.RESULT_OK && data != null){
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case ADDRESS:
                    if (resultCode == RESULT_OK) {
                        //获取纬度
                        latitude = data.getDoubleExtra("latitude",0.0);
                        //获取经度
                        longitude = data.getDoubleExtra("longitude",0.0);
                        //修改地址
                        location = data.getStringExtra("position");
                        text_address.setText(location);
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
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        button_image.setImageBitmap(bm);
        cursor.close();
        UPDATE_OR_DELETE = 3;
        text_image = (TextView)findViewById(R.id.text_image);
        text_image.setText("图片上传成功！");
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
                    Jump(ADD_SUCCEED);
                }else{
                    Jump(ADD_FAILED);
                }
            }
        }


    }
}

