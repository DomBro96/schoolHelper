package cn.dombro.schoolHelper.common;

import cn.dombro.schoolHelper.model.*;
import cn.dombro.schoolHelper.route.ContactRoute;
import cn.dombro.schoolHelper.route.MomentRoute;
import cn.dombro.schoolHelper.route.UserRoute;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.template.Engine;

/**
 * Created by 18246 on 2017/5/11.
 */
public class MyConfig extends JFinalConfig {
    public void configConstant(Constants constants) {

        PropKit.use("config.properties");
        //开发者模式
        constants.setDevMode(true);
    }

    public void configRoute(Routes routes) {

        routes.add(new UserRoute());
        routes.add(new ContactRoute());
        routes.add(new MomentRoute());
    }

    public void configEngine(Engine engine) {

    }

    public void configPlugin(Plugins plugins) {
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"),PropKit.get("user"),PropKit.get("password"));
        dp.setDriverClass(PropKit.get("drivierClass"));
        dp.set(PropKit.getInt("initialSize"),PropKit.getInt("minIdle"),PropKit.getInt("maxActive"));
        dp.setMaxWait(PropKit.getInt("maxWait"));
        dp.addFilter(new StatFilter());
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        dp.addFilter(wallFilter);
        plugins.add(dp);
        //配置 ActiveRecordPlugin 插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        //设置 sql 文件位置
        arp.setBaseSqlTemplatePath(PathKit.getWebRootPath()+"/WEB-INF");
        arp.addSqlTemplate("sql/all.sql");
        //将 表名 和 Model 进行映射
        arp.addMapping("users",User.class);
        arp.addMapping("contact",Contact.class);
        arp.addMapping("moment","mid",Moment.class);
        arp.addMapping("location",Location.class);
        arp.addMapping("comment","cid",Comment.class);
        plugins.add(arp);
    }

    public void configInterceptor(Interceptors interceptors) {
        //测试了一下
    }

    public void configHandler(Handlers handlers) {
        DruidStatViewHandler druidStatViewHandler = new DruidStatViewHandler("/sh_druid");
        handlers.add(druidStatViewHandler);
    }
    public static void main(String[] args) {
        JFinal.start("src/main/webapp",8080,"/");

    }
}
