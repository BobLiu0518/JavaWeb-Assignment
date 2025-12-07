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
<h1>商品详情</h1>
<c:if test="${user != null && user.id == goods.publisherId}">
    <c:if test="${!goods.sold}">
        <a href="<c:url value="/goods/${goods.id}?action=sold"/>">卖掉了</a>
        <a href="<c:url value="/goods/${goods.id}?action=edit"/>">编辑</a>
    </c:if>
    <a href="<c:url value="/goods/${goods.id}?action=delete"/>">删除</a>
</c:if>

<img src="<c:url value="/image?hash=${goods.imageHash}&mime=${goods.imageMime}" />"/>
<p><c:out value="${goods.name}"/></p>
<p><c:out value="${goods.description}"/></p>
<p>￥<c:out value="${goods.price}"/></p>
</body>
</html>
