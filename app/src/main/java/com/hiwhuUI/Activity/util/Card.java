package com.hiwhuUI.Activity.util;

public class Card {
    private String title;
    private String img_id;
    private String time;
    private String location;
    private int state;  //0-未报名/审核未开始; 1-待审核/审核; 2-待参加; 3-已参加/已结束;
    //private boolean star;

    public Card(String title,String img_id,String time,String location,int state){
        this.title = title;
        this.img_id =img_id;
        this.time = time;
        this.location = location;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getImg_id() {
        return img_id;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public int getState() {
        return state;
    }
}
