package com.hiwhuUI.Activity.message;

/** created by 王清玉
 * modified by 刘劭荣
 **/
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.com_viewActivity;
import com.hiwhuUI.Activity.stu_viewActivity;

import java.util.ArrayList;
import java.util.List;

import com.hiwhuUI.Activity.function.RefreshableView;
import HttpConnect.GetActivityBySponsorID;
import data.staticData;

public class comM_result extends AppCompatActivity {
    private List<comResult> resultList = new ArrayList<>();
    List<entity.Activity> activityList=null;

    RefreshableView refreshableView;
    ResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultList = new ArrayList<>();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_com_m_result);

        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);

        //更改标题栏
        TextView title = (TextView)findViewById(R.id.text_title) ;
        title.setText("活动上传结果");
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
        adapter = new ResultAdapter(comM_result.this,R.layout.com_result_item,resultList);
        ListView listView = (ListView)findViewById(R.id.result_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(comM_result.this,com_viewActivity.class);
                intent.putExtra("activity_id",activityList.get(position).getActivityID());
                startActivity(intent);
            }
        });


        //下拉刷新
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                initComResult();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                refreshableView.finishRefreshing();
            }
        }, 0);
    }

    //list初始化
    private void initComResult(){
        resultList = new ArrayList<>();
        GetActivityBySponsorID gabs = GetActivityBySponsorID.GetActivityInit(staticData.getSponsorID());
        activityList = gabs.activityList;
        for (int i = 0; i <activityList.size(); i++){
            comResult result = new comResult(activityList.get(i).getTitle(),R.drawable.jump1,"成功");
            resultList.add(result);
        }

    }


}

