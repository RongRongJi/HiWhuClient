package data;

/**
 * Created by ASUS on 2018/7/7.
 */

public class staticData {
    static String url = "http://192.168.23.1:8080";
    static String studentID = null;
    static String sponsorID = null;
    static String currentActivity = null;
    static int userType = 0; //0-未登录; 1-学生; 2-发布方;

    public static String getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(String currentActivity) {
        staticData.currentActivity = currentActivity;
    }

    public static String getUrl(){
        return url;
    }

    public static String getStudentID() {
        return studentID;
    }

    public static void setStudentID(String studentID) {
        staticData.studentID = studentID;
    }

    public static String getSponsorID() {
        return sponsorID;
    }

    public static void setSponsorID(String sponsorID) {
        staticData.sponsorID = sponsorID;
    }

    public static int getUserType() {
        return userType;
    }

    public static void setUserType(int userType) {
        staticData.userType = userType;
    }
}
