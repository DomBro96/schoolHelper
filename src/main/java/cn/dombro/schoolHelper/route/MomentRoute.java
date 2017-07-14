package cn.dombro.schoolHelper.route;

import cn.dombro.schoolHelper.controller.MomentController;
import com.jfinal.config.Routes;

/**
 * Created by 18246 on 2017/5/15.
 */
public class MomentRoute extends Routes {

    public void config() {
        setBaseViewPath("/front");
        add("/moment",MomentController.class,"/moment");
    }
}
