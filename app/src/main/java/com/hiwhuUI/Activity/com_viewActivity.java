package com.hiwhuUI.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.util.CommentExpandableListView;
import com.hiwhuUI.Activity.util.ExpandAdapter_Comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import HttpConnect.GetCommentAndReply;
import HttpConnect.GetCurrentActivity;
import HttpConnect.HttpUtil;
import data.staticData;
import entity.CommentAndReply;
import entity.CommentCard;
import entity.Reply;
import entity.ReplyCard;
import okhttp3.Call;
import okhttp3.Response;

import android.widget.TextView;

public class com_viewActivity extends AppCompatActivity {
    private String activity_id;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private ImageView image;
    private TextView name;
    private TextView starttime;
    private TextView endtime;
    private TextView resstarttime;
    private TextView resendtime;
    private TextView position;
    private TextView details;
    private CommentExpandableListView listView;
    private ExpandAdapter_Comment adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_view);

        activity_id = getIntent().getStringExtra("activity_id");
        GetCurrentActivity.GetActivityInit(activity_id);

        toolbar = (Toolbar) findViewById(R.id.toolbar_com_view);
        toolbar.setTitle("");
        toolbar_title = (TextView) findViewById(R.id.toolbar_com_view_text);
        toolbar_title.setText("活动详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.activity_edit:
                        Toast.makeText(com_viewActivity.this,"重新编辑",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(com_viewActivity.this,com_updateActivity.class);
                        intent.putExtra("activity_id",activity_id);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.activity_delete:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(com_viewActivity.this);
                        dialog.setTitle("你确定要删除该活动吗？");
                        dialog.setMessage("删除后将不可恢复");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                String url = staticData.getUrl()+"/DeleteActivityServlet"
                                        +"?activityID="+activity_id;
                                HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String s = response.body().string();
                                        Log.e("return---", s);
                                        if(s.equals("succeed")){
                                            Jump(true);
                                            staticData.setCurrentActivity(null);
                                        }else{
                                            Jump(false);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        Log.e("error",e.toString());
                                    }
                                });

                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            }
                        });
                        dialog.show();
                        return true;
                }
                return false;
            }
        });*/

        name = (TextView)findViewById(R.id.activity_name);
        starttime = (TextView)findViewById(R.id.activity_startTime);
        endtime = (TextView)findViewById(R.id.activity_endTime);
        resstarttime = (TextView)findViewById(R.id.join_startTime);
        resendtime = (TextView)findViewById(R.id.join_endTime);
        position = (TextView)findViewById(R.id.activity_position);
        details = (TextView) findViewById(R.id.details);

        name.setText(staticData.activity.getTitle());
        starttime.setText(staticData.activity.getStartTIme());
        endtime.setText(staticData.activity.getEndTime());
        if(staticData.activity.getRegistrationStartTime().equals("1000-12-31 00:00:00.0")){
            resstarttime.setText("不需要报名");
            resendtime.setText("不需要报名");
        }else{
            resstarttime.setText(staticData.activity.getRegistrationStartTime());
            resendtime.setText(staticData.activity.getRegistrationEndTime());
        }
        resstarttime.setText(staticData.activity.getRegistrationStartTime());
        resendtime.setText(staticData.activity.getRegistrationEndTime());

        if(staticData.activity.getLocation()!=null){
            String[] locations = staticData.activity.getLocation().split("\\|\\|");
            if(locations.length!=0)
                position.setText(locations[0]);
            else
                position.setText("地点未设置");
        }else{
            position.setText("地点未设置");
        }

        details.setText(staticData.activity.getActivityProfile());

        image = (ImageView)findViewById(R.id.activity_poster);
        Glide.with(getBaseContext()).load(staticData.getUrl()+"/"+staticData.activity.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .crossFade().into(image);

        listView = (CommentExpandableListView)findViewById(R.id.comment_list);
        GetCommentAndReply gcar = GetCommentAndReply.GetCollectionInit(activity_id);
        List<CommentAndReply> sList = gcar.sList;

        List<CommentCard> commentList = new ArrayList<>();
        List<CommentCard> commentList2 = new ArrayList<>();
        for(CommentAndReply car : sList){
            List<ReplyCard> rlycards = new ArrayList<>();
            List<Reply> rlys = car.getReplyList();
            for(Reply r : rlys){
                ReplyCard card = new ReplyCard(r.getReply_name(),r.getReply_content(),r.getReply_time());
                rlycards.add(card);
            }
            CommentCard cCard = new CommentCard(car.getCommentID(),car.getUserName(),
                    car.getUserHeadProtrait(),car.getContent(),car.getTime(),rlycards);
            commentList.add(cCard);
        }
        commentList2.addAll(commentList);
        initExpandableListView(commentList2);
    }

    private void initExpandableListView(final List<CommentCard> commentList){
        listView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new ExpandAdapter_Comment(this, commentList);
        listView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            listView.expandGroup(i);
        }

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                if(isExpanded){
                    expandableListView.collapseGroup(groupPosition);
                }else {
                    expandableListView.expandGroup(groupPosition, true);
                }
                return true;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int flatPos, long l) {
                //得到点击的父位置，子位置
                long packedPos = ((ExpandableListView)parent).getExpandableListPosition(flatPos);
                int groupPosition= ExpandableListView.getPackedPositionGroup(packedPos);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPos);
                if(childPosition == -1){//-1-父项; >=0-子项;
                    Intent intent = new Intent(com_viewActivity.this,data_editActivity.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("ref_comment_id",adapter.getGroup(groupPosition).getCommentId());
                    intent.putExtra("ref_comment_content",adapter.getGroup(groupPosition).getContent());
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    public void Jump(final boolean flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(flag){
                    Toast.makeText(com_viewActivity.this,"活动已删除！",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(com_viewActivity.this,"活动删除异常！",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toolbar跟menu
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(!staticData.isSponsorCanOpera()){
            menu.findItem(R.id.activity_edit).setVisible(false);
            menu.findItem(R.id.activity_delete).setVisible(false);
            invalidateOptionsMenu();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

/*        if(id==android.R.id.home){
            finish();
            return true;
        }
        else return false;*/

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.activity_edit:
                Toast.makeText(com_viewActivity.this,"重新编辑",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(com_viewActivity.this,com_updateActivity.class);
                intent.putExtra("activity_id",activity_id);
                startActivity(intent);
                finish();
                return true;
            case R.id.activity_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(com_viewActivity.this);
                dialog.setTitle("你确定要删除该活动吗？");
                dialog.setMessage("删除后将不可恢复");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String url = staticData.getUrl()+"/DeleteActivityServlet"
                                +"?activityID="+activity_id;
                        HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String s = response.body().string();
                                Log.e("return---", s);
                                if(s.equals("succeed")){
                                    Jump(true);
                                    staticData.setCurrentActivity(null);
                                }else{
                                    Jump(false);
                                }
                            }
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("error",e.toString());
                            }
                        });

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                    }
                });
                dialog.show();
                return true;
        }
        return false;
    }
}