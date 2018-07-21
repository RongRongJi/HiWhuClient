package entity;

/**
 * Created by 赵紫微
 */
public class ReplyCard {
    private String reply_name;
    private String reply_content;
    private String reply_time;

    public ReplyCard(String reply_name,String reply_content,String reply_time){
        this.reply_name = reply_name;
        this.reply_content = reply_content;
        this.reply_time = reply_time;
    }

    public String getReply_name() {
        return reply_name;
    }

    public String getReply_content() {
        return reply_content;
    }

    public String getReply_time() {
        return reply_time;
    }
}