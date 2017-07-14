package cn.dombro.schoolHelper.controller;

import cn.dombro.schoolHelper.model.Contact;
import cn.dombro.schoolHelper.model.User;
import cn.dombro.schoolHelper.service.ContactService;
import com.jfinal.core.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18246 on 2017/5/15.
 */
public class ContactController extends Controller{

    public void myContacts(){
        List<Contact> contactList = ContactService.getService().getContacts((Integer) getSession().getAttribute("userId"));
        List<User> userList = new ArrayList<User>();
        for(Contact contact : contactList){
            userList.add(contact.getUser());
        }
        renderJson(userList);
    }

    public void addContact(){
        int cid = getParaToInt("cid");
        int id = (Integer) getSession().getAttribute("userId");
        ContactService.getService().add(id,cid);
        renderText("添加成功");
    }

    public void deleteContact(){
        int cid = getParaToInt("cid");
        int id = (Integer) getSession().getAttribute("userId");
        ContactService.getService().delete(id,cid);
        renderText("删除成功");
    }

}
