package com.hiwhuUI.Activity.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hiwhu.hiwhuclient.R;

import java.util.ArrayList;
import java.util.List;

import HttpConnect.GetActivityBySponsorID;
import HttpConnect.GetAppliedActivity;
import HttpConnect.GetAppliedStudentByActivityID;
import HttpConnect.GetSearchResult;
import data.staticData;
import entity.Activity;
import entity.ActivityCard;
import entity.StuCard;
import entity.Student;

public class ListFragment extends Fragment {

    public static ListFragment newInstance(String info) {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        args.putString("option", info);
        fragment.setArguments(args);
        return fragment;
    }

    //重载newInstance 搜索用
    public static ListFragment newInstance(String info, String search){
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        args.putString("option", info);
        args.putString("search",search);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, null);

        String str = getArguments().getString("option");

        if(str.equals("待审核")){
            view = setActivityCardView(view,1);
        }
        else if(str.equals("待参加")){
            view = setActivityCardView(view,2);
        }
        else if(str.equals("已参加")){
            view = setActivityCardView(view,3);
        }
        else if(str.equals("收藏")){
            view = setActivityCardView(view,4);
        }
        else if(str.equals("审核活动")){
            view = setActivityCardView(view,5);
        }
        else if(str.equals("发布历史")){
            view = setActivityCardView(view,6);
        }
        else if(str.equals("搜索")){
            view = setActivityCardView(view,8);
        }
        else{
            //审核人员
            //Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
            staticData.setCurrentActivity(str);
            view = setStuCardView(view,str);
        }

        return view;

    }

    private View setActivityCardView(View view, int option){

        RecyclerAdapter_activityCard recyclerAdapter = null;

        List<Activity> list = staticData.activityList;

        switch (option){
            //分别获取后台活动数据 list
            case 1://学生待审核的活动列表
                GetAppliedActivity unchecked = GetAppliedActivity.GetActivityInit(0);
                List<Activity> uncheckedList = unchecked.activityList;
                recyclerAdapter = new RecyclerAdapter_activityCard(toActivityCardList(uncheckedList),0);
                break;
            case 2://学生待参加的活动列表
                GetAppliedActivity passed = GetAppliedActivity.GetActivityInit(1);
                List<Activity> waitList = passed.activityList;
                recyclerAdapter = new RecyclerAdapter_activityCard(toActivityCardList(waitList),0);
                break;
            case 3://学生参加过的活动
                GetAppliedActivity joined = GetAppliedActivity.GetActivityInit(2);
                List<Activity> joinedList = joined.activityList;
                recyclerAdapter = new RecyclerAdapter_activityCard(toActivityCardList(joinedList),0);
                break;
            case 4://学生收藏的活动
                recyclerAdapter = new RecyclerAdapter_activityCard(toActivityCardList(list),1);
                break;
            case 5://主办方审核活动
                GetActivityBySponsorID gas = GetActivityBySponsorID.GetActivityInit(staticData.getSponsorID());
                List<Activity> checkList = gas.registerAcitivtylist;
                recyclerAdapter = new RecyclerAdapter_activityCard(toActivityCardList(checkList),2);
                break;
            case 6://主办方的发布历史
                GetActivityBySponsorID gbs = GetActivityBySponsorID.GetActivityInit(staticData.getSponsorID());
                List<Activity> historyList = gbs.activityList;
                recyclerAdapter = new RecyclerAdapter_activityCard(toActivityCardList(historyList),0);
                break;
            case 8://搜索框
                String str = getArguments().getString("search");
                Log.e("search is",str);
                GetSearchResult gsr = GetSearchResult.GetActivityInit(str);
                List<Activity> searchList = gsr.activityList;
                recyclerAdapter = new RecyclerAdapter_activityCard(toActivityCardList(searchList),0);
                break;
        }


        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView_cardList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    private View setStuCardView(View view,String activity_id) {

        RecyclerAdapter_stuCard recyclerAdapter = null;
        GetAppliedStudentByActivityID gasba = GetAppliedStudentByActivityID.GetApplyInit(activity_id);
        List<Student> list = gasba.applylist;

        recyclerAdapter = new RecyclerAdapter_stuCard(toStuCardList(list));

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView_cardList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    private List<ActivityCard> toActivityCardList(List<Activity> list){

        List<ActivityCard> cardList =new ArrayList<>();

        for (int i = 0; i <list.size() ; i++) {
            ActivityCard a = list.get(i).toActivityCard();
            a.setSignup(true);
            cardList.add(a);
        }

        return cardList;
    }

    private List<StuCard> toStuCardList(List<Student> list){
        List<StuCard> cardList =new ArrayList<>();

        for (int i = 0; i <list.size() ; i++) {
            StuCard a = list.get(i).toStuCard();
            cardList.add(a);
        }

        return cardList;
    }

}