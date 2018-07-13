package entity;

public class ActivityCard {
    private String activity_id;
    private String title;
    private String img_id;
    private String time;
    private String location;

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

}