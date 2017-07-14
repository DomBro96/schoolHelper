package cn.dombro.schoolHelper.service;

import cn.dombro.schoolHelper.model.User;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by 18246 on 2017/5/11.
 */
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static UserService service = null;

    public static UserService getService(){
        service = new UserService();
        return service;
    }

    public User get(String account,String password){
        Kv cond = Kv.by("account",account).set("password",password);
        SqlPara para = User.dao.getSqlPara("users.get",cond);
        User user = User.dao.findFirst(para);
       if(user == null){
           LOGGER.info("account : "+account +"password:"+ password +"不存在");
           throw new RuntimeException("用户不存在");
       }
       return user;
    }

    /**
     * 根据账户 查找 ，用于添加 好友 业务
     */
    public User get(String account){
      Kv cond = Kv.by("account",account);
      SqlPara para = User.dao.getSqlPara("users.account",cond);
      User user = User.dao.findFirst(para);
        if(user == null){
            LOGGER.info("account : "+account +"不存在");
            throw new RuntimeException("用户不存在");
        }
        return user;
    }

    //声明为事务
    @Before(Tx.class)
    public void insert(String account,String name,String password,String gander,String sign,String facePath){
        getAccountCount(account);
         new User().set("account",account).set("username",name).set("password",password).set("gander",gander).
                set("sign",sign).set("facePath",facePath).save();
        LOGGER.info("向 users 表中添加一条记录 账号为 "+account );
    }

    //更新
    public void updateUser(int id,String name,String password,String gander,String sign,String facePath){
        User.dao.findById(id).set("username",name).set("password",password).set("gander",gander).
                set("sign",sign).set("facePath",facePath).update();
        LOGGER.info("向 users 表中 更新 一条记录 id 为 "+ id);
    }


    public void getAccountCount(String account){
        Kv cond = Kv.by("account",account);
        SqlPara para = User.dao.getSqlPara("users.account",cond);
        List<User> users = User.dao.find(para);
        if(users.size()> 0){
            LOGGER.info("该账号已经注册了");
            throw new RuntimeException("账号已经被注册");
        }
    }

}
