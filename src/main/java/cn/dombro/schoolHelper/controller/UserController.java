package cn.dombro.schoolHelper.controller;

import cn.dombro.schoolHelper.model.User;
import cn.dombro.schoolHelper.service.UserService;
import cn.dombro.schoolHelper.util.UploadUtil;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import java.io.*;

/**
 * Created by 18246 on 2017/5/12.
 */
public class UserController extends Controller{


    public void newUser(){
        UploadFile file = getFile("face");
        File source = file.getFile();
        String account = getPara("account");
        String username = getPara("username");
        String password = getPara("password");
        String gander = getPara("gander");
        String sign = getPara("sign");
        UploadUtil.upload(source,account);
        String facePath = UploadUtil.getFilePath();
        UserService.getService().insert(account,username,password,gander,sign,facePath);
        renderText("添加用户成功");
    }

    public void login(){
        String account = getPara("account");
        String password = getPara("password");
        User user = UserService.getService().get(account,password);
        getSession().setAttribute("userId",user.get("id"));
        renderJson(user);
    }
    public void searchUser(){
        String account = getPara("account");
        User user = UserService.getService().get(account);
        renderJson(user);
    }

    public void updateUser(){
         UserService.getService().updateUser(1,"DD","123","男","世界更美好","D://");
         renderText("成功");
    }


}
