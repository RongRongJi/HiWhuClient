package com.hiwhuUI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;

public class data_editActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_edit);
        //接收上个活动并显示
        EditText et = (EditText) findViewById(R.id.edit_data);
        Intent intent = getIntent();
        String name_pre = intent.getStringExtra("data");
        et.setText(name_pre);
        et.setSelection(et.getText().length());
        //确定
        Button bt2 = (Button)findViewById(R.id.btn2_data_edit);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText editText = (EditText)findViewById(R.id.edit_data);
                String data = editText.getText().toString();
                intent.putExtra("data",data);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
