package com.hiwhuUI.Activity.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.hiwhu.hiwhuclient.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    public static ListFragment newInstance(String info) {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        args.putString("option", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, null);

        String str = getArguments().getString("option");

        List<Card> cardList=new ArrayList<>();
        cardList.clear();
        RecyclerAdapter recyclerAdapter;

        if(str.equals("待审核")){
            for (int i = 0; i <5 ; i++) {
                //获取后台活动数据
                Card a = new Card("标题"+i+1,"@drawable/activity_small","时间"+i+1,"地点"+i+1,true);
                cardList.add(a);
            }
            recyclerAdapter = new RecyclerAdapter(cardList,0);

        }
        else if(str.equals("待参加")){
            for (int i = 0; i <5 ; i++) {
                //获取后台活动数据
                Card a = new Card("标题"+i+2,"@drawable/activity_small","时间"+i+2,"地点"+i+2,true);
                cardList.add(a);
            }
            recyclerAdapter = new RecyclerAdapter(cardList,0);
        }
        else if(str.equals("已参加")){
            for (int i = 0; i <5 ; i++) {
                //获取后台活动数据
                Card a = new Card("标题"+i+3,"@drawable/activity_small","时间"+i+3,"地点"+i+3,true);
                cardList.add(a);
            }
            recyclerAdapter = new RecyclerAdapter(cardList,0);
        }
        else if(str.equals("收藏")){
            for (int i = 0; i <5 ; i++) {
                //获取后台活动数据
                Card a = new Card("标题"+i+4,"@drawable/activity_small","时间"+i+4,"地点"+i+4,true);
                cardList.add(a);
            }
            recyclerAdapter = new RecyclerAdapter(cardList,1);
        }
        else if(str.equals("审核")){
            for (int i = 0; i <5 ; i++) {
                //获取后台活动数据
                Card a = new Card("标题"+i+5,"@drawable/activity_small","时间"+i+5,"地点"+i+5,true);
                cardList.add(a);
            }
            recyclerAdapter = new RecyclerAdapter(cardList,2);
        }
        else if(str.equals("发布历史")){
            for (int i = 0; i <5 ; i++) {
                //获取后台活动数据
                Card a = new Card("标题"+i+6,"@drawable/activity_small","时间"+i+6,"地点"+i+6,true);
                cardList.add(a);
            }
            recyclerAdapter = new RecyclerAdapter(cardList,0);
        }
        else{
            for (int i = 0; i <5 ; i++) {
                //获取后台活动数据
                Card a = new Card("标题"+i,"@drawable/activity_small","时间"+i,"地点"+i,true);
                cardList.add(a);
            }
            recyclerAdapter = new RecyclerAdapter(cardList,0);
        }

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.test_card_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(recyclerAdapter);

        return view;

    }
}