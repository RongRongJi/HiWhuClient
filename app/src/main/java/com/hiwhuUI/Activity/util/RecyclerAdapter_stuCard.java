package com.hiwhuUI.Activity.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hiwhu.hiwhuclient.R;

import java.io.IOException;
import java.util.List;

import HttpConnect.HttpUtil;
import data.staticData;
import entity.Activity;
import entity.StuCard;
import okhttp3.Call;
import okhttp3.Response;

public class RecyclerAdapter_stuCard extends RecyclerView.Adapter<RecyclerAdapter_stuCard.ViewHolder>{

    private Context context;
    private List<StuCard> stuCardList;
    private int lock = 0;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView card_img;
        TextView card_studenNum;
        TextView card_name;
        Button card_btn;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view;
            card_img = (ImageView)view.findViewById(R.id.card_stu_img);
            card_studenNum = (TextView)view.findViewById(R.id.card_stu_studentNum);
            card_name = (TextView)view.findViewById(R.id.card_stu_name);
            card_btn = (Button)view.findViewById(R.id.card_stu_btn);
        }


    }

    public RecyclerAdapter_stuCard(List<StuCard> list) {
        this.stuCardList = list;
    }

    @NonNull
    @Override
    public RecyclerAdapter_stuCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_stu_card,parent,false);
        return new RecyclerAdapter_stuCard.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_stuCard.ViewHolder holder, int position) {
        final StuCard stuCard = stuCardList.get(position);
        Log.e("studentinfo----:",stuCard.getStudentNum()+" "+stuCard.getName()+" "+stuCard.getImg_id());
        holder.card_studenNum.setText(stuCard.getStudentNum());
        holder.card_name.setText(stuCard.getName());
        holder.card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button btn = (Button)v;
                String activityID = staticData.getCurrentActivity();
                //Toast.makeText(context, stuCard.getStudentNum(), Toast.LENGTH_SHORT).show();
                String url = staticData.getUrl()+"/PassApplyServlet?studentID="+stuCard.getStudentNum()+"&activityID="+activityID;
                Log.e("url---",url);
                //后台处理学生审核
                HttpUtil.sendOkHttpRequest(url,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.e("return---",s);
                        if(s.equals("pass\r\nMessageInsert\r\n")){
                            lock=1;
                        }else{
                            lock=2;
                        }
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                });
                while (lock==0){}
                if(lock==1){
                    btn.setText("已通过");
                    btn.setEnabled(false);
                    lock=0;
                }else{
                    Toast.makeText(context,"操作超时",Toast.LENGTH_LONG).show();
                }

            }
        });
        //Glide.with(context).load(stuCard.getImg_id()).into(holder.card_img);
        Glide.with(context).load(stuCard.getImg_id())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .crossFade().into(holder.card_img);
    }

    @Override
    public int getItemCount() {
        return stuCardList.size();
    }


}