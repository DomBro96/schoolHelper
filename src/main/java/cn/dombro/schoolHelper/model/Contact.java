package cn.dombro.schoolHelper.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by 18246 on 2017/5/11.
 */
public class Contact extends Model<Contact> {

    public static final Contact dao = new Contact();

    //级联两个表的操作
    //以当前 contact 表中 cid 为键 ， 查询 该 cid 即好友的信息
    public User getUser(){
           return User.dao.findByIdLoadColumns(getInt("cid"),"id,account,username,gander,sign,facePath");
    }

}
