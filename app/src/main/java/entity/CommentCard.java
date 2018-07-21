package entity;

import java.util.List;
/**
 * Created by 赵紫微
 */
public class CommentCard {
    private String commentId;
    private String name;
    private String img_id;
    private String content;
    private String time;
    private List<ReplyCard> replyCardList;

    public CommentCard(String commentId,String name,String img_id,String content,String time,List<ReplyCard> replyCardList){
        this.commentId = commentId;
        this.name = name;
        this.img_id = img_id;
        this.content = content;
        this.time = time;
        this.replyCardList = replyCardList;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getName() {
        return name;
    }

    public String getImg_id() {
        return img_id;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public List<ReplyCard> getReplyCardList() {
        return replyCardList;
    }

    public void setReplyCardList(List<ReplyCard> replyCardList) {
        this.replyCardList = replyCardList;
    }
}