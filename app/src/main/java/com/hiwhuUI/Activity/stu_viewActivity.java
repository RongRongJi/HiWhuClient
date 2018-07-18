package com.hiwhuUI.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.util.CommentExpandableListView;
import com.hiwhuUI.Activity.util.ExpandAdapter_Comment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import HttpConnect.GetActivityBySponsorID;
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

import static com.hiwhuUI.Activity.process.Colors.colors;

public class stu_viewActivity extends AppCompatActivity {

    private String LAT_MAP = null;
    private String LONG_MAP = null;

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
    private int state = 0;  //获取活动状态, 0-可以报名; 1-报名截止; 2-不需要报名;
    private GetCommentAndReply gcar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_view);

        activity_id = getIntent().getStringExtra("activity_id");

        //收藏初始化
        if(staticData.getStudentID()!=null){
            if(GetCurrentCollection.list==null){
                GetCurrentCollection.GetCollectionInit(staticData.getStudentID());
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar_stu_view);
        toolbar.setTitle("");
        toolbar_title = (TextView) findViewById(R.id.toolbar_stu_view_text);
        toolbar_title.setText("活动详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        unstar = getResources().getDrawable(R.drawable.ic_star_border_black_24dp);
        stared = getResources().getDrawable(R.drawable.ic_star_black_24dp);

        btn_star = findViewById(R.id.bottom_star);
        star = GetCurrentCollection.isStar(activity_id);
        if(star) btn_star.setCompoundDrawablesWithIntrinsicBounds(null,stared,null,null);
        else btn_star.setCompoundDrawablesWithIntrinsicBounds(null,unstar,null,null);

        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        btn_comment = findViewById(R.id.bottom_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stu_viewActivity.this, data_editActivity.class);
                intent.putExtra("activity_id", activity_id);
                startActivity(intent);
            }
        });



        name = (TextView)findViewById(R.id.activity_name);
        starttime = (TextView)findViewById(R.id.activity_startTime);
        endtime = (TextView)findViewById(R.id.activity_endTime);
        resstarttime = (TextView)findViewById(R.id.join_startTime);
        resendtime = (TextView)findViewById(R.id.join_endTime);
        position = (TextView)findViewById(R.id.activity_position);
        details = (TextView) findViewById(R.id.details);

        name.setText("加载中...");
        starttime.setText("加载中...");
        endtime.setText("加载中...");
        resstarttime.setText("加载中...");
        resendtime.setText("加载中...");
        position.setText("加载中...");
        details.setText("加载中...");

        image = (ImageView)findViewById(R.id.activity_poster);
        Glide.with(getBaseContext()).load(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .crossFade().into(image);

        map_btn = (ImageView)findViewById(R.id.map_menu);
        //注册上下文浮动菜单
        registerForContextMenu(map_btn);

        listView = (CommentExpandableListView)findViewById(R.id.comment_list);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            createProgressBar();
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            activity_id = getIntent().getStringExtra("activity_id");
                            GetCurrentActivity.GetActivityInit(activity_id);
                            //gcar = GetCommentAndReply.GetCollectionInit(activity_id);
                            Message msg = new Message();
                            msg.what=0;
                            handler.sendMessage(msg);
                            new Thread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            activity_id = getIntent().getStringExtra("activity_id");
                                            //GetCurrentActivity.GetActivityInit(activity_id);
                                            gcar = GetCommentAndReply.GetCollectionInit(activity_id);
                                            Message msg = new Message();
                                            msg.what=1;
                                            handler.sendMessage(msg);
                                        }
                                    }
                            ).start();
                        }
                    }
            ).start();
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    initInfo();
                    initImage();
                    //initComment();
                    initUI();
                    break;
                case 1:
                    initComment();
                    break;
            }
        }
    };

    private void initInfo(){
        btn_signup = findViewById(R.id.bottom_signup);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time=df.format(new Date());// new Date()为获取当前系统时间
        if(staticData.activity.getRegistrationStartTime().equals("1000-12-31 00:00:00.0")) state=2;
        else if(GetActivityBySponsorID.compare(staticData.activity.getRegistrationEndTime(),time)) state=1;

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
            case 2:
                btn_signup.setVisibility(View.GONE);
                break;
        }
        name.setText(staticData.activity.getTitle());
        starttime.setText(staticData.activity.getStartTIme());
        endtime.setText(staticData.activity.getEndTime());
        if(state==2){
            resstarttime.setText("不需要报名");
            resendtime.setText("不需要报名");
        }else{
            resstarttime.setText(staticData.activity.getRegistrationStartTime());
            resendtime.setText(staticData.activity.getRegistrationEndTime());
        }
        if(staticData.activity.getLocation()!=null){
            String[] locations = staticData.activity.getLocation().split("\\|\\|");
            if(locations.length!=0){
                position.setText(locations[0]);
                LAT_MAP = locations[1];
                LONG_MAP = locations[2];
            }
            else
                position.setText("地点未设置");
        }else{
            position.setText("地点未设置");
        }
        details.setText(staticData.activity.getActivityProfile());


    }

    private void initImage(){
        image = (ImageView)findViewById(R.id.activity_poster);
        Glide.with(getBaseContext()).load(staticData.getUrl()+"/"+staticData.activity.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .crossFade().into(image);
    }

    private void initComment(){
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


    private void initUI(){
        image.setFocusable(true);
        image.setFocusableInTouchMode(true);
        image.requestFocus();
        progressBar.setVisibility(View.GONE);
    }

    private void createProgressBar(){
        Context mContext=this;
        //整个Activity布局的最终父布局,参见参考资料
        FrameLayout rootFrameLayout=(FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams=
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        progressBar=new ProgressBar(mContext);
        progressBar.setLayoutParams(layoutParams);
        progressBar.setVisibility(View.VISIBLE);
        ThreeBounce doubleBounce = new ThreeBounce();
        doubleBounce.setBounds(0, 0, 100, 100);
        doubleBounce.setColor(colors[7]);
        progressBar.setIndeterminateDrawable(doubleBounce);
        rootFrameLayout.addView(progressBar);

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
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.map,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(LAT_MAP==null||LONG_MAP==null){
            Toast.makeText(this,"该主办方未设置地点",Toast.LENGTH_SHORT).show();
            return false;
        }
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