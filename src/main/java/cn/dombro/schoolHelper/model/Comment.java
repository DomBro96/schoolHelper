package cn.dombro.schoolHelper.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by 18246 on 2017/5/17.
 */
public class Comment extends Model<Comment> {

    public static final Comment dao = new Comment();

    /**
     *
      * @return 通过当前 Comment 的 uid 获取 到 User 对象，且 只取 其中 username 属性
     */
    public User getUser(){
        return User.dao.findByIdLoadColumns(get("uid"),"username");
    }
}
