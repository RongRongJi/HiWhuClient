package com.hiwhuUI.Activity.util;

import android.content.ContentUris;
import android.content.Context;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.ListActivity;
import com.hiwhuUI.Activity.SignupActivity;
import com.hiwhuUI.Activity.com_viewActivity;
import com.hiwhuUI.Activity.process.DetailActivity;
import com.hiwhuUI.Activity.stu_viewActivity;

import java.util.List;

import HttpConnect.GetAllActivity;
import HttpConnect.GetCurrentActivity;
import data.staticData;
import entity.ActivityCard;

public class RecyclerAdapter_activityCard extends RecyclerView.Adapter<RecyclerAdapter_activityCard.ViewHolder> {

    private Context context;
    private List<ActivityCard> cardList;
    private int option; //0-不显示按钮; 1-显示报名按钮; 2-显示审核按钮;
    private final int DELETE = 3;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView card_img;
        TextView card_title;
        TextView card_time;
        TextView card_location;
        TextView card_btn;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view;
            card_img = (ImageView)view.findViewById(R.id.card_activity_img);
            card_title = (TextView)view.findViewById(R.id.card_activity_title);
            card_time = (TextView)view.findViewById(R.id.card_activity_time);
            card_location = (TextView)view.findViewById(R.id.card_activity_location);
            card_btn = (TextView)view.findViewById(R.id.card_activity_btn);
        }
    }

    public RecyclerAdapter_activityCard(List<ActivityCard> list, int option) {
        this.cardList = list;
        this.option = option;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_activity_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ActivityCard activityCard = cardList.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(staticData.getUserType()==1){
                    Intent intent = new Intent(context, stu_viewActivity.class);
                    intent.putExtra("activity_id",activityCard.getActivity_id());
                    context.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(context, com_viewActivity.class);
                    intent.putExtra("activity_id",activityCard.getActivity_id());
                    if(staticData.isSponsorCanOpera()) ((ListActivity)context).startActivityForResult(intent,DELETE);
                    else context.startActivity(intent);
                }
            }
        });
        holder.card_title.setText(activityCard.getTitle());
        holder.card_time.setText(activityCard.getTime());
        if(activityCard.getLocation()==null)
            holder.card_location.setText("地点未设置");
        else{
            String[] tmp = activityCard.getLocation().split("\\|\\|");
            holder.card_location.setText(tmp[0]);
        }
        switch (option){
            case 0:
                holder.card_btn.setVisibility(View.GONE);
                break;
            case 2:
                holder.card_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ListActivity.class);
                        intent.putExtra("id",7);
                        intent.putExtra("activity_id",activityCard.getActivity_id());
                        context.startActivity(intent);
                    }
                });
                break;
        }



        Glide.with(context).load(activityCard.getImg_id())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .crossFade().into(holder.card_img);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public void resetCardList(List<ActivityCard> list){
        this.cardList.clear();
        this.cardList.addAll(list);
        notifyDataSetChanged();
    }
}