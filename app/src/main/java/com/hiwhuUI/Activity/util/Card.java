package com.hiwhuUI.Activity.util;

public class Card {
    private String title;
    private String img_id;
    private String time;
    private String location;
    private boolean signup;
    private boolean star;

    public Card(String title,String img_id,String time,String location,boolean signup){
        this.title = title;
        this.img_id =img_id;
        this.time = time;
        this.location = location;
        this.signup = signup;
        //this.star = star;
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

    public boolean isSignup() {
        return signup;
    }

/*    public void setStar(boolean star) {
        this.star = star;
    }

    public boolean isStar() {
        return star;
    }*/
}
