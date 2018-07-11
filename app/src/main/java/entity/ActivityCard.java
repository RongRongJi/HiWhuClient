package entity;

public class ActivityCard {
    private String activity_id;
    private String title;
    private String img_id;
    private String time;
    private String location;
    private boolean signup;
    private boolean star;

    public ActivityCard(String activity_id,String title, String img_id, String time, String location){
        this.activity_id = activity_id;
        this.title = title;
        this.img_id =img_id;
        this.time = time;
        this.location = location;
    }

    public String getActivity_id() {
        return activity_id;
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

    public void setSignup(boolean signup) {
        this.signup = signup;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    /*
    public boolean isStar() {
        return star;
    }*/
}