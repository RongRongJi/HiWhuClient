package com.hiwhuUI.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.MenuItem;

import com.hiwhu.hiwhuclient.R;


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
                                return true;
                            case R.id.activity_delete:
                                AlertDialog.Builder dialog = new AlertDialog.Builder(com_ViewActivity.this);
                                dialog.setTitle("你确定要删除该活动吗？");
                                dialog.setMessage("删除后将不可恢复");
                                dialog.setCancelable(false);
                                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
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
    }
}
