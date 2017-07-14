package cn.dombro.schoolHelper.model;

/**
 * Created by 18246 on 2017/5/18.
 */
public class UserMoment {

    private User user;
    private Moment moment;

    public UserMoment(Moment moment) {
        this.moment = moment;
        this.user = moment.getUser();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Moment getMoment() {
        return moment;
    }

    public void setMoment(Moment moment) {
        this.moment = moment;
    }

    @Override
    public String toString() {
        return "UserMoment{" +
                "user=" + user +
                ", moment=" + moment +
                '}';
    }
}
