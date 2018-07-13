package com.hiwhuUI.Activity.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiwhu.hiwhuclient.R;


import java.util.ArrayList;
import java.util.List;

import data.staticData;
import entity.Activity;
import entity.ActivityCard;

public class TabFragment extends Fragment {

    private int TAG;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        List<ActivityCard> cardList=new ArrayList<>();
        cardList.clear();

        List<Activity> list = staticData.activityList;
        switch (TAG){
            case staticData.TUIJIAN:
                for (int i = 0; i <list.size() ; i++) {
                    //获取后台活动数据
                    ActivityCard a = list.get(i).toActivityCard();
                    a.setSignup(true);
                    cardList.add(a);
                }
                break;
            default:
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getType().equals(String.valueOf(TAG))){
                        ActivityCard a = list.get(i).toActivityCard();
                        a.setSignup(true);
                        cardList.add(a);
                    }
                }
        }


        RecyclerAdapter_activityCard recyclerAdapter;
        if(staticData.getUserType()==1){
            recyclerAdapter = new RecyclerAdapter_activityCard(cardList,1);
        }
        else{
            recyclerAdapter = new RecyclerAdapter_activityCard(cardList,0);
        }

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.tab_card_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(recyclerAdapter);


        return view;
    }

    public void setTAG(int TAG){
        this.TAG = TAG;
    }
}
