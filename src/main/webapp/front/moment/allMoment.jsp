<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 18246
  Date: 2017/5/18
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>朋友圈</title>
</head>
<body>

      <c:if test="${!empty requestScope.momentListMap}">
          <table cellspacing="5">
              <c:forEach items="${requestScope.momentListMap}" var="usermoment" >
                  <tr>
                      <td>${usermoment.key.user}</td>
                      <td>${usermoment.key.moment}</td>
                  </tr>
                  <c:forEach items="${usermoment.value}" var="usercomment">
                      <tr>
                          <td>评论：</td>
                          <td>${usercomment.user}</td>
                          <td>${usercomment.comment}</td>
                      </tr>
                  </c:forEach>
                  <tr>
                      <td>
                          <form action="/moment/putComment" method="post">
                              <input type="hidden" name="mid" value="${usermoment.key.moment.mid}"/>
                              <textarea name="comment" rows="1" cols="30" >评论...</textarea>
                              &nbsp;<input type="submit" value="确定"/>
                          </form>
                      </td>
                  </tr>
              </c:forEach >
          </table>
      </c:if>
      <c:if test="${!empty requestScope.skip}">
   <a href="<%=request.getContextPath()%>/moment/showMoment?toPage=${pageNum>1?pageNum-1:1}">上一页</a> ${requestScope.skip} <a href="<%=request.getContextPath()%>/moment/showMoment?toPage=${pageNum<totalPage?pageNum+1:totalPage}">下一页</a>
      </c:if>
</body>
</html>
