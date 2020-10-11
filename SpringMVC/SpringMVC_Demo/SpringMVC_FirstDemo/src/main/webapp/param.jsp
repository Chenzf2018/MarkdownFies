<%--
  Created by IntelliJ IDEA.
  User: Chenzf
  Date: 2020/10/10
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>测试参数接收</h1>
<form action="${pageContext.request.contextPath}/ParamController/testObject" method="post">
    用户名: <input type="text" name="name"/>  <br>
    年龄:  <input type="text" name="age"/>   <br>
    性别:  <input type="text" name="sexual">    <br>
    工资:  <input type="text" name="salary"> <br>
    生日:  <input type="text" name="birth"> <br>
    <input type="submit" value="提交"/>
</form>

</body>
</html>
