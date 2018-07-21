package entity;

/**
 * Created by 韩黎明
 */
public class StuCard {

    private String studentNum;
    private String name;
    private String img_id;

    public StuCard(String studentNum, String name,String img_id){
        this.studentNum = studentNum;
        this.name = name;
        this.img_id = img_id;
    }

    public String getName() {
        return name;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public String getImg_id() {
        return img_id;
    }
}