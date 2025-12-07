<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>二手交易平台</title>
</head>
<body>
<c:import url="/header.jsp"/>
<h1>成功！</h1>
<p>${message}</p>
<a href="javascript:history.back()">返回上一页</a>
<a href="<c:url value="/goods/" />">返回首页</a>
</body>
</html>