package com.hiwhuUI.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hiwhu.hiwhuclient.R;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        String activity_id = getIntent().getStringExtra("activity_id");
        Toast.makeText(SignupActivity.this, activity_id, Toast.LENGTH_SHORT).show();
    }
}