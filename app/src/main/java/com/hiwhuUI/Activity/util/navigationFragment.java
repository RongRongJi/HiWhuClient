package com.hiwhuUI.Activity.util;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.ListActivity;
import com.hiwhuUI.Activity.LoginActivity;
import com.hiwhuUI.Activity.message.McAdapter;
import com.hiwhuUI.Activity.message.comM_result;
import com.hiwhuUI.Activity.message.comMessage;
import com.hiwhuUI.Activity.com_describeActivity;
import com.hiwhuUI.Activity.message.comWord;
import com.hiwhuUI.Activity.message.stuReply;
import com.hiwhuUI.Activity.message.stuResult;

import java.util.ArrayList;
import java.util.List;

import HttpConnect.GetCurrentSponsor;
import HttpConnect.GetCurrentStudent;
import data.staticData;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class navigationFragment extends Fragment {
    public static navigationFragment newInstance(String info) {
        Bundle args = new Bundle();
        navigationFragment fragment = new navigationFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String str = getArguments().getString("info");
        int userType = staticData.getUserType();
        View view = null;
        if(str.equals("主页")){
            Log.e("error","进入主页");
            view = inflater.inflate(R.layout.fragment_navigation, null);
            TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
            tabs.addTab(tabs.newTab().setText("推荐").setTag(0));
            tabs.addTab(tabs.newTab().setText("竞赛").setTag(1));
            tabs.addTab(tabs.newTab().setText("体育").setTag(2));
            tabs.addTab(tabs.newTab().setText("文艺").setTag(3));
            tabs.addTab(tabs.newTab().setText("公益").setTag(4));
            tabs.addTab(tabs.newTab().setText("讲座").setTag(5));
            tabs.addTab(tabs.newTab().setText("其他").setTag(6));

            ViewPager tab_viewPager = (ViewPager) view.findViewById(R.id.tab_viewPager);
            TabAdapter tab_adapter = new TabAdapter(getChildFragmentManager());
            for(int i=0;i<tabs.getTabCount();i++){
                TabFragment tab = new TabFragment();
                tab.setTAG(i);
                tab_adapter.addFragment(tab);
            }

            tab_viewPager.setAdapter(tab_adapter);
            tabs.setupWithViewPager(tab_viewPager);
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        }
        else if(str.equals("我的")){
            Log.e("error","进入我的");
            if(userType==1){
                GetCurrentStudent.GetStudentInit();
                view = inflater.inflate(R.layout.activity_stu_data, null);

                ImageView back = (ImageView) view.findViewById(R.id.stu_h_back);
                ImageView head = (ImageView) view.findViewById(R.id.stu_h_head);
                //获取后台url
                String url = staticData.getUrl()+"/"+staticData.student.getHeadProtrait();

                //获取后台学生用户名
                TextView  studentName= (TextView)view.findViewById(R.id.stu_data_username);
                studentName.setText(staticData.student.getUserName());


                Button button1 = (Button) view.findViewById(R.id.stu_data_cancel) ;
                RelativeLayout relativeLayout1 = (RelativeLayout) view.findViewById(R.id.stu_data_unchecked);
                RelativeLayout relativeLayout2 = (RelativeLayout) view.findViewById(R.id.stu_data_checked);
                RelativeLayout relativeLayout3 = (RelativeLayout) view.findViewById(R.id.stu_data_taken);
                RelativeLayout relativeLayout4 = (RelativeLayout) view.findViewById(R.id.stu_data_store);
                //设置背景磨砂效果
                Glide.with(this).load(R.drawable.stu_data_back)
                        .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getContext()))
                        .into(back);

                //设置圆形头像
                Glide.with(this).load(url).skipMemoryCache(true) // 不使用内存缓存
                        .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                        .bitmapTransform(new CropCircleTransformation(getContext()))
                        .into(head);
                //取消按钮
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new  Intent(getActivity(),LoginActivity.class);
                        staticData.setSponsorCanOpera(false);
                        staticData.setStudentID(null);
                        staticData.setSponsorID(null);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                //待审核
                relativeLayout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra("id",1);
                        startActivity(intent);
                    }
                });
                //待参加
                relativeLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra("id",2);
                        startActivity(intent);
                    }
                });
                //已参加
                relativeLayout3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra("id",3);
                        startActivity(intent);
                    }
                });
                //收藏
                relativeLayout4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra("id",4);
                        startActivity(intent);
                    }
                });

            }
            else if(userType==2){
                GetCurrentSponsor.GetSponsorInit();
                view = inflater.inflate(R.layout.activity_com_data, null);

                ImageView back = (ImageView) view.findViewById(R.id.com_h_back);
                ImageView head = (ImageView) view.findViewById(R.id.com_h_head);
                //获取后台url
                String url = staticData.getUrl()+"/"+staticData.sponsor.getHeadProtrait();

                //获取后台主办方用户名
                TextView sponsorName = (TextView)view.findViewById(R.id.com_data_username);
                sponsorName.setText(staticData.sponsor.getSponsorName());


                Button button1 = (Button) view.findViewById(R.id.btn_com_data_cancel) ;
                RelativeLayout relativeLayout1 = (RelativeLayout) view.findViewById(R.id.com_data_describe);
                RelativeLayout relativeLayout2 = (RelativeLayout) view.findViewById(R.id.com_data_unchecked);
                //RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.com_data_review);
                RelativeLayout relativeLayout4 = (RelativeLayout) view.findViewById(R.id.com_data_history);
                //设置背景磨砂效果
                Glide.with(this).load(R.drawable.stu_data_back)
                        .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getContext()))
                        .into(back);
                //设置圆形头像
                Glide.with(this).load(url).skipMemoryCache(true) // 不使用内存缓存
                        .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                        .bitmapTransform(new CropCircleTransformation(getContext()))
                        .into(head);
                //社团简介跳转
                relativeLayout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),com_describeActivity.class);
                        startActivity(intent);
                    }
                });
                //退出登录
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new  Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        staticData.setSponsorID(null);
                        staticData.setStudentID(null);
                        staticData.setSponsorCanOpera(false);
                        getActivity().finish();
                    }
                });
                //审核报名人员
                relativeLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra("id",5);
                        startActivity(intent);
                    }
                });
                //活动回顾
                //发布历史
                relativeLayout4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra("id",6);
                        startActivity(intent);
                    }
                });


            }
        }
        else{
            Log.e("error","进入消息");
            if (userType == 1){
                view = inflater.inflate(R.layout.activity_message_com, null);
                final List<comMessage>  stuMessageList= new ArrayList<>();
                for (int i = 0;i<1;i++){
                    comMessage ms_reply = new comMessage("评论回复",R.drawable.ms_reply);
                    stuMessageList.add(ms_reply);
                    //comMessage ms_remind = new comMessage("收藏活动报名提醒",R.drawable.ms_remind);
                    //stuMessageList.add(ms_remind);
                    comMessage ms_result = new comMessage("报名结果",R.drawable.ms_result);
                    stuMessageList.add(ms_result);
                }
                McAdapter adapter = new McAdapter(getActivity(), R.layout.message_com_item,stuMessageList);
                ListView listView = (ListView) view.findViewById(R.id.message_com);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        comMessage commessage = stuMessageList.get(position);
                        switch (position){
                            case 0:
                                Intent intent = new Intent(getActivity(),stuReply.class);
                                startActivity(intent);
                                break;
                            //case 1:
                            //    Intent intent1 = new Intent(getActivity(),stuRemind.class);
                            //    startActivity(intent1);
                            //    break;
                            case 1:
                                Intent intent1 = new Intent(getActivity(),stuResult.class);
                                startActivity(intent1);
                        }
                    }
                });
            }
            else if (userType ==2){
                view = inflater.inflate(R.layout.activity_message_com, null);
                final List<comMessage> messageList = new ArrayList<>();
                for (int i = 0;i<1;i++){
                    comMessage mc_activity = new comMessage("上传的活动",R.drawable.mc_activity);
                    messageList.add(mc_activity);
                    comMessage mc_messgae = new comMessage("收到的留言",R.drawable.mc_message);
                    messageList.add(mc_messgae);
                }
                McAdapter adapter = new McAdapter(getActivity(), R.layout.message_com_item,messageList);
                ListView listView = (ListView) view.findViewById(R.id.message_com);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        comMessage commessage = messageList.get(position);
                        switch (position){
                            case 0:
                                Intent intent = new Intent(getActivity(),comM_result.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(getActivity(),comWord.class);
                                startActivity(intent1);
                                break;
                        }
                    }
                });
            }

        }
        return view;
    }
}