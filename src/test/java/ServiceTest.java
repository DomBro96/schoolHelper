import cn.dombro.schoolHelper.model.Contact;
import cn.dombro.schoolHelper.model.Location;
import cn.dombro.schoolHelper.model.Moment;
import cn.dombro.schoolHelper.model.User;
import cn.dombro.schoolHelper.service.ContactService;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * Created by 18246 on 2017/5/12.
 */
public class ServiceTest {



    public static void main(String[] args) {

        PropKit.use("config.properties");
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"),PropKit.get("user"),PropKit.get("password"));
        dp.setDriverClass(PropKit.get("drivierClass"));
        dp.set(PropKit.getInt("initialSize"),PropKit.getInt("minIdle"),PropKit.getInt("maxActive"));
        dp.setMaxWait(PropKit.getInt("maxWait"));;
        //配置 ActiveRecordPlugin 插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        //设置 sql 文件位置
//        arp.setBaseSqlTemplatePath(PathKit.getWebRootPath()+"/WEB-INF");
//        arp.addSqlTemplate("sql/all.sql");
        //将 表名 和 Model 进行映射
        arp.addMapping("users",User.class);
        arp.addMapping("contact",Contact.class);
        arp.addMapping("moment",Moment.class);
        arp.addMapping("location",Location.class);
        dp.start();
        arp.start();

//        Contact contact = Contact.dao.findById(6);
//        System.out.println(contact);
//        System.out.println(contact.getUser());
//        Db.update("DELETE FROM contact WHERE id = "+5+" AND cid = "+6);
//        Db.update("DELETE FROM contact WHERE id = "+6+" AND cid = "+5);
//        Record record1 = Db.findFirst("SELECT id, cid FROM contact WHERE id = 5 AND cid = 6");
//        Db.delete("contact",record1);
//        Record record2 = Db.findFirst("SELECT id, cid FROM contact WHERE id = 6 AND cid = 5");
//        Db.delete("contact",record2);
          Contact.dao.findFirst("SELECT id, cid FROM contact WHERE id = 5 and cid = 6").delete();
//        Contact.dao.findFirst("SELECT id, cid FROM contact WHERE id = 6 and cid = 5").deleteById(6);

    }
}
