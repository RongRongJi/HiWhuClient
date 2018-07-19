package com.hiwhuUI.Activity.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiwhu.hiwhuclient.R;


import java.util.ArrayList;
import java.util.List;

import HttpConnect.GetAllActivity;
import data.staticData;
import entity.Activity;
import entity.ActivityCard;

public class TabFragment extends Fragment {

    private int TAG;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        List<ActivityCard> cardList=new ArrayList<>();
        cardList.clear();
        GetAllActivity.GetActivityInit();
        List<Activity> list = staticData.activityList;
        initList();
        switch (TAG){
            case staticData.TUIJIAN:
                for (int i = 0; i <list.size() ; i++) {
                    //获取后台活动数据
                    ActivityCard a = list.get(i).toActivityCard();
                    cardList.add(a);
                }
                break;
            default:
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getType().equals(String.valueOf(TAG))){
                        ActivityCard a = list.get(i).toActivityCard();
                        cardList.add(a);
                    }
                }
        }


        RecyclerAdapter_activityCard recyclerAdapter = new RecyclerAdapter_activityCard(cardList,0);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.tab_card_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(recyclerAdapter);

        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTab();
            }
        });

        return view;
    }
    private void refreshTab(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        //adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    public void initList(){

    }
    public void setTAG(int TAG){
        this.TAG = TAG;
    }
}
