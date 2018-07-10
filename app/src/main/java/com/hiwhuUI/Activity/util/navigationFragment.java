package com.hiwhuUI.Activity.util;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.ListActivity;
import com.hiwhuUI.Activity.LoginActivity;
import com.hiwhuUI.Activity.com_dataActivity;
import com.hiwhuUI.Activity.com_describeActivity;
import com.hiwhuUI.Activity.stu_dataActivity;

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
            if(userType==1){
                view = inflater.inflate(R.layout.activity_stu_data, null);

                ImageView back = (ImageView) view.findViewById(R.id.stu_h_back);
                ImageView head = (ImageView) view.findViewById(R.id.stu_h_head);
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
                Glide.with(this).load(R.drawable.stu_data_head)
                        .bitmapTransform(new CropCircleTransformation(getContext()))
                        .into(head);
                //取消按钮
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new  Intent(getActivity(),LoginActivity.class);
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
                view = inflater.inflate(R.layout.activity_com_data, null);

                ImageView back = (ImageView) view.findViewById(R.id.com_h_back);
                ImageView head = (ImageView) view.findViewById(R.id.com_h_head);
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
                Glide.with(this).load(R.drawable.com_data_head)
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
                //取消按钮
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new  Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
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
            view = inflater.inflate(R.layout.fragment_navigation, null);
            TextView textView = (TextView) view.findViewById(R.id.nagative_textView);
            textView.setText(getArguments().getString("info"));
        }
        return view;
    }
}
