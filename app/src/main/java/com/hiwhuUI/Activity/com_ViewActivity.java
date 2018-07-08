package com.hiwhuUI.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.MenuItem;

import com.hiwhu.hiwhuclient.R;

import java.io.IOException;

import HttpConnect.HttpUtil;
import data.staticData;
import okhttp3.Call;
import okhttp3.Response;


public class com_ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_view);

        findViewById(R.id.edit_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.实例化
                PopupMenu popupMenu=new PopupMenu(com_ViewActivity.this,view);
                //2.加载Menu资源
                getMenuInflater().inflate(R.menu.edit,popupMenu.getMenu());

                //3.为弹出菜单设置点击监听
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.activity_edit:
                                Toast.makeText(com_ViewActivity.this,"重新编辑",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(com_ViewActivity.this,com_updateActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.activity_delete:
                                AlertDialog.Builder dialog = new AlertDialog.Builder(com_ViewActivity.this);
                                dialog.setTitle("你确定要删除该活动吗？");
                                dialog.setMessage("删除后将不可恢复");
                                dialog.setCancelable(false);
                                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        String url = staticData.getUrl()+"/DeleteActivityServlet"
                                                +"?activityID="+staticData.getCurrentActivity();
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
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                //4.显示弹出菜单
                popupMenu.show();
            }
        });

        ImageButton backButton = (ImageButton)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staticData.setCurrentActivity(null);
                Intent intent = new Intent(com_ViewActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
    public void Jump(final boolean flag){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(flag){
                    Toast.makeText(com_ViewActivity.this,"活动已删除！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(com_ViewActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(com_ViewActivity.this,"活动删除异常！",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
