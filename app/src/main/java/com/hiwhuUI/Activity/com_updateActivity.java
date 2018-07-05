package com.hiwhuUI.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hiwhu.hiwhuclient.R;

public class com_updateActivity extends AppCompatActivity {

    final static int CODE = 1;
    //调用系统相册-选择图片
    private static final int IMAGE = 1;

    private TextView text_image;
    //private ImageView imageView;

    private Button beginDate;
    private Button beginTime;
    private Button endDate;
    private Button endTime;
    private Button beginDate_signup;
    private Button beginTime_signup;
    private Button endDate_signup;
    private Button endTime_signup;

    private RadioButton button_need;
    private ImageButton button_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_update);

        beginDate = (Button)findViewById(R.id.beginDate);
        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(com_updateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String theDate = String.format("%d-%d-%d",year,month+1,day);
                        beginDate.setText(theDate);
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
                        String theTime = String.format("%d:%d",hour,minute);
                        beginTime.setText(theTime);
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
                        String theDate = String.format("%d-%d-%d",year,month+1,day);
                        endDate.setText(theDate);
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
                        String theTime = String.format("%d:%d",hour,minute);
                        endTime.setText(theTime);
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
                            String theDate = String.format("%d-%d-%d", year, month + 1, day);
                            beginDate_signup.setText(theDate);
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
                            String theTime = String.format("%d:%d", hour, minute);
                            beginTime_signup.setText(theTime);
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
                            String theDate = String.format("%d-%d-%d", year, month + 1, day);
                            endDate_signup.setText(theDate);
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
                            String theTime = String.format("%d:%d", hour, minute);
                            endTime_signup.setText(theTime);
                        }
                    }, 0, 0, true).show();
                }
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_image:
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
            case R.id.button_address:

                //Intent intent2 = new Intent(pdateActivity.this,BMapActivity.class);
                //startActivity(intent2);
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
        //int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        //String picturePath = cursor.getString(columnIndex);
        cursor.close();
        //imageView = (ImageView)findViewById(R.id.imageView);
        //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        text_image = (TextView)findViewById(R.id.text_image);
        text_image.setText("图片上传成功！");
    }
}
