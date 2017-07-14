package cn.dombro.schoolHelper.model;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.SqlPara;

import java.util.List;


/**
 * Created by 18246 on 2017/5/11.
 */
public class Moment extends Model<Moment> {

    public static final Moment dao = new Moment();

    //根据 朋友圈 id  找到用户(显示用户昵称)
    public User getUser(){
        return User.dao.findByIdLoadColumns(get("uid"),"username");
    }
    // 根据 mid 找到 评论列表
    public List<Comment> getComment(){
        Kv cond = Kv.by("mid",get("mid"));
        SqlPara para = Comment.dao.getSqlPara("comment.getList",cond);
        List<Comment> commentList = Comment.dao.find(para);
        return commentList;
    }
}
