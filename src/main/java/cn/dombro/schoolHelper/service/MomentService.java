package cn.dombro.schoolHelper.service;

import cn.dombro.schoolHelper.model.*;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 18246 on 2017/5/15.
 */
public class MomentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MomentService.class);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Map<Moment,User> momentUserMap = null;
    private Map<UserMoment,List<UserComment>> momentComment = null;
    private List<Comment> commentList = new ArrayList<Comment>();
    private List<UserComment> userCommentList = null;

    private static MomentService service = new MomentService();
    public  static MomentService getService(){
        return service;
    }
    //添加记录
    public void insert(int uid,String moment){
        String pubtime = simpleDateFormat.format(new Date());
        new Moment().set("uid",uid).set("moment",moment).set("pubtime",pubtime).save();
        LOGGER.info("向 moment 表中 添加了一条记录 uid : "+ uid +" moment : " +moment+" public : "+pubtime);
    }

    public Page<Moment> getList(int pageNum){
        //1.得到 SqlPara 对象(该对象封装了带参数的Sql语句，当然也可以不带参数)
        SqlPara sqlPara = Moment.dao.getSqlPara("moment.getAll");
        //2.通过 Model.dao.paginate()将 表中记录 分页查询出来
        Page<Moment> momentPage = Moment.dao.paginate(pageNum,3,sqlPara);
        LOGGER.info("从 moment 表中 分页查询全部记录 页数： "+pageNum+" 每页大小 ：3");
        return momentPage;
    }
    //查看 指定 用户 id 的 朋友圈
    public List<Moment> getForUid(int uid){
        Kv cond = Kv.by("uid",uid);
        SqlPara para = Moment.dao.getSqlPara("moment.get",cond);
        LOGGER.info("从 moment 表中 查询 uid 为 "+uid+" 的全部记录");
        return Moment.dao.find(para);
    }
//
//    public Map<Moment,User> getMomentMap(List<Moment> momentList){
//         momentUserMap = new HashMap<Moment, User>();
//         for(Moment moment : momentList){
//             User user = Moment.dao.getUser();
//             momentUserMap.put(moment,user);
//         }
//         return momentUserMap;
//    }

    /**
     *
     * @param momentList  参数为 朋友圈 的列表
     * 因为要以 : <用户名：朋友圈> + List<用户名 ：评论> 显示 ,且 评论列表 对应唯一 朋友圈，需要使用 Map
     * 新建了两个类：
     * UserMoment ： 其中有 User和 Moment 两个属性，User 属性通过 Moment 的级联操作获取到（根据 uid 来获取，且仅获取 username 属性）
     * UserComment ：其中有 User 和 Comment 两个属性，User 属性通过 Comment 的级联操作获取到（根据 uid 来获取，且仅时获取 username 属性）
     *
     */
    public  Map<UserMoment,List<UserComment>> getMomCommMap(List<Moment> momentList){
            momentComment = new HashMap<UserMoment,List<UserComment>>();
            //1.遍历 传入的 朋友列表
            for (Moment moment:momentList ){
            //2.每次遍历都把 userCommentList（评论的 List）清空
                userCommentList = new ArrayList<UserComment>();
            //3.根据当前 moment 找到对应的 List<Comment>
                commentList = moment.getComment();
            //4.遍历 List<Comment>
                for(Comment comment : commentList){
            //5.对应生成 UserComment 对象 ，将其放入 userCommentList 中
                   UserComment userComment = new UserComment(comment);
                   userCommentList.add(userComment);
                }
            //6.根据当前 朋友圈 生成 对应 UserMoment 对象
                UserMoment userMoment = new UserMoment(moment);
            //7.将UserMoment 作为键 对应 评论 列表 作为 值 放入 Map
                momentComment.put(userMoment,userCommentList);
            }

            return momentComment;
    }



    //删除 一条 朋友圈,以及其评论
    @Before(Tx.class)
    public void delete(int mid){
        Kv cond = Kv.by("mid",mid);
        SqlPara para = Moment.dao.getSqlPara("moment.deleteMoment",cond);
        Moment moment =  Moment.dao.findFirst(para);
        List<Comment> commentList = moment.getComment();
        moment.delete();
        LOGGER.info("从 moment 表中 删除了一条记录 mid :"+mid);
        deleteComm(commentList);

    }

    public void deleteComm(List<Comment> commentList){
        for(Comment comment : commentList){
            Comment.dao.deleteById(comment.get("cid"));
            LOGGER.info("从 comment 表中 删除了一条记录  cid :"+comment.get("cid"));
        }
    }

    //增加评论
    public void addComm(int mid,int uid,String comment){
        String pubtime = simpleDateFormat.format(new Date());
        if (uid == 0){
            throw new RuntimeException("请登录后评论");
        }
        new Comment().set("mid",mid).set("uid",uid).set("comment",comment).set("pubtime",pubtime).save();
        LOGGER.info("向 comment 表中 添加了一条记录 mid :"+mid+"  uid : "+ uid +" coamment : " +comment+" public : "+pubtime);
    }

    public List<Comment> getForMid(int mid){
        Kv cond = Kv.by("mid",mid);
        SqlPara para = Comment.dao.getSqlPara("comment.getList",cond);
        List<Comment> commentList = Comment.dao.find(para);
        LOGGER.info("从 comment 表中 查询 mid 为 "+mid+" 的全部记录");
        return  commentList;

    }

    public List<Comment> getAllComment(){

        String sql = Comment.dao.getSql("comment.getAll");
        List<Comment> commentList = Comment.dao.find(sql);
        LOGGER.info("从 comment 表中 查询 全部记录");
        return commentList;
    }

}
