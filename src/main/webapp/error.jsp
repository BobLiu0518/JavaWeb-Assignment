<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>在线问答留言交互平台</title>
    <link rel="stylesheet" href="<c:url value="/style.css"/>">
</head>
<body>
<div class="container">
    <div class="header topbar">
        <h1>在线问答留言交互平台</h1>
        <div class="nav">
            <a class="footer-link" href="<c:url value="/threads"/>">返回主页</a>
            <a class="footer-link" href="javascript:history.back()">返回上一页</a>
        </div>
    </div>
    <div class="card">
        <p>出错了：${message}</p>
    </div>
</div>
</body>
</html>
