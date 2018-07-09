package com.hiwhuUI.Activity.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

public class TabFragment extends Fragment {

    private int TAG;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        List<Card> cardList=new ArrayList<>();
        cardList.clear();

        List<Activity> list = staticData.activityList;
        switch (TAG){
            case staticData.TUIJIAN:
                for (int i = 0; i <list.size() ; i++) {
                    //获取后台活动数据
                    Card a = new Card(list.get(i).getTitle(),"@drawable/activity_small","时间:"+
                            list.get(i).getStartTIme()+"-"+list.get(i).getEndTime(),"地点:"+list.get(i).getLocation(),true);
                    cardList.add(a);
                }
                break;
            default:
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getType().equals(String.valueOf(TAG))){
                        Card a = new Card(list.get(i).getTitle(),"@drawable/activity_small","时间:"+
                                list.get(i).getStartTIme()+"-"+list.get(i).getEndTime(),"地点:"+list.get(i).getLocation(),true);
                        cardList.add(a);
                    }
                }
        }


        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(cardList);

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.tab_card_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(recyclerAdapter);


        return view;
    }

    public void setTAG(int TAG){
        this.TAG = TAG;
    }
}
