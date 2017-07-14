package cn.dombro.schoolHelper.service;

import cn.dombro.schoolHelper.model.Contact;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by 18246 on 2017/5/12.
 */
public class ContactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    private static ContactService service = null;

    public static ContactService getService(){
        service = new ContactService();
        return service;
    }
    /*
     * 在 联系人数据表中 增加 记录
     */
    @Before(Tx.class)
    public void add(int id,int cid){
      getCount(id, cid);
      new Contact().set("id",id).set("cid",cid).save();
      LOGGER.info("向 contact 表中添加 "+ id +","+ cid +" 一条记录");
      new Contact().set("id",cid).set("cid",id).save();
      LOGGER.info("向 contact 表中添加 "+ cid +","+ id +" 一条记录");
    }

    //删除指定好友，须同时确认 id 和 cid,并从表中删除两项纪录
    //使用 model 进行删除 出现 bug（会删除其他记录）  所以这里采用 Db 的 update 方法 来进行删除操作
    public void delete(int id,int cid){
        Db.update("DELETE FROM contact WHERE id = "+id+" AND cid = "+cid);
        LOGGER.info("向 contact 表中删除 "+ id +","+ cid +" 一条记录");
        Db.update("DELETE FROM contact WHERE id = "+cid+" AND cid = "+id);
        LOGGER.info("向 contact 表中删除 "+ cid +","+ id +" 一条记录");
    }

    /**
     *  获取好友列表
     */
    public List<Contact> getContacts(int id){
        Kv cond = Kv.by("id",id);
        SqlPara para = Contact.dao.getSqlPara("contact.getList",cond);
        List<Contact> contacts = Contact.dao.find(para);
        LOGGER.info("向 contact 表中删获取以 " + id +"为键所有记录");
        return contacts;
    }

    public void getCount(int id,int cid){
        Kv condId = Kv.by("id",id).set("cid",cid);
        SqlPara paraId = Contact.dao.getSqlPara("contact.getId",condId);
        List<Contact> contactsById = Contact.dao.find(paraId);
        Kv congCid = Kv.by("id",cid).set("cid",id);
        SqlPara  paraCid = Contact.dao.getSqlPara("contact.getCid",congCid);
        List<Contact> contactsByCid = Contact.dao.find(paraCid);
        if(contactsByCid.size() > 0 && contactsById.size() > 0){
            LOGGER.info("id: "+id+"和 cid: "+cid+" 已经添加好友");
            throw new RuntimeException("已经添加好友了 ");
        }
    }



}
