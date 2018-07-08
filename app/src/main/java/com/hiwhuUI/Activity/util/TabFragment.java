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

public class TabFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        List<Card> cardList=new ArrayList<>();

        cardList.clear();
        for (int i = 0; i <5 ; i++) {
            //获取后台活动数据
            Card a = new Card("标题"+i+1,"@drawable/activity_small","时间"+i+1,"地点"+i+1,true);
            cardList.add(a);
        }

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(cardList);

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.tab_card_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(recyclerAdapter);


        return view;
    }
}
