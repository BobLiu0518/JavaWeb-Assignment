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
<div class="container" style="text-align: center; padding-top: 2rem;">
    <h1 style="color: #e74c3c;">失败！</h1>
    <p style="font-size: 1.2rem; margin: 1rem 0;">${message}</p>
    <div style="margin-top: 2rem;">
        <a href="javascript:history.back()" style="margin-right: 1rem;">返回上一页</a>
        <a href="<c:url value="/goods/" />">返回首页</a>
    </div>
</div>
</body>
</html>
