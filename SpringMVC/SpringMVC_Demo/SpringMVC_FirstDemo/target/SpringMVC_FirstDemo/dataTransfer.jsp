<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Chenzf
  Date: 2020/10/10
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>测试数据传递机制</title>
</head>
<body>

<h1>测试数据传递机制</h1>
<h2>获取request作用域数据方式一：${requestScope.username}</h2>
<h2>获取request作用域数据方式二：${username}</h2>

<hr color="blue">
<h2>获取对象</h2>
<h2>name:${requestScope.user.name}</h2>
<h2>age:${requestScope.user.age}</h2>
<h2>salary:${requestScope.user.salary}</h2>
<h2>sexual:${requestScope.user.sexual}</h2>
<h2>birth:${requestScope.user.birth}</h2>
<h2>birth:<fmt:formatDate value="${requestScope.user.birth}" pattern="yyyy-MM-dd"/> </h2>
<hr color="blue">

<hr color="red">
<h2>获取集合</h2>
<c:forEach items="${requestScope.users}" var="user">
    name:${user.name}, age:${user.age} <br>
</c:forEach>
<hr color="red">

<hr color="green">



<h2>name:${sessionScope.user.name}</h2>
<h2>age:${sessionScope.user.age}</h2>
<h2>salary:${sessionScope.user.salary}</h2>
<h2>sexual:${sessionScope.user.sexual}</h2>
<h2>birth:${sessionScope.user.birth}</h2>

<hr color="green">

</body>
</html>
