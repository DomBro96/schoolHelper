package cn.dombro.schoolHelper.controller;

import cn.dombro.schoolHelper.model.*;
import cn.dombro.schoolHelper.service.MomentService;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

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
        //1.获取前台 要显示 页号 默认值为 1
        int toPage = getParaToInt("toPage",1);
        //2.获取 当前页 分页列表
        Page<Moment> pageList = MomentService.getService().getList(toPage);
        //4.根据分页列表查询总页数
        int totalPage = pageList.getTotalPage();
        //5.数据库中数据总条数
        int totalRow = pageList.getTotalRow();
        //6.该分页列表所在页数
        int pageNum = pageList.getPageNumber();
        //7.在前台显示 第 xxx 页的超链接，由于前台无法知道到底分了多少页，所以计算出总页数，并传给前台
        String skip = "";
        for (int page = 1;page<=totalPage;page++){
            // skip = skip + "<a href=\"?toPage="+page+"\">"+page+"</a> ";
            skip = skip + "<a href=/moment/showMoment?toPage="+page+">"+page+"</a> ";
        }
        //8.获取该页内的 对象 List 记录
        List<Moment> momentList = pageList.getList();
        Map<UserMoment,List<UserComment>> momentListMap = MomentService.getService().getMomCommMap(momentList);
        getRequest().setAttribute("momentListMap",momentListMap);
        setAttr("pageNum",pageNum);
        setAttr("totalPage",totalPage);
        setAttr("totalRow",totalRow);
        setAttr("skip",skip);

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
