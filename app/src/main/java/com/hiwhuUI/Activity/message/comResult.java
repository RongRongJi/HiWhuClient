package com.hiwhuUI.Activity.message;

/** created by 王清玉
 *
 **/
public class comResult {
    private String activityName;
    private int backId;
    private String state;

    public comResult(String activityName,int backId,String state){
        this.activityName = activityName;
        this.backId = backId;
        this.state = state;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getBackId(){
        return backId;
    }

    public String getState() {
        return state;
    }

}
