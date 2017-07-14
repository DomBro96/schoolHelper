package cn.dombro.schoolHelper.model;



/**
 * Created by 18246 on 2017/5/18.
 */
public class UserComment {
    private User user;
    private Comment comment;

    public UserComment(Comment comment) {
        this.comment = comment;
        this.user = comment.getUser();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "UserComment{" +
                "user=" + user +
                ", comment=" + comment +
                '}';
    }
}
