package com.hiwhuUI.Activity.util;

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

import com.hiwhu.hiwhuclient.R;
import com.hiwhuUI.Activity.SignupActivity;
import com.hiwhuUI.Activity.stu_viewActivity;

import java.util.List;

import data.staticData;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Card> cardList;
    private int option; //0-不显示按钮; 1-显示报名按钮; 2-显示审核按钮;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView card_img;
        TextView card_title;
        TextView card_time;
        TextView card_location;
        Button card_btn;

        public ViewHolder(final View view) {
            super(view);
            cardView = (CardView)view;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), stu_viewActivity.class);
                    view.getContext().startActivity(intent);
                }
            });

            card_img = (ImageView)view.findViewById(R.id.card_img);
            card_title = (TextView)view.findViewById(R.id.card_title);
            card_time = (TextView)view.findViewById(R.id.card_time);
            card_location = (TextView)view.findViewById(R.id.card_location);
            card_btn = (Button)view.findViewById(R.id.card_btn);
            card_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), SignupActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public RecyclerAdapter(List<Card> list,int option) {
        this.cardList = list;
        this.option = option;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.card_title.setText(card.getTitle());
        holder.card_time.setText(card.getTime());
        holder.card_location.setText(card.getLocation());
        holder.card_btn.setEnabled(card.isSignup());
        switch (option){
            case 0:
                holder.card_btn.setVisibility(View.INVISIBLE);
                break;
            case 1:
                holder.card_btn.setText("我要报名");
                break;
            case 2:
                holder.card_btn.setText("审核");
                break;
        }
        //Glide.with(context).load(card.getImg_id()).into(holder.card_img);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
