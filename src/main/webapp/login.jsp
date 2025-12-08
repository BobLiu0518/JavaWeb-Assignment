<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>二手交易平台</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<c:import url="/header.jsp"/>
<div class="container">
    <h1 style="text-align: center; margin-bottom: 1rem;">登录</h1>
    <form action="<c:url value="/auth/login" />" method="POST">
        <label>
            <span>用户名：</span>
            <input name="username" required/>
        </label>
        <label>
            <span>密码：</span>
            <input name="password" type="password" required/>
        </label>
        <button type="submit">登录</button>
    </form>
</div>
</body>
</html>
