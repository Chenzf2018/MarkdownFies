<%--
  Created by IntelliJ IDEA.
  User: Chenzf
  Date: 2020/10/11
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>添加用户信息</title>
</head>
<body>

<h1>添加用户信息</h1>
<form action="${pageContext.request.contextPath}/UserController/saveUser" method="post">
    用户id：<input type="text" name="id"/> <br>
    用户名：<input type="text" name="name"/> <br>
    用户年龄：<input type="text" name="age"/> <br>
    用户生日：<input type="text" name="birth"/> <br>
    <input type="submit" value="保存用户信息">
</form>

</body>
</html>
