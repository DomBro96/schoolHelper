package cn.dombro.schoolHelper.route;

import cn.dombro.schoolHelper.controller.ContactController;
import com.jfinal.config.Routes;

/**
 * Created by 18246 on 2017/5/15.
 */
public class ContactRoute extends Routes {
    public void config() {
        setBaseViewPath("/front");
        add("/contact",ContactController.class,"/contact");
    }
}
