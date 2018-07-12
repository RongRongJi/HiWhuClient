package com.hiwhuUI.Activity.message;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;

import java.util.ArrayList;
import java.util.List;

public class stuReply extends AppCompatActivity {

        private List<comResult> RemindList = new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_com_m_result);
            //更改标题栏
            TextView title = (TextView)findViewById(R.id.text_title) ;
            title.setText("评论回复");
            Button back = (Button)findViewById(R.id.button_backward);
            back.setText("返回");
            //返回消息主页
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Button forward = (Button)findViewById(R.id.button_forward);
            forward.setText(null);
            //隐藏默认标题栏
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.hide();
            }

            initComResult();
            ResultAdapter adapter = new ResultAdapter(stuReply.this,R.layout.com_result_item,RemindList);
            ListView listView = (ListView)findViewById(R.id.result_list);
            listView.setAdapter(adapter);
        }
        private void initComResult(){
            for (int i = 0; i <1; i++){
                comResult result1 = new comResult("好想回家避暑啊",R.drawable.jump1,"2");
                RemindList.add(result1);
                comResult result2 = new comResult("好好写前端",R.drawable.jump1,"2");
                RemindList.add(result2);
                comResult result3 = new comResult("技术经理是个大猪蹄子",R.drawable.jump1,"1");
                RemindList.add(result3);
                comResult result4 = new comResult("项目经理世最可",R.drawable.jump1,"1");
                RemindList.add(result4);
                comResult result5 = new comResult("咕咕咕咕咕",R.drawable.jump1,"1");
                RemindList.add(result5);
                comResult result6 = new comResult("百度地图API",R.drawable.jump1,"1");
                RemindList.add(result6);
                comResult result7 = new comResult("想吃火锅了",R.drawable.jump1,"1");
                RemindList.add(result7);
                comResult result8 = new comResult("还行吧好歹这是学生界面",R.drawable.jump1,"1");
                RemindList.add(result8);
                comResult result9 = new comResult("真香",R.drawable.jump1,"1");
                RemindList.add(result9);
                comResult result10 = new comResult("？",R.drawable.jump1,"1");
                RemindList.add(result10);
                comResult result11 = new comResult("什么意思",R.drawable.jump1,"1");
                RemindList.add(result11);
                comResult result12 = new comResult("seventeen七月十六回归了解一下呗",R.drawable.jump1,"1");
                RemindList.add(result12);
            }}
    }
