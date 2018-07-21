package entity;
/**
 * Created by 韩黎明
 */
import java.util.List;

public class Comment {
    private String commentId;
    private String userId;
    private String userName;
    private String img_id;
    private String content;
    private String activityId;
    private String time;
    private List<ReplyCard> replyCardList;

    public Comment(String commentId,String userId,String userName,String img_id,String content,String activityId,String time,List<ReplyCard> replyCardList){
        this.commentId = commentId;
        this.userId = userId;
        this.userName = userName;
        this.img_id = img_id;
        this.content = content;
        this.activityId = activityId;
        this.time = time;
        this.replyCardList = replyCardList;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getImg_id() {
        return img_id;
    }

    public String getContent() {
        return content;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getTime() {
        return time;
    }

    public List<ReplyCard> getReplyCardList() {
        return replyCardList;
    }

    public CommentCard toCommentCard(){
        return new CommentCard(commentId,userName, img_id,content,time,replyCardList);
    }
}