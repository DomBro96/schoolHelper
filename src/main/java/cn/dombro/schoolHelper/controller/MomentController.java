package cn.dombro.schoolHelper.controller;

import cn.dombro.schoolHelper.model.*;
import cn.dombro.schoolHelper.service.MomentService;
import com.jfinal.core.Controller;

import java.util.List;
import java.util.Map;

/**
 * Created by 18246 on 2017/5/15.
 */
public class MomentController extends Controller {


    public void putMoment(){
         String moment = getPara("moment");
         MomentService.getService().insert((Integer)getSession().getAttribute("userId"),moment);
         renderText("添加成功");
    }

    public void deleteMoment(){
         int mid = getParaToInt(0);
         MomentService.getService().delete(mid);
         renderText("删除成功");
    }

    public void showMoment(){
        List<Moment> momentList = MomentService.getService().getList();
        //Map<Moment,User> momentUserMap = MomentService.getService().getMomentMap(momentList);
        Map<UserMoment,List<UserComment>> momentListMap = MomentService.getService().getMomCommMap(momentList);
        getRequest().setAttribute("momentListMap",momentListMap);
        renderJsp("allMoment.jsp");
        //renderJson(momentListMap);
    }

    public void myMoment(){
        List<Moment> myMoments = MomentService.getService().getForUid((Integer)getSession().getAttribute("userId"));
        Map<UserMoment,List<UserComment>> myMomentListMap = MomentService.getService().getMomCommMap(myMoments);
        getRequest().setAttribute("myMomentListMap",myMomentListMap);
        renderJsp("myMoment.jsp");

    }

    public void putComment(){
        String comment = getPara("comment");
        int mid = getParaToInt("mid");
        int uid = (Integer) getSession().getAttribute("userId");
        MomentService.getService().addComm(mid,uid,comment);
        renderText("评论成功");
    }

}
