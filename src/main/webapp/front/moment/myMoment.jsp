<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 18246
  Date: 2017/5/17
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的朋友圈</title>
</head>
<body>
<c:if test="${!empty requestScope.myMomentListMap}">
    <table cellspacing="5">
        <c:forEach items="${requestScope.myMomentListMap}" var="mymoment">
            <tr>
                <td>${mymoment.key.user}</td>
                <td>${mymoment.key.moment}</td>
                <td><a href="<%=request.getContextPath()%>/moment/deleteMoment/${mymoment.key.moment.mid}">删除</a></td>
            </tr>
            <c:forEach items="${mymoment.value}" var="usercomment">
                <tr>
                    <td>评论：</td>
                    <td>${usercomment.user}</td>
                    <td>${usercomment.comment}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
