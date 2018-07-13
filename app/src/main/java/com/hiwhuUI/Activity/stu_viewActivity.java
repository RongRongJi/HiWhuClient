package com.hiwhuUI.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.util.CommentExpandableListView;
import com.hiwhuUI.Activity.util.ExpandAdapter_Comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import HttpConnect.GetAllActivity;
import HttpConnect.GetCommentAndReply;
import HttpConnect.GetCurrentActivity;
import HttpConnect.GetCurrentCollection;
import HttpConnect.HttpUtil;
import data.staticData;
import entity.CommentAndReply;
import entity.CommentCard;
import entity.Reply;
import entity.ReplyCard;
import okhttp3.Call;
import okhttp3.Response;

public class stu_viewActivity extends AppCompatActivity {

    private static final String LAT_MAP ="30.5387500000";
    private static final String LONG_MAP ="114.3725800000";

    private TextView name;
    private TextView starttime;
    private TextView endtime;
    private TextView resstarttime;
    private TextView resendtime;
    private TextView position;
    private ImageView image;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private String activity_id;
    private ImageView map_btn;
    private boolean star = false;
    private Drawable unstar;
    private Drawable stared;
    private Button btn_star;
    private Button btn_comment;
    private Button btn_signup;
    private TextView details;
    private CommentExpandableListView listView;
    private ExpandAdapter_Comment adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_view);

        activity_id = getIntent().getStringExtra("activity_id");
        GetCurrentActivity.GetActivityInit(activity_id);

        //收藏初始化
        if(staticData.getStudentID()!=null){
            if(GetCurrentCollection.list==null){
                GetCurrentCollection.GetCollectionInit(staticData.getStudentID());
            }
        }

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_stu_view);
        toolbar.setTitle("");
        toolbar_title = (TextView) findViewById(R.id.toolbar_stu_view_text);
        toolbar_title.setText("活动详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        unstar = getResources().getDrawable(R.drawable.ic_star_border_black_24dp);
        stared = getResources().getDrawable(R.drawable.ic_star_black_24dp);

        btn_star = findViewById(R.id.bottom_star);
        if(staticData.getStudentID()!=null){
            star = GetCurrentCollection.isStar(activity_id);
        }

        if(star) btn_star.setCompoundDrawablesWithIntrinsicBounds(null,stared,null,null);
        else btn_star.setCompoundDrawablesWithIntrinsicBounds(null,unstar,null,null);

        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(staticData.getStudentID()==null){
                    Toast.makeText(stu_viewActivity.this,"请使用学生账号收藏！",Toast.LENGTH_LONG).show();
                }else{
                    String url = staticData.getUrl()+"/StuCollectActivityServlet?studentID="+staticData.getStudentID()
                            +"&activityID="+activity_id;
                    HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if(s.equals("succeed\r\n")){
                                Jump(true);
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

            }
        });
        btn_comment = findViewById(R.id.bottom_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stu_viewActivity.this, data_editActivity.class);
                intent.putExtra("activity_id",activity_id);
                startActivity(intent);
            }
        });
        btn_signup = findViewById(R.id.bottom_signup);
        int state = 0;  //通过 activity_id 获取活动状态, 0-可以报名; 1-报名截止;
        switch (state){
            case 0:
                btn_signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(stu_viewActivity.this, SignupActivity.class);
                        intent.putExtra("activity_id",activity_id);
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                btn_signup.setText("报名已截止");
                btn_signup.setClickable(false);
                break;
        }

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
        resstarttime.setText(staticData.activity.getRegistrationStartTime());
        resendtime.setText(staticData.activity.getRegistrationEndTime());
        position.setText(staticData.activity.getLocation());
        details.setText(staticData.activity.getActivityProfile());

        image = (ImageView)findViewById(R.id.activity_poster);
        Glide.with(getBaseContext()).load(staticData.getUrl()+"/"+staticData.activity.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .crossFade().into(image);

        map_btn = (ImageView)findViewById(R.id.map_menu);
        //注册上下文浮动菜单
        registerForContextMenu(map_btn);

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

    //收藏动作
    private void Jump(final boolean flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(flag){
                    if(star){
                        //从收藏表中删除 studenId,activity_id
                        Toast.makeText(stu_viewActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                        star = false;
                        GetCurrentCollection.removeStarList(activity_id);
                        btn_star.setCompoundDrawablesWithIntrinsicBounds(null,unstar,null,null);
                    }
                    else {
                        //在收藏表中插入 studenId,activity_id
                        Toast.makeText(stu_viewActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        star = true;
                        GetCurrentCollection.addStarList(activity_id);
                        btn_star.setCompoundDrawablesWithIntrinsicBounds(null,stared,null,null);
                    }
                }else{
                    Toast.makeText(stu_viewActivity.this,"操作超时！",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    Intent intent = new Intent(stu_viewActivity.this,data_editActivity.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("ref_comment_id",adapter.getGroup(groupPosition).getCommentId());
                    intent.putExtra("ref_comment_content",adapter.getGroup(groupPosition).getContent());

                    startActivity(intent);
                }
                return true;
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.map,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.map_gd:
                Intent intent;
                if (isAvilible(this, "com.autonavi.minimap")) {
                    goToNaviActivity(this,"test",null,LAT_MAP,LONG_MAP,"1","2");
                }else{
                    Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                return true;
            case R.id.map_tx:
                Intent intent2;
                if (isAvilible(this, "com.tencent.map")) {
                    gotoTengxun(this,LAT_MAP,LONG_MAP);
                }else{
                    Toast.makeText(this, "您尚未安装腾讯地图", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("market://details?id=com.tencent.map");
                    intent2 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent2);
                }
                return true;
            case R.id.map_bd:
                Intent intent3;
                if (isAvilible(this, "com.baidu.BaiduMap")) {
                    goToBaiduActivity(this,LAT_MAP,LONG_MAP);
                }else{
                    Toast.makeText(this, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                    intent3 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent3);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    /*
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 启动高德App进行导航
     *
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname 非必填 POI 名称
     * @param lat 必填 纬度
     * @param lon 必填 经度
     * @param dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style 必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static  void goToNaviActivity(Context context,String sourceApplication , String poiname , String lat , String lon , String dev , String style){
        StringBuffer stringBuffer  = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)){
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 启动百度App进行导航
     *
     * @param lat 必填 纬度
     * @param lon 必填 经度
     */
    public static  void goToBaiduActivity(Context context , String lat , String lon)
    {

        // 百度地图
        Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/geocoder?location=" + lat + "," + lon));
        context.startActivity(naviIntent);
    }

    /**
     * 启动腾讯地图App进行导航
     *
     * @param lat 必填 纬度
     * @param lon 必填 经度
     */
    public static  void gotoTengxun(Context context , String lat , String lon){

        // 腾讯地图
        Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to=目的地&tocoord=" + lat + "," + lon + "&policy=0&referer=appName"));
        context.startActivity(naviIntent);

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
}