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

public class stuResult extends AppCompatActivity {
    private List<comResult> resultList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_m_result);
        //更改标题栏
        TextView title = (TextView)findViewById(R.id.text_title) ;
        title.setText("报名结果");
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
        ResultAdapter adapter = new ResultAdapter(stuResult.this,R.layout.com_result_item,resultList);
        ListView listView = (ListView)findViewById(R.id.result_list);
        listView.setAdapter(adapter);
  /*      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                comResult comresult  = resultList.get(position);
                switch (position){
                    case 2|5|11|8|10|6:
                        Intent intent = new Intent(comM_result.this,com_ViewActivity.class);
                        startActivity(intent);
                        break;
                    case 0|1|3|4|7:
                        break;
                    case 9:
                        break;
                }
            }
        });*/
    }

    //list初始化
    private void initComResult(){
        for (int i = 0; i <1; i++){
            comResult result1 = new comResult("好想回家避暑啊",0,"待审核");
            resultList.add(result1);
            comResult result2 = new comResult("好好写前端",0,"失败");
            resultList.add(result2);
            comResult result3 = new comResult("技术经理是个大猪蹄子",R.drawable.jump1,"成功");
            resultList.add(result3);
            comResult result4 = new comResult("项目经理世最可",0,"失败");
            resultList.add(result4);
            comResult result5 = new comResult("咕咕咕咕咕",0,"失败");
            resultList.add(result5);
            comResult result6 = new comResult("百度地图API",R.drawable.jump1,"成功");
            resultList.add(result6);
            comResult result7 = new comResult("想吃火锅了",R.drawable.jump1,"成功");
            resultList.add(result7);
            comResult result8 = new comResult("还行吧好歹这是学生界面",0,"失败");
            resultList.add(result8);
            comResult result9 = new comResult("真香",R.drawable.jump1,"成功");
            resultList.add(result9);
            comResult result10 = new comResult("？",0,"失败");
            resultList.add(result10);
            comResult result11 = new comResult("什么意思",R.drawable.jump1,"成功");
            resultList.add(result11);
            comResult result12 = new comResult("seventeen七月十六回归了解一下呗",0,"成功");
            resultList.add(result12);
        }

    }
}
