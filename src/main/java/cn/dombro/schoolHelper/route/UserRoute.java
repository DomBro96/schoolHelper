package cn.dombro.schoolHelper.route;

import cn.dombro.schoolHelper.controller.UserController;
import com.jfinal.config.Routes;

/**
 * Created by 18246 on 2017/5/12.
 */
public class UserRoute extends Routes {
    public void config() {
        setBaseViewPath("/front");
        add("/user", UserController.class,"/user");
    }
}
