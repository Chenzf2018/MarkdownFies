<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Chenzf
  Date: 2020/10/11
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>展示所有用户信息</title>
</head>
<body>

<h1>展示用户列表</h1>
<c:forEach items="${requestScope.users}" var="user">
    ${user.id}, ${user.name}, ${user.age}, ${user.birth} <br>
</c:forEach>

<a href="${pageContext.request.contextPath}/addUser.jsp">添加用户信息</a>

</body>
</html>
